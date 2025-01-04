package com.neu.carbon.mes.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.neu.carbon.mes.mapper.MesProductScheduleMaterialMapper;
import com.neu.carbon.mes.domain.MesProductScheduleMaterial;
import com.neu.carbon.mes.service.IMesProductScheduleMaterialService;

/**
 * 排产物料信息Service业务层处理
 * 
 * @author neuedu
 * @date 2022-07-13
 */
@Service
public class MesProductScheduleMaterialServiceImpl implements IMesProductScheduleMaterialService 
{
    @Autowired
    private MesProductScheduleMaterialMapper mesProductScheduleMaterialMapper;

    /**
     * 查询排产物料信息
     * 
     * @param id 排产物料信息ID
     * @return 排产物料信息
     */
    @Override
    public MesProductScheduleMaterial selectMesProductScheduleMaterialById(Long id)
    {
        return mesProductScheduleMaterialMapper.selectMesProductScheduleMaterialById(id);
    }

    /**
     * 查询排产物料信息列表
     * 
     * @param mesProductScheduleMaterial 排产物料信息
     * @return 排产物料信息
     */
    @Override
    public List<MesProductScheduleMaterial> selectMesProductScheduleMaterialList(MesProductScheduleMaterial mesProductScheduleMaterial)
    {
        return mesProductScheduleMaterialMapper.selectMesProductScheduleMaterialList(mesProductScheduleMaterial);
    }

    /**
     * 新增排产物料信息
     * 
     * @param mesProductScheduleMaterial 排产物料信息
     * @return 结果
     */
    @Override
    public int insertMesProductScheduleMaterial(MesProductScheduleMaterial mesProductScheduleMaterial)
    {
        return mesProductScheduleMaterialMapper.insertMesProductScheduleMaterial(mesProductScheduleMaterial);
    }

    /**
     * 修改排产物料信息
     * 
     * @param mesProductScheduleMaterial 排产物料信息
     * @return 结果
     */
    @Override
    public int updateMesProductScheduleMaterial(MesProductScheduleMaterial mesProductScheduleMaterial)
    {
        return mesProductScheduleMaterialMapper.updateMesProductScheduleMaterial(mesProductScheduleMaterial);
    }

    /**
     * 批量删除排产物料信息
     * 
     * @param ids 需要删除的排产物料信息ID
     * @return 结果
     */
    @Override
    public int deleteMesProductScheduleMaterialByIds(Long[] ids)
    {
        return mesProductScheduleMaterialMapper.deleteMesProductScheduleMaterialByIds(ids);
    }

    /**
     * 删除排产物料信息信息
     * 
     * @param id 排产物料信息ID
     * @return 结果
     */
    @Override
    public int deleteMesProductScheduleMaterialById(Long id)
    {
        return mesProductScheduleMaterialMapper.deleteMesProductScheduleMaterialById(id);
    }
}
