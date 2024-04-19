package com.ruoyi.wallet.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.wallet.domain.DfWalletType;
import com.ruoyi.wallet.vo.WalletTypeVo;

import java.util.List;

/**
 * 账户类型列Service接口
 * 
 * @author ruoyi
 * @date 2022-04-13
 */
public interface IDfWalletTypeService extends IService<DfWalletType>
{
    /**
     * 查询账户类型列
     * 
     * @param id 账户类型列ID
     * @return 账户类型列
     */
    public DfWalletType selectDfWalletTypeById(Long id);

    /**
     * 查询账户类型列列表
     * 
     * @param dfWalletType 账户类型列
     * @return 账户类型列集合
     */
    public List<DfWalletType> selectDfWalletTypeList(DfWalletType dfWalletType);

    /**
     * 查询账户类型列表
     * @return
     */
    public List<WalletTypeVo> selectTypeList();

    /**
     * 新增账户类型列
     * 
     * @param dfWalletType 账户类型列
     * @return 结果
     */
    public int insertDfWalletType(DfWalletType dfWalletType);

    /**
     * 修改账户类型列
     * 
     * @param dfWalletType 账户类型列
     * @return 结果
     */
    public int updateDfWalletType(DfWalletType dfWalletType);

    /**
     * 批量删除账户类型列
     * 
     * @param ids 需要删除的账户类型列ID
     * @return 结果
     */
    public int deleteDfWalletTypeByIds(Long[] ids);

    /**
     * 删除账户类型列信息
     * 
     * @param id 账户类型列ID
     * @return 结果
     */
    public int deleteDfWalletTypeById(Long id);
}
