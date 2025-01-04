package com.neu.carbon.mes.mapper;

import java.util.List;
import com.neu.carbon.mes.domain.MesProductJobMaterial;

/**
 * 生产作业物料Mapper接口
 * 
 * @author neuedu
 * @date 2022-07-15
 */
public interface MesProductJobMaterialMapper 
{
    /**
     * 查询生产作业物料
     * 
     * @param id 生产作业物料ID
     * @return 生产作业物料
     */
    public MesProductJobMaterial selectMesProductJobMaterialById(Long id);

    /**
     * 查询生产作业物料列表
     * 
     * @param mesProductJobMaterial 生产作业物料
     * @return 生产作业物料集合
     */
    public List<MesProductJobMaterial> selectMesProductJobMaterialList(MesProductJobMaterial mesProductJobMaterial);

    /**
     * 新增生产作业物料
     * 
     * @param mesProductJobMaterial 生产作业物料
     * @return 结果
     */
    public int insertMesProductJobMaterial(MesProductJobMaterial mesProductJobMaterial);

    /**
     * 修改生产作业物料
     * 
     * @param mesProductJobMaterial 生产作业物料
     * @return 结果
     */
    public int updateMesProductJobMaterial(MesProductJobMaterial mesProductJobMaterial);

    /**
     * 删除生产作业物料
     * 
     * @param id 生产作业物料ID
     * @return 结果
     */
    public int deleteMesProductJobMaterialById(Long id);

    /**
     * 批量删除生产作业物料
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteMesProductJobMaterialByIds(Long[] ids);
}
