package com.neu.carbon.wms.mapper;

import java.util.List;
import com.neu.carbon.wms.domain.WmsDeliveryBillDetail;

/**
 * 配送单明细Mapper接口
 * 
 * @author neuedu
 * @date 2022-07-05
 */
public interface WmsDeliveryBillDetailMapper 
{
    /**
     * 查询配送单明细
     * 
     * @param id 配送单明细ID
     * @return 配送单明细
     */
    public WmsDeliveryBillDetail selectWmsDeliveryBillDetailById(Long id);

    /**
     * 查询配送单明细列表
     * 
     * @param wmsDeliveryBillDetail 配送单明细
     * @return 配送单明细集合
     */
    public List<WmsDeliveryBillDetail> selectWmsDeliveryBillDetailList(WmsDeliveryBillDetail wmsDeliveryBillDetail);

    /**
     * 新增配送单明细
     * 
     * @param wmsDeliveryBillDetail 配送单明细
     * @return 结果
     */
    public int insertWmsDeliveryBillDetail(WmsDeliveryBillDetail wmsDeliveryBillDetail);

    /**
     * 修改配送单明细
     * 
     * @param wmsDeliveryBillDetail 配送单明细
     * @return 结果
     */
    public int updateWmsDeliveryBillDetail(WmsDeliveryBillDetail wmsDeliveryBillDetail);

    /**
     * 删除配送单明细
     * 
     * @param id 配送单明细ID
     * @return 结果
     */
    public int deleteWmsDeliveryBillDetailById(Long id);

    /**
     * 批量删除配送单明细
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWmsDeliveryBillDetailByIds(Long[] ids);
}
