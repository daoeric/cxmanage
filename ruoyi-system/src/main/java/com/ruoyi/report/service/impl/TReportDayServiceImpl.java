package com.ruoyi.report.service.impl;

import com.ruoyi.common.dto.ListDto;
import com.ruoyi.common.dto.ReportDayDTO;
import com.ruoyi.customer.domain.TCustomer;
import com.ruoyi.report.domain.TReportDay;
import com.ruoyi.report.mapper.TReportDayMapper;
import com.ruoyi.report.service.ITReportDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 日报统计Service业务层处理
 * 
 * @author yf
 * @date 2022-07-06
 */
@Service
public class TReportDayServiceImpl implements ITReportDayService 
{
    @Autowired
    private TReportDayMapper tReportDayMapper;

    /**
     * 查询日报统计
     * 
     * @param id 日报统计ID
     * @return 日报统计
     */
    @Override
    public TReportDay selectTReportDayById(Long id)
    {
        return tReportDayMapper.selectTReportDayById(id);
    }

    /**
     * 查询日报统计列表
     * 
     * @param tReportDay 日报统计
     * @return 日报统计
     */
    @Override
    public List<TReportDay> selectTReportDayList(ReportDayDTO tReportDay)
    {
        return tReportDayMapper.selectTReportDayList(tReportDay);
    }

    /**
     * 新增日报统计
     * 
     * @param tReportDay 日报统计
     * @return 结果
     */
    @Override
    public int insertTReportDay(TReportDay tReportDay)
    {
        return tReportDayMapper.insertTReportDay(tReportDay);
    }

    /**
     * 修改日报统计
     * 
     * @param tReportDay 日报统计
     * @return 结果
     */
    @Override
    public int updateTReportDay(TReportDay tReportDay)
    {
        return tReportDayMapper.updateTReportDay(tReportDay);
    }

    /**
     * 批量删除日报统计
     * 
     * @param ids 需要删除的日报统计ID
     * @return 结果
     */
    @Override
    public int deleteTReportDayByIds(Long[] ids)
    {
        return tReportDayMapper.deleteTReportDayByIds(ids);
    }

    /**
     * 删除日报统计信息
     * 
     * @param id 日报统计ID
     * @return 结果
     */
    @Override
    public int deleteTReportDayById(Long id)
    {
        return tReportDayMapper.deleteTReportDayById(id);
    }

    @Override
    public Map<String, Object> reportDayList(ReportDayDTO tReportDay) {
        return tReportDayMapper.reportDayList(tReportDay);
    }

    @Override
    public List<TReportDay> userDayReportList(ListDto listDto, TCustomer customer) {
        return null;
    }
}
