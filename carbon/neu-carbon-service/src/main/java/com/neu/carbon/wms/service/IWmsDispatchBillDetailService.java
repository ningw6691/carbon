package com.neu.carbon.wms.service;

import java.util.List;
import com.neu.carbon.wms.domain.WmsDispatchBillDetail;

/**
 * 车辆调度单明细Service接口
 * 
 * @author neuedu
 * @date 2022-07-04
 */
public interface IWmsDispatchBillDetailService 
{
    /**
     * 查询车辆调度单明细
     * 
     * @param id 车辆调度单明细ID
     * @return 车辆调度单明细
     */
    public WmsDispatchBillDetail selectWmsDispatchBillDetailById(Long id);

    /**
     * 查询车辆调度单明细列表
     * 
     * @param wmsDispatchBillDetail 车辆调度单明细
     * @return 车辆调度单明细集合
     */
    public List<WmsDispatchBillDetail> selectWmsDispatchBillDetailList(WmsDispatchBillDetail wmsDispatchBillDetail);

    /**
     * 新增车辆调度单明细
     * 
     * @param wmsDispatchBillDetail 车辆调度单明细
     * @return 结果
     */
    public int insertWmsDispatchBillDetail(WmsDispatchBillDetail wmsDispatchBillDetail);

    /**
     * 修改车辆调度单明细
     * 
     * @param wmsDispatchBillDetail 车辆调度单明细
     * @return 结果
     */
    public int updateWmsDispatchBillDetail(WmsDispatchBillDetail wmsDispatchBillDetail);

    /**
     * 批量删除车辆调度单明细
     * 
     * @param ids 需要删除的车辆调度单明细ID
     * @return 结果
     */
    public int deleteWmsDispatchBillDetailByIds(Long[] ids);

    /**
     * 删除车辆调度单明细信息
     * 
     * @param id 车辆调度单明细ID
     * @return 结果
     */
    public int deleteWmsDispatchBillDetailById(Long id);
}
