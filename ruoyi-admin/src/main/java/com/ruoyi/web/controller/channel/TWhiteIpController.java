package com.ruoyi.web.controller.channel;

import com.ruoyi.channel.domain.TWhiteIp;
import com.ruoyi.channel.service.ITWhiteIpService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.dto.ChannelStatusDto;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * IP白名单Controller
 * 
 * @author yf
 * @date 2022-05-30
 */
@RestController
@RequestMapping("/channel/ip")
public class TWhiteIpController extends BaseController
{
    @Autowired
    private ITWhiteIpService tWhiteIpService;

    /**
     * 查询IP白名单列表
     */
    @PreAuthorize("@ss.hasPermi('channel:ip:list')")
    @GetMapping("/list")
    public TableDataInfo list(TWhiteIp tWhiteIp)
    {
        startPage();
        List<TWhiteIp> list = tWhiteIpService.selectTWhiteIpList(tWhiteIp);
        return getDataTable(list);
    }

    /**
     * 导出IP白名单列表
     */
    @PreAuthorize("@ss.hasPermi('channel:ip:export')")
    @Log(title = "IP白名单", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TWhiteIp tWhiteIp)
    {
        List<TWhiteIp> list = tWhiteIpService.selectTWhiteIpList(tWhiteIp);
        ExcelUtil<TWhiteIp> util = new ExcelUtil<TWhiteIp>(TWhiteIp.class);
        return util.exportExcel(list, "IP白名单数据");
    }

    /**
     * 获取IP白名单详细信息
     */
    @PreAuthorize("@ss.hasPermi('channel:ip:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(tWhiteIpService.selectTWhiteIpById(id));
    }

    /**
     * 新增IP白名单
     */
    @PreAuthorize("@ss.hasPermi('channel:ip:add')")
    @Log(title = "IP白名单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TWhiteIp tWhiteIp)
    {
        return toAjax(tWhiteIpService.insertTWhiteIp(tWhiteIp));
    }

    /**
     * 修改IP白名单
     */
    @PreAuthorize("@ss.hasPermi('channel:ip:edit')")
    @Log(title = "IP白名单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TWhiteIp tWhiteIp)
    {
        return toAjax(tWhiteIpService.updateTWhiteIp(tWhiteIp));
    }

    @PreAuthorize("@ss.hasPermi('channel:ip:edit')")
    @Log(title = "IP白名单", businessType = BusinessType.UPDATE)
    @PostMapping("/update/status")
    public AjaxResult changeStatus(@RequestBody ChannelStatusDto dto)
    {
        return toAjax(tWhiteIpService.changeStatus(dto));
    }



    /**
     * 删除IP白名单
     */
    @PreAuthorize("@ss.hasPermi('channel:ip:remove')")
    @Log(title = "IP白名单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(tWhiteIpService.deleteTWhiteIpByIds(ids));
    }
}
