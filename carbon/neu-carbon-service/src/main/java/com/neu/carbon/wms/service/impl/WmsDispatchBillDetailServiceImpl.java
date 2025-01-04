package com.neu.carbon.wms.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.neu.carbon.wms.mapper.WmsDispatchBillDetailMapper;
import com.neu.carbon.wms.domain.WmsDispatchBillDetail;
import com.neu.carbon.wms.service.IWmsDispatchBillDetailService;

/**
 * 车辆调度单明细Service业务层处理
 * 
 * @author neuedu
 * @date 2022-07-04
 */
@Service
public class WmsDispatchBillDetailServiceImpl implements IWmsDispatchBillDetailService 
{
    @Autowired
    private WmsDispatchBillDetailMapper wmsDispatchBillDetailMapper;

    /**
     * 查询车辆调度单明细
     * 
     * @param id 车辆调度单明细ID
     * @return 车辆调度单明细
     */
    @Override
    public WmsDispatchBillDetail selectWmsDispatchBillDetailById(Long id)
    {
        return wmsDispatchBillDetailMapper.selectWmsDispatchBillDetailById(id);
    }

    /**
     * 查询车辆调度单明细列表
     * 
     * @param wmsDispatchBillDetail 车辆调度单明细
     * @return 车辆调度单明细
     */
    @Override
    public List<WmsDispatchBillDetail> selectWmsDispatchBillDetailList(WmsDispatchBillDetail wmsDispatchBillDetail)
    {
        return wmsDispatchBillDetailMapper.selectWmsDispatchBillDetailList(wmsDispatchBillDetail);
    }

    /**
     * 新增车辆调度单明细
     * 
     * @param wmsDispatchBillDetail 车辆调度单明细
     * @return 结果
     */
    @Override
    public int insertWmsDispatchBillDetail(WmsDispatchBillDetail wmsDispatchBillDetail)
    {
        return wmsDispatchBillDetailMapper.insertWmsDispatchBillDetail(wmsDispatchBillDetail);
    }

    /**
     * 修改车辆调度单明细
     * 
     * @param wmsDispatchBillDetail 车辆调度单明细
     * @return 结果
     */
    @Override
    public int updateWmsDispatchBillDetail(WmsDispatchBillDetail wmsDispatchBillDetail)
    {
        return wmsDispatchBillDetailMapper.updateWmsDispatchBillDetail(wmsDispatchBillDetail);
    }

    /**
     * 批量删除车辆调度单明细
     * 
     * @param ids 需要删除的车辆调度单明细ID
     * @return 结果
     */
    @Override
    public int deleteWmsDispatchBillDetailByIds(Long[] ids)
    {
        return wmsDispatchBillDetailMapper.deleteWmsDispatchBillDetailByIds(ids);
    }

    /**
     * 删除车辆调度单明细信息
     * 
     * @param id 车辆调度单明细ID
     * @return 结果
     */
    @Override
    public int deleteWmsDispatchBillDetailById(Long id)
    {
        return wmsDispatchBillDetailMapper.deleteWmsDispatchBillDetailById(id);
    }
}
