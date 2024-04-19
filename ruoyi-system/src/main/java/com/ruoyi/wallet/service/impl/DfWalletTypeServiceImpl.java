package com.ruoyi.wallet.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.wallet.domain.DfWalletType;
import com.ruoyi.wallet.mapper.DfWalletTypeMapper;
import com.ruoyi.wallet.service.IDfWalletTypeService;
import com.ruoyi.wallet.vo.WalletTypeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 账户类型列Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-04-13
 */
@Service
public class DfWalletTypeServiceImpl extends ServiceImpl<DfWalletTypeMapper, DfWalletType> implements IDfWalletTypeService
{
    @Autowired
    private DfWalletTypeMapper dfWalletTypeMapper;

    /**
     * 查询账户类型列
     * 
     * @param id 账户类型列ID
     * @return 账户类型列
     */
    @Override
    public DfWalletType selectDfWalletTypeById(Long id)
    {
        return dfWalletTypeMapper.selectDfWalletTypeById(id);
    }

    /**
     * 查询账户类型列列表
     * 
     * @param dfWalletType 账户类型列
     * @return 账户类型列
     */
    @Override
    public List<DfWalletType> selectDfWalletTypeList(DfWalletType dfWalletType)
    {
        return dfWalletTypeMapper.selectDfWalletTypeList(dfWalletType);
    }

    /**
     * 查询账户类型列表
     * @return
     */
    @Override
    public List<WalletTypeVo> selectTypeList(){
        return dfWalletTypeMapper.selectTypeList();
    }

    /**
     * 新增账户类型列
     * 
     * @param dfWalletType 账户类型列
     * @return 结果
     */
    @Override
    public int insertDfWalletType(DfWalletType dfWalletType)
    {
        return dfWalletTypeMapper.insertDfWalletType(dfWalletType);
    }

    /**
     * 修改账户类型列
     * 
     * @param dfWalletType 账户类型列
     * @return 结果
     */
    @Override
    public int updateDfWalletType(DfWalletType dfWalletType)
    {
        return dfWalletTypeMapper.updateDfWalletType(dfWalletType);
    }

    /**
     * 批量删除账户类型列
     * 
     * @param ids 需要删除的账户类型列ID
     * @return 结果
     */
    @Override
    public int deleteDfWalletTypeByIds(Long[] ids)
    {
        return dfWalletTypeMapper.deleteDfWalletTypeByIds(ids);
    }

    /**
     * 删除账户类型列信息
     * 
     * @param id 账户类型列ID
     * @return 结果
     */
    @Override
    public int deleteDfWalletTypeById(Long id)
    {
        return dfWalletTypeMapper.deleteDfWalletTypeById(id);
    }
}
