package com.ruoyi.channel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.channel.domain.TChannel;

import java.util.List;

/**
 * 通道信息Mapper接口
 * 
 * @author ruoyi
 * @date 2022-04-23
 */
public interface TChannelMapper extends BaseMapper<TChannel>
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
     * 删除通道信息
     * 
     * @param channelId 通道信息ID
     * @return 结果
     */
    public int deleteTChannelById(Integer channelId);

    /**
     * 批量删除通道信息
     * 
     * @param channelIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteTChannelByIds(Integer[] channelIds);
}
