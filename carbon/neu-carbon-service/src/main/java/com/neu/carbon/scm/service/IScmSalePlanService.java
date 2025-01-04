package com.neu.carbon.scm.service;

import java.util.List;
import com.neu.carbon.scm.domain.ScmSalePlan;

/**
 * 销售计划Service接口
 * 
 * @author neuedu
 * @date 2022-07-13
 */
public interface IScmSalePlanService 
{
    /**
     * 查询销售计划
     * 
     * @param id 销售计划ID
     * @return 销售计划
     */
    public ScmSalePlan selectScmSalePlanById(Long id);

    /**
     * 查询销售计划列表
     * 
     * @param scmSalePlan 销售计划
     * @return 销售计划集合
     */
    public List<ScmSalePlan> selectScmSalePlanList(ScmSalePlan scmSalePlan);

    /**
     * 新增销售计划
     * 
     * @param scmSalePlan 销售计划
     * @return 结果
     */
    public int insertScmSalePlan(ScmSalePlan scmSalePlan);

    /**
     * 修改销售计划
     * 
     * @param scmSalePlan 销售计划
     * @return 结果
     */
    public int updateScmSalePlan(ScmSalePlan scmSalePlan);

    /**
     * 批量删除销售计划
     * 
     * @param ids 需要删除的销售计划ID
     * @return 结果
     */
    public int deleteScmSalePlanByIds(Long[] ids);

    /**
     * 删除销售计划信息
     * 
     * @param id 销售计划ID
     * @return 结果
     */
    public int deleteScmSalePlanById(Long id);
}
