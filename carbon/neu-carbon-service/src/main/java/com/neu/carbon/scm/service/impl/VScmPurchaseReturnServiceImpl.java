package com.neu.carbon.scm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.neu.carbon.scm.mapper.VScmPurchaseReturnMapper;
import com.neu.carbon.scm.domain.VScmPurchaseReturn;
import com.neu.carbon.scm.service.IVScmPurchaseReturnService;

/**
 * 采购退货Service业务层处理
 * 
 * @author neuedu
 * @date 2022-07-25
 */
@Service
public class VScmPurchaseReturnServiceImpl implements IVScmPurchaseReturnService 
{
    @Autowired
    private VScmPurchaseReturnMapper vScmPurchaseReturnMapper;

    /**
     * 查询采购退货
     * 
     * @param materialId 采购退货ID
     * @return 采购退货
     */
    @Override
    public VScmPurchaseReturn selectVScmPurchaseReturnById(Long materialId)
    {
        return vScmPurchaseReturnMapper.selectVScmPurchaseReturnById(materialId);
    }

    /**
     * 查询采购退货列表
     * 
     * @param vScmPurchaseReturn 采购退货
     * @return 采购退货
     */
    @Override
    public List<VScmPurchaseReturn> selectVScmPurchaseReturnList(VScmPurchaseReturn vScmPurchaseReturn)
    {
        return vScmPurchaseReturnMapper.selectVScmPurchaseReturnList(vScmPurchaseReturn);
    }

    /**
     * 新增采购退货
     * 
     * @param vScmPurchaseReturn 采购退货
     * @return 结果
     */
    @Override
    public int insertVScmPurchaseReturn(VScmPurchaseReturn vScmPurchaseReturn)
    {
        return vScmPurchaseReturnMapper.insertVScmPurchaseReturn(vScmPurchaseReturn);
    }

    /**
     * 修改采购退货
     * 
     * @param vScmPurchaseReturn 采购退货
     * @return 结果
     */
    @Override
    public int updateVScmPurchaseReturn(VScmPurchaseReturn vScmPurchaseReturn)
    {
        return vScmPurchaseReturnMapper.updateVScmPurchaseReturn(vScmPurchaseReturn);
    }

    /**
     * 批量删除采购退货
     * 
     * @param materialIds 需要删除的采购退货ID
     * @return 结果
     */
    @Override
    public int deleteVScmPurchaseReturnByIds(Long[] materialIds)
    {
        return vScmPurchaseReturnMapper.deleteVScmPurchaseReturnByIds(materialIds);
    }

    /**
     * 删除采购退货信息
     * 
     * @param materialId 采购退货ID
     * @return 结果
     */
    @Override
    public int deleteVScmPurchaseReturnById(Long materialId)
    {
        return vScmPurchaseReturnMapper.deleteVScmPurchaseReturnById(materialId);
    }
}
