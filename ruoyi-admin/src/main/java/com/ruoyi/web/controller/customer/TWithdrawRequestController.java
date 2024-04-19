package com.ruoyi.web.controller.customer;

import com.beust.jcommander.internal.Maps;
import com.github.pagehelper.PageHelper;
import com.ruoyi.common.annotation.GoogleCodeCheck;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.dto.admin.MualWithAddDto;
import com.ruoyi.common.dto.admin.WithdrawalRequestApproveDto;
import com.ruoyi.common.dto.admin.WithdrawalSearchDto;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.enums.ExceptionEnum;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.RedisLock;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.common.vo.WithdrawalRequestVO;
import com.ruoyi.customer.domain.TWithdrawRequest;
import com.ruoyi.customer.service.ITWithdrawRequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 代付提现Controller
 * 
 * @author yf
 * @date 2022-04-17
 */
@RestController
@RequestMapping("/customer/withdrawal")
@Slf4j
public class TWithdrawRequestController extends BaseController
{
    @Autowired
    private ITWithdrawRequestService tWithdrawRequestService;

    @Autowired
    private RedisLock redisLock;

//    @Autowired
//    private WebSocketService webSocketService;

    /**
     * 查询代付提现列表
     */
    @PreAuthorize("@ss.hasPermi('customer:withdrawal:list')")
    @PostMapping("/list")
    public TableDataInfo list(@RequestBody WithdrawalSearchDto withdrawalSearchDto)
    {
        PageHelper.startPage(withdrawalSearchDto.getPageNum(),withdrawalSearchDto.getPageSize());
        List<WithdrawalRequestVO> list = tWithdrawRequestService.listOrder(withdrawalSearchDto);
        TableDataInfo tableDataInfo = getDataTable(list);
        if (tableDataInfo.getTotal()>0) {
            //查询统计总计
            PageHelper.clearPage();
            PageHelper.startPage(0,-1,false);
            Map<String,Object> map = tWithdrawRequestService.sum(withdrawalSearchDto);
            Map<String,Object> successMap = Maps.newHashMap();
            Map<String,Object> failMap = Maps.newHashMap();
            if (withdrawalSearchDto.getStatus() == null) {
                //代付成功率
                PageHelper.clearPage();
                PageHelper.startPage(0,-1,false);
                successMap = tWithdrawRequestService.successRate(withdrawalSearchDto);
                BigDecimal successCount = successMap.get("successCount") == null ? BigDecimal.ZERO : new BigDecimal(successMap.get("successCount").toString());
                long total = tableDataInfo.getTotal();
                BigDecimal rate  = successCount.divide(new BigDecimal(total),2,RoundingMode.HALF_UP).multiply(new BigDecimal(100));
                successMap.put("successRate",rate + "%");

                //失败统计
                PageHelper.clearPage();
                PageHelper.startPage(0,-1,false);
                failMap = tWithdrawRequestService.failRate(withdrawalSearchDto);
                //BigDecimal failCount = successMap.get("failCount") == null ? BigDecimal.ZERO : new BigDecimal(successMap.get("failCount").toString());
            }
            else if(withdrawalSearchDto.getStatus() == 1){
                successMap.put("successRate","0%");
                successMap.put("successTotalAmount",0);
                successMap.put("successCount",0);
                successMap.put("successProfit",0);

                failMap.put("failTotalAmount",0);
                failMap.put("failCount",0);

            } else if (withdrawalSearchDto.getStatus() == 3) {
                successMap.put("successRate","0%");
                successMap.put("successTotalAmount",0);
                successMap.put("successCount",0);
                successMap.put("successProfit",0);
                failMap.put("failTotalAmount",map.get("totalAmount"));
                failMap.put("failCount",tableDataInfo.getTotal());
            } else {
                successMap.put("successRate","100%");
                successMap.put("successTotalAmount",map.get("totalAmount"));
                successMap.put("successCount",tableDataInfo.getTotal());
                successMap.put("successProfit",map.get("profit"));
                failMap.put("failTotalAmount",0);
                failMap.put("failCount",0);
            }
            map.putAll(successMap);
            map.putAll(failMap);
            tableDataInfo.setOption(map);
        }
        return tableDataInfo;
    }

