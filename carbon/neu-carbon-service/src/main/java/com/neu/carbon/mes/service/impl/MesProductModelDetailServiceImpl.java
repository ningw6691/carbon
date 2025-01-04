package com.neu.carbon.mes.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.neu.carbon.mes.mapper.MesProductModelDetailMapper;
import com.neu.carbon.mes.domain.MesProductModelDetail;
import com.neu.carbon.mes.service.IMesProductModelDetailService;

/**
 * 产品模型明细Service业务层处理
 * 
 * @author neuedu
 * @date 2022-07-08
 */
@Service
public class MesProductModelDetailServiceImpl implements IMesProductModelDetailService 
{
    @Autowired
    private MesProductModelDetailMapper mesProductModelDetailMapper;

    /**
     * 查询产品模型明细
     * 
     * @param id 产品模型明细ID
     * @return 产品模型明细
     */
    @Override
    public MesProductModelDetail selectMesProductModelDetailById(Long id)
    {
        return mesProductModelDetailMapper.selectMesProductModelDetailById(id);
    }

    /**
     * 查询产品模型明细列表
     * 
     * @param mesProductModelDetail 产品模型明细
     * @return 产品模型明细
     */
    @Override
    public List<MesProductModelDetail> selectMesProductModelDetailList(MesProductModelDetail mesProductModelDetail)
    {
        return mesProductModelDetailMapper.selectMesProductModelDetailList(mesProductModelDetail);
    }

    /**
     * 新增产品模型明细
     * 
     * @param mesProductModelDetail 产品模型明细
     * @return 结果
     */
    @Override
    public int insertMesProductModelDetail(MesProductModelDetail mesProductModelDetail)
    {
        return mesProductModelDetailMapper.insertMesProductModelDetail(mesProductModelDetail);
    }

    /**
     * 修改产品模型明细
     * 
     * @param mesProductModelDetail 产品模型明细
     * @return 结果
     */
    @Override
    public int updateMesProductModelDetail(MesProductModelDetail mesProductModelDetail)
    {
        return mesProductModelDetailMapper.updateMesProductModelDetail(mesProductModelDetail);
    }

    /**
     * 批量删除产品模型明细
     * 
     * @param ids 需要删除的产品模型明细ID
     * @return 结果
     */
    @Override
    public int deleteMesProductModelDetailByIds(Long[] ids)
    {
        return mesProductModelDetailMapper.deleteMesProductModelDetailByIds(ids);
    }

    /**
     * 删除产品模型明细信息
     * 
     * @param id 产品模型明细ID
     * @return 结果
     */
    @Override
    public int deleteMesProductModelDetailById(Long id)
    {
        return mesProductModelDetailMapper.deleteMesProductModelDetailById(id);
    }
}
