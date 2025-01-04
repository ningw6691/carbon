package com.neu.carbon.wms.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.neu.carbon.wms.mapper.WmsInWarehouseApplyDetailMapper;
import com.neu.carbon.wms.domain.WmsInWarehouseApplyDetail;
import com.neu.carbon.wms.service.IWmsInWarehouseApplyDetailService;

/**
 * 入库申请明细Service业务层处理
 * 
 * @author neusoft
 * @date 2022-06-27
 */
@Service
public class WmsInWarehouseApplyDetailServiceImpl implements IWmsInWarehouseApplyDetailService 
{
    @Autowired
    private WmsInWarehouseApplyDetailMapper wmsInWarehouseApplyDetailMapper;

    /**
     * 查询入库申请明细
     * 
     * @param id 入库申请明细ID
     * @return 入库申请明细
     */
    @Override
    public WmsInWarehouseApplyDetail selectWmsInWarehouseApplyDetailById(Long id)
    {
        return wmsInWarehouseApplyDetailMapper.selectWmsInWarehouseApplyDetailById(id);
    }

    /**
     * 查询入库申请明细列表
     * 
     * @param wmsInWarehouseApplyDetail 入库申请明细
     * @return 入库申请明细
     */
    @Override
    public List<WmsInWarehouseApplyDetail> selectWmsInWarehouseApplyDetailList(WmsInWarehouseApplyDetail wmsInWarehouseApplyDetail)
    {
        return wmsInWarehouseApplyDetailMapper.selectWmsInWarehouseApplyDetailList(wmsInWarehouseApplyDetail);
    }

    /**
     * 新增入库申请明细
     * 
     * @param wmsInWarehouseApplyDetail 入库申请明细
     * @return 结果
     */
    @Override
    public int insertWmsInWarehouseApplyDetail(WmsInWarehouseApplyDetail wmsInWarehouseApplyDetail)
    {
        return wmsInWarehouseApplyDetailMapper.insertWmsInWarehouseApplyDetail(wmsInWarehouseApplyDetail);
    }

    /**
     * 修改入库申请明细
     * 
     * @param wmsInWarehouseApplyDetail 入库申请明细
     * @return 结果
     */
    @Override
    public int updateWmsInWarehouseApplyDetail(WmsInWarehouseApplyDetail wmsInWarehouseApplyDetail)
    {
        return wmsInWarehouseApplyDetailMapper.updateWmsInWarehouseApplyDetail(wmsInWarehouseApplyDetail);
    }

    /**
     * 批量删除入库申请明细
     * 
     * @param ids 需要删除的入库申请明细ID
     * @return 结果
     */
    @Override
    public int deleteWmsInWarehouseApplyDetailByIds(Long[] ids)
    {
        return wmsInWarehouseApplyDetailMapper.deleteWmsInWarehouseApplyDetailByIds(ids);
    }

    /**
     * 删除入库申请明细信息
     * 
     * @param id 入库申请明细ID
     * @return 结果
     */
    @Override
    public int deleteWmsInWarehouseApplyDetailById(Long id)
    {
        return wmsInWarehouseApplyDetailMapper.deleteWmsInWarehouseApplyDetailById(id);
    }
}
