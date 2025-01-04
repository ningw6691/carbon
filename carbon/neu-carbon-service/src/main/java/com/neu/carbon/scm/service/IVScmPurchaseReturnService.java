package com.neu.carbon.scm.service;

import java.util.List;
import com.neu.carbon.scm.domain.VScmPurchaseReturn;

/**
 * 采购退货Service接口
 * 
 * @author neuedu
 * @date 2022-07-25
 */
public interface IVScmPurchaseReturnService 
{
    /**
     * 查询采购退货
     * 
     * @param materialId 采购退货ID
     * @return 采购退货
     */
    public VScmPurchaseReturn selectVScmPurchaseReturnById(Long materialId);

    /**
     * 查询采购退货列表
     * 
     * @param vScmPurchaseReturn 采购退货
     * @return 采购退货集合
     */
    public List<VScmPurchaseReturn> selectVScmPurchaseReturnList(VScmPurchaseReturn vScmPurchaseReturn);

    /**
     * 新增采购退货
     * 
     * @param vScmPurchaseReturn 采购退货
     * @return 结果
     */
    public int insertVScmPurchaseReturn(VScmPurchaseReturn vScmPurchaseReturn);

    /**
     * 修改采购退货
     * 
     * @param vScmPurchaseReturn 采购退货
     * @return 结果
     */
    public int updateVScmPurchaseReturn(VScmPurchaseReturn vScmPurchaseReturn);

    /**
     * 批量删除采购退货
     * 
     * @param materialIds 需要删除的采购退货ID
     * @return 结果
     */
    public int deleteVScmPurchaseReturnByIds(Long[] materialIds);

    /**
     * 删除采购退货信息
     * 
     * @param materialId 采购退货ID
     * @return 结果
     */
    public int deleteVScmPurchaseReturnById(Long materialId);
}
