package com.neu.carbon.wms.mapper;

import java.util.List;
import com.neu.carbon.wms.domain.WmsInWarehouseApply;
import com.neu.carbon.wms.domain.WmsInWarehouseApplyDetail;

/**
 * 入库申请Mapper接口
 * 
 * @author neusoft
 * @date 2022-06-27
 */
public interface WmsInWarehouseApplyMapper 
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
     * 删除入库申请
     * 
     * @param id 入库申请ID
     * @return 结果
     */
    public int deleteWmsInWarehouseApplyById(Long id);

    /**
     * 批量删除入库申请
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWmsInWarehouseApplyByIds(Long[] ids);

    /**
     * 批量删除入库申请明细
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWmsInWarehouseApplyDetailByApplyIds(Long[] ids);
    
    /**
     * 批量新增入库申请明细
     * 
     * @param wmsInWarehouseApplyDetailList 入库申请明细列表
     * @return 结果
     */
    public int batchWmsInWarehouseApplyDetail(List<WmsInWarehouseApplyDetail> wmsInWarehouseApplyDetailList);
    

    /**
     * 通过入库申请ID删除入库申请明细信息
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
    public int deleteWmsInWarehouseApplyDetailByApplyId(Long id);
}
