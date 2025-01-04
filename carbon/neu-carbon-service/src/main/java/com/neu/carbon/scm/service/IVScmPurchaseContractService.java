package com.neu.carbon.scm.service;

import java.util.List;
import com.neu.carbon.scm.domain.VScmPurchaseContract;

/**
 * 采购合同报表Service接口
 * 
 * @author neuedu
 * @date 2022-07-29
 */
public interface IVScmPurchaseContractService 
{
    /**
     * 查询采购合同报表
     * 
     * @param contractId 采购合同报表ID
     * @return 采购合同报表
     */
    public VScmPurchaseContract selectVScmPurchaseContractById(Long contractId);

    /**
     * 查询采购合同报表列表
     * 
     * @param vScmPurchaseContract 采购合同报表
     * @return 采购合同报表集合
     */
    public List<VScmPurchaseContract> selectVScmPurchaseContractList(VScmPurchaseContract vScmPurchaseContract);

    /**
     * 按月份统计
     *
     * @param vScmPurchaseContract 采购合同报表
     * @return 采购合同报表集合
     */
    public List<VScmPurchaseContract> selectMonthReport(VScmPurchaseContract vScmPurchaseContract);

    /**
     * 按季度份统计
     *
     * @param vScmPurchaseContract 采购合同报表
     * @return 采购合同报表集合
     */
    public List<VScmPurchaseContract> selectQuarterReport(VScmPurchaseContract vScmPurchaseContract);


    /**
     * 按月份统计供应商对账
     *
     * @param vScmPurchaseContract 采购合同报表
     * @return 采购合同报表集合
     */
    public List<VScmPurchaseContract> selectMonthSupplierReport(VScmPurchaseContract vScmPurchaseContract);

    /**
     * 按季度统计供应商对账
     *
     * @param vScmPurchaseContract 采购合同报表
     * @return 采购合同报表集合
     */
    public List<VScmPurchaseContract> selectQuarterSupplierReport(VScmPurchaseContract vScmPurchaseContract);
}
