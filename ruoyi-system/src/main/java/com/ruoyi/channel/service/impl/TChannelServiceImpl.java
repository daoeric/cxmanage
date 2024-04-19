package com.ruoyi.channel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.channel.domain.TChannel;
import com.ruoyi.channel.domain.TChannelSet;
import com.ruoyi.channel.domain.TWhiteIp;
import com.ruoyi.channel.mapper.TChannelMapper;
import com.ruoyi.channel.service.ITChannelService;
import com.ruoyi.channel.service.ITChannelSetService;
import com.ruoyi.channel.service.ITWhiteIpService;
import com.ruoyi.common.dto.TChannelDto;
import com.ruoyi.common.enums.StatusEnum;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.common.vo.payment.BalanceResult;
import com.ruoyi.payment.IPaymentService;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * 通道信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-04-23
 */
@Service
public class TChannelServiceImpl extends ServiceImpl<TChannelMapper,TChannel> implements ITChannelService
{
    @Autowired
    private TChannelMapper tChannelMapper;

    @Autowired
    private ITChannelSetService channelSetService;

    @Autowired
    private ITWhiteIpService whiteIpService;

    /**
     * 查询通道信息
     * 
     * @param channelId 通道信息ID
     * @return 通道信息
     */
    @Override
    public TChannel selectTChannelById(Integer channelId)
    {
        return tChannelMapper.selectTChannelById(channelId);
    }

    /**
     * 查询通道信息列表
     * 
     * @param tChannel 通道信息
     * @return 通道信息
     */
    @Override
    public List<TChannel> selectTChannelList(TChannel tChannel)
    {
        return tChannelMapper.selectTChannelList(tChannel);
    }

    /**
     * 新增通道信息
     * 
     * @param tChannel 通道信息
     * @return 结果
     */
    @Override
    public int insertTChannel(TChannel tChannel)
    {
        tChannel.setCreateTime(DateUtils.getNowDate());
        return tChannelMapper.insertTChannel(tChannel);
    }

    /**
     * 修改通道信息
     * 
     * @param tChannel 通道信息
     * @return 结果
     */
    @Override
    public int updateTChannel(TChannel tChannel)
    {
        tChannel.setUpdateTime(DateUtils.getNowDate());
        return tChannelMapper.updateTChannel(tChannel);
    }

    /**
     * 批量删除通道信息
     * 
     * @param channelIds 需要删除的通道信息ID
     * @return 结果
     */
    @Override
    public int deleteTChannelByIds(Integer[] channelIds)
    {
        return tChannelMapper.deleteTChannelByIds(channelIds);
    }

    /**
     * 删除通道信息信息
     * 
     * @param channelId 通道信息ID
     * @return 结果
     */
    @Override
    public int deleteTChannelById(Integer channelId)
    {
        return tChannelMapper.deleteTChannelById(channelId);
    }

    @Override
    public TChannelDto getEnableChanel(Long customerId, String productId) {
        //获取
        QueryWrapper<TChannelSet> channelSetQueryWrapper = new QueryWrapper<>();
        channelSetQueryWrapper.eq("customer_id",customerId);
        channelSetQueryWrapper.eq("product_id","A999".equals(productId)?"A888":productId);
        TChannelSet channelSet = channelSetService.getOne(channelSetQueryWrapper);
        if (channelSet == null) {
            return null;
        }

        Integer mode = channelSet.getMode();
        TChannelDto dto = new TChannelDto();
        if(!"A999".equals(productId)){//入金不需要走通道
            String channelIds = channelSet.getChannelIds();
            String[] channelArray = channelIds.split(",");
            Integer channelId = null;
            if (mode == 1) {
                channelId = Integer.valueOf(channelArray[0]);
            } else {
                int length = channelArray.length;
                //随机生成下标
                Random random = new Random();
                int index = random.nextInt(length);
                channelId = Integer.valueOf(channelArray[index]);
            }
            TChannel channel = tChannelMapper.selectById(channelId);
            BeanUtils.copyProperties(channel,dto);
            dto.setNotifyUrl(channel.getWithdrawalNotify());
        } else {
            dto.setStatus(StatusEnum.OK.getCode());
        }
        dto.setMchRate(channelSet.getRate());
        return  dto;
    }

    @Override
    public List<String> listAlias() {
        QueryWrapper<TChannel> wrapper = new QueryWrapper<>();
        wrapper.select("alias");
        wrapper.groupBy("alias");
        List<Object> objectList = this.listObjs(wrapper);
        List<String>  result = Lists.newArrayList();
        for (Object o : objectList) {
            result.add(o.toString());
        }
        return result;
    }

    @Override
    public List<TChannel> listByProductId(String productId) {
        QueryWrapper<TChannel> wrapper = new QueryWrapper<>();
        wrapper.eq("product_id",productId);
//        wrapper.eq("status",0);
        return this.list(wrapper);
    }

    @Override
    public TChannel getOne(String productId, String code) {
        QueryWrapper<TChannel> wrapper = new QueryWrapper<>();
        wrapper.eq("product_id",productId);
        wrapper.eq("code",code);
        return getOne(wrapper);
    }

    @Override
    public String getChannelPriKey(String code) {
        QueryWrapper<TChannel> wrapper = new QueryWrapper<>();
        wrapper.eq("code",code);
        List<TChannel> channelList = this.list(wrapper);
        if (channelList != null && !channelList.isEmpty()) {
            return channelList.get(0).getPriKey();
        }
        return null;
    }

    @Override
    public TChannel getWithdrawalChannel(String code) {
        QueryWrapper<TChannel>  wrapper = new QueryWrapper<>();
        wrapper.eq("code",code);
        wrapper.eq("product_id","A888");
        wrapper.last("limit 1");
        return this.getOne(wrapper);
    }

    @Override
    public List<BalanceResult> listBalance() {
        //获取有查询余额的通道
        QueryWrapper<TWhiteIp> wrapper = new QueryWrapper<>();
        wrapper.eq("status",0);
        List<BalanceResult> resultList = Lists.newArrayList();
        List<TWhiteIp> channelList = whiteIpService.list(wrapper);
        final CountDownLatch latch = new CountDownLatch(channelList.size());
        for (TWhiteIp channel : channelList) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String code = channel.getCode();
                        IPaymentService paymentService = SpringUtils.getBean(code);
                        BalanceResult balanceResult = paymentService.getBalance();
                        if (balanceResult != null) {
                            resultList.add(balanceResult);
                        }
                    } catch (Throwable e) {
                        // whatever
                        log.error("查询余额异常：{}",e);
                    } finally {
                        // 很关键, 无论上面程序是否异常必须执行countDown,否则await无法释放
                        latch.countDown();
                    }
                }
            }).start();
        }
        try {
            // 10个线程countDown()都执行之后才会释放当前线程,程序才能继续往后执行
            latch.await();
        } catch (InterruptedException e) {
            // whatever
        }
        return resultList;
    }
}
