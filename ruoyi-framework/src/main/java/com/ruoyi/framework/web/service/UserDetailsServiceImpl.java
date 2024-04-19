package com.ruoyi.framework.web.service;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.ruoyi.common.core.domain.model.LoginMerchantUser;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.exception.base.BaseException;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.customer.domain.TCustomer;
import com.ruoyi.customer.service.ITCustomerService;
import com.ruoyi.system.service.ISysConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.enums.UserStatus;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.MessageUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.service.ISysUserService;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.ip.IpUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户验证处理
 *
 * @author ruoyi
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private ISysUserService userService;
    
    @Autowired
    private SysPasswordService passwordService;

    @Autowired
    private SysPermissionService permissionService;

    @Autowired
    private ITCustomerService customerService;

    @Autowired
    private ISysConfigService sysConfigService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String url = request.getRequestURI();
        //根据url判断是客户端请求还是后台请求
        if (StringUtils.isNotEmpty(url) && url.startsWith(Constants.CLIENT_URL_PREFIX)) {//客户端请求
            TCustomer user = customerService.selectTCustomerByUsername(username);
            if (ObjectUtils.isNull(user))
            {
                log.info("登录用户：{} 不存在.", username);
                throw new UsernameNotFoundException("登录用户：" + username + " 不存在");
            }
            else if (UserStatus.DISABLE.getCode().equals(user.getStatus()+""))
            {
                log.info("登录用户：{} 已被停用.", username);
                throw new BaseException("对不起，您的账号：" + username + " 已停用");
            }
            LoginMerchantUser loginFbdUser = new LoginMerchantUser();
            BeanUtils.copyProperties(user,loginFbdUser);
            return loginFbdUser;
        } else {
            SysUser user = userService.selectUserByUserName(username);
            if (StringUtils.isNull(user))
            {
                log.info("登录用户：{} 不存在.", username);
                throw new ServiceException(MessageUtils.message("user.not.exists"));
            }
            else if (UserStatus.DELETED.getCode().equals(user.getDelFlag()))
            {
                log.info("登录用户：{} 已被删除.", username);
                throw new ServiceException(MessageUtils.message("user.password.delete"));
            }
            else if (UserStatus.DISABLE.getCode().equals(user.getStatus()))
            {
                log.info("登录用户：{} 已被停用.", username);
                throw new ServiceException(MessageUtils.message("user.blocked"));
            }
            passwordService.validate(user);
            //查看IP白名单
            String ips = sysConfigService.selectConfigByKey("white.ip");
            //如果配置了IP就需要验证ip白名单，其中admin用户不需要校验白名单
            if (StringUtils.isNotEmpty(ips)) {
                String requestIp = IpUtils.getIpAddr(ServletUtils.getRequest());
                if (!ips.contains(requestIp) && !username.equals("admin")) {
                    throw new CustomException(String.format("%s不在白名单内，请联系管理员加白",requestIp));
                }
            }
            return createLoginUser(user);
        }

    }

    public UserDetails createLoginUser(SysUser user)
    {
        return new LoginUser(user.getUserId(), user.getDeptId(), user, permissionService.getMenuPermission(user));
    }
}
