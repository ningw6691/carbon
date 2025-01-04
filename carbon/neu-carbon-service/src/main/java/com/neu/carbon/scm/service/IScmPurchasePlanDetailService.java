package com.neu.carbon.scm.service;

import java.util.List;
import com.neu.carbon.scm.domain.ScmPurchasePlanDetail;

/**
 * 采购计划详细Service接口
 * 
 * @author neuedu
 * @date 2022-06-28
 */
public interface IScmPurchasePlanDetailService 
{
    /**
     * 查询采购计划详细
     * 
     * @param id 采购计划详细ID
     * @return 采购计划详细
     */
    public ScmPurchasePlanDetail selectScmPurchasePlanDetailById(Long id);

    /**
     * 查询采购计划详细列表
     * 
     * @param scmPurchasePlanDetail 采购计划详细
     * @return 采购计划详细集合
     */
    public List<ScmPurchasePlanDetail> selectScmPurchasePlanDetailList(ScmPurchasePlanDetail scmPurchasePlanDetail);

    /**
     * 新增采购计划详细
     * 
     * @param scmPurchasePlanDetail 采购计划详细
     * @return 结果
     */
    public int insertScmPurchasePlanDetail(ScmPurchasePlanDetail scmPurchasePlanDetail);

    /**
     * 修改采购计划详细
     * 
     * @param scmPurchasePlanDetail 采购计划详细
     * @return 结果
     */
    public int updateScmPurchasePlanDetail(ScmPurchasePlanDetail scmPurchasePlanDetail);

    /**
     * 批量删除采购计划详细
     * 
     * @param ids 需要删除的采购计划详细ID
     * @return 结果
     */
    public int deleteScmPurchasePlanDetailByIds(Long[] ids);

    /**
     * 删除采购计划详细信息
     * 
     * @param id 采购计划详细ID
     * @return 结果
     */
    public int deleteScmPurchasePlanDetailById(Long id);
}
