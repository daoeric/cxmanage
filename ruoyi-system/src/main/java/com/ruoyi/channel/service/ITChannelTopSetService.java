package com.ruoyi.channel.service;

import com.ruoyi.channel.domain.TChannelTopSet;
import com.ruoyi.channel.domain.TProduct;
import com.ruoyi.common.vo.ChannelSetVO;

import java.util.List;

/**
 * 通用费率配置Service接口
 * 
 * @author cx
 * @date 2023-09-15
 */
public interface ITChannelTopSetService 
{
    /**
     * 查询通用费率配置
     * 
     * @param id 通用费率配置ID
     * @return 通用费率配置
     */
    public TChannelTopSet selectTChannelTopSetById(Long id);

    /**
     * 查询通用费率配置列表
     * 
     * @param tChannelTopSet 通用费率配置
     * @return 通用费率配置集合
     */
    public List<ChannelSetVO> selectTChannelTopSetList(TChannelTopSet tChannelTopSet);

    /**
     * 新增通用费率配置
     * 
     * @param tChannelTopSet 通用费率配置
     * @return 结果
     */
    public int insertTChannelTopSet(TChannelTopSet tChannelTopSet);

    /**
     * 修改通用费率配置
     * 
     * @param tChannelTopSet 通用费率配置
     * @return 结果
     */
    public int updateTChannelTopSet(TChannelTopSet tChannelTopSet);

    /**
     * 批量删除通用费率配置
     * 
     * @param ids 需要删除的通用费率配置ID
     * @return 结果
     */
    public int deleteTChannelTopSetByIds(Long[] ids);

    /**
     * 删除通用费率配置信息
     * 
     * @param id 通用费率配置ID
     * @return 结果
     */
    public int deleteTChannelTopSetById(Long id);

    List<TProduct> getUnProduct();

    List<TChannelTopSet> list(TChannelTopSet channelTopSet);
}
