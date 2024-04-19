package com.ruoyi.web.controller.customer;

import com.github.pagehelper.PageHelper;
import com.ruoyi.common.annotation.GoogleCodeCheck;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.dto.ManualDepositDto;
import com.ruoyi.common.dto.admin.DepositSearchDto;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.RedisLock;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.common.vo.PaymentRequestVO;
import com.ruoyi.customer.domain.TPaymentRequest;
import com.ruoyi.customer.service.ITPaymentRequestService;
import com.ruoyi.framework.web.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 商户充值订单Controller
 * 
 * @author yf
 * @date 2022-04-17
 */
@RestController
@RequestMapping("/customer/deposit")
@Slf4j
public class TPaymentRequestController extends BaseController
{

    @Autowired
    private RedisLock redisLock;

    @Autowired
    private ITPaymentRequestService tPaymentRequestService;

    @Autowired
    private TokenService tokenService;

    /**
     * 查询商户充值订单列表
     */
    @PreAuthorize("@ss.hasPermi('customer:deposit:list')")
    @PostMapping("/list")
    public TableDataInfo list(@RequestBody DepositSearchDto depositSearchDto)
    {
        PageHelper.startPage(depositSearchDto.getPageNum(),depositSearchDto.getPageSize());
        List<PaymentRequestVO> list = tPaymentRequestService.listOrder(depositSearchDto);
        TableDataInfo tableDataInfo = getDataTable(list);
        if (tableDataInfo.getTotal()>0) {
            //查询统计总计
            PageHelper.startPage(0,-1,false);
            Map<String,Object> map = tPaymentRequestService.sum(depositSearchDto);
            tableDataInfo.setOption(map);
        }
        return tableDataInfo;
    }

    /**
     * 导出商户充值订单列表
     */
    @PreAuthorize("@ss.hasPermi('customer:deposit:export')")
    @Log(title = "商户充值订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public AjaxResult export(@RequestBody DepositSearchDto depositSearchDto)
    {
        depositSearchDto.setPageNum(null);
        depositSearchDto.setPageSize(null);
        List<PaymentRequestVO> list = tPaymentRequestService.listOrder(depositSearchDto);
        ExcelUtil<PaymentRequestVO> util = new ExcelUtil<>(PaymentRequestVO.class);
        return util.exportExcel(list, "商户充值订单数据");
    }

    /**
     * 获取商户充值订单详细信息
     */
    @PreAuthorize("@ss.hasPermi('customer:deposit:query')")
    @GetMapping(value = "/{requestId}")
    public AjaxResult getInfo(@PathVariable("requestId") String requestId)
    {
        return AjaxResult.success(tPaymentRequestService.selectTPaymentRequestById(requestId));
    }

    /**
     * 新增商户充值订单
     */
    @PreAuthorize("@ss.hasPermi('customer:deposit:add')")
    @Log(title = "商户充值订单", businessType = BusinessType.INSERT)
    @PostMapping
    @RepeatSubmit
    @GoogleCodeCheck
    public AjaxResult add(@Validated @RequestBody ManualDepositDto manualDepositDto)
    {
        try {
            return toAjax(tPaymentRequestService.insertTPaymentRequest(manualDepositDto));
        } catch (Exception e) {
            log.info("{}手动重置订单发起失败:{}",manualDepositDto,e.getMessage(),e);
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 修改商户充值订单
     */
    @PreAuthorize("@ss.hasPermi('customer:deposit:edit')")
    @Log(title = "商户充值订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TPaymentRequest tPaymentRequest)
    {
        return toAjax(tPaymentRequestService.updateTPaymentRequest(tPaymentRequest));
    }

    /**
     * 删除商户充值订单
     */
    @PreAuthorize("@ss.hasPermi('customer:deposit:remove')")
    @Log(title = "商户充值订单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{requestIds}")
    public AjaxResult remove(@PathVariable String[] requestIds)
    {
        return toAjax(tPaymentRequestService.deleteTPaymentRequestByIds(requestIds));
    }

    /**
     *手动回调订单
     */
    @PreAuthorize("@ss.hasPermi('customer:deposit:callback')")
    @Log(title = "手动回调", businessType = BusinessType.UPDATE)
    @PostMapping("/mualCallback/{requestId}")
    public AjaxResult mualCallback(@PathVariable String requestId)
    {
        String key = "mualCallback:"+requestId;
        //获取当前订单
        TPaymentRequest request = tPaymentRequestService.getById(requestId);
        String rs = "";
        try{
            if(redisLock.tryLock(key,3,10,TimeUnit.SECONDS)){
                tPaymentRequestService.doSuccess(requestId,null);
                rs = SpringUtils.getBean(ITPaymentRequestService.class).callbackCustomer(request);
            }
            //异步手动发起回调给下游
//            AsyncManager.me().execute(AsyncFactory.paymentCallback(request));
        }catch (Exception e){
            log.error("{}手动回调异常{}",requestId,e.getMessage(),e);
            return AjaxResult.error("手动回调异常:"+e.getMessage());
        } finally {
            redisLock.unlock(key);
        }
        return AjaxResult.success(rs);
    }
}
