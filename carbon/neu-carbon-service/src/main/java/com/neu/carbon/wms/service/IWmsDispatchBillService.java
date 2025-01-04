package com.neu.carbon.wms.service;

import java.util.List;
import com.neu.carbon.wms.domain.WmsDispatchBill;

/**
 * 车辆调度单Service接口
 * 
 * @author neuedu
 * @date 2022-07-04
 */
public interface IWmsDispatchBillService 
{
    /**
     * 查询车辆调度单
     * 
     * @param id 车辆调度单ID
     * @return 车辆调度单
     */
    public WmsDispatchBill selectWmsDispatchBillById(Long id);

    /**
     * 查询车辆调度单列表
     * 
     * @param wmsDispatchBill 车辆调度单
     * @return 车辆调度单集合
     */
    public List<WmsDispatchBill> selectWmsDispatchBillList(WmsDispatchBill wmsDispatchBill);

    /**
     * 新增车辆调度单
     * 
     * @param wmsDispatchBill 车辆调度单
     * @return 结果
     */
    public int insertWmsDispatchBill(WmsDispatchBill wmsDispatchBill);

    /**
     * 修改车辆调度单
     * 
     * @param wmsDispatchBill 车辆调度单
     * @return 结果
     */
    public int updateWmsDispatchBill(WmsDispatchBill wmsDispatchBill);

    /**
     * 批量删除车辆调度单
     * 
     * @param ids 需要删除的车辆调度单ID
     * @return 结果
     */
    public int deleteWmsDispatchBillByIds(Long[] ids);

    /**
     * 删除车辆调度单信息
     * 
     * @param id 车辆调度单ID
     * @return 结果
     */
    public int deleteWmsDispatchBillById(Long id);
}
