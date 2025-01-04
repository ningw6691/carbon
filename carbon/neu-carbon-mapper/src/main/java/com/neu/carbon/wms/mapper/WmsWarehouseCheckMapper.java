package com.neu.carbon.wms.mapper;

import java.util.List;
import com.neu.carbon.wms.domain.WmsWarehouseCheck;

/**
 * 盘点信息Mapper接口
 * 
 * @author neuedu
 * @date 2022-07-06
 */
public interface WmsWarehouseCheckMapper 
{
    /**
     * 查询盘点信息
     * 
     * @param id 盘点信息ID
     * @return 盘点信息
     */
    public WmsWarehouseCheck selectWmsWarehouseCheckById(Long id);

    /**
     * 查询盘点信息列表
     * 
     * @param wmsWarehouseCheck 盘点信息
     * @return 盘点信息集合
     */
    public List<WmsWarehouseCheck> selectWmsWarehouseCheckList(WmsWarehouseCheck wmsWarehouseCheck);

    /**
     * 新增盘点信息
     * 
     * @param wmsWarehouseCheck 盘点信息
     * @return 结果
     */
    public int insertWmsWarehouseCheck(WmsWarehouseCheck wmsWarehouseCheck);

    /**
     * 修改盘点信息
     * 
     * @param wmsWarehouseCheck 盘点信息
     * @return 结果
     */
    public int updateWmsWarehouseCheck(WmsWarehouseCheck wmsWarehouseCheck);

    /**
     * 删除盘点信息
     * 
     * @param id 盘点信息ID
     * @return 结果
     */
    public int deleteWmsWarehouseCheckById(Long id);

    /**
     * 批量删除盘点信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWmsWarehouseCheckByIds(Long[] ids);
}
