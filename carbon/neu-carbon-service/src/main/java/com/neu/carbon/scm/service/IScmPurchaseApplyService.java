package com.neu.carbon.scm.service;

import java.util.List;
import com.neu.carbon.scm.domain.ScmPurchaseApply;

/**
 * 采购申请Service接口
 * 
 * @author neuedu
 * @date 2022-06-29
 */
public interface IScmPurchaseApplyService 
{
    /**
     * 查询采购申请
     * 
     * @param id 采购申请ID
     * @return 采购申请
     */
    public ScmPurchaseApply selectScmPurchaseApplyById(Long id);

    /**
     * 查询采购申请列表
     * 
     * @param scmPurchaseApply 采购申请
     * @return 采购申请集合
     */
    public List<ScmPurchaseApply> selectScmPurchaseApplyList(ScmPurchaseApply scmPurchaseApply);

    /**
     * 新增采购申请
     * 
     * @param scmPurchaseApply 采购申请
     * @return 结果
     */
    public int insertScmPurchaseApply(ScmPurchaseApply scmPurchaseApply);

    /**
     * 修改采购申请
     * 
     * @param scmPurchaseApply 采购申请
     * @return 结果
     */
    public int updateScmPurchaseApply(ScmPurchaseApply scmPurchaseApply);

    /**
     * 批量删除采购申请
     * 
     * @param ids 需要删除的采购申请ID
     * @return 结果
     */
    public int deleteScmPurchaseApplyByIds(Long[] ids);

    /**
     * 删除采购申请信息
     * 
     * @param id 采购申请ID
     * @return 结果
     */
    public int deleteScmPurchaseApplyById(Long id);
}
