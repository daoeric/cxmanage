package com.ruoyi.channel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.channel.domain.TSettlementPlan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 结算方案Mapper接口
 * 
 * @author ruoyi
 * @date 2022-04-23
 */
public interface TSettlementPlanMapper extends BaseMapper<TSettlementPlan>
{
    /**
     * 查询结算方案
     * 
     * @param planId 结算方案ID
     * @return 结算方案
     */
    public TSettlementPlan selectTSettlementPlanById(Long planId);

    /**
     * 查询结算方案列表
     * 
     * @param tSettlementPlan 结算方案
     * @return 结算方案集合
     */
    public List<TSettlementPlan> selectTSettlementPlanList(TSettlementPlan tSettlementPlan);

    /**
     * 新增结算方案
     * 
     * @param tSettlementPlan 结算方案
     * @return 结果
     */
    public int insertTSettlementPlan(TSettlementPlan tSettlementPlan);

    /**
     * 修改结算方案
     * 
     * @param tSettlementPlan 结算方案
     * @return 结果
     */
    public int updateTSettlementPlan(TSettlementPlan tSettlementPlan);

    /**
     * 删除结算方案
     * 
     * @param planId 结算方案ID
     * @return 结果
     */
    public int deleteTSettlementPlanById(Long planId);

    /**
     * 批量删除结算方案
     * 
     * @param planIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteTSettlementPlanByIds(Long[] planIds);

    /**
     * 根据方案名查询个数
     * @param planName
     * @return
     */
    int countByName(@Param("planName") String planName);
}
