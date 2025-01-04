package com.neu.carbon.wms.service.impl;

import java.util.List;
import com.neu.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.neu.carbon.wms.mapper.WmsWarehouseMaterialDetailMapper;
import com.neu.carbon.wms.domain.WmsWarehouseMaterialDetail;
import com.neu.carbon.wms.service.IWmsWarehouseMaterialDetailService;

/**
 * 仓库物料明细Service业务层处理
 * 
 * @author neusoft
 * @date 2022-06-24
 */
@Service
public class WmsWarehouseMaterialDetailServiceImpl implements IWmsWarehouseMaterialDetailService 
{
    @Autowired
    private WmsWarehouseMaterialDetailMapper wmsWarehouseMaterialDetailMapper;

    /**
     * 查询仓库物料明细
     * 
     * @param id 仓库物料明细ID
     * @return 仓库物料明细
     */
    @Override
    public WmsWarehouseMaterialDetail selectWmsWarehouseMaterialDetailById(Long id)
    {
        return wmsWarehouseMaterialDetailMapper.selectWmsWarehouseMaterialDetailById(id);
    }

    /**
     * 查询仓库物料明细列表
     * 
     * @param wmsWarehouseMaterialDetail 仓库物料明细
     * @return 仓库物料明细
     */
    @Override
    public List<WmsWarehouseMaterialDetail> selectWmsWarehouseMaterialDetailList(WmsWarehouseMaterialDetail wmsWarehouseMaterialDetail)
    {
        return wmsWarehouseMaterialDetailMapper.selectWmsWarehouseMaterialDetailList(wmsWarehouseMaterialDetail);
    }

    /**
     * 新增仓库物料明细
     * 
     * @param wmsWarehouseMaterialDetail 仓库物料明细
     * @return 结果
     */
    @Override
    public int insertWmsWarehouseMaterialDetail(WmsWarehouseMaterialDetail wmsWarehouseMaterialDetail)
    {
        wmsWarehouseMaterialDetail.setCreateTime(DateUtils.getNowDate());
        return wmsWarehouseMaterialDetailMapper.insertWmsWarehouseMaterialDetail(wmsWarehouseMaterialDetail);
    }

    /**
     * 修改仓库物料明细
     * 
     * @param wmsWarehouseMaterialDetail 仓库物料明细
     * @return 结果
     */
    @Override
    public int updateWmsWarehouseMaterialDetail(WmsWarehouseMaterialDetail wmsWarehouseMaterialDetail)
    {
        wmsWarehouseMaterialDetail.setUpdateTime(DateUtils.getNowDate());
        return wmsWarehouseMaterialDetailMapper.updateWmsWarehouseMaterialDetail(wmsWarehouseMaterialDetail);
    }

    /**
     * 批量删除仓库物料明细
     * 
     * @param ids 需要删除的仓库物料明细ID
     * @return 结果
     */
    @Override
    public int deleteWmsWarehouseMaterialDetailByIds(Long[] ids)
    {
        return wmsWarehouseMaterialDetailMapper.deleteWmsWarehouseMaterialDetailByIds(ids);
    }

    /**
     * 删除仓库物料明细信息
     * 
     * @param id 仓库物料明细ID
     * @return 结果
     */
    @Override
    public int deleteWmsWarehouseMaterialDetailById(Long id)
    {
        return wmsWarehouseMaterialDetailMapper.deleteWmsWarehouseMaterialDetailById(id);
    }
}
