package com.neu.carbon.scm.mapper;

import java.util.List;
import com.neu.carbon.scm.domain.ScmSaleOrder;
import com.neu.carbon.scm.domain.ScmSaleOrderDetail;

/**
 * 销售订单Mapper接口
 * 
 * @author neuedu
 * @date 2022-07-03
 */
public interface ScmSaleOrderMapper 
{
    /**
     * 查询销售订单
     * 
     * @param id 销售订单ID
     * @return 销售订单
     */
    public ScmSaleOrder selectScmSaleOrderById(Long id);

    /**
     * 查询销售订单列表
     * 
     * @param scmSaleOrder 销售订单
     * @return 销售订单集合
     */
    public List<ScmSaleOrder> selectScmSaleOrderList(ScmSaleOrder scmSaleOrder);

    /**
     * 新增销售订单
     * 
     * @param scmSaleOrder 销售订单
     * @return 结果
     */
    public int insertScmSaleOrder(ScmSaleOrder scmSaleOrder);

    /**
     * 修改销售订单
     * 
     * @param scmSaleOrder 销售订单
     * @return 结果
     */
    public int updateScmSaleOrder(ScmSaleOrder scmSaleOrder);

    /**
     * 删除销售订单
     * 
     * @param id 销售订单ID
     * @return 结果
     */
    public int deleteScmSaleOrderById(Long id);

    /**
     * 批量删除销售订单
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteScmSaleOrderByIds(Long[] ids);

    /**
     * 批量删除订单明细
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteScmSaleOrderDetailByOrderIds(Long[] ids);
    
    /**
     * 批量新增订单明细
     * 
     * @param scmSaleOrderDetailList 订单明细列表
     * @return 结果
     */
    public int batchScmSaleOrderDetail(List<ScmSaleOrderDetail> scmSaleOrderDetailList);
    

    /**
     * 通过销售订单ID删除订单明细信息
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
    public int deleteScmSaleOrderDetailByOrderId(Long id);
}
