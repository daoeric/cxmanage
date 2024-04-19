package com.ruoyi.customer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.common.dto.admin.WithdrawalSearchDto;
import com.ruoyi.common.vo.WithdrawalRequestVO;
import com.ruoyi.customer.domain.TWithdrawRequest;

import java.util.List;
import java.util.Map;

/**
 * 代付提现Mapper接口
 * 
 * @author yf
 * @date 2022-04-17
 */
public interface TWithdrawRequestMapper extends BaseMapper<TWithdrawRequest>
{
    /**
     * 查询代付提现
     * 
     * @param withdrawId 代付提现ID
     * @return 代付提现
     */
    public TWithdrawRequest selectTWithdrawRequestById(String withdrawId);

    /**
     * 查询代付提现列表
     * 
     * @param tWithdrawRequest 代付提现
     * @return 代付提现集合
     */
    public List<TWithdrawRequest> selectTWithdrawRequestList(TWithdrawRequest tWithdrawRequest);

    /**
     * 新增代付提现
     * 
     * @param tWithdrawRequest 代付提现
     * @return 结果
     */
    public int insertTWithdrawRequest(TWithdrawRequest tWithdrawRequest);

    /**
     * 修改代付提现
     * 
     * @param tWithdrawRequest 代付提现
     * @return 结果
     */
    public int updateTWithdrawRequest(TWithdrawRequest tWithdrawRequest);

    /**
     * 删除代付提现
     * 
     * @param withdrawId 代付提现ID
     * @return 结果
     */
    public int deleteTWithdrawRequestById(String withdrawId);

    /**
     * 批量删除代付提现
     * 
     * @param withdrawIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteTWithdrawRequestByIds(String[] withdrawIds);

    /**
     * 查询提款订单 VO
     * @param tWithdrawRequest
     * @return
     */
    List<WithdrawalRequestVO> listOrder(WithdrawalSearchDto tWithdrawRequest);

    Map<String, Object> listOrderSum(WithdrawalSearchDto dto);

    Map<String,Object> successRate(WithdrawalSearchDto dto);

    TWithdrawRequest findByIdForUpdate(String billNo);

    Map<String, Object> failRate(WithdrawalSearchDto dto);
}
