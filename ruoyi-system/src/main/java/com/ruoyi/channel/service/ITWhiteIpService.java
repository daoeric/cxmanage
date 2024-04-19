package com.ruoyi.channel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.channel.domain.TWhiteIp;
import com.ruoyi.common.dto.ChannelStatusDto;

import java.util.List;

/**
 * IP白名单Service接口
 * 
 * @author yf
 * @date 2022-05-30
 */
public interface ITWhiteIpService extends IService<TWhiteIp>
{
    /**
     * 查询IP白名单
     * 
     * @param id IP白名单ID
     * @return IP白名单
     */
    public TWhiteIp selectTWhiteIpById(Integer id);

    /**
     * 查询IP白名单列表
     * 
     * @param tWhiteIp IP白名单
     * @return IP白名单集合
     */
    public List<TWhiteIp> selectTWhiteIpList(TWhiteIp tWhiteIp);

    /**
     * 新增IP白名单
     * 
     * @param tWhiteIp IP白名单
     * @return 结果
     */
    public int insertTWhiteIp(TWhiteIp tWhiteIp);

    /**
     * 修改IP白名单
     * 
     * @param tWhiteIp IP白名单
     * @return 结果
     */
    public int updateTWhiteIp(TWhiteIp tWhiteIp);

    /**
     * 批量删除IP白名单
     * 
     * @param ids 需要删除的IP白名单ID
     * @return 结果
     */
    public int deleteTWhiteIpByIds(Integer[] ids);

    /**
     * 删除IP白名单信息
     * 
     * @param id IP白名单ID
     * @return 结果
     */
    public int deleteTWhiteIpById(Integer id);


    boolean checkIp(String serviceKey, String requestIp);

    String getByCode(String serviceKey);

    /**
     * 更改状态
     * @param dto
     * @return
     */
    boolean changeStatus(ChannelStatusDto dto);
}
