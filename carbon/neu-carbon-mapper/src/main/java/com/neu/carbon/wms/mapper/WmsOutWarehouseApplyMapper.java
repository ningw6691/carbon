package com.neu.carbon.wms.mapper;

import java.util.List;
import com.neu.carbon.wms.domain.WmsOutWarehouseApply;
import com.neu.carbon.wms.domain.WmsOutWarehouseApplyDetail;

/**
 * 出库申请Mapper接口
 * 
 * @author neuedu
 * @date 2022-06-29
 */
public interface WmsOutWarehouseApplyMapper 
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
     * 删除出库申请
     * 
     * @param id 出库申请ID
     * @return 结果
     */
    public int deleteWmsOutWarehouseApplyById(Long id);

    /**
     * 批量删除出库申请
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWmsOutWarehouseApplyByIds(Long[] ids);

    /**
     * 批量删除出库申请明细
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWmsOutWarehouseApplyDetailByApplyIds(Long[] ids);
    
    /**
     * 批量新增出库申请明细
     * 
     * @param wmsOutWarehouseApplyDetailList 出库申请明细列表
     * @return 结果
     */
    public int batchWmsOutWarehouseApplyDetail(List<WmsOutWarehouseApplyDetail> wmsOutWarehouseApplyDetailList);
    

    /**
     * 通过出库申请ID删除出库申请明细信息
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
    public int deleteWmsOutWarehouseApplyDetailByApplyId(Long id);
}
