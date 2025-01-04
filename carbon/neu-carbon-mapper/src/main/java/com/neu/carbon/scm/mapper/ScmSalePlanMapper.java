package com.neu.carbon.scm.mapper;

import java.util.List;
import com.neu.carbon.scm.domain.ScmSalePlan;
import com.neu.carbon.scm.domain.ScmSalePlanDetail;

/**
 * 销售计划Mapper接口
 * 
 * @author neuedu
 * @date 2022-07-13
 */
public interface ScmSalePlanMapper 
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
     * 删除销售计划
     * 
     * @param id 销售计划ID
     * @return 结果
     */
    public int deleteScmSalePlanById(Long id);

    /**
     * 批量删除销售计划
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteScmSalePlanByIds(Long[] ids);

    /**
     * 批量删除指标配置
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteScmSalePlanDetailByPlanIds(Long[] ids);
    
    /**
     * 批量新增指标配置
     * 
     * @param scmSalePlanDetailList 指标配置列表
     * @return 结果
     */
    public int batchScmSalePlanDetail(List<ScmSalePlanDetail> scmSalePlanDetailList);
    

    /**
     * 通过销售计划ID删除指标配置信息
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
    public int deleteScmSalePlanDetailByPlanId(Long id);
}
