package com.ruoyi.customer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.dto.DepositDto;
import com.ruoyi.common.dto.ListDto;
import com.ruoyi.common.dto.ManualDepositDto;
import com.ruoyi.common.dto.admin.DepositSearchDto;
import com.ruoyi.common.vo.PaymentRequestVO;
import com.ruoyi.common.vo.front.TPaymentRequestVO;
import com.ruoyi.common.vo.payment.DepositResult;
import com.ruoyi.customer.domain.TPaymentRequest;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * 商户充值订单Service接口
 * 
 * @author yf
 * @date 2022-04-17
 */
public interface ITPaymentRequestService extends IService<TPaymentRequest>
{
    /**
     * 查询商户充值订单
     * 
     * @param requestId 商户充值订单ID
     * @return 商户充值订单
     */
    public TPaymentRequest selectTPaymentRequestById(String requestId);

    /**
     * 查询商户充值订单列表
     * 
     * @param tPaymentRequest 商户充值订单
     * @return 商户充值订单集合
     */
    public List<TPaymentRequest> selectTPaymentRequestList(TPaymentRequest tPaymentRequest);

    /**
     * 新增商户充值订单
     * 
     * @param dto 商户充值订单
     * @return 结果
     */
    public boolean insertTPaymentRequest(ManualDepositDto dto) throws Exception;

    /**
     * 修改商户充值订单
     * 
     * @param tPaymentRequest 商户充值订单
     * @return 结果
     */
    public int updateTPaymentRequest(TPaymentRequest tPaymentRequest);

    /**
     * 批量删除商户充值订单
     * 
     * @param requestIds 需要删除的商户充值订单ID
     * @return 结果
     */
    public int deleteTPaymentRequestByIds(String[] requestIds);

    /**
     * 删除商户充值订单信息
     * 
     * @param requestId 商户充值订单ID
     * @return 结果
     */
    public int deleteTPaymentRequestById(String requestId);

    /**
     * 内充
     * @param depositDto
     * @return
     * @throws Exception
     */
    DepositResult createUSDTDeposit(DepositDto depositDto) throws Exception;

    /**
     * 接口发起代收
     * @param depositDto
     * @param isManual true -手动入金 (不需要调用三方) false-自动入金
     * @return
     */
    DepositResult create(DepositDto depositDto,boolean isManual) throws Exception;


    /**
     * 存款订单设置成功
     * @param billNo 订单号
     * @param amount 实际金额，如果为null 则以订单金额为主
     * @return
     */
    boolean doSuccess(String billNo,BigDecimal amount);

    /**
     * 订单VO 列表
     * @param tPaymentRequest
     * @return
     */
    List<PaymentRequestVO> listOrder(DepositSearchDto tPaymentRequest);


    /**
     * 代收回调
     * @param paymentRequest
     */
    String callbackCustomer(TPaymentRequest paymentRequest);

    /**
     * 查询订单
     * @param mchId
     * @param orderNo
     * @return
     */
    TPaymentRequest queryOrder(Long mchId, String orderNo);

    Map<String, Object> sum(ListDto dto, Long mchId);

    Map<String, Object> sum(DepositSearchDto depositSearchDto);

    Map<String, Object> depositInfo(Integer status);

    List<Map<String, Object>> depositChatData();

    /**
     * list 商户展示数据
     * @param dto
     * @param merId
     * @return
     */
    List<TPaymentRequestVO> listCustomer(ListDto dto, Long merId, boolean isAgent);

    /**
     * 日报数据统计插入
     */
    void dayReport() throws ParseException;

    Map<String, Object> successSum(ListDto dto, Long mchId,Integer usetType);

    void channelDayReport() throws ParseException;
}
