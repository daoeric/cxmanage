package com.ruoyi.web.controller.channel;

import com.ruoyi.channel.domain.TSettlementPlan;
import com.ruoyi.channel.service.ITSettlementPlanService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 结算方案Controller
 * 
 * @author ruoyi
 * @date 2022-04-23
 */
@RestController
@RequestMapping("/channel/plan")
public class TSettlementPlanController extends BaseController
{
    @Autowired
    private ITSettlementPlanService tSettlementPlanService;

    /**
     * 查询结算方案列表
     */
    @PreAuthorize("@ss.hasPermi('channel:plan:list')")
    @GetMapping("/list")
    public TableDataInfo list(TSettlementPlan tSettlementPlan)
    {
        startPage();
        List<TSettlementPlan> list = tSettlementPlanService.selectTSettlementPlanList(tSettlementPlan);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('channel:plan:list')")
    @GetMapping("/list/all")
    public AjaxResult listAll(TSettlementPlan tSettlementPlan)
    {
        List<TSettlementPlan> list = tSettlementPlanService.selectTSettlementPlanList(tSettlementPlan);
        return AjaxResult.success(list);
    }

    /**
     * 导出结算方案列表
     */
    @PreAuthorize("@ss.hasPermi('channel:plan:export')")
    @Log(title = "结算方案", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TSettlementPlan tSettlementPlan)
    {
        List<TSettlementPlan> list = tSettlementPlanService.selectTSettlementPlanList(tSettlementPlan);
        ExcelUtil<TSettlementPlan> util = new ExcelUtil<TSettlementPlan>(TSettlementPlan.class);
        return util.exportExcel(list, "结算方案数据");
    }

    /**
     * 获取结算方案详细信息
     */
    @PreAuthorize("@ss.hasPermi('channel:plan:query')")
    @GetMapping(value = "/{planId}")
    public AjaxResult getInfo(@PathVariable("planId") Long planId)
    {
        return AjaxResult.success(tSettlementPlanService.selectTSettlementPlanById(planId));
    }

    /**
     * 新增结算方案
     */
    @PreAuthorize("@ss.hasPermi('channel:plan:add')")
    @Log(title = "结算方案", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TSettlementPlan tSettlementPlan)
    {
        return toAjax(tSettlementPlanService.insertTSettlementPlan(tSettlementPlan));
    }

    /**
     * 修改结算方案
     */
    @PreAuthorize("@ss.hasPermi('channel:plan:edit')")
    @Log(title = "结算方案", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TSettlementPlan tSettlementPlan)
    {
        return toAjax(tSettlementPlanService.updateTSettlementPlan(tSettlementPlan));
    }

    /**
     * 删除结算方案
     */
    @PreAuthorize("@ss.hasPermi('channel:plan:remove')")
    @Log(title = "结算方案", businessType = BusinessType.DELETE)
	@DeleteMapping("/{planIds}")
    public AjaxResult remove(@PathVariable Long[] planIds)
    {
        return toAjax(tSettlementPlanService.deleteTSettlementPlanByIds(planIds));
    }
}
