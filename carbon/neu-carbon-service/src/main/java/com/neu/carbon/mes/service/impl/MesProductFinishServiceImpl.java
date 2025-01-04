package com.neu.carbon.mes.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.neu.carbon.mes.mapper.MesProductFinishMapper;
import com.neu.carbon.mes.domain.MesProductFinish;
import com.neu.carbon.mes.service.IMesProductFinishService;

/**
 * 生产完工单Service业务层处理
 * 
 * @author neuedu
 * @date 2022-07-17
 */
@Service
public class MesProductFinishServiceImpl implements IMesProductFinishService 
{
    @Autowired
    private MesProductFinishMapper mesProductFinishMapper;

    /**
     * 查询生产完工单
     * 
     * @param id 生产完工单ID
     * @return 生产完工单
     */
    @Override
    public MesProductFinish selectMesProductFinishById(Long id)
    {
        return mesProductFinishMapper.selectMesProductFinishById(id);
    }

    /**
     * 查询生产完工单列表
     * 
     * @param mesProductFinish 生产完工单
     * @return 生产完工单
     */
    @Override
    public List<MesProductFinish> selectMesProductFinishList(MesProductFinish mesProductFinish)
    {
        return mesProductFinishMapper.selectMesProductFinishList(mesProductFinish);
    }

    /**
     * 新增生产完工单
     * 
     * @param mesProductFinish 生产完工单
     * @return 结果
     */
    @Override
    public int insertMesProductFinish(MesProductFinish mesProductFinish)
    {
        return mesProductFinishMapper.insertMesProductFinish(mesProductFinish);
    }

    /**
     * 修改生产完工单
     * 
     * @param mesProductFinish 生产完工单
     * @return 结果
     */
    @Override
    public int updateMesProductFinish(MesProductFinish mesProductFinish)
    {
        return mesProductFinishMapper.updateMesProductFinish(mesProductFinish);
    }

    /**
     * 批量删除生产完工单
     * 
     * @param ids 需要删除的生产完工单ID
     * @return 结果
     */
    @Override
    public int deleteMesProductFinishByIds(Long[] ids)
    {
        return mesProductFinishMapper.deleteMesProductFinishByIds(ids);
    }

    /**
     * 删除生产完工单信息
     * 
     * @param id 生产完工单ID
     * @return 结果
     */
    @Override
    public int deleteMesProductFinishById(Long id)
    {
        return mesProductFinishMapper.deleteMesProductFinishById(id);
    }
}
