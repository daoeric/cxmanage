package com.ruoyi.customer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.common.dto.admin.CustomerSearchDto;
import com.ruoyi.common.vo.CustomerAdminVO;
import com.ruoyi.customer.domain.TCustomer;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 商户信息Mapper接口
 * 
 * @author yf
 * @date 2022-04-17
 */
public interface TCustomerMapper extends BaseMapper<TCustomer>
{
    /**
     * 查询商户信息
     * 
     * @param id 商户信息ID
     * @return 商户信息
     */
    public TCustomer selectTCustomerById(Long id);

    /**
     * 查询商户信息列表
     * 
     * @param tCustomer 商户信息
     * @return 商户信息集合
     */
    public List<TCustomer> selectTCustomerList(TCustomer tCustomer);

    /**
     * 新增商户信息
     * 
     * @param tCustomer 商户信息
     * @return 结果
     */
    public int insertTCustomer(TCustomer tCustomer);

    /**
     * 修改商户信息
     * 
     * @param tCustomer 商户信息
     * @return 结果
     */
    public int updateTCustomer(TCustomer tCustomer);

    /**
     * 删除商户信息
     * 
     * @param id 商户信息ID
     * @return 结果
     */
    public int deleteTCustomerById(Long id);

    /**
     * 批量删除商户信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTCustomerByIds(Long[] ids);

    /**
     * 管理端商户List
     * @param tCustomer
     * @return
     */
    List<CustomerAdminVO> selectAdminCustomerList(CustomerSearchDto tCustomer);

    TCustomer findByIdForUpdate(Long mchId);

    /**
     * 发起订单 额度操作
     * @param mchId
     * @param amount
     * @return
     */
    int withdrawalCreate(@Param("mchId") Long mchId,@Param("amount") BigDecimal amount);

    /**
     * 代付成功,额度操作
     * @param mchId
     * @param amount
     * @return
     */
    int withdrawalSuccess(@Param("mchId") Long mchId,@Param("amount") BigDecimal amount);

    /**
     * 代付失败额度操作
     * @param mchId
     * @param amount
     * @return
     */
    int withdrawalFailed(@Param("mchId")Long mchId,@Param("amount") BigDecimal amount);

    Map<String, Object> sumBalance(CustomerSearchDto tCustomer);
}
