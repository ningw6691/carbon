package com.neu.carbon.scm.mapper;

import java.util.List;
import com.neu.carbon.scm.domain.ScmSaleDelivery;
import com.neu.carbon.scm.domain.ScmSaleDeliveryDetail;

/**
 * 销售发货单Mapper接口
 * 
 * @author neuedu
 * @date 2022-07-05
 */
public interface ScmSaleDeliveryMapper 
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
     * 删除销售发货单
     * 
     * @param id 销售发货单ID
     * @return 结果
     */
    public int deleteScmSaleDeliveryById(Long id);

    /**
     * 批量删除销售发货单
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteScmSaleDeliveryByIds(Long[] ids);

    /**
     * 批量删除发货单明细
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteScmSaleDeliveryDetailByDeliveryIds(Long[] ids);
    
    /**
     * 批量新增发货单明细
     * 
     * @param scmSaleDeliveryDetailList 发货单明细列表
     * @return 结果
     */
    public int batchScmSaleDeliveryDetail(List<ScmSaleDeliveryDetail> scmSaleDeliveryDetailList);
    

    /**
     * 通过销售发货单ID删除发货单明细信息
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
    public int deleteScmSaleDeliveryDetailByDeliveryId(Long id);
}
