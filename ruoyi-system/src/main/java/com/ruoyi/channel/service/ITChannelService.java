package com.ruoyi.channel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.channel.domain.TChannel;
import com.ruoyi.common.dto.TChannelDto;
import com.ruoyi.common.vo.payment.BalanceResult;

import java.util.List;

/**
 * 通道信息Service接口
 * 
 * @author ruoyi
 * @date 2022-04-23
 */
public interface ITChannelService extends IService<TChannel>
{
    /**
     * 查询通道信息
     * 
     * @param channelId 通道信息ID
     * @return 通道信息
     */
    public TChannel selectTChannelById(Integer channelId);

    /**
     * 查询通道信息列表
     * 
     * @param tChannel 通道信息
     * @return 通道信息集合
     */
    public List<TChannel> selectTChannelList(TChannel tChannel);

    /**
     * 新增通道信息
     * 
     * @param tChannel 通道信息
     * @return 结果
     */
    public int insertTChannel(TChannel tChannel);

    /**
     * 修改通道信息
     * 
     * @param tChannel 通道信息
     * @return 结果
     */
    public int updateTChannel(TChannel tChannel);

    /**
     * 批量删除通道信息
     * 
     * @param channelIds 需要删除的通道信息ID
     * @return 结果
     */
    public int deleteTChannelByIds(Integer[] channelIds);

    /**
     * 删除通道信息信息
     * 
     * @param channelId 通道信息ID
     * @return 结果
     */
    public int deleteTChannelById(Integer channelId);

    /**
     * 获取可用的渠道 ,如果是轮询则随机一个
     * @param customerId
     * @param productId
     * @return
     */
    TChannelDto getEnableChanel(Long customerId, String productId);

    /**
     * 查询通道上游名称列表
     * @return
     */
    List<String> listAlias();

    /**
     * 根据产品ID获取通道list
     * @param productId
     * @return
     */
    List<TChannel> listByProductId(String productId);

    TChannel getOne(String productId, String heyi);

    String getChannelPriKey(String heyi);

    TChannel getWithdrawalChannel(String qilin);

    /**
     * 获取上游通道的余额
     * @return
     */
    List<BalanceResult> listBalance();
}
