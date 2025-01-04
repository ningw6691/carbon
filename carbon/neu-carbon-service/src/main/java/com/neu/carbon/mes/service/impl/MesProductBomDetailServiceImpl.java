package com.neu.carbon.mes.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.neu.carbon.mes.mapper.MesProductBomDetailMapper;
import com.neu.carbon.mes.domain.MesProductBomDetail;
import com.neu.carbon.mes.service.IMesProductBomDetailService;

/**
 * BOM单明细Service业务层处理
 * 
 * @author neuedu
 * @date 2022-07-11
 */
@Service
public class MesProductBomDetailServiceImpl implements IMesProductBomDetailService 
{
    @Autowired
    private MesProductBomDetailMapper mesProductBomDetailMapper;

    /**
     * 查询BOM单明细
     * 
     * @param id BOM单明细ID
     * @return BOM单明细
     */
    @Override
    public MesProductBomDetail selectMesProductBomDetailById(Long id)
    {
        return mesProductBomDetailMapper.selectMesProductBomDetailById(id);
    }

    /**
     * 查询BOM单明细列表
     * 
     * @param mesProductBomDetail BOM单明细
     * @return BOM单明细
     */
    @Override
    public List<MesProductBomDetail> selectMesProductBomDetailList(MesProductBomDetail mesProductBomDetail)
    {
        return mesProductBomDetailMapper.selectMesProductBomDetailList(mesProductBomDetail);
    }

    /**
     * 新增BOM单明细
     * 
     * @param mesProductBomDetail BOM单明细
     * @return 结果
     */
    @Override
    public int insertMesProductBomDetail(MesProductBomDetail mesProductBomDetail)
    {
        return mesProductBomDetailMapper.insertMesProductBomDetail(mesProductBomDetail);
    }

    /**
     * 修改BOM单明细
     * 
     * @param mesProductBomDetail BOM单明细
     * @return 结果
     */
    @Override
    public int updateMesProductBomDetail(MesProductBomDetail mesProductBomDetail)
    {
        return mesProductBomDetailMapper.updateMesProductBomDetail(mesProductBomDetail);
    }

    /**
     * 批量删除BOM单明细
     * 
     * @param ids 需要删除的BOM单明细ID
     * @return 结果
     */
    @Override
    public int deleteMesProductBomDetailByIds(Long[] ids)
    {
        return mesProductBomDetailMapper.deleteMesProductBomDetailByIds(ids);
    }

    /**
     * 删除BOM单明细信息
     * 
     * @param id BOM单明细ID
     * @return 结果
     */
    @Override
    public int deleteMesProductBomDetailById(Long id)
    {
        return mesProductBomDetailMapper.deleteMesProductBomDetailById(id);
    }
}
