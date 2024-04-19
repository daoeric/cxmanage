package com.ruoyi.channel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.channel.domain.TWhiteIp;

import java.util.List;

/**
 * IP白名单Mapper接口
 * 
 * @author yf
 * @date 2022-05-30
 */
public interface TWhiteIpMapper extends BaseMapper<TWhiteIp>
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
     * 删除IP白名单
     * 
     * @param id IP白名单ID
     * @return 结果
     */
    public int deleteTWhiteIpById(Integer id);

    /**
     * 批量删除IP白名单
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTWhiteIpByIds(Integer[] ids);
}
