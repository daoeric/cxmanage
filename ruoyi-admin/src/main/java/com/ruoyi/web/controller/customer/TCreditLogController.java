package com.ruoyi.web.controller.customer;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.dto.admin.CreditLogSearchDto;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.customer.domain.TCreditLog;
import com.ruoyi.customer.service.ITCreditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 额度变更Controller
 * 
 * @author ruoyi
 * @date 2022-04-17
 */
@RestController
@RequestMapping("/customer/creditLog")
public class TCreditLogController extends BaseController
{
    @Autowired
    private ITCreditLogService tCreditLogService;

    /**
     * 查询额度变更列表
     */
    @PreAuthorize("@ss.hasPermi('customer:creditLog:list')")
    @PostMapping("/list")
    public TableDataInfo list(@RequestBody CreditLogSearchDto creditLogSearchDto)
    {
        startPage();
        List<TCreditLog> list = tCreditLogService.selectTCreditLogList(creditLogSearchDto);
        return getDataTable(list);
    }

    /**
     * 导出额度变更列表
     */
    @PreAuthorize("@ss.hasPermi('customer:creditLog:export')")
    @Log(title = "额度变更", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public AjaxResult export(@RequestBody CreditLogSearchDto creditLogSearchDto)
    {
        List<TCreditLog> list = tCreditLogService.selectTCreditLogList(creditLogSearchDto);
        ExcelUtil<TCreditLog> util = new ExcelUtil<TCreditLog>(TCreditLog.class);
        return util.exportExcel(list, "额度变更数据");
    }

    /**
     * 获取额度变更详细信息
     */
    @PreAuthorize("@ss.hasPermi('customer:creditLog:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(tCreditLogService.selectTCreditLogById(id));
    }

    /**
     * 新增额度变更
     */
    @PreAuthorize("@ss.hasPermi('customer:creditLog:add')")
    @Log(title = "额度变更", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TCreditLog tCreditLog)
    {
        return toAjax(tCreditLogService.insertTCreditLog(tCreditLog));
    }

    /**
     * 修改额度变更
     */
    @PreAuthorize("@ss.hasPermi('customer:creditLog:edit')")
    @Log(title = "额度变更", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TCreditLog tCreditLog)
    {
        return toAjax(tCreditLogService.updateTCreditLog(tCreditLog));
    }

    /**
     * 删除额度变更
     */
    @PreAuthorize("@ss.hasPermi('customer:creditLog:remove')")
    @Log(title = "额度变更", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(tCreditLogService.deleteTCreditLogByIds(ids));
    }
}
