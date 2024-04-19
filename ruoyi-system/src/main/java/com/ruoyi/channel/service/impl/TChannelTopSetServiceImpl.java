package com.ruoyi.channel.service.impl;

import com.ruoyi.channel.domain.TChannel;
import com.ruoyi.channel.domain.TChannelTopSet;
import com.ruoyi.channel.domain.TProduct;
import com.ruoyi.channel.mapper.TChannelTopSetMapper;
import com.ruoyi.channel.service.ITChannelService;
import com.ruoyi.channel.service.ITChannelSetService;
import com.ruoyi.channel.service.ITChannelTopSetService;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.vo.ChannelSetVO;
import com.ruoyi.customer.service.ITCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 通用费率配置Service业务层处理
 * 
 * @author cx
 * @date 2023-09-15
 */
@Service
public class TChannelTopSetServiceImpl implements ITChannelTopSetService 
{
    @Autowired
    private TChannelTopSetMapper tChannelTopSetMapper;
    @Autowired
    private ITChannelService channelService;
    @Autowired
    private ITCustomerService customerService;
    @Autowired
    private ITChannelSetService channelSetService;

    /**
     * 查询通用费率配置
     * 
     * @param id 通用费率配置ID
     * @return 通用费率配置
     */
    @Override
    public TChannelTopSet selectTChannelTopSetById(Long id)
    {
        return tChannelTopSetMapper.selectTChannelTopSetById(id);
    }

    /**
     * 查询通用费率配置列表
     * 
     * @param tChannelTopSet 通用费率配置
     * @return 通用费率配置
     */
    @Override
    public List<ChannelSetVO> selectTChannelTopSetList(TChannelTopSet tChannelTopSet)
    {
        List<ChannelSetVO> result = tChannelTopSetMapper.selectTChannelTopSetList(tChannelTopSet);
        for (ChannelSetVO channelSetVO : result) {
            String ids = channelSetVO.getChannelIds();
            if (StringUtils.isNotEmpty(ids)) {
                String[] idsArray = ids.split(",");
                StringBuffer channelNamesBuffer = new StringBuffer("");
                for (String id : idsArray) {
                    TChannel channel = channelService.getById(id);
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
        return result;
    }

    /**
     * 新增通用费率配置
     * 
     * @param tChannelTopSet 通用费率配置
     * @return 结果
     */
    @Override
    public int insertTChannelTopSet(TChannelTopSet tChannelTopSet)
    {
        //通用配置新增或修改会同步到所有商户
        return tChannelTopSetMapper.insertTChannelTopSet(tChannelTopSet);
    }

    /**
     * 修改通用费率配置
     * 
     * @param tChannelTopSet 通用费率配置
     * @return 结果
     */
    @Override
    @Transactional
    public int updateTChannelTopSet(TChannelTopSet tChannelTopSet)
    {
        int count = tChannelTopSetMapper.updateTChannelTopSet(tChannelTopSet);
        if (count >0) {
            channelSetService.updateByCommon(tChannelTopSet);

        }
        return count;
    }

    /**
     * 批量删除通用费率配置
     * 
     * @param ids 需要删除的通用费率配置ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteTChannelTopSetByIds(Long[] ids)
    {
        List<String> productIdList = tChannelTopSetMapper.getProductIdByIds(ids);
        channelSetService.deleteTchannelSetByProductIds(productIdList);
        return tChannelTopSetMapper.deleteTChannelTopSetByIds(ids);
    }

    /**
     * 删除通用费率配置信息
     * 
     * @param id 通用费率配置ID
     * @return 结果
     */
    @Override
    public int deleteTChannelTopSetById(Long id)
    {
        return tChannelTopSetMapper.deleteTChannelTopSetById(id);
    }

    @Override
    public List<TProduct> getUnProduct() {
        return tChannelTopSetMapper.getUnproduct();
    }

    @Override
    public List<TChannelTopSet> list(TChannelTopSet channelTopSet) {
        return tChannelTopSetMapper.list(channelTopSet);
    }
}
