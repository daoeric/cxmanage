package com.ruoyi.customer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.common.dto.ListDto;
import com.ruoyi.common.dto.admin.DepositSearchDto;
import com.ruoyi.common.vo.PaymentRequestVO;
import com.ruoyi.common.vo.front.TPaymentRequestVO;
import com.ruoyi.customer.domain.TPaymentRequest;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 商户充值订单Mapper接口
 * 
 * @author yf
 * @date 2022-04-17
 */
public interface TPaymentRequestMapper extends BaseMapper<TPaymentRequest>
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
     * @param tPaymentRequest 商户充值订单
     * @return 结果
     */
    public int insertTPaymentRequest(TPaymentRequest tPaymentRequest);

    /**
     * 修改商户充值订单
     * 
     * @param tPaymentRequest 商户充值订单
     * @return 结果
     */
    public int updateTPaymentRequest(TPaymentRequest tPaymentRequest);

    /**
     * 删除商户充值订单
     * 
     * @param requestId 商户充值订单ID
     * @return 结果
     */
    public int deleteTPaymentRequestById(String requestId);

    /**
     * 批量删除商户充值订单
     * 
     * @param requestIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteTPaymentRequestByIds(String[] requestIds);

    /**
     * 存款订单 VO
     * @param tPaymentRequest
     * @return
     */
    List<PaymentRequestVO> listOrder(DepositSearchDto tPaymentRequest);

    List<TPaymentRequestVO> listCustomer(@Param("dto") ListDto dto, @Param("mchId") Long merId,@Param("isAgent") boolean isAgent);

    Map<String, Object> sum(@Param("dto") ListDto dto, @Param("mchId") Long merId,@Param("isAgent") boolean isAgent);

    Map<String, Object> listOrderSum(DepositSearchDto tPaymentRequest);

    Map<String, Object> successSum(@Param("dto") ListDto dto, @Param("mchId") Long merId,@Param("isAgent") boolean isAgent);

    void insertDayReport(@Param("begin") Date begin,@Param("end") Date end);

    void insertChannelDayReport(@Param("begin") Date begin,@Param("end") Date end);
}
