package com.neu.carbon.mes.service;

import java.util.List;
import com.neu.carbon.mes.domain.MesProductJob;

/**
 * 生产作业Service接口
 * 
 * @author neuedu
 * @date 2022-07-15
 */
public interface IMesProductJobService 
{
    /**
     * 查询生产作业
     * 
     * @param id 生产作业ID
     * @return 生产作业
     */
    public MesProductJob selectMesProductJobById(Long id);

    /**
     * 查询生产作业列表
     * 
     * @param mesProductJob 生产作业
     * @return 生产作业集合
     */
    public List<MesProductJob> selectMesProductJobList(MesProductJob mesProductJob);

    /**
     * 新增生产作业
     * 
     * @param mesProductJob 生产作业
     * @return 结果
     */
    public int insertMesProductJob(MesProductJob mesProductJob);

    /**
     * 修改生产作业
     * 
     * @param mesProductJob 生产作业
     * @return 结果
     */
    public int updateMesProductJob(MesProductJob mesProductJob);

    /**
     * 批量删除生产作业
     * 
     * @param ids 需要删除的生产作业ID
     * @return 结果
     */
    public int deleteMesProductJobByIds(Long[] ids);

    /**
     * 删除生产作业信息
     * 
     * @param id 生产作业ID
     * @return 结果
     */
    public int deleteMesProductJobById(Long id);
    
    /**
     * 修改生产作业状态
     * @param mesProductJob
     * @return
     */
    public int updateProductJobStatus(MesProductJob mesProductJob);
}
