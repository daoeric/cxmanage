package com.ruoyi.web.controller.report;

import com.github.pagehelper.PageHelper;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.dto.ReportDayDTO;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.report.domain.TReportDay;
import com.ruoyi.report.service.ITReportDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 日报统计Controller
 * 
 * @author yf
 * @date 2022-07-06
 */
@RestController
@RequestMapping("/report/day")
public class TReportDayController extends BaseController
{
    @Autowired
    private ITReportDayService tReportDayService;

    /**
     * 查询日报统计列表
     */
    @PreAuthorize("@ss.hasPermi('report:day:list')")
    @PostMapping("/list")
    public TableDataInfo list(@RequestBody ReportDayDTO reportDayDTO)
    {
        startPage();
        List<TReportDay> list = tReportDayService.selectTReportDayList(reportDayDTO);
        TableDataInfo tableDataInfo = getDataTable(list);
        if (tableDataInfo.getTotal()>0) {
            //查询统计总计
            PageHelper.startPage(0,-1,false);
            Map<String,Object> options =  tReportDayService.reportDayList(reportDayDTO);
            tableDataInfo.setOption(options);
        }
        //获取统计数据
        return tableDataInfo;
    }

    /**
     * 导出日报统计列表
     */
    @PreAuthorize("@ss.hasPermi('report:day:export')")
    @Log(title = "日报统计", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public AjaxResult export(@RequestBody ReportDayDTO reportDayDTO)
    {
        List<TReportDay> list = tReportDayService.selectTReportDayList(reportDayDTO);
        ExcelUtil<TReportDay> util = new ExcelUtil<TReportDay>(TReportDay.class);
        return util.exportExcel(list, "日报统计数据");
    }

    /**
     * 获取日报统计详细信息
     */
    @PreAuthorize("@ss.hasPermi('report:day:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tReportDayService.selectTReportDayById(id));
    }

    /**
     * 新增日报统计
     */
    @PreAuthorize("@ss.hasPermi('report:day:add')")
    @Log(title = "日报统计", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TReportDay tReportDay)
    {
        return toAjax(tReportDayService.insertTReportDay(tReportDay));
    }

    /**
     * 修改日报统计
     */
    @PreAuthorize("@ss.hasPermi('report:day:edit')")
    @Log(title = "日报统计", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TReportDay tReportDay)
    {
        return toAjax(tReportDayService.updateTReportDay(tReportDay));
    }

    /**
     * 删除日报统计
     */
    @PreAuthorize("@ss.hasPermi('report:day:remove')")
    @Log(title = "日报统计", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tReportDayService.deleteTReportDayByIds(ids));
    }
}
