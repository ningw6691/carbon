package com.neu.carbon.wms.mapper;

import java.util.List;
import com.neu.carbon.wms.domain.WmsWarehouseMaterialMove;

/**
 * 移库信息Mapper接口
 * 
 * @author neuedu
 * @date 2022-07-05
 */
public interface WmsWarehouseMaterialMoveMapper 
{
    /**
     * 查询移库信息
     * 
     * @param id 移库信息ID
     * @return 移库信息
     */
    public WmsWarehouseMaterialMove selectWmsWarehouseMaterialMoveById(Long id);

    /**
     * 查询移库信息列表
     * 
     * @param wmsWarehouseMaterialMove 移库信息
     * @return 移库信息集合
     */
    public List<WmsWarehouseMaterialMove> selectWmsWarehouseMaterialMoveList(WmsWarehouseMaterialMove wmsWarehouseMaterialMove);

    /**
     * 新增移库信息
     * 
     * @param wmsWarehouseMaterialMove 移库信息
     * @return 结果
     */
    public int insertWmsWarehouseMaterialMove(WmsWarehouseMaterialMove wmsWarehouseMaterialMove);

    /**
     * 修改移库信息
     * 
     * @param wmsWarehouseMaterialMove 移库信息
     * @return 结果
     */
    public int updateWmsWarehouseMaterialMove(WmsWarehouseMaterialMove wmsWarehouseMaterialMove);

    /**
     * 删除移库信息
     * 
     * @param id 移库信息ID
     * @return 结果
     */
    public int deleteWmsWarehouseMaterialMoveById(Long id);

    /**
     * 批量删除移库信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWmsWarehouseMaterialMoveByIds(Long[] ids);
}
