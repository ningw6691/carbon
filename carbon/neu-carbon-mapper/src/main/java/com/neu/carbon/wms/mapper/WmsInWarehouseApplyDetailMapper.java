package com.neu.carbon.wms.mapper;

import java.util.List;
import com.neu.carbon.wms.domain.WmsInWarehouseApplyDetail;

/**
 * 入库申请明细Mapper接口
 * 
 * @author neusoft
 * @date 2022-06-27
 */
public interface WmsInWarehouseApplyDetailMapper 
{
    /**
     * 查询入库申请明细
     * 
     * @param id 入库申请明细ID
     * @return 入库申请明细
     */
    public WmsInWarehouseApplyDetail selectWmsInWarehouseApplyDetailById(Long id);

    /**
     * 查询入库申请明细列表
     * 
     * @param wmsInWarehouseApplyDetail 入库申请明细
     * @return 入库申请明细集合
     */
    public List<WmsInWarehouseApplyDetail> selectWmsInWarehouseApplyDetailList(WmsInWarehouseApplyDetail wmsInWarehouseApplyDetail);

    /**
     * 新增入库申请明细
     * 
     * @param wmsInWarehouseApplyDetail 入库申请明细
     * @return 结果
     */
    public int insertWmsInWarehouseApplyDetail(WmsInWarehouseApplyDetail wmsInWarehouseApplyDetail);

    /**
     * 修改入库申请明细
     * 
     * @param wmsInWarehouseApplyDetail 入库申请明细
     * @return 结果
     */
    public int updateWmsInWarehouseApplyDetail(WmsInWarehouseApplyDetail wmsInWarehouseApplyDetail);

    /**
     * 删除入库申请明细
     * 
     * @param id 入库申请明细ID
     * @return 结果
     */
    public int deleteWmsInWarehouseApplyDetailById(Long id);

    /**
     * 批量删除入库申请明细
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWmsInWarehouseApplyDetailByIds(Long[] ids);
}
