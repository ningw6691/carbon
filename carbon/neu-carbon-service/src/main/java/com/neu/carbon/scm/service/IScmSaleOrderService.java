package com.neu.carbon.scm.service;

import java.util.List;
import com.neu.carbon.scm.domain.ScmSaleOrder;

/**
 * 销售订单Service接口
 * 
 * @author neuedu
 * @date 2022-07-03
 */
public interface IScmSaleOrderService 
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
     * 批量删除销售订单
     * 
     * @param ids 需要删除的销售订单ID
     * @return 结果
     */
    public int deleteScmSaleOrderByIds(Long[] ids);

    /**
     * 删除销售订单信息
     * 
     * @param id 销售订单ID
     * @return 结果
     */
    public int deleteScmSaleOrderById(Long id);
}
