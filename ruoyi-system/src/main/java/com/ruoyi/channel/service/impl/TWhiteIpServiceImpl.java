package com.ruoyi.channel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.channel.domain.TChannel;
import com.ruoyi.channel.domain.TWhiteIp;
import com.ruoyi.channel.mapper.TWhiteIpMapper;
import com.ruoyi.channel.service.ITChannelService;
import com.ruoyi.channel.service.ITWhiteIpService;
import com.ruoyi.common.dto.ChannelStatusDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * IP白名单Service业务层处理
 * 
 * @author yf
 * @date 2022-05-30
 */
@Service
public class TWhiteIpServiceImpl extends ServiceImpl<TWhiteIpMapper,TWhiteIp> implements ITWhiteIpService
{
    @Autowired
    private TWhiteIpMapper tWhiteIpMapper;
    @Autowired
    private ITChannelService channelService;

    /**
     * 查询IP白名单
     * 
     * @param id IP白名单ID
     * @return IP白名单
     */
    @Override
    public TWhiteIp selectTWhiteIpById(Integer id)
    {
        return tWhiteIpMapper.selectTWhiteIpById(id);
    }

    /**
     * 查询IP白名单列表
     * 
     * @param tWhiteIp IP白名单
     * @return IP白名单
     */
    @Override
    public List<TWhiteIp> selectTWhiteIpList(TWhiteIp tWhiteIp)
    {
        return tWhiteIpMapper.selectTWhiteIpList(tWhiteIp);
    }

    /**
     * 新增IP白名单
     * 
     * @param tWhiteIp IP白名单
     * @return 结果
     */
    @Override
    public int insertTWhiteIp(TWhiteIp tWhiteIp)
    {
        return tWhiteIpMapper.insertTWhiteIp(tWhiteIp);
    }

    /**
     * 修改IP白名单
     * 
     * @param tWhiteIp IP白名单
     * @return 结果
     */
    @Override
    public int updateTWhiteIp(TWhiteIp tWhiteIp)
    {
        return tWhiteIpMapper.updateTWhiteIp(tWhiteIp);
    }

    /**
     * 批量删除IP白名单
     * 
     * @param ids 需要删除的IP白名单ID
     * @return 结果
     */
    @Override
    public int deleteTWhiteIpByIds(Integer[] ids)
    {
        return tWhiteIpMapper.deleteTWhiteIpByIds(ids);
    }

    /**
     * 删除IP白名单信息
     * 
     * @param id IP白名单ID
     * @return 结果
     */
    @Override
    public int deleteTWhiteIpById(Integer id)
    {
        return tWhiteIpMapper.deleteTWhiteIpById(id);
    }

    @Override
    public boolean checkIp(String serviceKey, String requestIp) {
        boolean result = false;
        QueryWrapper<TChannel> channelQueryWrapper = new QueryWrapper<>();
        channelQueryWrapper.eq("code",serviceKey);
        channelQueryWrapper.last("limit 1");
        TChannel channel = channelService.getOne(channelQueryWrapper);
        if (channel != null) {
            String alias = channel.getAlias();
            QueryWrapper<TWhiteIp> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("alias",alias);
            TWhiteIp whiteIp = this.getOne(queryWrapper);
            if (whiteIp!=null) {
                String ipAddress = whiteIp.getIpAddress();
                if (ipAddress.contains(requestIp)) {
                    result = true;
                }
            } else { //如果没有配置默认 不需要校验白名单
                result = true;
            }
        }
        return result;
    }

    @Override
    public String getByCode(String serviceKey) {
        String result = null;
        QueryWrapper<TWhiteIp> wrapper = new QueryWrapper<>();
        wrapper.eq("code",serviceKey);
        wrapper.last("limit 1");
        TWhiteIp whiteIp = this.getOne(wrapper);
        if (whiteIp != null) {
            result = whiteIp.getIpAddress();
        }
        return result;
    }

    @Override
    @Transactional
    public boolean changeStatus(ChannelStatusDto dto) {
        UpdateWrapper<TWhiteIp> wrapper = new UpdateWrapper<>();
        wrapper.eq("alias",dto.getAlias());
        wrapper.set("status",dto.getStatus());
        return this.update(wrapper);
    }

}
