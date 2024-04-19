package com.ruoyi.wallet.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.wallet.domain.DfWalletUser;
import com.ruoyi.wallet.vo.WalletUserVo;

import java.util.List;

/**
 * 钱包账户列Service接口
 * 
 * @author ruoyi
 * @date 2022-04-13
 */
public interface IDfWalletUserService extends IService<DfWalletUser>
{
    /**
     * 查询钱包账户列
     * 
     * @param id 钱包账户列ID
     * @return 钱包账户列
     */
    public DfWalletUser selectDfWalletUserById(Long id);

    /**
     * 查询钱包账户列列表
     * 
     * @param dfWalletUser 钱包账户列
     * @return 钱包账户列集合
     */
    public List<DfWalletUser> selectDfWalletUserList(DfWalletUser dfWalletUser);

    /**
     * 新增钱包账户列
     * 
     * @param dfWalletUser 钱包账户列
     * @return 结果
     */
    public int insertDfWalletUser(DfWalletUser dfWalletUser);

    /**
     * 修改钱包账户列
     * 
     * @param dfWalletUser 钱包账户列
     * @return 结果
     */
    public int updateDfWalletUser(DfWalletUser dfWalletUser);

    /**
     * 批量删除钱包账户列
     * 
     * @param ids 需要删除的钱包账户列ID
     * @return 结果
     */
    public int deleteDfWalletUserByIds(Long[] ids);

    /**
     * 删除钱包账户列信息
     * 
     * @param id 钱包账户列ID
     * @return 结果
     */
    public int deleteDfWalletUserById(Long id);

    /**
     * 查询客户钱包列表
     * @param wallet
     * @return
     */
    public List<WalletUserVo> selectWalletUserVoList(DfWalletUser wallet);
}
