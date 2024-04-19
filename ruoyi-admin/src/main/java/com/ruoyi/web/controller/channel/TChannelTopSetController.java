package com.ruoyi.web.controller.channel;

import com.ruoyi.channel.domain.TChannelTopSet;
import com.ruoyi.channel.domain.TProduct;
import com.ruoyi.channel.service.ITChannelTopSetService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.vo.ChannelSetVO;
import com.ruoyi.framework.manager.AsyncManager;
import com.ruoyi.framework.manager.factory.AsyncFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 通用费率配置Controller
 * 
 * @author cx
 * @date 2023-09-15
 */
@RestController
@RequestMapping("/channel/commonSet")
@Slf4j
public class TChannelTopSetController extends BaseController
{
    @Autowired
    private ITChannelTopSetService tChannelTopSetService;

    /**
     * 查询通用费率配置列表
     */
    @PreAuthorize("@ss.hasPermi('channel:commonSet:list')")
    @GetMapping("/list")
    public TableDataInfo list(TChannelTopSet tChannelTopSet)
    {
        startPage();
        List<ChannelSetVO> list = tChannelTopSetService.selectTChannelTopSetList(tChannelTopSet);
        return getDataTable(list);
    }

    /**
     * 导出通用费率配置列表
     */
    @PreAuthorize("@ss.hasPermi('channel:commonSet:export')")
    @Log(title = "通用费率配置", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TChannelTopSet tChannelTopSet)
    {
        List<ChannelSetVO> list = tChannelTopSetService.selectTChannelTopSetList(tChannelTopSet);
        ExcelUtil<ChannelSetVO> util = new ExcelUtil<ChannelSetVO>(ChannelSetVO.class);
        return util.exportExcel(list, "通用费率配置数据");
    }

    /**
     * 获取通用费率配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('channel:commonSet:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tChannelTopSetService.selectTChannelTopSetById(id));
    }

    /**
     * 新增通用费率配置
     */
    @PreAuthorize("@ss.hasPermi('channel:commonSet:add')")
    @Log(title = "通用费率配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TChannelTopSet tChannelTopSet)
    {
        int mode = tChannelTopSet.getMode();
        String channelIds = tChannelTopSet.getChannelIds();
        if (mode == 0 && channelIds.contains(",")) {
            throw new CustomException("参数有误！");
        }
        int rs = tChannelTopSetService.insertTChannelTopSet(tChannelTopSet);
        if (rs > 0) {
            //同步处理速度太慢了，这里用异步处理
            AsyncManager.me().execute(AsyncFactory.addCommonChannelAsync(tChannelTopSet));
        }
        return toAjax(rs);
    }

    /**
     * 修改通用费率配置
     */
    @PreAuthorize("@ss.hasPermi('channel:commonSet:edit')")
    @Log(title = "通用费率配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TChannelTopSet tChannelTopSet)
    {
        int mode = tChannelTopSet.getMode();
        String channelIds = tChannelTopSet.getChannelIds();
        if (!"A999".equals(tChannelTopSet.getProductId()) && mode == 1 && channelIds.contains(",")) {
            throw new CustomException("参数有误！");
        }
        return toAjax(tChannelTopSetService.updateTChannelTopSet(tChannelTopSet));
    }

    /**
     * 删除通用费率配置
     */
    @PreAuthorize("@ss.hasPermi('channel:commonSet:remove')")
    @Log(title = "通用费率配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tChannelTopSetService.deleteTChannelTopSetByIds(ids));
    }

    @GetMapping("/unProduct")
    public AjaxResult unProduct()
    {
        List<TProduct> productList = tChannelTopSetService.getUnProduct();
        return AjaxResult.success(productList);
    }
}
