package com.neu.carbon.scm.mapper;

import java.util.List;
import com.neu.carbon.scm.domain.ScmSaleDeliveryDetail;

/**
 * 发货单明细Mapper接口
 * 
 * @author neuedu
 * @date 2022-07-05
 */
public interface ScmSaleDeliveryDetailMapper 
{
    /**
     * 查询发货单明细
     * 
     * @param id 发货单明细ID
     * @return 发货单明细
     */
    public ScmSaleDeliveryDetail selectScmSaleDeliveryDetailById(Long id);

    /**
     * 查询发货单明细列表
     * 
     * @param scmSaleDeliveryDetail 发货单明细
     * @return 发货单明细集合
     */
    public List<ScmSaleDeliveryDetail> selectScmSaleDeliveryDetailList(ScmSaleDeliveryDetail scmSaleDeliveryDetail);

    /**
     * 新增发货单明细
     * 
     * @param scmSaleDeliveryDetail 发货单明细
     * @return 结果
     */
    public int insertScmSaleDeliveryDetail(ScmSaleDeliveryDetail scmSaleDeliveryDetail);

    /**
     * 修改发货单明细
     * 
     * @param scmSaleDeliveryDetail 发货单明细
     * @return 结果
     */
    public int updateScmSaleDeliveryDetail(ScmSaleDeliveryDetail scmSaleDeliveryDetail);

    /**
     * 删除发货单明细
     * 
     * @param id 发货单明细ID
     * @return 结果
     */
    public int deleteScmSaleDeliveryDetailById(Long id);

    /**
     * 批量删除发货单明细
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteScmSaleDeliveryDetailByIds(Long[] ids);
}
