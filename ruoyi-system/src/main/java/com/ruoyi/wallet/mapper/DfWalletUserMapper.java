package com.ruoyi.wallet.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.wallet.domain.DfWalletUser;
import com.ruoyi.wallet.vo.WalletUserVo;

import java.util.List;

/**
 * 钱包账户列Mapper接口
 * 
 * @author ruoyi
 * @date 2022-04-13
 */
public interface DfWalletUserMapper extends BaseMapper<DfWalletUser>
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
     * 删除钱包账户列
     * 
     * @param id 钱包账户列ID
     * @return 结果
     */
    public int deleteDfWalletUserById(Long id);

    /**
     * 批量删除钱包账户列
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDfWalletUserByIds(Long[] ids);

    /**
     * 查询客户钱包列表
     * @param wallet
     * @return
     */
    List<WalletUserVo> selectWalletUserVoList(DfWalletUser wallet);
}
