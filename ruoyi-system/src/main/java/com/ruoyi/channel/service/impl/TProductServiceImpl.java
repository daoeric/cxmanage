package com.ruoyi.channel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.channel.domain.TProduct;
import com.ruoyi.channel.mapper.TProductMapper;
import com.ruoyi.channel.service.ITProductService;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 产品Service业务层处理
 * 
 * @author yf
 * @date 2022-05-21
 */
@Service
public class TProductServiceImpl extends ServiceImpl<TProductMapper,TProduct> implements ITProductService
{
    @Autowired
    private TProductMapper tProductMapper;

    /**
     * 查询产品
     * 
     * @param productId 产品ID
     * @return 产品
     */
    @Override
    public TProduct selectTProductById(String productId)
    {
        return tProductMapper.selectTProductById(productId);
    }

    /**
     * 查询产品列表
     * 
     * @param tProduct 产品
     * @return 产品
     */
    @Override
    public List<TProduct> selectTProductList(TProduct tProduct)
    {
        return tProductMapper.selectTProductList(tProduct);
    }

    /**
     * 新增产品
     * 
     * @param tProduct 产品
     * @return 结果
     */
    @Override
    public int insertTProduct(TProduct tProduct)
    {
        tProduct.setCreateTime(DateUtils.getNowDate());
        //查看产品编号是否重复
        boolean check = checkProductIdExist(tProduct.getProductId());
        if (check) {
            throw new CustomException("产品编码重复");
        }
        return tProductMapper.insertTProduct(tProduct);
    }

    private boolean checkProductIdExist(String productId) {
        QueryWrapper<TProduct> wrapper = new QueryWrapper<>();
        wrapper.eq("product_id",productId);
        return this.count(wrapper)>0;
    }

    /**
     * 修改产品
     * 
     * @param tProduct 产品
     * @return 结果
     */
    @Override
    public int updateTProduct(TProduct tProduct)
    {
        tProduct.setUpdateTime(DateUtils.getNowDate());
        return tProductMapper.updateTProduct(tProduct);
    }

    /**
     * 批量删除产品
     * 
     * @param productIds 需要删除的产品ID
     * @return 结果
     */
    @Override
    public int deleteTProductByIds(String[] productIds)
    {
        return tProductMapper.deleteTProductByIds(productIds);
    }

    /**
     * 删除产品信息
     * 
     * @param productId 产品ID
     * @return 结果
     */
    @Override
    public int deleteTProductById(String productId)
    {
        return tProductMapper.deleteTProductById(productId);
    }

    @Override
    public List<TProduct> selectAll() {
        QueryWrapper<TProduct>  wrapper = new QueryWrapper<>();

        return null;
    }

    @Override
    public List<TProduct> listDeposit() {
        QueryWrapper<TProduct> wrapper = new QueryWrapper<>();
        wrapper.eq("product_type",0);
        wrapper.orderByAsc("product_id");
        return this.list(wrapper);
    }
}
