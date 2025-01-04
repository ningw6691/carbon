package com.neu.carbon.wms.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

import com.neu.common.enums.SerialNoPrefix;
import com.neu.common.utils.StringUtils;
import com.neu.smty.utils.CodeUtil;

import org.springframework.transaction.annotation.Transactional;
import com.neu.carbon.wms.domain.WmsDispatchBillDetail;
import com.neu.carbon.wms.mapper.WmsDispatchBillMapper;
import com.neu.carbon.wms.domain.WmsDispatchBill;
import com.neu.carbon.wms.service.IWmsDispatchBillService;

/**
 * 车辆调度单Service业务层处理
 * 
 * @author neuedu
 * @date 2022-07-04
 */
@Service
public class WmsDispatchBillServiceImpl implements IWmsDispatchBillService 
{
    @Autowired
    private WmsDispatchBillMapper wmsDispatchBillMapper;

    /**
     * 查询车辆调度单
     * 
     * @param id 车辆调度单ID
     * @return 车辆调度单
     */
    @Override
    public WmsDispatchBill selectWmsDispatchBillById(Long id)
    {
        return wmsDispatchBillMapper.selectWmsDispatchBillById(id);
    }

    /**
     * 查询车辆调度单列表
     * 
     * @param wmsDispatchBill 车辆调度单
     * @return 车辆调度单
     */
    @Override
    public List<WmsDispatchBill> selectWmsDispatchBillList(WmsDispatchBill wmsDispatchBill)
    {
        return wmsDispatchBillMapper.selectWmsDispatchBillList(wmsDispatchBill);
    }

    /**
     * 新增车辆调度单
     * 
     * @param wmsDispatchBill 车辆调度单
     * @return 结果
     */
    @Transactional
    @Override
    public int insertWmsDispatchBill(WmsDispatchBill wmsDispatchBill)
    {
    	wmsDispatchBill.setSerialNo(CodeUtil.getSerialNo(SerialNoPrefix.DD));
        int rows = wmsDispatchBillMapper.insertWmsDispatchBill(wmsDispatchBill);
        insertWmsDispatchBillDetail(wmsDispatchBill);
        return rows;
    }

    /**
     * 修改车辆调度单
     * 
     * @param wmsDispatchBill 车辆调度单
     * @return 结果
     */
    @Transactional
    @Override
    public int updateWmsDispatchBill(WmsDispatchBill wmsDispatchBill)
    {
        wmsDispatchBillMapper.deleteWmsDispatchBillDetailByDispatchBillId(wmsDispatchBill.getId());
        insertWmsDispatchBillDetail(wmsDispatchBill);
        return wmsDispatchBillMapper.updateWmsDispatchBill(wmsDispatchBill);
    }

    /**
     * 批量删除车辆调度单
     * 
     * @param ids 需要删除的车辆调度单ID
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteWmsDispatchBillByIds(Long[] ids)
    {
        wmsDispatchBillMapper.deleteWmsDispatchBillDetailByDispatchBillIds(ids);
        return wmsDispatchBillMapper.deleteWmsDispatchBillByIds(ids);
    }

    /**
     * 删除车辆调度单信息
     * 
     * @param id 车辆调度单ID
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteWmsDispatchBillById(Long id)
    {
        wmsDispatchBillMapper.deleteWmsDispatchBillDetailByDispatchBillId(id);
        return wmsDispatchBillMapper.deleteWmsDispatchBillById(id);
    }

    /**
     * 新增车辆调度单明细信息
     * 
     * @param wmsDispatchBill 车辆调度单对象
     */
    public void insertWmsDispatchBillDetail(WmsDispatchBill wmsDispatchBill)
    {
        List<WmsDispatchBillDetail> wmsDispatchBillDetailList = wmsDispatchBill.getWmsDispatchBillDetailList();
        Long id = wmsDispatchBill.getId();
        if (StringUtils.isNotNull(wmsDispatchBillDetailList))
        {
            List<WmsDispatchBillDetail> list = new ArrayList<WmsDispatchBillDetail>();
            for (WmsDispatchBillDetail wmsDispatchBillDetail : wmsDispatchBillDetailList)
            {
                wmsDispatchBillDetail.setDispatchBillId(id);
                list.add(wmsDispatchBillDetail);
            }
            if (list.size() > 0)
            {
                wmsDispatchBillMapper.batchWmsDispatchBillDetail(list);
            }
        }
    }
}
