package com.neu.carbon.scm.service;

import java.util.List;
import com.neu.carbon.scm.domain.ScmPurchaseReturn;

/**
 * 采购退货Service接口
 * 
 * @author neuedu
 * @date 2022-07-07
 */
public interface IScmPurchaseReturnService 
{
    /**
     * 查询采购退货
     * 
     * @param id 采购退货ID
     * @return 采购退货
     */
    public ScmPurchaseReturn selectScmPurchaseReturnById(Long id);

    /**
     * 查询采购退货列表
     * 
     * @param scmPurchaseReturn 采购退货
     * @return 采购退货集合
     */
    public List<ScmPurchaseReturn> selectScmPurchaseReturnList(ScmPurchaseReturn scmPurchaseReturn);

    /**
     * 新增采购退货
     * 
     * @param scmPurchaseReturn 采购退货
     * @return 结果
     */
    public int insertScmPurchaseReturn(ScmPurchaseReturn scmPurchaseReturn);

    /**
     * 修改采购退货
     * 
     * @param scmPurchaseReturn 采购退货
     * @return 结果
     */
    public int updateScmPurchaseReturn(ScmPurchaseReturn scmPurchaseReturn);

    /**
     * 批量删除采购退货
     * 
     * @param ids 需要删除的采购退货ID
     * @return 结果
     */
    public int deleteScmPurchaseReturnByIds(Long[] ids);

    /**
     * 删除采购退货信息
     * 
     * @param id 采购退货ID
     * @return 结果
     */
    public int deleteScmPurchaseReturnById(Long id);
}
