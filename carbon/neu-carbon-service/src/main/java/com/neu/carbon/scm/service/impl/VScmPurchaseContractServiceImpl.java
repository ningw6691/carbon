package com.neu.carbon.scm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.neu.carbon.scm.mapper.VScmPurchaseContractMapper;
import com.neu.carbon.scm.domain.VScmPurchaseContract;
import com.neu.carbon.scm.service.IVScmPurchaseContractService;

/**
 * 采购合同报表Service业务层处理
 * 
 * @author neuedu
 * @date 2022-07-29
 */
@Service
public class VScmPurchaseContractServiceImpl implements IVScmPurchaseContractService 
{
    @Autowired
    private VScmPurchaseContractMapper vScmPurchaseContractMapper;

    /**
     * 查询采购合同报表
     * 
     * @param contractId 采购合同报表ID
     * @return 采购合同报表
     */
    @Override
    public VScmPurchaseContract selectVScmPurchaseContractById(Long contractId)
    {
        return vScmPurchaseContractMapper.selectVScmPurchaseContractById(contractId);
    }

    /**
     * 查询采购合同报表列表
     * 
     * @param vScmPurchaseContract 采购合同报表
     * @return 采购合同报表
     */
    @Override
    public List<VScmPurchaseContract> selectVScmPurchaseContractList(VScmPurchaseContract vScmPurchaseContract)
    {
        return vScmPurchaseContractMapper.selectVScmPurchaseContractList(vScmPurchaseContract);
    }

    /**
     * 按月份统计
     *
     * @param vScmPurchaseContract 发货退货明细报表
     * @return 发货退货明细报表集合
     */
    @Override
    public List<VScmPurchaseContract> selectMonthReport(VScmPurchaseContract vScmPurchaseContract) {
        return vScmPurchaseContractMapper.selectMonthReport(vScmPurchaseContract);
    }

    /**
     * 按季度份统计
     *
     * @param vScmPurchaseContract 发货退货明细报表
     * @return 发货退货明细报表集合
     */
    @Override
    public List<VScmPurchaseContract> selectQuarterReport(VScmPurchaseContract vScmPurchaseContract){
        return vScmPurchaseContractMapper.selectQuarterReport(vScmPurchaseContract);
    }
    /**
     * 按月份统计供应商对账
     *
     * @param vScmPurchaseContract 发货退货明细报表
     * @return 发货退货明细报表集合
     */
    @Override
    public List<VScmPurchaseContract> selectMonthSupplierReport(VScmPurchaseContract vScmPurchaseContract) {
        return vScmPurchaseContractMapper.selectMonthSupplierReport(vScmPurchaseContract);
    }

    /**
     * 按季度统计供应商对账
     *
     * @param vScmPurchaseContract 发货退货明细报表
     * @return 发货退货明细报表集合
     */
    @Override
    public List<VScmPurchaseContract> selectQuarterSupplierReport(VScmPurchaseContract vScmPurchaseContract){
        return vScmPurchaseContractMapper.selectQuarterSupplierReport(vScmPurchaseContract);
    }


}
