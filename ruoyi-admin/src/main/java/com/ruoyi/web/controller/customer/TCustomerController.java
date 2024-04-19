package com.ruoyi.web.controller.customer;

import com.github.pagehelper.PageHelper;
import com.ruoyi.common.annotation.GoogleCodeCheck;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.dto.*;
import com.ruoyi.common.dto.admin.CustomerSearchDto;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.RedisLock;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.google.GoogleAuthenticator;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.common.vo.CustomerAdminVO;
import com.ruoyi.customer.domain.TCustomer;
import com.ruoyi.customer.service.ITCustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 商户信息Controller
 * 
 * @author yf
 * @date 2022-04-17
 */
@RestController
@Slf4j
@RequestMapping("/customer/info")
public class TCustomerController extends BaseController
{
    @Autowired
    private ITCustomerService tCustomerService;

    @Autowired
    private RedisLock redisLock;

    @GetMapping(value = "/agent/list")
    public AjaxResult agentList()
    {
        List<TCustomer> agentList = tCustomerService.listAgent();
        return AjaxResult.success(agentList);
    }

    @PostMapping(value = "/agent/update")
    @PreAuthorize("@ss.hasPermi('customer:info:edit')")
    @Log(title = "分配代理", businessType = BusinessType.UPDATE)
    public AjaxResult updateAgent(@Validated @RequestBody UpdateAgentDto updateAgentDto)
    {
        boolean result = tCustomerService.updateAgent(updateAgentDto);
        return AjaxResult.success(result);
    }

    /**
     * 查询商户信息列表
     */
    @PreAuthorize("@ss.hasPermi('customer:info:list')")
    @GetMapping("/list")
    public TableDataInfo list(CustomerSearchDto tCustomer)
    {
        startPage();
        List<CustomerAdminVO> list = tCustomerService.selectAdminCustomerList(tCustomer);
        TableDataInfo tableDataInfo = getDataTable(list);
        if (tableDataInfo.getTotal()>0) {
            PageHelper.clearPage();
            PageHelper.startPage(0,-1,false);
            Map<String,Object> map = tCustomerService.sumBalance(tCustomer);
            tableDataInfo.setOption(map);
        }
        return tableDataInfo;
    }

    /**
     * 导出商户信息列表
     */
    @PreAuthorize("@ss.hasPermi('customer:info:export')")
    @Log(title = "商户信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TCustomer tCustomer)
    {
        List<TCustomer> list = tCustomerService.selectTCustomerList(tCustomer);
        ExcelUtil<TCustomer> util = new ExcelUtil<TCustomer>(TCustomer.class);
        return util.exportExcel(list, "商户信息数据");
    }

    /**
     * 获取商户信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('customer:info:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tCustomerService.getById(id));
    }

    /**
     * 新增商户信息
     */
    @PreAuthorize("@ss.hasPermi('customer:info:add')")
    @Log(title = "商户信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TCustomer tCustomer)
    {
        String username = tCustomer.getUsername();
        TCustomer customer = tCustomerService.selectTCustomerByUsername(username);
        if (customer != null) {
            return AjaxResult.error("用户名已存在");
        }
        tCustomer.setPassword(SecurityUtils.encryptPassword(tCustomer.getPassword()));
        tCustomer.setWithdrawPassword(SecurityUtils.encryptPassword(tCustomer.getWithdrawPassword()));
        //生成私钥
        tCustomer.setPriKey(IdUtils.fastSimpleUUID());
        //生成谷歌验证
        tCustomer.setGoogleCode(GoogleAuthenticator.getRandomSecretKey());
        return toAjax(tCustomerService.insertTCustomer(tCustomer));
    }

    @PreAuthorize("@ss.hasPermi('customer:info:edit')")
    @Log(title = "禁用/启用商户", businessType = BusinessType.UPDATE)
    @PostMapping("updateStatus")
    public AjaxResult updateStatus(@RequestBody TCustomer tCustomer)
    {
       Long mchId = tCustomer.getId();
       Integer status = tCustomer.getStatus();
        return toAjax(tCustomerService.updateStatus(mchId,status));
    }

    /**
     * 修改商户信息
     */
    @PreAuthorize("@ss.hasPermi('customer:info:edit')")
    @Log(title = "商户信息", businessType = BusinessType.UPDATE)
    @PutMapping
    @GoogleCodeCheck
    public AjaxResult edit(@RequestBody TCustomer tCustomer)
    {
        return toAjax(tCustomerService.updateTCustomer(tCustomer));
    }


    @PreAuthorize("@ss.hasPermi('customer:info:edit')")
    @Log(title = "商户渠道变更", businessType = BusinessType.UPDATE)
    @PutMapping("/updateChanel")
    public AjaxResult updateChanel(@RequestBody TCustomer tCustomer)
    {
        return toAjax(tCustomerService.updateChanel(tCustomer));
    }



    /**
     * 删除商户信息
     */
    @PreAuthorize("@ss.hasPermi('customer:info:remove')")
    @Log(title = "商户信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tCustomerService.deleteTCustomerByIds(ids));
    }

    @PreAuthorize("@ss.hasPermi('customer:info:edit')")
    @Log(title = "商户修改结算方式", businessType = BusinessType.UPDATE)
    @PutMapping("/updatePlan")
    public AjaxResult updatePlan(@RequestBody UpdatePlanDto dto)
    {
        return toAjax(tCustomerService.updatePlan(dto.getId(),dto.getPlanId()));
    }

    @PreAuthorize("@ss.hasPermi('customer:info:edit')")
    @Log(title = "商户重置密码", businessType = BusinessType.UPDATE)
    @PutMapping("/resetPwd")
    @GoogleCodeCheck
    public AjaxResult resetPwd(@RequestBody ResetPwdBody dto)
    {
        return toAjax(tCustomerService.updatePwd(dto.getType(),dto.getId(),dto.getPassword()));
    }

    @PreAuthorize("@ss.hasPermi('customer:info:edit')")
    @Log(title = "重置谷歌", businessType = BusinessType.UPDATE)
    @PutMapping("/resetGoogle")
    @GoogleCodeCheck
    public AjaxResult resetGoogle(@RequestBody UserIdBody dto)
    {
        return toAjax(tCustomerService.resetGoogle(dto.getId()));
    }

    @PreAuthorize("@ss.hasPermi('customer:info:edit')")
    @Log(title = "添加商户额度", businessType = BusinessType.UPDATE)
    @GoogleCodeCheck
    @PutMapping("/addBalance")
    public AjaxResult addBalance(@Validated @RequestBody AddBalanceBody dto)
    {
        String key  = "addBalance:"+dto.getId();
        try{
            if(redisLock.tryLock(key,3,10,TimeUnit.SECONDS)){
                boolean rs = tCustomerService.addBalance(dto.getId(),dto.getAmount(),dto.getRemark());
                return toAjax(rs);
            }
        }catch (Exception e){
            log.error("添加商户额度异常{}",e.getMessage());
        }finally {
            redisLock.unlock(key);
        }
        return AjaxResult.error("添加商户额度异常");
    }

}
