package com.neu.carbon.wms.service;

import java.util.List;
import com.neu.carbon.wms.domain.WmsInWarehouseApply;

/**
 * 入库申请Service接口
 * 
 * @author neusoft
 * @date 2022-06-27
 */
public interface IWmsInWarehouseApplyService 
{
    /**
     * 查询入库申请
     * 
     * @param id 入库申请ID
     * @return 入库申请
     */
    public WmsInWarehouseApply selectWmsInWarehouseApplyById(Long id);

    /**
     * 查询入库申请列表
     * 
     * @param wmsInWarehouseApply 入库申请
     * @return 入库申请集合
     */
    public List<WmsInWarehouseApply> selectWmsInWarehouseApplyList(WmsInWarehouseApply wmsInWarehouseApply);

    /**
     * 新增入库申请
     * 
     * @param wmsInWarehouseApply 入库申请
     * @return 结果
     */
    public int insertWmsInWarehouseApply(WmsInWarehouseApply wmsInWarehouseApply);

    /**
     * 修改入库申请
     * 
     * @param wmsInWarehouseApply 入库申请
     * @return 结果
     */
    public int updateWmsInWarehouseApply(WmsInWarehouseApply wmsInWarehouseApply);

    /**
     * 批量删除入库申请
     * 
     * @param ids 需要删除的入库申请ID
     * @return 结果
     */
    public int deleteWmsInWarehouseApplyByIds(Long[] ids);

    /**
     * 删除入库申请信息
     * 
     * @param id 入库申请ID
     * @return 结果
     */
    public int deleteWmsInWarehouseApplyById(Long id);
    
    /**
     * 执行入库
     * @param id
     */
    public void performInWarehouse(Long id);
}
