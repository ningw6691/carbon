package com.neu.carbon.mes.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.neu.carbon.mes.mapper.MesProductPlanDetailMapper;
import com.neu.carbon.mes.domain.MesProductPlanDetail;
import com.neu.carbon.mes.service.IMesProductPlanDetailService;

/**
 * 生产计划明细Service业务层处理
 * 
 * @author neuedu
 * @date 2022-07-12
 */
@Service
public class MesProductPlanDetailServiceImpl implements IMesProductPlanDetailService 
{
    @Autowired
    private MesProductPlanDetailMapper mesProductPlanDetailMapper;

    /**
     * 查询生产计划明细
     * 
     * @param id 生产计划明细ID
     * @return 生产计划明细
     */
    @Override
    public MesProductPlanDetail selectMesProductPlanDetailById(Long id)
    {
        return mesProductPlanDetailMapper.selectMesProductPlanDetailById(id);
    }

    /**
     * 查询生产计划明细列表
     * 
     * @param mesProductPlanDetail 生产计划明细
     * @return 生产计划明细
     */
    @Override
    public List<MesProductPlanDetail> selectMesProductPlanDetailList(MesProductPlanDetail mesProductPlanDetail)
    {
        return mesProductPlanDetailMapper.selectMesProductPlanDetailList(mesProductPlanDetail);
    }

    /**
     * 新增生产计划明细
     * 
     * @param mesProductPlanDetail 生产计划明细
     * @return 结果
     */
    @Override
    public int insertMesProductPlanDetail(MesProductPlanDetail mesProductPlanDetail)
    {
        return mesProductPlanDetailMapper.insertMesProductPlanDetail(mesProductPlanDetail);
    }

    /**
     * 修改生产计划明细
     * 
     * @param mesProductPlanDetail 生产计划明细
     * @return 结果
     */
    @Override
    public int updateMesProductPlanDetail(MesProductPlanDetail mesProductPlanDetail)
    {
        return mesProductPlanDetailMapper.updateMesProductPlanDetail(mesProductPlanDetail);
    }

    /**
     * 批量删除生产计划明细
     * 
     * @param ids 需要删除的生产计划明细ID
     * @return 结果
     */
    @Override
    public int deleteMesProductPlanDetailByIds(Long[] ids)
    {
        return mesProductPlanDetailMapper.deleteMesProductPlanDetailByIds(ids);
    }

    /**
     * 删除生产计划明细信息
     * 
     * @param id 生产计划明细ID
     * @return 结果
     */
    @Override
    public int deleteMesProductPlanDetailById(Long id)
    {
        return mesProductPlanDetailMapper.deleteMesProductPlanDetailById(id);
    }
}
