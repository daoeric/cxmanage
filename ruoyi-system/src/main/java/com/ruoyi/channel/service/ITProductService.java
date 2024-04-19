package com.ruoyi.channel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.channel.domain.TProduct;

import java.util.List;

/**
 * 产品Service接口
 * 
 * @author yf
 * @date 2022-05-21
 */
public interface ITProductService extends IService<TProduct>
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
     * 批量删除产品
     * 
     * @param productIds 需要删除的产品ID
     * @return 结果
     */
    public int deleteTProductByIds(String[] productIds);

    /**
     * 删除产品信息
     * 
     * @param productId 产品ID
     * @return 结果
     */
    public int deleteTProductById(String productId);

    /**
     * 获取所有产品通道
     * @return
     */
    List<TProduct> selectAll();

    /**
     * 获取存款类型的产品
     * @return
     */
    List<TProduct> listDeposit();
}
