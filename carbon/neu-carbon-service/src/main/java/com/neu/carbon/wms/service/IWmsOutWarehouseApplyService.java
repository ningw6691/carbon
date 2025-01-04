package com.neu.carbon.wms.service;

import java.util.List;
import com.neu.carbon.wms.domain.WmsOutWarehouseApply;

/**
 * 出库申请Service接口
 * 
 * @author neuedu
 * @date 2022-06-29
 */
public interface IWmsOutWarehouseApplyService 
{
    /**
     * 查询出库申请
     * 
     * @param id 出库申请ID
     * @return 出库申请
     */
    public WmsOutWarehouseApply selectWmsOutWarehouseApplyById(Long id);

    /**
     * 查询出库申请列表
     * 
     * @param wmsOutWarehouseApply 出库申请
     * @return 出库申请集合
     */
    public List<WmsOutWarehouseApply> selectWmsOutWarehouseApplyList(WmsOutWarehouseApply wmsOutWarehouseApply);

    /**
     * 新增出库申请
     * 
     * @param wmsOutWarehouseApply 出库申请
     * @return 结果
     */
    public int insertWmsOutWarehouseApply(WmsOutWarehouseApply wmsOutWarehouseApply);

    /**
     * 修改出库申请
     * 
     * @param wmsOutWarehouseApply 出库申请
     * @return 结果
     */
    public int updateWmsOutWarehouseApply(WmsOutWarehouseApply wmsOutWarehouseApply);

    /**
     * 批量删除出库申请
     * 
     * @param ids 需要删除的出库申请ID
     * @return 结果
     */
    public int deleteWmsOutWarehouseApplyByIds(Long[] ids);

    /**
     * 删除出库申请信息
     * 
     * @param id 出库申请ID
     * @return 结果
     */
    public int deleteWmsOutWarehouseApplyById(Long id);
    
    /**
     * 执行分拣
     * @param id
     */
    public void performOutWarehouse(Long id);
    
    /**
     * 执行发货
     * @param id
     */
    public void performDelivery(Long id);
}
