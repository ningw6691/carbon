package com.neu.carbon.mes.mapper;

import java.util.List;
import com.neu.carbon.mes.domain.MesProductJob;
import com.neu.carbon.mes.domain.MesProductJobMaterial;

/**
 * 生产作业Mapper接口
 * 
 * @author neuedu
 * @date 2022-07-15
 */
public interface MesProductJobMapper 
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
     * 删除生产作业
     * 
     * @param id 生产作业ID
     * @return 结果
     */
    public int deleteMesProductJobById(Long id);

    /**
     * 批量删除生产作业
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteMesProductJobByIds(Long[] ids);

    /**
     * 批量删除生产作业物料
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteMesProductJobMaterialByJobIds(Long[] ids);
    
    /**
     * 批量新增生产作业物料
     * 
     * @param mesProductJobMaterialList 生产作业物料列表
     * @return 结果
     */
    public int batchMesProductJobMaterial(List<MesProductJobMaterial> mesProductJobMaterialList);
    

    /**
     * 通过生产作业ID删除生产作业物料信息
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
    public int deleteMesProductJobMaterialByJobId(Long id);
}
