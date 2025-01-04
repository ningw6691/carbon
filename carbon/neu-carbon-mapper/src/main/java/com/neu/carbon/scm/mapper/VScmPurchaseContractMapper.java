package com.neu.carbon.scm.mapper;

import java.util.List;
import com.neu.carbon.scm.domain.VScmPurchaseContract;

/**
 * 采购合同报表Mapper接口
 * 
 * @author neuedu
 * @date 2022-07-29
 */
public interface VScmPurchaseContractMapper 
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
     * 采购付款月份统计
     *
     * @param vScmPurchaseContract 发货退货明细报表
     * @return 发货退货明细报表集合
     */
    public List<VScmPurchaseContract> selectMonthReport(VScmPurchaseContract vScmPurchaseContract);


    /**
     * 采购付款季度统计
     *
     * @param vScmPurchaseContract 发货退货明细报表
     * @return 发货退货明细报表集合
     */
    public List<VScmPurchaseContract> selectQuarterReport(VScmPurchaseContract vScmPurchaseContract);

    /**
     * 按月份统计供应商对账
     *
     * @param vScmPurchaseContract 发货退货明细报表
     * @return 发货退货明细报表集合
     */
    public List<VScmPurchaseContract> selectMonthSupplierReport(VScmPurchaseContract vScmPurchaseContract);


    /**
     * 按季度统计供应商对账
     *
     * @param vScmPurchaseContract 发货退货明细报表
     * @return 发货退货明细报表集合
     */
    public List<VScmPurchaseContract> selectQuarterSupplierReport(VScmPurchaseContract vScmPurchaseContract);


}
