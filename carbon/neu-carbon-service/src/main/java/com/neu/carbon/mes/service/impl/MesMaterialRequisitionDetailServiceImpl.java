package com.neu.carbon.mes.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.neu.carbon.mes.mapper.MesMaterialRequisitionDetailMapper;
import com.neu.carbon.mes.domain.MesMaterialRequisitionDetail;
import com.neu.carbon.mes.service.IMesMaterialRequisitionDetailService;

/**
 * 领料单物料明细Service业务层处理
 * 
 * @author neuedu
 * @date 2022-07-14
 */
@Service
public class MesMaterialRequisitionDetailServiceImpl implements IMesMaterialRequisitionDetailService 
{
    @Autowired
    private MesMaterialRequisitionDetailMapper mesMaterialRequisitionDetailMapper;

    /**
     * 查询领料单物料明细
     * 
     * @param id 领料单物料明细ID
     * @return 领料单物料明细
     */
    @Override
    public MesMaterialRequisitionDetail selectMesMaterialRequisitionDetailById(Long id)
    {
        return mesMaterialRequisitionDetailMapper.selectMesMaterialRequisitionDetailById(id);
    }

    /**
     * 查询领料单物料明细列表
     * 
     * @param mesMaterialRequisitionDetail 领料单物料明细
     * @return 领料单物料明细
     */
    @Override
    public List<MesMaterialRequisitionDetail> selectMesMaterialRequisitionDetailList(MesMaterialRequisitionDetail mesMaterialRequisitionDetail)
    {
        return mesMaterialRequisitionDetailMapper.selectMesMaterialRequisitionDetailList(mesMaterialRequisitionDetail);
    }

    /**
     * 新增领料单物料明细
     * 
     * @param mesMaterialRequisitionDetail 领料单物料明细
     * @return 结果
     */
    @Override
    public int insertMesMaterialRequisitionDetail(MesMaterialRequisitionDetail mesMaterialRequisitionDetail)
    {
        return mesMaterialRequisitionDetailMapper.insertMesMaterialRequisitionDetail(mesMaterialRequisitionDetail);
    }

    /**
     * 修改领料单物料明细
     * 
     * @param mesMaterialRequisitionDetail 领料单物料明细
     * @return 结果
     */
    @Override
    public int updateMesMaterialRequisitionDetail(MesMaterialRequisitionDetail mesMaterialRequisitionDetail)
    {
        return mesMaterialRequisitionDetailMapper.updateMesMaterialRequisitionDetail(mesMaterialRequisitionDetail);
    }

    /**
     * 批量删除领料单物料明细
     * 
     * @param ids 需要删除的领料单物料明细ID
     * @return 结果
     */
    @Override
    public int deleteMesMaterialRequisitionDetailByIds(Long[] ids)
    {
        return mesMaterialRequisitionDetailMapper.deleteMesMaterialRequisitionDetailByIds(ids);
    }

    /**
     * 删除领料单物料明细信息
     * 
     * @param id 领料单物料明细ID
     * @return 结果
     */
    @Override
    public int deleteMesMaterialRequisitionDetailById(Long id)
    {
        return mesMaterialRequisitionDetailMapper.deleteMesMaterialRequisitionDetailById(id);
    }
}
