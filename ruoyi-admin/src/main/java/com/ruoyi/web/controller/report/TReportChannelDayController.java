package com.ruoyi.web.controller.report;

import com.github.pagehelper.PageHelper;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.dto.ReportChannelDayDTO;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.report.domain.TReportChannelDay;
import com.ruoyi.report.service.ITReportChannelDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 渠道统计Controller
 * 
 * @author ruoyi
 * @date 2023-10-04
 */
@RestController
@RequestMapping("/report/channelReport")
public class TReportChannelDayController extends BaseController
{
    @Autowired
    private ITReportChannelDayService tReportChannelDayService;

    /**
     * 查询渠道统计列表
     */
    @PreAuthorize("@ss.hasPermi('report:channelReport:list')")
    @PostMapping("/list")
    public TableDataInfo list(@RequestBody ReportChannelDayDTO dto)
    {
        startPage();
        List<TReportChannelDay> list = tReportChannelDayService.selectTReportChannelDayList(dto);
        TableDataInfo tableDataInfo = getDataTable(list);
        if (tableDataInfo.getTotal()>0) {
            //查询统计总计
            PageHelper.startPage(0,-1,false);
            Map<String,Object> options =  tReportChannelDayService.reportDayList(dto);
            tableDataInfo.setOption(options);
        }
        return tableDataInfo;
    }

    /**
     * 导出渠道统计列表
     */
    @PreAuthorize("@ss.hasPermi('report:channelReport:export')")
    @Log(title = "渠道统计", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ReportChannelDayDTO tReportChannelDay)
    {
        List<TReportChannelDay> list = tReportChannelDayService.selectTReportChannelDayList(tReportChannelDay);
        ExcelUtil<TReportChannelDay> util = new ExcelUtil<TReportChannelDay>(TReportChannelDay.class);
        return util.exportExcel(list, "渠道统计数据");
    }

    /**
     * 获取渠道统计详细信息
     */
    @PreAuthorize("@ss.hasPermi('report:channelReport:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tReportChannelDayService.selectTReportChannelDayById(id));
    }

    /**
     * 新增渠道统计
     */
    @PreAuthorize("@ss.hasPermi('report:channelReport:add')")
    @Log(title = "渠道统计", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TReportChannelDay tReportChannelDay)
    {
        return toAjax(tReportChannelDayService.insertTReportChannelDay(tReportChannelDay));
    }

    /**
     * 修改渠道统计
     */
    @PreAuthorize("@ss.hasPermi('report:channelReport:edit')")
    @Log(title = "渠道统计", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TReportChannelDay tReportChannelDay)
    {
        return toAjax(tReportChannelDayService.updateTReportChannelDay(tReportChannelDay));
    }

    /**
     * 删除渠道统计
     */
    @PreAuthorize("@ss.hasPermi('report:channelReport:remove')")
    @Log(title = "渠道统计", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tReportChannelDayService.deleteTReportChannelDayByIds(ids));
    }
}
