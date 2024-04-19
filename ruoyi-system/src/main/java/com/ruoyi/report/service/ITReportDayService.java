package com.ruoyi.report.service;

import com.ruoyi.common.dto.ListDto;
import com.ruoyi.common.dto.ReportDayDTO;
import com.ruoyi.customer.domain.TCustomer;
import com.ruoyi.report.domain.TReportDay;

import java.util.List;
import java.util.Map;

/**
 * 日报统计Service接口
 * 
 * @author yf
 * @date 2022-07-06
 */
public interface ITReportDayService 
{
    /**
     * 查询日报统计
     * 
     * @param id 日报统计ID
     * @return 日报统计
     */
    public TReportDay selectTReportDayById(Long id);

    /**
     * 查询日报统计列表
     * 
     * @param tReportDay 日报统计
     * @return 日报统计集合
     */
    public List<TReportDay> selectTReportDayList(ReportDayDTO tReportDay);

    /**
     * 新增日报统计
     * 
     * @param tReportDay 日报统计
     * @return 结果
     */
    public int insertTReportDay(TReportDay tReportDay);

    /**
     * 修改日报统计
     * 
     * @param tReportDay 日报统计
     * @return 结果
     */
    public int updateTReportDay(TReportDay tReportDay);

    /**
     * 批量删除日报统计
     * 
     * @param ids 需要删除的日报统计ID
     * @return 结果
     */
    public int deleteTReportDayByIds(Long[] ids);

    /**
     * 删除日报统计信息
     * 
     * @param id 日报统计ID
     * @return 结果
     */
    public int deleteTReportDayById(Long id);

    Map<String, Object> reportDayList(ReportDayDTO tReportDay);

    /**
     * 商户获取报表记录
     * @param listDto
     * @param customer
     * @return
     */
    List<TReportDay> userDayReportList(ListDto listDto, TCustomer customer);
}
