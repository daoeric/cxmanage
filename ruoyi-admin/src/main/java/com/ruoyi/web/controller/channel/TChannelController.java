package com.ruoyi.web.controller.channel;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.channel.domain.TChannel;
import com.ruoyi.channel.service.ITChannelService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.common.vo.payment.BalanceResult;
import com.ruoyi.payment.IPaymentService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 通道信息Controller
 * 
 * @author ruoyi
 * @date 2022-04-23
 */
@RestController
@RequestMapping("/channel/channel")
public class TChannelController extends BaseController
{
    @Autowired
    private ITChannelService tChannelService;

    @GetMapping("/alias")
    public AjaxResult alias()
    {
        return AjaxResult.success(tChannelService.listAlias());
    }


    /**
     * 查询通道信息列表
     */
    @PreAuthorize("@ss.hasPermi('channel:channel:list')")
    @GetMapping("/list")
    public TableDataInfo list(TChannel tChannel)
    {
        startPage();
        List<TChannel> list = tChannelService.selectTChannelList(tChannel);
        return getDataTable(list);
    }

    /**
     * 不分页查询可用通道信息
     * @return
     */
    @PreAuthorize("@ss.hasPermi('channel:channel:list')")
    @GetMapping("/list/all")
    public AjaxResult listAll()
    {
        QueryWrapper<TChannel> wrapper = new QueryWrapper<>();
        wrapper.eq("status",0);
        return AjaxResult.success(tChannelService.list(wrapper));
    }

//    /**
//     * 不分页查询所有通道信息
//     * @return
//     */
//    @PreAuthorize("@ss.hasPermi('channel:channel:list')")
//    @GetMapping("/list/all2/{code}")
//    public AjaxResult listAll2(@PathVariable("code")String code)
//    {
//        QueryWrapper<TChannel> wrapper = new QueryWrapper<>();
//        wrapper.eq("code",code);
//        return AjaxResult.success(tChannelService.list(wrapper));
//    }


    /**
     * 导出通道信息列表
     */
    @PreAuthorize("@ss.hasPermi('channel:channel:export')")
    @Log(title = "通道信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TChannel tChannel)
    {
        List<TChannel> list = tChannelService.selectTChannelList(tChannel);
        ExcelUtil<TChannel> util = new ExcelUtil<TChannel>(TChannel.class);
        return util.exportExcel(list, "通道信息数据");
    }

    /**
     * 获取通道信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('channel:channel:query')")
    @GetMapping(value = "/{channelId}")
    public AjaxResult getInfo(@PathVariable("channelId") Integer channelId)
    {
        return AjaxResult.success(tChannelService.selectTChannelById(channelId));
    }

    /**
     * 新增通道信息
     */
    @PreAuthorize("@ss.hasPermi('channel:channel:add')")
    @Log(title = "通道信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TChannel tChannel)
    {
        return toAjax(tChannelService.insertTChannel(tChannel));
    }

    /**
     * 修改通道信息
     */
    @PreAuthorize("@ss.hasPermi('channel:channel:edit')")
    @Log(title = "通道信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TChannel tChannel)
    {
        return toAjax(tChannelService.updateTChannel(tChannel));
    }

    /**
     * 删除通道信息
     */
    @PreAuthorize("@ss.hasPermi('channel:channel:remove')")
    @Log(title = "通道信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{channelIds}")
    public AjaxResult remove(@PathVariable Integer[] channelIds)
    {
        return toAjax(tChannelService.deleteTChannelByIds(channelIds));
    }

    /**
     * 根据产品ID获取通道
     */
    @GetMapping("/list/{productId}")
    public AjaxResult list(@PathVariable String productId)
    {
        List<TChannel> channelList = tChannelService.listByProductId(productId);
        return AjaxResult.success(channelList);
    }

    @GetMapping("/upFlow/balance")
    public AjaxResult balanceList()
    {
        //获取所有上游的余额
        List<BalanceResult> balanceResultList = tChannelService.listBalance();
        return AjaxResult.success(balanceResultList);
    }

    @GetMapping("/upFlow/balance/{code}")
    public AjaxResult upFlowBalance(@PathVariable String code){
        try {
            IPaymentService paymentService = SpringUtils.getBean(code);
            BalanceResult balanceResult = paymentService.getBalance();
            return AjaxResult.success(balanceResult);
        } catch (BeansException e) {
            throw new CustomException(e.getMessage());
        }
    }

}
