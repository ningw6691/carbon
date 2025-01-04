package com.neu.carbon.mes.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

import com.neu.common.enums.SerialNoPrefix;
import com.neu.common.utils.StringUtils;
import com.neu.smty.utils.CodeUtil;

import org.springframework.transaction.annotation.Transactional;
import com.neu.carbon.mes.domain.MesProductScheduleMaterial;
import com.neu.carbon.mes.mapper.MesProductScheduleMapper;
import com.neu.carbon.mes.domain.MesProductSchedule;
import com.neu.carbon.mes.service.IMesProductScheduleService;

/**
 * 计划排产Service业务层处理
 * 
 * @author neuedu
 * @date 2022-07-13
 */
@Service
public class MesProductScheduleServiceImpl implements IMesProductScheduleService 
{
    @Autowired
    private MesProductScheduleMapper mesProductScheduleMapper;

    /**
     * 查询计划排产
     * 
     * @param id 计划排产ID
     * @return 计划排产
     */
    @Override
    public MesProductSchedule selectMesProductScheduleById(Long id)
    {
        return mesProductScheduleMapper.selectMesProductScheduleById(id);
    }

    /**
     * 查询计划排产列表
     * 
     * @param mesProductSchedule 计划排产
     * @return 计划排产
     */
    @Override
    public List<MesProductSchedule> selectMesProductScheduleList(MesProductSchedule mesProductSchedule)
    {
        return mesProductScheduleMapper.selectMesProductScheduleList(mesProductSchedule);
    }

    /**
     * 新增计划排产
     * 
     * @param mesProductSchedule 计划排产
     * @return 结果
     */
    @Transactional
    @Override
    public int insertMesProductSchedule(MesProductSchedule mesProductSchedule)
    {
    	mesProductSchedule.setSerialNo(CodeUtil.getSerialNo(SerialNoPrefix.JHPC));
        int rows = mesProductScheduleMapper.insertMesProductSchedule(mesProductSchedule);
        insertMesProductScheduleMaterial(mesProductSchedule);
        return rows;
    }

    /**
     * 修改计划排产
     * 
     * @param mesProductSchedule 计划排产
     * @return 结果
     */
    @Transactional
    @Override
    public int updateMesProductSchedule(MesProductSchedule mesProductSchedule)
    {
    	//下发排产不能修改子表
    	mesProductScheduleMapper.deleteMesProductScheduleMaterialByScheduleId(mesProductSchedule.getId());
        insertMesProductScheduleMaterial(mesProductSchedule);
        return mesProductScheduleMapper.updateMesProductSchedule(mesProductSchedule);
    }
    
    @Override
    public int updateMesProductScheduleStatus(MesProductSchedule mesProductSchedule) {
    	return mesProductScheduleMapper.updateMesProductSchedule(mesProductSchedule);
    }

    /**
     * 批量删除计划排产
     * 
     * @param ids 需要删除的计划排产ID
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteMesProductScheduleByIds(Long[] ids)
    {
        mesProductScheduleMapper.deleteMesProductScheduleMaterialByScheduleIds(ids);
        return mesProductScheduleMapper.deleteMesProductScheduleByIds(ids);
    }

    /**
     * 删除计划排产信息
     * 
     * @param id 计划排产ID
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteMesProductScheduleById(Long id)
    {
        mesProductScheduleMapper.deleteMesProductScheduleMaterialByScheduleId(id);
        return mesProductScheduleMapper.deleteMesProductScheduleById(id);
    }

    /**
     * 新增排产物料信息信息
     * 
     * @param mesProductSchedule 计划排产对象
     */
    public void insertMesProductScheduleMaterial(MesProductSchedule mesProductSchedule)
    {
        List<MesProductScheduleMaterial> mesProductScheduleMaterialList = mesProductSchedule.getMesProductScheduleMaterialList();
        Long id = mesProductSchedule.getId();
        if (StringUtils.isNotNull(mesProductScheduleMaterialList))
        {
            List<MesProductScheduleMaterial> list = new ArrayList<MesProductScheduleMaterial>();
            for (MesProductScheduleMaterial mesProductScheduleMaterial : mesProductScheduleMaterialList)
            {
                mesProductScheduleMaterial.setScheduleId(id);
                list.add(mesProductScheduleMaterial);
            }
            if (list.size() > 0)
            {
                mesProductScheduleMapper.batchMesProductScheduleMaterial(list);
            }
        }
    }
}
