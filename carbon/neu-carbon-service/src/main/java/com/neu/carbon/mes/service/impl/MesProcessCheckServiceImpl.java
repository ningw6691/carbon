package com.neu.carbon.mes.service.impl;

import com.neu.carbon.mes.domain.MesProcessCheck;
import com.neu.carbon.mes.domain.MesProductJob;
import com.neu.carbon.mes.domain.MesProductSchedule;
import com.neu.carbon.mes.mapper.MesProcessCheckMapper;
import com.neu.carbon.mes.mapper.MesProductJobMapper;
import com.neu.carbon.mes.mapper.MesProductScheduleMapper;
import com.neu.carbon.mes.service.IMesProcessCheckService;
import com.neu.common.enums.SerialNoPrefix;
import com.neu.smty.utils.CodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 生产采样单Service业务层处理
 *
 * @author neuedu
 * @date 2022-07-12
 */
@Service
public class MesProcessCheckServiceImpl implements IMesProcessCheckService {
    @Autowired
    private MesProcessCheckMapper mesProcessCheckMapper;
    @Autowired
    private MesProductScheduleMapper mesProductScheduleMapper;
    @Autowired
    private MesProductJobMapper mesProductJobMapper;

    /**
     * 查询生产采样单
     *
     * @param id 生产采样单ID
     * @return 生产采样单
     */
    @Override
    public MesProcessCheck selectMesProcessCheckById(Long id) {
        return mesProcessCheckMapper.selectMesProcessCheckById(id);
    }

    /**
     * 查询生产采样单列表
     *
     * @param mesProcessCheck 生产采样单
     * @return 生产采样单
     */
    @Override
    public List<MesProcessCheck> selectMesProcessCheckList(MesProcessCheck mesProcessCheck) {
        return mesProcessCheckMapper.selectMesProcessCheckList(mesProcessCheck);
    }

    /**
     * 新增生产采样单
     *
     * @param mesProcessCheck 生产采样单
     * @return 结果
     */
    @Override
    public int insertMesProcessCheck(MesProcessCheck mesProcessCheck) {
        mesProcessCheck.setSerialNo(CodeUtil.getSerialNo(SerialNoPrefix.SCJY));
        mesProcessCheck.setCheckDate(new Date());
        MesProductJob productJob = mesProductJobMapper.selectMesProductJobById(mesProcessCheck.getProductJobId());
        if (productJob != null) {
            mesProcessCheck.setProductPlanId(productJob.getPlanId());
            mesProcessCheck.setProductScheduleId(productJob.getScheduleId());
            MesProductSchedule productSchedule = mesProductScheduleMapper.selectMesProductScheduleById(productJob.getScheduleId());
            if (productSchedule != null) {
                mesProcessCheck.setProductLineId(productSchedule.getProductLineId());
            }
        }
        return mesProcessCheckMapper.insertMesProcessCheck(mesProcessCheck);
    }

    /**
     * 修改生产采样单
     *
     * @param mesProcessCheck 生产采样单
     * @return 结果
     */
    @Override
    public int updateMesProcessCheck(MesProcessCheck mesProcessCheck) {
        MesProductJob productJob = mesProductJobMapper.selectMesProductJobById(mesProcessCheck.getProductJobId());
        if (productJob != null) {
            mesProcessCheck.setProductPlanId(productJob.getPlanId());
            mesProcessCheck.setProductScheduleId(productJob.getScheduleId());
            MesProductSchedule productSchedule = mesProductScheduleMapper.selectMesProductScheduleById(productJob.getScheduleId());
            if (productSchedule != null) {
                mesProcessCheck.setProductLineId(productSchedule.getProductLineId());
            }
        }
        return mesProcessCheckMapper.updateMesProcessCheck(mesProcessCheck);
    }

    /**
     * 批量删除生产采样单
     *
     * @param ids 需要删除的生产采样单ID
     * @return 结果
     */
    @Override
    public int deleteMesProcessCheckByIds(Long[] ids) {
        return mesProcessCheckMapper.deleteMesProcessCheckByIds(ids);
    }

    /**
     * 删除生产采样单信息
     *
     * @param id 生产采样单ID
     * @return 结果
     */
    @Override
    public int deleteMesProcessCheckById(Long id) {
        return mesProcessCheckMapper.deleteMesProcessCheckById(id);
    }
}
