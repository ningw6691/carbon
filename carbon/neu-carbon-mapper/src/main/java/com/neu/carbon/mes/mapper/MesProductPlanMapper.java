package com.neu.carbon.mes.mapper;

import java.util.List;
import com.neu.carbon.mes.domain.MesProductPlan;
import com.neu.carbon.mes.domain.MesProductPlanDetail;

/**
 * 生产计划Mapper接口
 * 
 * @author neuedu
 * @date 2022-07-12
 */
public interface MesProductPlanMapper 
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
     * 删除生产计划
     * 
     * @param id 生产计划ID
     * @return 结果
     */
    public int deleteMesProductPlanById(Long id);

    /**
     * 批量删除生产计划
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteMesProductPlanByIds(Long[] ids);

    /**
     * 批量删除生产计划明细
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteMesProductPlanDetailByPlanIds(Long[] ids);
    
    /**
     * 批量新增生产计划明细
     * 
     * @param mesProductPlanDetailList 生产计划明细列表
     * @return 结果
     */
    public int batchMesProductPlanDetail(List<MesProductPlanDetail> mesProductPlanDetailList);
    

    /**
     * 通过生产计划ID删除生产计划明细信息
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
    public int deleteMesProductPlanDetailByPlanId(Long id);
}
