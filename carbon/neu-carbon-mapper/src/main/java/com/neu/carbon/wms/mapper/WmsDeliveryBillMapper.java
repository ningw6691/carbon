package com.neu.carbon.wms.mapper;

import java.util.List;
import com.neu.carbon.wms.domain.WmsDeliveryBill;
import com.neu.carbon.wms.domain.WmsDeliveryBillDetail;

/**
 * 配送管理Mapper接口
 * 
 * @author neuedu
 * @date 2022-07-05
 */
public interface WmsDeliveryBillMapper 
{
    /**
     * 查询配送管理
     * 
     * @param id 配送管理ID
     * @return 配送管理
     */
    public WmsDeliveryBill selectWmsDeliveryBillById(Long id);

    /**
     * 查询配送管理列表
     * 
     * @param wmsDeliveryBill 配送管理
     * @return 配送管理集合
     */
    public List<WmsDeliveryBill> selectWmsDeliveryBillList(WmsDeliveryBill wmsDeliveryBill);

    /**
     * 新增配送管理
     * 
     * @param wmsDeliveryBill 配送管理
     * @return 结果
     */
    public int insertWmsDeliveryBill(WmsDeliveryBill wmsDeliveryBill);

    /**
     * 修改配送管理
     * 
     * @param wmsDeliveryBill 配送管理
     * @return 结果
     */
    public int updateWmsDeliveryBill(WmsDeliveryBill wmsDeliveryBill);

    /**
     * 删除配送管理
     * 
     * @param id 配送管理ID
     * @return 结果
     */
    public int deleteWmsDeliveryBillById(Long id);

    /**
     * 批量删除配送管理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWmsDeliveryBillByIds(Long[] ids);

    /**
     * 批量删除配送单明细
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWmsDeliveryBillDetailByDeliveryBillIds(Long[] ids);
    
    /**
     * 批量新增配送单明细
     * 
     * @param wmsDeliveryBillDetailList 配送单明细列表
     * @return 结果
     */
    public int batchWmsDeliveryBillDetail(List<WmsDeliveryBillDetail> wmsDeliveryBillDetailList);
    

    /**
     * 通过配送管理ID删除配送单明细信息
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
    public int deleteWmsDeliveryBillDetailByDeliveryBillId(Long id);
}
