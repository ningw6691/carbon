package com.neu.carbon.wms.mapper;

import java.util.List;
import com.neu.carbon.wms.domain.WmsOutWarehouseApplyDetail;

/**
 * 出库申请明细Mapper接口
 * 
 * @author neuedu
 * @date 2022-06-29
 */
public interface WmsOutWarehouseApplyDetailMapper 
{
    /**
     * 查询出库申请明细
     * 
     * @param id 出库申请明细ID
     * @return 出库申请明细
     */
    public WmsOutWarehouseApplyDetail selectWmsOutWarehouseApplyDetailById(Long id);

    /**
     * 查询出库申请明细列表
     * 
     * @param wmsOutWarehouseApplyDetail 出库申请明细
     * @return 出库申请明细集合
     */
    public List<WmsOutWarehouseApplyDetail> selectWmsOutWarehouseApplyDetailList(WmsOutWarehouseApplyDetail wmsOutWarehouseApplyDetail);

    /**
     * 新增出库申请明细
     * 
     * @param wmsOutWarehouseApplyDetail 出库申请明细
     * @return 结果
     */
    public int insertWmsOutWarehouseApplyDetail(WmsOutWarehouseApplyDetail wmsOutWarehouseApplyDetail);

    /**
     * 修改出库申请明细
     * 
     * @param wmsOutWarehouseApplyDetail 出库申请明细
     * @return 结果
     */
    public int updateWmsOutWarehouseApplyDetail(WmsOutWarehouseApplyDetail wmsOutWarehouseApplyDetail);

    /**
     * 删除出库申请明细
     * 
     * @param id 出库申请明细ID
     * @return 结果
     */
    public int deleteWmsOutWarehouseApplyDetailById(Long id);

    /**
     * 批量删除出库申请明细
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWmsOutWarehouseApplyDetailByIds(Long[] ids);
}
