package com.neu.carbon.scm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.neu.carbon.scm.mapper.VScmSaleDeliveryDetailMapper;
import com.neu.carbon.scm.domain.VScmSaleDeliveryDetail;
import com.neu.carbon.scm.service.IVScmSaleDeliveryDetailService;

/**
 * 销售发货退货明细报表Service业务层处理
 * 
 * @author neuedu
 * @date 2022-08-02
 */
@Service
public class VScmSaleDeliveryDetailServiceImpl implements IVScmSaleDeliveryDetailService 
{
    @Autowired
    private VScmSaleDeliveryDetailMapper vScmSaleDeliveryDetailMapper;

    /**
     * 查询销售发货退货明细报表
     * 
     * @param contractNo 销售发货退货明细报表ID
     * @return 销售发货退货明细报表
     */
    @Override
    public VScmSaleDeliveryDetail selectVScmSaleDeliveryDetailById(String contractNo)
    {
        return vScmSaleDeliveryDetailMapper.selectVScmSaleDeliveryDetailById(contractNo);
    }

    /**
     * 查询销售发货退货明细报表列表
     * 
     * @param vScmSaleDeliveryDetail 销售发货退货明细报表
     * @return 销售发货退货明细报表
     */
    @Override
    public List<VScmSaleDeliveryDetail> selectVScmSaleDeliveryDetailList(VScmSaleDeliveryDetail vScmSaleDeliveryDetail)
    {
        return vScmSaleDeliveryDetailMapper.selectVScmSaleDeliveryDetailList(vScmSaleDeliveryDetail);
    }

    /**
     * 新增销售发货退货明细报表
     * 
     * @param vScmSaleDeliveryDetail 销售发货退货明细报表
     * @return 结果
     */
    @Override
    public int insertVScmSaleDeliveryDetail(VScmSaleDeliveryDetail vScmSaleDeliveryDetail)
    {
        return vScmSaleDeliveryDetailMapper.insertVScmSaleDeliveryDetail(vScmSaleDeliveryDetail);
    }

    /**
     * 修改销售发货退货明细报表
     * 
     * @param vScmSaleDeliveryDetail 销售发货退货明细报表
     * @return 结果
     */
    @Override
    public int updateVScmSaleDeliveryDetail(VScmSaleDeliveryDetail vScmSaleDeliveryDetail)
    {
        return vScmSaleDeliveryDetailMapper.updateVScmSaleDeliveryDetail(vScmSaleDeliveryDetail);
    }

    /**
     * 批量删除销售发货退货明细报表
     * 
     * @param contractNos 需要删除的销售发货退货明细报表ID
     * @return 结果
     */
    @Override
    public int deleteVScmSaleDeliveryDetailByIds(String[] contractNos)
    {
        return vScmSaleDeliveryDetailMapper.deleteVScmSaleDeliveryDetailByIds(contractNos);
    }

    /**
     * 删除销售发货退货明细报表信息
     * 
     * @param contractNo 销售发货退货明细报表ID
     * @return 结果
     */
    @Override
    public int deleteVScmSaleDeliveryDetailById(String contractNo)
    {
        return vScmSaleDeliveryDetailMapper.deleteVScmSaleDeliveryDetailById(contractNo);
    }
}
