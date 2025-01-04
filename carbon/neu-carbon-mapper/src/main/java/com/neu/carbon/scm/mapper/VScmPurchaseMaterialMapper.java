package com.neu.carbon.scm.mapper;

import com.neu.carbon.scm.domain.RepContractArriveReturnDetail;
import com.neu.carbon.scm.domain.VScmPurchaseMaterial;

import java.util.List;

/**
 * 采购物料Mapper接口
 *
 * @author neuedu
 * @date 2022-07-25
 */
public interface VScmPurchaseMaterialMapper {
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
    public List<RepContractArriveReturnDetail> selectContractArriveReturnMonthReport(RepContractArriveReturnDetail repContractArriveReturnDetail);

    /**
     * 按季度统计采购-入库-退货报表
     *
     * @param repContractArriveReturnDetail 采购-入库-退货报表实体
     * @return 采购-入库-退货报表实体
     */
    public List<RepContractArriveReturnDetail> selectContractArriveReturnQuarterReport(RepContractArriveReturnDetail repContractArriveReturnDetail);


}
