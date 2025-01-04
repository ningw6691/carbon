package com.neu.carbon.scm.mapper;

import java.util.List;
import com.neu.carbon.scm.domain.ScmPurchasePlan;
import com.neu.carbon.scm.domain.ScmPurchasePlanDetail;

/**
 * 采购计划Mapper接口
 * 
 * @author neuedu
 * @date 2022-06-28
 */
public interface ScmPurchasePlanMapper 
{
    /**
     * 查询采购计划
     * 
     * @param id 采购计划ID
     * @return 采购计划
     */
    public ScmPurchasePlan selectScmPurchasePlanById(Long id);

    /**
     * 查询采购计划列表
     * 
     * @param scmPurchasePlan 采购计划
     * @return 采购计划集合
     */
    public List<ScmPurchasePlan> selectScmPurchasePlanList(ScmPurchasePlan scmPurchasePlan);

    /**
     * 新增采购计划
     * 
     * @param scmPurchasePlan 采购计划
     * @return 结果
     */
    public int insertScmPurchasePlan(ScmPurchasePlan scmPurchasePlan);

    /**
     * 修改采购计划
     * 
     * @param scmPurchasePlan 采购计划
     * @return 结果
     */
    public int updateScmPurchasePlan(ScmPurchasePlan scmPurchasePlan);

    /**
     * 删除采购计划
     * 
     * @param id 采购计划ID
     * @return 结果
     */
    public int deleteScmPurchasePlanById(Long id);

    /**
     * 批量删除采购计划
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteScmPurchasePlanByIds(Long[] ids);

    /**
     * 批量删除采购计划详细
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteScmPurchasePlanDetailByPurchasePlanIds(Long[] ids);
    
    /**
     * 批量新增采购计划详细
     * 
     * @param scmPurchasePlanDetailList 采购计划详细列表
     * @return 结果
     */
    public int batchScmPurchasePlanDetail(List<ScmPurchasePlanDetail> scmPurchasePlanDetailList);
    

    /**
     * 通过采购计划ID删除采购计划详细信息
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
    public int deleteScmPurchasePlanDetailByPurchasePlanId(Long id);
}
