package com.neu.carbon.mes.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.neu.carbon.mes.mapper.MesProductJobMaterialMapper;
import com.neu.carbon.mes.domain.MesProductJobMaterial;
import com.neu.carbon.mes.service.IMesProductJobMaterialService;

/**
 * 生产作业物料Service业务层处理
 * 
 * @author neuedu
 * @date 2022-07-15
 */
@Service
public class MesProductJobMaterialServiceImpl implements IMesProductJobMaterialService 
{
    @Autowired
    private MesProductJobMaterialMapper mesProductJobMaterialMapper;

    /**
     * 查询生产作业物料
     * 
     * @param id 生产作业物料ID
     * @return 生产作业物料
     */
    @Override
    public MesProductJobMaterial selectMesProductJobMaterialById(Long id)
    {
        return mesProductJobMaterialMapper.selectMesProductJobMaterialById(id);
    }

    /**
     * 查询生产作业物料列表
     * 
     * @param mesProductJobMaterial 生产作业物料
     * @return 生产作业物料
     */
    @Override
    public List<MesProductJobMaterial> selectMesProductJobMaterialList(MesProductJobMaterial mesProductJobMaterial)
    {
        return mesProductJobMaterialMapper.selectMesProductJobMaterialList(mesProductJobMaterial);
    }

    /**
     * 新增生产作业物料
     * 
     * @param mesProductJobMaterial 生产作业物料
     * @return 结果
     */
    @Override
    public int insertMesProductJobMaterial(MesProductJobMaterial mesProductJobMaterial)
    {
        return mesProductJobMaterialMapper.insertMesProductJobMaterial(mesProductJobMaterial);
    }

    /**
     * 修改生产作业物料
     * 
     * @param mesProductJobMaterial 生产作业物料
     * @return 结果
     */
    @Override
    public int updateMesProductJobMaterial(MesProductJobMaterial mesProductJobMaterial)
    {
        return mesProductJobMaterialMapper.updateMesProductJobMaterial(mesProductJobMaterial);
    }

    /**
     * 批量删除生产作业物料
     * 
     * @param ids 需要删除的生产作业物料ID
     * @return 结果
     */
    @Override
    public int deleteMesProductJobMaterialByIds(Long[] ids)
    {
        return mesProductJobMaterialMapper.deleteMesProductJobMaterialByIds(ids);
    }

    /**
     * 删除生产作业物料信息
     * 
     * @param id 生产作业物料ID
     * @return 结果
     */
    @Override
    public int deleteMesProductJobMaterialById(Long id)
    {
        return mesProductJobMaterialMapper.deleteMesProductJobMaterialById(id);
    }
}
