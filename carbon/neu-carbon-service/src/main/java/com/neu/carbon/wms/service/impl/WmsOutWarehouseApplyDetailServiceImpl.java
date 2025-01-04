package com.neu.carbon.wms.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.neu.carbon.wms.mapper.WmsOutWarehouseApplyDetailMapper;
import com.neu.carbon.wms.domain.WmsOutWarehouseApplyDetail;
import com.neu.carbon.wms.service.IWmsOutWarehouseApplyDetailService;

/**
 * 出库申请明细Service业务层处理
 * 
 * @author neuedu
 * @date 2022-06-29
 */
@Service
public class WmsOutWarehouseApplyDetailServiceImpl implements IWmsOutWarehouseApplyDetailService 
{
    @Autowired
    private WmsOutWarehouseApplyDetailMapper wmsOutWarehouseApplyDetailMapper;

    /**
     * 查询出库申请明细
     * 
     * @param id 出库申请明细ID
     * @return 出库申请明细
     */
    @Override
    public WmsOutWarehouseApplyDetail selectWmsOutWarehouseApplyDetailById(Long id)
    {
        return wmsOutWarehouseApplyDetailMapper.selectWmsOutWarehouseApplyDetailById(id);
    }

    /**
     * 查询出库申请明细列表
     * 
     * @param wmsOutWarehouseApplyDetail 出库申请明细
     * @return 出库申请明细
     */
    @Override
    public List<WmsOutWarehouseApplyDetail> selectWmsOutWarehouseApplyDetailList(WmsOutWarehouseApplyDetail wmsOutWarehouseApplyDetail)
    {
        return wmsOutWarehouseApplyDetailMapper.selectWmsOutWarehouseApplyDetailList(wmsOutWarehouseApplyDetail);
    }

    /**
     * 新增出库申请明细
     * 
     * @param wmsOutWarehouseApplyDetail 出库申请明细
     * @return 结果
     */
    @Override
    public int insertWmsOutWarehouseApplyDetail(WmsOutWarehouseApplyDetail wmsOutWarehouseApplyDetail)
    {
        return wmsOutWarehouseApplyDetailMapper.insertWmsOutWarehouseApplyDetail(wmsOutWarehouseApplyDetail);
    }

    /**
     * 修改出库申请明细
     * 
     * @param wmsOutWarehouseApplyDetail 出库申请明细
     * @return 结果
     */
    @Override
    public int updateWmsOutWarehouseApplyDetail(WmsOutWarehouseApplyDetail wmsOutWarehouseApplyDetail)
    {
        return wmsOutWarehouseApplyDetailMapper.updateWmsOutWarehouseApplyDetail(wmsOutWarehouseApplyDetail);
    }

    /**
     * 批量删除出库申请明细
     * 
     * @param ids 需要删除的出库申请明细ID
     * @return 结果
     */
    @Override
    public int deleteWmsOutWarehouseApplyDetailByIds(Long[] ids)
    {
        return wmsOutWarehouseApplyDetailMapper.deleteWmsOutWarehouseApplyDetailByIds(ids);
    }

    /**
     * 删除出库申请明细信息
     * 
     * @param id 出库申请明细ID
     * @return 结果
     */
    @Override
    public int deleteWmsOutWarehouseApplyDetailById(Long id)
    {
        return wmsOutWarehouseApplyDetailMapper.deleteWmsOutWarehouseApplyDetailById(id);
    }
}
