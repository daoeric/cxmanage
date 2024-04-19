package com.ruoyi.web.controller.channel;

import com.ruoyi.channel.domain.TChannelSet;
import com.ruoyi.channel.domain.TProduct;
import com.ruoyi.channel.service.ITChannelService;
import com.ruoyi.channel.service.ITChannelSetService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.vo.ChannelSetVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 通道配置Controller
 * 
 * @author yf
 * @date 2022-05-22
 */
@RestController
@Slf4j
@RequestMapping("/channel/channelSet")
public class TChannelSetController extends BaseController
{
    @Autowired
    private ITChannelSetService tChannelSetService;
    @Autowired
    private ITChannelService channelService;

    /**
     * 查询通道配置列表
     */
    @PreAuthorize("@ss.hasPermi('channel:plan:list')")
    @GetMapping("/list")
    public TableDataInfo list(TChannelSet tChannelSet)
    {
        startPage();
        List<ChannelSetVO> list = tChannelSetService.selectTChannelSetList(tChannelSet);
        return getDataTable(list);
    }

    /**
     * 获取通道配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('channel:plan:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tChannelSetService.selectTChannelSetById(id));
    }

    /**
     * 新增通道配置
     */
    @PreAuthorize("@ss.hasPermi('channel:plan:add')")
    @Log(title = "通道配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TChannelSet tChannelSet)
    {
        return toAjax(tChannelSetService.insertTChannelSet(tChannelSet));
    }

    /**
     * 修改通道配置
     */
    @PreAuthorize("@ss.hasPermi('channel:plan:edit')")
    @Log(title = "通道配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TChannelSet tChannelSet)
    {

        try {
            return toAjax(tChannelSetService.updateTChannelSet(tChannelSet));
        } catch (Exception e) {
            log.error("修改通道配置出现异常：{}",e.getMessage(),e);
            throw new CustomException("修改通道配置出现异常"+e.getMessage());
        }
    }

    /**
     * 删除通道配置
     */
    @PreAuthorize("@ss.hasPermi('channel:plan:remove')")
    @Log(title = "通道配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tChannelSetService.deleteTChannelSetByIds(ids));
    }

    /**
     * 查询通道产品列表
     */
    @GetMapping("/unProduct/{planId}")
    public AjaxResult unProduct(@PathVariable Integer planId)
    {
        List<TProduct> productList = tChannelSetService.getUnProduct(planId);
        return AjaxResult.success(productList);
    }

//    @PostMapping("/addPlanDetail")
//    @Log(title = "添加通道详情", businessType = BusinessType.INSERT)
//    public AjaxResult addPlanDetail(@PathVariable Integer planId)
//    {
//        List<TProduct> productList = tChannelSetService.getUnProduct(planId);
//        return AjaxResult.success(productList);
//    }
}
