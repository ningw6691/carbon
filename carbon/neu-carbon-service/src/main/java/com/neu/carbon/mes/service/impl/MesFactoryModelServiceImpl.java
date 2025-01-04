package com.neu.carbon.mes.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.neu.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.neu.carbon.mes.domain.MesFactoryModelDetail;
import com.neu.carbon.mes.mapper.MesFactoryModelMapper;
import com.neu.carbon.mes.domain.MesFactoryModel;
import com.neu.carbon.mes.service.IMesFactoryModelService;

/**
 * 工厂建模Service业务层处理
 * 
 * @author neuedu
 * @date 2022-07-07
 */
@Service
public class MesFactoryModelServiceImpl implements IMesFactoryModelService 
{
    @Autowired
    private MesFactoryModelMapper mesFactoryModelMapper;

    /**
     * 查询工厂建模
     * 
     * @param id 工厂建模ID
     * @return 工厂建模
     */
    @Override
    public MesFactoryModel selectMesFactoryModelById(Long id)
    {
        return mesFactoryModelMapper.selectMesFactoryModelById(id);
    }

    /**
     * 查询工厂建模列表
     * 
     * @param mesFactoryModel 工厂建模
     * @return 工厂建模
     */
    @Override
    public List<MesFactoryModel> selectMesFactoryModelList(MesFactoryModel mesFactoryModel)
    {
        return mesFactoryModelMapper.selectMesFactoryModelList(mesFactoryModel);
    }

    /**
     * 新增工厂建模
     * 
     * @param mesFactoryModel 工厂建模
     * @return 结果
     */
    @Transactional
    @Override
    public int insertMesFactoryModel(MesFactoryModel mesFactoryModel)
    {
        int rows = mesFactoryModelMapper.insertMesFactoryModel(mesFactoryModel);
        insertMesFactoryModelDetail(mesFactoryModel);
        return rows;
    }

    /**
     * 修改工厂建模
     * 
     * @param mesFactoryModel 工厂建模
     * @return 结果
     */
    @Transactional
    @Override
    public int updateMesFactoryModel(MesFactoryModel mesFactoryModel)
    {
        mesFactoryModelMapper.deleteMesFactoryModelDetailByProductLineId(mesFactoryModel.getId());
        insertMesFactoryModelDetail(mesFactoryModel);
        return mesFactoryModelMapper.updateMesFactoryModel(mesFactoryModel);
    }

    /**
     * 批量删除工厂建模
     * 
     * @param ids 需要删除的工厂建模ID
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteMesFactoryModelByIds(Long[] ids)
    {
        mesFactoryModelMapper.deleteMesFactoryModelDetailByProductLineIds(ids);
        return mesFactoryModelMapper.deleteMesFactoryModelByIds(ids);
    }

    /**
     * 删除工厂建模信息
     * 
     * @param id 工厂建模ID
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteMesFactoryModelById(Long id)
    {
        mesFactoryModelMapper.deleteMesFactoryModelDetailByProductLineId(id);
        return mesFactoryModelMapper.deleteMesFactoryModelById(id);
    }

    /**
     * 新增工厂建模明细信息
     * 
     * @param mesFactoryModel 工厂建模对象
     */
    public void insertMesFactoryModelDetail(MesFactoryModel mesFactoryModel)
    {
        List<MesFactoryModelDetail> mesFactoryModelDetailList = mesFactoryModel.getMesFactoryModelDetailList();
        Long id = mesFactoryModel.getId();
        if (StringUtils.isNotNull(mesFactoryModelDetailList))
        {
            List<MesFactoryModelDetail> list = new ArrayList<MesFactoryModelDetail>();
            for (MesFactoryModelDetail mesFactoryModelDetail : mesFactoryModelDetailList)
            {
                mesFactoryModelDetail.setProductLineId(id);
                list.add(mesFactoryModelDetail);
            }
            if (list.size() > 0)
            {
                mesFactoryModelMapper.batchMesFactoryModelDetail(list);
            }
        }
    }
}
