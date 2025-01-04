package com.neu.carbon.scm.service.impl;

import java.util.List;

import com.neu.carbon.scm.domain.RepContractArriveReturnDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.neu.carbon.scm.mapper.VScmPurchaseMaterialMapper;
import com.neu.carbon.scm.domain.VScmPurchaseMaterial;
import com.neu.carbon.scm.service.IVScmPurchaseMaterialService;

/**
 * 采购物料Service业务层处理
 * 
 * @author neuedu
 * @date 2022-07-25
 */
@Service
public class VScmPurchaseMaterialServiceImpl implements IVScmPurchaseMaterialService 
{
    @Autowired
    private VScmPurchaseMaterialMapper vScmPurchaseMaterialMapper;

    /**
     * 查询采购物料列表
     * 
     * @param vScmPurchaseMaterial 采购物料
     * @return 采购物料
     */
    @Override
    public List<VScmPurchaseMaterial> selectVScmPurchaseMaterialList(VScmPurchaseMaterial vScmPurchaseMaterial)
    {
        return vScmPurchaseMaterialMapper.selectVScmPurchaseMaterialList(vScmPurchaseMaterial);
    }

    /**
     * 按月份统计采购-入库-退货报表
     *
     * @param repContractArriveReturnDetail 采购-入库-退货报表实体
     * @return 采购-入库-退货报表实体
     */
    @Override
    public List<RepContractArriveReturnDetail> selectContractArriveReturnMonthReport(RepContractArriveReturnDetail repContractArriveReturnDetail) {
        return vScmPurchaseMaterialMapper.selectContractArriveReturnMonthReport(repContractArriveReturnDetail);
    }

    /**
     * 按季度统计采购-入库-退货报表
     *
     * @param repContractArriveReturnDetail 采购-入库-退货报表实体
     * @return 采购-入库-退货报表实体
     */
    @Override
    public List<RepContractArriveReturnDetail> selectContractArriveReturnQuarterReport(RepContractArriveReturnDetail repContractArriveReturnDetail) {
        return vScmPurchaseMaterialMapper.selectContractArriveReturnQuarterReport(repContractArriveReturnDetail);
    }


}
