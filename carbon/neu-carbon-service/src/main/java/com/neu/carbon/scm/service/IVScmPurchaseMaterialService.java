package com.neu.carbon.scm.service;

import java.util.List;

import com.neu.carbon.scm.domain.RepContractArriveReturnDetail;
import com.neu.carbon.scm.domain.RepContractDeliveryReturnDetail;
import com.neu.carbon.scm.domain.VScmPurchaseMaterial;

/**
 * 采购物料Service接口
 * 
 * @author neuedu
 * @date 2022-07-25
 */
public interface IVScmPurchaseMaterialService 
{
    /**
     * 查询采购物料列表
     * 
     * @param vScmPurchaseMaterial 采购物料
     * @return 采购物料集合
     */
    public List<VScmPurchaseMaterial> selectVScmPurchaseMaterialList(VScmPurchaseMaterial vScmPurchaseMaterial);


    /**
     * 按月份统计采购-入库-退货报表
     *
     * @param repContractArriveReturnDetail 采购-入库-退货报表实体
     * @return 采购-入库-退货报表实体
     */
    List<RepContractArriveReturnDetail> selectContractArriveReturnMonthReport(RepContractArriveReturnDetail repContractArriveReturnDetail);

    /**
     * 按季度统计采购-入库-退货报表
     *
     * @param repContractArriveReturnDetail 采购-入库-退货报表实体
     * @return 采购-入库-退货报表实体
     */
    List<RepContractArriveReturnDetail> selectContractArriveReturnQuarterReport(RepContractArriveReturnDetail repContractArriveReturnDetail);


}
