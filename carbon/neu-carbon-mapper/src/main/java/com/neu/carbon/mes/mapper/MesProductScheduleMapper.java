package com.neu.carbon.mes.mapper;

import java.util.List;
import com.neu.carbon.mes.domain.MesProductSchedule;
import com.neu.carbon.mes.domain.MesProductScheduleMaterial;

/**
 * 计划排产Mapper接口
 * 
 * @author neuedu
 * @date 2022-07-13
 */
public interface MesProductScheduleMapper 
{
    /**
     * 查询计划排产
     * 
     * @param id 计划排产ID
     * @return 计划排产
     */
    public MesProductSchedule selectMesProductScheduleById(Long id);

    /**
     * 查询计划排产列表
     * 
     * @param mesProductSchedule 计划排产
     * @return 计划排产集合
     */
    public List<MesProductSchedule> selectMesProductScheduleList(MesProductSchedule mesProductSchedule);

    /**
     * 新增计划排产
     * 
     * @param mesProductSchedule 计划排产
     * @return 结果
     */
    public int insertMesProductSchedule(MesProductSchedule mesProductSchedule);

    /**
     * 修改计划排产
     * 
     * @param mesProductSchedule 计划排产
     * @return 结果
     */
    public int updateMesProductSchedule(MesProductSchedule mesProductSchedule);

    /**
     * 删除计划排产
     * 
     * @param id 计划排产ID
     * @return 结果
     */
    public int deleteMesProductScheduleById(Long id);

    /**
     * 批量删除计划排产
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteMesProductScheduleByIds(Long[] ids);

    /**
     * 批量删除排产物料信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteMesProductScheduleMaterialByScheduleIds(Long[] ids);
    
    /**
     * 批量新增排产物料信息
     * 
     * @param mesProductScheduleMaterialList 排产物料信息列表
     * @return 结果
     */
    public int batchMesProductScheduleMaterial(List<MesProductScheduleMaterial> mesProductScheduleMaterialList);
    

    /**
     * 通过计划排产ID删除排产物料信息信息
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
    public int deleteMesProductScheduleMaterialByScheduleId(Long id);
}
