package com.ruoyi.customer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.dto.ListDto;
import com.ruoyi.common.dto.WithdrawalAddDto;
import com.ruoyi.common.dto.WithdrawalDto;
import com.ruoyi.common.dto.admin.MualWithAddDto;
import com.ruoyi.common.dto.admin.WithdrawalSearchDto;
import com.ruoyi.common.vo.WithdrawalRequestVO;
import com.ruoyi.customer.domain.TCustomer;
import com.ruoyi.customer.domain.TWithdrawRequest;

import java.util.List;
import java.util.Map;

/**
 * 代付提现Service接口
 * 
 * @author yf
 * @date 2022-04-17
 */
public interface ITWithdrawRequestService extends IService<TWithdrawRequest>
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
     * 批量删除代付提现
     * 
     * @param withdrawIds 需要删除的代付提现ID
     * @return 结果
     */
    public int deleteTWithdrawRequestByIds(String[] withdrawIds);

    /**
     * 删除代付提现信息
     * 
     * @param withdrawId 代付提现ID
     * @return 结果
     */
    public int deleteTWithdrawRequestById(String withdrawId);

    /**
     * 发起代付请求
     *
     * @param withdrawalDto
     * @return
     */
    TWithdrawRequest create(WithdrawalDto withdrawalDto,boolean isAuto) throws Exception;

    /**
     * 代付回调
     * @param billNo 内部订单号
     * @param orderNo 上游订单号
     * @return
     */
    boolean doSuccess(String billNo, String orderNo,String remark);

    /**
     * 检查订单号是否存在
     * @param mchId
     * @param orderId
     * @return
     */
    boolean checkOrderExist(Long mchId, String orderId);

    /**
     * 回调下游客户
     * @param withdrawRequest
     */
    String callbackCustomer(TWithdrawRequest withdrawRequest);

    /**
     * 根据商户号和商户订单号查询订单
     * @param mchId
     * @param orderId
     * @return
     */
    TWithdrawRequest queryOrder(Long mchId, String orderId);

    /**
     * 获取提款订单VO List
     * @param tWithdrawRequest
     * @return
     */
    List<WithdrawalRequestVO> listOrder(WithdrawalSearchDto tWithdrawRequest);

    /**
     * 审核订单
     * @param tWithdrawRequest
     * @return
     */
//    boolean approve(TWithdrawRequest tWithdrawRequest);

    /**
     * 订单失败处理
     * @param billNo
     * @param orderNo
     * @param result
     * @return
     */
    boolean doFail(String billNo, String orderNo, String result);

    Map<String, Object> sum(ListDto dto, TCustomer customer);

    Map<String, Object> sum(WithdrawalSearchDto withdrawalSearchDto);

    /**
     * 获取订单成功率
     *
     * @param withdrawalSearchDto
     * @return
     */
    Map<String, Object> successRate(WithdrawalSearchDto withdrawalSearchDto);

    Map<String, Object> successRate(ListDto dto, TCustomer customer);

    /**
     * 手动申请提现
     *
     * @param dto
     * @param ip
     * @return
     */
    TWithdrawRequest createMual(Long mchId, WithdrawalAddDto dto, String ip) throws Exception;

    /**
     * 管理员手动代付
     *
     * @param mualWithAddDto
     * @return
     */
    TWithdrawRequest adminCreateMual(MualWithAddDto mualWithAddDto) throws Exception;

    /**
     * 查看需要警告的数量
     * @return
     */
    Integer countNotice();

    Map<String, Object> failRate(WithdrawalSearchDto withdrawalSearchDto);

    Map<String, Object> failRate(ListDto dto, TCustomer customer);
}
