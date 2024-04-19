package com.ruoyi.channel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.channel.domain.TChannelSet;
import com.ruoyi.channel.domain.TChannelTopSet;
import com.ruoyi.common.vo.ChannelSetVO;
import com.ruoyi.common.vo.front.TChannelSetVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 通道配置Mapper接口
 * 
 * @author yf
 * @date 2022-05-22
 */
public interface TChannelSetMapper extends BaseMapper<TChannelSet>
{
    /**
     * 查询通道配置
     * 
     * @param id 通道配置ID
     * @return 通道配置
     */
    public TChannelSet selectTChannelSetById(Long id);

    /**
     * 查询通道配置列表
     * 
     * @param tChannelSet 通道配置
     * @return 通道配置集合
     */
    public List<ChannelSetVO> selectTChannelSetList(TChannelSet tChannelSet);

    /**
     * 新增通道配置
     * 
     * @param tChannelSet 通道配置
     * @return 结果
     */
    public int insertTChannelSet(TChannelSet tChannelSet);

    /**
     * 修改通道配置
     * 
     * @param tChannelSet 通道配置
     * @return 结果
     */
    public int updateTChannelSet(TChannelSet tChannelSet);

    /**
     * 删除通道配置
     * 
     * @param id 通道配置ID
     * @return 结果
     */
    public int deleteTChannelSetById(Long id);

    /**
     * 批量删除通道配置
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTChannelSetByIds(Long[] ids);

    List<TChannelSetVO> listByPlanId(Integer planId);

    int mergeChannel(@Param("topSet") TChannelTopSet topSet, @Param("customerId") Long customerId);

    int deleteTchannelSetByProductIds(@Param("array") List<String> productIdList);

    int updateByCommon(TChannelTopSet tChannelTopSet);

    List<TChannelSetVO> listRateByCustomerId(@Param("customerId") Long customerId);
}
