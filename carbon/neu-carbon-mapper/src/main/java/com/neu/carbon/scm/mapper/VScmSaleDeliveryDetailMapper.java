package com.neu.carbon.scm.mapper;

import java.util.List;
import com.neu.carbon.scm.domain.VScmSaleDeliveryDetail;

/**
 * 销售发货退货明细报表Mapper接口
 * 
 * @author neuedu
 * @date 2022-08-02
 */
public interface VScmSaleDeliveryDetailMapper 
{
    /**
     * 查询销售发货退货明细报表
     * 
     * @param contractNo 销售发货退货明细报表ID
     * @return 销售发货退货明细报表
     */
    public VScmSaleDeliveryDetail selectVScmSaleDeliveryDetailById(String contractNo);

    /**
     * 查询销售发货退货明细报表列表
     * 
     * @param vScmSaleDeliveryDetail 销售发货退货明细报表
     * @return 销售发货退货明细报表集合
     */
    public List<VScmSaleDeliveryDetail> selectVScmSaleDeliveryDetailList(VScmSaleDeliveryDetail vScmSaleDeliveryDetail);

    /**
     * 新增销售发货退货明细报表
     * 
     * @param vScmSaleDeliveryDetail 销售发货退货明细报表
     * @return 结果
     */
    public int insertVScmSaleDeliveryDetail(VScmSaleDeliveryDetail vScmSaleDeliveryDetail);

    /**
     * 修改销售发货退货明细报表
     * 
     * @param vScmSaleDeliveryDetail 销售发货退货明细报表
     * @return 结果
     */
    public int updateVScmSaleDeliveryDetail(VScmSaleDeliveryDetail vScmSaleDeliveryDetail);

    /**
     * 删除销售发货退货明细报表
     * 
     * @param contractNo 销售发货退货明细报表ID
     * @return 结果
     */
    public int deleteVScmSaleDeliveryDetailById(String contractNo);

    /**
     * 批量删除销售发货退货明细报表
     * 
     * @param contractNos 需要删除的数据ID
     * @return 结果
     */
    public int deleteVScmSaleDeliveryDetailByIds(String[] contractNos);
}
