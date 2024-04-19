package com.ruoyi.channel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.channel.domain.TChannel;
import com.ruoyi.channel.domain.TChannelSet;
import com.ruoyi.channel.domain.TChannelTopSet;
import com.ruoyi.channel.domain.TProduct;
import com.ruoyi.channel.mapper.TChannelSetMapper;
import com.ruoyi.channel.service.ITChannelService;
import com.ruoyi.channel.service.ITChannelSetService;
import com.ruoyi.channel.service.ITProductService;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.vo.ChannelSetVO;
import com.ruoyi.common.vo.front.TChannelSetVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 通道配置Service业务层处理
 * 
 * @author yf
 * @date 2022-05-22
 */
@Service
public class TChannelSetServiceImpl extends ServiceImpl<TChannelSetMapper,TChannelSet> implements ITChannelSetService
{
    @Autowired
    private TChannelSetMapper tChannelSetMapper;

    @Autowired
    private ITChannelService channelService;

    @Autowired
    private ITProductService productService;

    /**
     * 查询通道配置
     * 
     * @param id 通道配置ID
     * @return 通道配置
     */
    @Override
    public TChannelSet selectTChannelSetById(Long id)
    {
        return tChannelSetMapper.selectTChannelSetById(id);
    }

    /**
     * 查询通道配置列表
     * 
     * @param tChannelSet 通道配置
     * @return 通道配置
     */
    @Override
    public List<ChannelSetVO> selectTChannelSetList(TChannelSet tChannelSet)
    {
        List<ChannelSetVO> channelSetVOList = tChannelSetMapper.selectTChannelSetList(tChannelSet);
        for (ChannelSetVO channelSetVO : channelSetVOList) {
            String ids = channelSetVO.getChannelIds();
            if (StringUtils.isNotEmpty(ids)) {
                String[] idsArray = ids.split(",");
                StringBuffer channelNamesBuffer = new StringBuffer("");
                for (String id : idsArray) {
                    TChannel  channel = channelService.getById(id);
                    if (channel != null) {
                        channelNamesBuffer.append(channel.getName());
                        channelNamesBuffer.append(",");
                    }
                }
                String lastStr = channelNamesBuffer.toString();
                if (lastStr.endsWith(",")) {
                    lastStr = lastStr.substring(0,lastStr.length()-1);
                }
                channelSetVO.setChannelName(lastStr);
            }
        }
        return channelSetVOList;
    }

    /**
     * 新增通道配置
     * 
     * @param tChannelSet 通道配置
     * @return 结果
     */
    @Override
    @Transactional
    public int insertTChannelSet(TChannelSet tChannelSet)
    {
        return tChannelSetMapper.insertTChannelSet(tChannelSet);
    }

    /**
     * 修改通道配置
     * 
     * @param tChannelSet 通道配置
     * @return 结果
     */
    @Override
    public int updateTChannelSet(TChannelSet tChannelSet)
    {
        return tChannelSetMapper.updateTChannelSet(tChannelSet);
    }

    /**
     * 批量删除通道配置
     * 
     * @param ids 需要删除的通道配置ID
     * @return 结果
     */
    @Override
    public int deleteTChannelSetByIds(Long[] ids)
    {
        return tChannelSetMapper.deleteTChannelSetByIds(ids);
    }

    /**
     * 删除通道配置信息
     * 
     * @param id 通道配置ID
     * @return 结果
     */
    @Override
    public int deleteTChannelSetById(Long id)
    {
        return tChannelSetMapper.deleteTChannelSetById(id);
    }

    @Override
    public List<TProduct> getUnProduct(Integer planId) {
        QueryWrapper<TProduct> wrapper = new QueryWrapper<>();
        wrapper.notInSql("product_id","(select product_id from t_channel_set where plan_id = "+planId+")");
        return productService.list(wrapper);
    }

    @Override
    public List<TChannelSetVO> listByPlanId(Integer planId) {
        QueryWrapper<TChannelSet> wrapper = new QueryWrapper<>();
        wrapper.eq("plan_id",planId);
        wrapper.orderByAsc("product_id");
        List<TChannelSetVO> channelSetVOList = tChannelSetMapper.listByPlanId(planId);
        return channelSetVOList;
    }

    @Override
    public boolean mergeChannel(TChannelTopSet topSet, Long customerId) {
        return tChannelSetMapper.mergeChannel(topSet,customerId) > 0;
    }

    @Override
    public int deleteTchannelSetByProductIds(List<String> productIdList) {
        return tChannelSetMapper.deleteTchannelSetByProductIds(productIdList);
    }

    @Override
    public int updateByCommon(TChannelTopSet tChannelTopSet) {
        return tChannelSetMapper.updateByCommon(tChannelTopSet);
    }

    @Override
    public TChannelSet getOne(Long customerId, String productId) {
        QueryWrapper<TChannelSet> channelSetQueryWrapper = new QueryWrapper<>();
        channelSetQueryWrapper.eq("customer_id",customerId);
        channelSetQueryWrapper.eq("product_id",productId);
        return this.getOne(channelSetQueryWrapper);
    }

    @Override
    public List<TChannelSetVO> listRateByCustomerId(Long customerId) {
        return tChannelSetMapper.listRateByCustomerId(customerId);
    }


}
