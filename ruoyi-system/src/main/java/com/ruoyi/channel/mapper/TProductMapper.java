package com.ruoyi.channel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.channel.domain.TProduct;

import java.util.List;

/**
 * 产品Mapper接口
 * 
 * @author yf
 * @date 2022-05-21
 */
public interface TProductMapper extends BaseMapper<TProduct>
{
    /**
     * 查询产品
     * 
     * @param productId 产品ID
     * @return 产品
     */
    public TProduct selectTProductById(String productId);

    /**
     * 查询产品列表
     * 
     * @param tProduct 产品
     * @return 产品集合
     */
    public List<TProduct> selectTProductList(TProduct tProduct);

    /**
     * 新增产品
     * 
     * @param tProduct 产品
     * @return 结果
     */
    public int insertTProduct(TProduct tProduct);

    /**
     * 修改产品
     * 
     * @param tProduct 产品
     * @return 结果
     */
    public int updateTProduct(TProduct tProduct);

    /**
     * 删除产品
     * 
     * @param productId 产品ID
     * @return 结果
     */
    public int deleteTProductById(String productId);

    /**
     * 批量删除产品
     * 
     * @param productIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteTProductByIds(String[] productIds);
}
