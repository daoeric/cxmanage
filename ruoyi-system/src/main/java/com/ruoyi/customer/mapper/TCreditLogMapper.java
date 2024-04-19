package com.ruoyi.customer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.common.dto.admin.CreditLogSearchDto;
import com.ruoyi.customer.domain.TCreditLog;

import java.util.List;

/**
 * 额度变更Mapper接口
 * 
 * @author ruoyi
 * @date 2022-04-17
 */
public interface TCreditLogMapper extends BaseMapper<TCreditLog>
{
    /**
     * 查询额度变更
     * 
     * @param id 额度变更ID
     * @return 额度变更
     */
    public TCreditLog selectTCreditLogById(String id);

    /**
     * 查询额度变更列表
     * 
     * @param tCreditLog 额度变更
     * @return 额度变更集合
     */
    public List<TCreditLog> selectTCreditLogList(CreditLogSearchDto tCreditLog);

    /**
     * 新增额度变更
     * 
     * @param tCreditLog 额度变更
     * @return 结果
     */
    public int insertTCreditLog(TCreditLog tCreditLog);

    /**
     * 修改额度变更
     * 
     * @param tCreditLog 额度变更
     * @return 结果
     */
    public int updateTCreditLog(TCreditLog tCreditLog);

    /**
     * 删除额度变更
     * 
     * @param id 额度变更ID
     * @return 结果
     */
    public int deleteTCreditLogById(String id);

    /**
     * 批量删除额度变更
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTCreditLogByIds(String[] ids);
}