    /**
     * 导出代付提现列表
     */
    @PreAuthorize("@ss.hasPermi('customer:withdrawal:export')")
    @Log(title = "代付提现", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public AjaxResult export(@RequestBody WithdrawalSearchDto withdrawalSearchDto)
    {
        withdrawalSearchDto.setPageNum(null);
        withdrawalSearchDto.setPageSize(null);
        List<WithdrawalRequestVO> list = tWithdrawRequestService.listOrder(withdrawalSearchDto);
        ExcelUtil<WithdrawalRequestVO> util = new ExcelUtil<>(WithdrawalRequestVO.class);
        return util.exportExcel(list, "代付提现数据");
    }

    /**
     * 获取代付提现详细信息
     */
    @PreAuthorize("@ss.hasPermi('customer:withdrawal:query')")
    @GetMapping(value = "/{withdrawId}")
    public AjaxResult getInfo(@PathVariable("withdrawId") String withdrawId)
    {
        return AjaxResult.success(tWithdrawRequestService.selectTWithdrawRequestById(withdrawId));
    }

    /**
     * 新增代付提现
     */
    @PreAuthorize("@ss.hasPermi('customer:withdrawal:add')")
    @Log(title = "代付提现", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TWithdrawRequest tWithdrawRequest)
    {
        return toAjax(tWithdrawRequestService.insertTWithdrawRequest(tWithdrawRequest));
    }

    @PreAuthorize("@ss.hasPermi('customer:withdrawal:add')")
    @Log(title = "手动代付", businessType = BusinessType.INSERT)
    @GoogleCodeCheck
    @PostMapping("mualAdd")
    public AjaxResult mualAdd(@Validated @RequestBody MualWithAddDto mualWithAddDto) throws Exception {
        TWithdrawRequest result = tWithdrawRequestService.adminCreateMual(mualWithAddDto);

        if (result != null) {
            // 发送websocket消息给订阅者
            log.info("开始通知管理人员处理订单：{}",result);
            //webSocketService.newOrderReceived(result);
        }
        return AjaxResult.success(result);
    }

    /**
     * 修改代付提现
     */
    @PreAuthorize("@ss.hasPermi('customer:withdrawal:edit')")
    @Log(title = "代付提现", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TWithdrawRequest tWithdrawRequest)
    {
        return toAjax(tWithdrawRequestService.updateTWithdrawRequest(tWithdrawRequest));
    }

    @PreAuthorize("@ss.hasPermi('customer:withdrawal:edit')")
    @Log(title = "审核代付订单", businessType = BusinessType.UPDATE)
    @PutMapping("/approve")
    @GoogleCodeCheck
    public AjaxResult approve(@RequestBody WithdrawalRequestApproveDto tWithdrawRequest)
    {
        String key =  "withdrawlRequest:approve:"+tWithdrawRequest.getWithdrawId();
        boolean result = true;
        try{
            if (redisLock.tryLock(key, 3, 10, TimeUnit.SECONDS)) {
                Integer status = tWithdrawRequest.getStatus();
                if (status == null) {
                    return AjaxResult.error("参数有误");
                } else if(status == 2){//通过 扣去冻结额度
                    result = tWithdrawRequestService.doSuccess(tWithdrawRequest.getWithdrawId(),null,tWithdrawRequest.getRemark());
                } else if(status ==3){//驳回 扣去冻结额度 ，增加
                    result = tWithdrawRequestService.doFail(tWithdrawRequest.getWithdrawId(),null,tWithdrawRequest.getRemark());
                }
            }
        }catch (Exception e){
            log.error("{}审核代付订单异常：{}",tWithdrawRequest.getWithdrawId(),e.getMessage(),e);
            return AjaxResult.error(e.getMessage());
        } finally {
            redisLock.unlock(key);
        }
        return result?AjaxResult.success("处理成功"):AjaxResult.error("处理失败");
    }

    /**
     * 删除代付提现
     */
    @PreAuthorize("@ss.hasPermi('customer:withdrawal:remove')")
    @Log(title = "代付提现", businessType = BusinessType.DELETE)
	@DeleteMapping("/{withdrawIds}")
    public AjaxResult remove(@PathVariable String[] withdrawIds)
    {
        return toAjax(tWithdrawRequestService.deleteTWithdrawRequestByIds(withdrawIds));
    }

    /**
     * 代付手动回调
     */
    @PreAuthorize("@ss.hasPermi('customer:withdrawal:mualCallback')")
    @Log(title = "代付手动回调", businessType = BusinessType.UPDATE)
    @PostMapping("mualCallback/{withdrawId}")
    public AjaxResult remove(@PathVariable String withdrawId)
    {
        TWithdrawRequest request = tWithdrawRequestService.getById(withdrawId);
        if (request == null) {
            throw new CustomException(ExceptionEnum.ORDER_NO_EXIST);
        }
        String rs = SpringUtils.getBean(ITWithdrawRequestService.class).callbackCustomer(request);
        return AjaxResult.success(rs);
    }

    /**
     * 查询
     * @return
     */
    @GetMapping("notice/count")
    public AjaxResult getNoticeCount(){

        Integer count = tWithdrawRequestService.countNotice();

        return AjaxResult.success(count);

    }



}
