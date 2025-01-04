package com.neu.carbon.wms.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.neu.carbon.wms.mapper.WmsDeliveryBillDetailMapper;
import com.neu.carbon.wms.domain.WmsDeliveryBillDetail;
import com.neu.carbon.wms.service.IWmsDeliveryBillDetailService;

/**
 * 配送单明细Service业务层处理
 * 
 * @author neuedu
 * @date 2022-07-05
 */
@Service
public class WmsDeliveryBillDetailServiceImpl implements IWmsDeliveryBillDetailService 
{
    @Autowired
    private WmsDeliveryBillDetailMapper wmsDeliveryBillDetailMapper;

    /**
     * 查询配送单明细
     * 
     * @param id 配送单明细ID
     * @return 配送单明细
     */
    @Override
    public WmsDeliveryBillDetail selectWmsDeliveryBillDetailById(Long id)
    {
        return wmsDeliveryBillDetailMapper.selectWmsDeliveryBillDetailById(id);
    }

    /**
     * 查询配送单明细列表
     * 
     * @param wmsDeliveryBillDetail 配送单明细
     * @return 配送单明细
     */
    @Override
    public List<WmsDeliveryBillDetail> selectWmsDeliveryBillDetailList(WmsDeliveryBillDetail wmsDeliveryBillDetail)
    {
        return wmsDeliveryBillDetailMapper.selectWmsDeliveryBillDetailList(wmsDeliveryBillDetail);
    }

    /**
     * 新增配送单明细
     * 
     * @param wmsDeliveryBillDetail 配送单明细
     * @return 结果
     */
    @Override
    public int insertWmsDeliveryBillDetail(WmsDeliveryBillDetail wmsDeliveryBillDetail)
    {
        return wmsDeliveryBillDetailMapper.insertWmsDeliveryBillDetail(wmsDeliveryBillDetail);
    }

    /**
     * 修改配送单明细
     * 
     * @param wmsDeliveryBillDetail 配送单明细
     * @return 结果
     */
    @Override
    public int updateWmsDeliveryBillDetail(WmsDeliveryBillDetail wmsDeliveryBillDetail)
    {
        return wmsDeliveryBillDetailMapper.updateWmsDeliveryBillDetail(wmsDeliveryBillDetail);
    }

    /**
     * 批量删除配送单明细
     * 
     * @param ids 需要删除的配送单明细ID
     * @return 结果
     */
    @Override
    public int deleteWmsDeliveryBillDetailByIds(Long[] ids)
    {
        return wmsDeliveryBillDetailMapper.deleteWmsDeliveryBillDetailByIds(ids);
    }

    /**
     * 删除配送单明细信息
     * 
     * @param id 配送单明细ID
     * @return 结果
     */
    @Override
    public int deleteWmsDeliveryBillDetailById(Long id)
    {
        return wmsDeliveryBillDetailMapper.deleteWmsDeliveryBillDetailById(id);
    }
}
