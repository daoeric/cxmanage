package com.ruoyi.channel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.channel.domain.TChannelSet;
import com.ruoyi.channel.domain.TChannelTopSet;
import com.ruoyi.channel.domain.TProduct;
import com.ruoyi.common.vo.ChannelSetVO;
import com.ruoyi.common.vo.front.TChannelSetVO;

import java.util.List;

/**
 * 通道配置Service接口
 * 
 * @author yf
 * @date 2022-05-22
 */
public interface ITChannelSetService extends IService<TChannelSet>
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
     * 批量删除通道配置
     * 
     * @param ids 需要删除的通道配置ID
     * @return 结果
     */
    public int deleteTChannelSetByIds(Long[] ids);

    /**
     * 删除通道配置信息
     * 
     * @param id 通道配置ID
     * @return 结果
     */
    public int deleteTChannelSetById(Long id);

    /**
     * 获取结算计划未绑定的产品
     * @param planId
     * @return
     */
    List<TProduct> getUnProduct(Integer planId);

    /**
     * 根据planId 获取通道配置
     * @param planId
     * @return
     */
    List<TChannelSetVO> listByPlanId(Integer planId);

    /**
     * merge处理通用配置的修改
     *
     * @param topSet
     * @param customerId
     * @return
     */
    boolean mergeChannel(TChannelTopSet topSet, Long customerId);

    int deleteTchannelSetByProductIds(List<String> productIdList);

    /**
     * 同步通用配置修改
     * @param tChannelTopSet
     * @return
     */
    int updateByCommon(TChannelTopSet tChannelTopSet);

    TChannelSet getOne(Long customerId, String productId);

    List<TChannelSetVO> listRateByCustomerId(Long id);
}
