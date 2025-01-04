package com.neu.carbon.mes.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.neu.carbon.mes.mapper.MesProcessModelMapper;
import com.neu.carbon.mes.domain.MesProcessModel;
import com.neu.carbon.mes.service.IMesProcessModelService;

/**
 * 工艺建模Service业务层处理
 * 
 * @author neuedu
 * @date 2022-07-11
 */
@Service
public class MesProcessModelServiceImpl implements IMesProcessModelService 
{
    @Autowired
    private MesProcessModelMapper mesProcessModelMapper;

    /**
     * 查询工艺建模
     * 
     * @param id 工艺建模ID
     * @return 工艺建模
     */
    @Override
    public MesProcessModel selectMesProcessModelById(Long id)
    {
        return mesProcessModelMapper.selectMesProcessModelById(id);
    }

    /**
     * 查询工艺建模列表
     * 
     * @param mesProcessModel 工艺建模
     * @return 工艺建模
     */
    @Override
    public List<MesProcessModel> selectMesProcessModelList(MesProcessModel mesProcessModel)
    {
        return mesProcessModelMapper.selectMesProcessModelList(mesProcessModel);
    }

    /**
     * 新增工艺建模
     * 
     * @param mesProcessModel 工艺建模
     * @return 结果
     */
    @Override
    public int insertMesProcessModel(MesProcessModel mesProcessModel)
    {
        return mesProcessModelMapper.insertMesProcessModel(mesProcessModel);
    }

    /**
     * 修改工艺建模
     * 
     * @param mesProcessModel 工艺建模
     * @return 结果
     */
    @Override
    public int updateMesProcessModel(MesProcessModel mesProcessModel)
    {
        return mesProcessModelMapper.updateMesProcessModel(mesProcessModel);
    }

    /**
     * 批量删除工艺建模
     * 
     * @param ids 需要删除的工艺建模ID
     * @return 结果
     */
    @Override
    public int deleteMesProcessModelByIds(Long[] ids)
    {
        return mesProcessModelMapper.deleteMesProcessModelByIds(ids);
    }

    /**
     * 删除工艺建模信息
     * 
     * @param id 工艺建模ID
     * @return 结果
     */
    @Override
    public int deleteMesProcessModelById(Long id)
    {
        return mesProcessModelMapper.deleteMesProcessModelById(id);
    }
}
