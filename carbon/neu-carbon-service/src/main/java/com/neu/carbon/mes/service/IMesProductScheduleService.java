package com.neu.carbon.mes.service;

import java.util.List;
import com.neu.carbon.mes.domain.MesProductSchedule;

/**
 * 计划排产Service接口
 * 
 * @author neuedu
 * @date 2022-07-13
 */
public interface IMesProductScheduleService 
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
     * 批量删除计划排产
     * 
     * @param ids 需要删除的计划排产ID
     * @return 结果
     */
    public int deleteMesProductScheduleByIds(Long[] ids);

    /**
     * 删除计划排产信息
     * 
     * @param id 计划排产ID
     * @return 结果
     */
    public int deleteMesProductScheduleById(Long id);
    
    /**
     * 更新排产状态
     * @param mesProductSchedule
     * @return
     */
    public int updateMesProductScheduleStatus(MesProductSchedule mesProductSchedule);
}
