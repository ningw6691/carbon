package com.neu.carbon.mes.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.neu.carbon.mes.mapper.MesCheckStandardMapper;
import com.neu.carbon.mes.domain.MesCheckStandard;
import com.neu.carbon.mes.service.IMesCheckStandardService;

/**
 * 质检标准Service业务层处理
 * 
 * @author neuedu
 * @date 2022-07-12
 */
@Service
public class MesCheckStandardServiceImpl implements IMesCheckStandardService 
{
    @Autowired
    private MesCheckStandardMapper mesCheckStandardMapper;

    /**
     * 查询质检标准
     * 
     * @param id 质检标准ID
     * @return 质检标准
     */
    @Override
    public MesCheckStandard selectMesCheckStandardById(Long id)
    {
        return mesCheckStandardMapper.selectMesCheckStandardById(id);
    }

    /**
     * 查询质检标准列表
     * 
     * @param mesCheckStandard 质检标准
     * @return 质检标准
     */
    @Override
    public List<MesCheckStandard> selectMesCheckStandardList(MesCheckStandard mesCheckStandard)
    {
        return mesCheckStandardMapper.selectMesCheckStandardList(mesCheckStandard);
    }

    /**
     * 新增质检标准
     * 
     * @param mesCheckStandard 质检标准
     * @return 结果
     */
    @Override
    public int insertMesCheckStandard(MesCheckStandard mesCheckStandard)
    {
        return mesCheckStandardMapper.insertMesCheckStandard(mesCheckStandard);
    }

    /**
     * 修改质检标准
     * 
     * @param mesCheckStandard 质检标准
     * @return 结果
     */
    @Override
    public int updateMesCheckStandard(MesCheckStandard mesCheckStandard)
    {
        return mesCheckStandardMapper.updateMesCheckStandard(mesCheckStandard);
    }

    /**
     * 批量删除质检标准
     * 
     * @param ids 需要删除的质检标准ID
     * @return 结果
     */
    @Override
    public int deleteMesCheckStandardByIds(Long[] ids)
    {
        return mesCheckStandardMapper.deleteMesCheckStandardByIds(ids);
    }

    /**
     * 删除质检标准信息
     * 
     * @param id 质检标准ID
     * @return 结果
     */
    @Override
    public int deleteMesCheckStandardById(Long id)
    {
        return mesCheckStandardMapper.deleteMesCheckStandardById(id);
    }
}
