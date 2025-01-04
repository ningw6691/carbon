package com.neu.carbon.mes.service;

import java.util.List;
import com.neu.carbon.mes.domain.MesProductPlan;

/**
 * 生产计划Service接口
 * 
 * @author neuedu
 * @date 2022-07-12
 */
public interface IMesProductPlanService 
{
    /**
     * 查询生产计划
     * 
     * @param id 生产计划ID
     * @return 生产计划
     */
    public MesProductPlan selectMesProductPlanById(Long id);

    /**
     * 查询生产计划列表
     * 
     * @param mesProductPlan 生产计划
     * @return 生产计划集合
     */
    public List<MesProductPlan> selectMesProductPlanList(MesProductPlan mesProductPlan);

    /**
     * 新增生产计划
     * 
     * @param mesProductPlan 生产计划
     * @return 结果
     */
    public int insertMesProductPlan(MesProductPlan mesProductPlan);

    /**
     * 修改生产计划
     * 
     * @param mesProductPlan 生产计划
     * @return 结果
     */
    public int updateMesProductPlan(MesProductPlan mesProductPlan);

    /**
     * 批量删除生产计划
     * 
     * @param ids 需要删除的生产计划ID
     * @return 结果
     */
    public int deleteMesProductPlanByIds(Long[] ids);

    /**
     * 删除生产计划信息
     * 
     * @param id 生产计划ID
     * @return 结果
     */
    public int deleteMesProductPlanById(Long id);
}
