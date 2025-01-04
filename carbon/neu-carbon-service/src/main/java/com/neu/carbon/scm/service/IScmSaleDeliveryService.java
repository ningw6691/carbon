package com.neu.carbon.scm.service;

import java.util.List;
import com.neu.carbon.scm.domain.ScmSaleDelivery;

/**
 * 销售发货单Service接口
 * 
 * @author neuedu
 * @date 2022-07-05
 */
public interface IScmSaleDeliveryService 
{
    /**
     * 查询销售发货单
     * 
     * @param id 销售发货单ID
     * @return 销售发货单
     */
    public ScmSaleDelivery selectScmSaleDeliveryById(Long id);

    /**
     * 查询销售发货单列表
     * 
     * @param scmSaleDelivery 销售发货单
     * @return 销售发货单集合
     */
    public List<ScmSaleDelivery> selectScmSaleDeliveryList(ScmSaleDelivery scmSaleDelivery);

    /**
     * 新增销售发货单
     * 
     * @param scmSaleDelivery 销售发货单
     * @return 结果
     */
    public int insertScmSaleDelivery(ScmSaleDelivery scmSaleDelivery);

    /**
     * 修改销售发货单
     * 
     * @param scmSaleDelivery 销售发货单
     * @return 结果
     */
    public int updateScmSaleDelivery(ScmSaleDelivery scmSaleDelivery);

    /**
     * 批量删除销售发货单
     * 
     * @param ids 需要删除的销售发货单ID
     * @return 结果
     */
    public int deleteScmSaleDeliveryByIds(Long[] ids);

    /**
     * 删除销售发货单信息
     * 
     * @param id 销售发货单ID
     * @return 结果
     */
    public int deleteScmSaleDeliveryById(Long id);
}
