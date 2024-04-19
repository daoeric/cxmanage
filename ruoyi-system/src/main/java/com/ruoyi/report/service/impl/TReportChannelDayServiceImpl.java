package com.ruoyi.report.service.impl;

import com.ruoyi.common.dto.ReportChannelDayDTO;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.report.domain.TReportChannelDay;
import com.ruoyi.report.mapper.TReportChannelDayMapper;
import com.ruoyi.report.service.ITReportChannelDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 渠道统计Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-10-04
 */
@Service
public class TReportChannelDayServiceImpl implements ITReportChannelDayService 
{
    @Autowired
    private TReportChannelDayMapper tReportChannelDayMapper;

    /**
     * 查询渠道统计
     * 
     * @param id 渠道统计ID
     * @return 渠道统计
     */
    @Override
    public TReportChannelDay selectTReportChannelDayById(Long id)
    {
        return tReportChannelDayMapper.selectTReportChannelDayById(id);
    }

    /**
     * 查询渠道统计列表
     * 
     * @param dto 渠道统计
     * @return 渠道统计
     */
    @Override
    public List<TReportChannelDay> selectTReportChannelDayList(ReportChannelDayDTO dto)
    {
        return tReportChannelDayMapper.selectTReportChannelDayList(dto);
    }

    /**
     * 新增渠道统计
     * 
     * @param tReportChannelDay 渠道统计
     * @return 结果
     */
    @Override
    public int insertTReportChannelDay(TReportChannelDay tReportChannelDay)
    {
        tReportChannelDay.setCreateTime(DateUtils.getNowDate());
        return tReportChannelDayMapper.insertTReportChannelDay(tReportChannelDay);
    }

    /**
     * 修改渠道统计
     * 
     * @param tReportChannelDay 渠道统计
     * @return 结果
     */
    @Override
    public int updateTReportChannelDay(TReportChannelDay tReportChannelDay)
    {
        return tReportChannelDayMapper.updateTReportChannelDay(tReportChannelDay);
    }

    /**
     * 批量删除渠道统计
     * 
     * @param ids 需要删除的渠道统计ID
     * @return 结果
     */
    @Override
    public int deleteTReportChannelDayByIds(Long[] ids)
    {
        return tReportChannelDayMapper.deleteTReportChannelDayByIds(ids);
    }

    /**
     * 删除渠道统计信息
     * 
     * @param id 渠道统计ID
     * @return 结果
     */
    @Override
    public int deleteTReportChannelDayById(Long id)
    {
        return tReportChannelDayMapper.deleteTReportChannelDayById(id);
    }

    @Override
    public Map<String, Object> reportDayList(ReportChannelDayDTO dto) {
        return tReportChannelDayMapper.reportDayList(dto);
    }
}
