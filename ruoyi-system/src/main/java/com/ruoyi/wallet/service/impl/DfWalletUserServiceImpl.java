package com.ruoyi.wallet.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.wallet.domain.DfWalletUser;
import com.ruoyi.wallet.mapper.DfWalletUserMapper;
import com.ruoyi.wallet.service.IDfWalletUserService;
import com.ruoyi.wallet.vo.WalletUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 钱包账户列Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-04-13
 */
@Service
public class DfWalletUserServiceImpl extends ServiceImpl<DfWalletUserMapper, DfWalletUser> implements IDfWalletUserService
{
    @Autowired
    private DfWalletUserMapper dfWalletUserMapper;

    /**
     * 查询钱包账户列
     * 
     * @param id 钱包账户列ID
     * @return 钱包账户列
     */
    @Override
    public DfWalletUser selectDfWalletUserById(Long id)
    {
        return dfWalletUserMapper.selectDfWalletUserById(id);
    }

    /**
     * 查询钱包账户列列表
     * 
     * @param dfWalletUser 钱包账户列
     * @return 钱包账户列
     */
    @Override
    public List<DfWalletUser> selectDfWalletUserList(DfWalletUser dfWalletUser)
    {
        return dfWalletUserMapper.selectDfWalletUserList(dfWalletUser);
    }

    /**
     * 新增钱包账户列
     * 
     * @param dfWalletUser 钱包账户列
     * @return 结果
     */
    @Override
    public int insertDfWalletUser(DfWalletUser dfWalletUser)
    {
        dfWalletUser.setGmtCreate(DateUtils.getNowDate().getTime());
        return dfWalletUserMapper.insertDfWalletUser(dfWalletUser);
    }

    /**
     * 修改钱包账户列
     * 
     * @param dfWalletUser 钱包账户列
     * @return 结果
     */
    @Override
    public int updateDfWalletUser(DfWalletUser dfWalletUser)
    {
        return dfWalletUserMapper.updateDfWalletUser(dfWalletUser);
    }

    /**
     * 批量删除钱包账户列
     * 
     * @param ids 需要删除的钱包账户列ID
     * @return 结果
     */
    @Override
    public int deleteDfWalletUserByIds(Long[] ids)
    {
        return dfWalletUserMapper.deleteDfWalletUserByIds(ids);
    }

    /**
     * 删除钱包账户列信息
     * 
     * @param id 钱包账户列ID
     * @return 结果
     */
    @Override
    public int deleteDfWalletUserById(Long id)
    {
        return dfWalletUserMapper.deleteDfWalletUserById(id);
    }

    /**
     * 查询客户钱包列表
     * @param wallect
     * @return
     */
    @Override
    public List<WalletUserVo> selectWalletUserVoList(DfWalletUser wallet){
        return dfWalletUserMapper.selectWalletUserVoList(wallet);
    }
}
