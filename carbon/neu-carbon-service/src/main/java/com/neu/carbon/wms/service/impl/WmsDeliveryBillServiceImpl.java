package com.neu.carbon.wms.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

import com.neu.common.enums.SerialNoPrefix;
import com.neu.common.utils.StringUtils;
import com.neu.smty.utils.CodeUtil;

import org.springframework.transaction.annotation.Transactional;
import com.neu.carbon.wms.domain.WmsDeliveryBillDetail;
import com.neu.carbon.wms.mapper.WmsDeliveryBillMapper;
import com.neu.carbon.wms.domain.WmsDeliveryBill;
import com.neu.carbon.wms.service.IWmsDeliveryBillService;

/**
 * 配送单Service业务层处理
 * 
 * @author neuedu
 * @date 2022-07-05
 */
@Service
public class WmsDeliveryBillServiceImpl implements IWmsDeliveryBillService 
{
    @Autowired
    private WmsDeliveryBillMapper wmsDeliveryBillMapper;

    /**
     * 查询配送单
     * 
     * @param id 配送单ID
     * @return 配送单
     */
    @Override
    public WmsDeliveryBill selectWmsDeliveryBillById(Long id)
    {
        return wmsDeliveryBillMapper.selectWmsDeliveryBillById(id);
    }

    /**
     * 查询配送单列表
     * 
     * @param wmsDeliveryBill 配送单
     * @return 配送单
     */
    @Override
    public List<WmsDeliveryBill> selectWmsDeliveryBillList(WmsDeliveryBill wmsDeliveryBill)
    {
        return wmsDeliveryBillMapper.selectWmsDeliveryBillList(wmsDeliveryBill);
    }

    /**
     * 新增配送单
     * 
     * @param wmsDeliveryBill 配送单
     * @return 结果
     */
    @Transactional
    @Override
    public int insertWmsDeliveryBill(WmsDeliveryBill wmsDeliveryBill)
    {
    	wmsDeliveryBill.setSerialNo(CodeUtil.getSerialNo(SerialNoPrefix.PS));
    	wmsDeliveryBill.setBillStatus("0");
        int rows = wmsDeliveryBillMapper.insertWmsDeliveryBill(wmsDeliveryBill);
        insertWmsDeliveryBillDetail(wmsDeliveryBill);
        return rows;
    }

    /**
     * 修改配送单
     * 
     * @param wmsDeliveryBill 配送单
     * @return 结果
     */
    @Transactional
    @Override
    public int updateWmsDeliveryBill(WmsDeliveryBill wmsDeliveryBill)
    {
        wmsDeliveryBillMapper.deleteWmsDeliveryBillDetailByDeliveryBillId(wmsDeliveryBill.getId());
        insertWmsDeliveryBillDetail(wmsDeliveryBill);
        return wmsDeliveryBillMapper.updateWmsDeliveryBill(wmsDeliveryBill);
    }

    /**
     * 批量删除配送单
     * 
     * @param ids 需要删除的配送单ID
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteWmsDeliveryBillByIds(Long[] ids)
    {
        wmsDeliveryBillMapper.deleteWmsDeliveryBillDetailByDeliveryBillIds(ids);
        return wmsDeliveryBillMapper.deleteWmsDeliveryBillByIds(ids);
    }

    /**
     * 删除配送单信息
     * 
     * @param id 配送单ID
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteWmsDeliveryBillById(Long id)
    {
        wmsDeliveryBillMapper.deleteWmsDeliveryBillDetailByDeliveryBillId(id);
        return wmsDeliveryBillMapper.deleteWmsDeliveryBillById(id);
    }

    /**
     * 新增配送单明细信息
     * 
     * @param wmsDeliveryBill 配送单对象
     */
    public void insertWmsDeliveryBillDetail(WmsDeliveryBill wmsDeliveryBill)
    {
        List<WmsDeliveryBillDetail> wmsDeliveryBillDetailList = wmsDeliveryBill.getWmsDeliveryBillDetailList();
        Long id = wmsDeliveryBill.getId();
        if (StringUtils.isNotNull(wmsDeliveryBillDetailList))
        {
            List<WmsDeliveryBillDetail> list = new ArrayList<WmsDeliveryBillDetail>();
            for (WmsDeliveryBillDetail wmsDeliveryBillDetail : wmsDeliveryBillDetailList)
            {
                wmsDeliveryBillDetail.setDeliveryBillId(id);
                list.add(wmsDeliveryBillDetail);
            }
            if (list.size() > 0)
            {
                wmsDeliveryBillMapper.batchWmsDeliveryBillDetail(list);
            }
        }
    }
}
