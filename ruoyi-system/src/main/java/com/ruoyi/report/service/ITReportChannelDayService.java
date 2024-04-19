package com.ruoyi.report.service;

import com.ruoyi.common.dto.ReportChannelDayDTO;
import com.ruoyi.report.domain.TReportChannelDay;

import java.util.List;
import java.util.Map;

/**
 * 渠道统计Service接口
 * 
 * @author ruoyi
 * @date 2023-10-04
 */
public interface ITReportChannelDayService 
{
    /**
     * 查询渠道统计
     * 
     * @param id 渠道统计ID
     * @return 渠道统计
     */
    public TReportChannelDay selectTReportChannelDayById(Long id);

    /**
     * 查询渠道统计列表
     * 
     * @param dto 渠道统计
     * @return 渠道统计集合
     */
    public List<TReportChannelDay> selectTReportChannelDayList(ReportChannelDayDTO dto);

    /**
     * 新增渠道统计
     * 
     * @param tReportChannelDay 渠道统计
     * @return 结果
     */
    public int insertTReportChannelDay(TReportChannelDay tReportChannelDay);

    /**
     * 修改渠道统计
     * 
     * @param tReportChannelDay 渠道统计
     * @return 结果
     */
    public int updateTReportChannelDay(TReportChannelDay tReportChannelDay);

    /**
     * 批量删除渠道统计
     * 
     * @param ids 需要删除的渠道统计ID
     * @return 结果
     */
    public int deleteTReportChannelDayByIds(Long[] ids);

    /**
     * 删除渠道统计信息
     * 
     * @param id 渠道统计ID
     * @return 结果
     */
    public int deleteTReportChannelDayById(Long id);

    Map<String, Object> reportDayList(ReportChannelDayDTO dto);
}
