package com.neu.carbon.wms.service;

import java.util.List;
import com.neu.carbon.wms.domain.WmsDeliveryBill;

/**
 * 配送单Service接口
 * 
 * @author neuedu
 * @date 2022-07-05
 */
public interface IWmsDeliveryBillService 
{
    /**
     * 查询配送单
     * 
     * @param id 配送单ID
     * @return 配送单
     */
    public WmsDeliveryBill selectWmsDeliveryBillById(Long id);

    /**
     * 查询配送单列表
     * 
     * @param wmsDeliveryBill 配送单
     * @return 配送单集合
     */
    public List<WmsDeliveryBill> selectWmsDeliveryBillList(WmsDeliveryBill wmsDeliveryBill);

    /**
     * 新增配送单
     * 
     * @param wmsDeliveryBill 配送单
     * @return 结果
     */
    public int insertWmsDeliveryBill(WmsDeliveryBill wmsDeliveryBill);

    /**
     * 修改配送单
     * 
     * @param wmsDeliveryBill 配送单
     * @return 结果
     */
    public int updateWmsDeliveryBill(WmsDeliveryBill wmsDeliveryBill);

    /**
     * 批量删除配送单
     * 
     * @param ids 需要删除的配送单ID
     * @return 结果
     */
    public int deleteWmsDeliveryBillByIds(Long[] ids);

    /**
     * 删除配送单信息
     * 
     * @param id 配送单ID
     * @return 结果
     */
    public int deleteWmsDeliveryBillById(Long id);
}
