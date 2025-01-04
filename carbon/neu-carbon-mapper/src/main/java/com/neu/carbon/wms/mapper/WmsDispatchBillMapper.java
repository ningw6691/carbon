package com.neu.carbon.wms.mapper;

import java.util.List;
import com.neu.carbon.wms.domain.WmsDispatchBill;
import com.neu.carbon.wms.domain.WmsDispatchBillDetail;

/**
 * 车辆调度单Mapper接口
 * 
 * @author neuedu
 * @date 2022-07-04
 */
public interface WmsDispatchBillMapper 
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
     * 删除车辆调度单
     * 
     * @param id 车辆调度单ID
     * @return 结果
     */
    public int deleteWmsDispatchBillById(Long id);

    /**
     * 批量删除车辆调度单
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWmsDispatchBillByIds(Long[] ids);

    /**
     * 批量删除车辆调度单明细
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWmsDispatchBillDetailByDispatchBillIds(Long[] ids);
    
    /**
     * 批量新增车辆调度单明细
     * 
     * @param wmsDispatchBillDetailList 车辆调度单明细列表
     * @return 结果
     */
    public int batchWmsDispatchBillDetail(List<WmsDispatchBillDetail> wmsDispatchBillDetailList);
    

    /**
     * 通过车辆调度单ID删除车辆调度单明细信息
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
    public int deleteWmsDispatchBillDetailByDispatchBillId(Long id);
}
