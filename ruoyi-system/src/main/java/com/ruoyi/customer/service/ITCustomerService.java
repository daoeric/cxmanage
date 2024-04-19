package com.ruoyi.customer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.dto.UpdateAgentDto;
import com.ruoyi.common.dto.admin.CustomerSearchDto;
import com.ruoyi.common.vo.CustomerAdminVO;
import com.ruoyi.customer.domain.TCustomer;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 商户信息Service接口
 * 
 * @author yf
 * @date 2022-04-17
 */
public interface ITCustomerService extends IService<TCustomer>
{
    /**
     * 查询商户信息
     * 
     * @param id 商户信息ID
     * @return 商户信息
     */
    public TCustomer selectTCustomerById(Long id);

    /**
     * 查询商户信息列表
     * 
     * @param tCustomer 商户信息
     * @return 商户信息集合
     */
    public List<TCustomer> selectTCustomerList(TCustomer tCustomer);

    /**
     * 新增商户信息
     * 
     * @param tCustomer 商户信息
     * @return 结果
     */
    public int insertTCustomer(TCustomer tCustomer);

    /**
     * 修改商户信息
     * 
     * @param tCustomer 商户信息
     * @return 结果
     */
    public int updateTCustomer(TCustomer tCustomer);

    /**
     * 批量删除商户信息
     * 
     * @param ids 需要删除的商户信息ID
     * @return 结果
     */
    public int deleteTCustomerByIds(Long[] ids);

    /**
     * 删除商户信息信息
     * 
     * @param id 商户信息ID
     * @return 结果
     */
    public int deleteTCustomerById(Long id);

    /**
     * 修改用户额度
     * @param mchId
     * @param amount
     * @return
     */
    boolean addAmount(Long mchId, BigDecimal amount, Integer type);


    boolean withdrawal(Long mchId, BigDecimal amount,BigDecimal fee, int flag,String orderNo);

    TCustomer selectTCustomerByUsername(String username);

    /**
     * 切换谷歌登录模式
     * @return
     */
    boolean switchSafeMode(Long userId);

    /**
     * 修改密码
     * @param id
     * @param encryptPassword
     * @return
     */
    boolean updatePassword(Long id, String encryptPassword);

    /**
     * 修改用户通道
     * @param tCustomer
     * @return
     */
    boolean updateChanel(TCustomer tCustomer);

    /**
     * 管理端商户户list
     * @param tCustomer
     * @return
     */
    List<CustomerAdminVO> selectAdminCustomerList(CustomerSearchDto tCustomer);

    /**
     * 修改结算计划
     * @param id
     * @param planId
     * @return
     */
    boolean updatePlan(Long id, Integer planId);

    /**
     * 重置密码
     * @param type  0-登录密码 1- 提现密码
     * @param userId 商户号
     * @param password 需要重置的密码
     * @return
     */
    boolean updatePwd(Integer type, Long userId, String password);

    /**
     * 充值谷歌
     * @param userId
     * @return
     */
    boolean resetGoogle(Long userId);

    /**
     * 添加商户额度
     * @param id
     * @param amount
     * @param remark
     * @return
     */
    boolean addBalance(Long id, BigDecimal amount, String remark);


    void addTotalUsed(Long mchId, BigDecimal amount);

    /**
     *
     * @param mchId
     * @param status
     * @return
     */
    boolean updateStatus(Long mchId, Integer status);

    /**
     * 获取所有代理
     * @return
     */
    List<TCustomer> listAgent();

    /**
     * 修改代理
     * @param updateAgentDto
     * @return
     */
    boolean updateAgent(UpdateAgentDto updateAgentDto);

    /**
     * 根据password修改密码 登陆或提现
     * @param id
     * @param encryptPassword
     * @param passwordMode
     * @return
     */
    boolean updatePassword(Long id, String encryptPassword, Integer passwordMode);

    List<Long> getChildrenIdList(TCustomer customer);


    Map<String, Object> sumBalance(CustomerSearchDto tCustomer);
}
