package com.ruoyi.customer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.dto.admin.CreditLogSearchDto;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.customer.domain.TCreditLog;
import com.ruoyi.customer.mapper.TCreditLogMapper;
import com.ruoyi.customer.service.ITCreditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 额度变更Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-04-17
 */
@Service
public class TCreditLogServiceImpl extends ServiceImpl<TCreditLogMapper,TCreditLog> implements ITCreditLogService
{
    @Autowired
    private TCreditLogMapper tCreditLogMapper;

    /**
     * 查询额度变更
     * 
     * @param id 额度变更ID
     * @return 额度变更
     */
    @Override
    public TCreditLog selectTCreditLogById(String id)
    {
        return tCreditLogMapper.selectTCreditLogById(id);
    }

    /**
     * 查询额度变更列表
     * 
     * @param tCreditLog 额度变更
     * @return 额度变更
     */
    @Override
    public List<TCreditLog> selectTCreditLogList(CreditLogSearchDto tCreditLog)
    {
        return tCreditLogMapper.selectTCreditLogList(tCreditLog);
    }

    /**
     * 新增额度变更
     * 
     * @param tCreditLog 额度变更
     * @return 结果
     */
    @Override
    public int insertTCreditLog(TCreditLog tCreditLog)
    {
        tCreditLog.setCreateTime(DateUtils.getNowDate());
        return tCreditLogMapper.insertTCreditLog(tCreditLog);
    }

    /**
     * 修改额度变更
     * 
     * @param tCreditLog 额度变更
     * @return 结果
     */
    @Override
    public int updateTCreditLog(TCreditLog tCreditLog)
    {
        tCreditLog.setUpdateTime(DateUtils.getNowDate());
        return tCreditLogMapper.updateTCreditLog(tCreditLog);
    }

    /**
     * 批量删除额度变更
     * 
     * @param ids 需要删除的额度变更ID
     * @return 结果
     */
    @Override
    public int deleteTCreditLogByIds(String[] ids)
    {
        return tCreditLogMapper.deleteTCreditLogByIds(ids);
    }

    /**
     * 删除额度变更信息
     * 
     * @param id 额度变更ID
     * @return 结果
     */
    @Override
    public int deleteTCreditLogById(String id)
    {
        return tCreditLogMapper.deleteTCreditLogById(id);
    }
}
