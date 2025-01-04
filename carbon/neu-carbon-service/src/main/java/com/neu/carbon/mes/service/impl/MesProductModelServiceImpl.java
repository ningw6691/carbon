package com.neu.carbon.mes.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.neu.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.neu.carbon.mes.domain.MesProductModelDetail;
import com.neu.carbon.mes.mapper.MesProductModelMapper;
import com.neu.carbon.mes.domain.MesProductModel;
import com.neu.carbon.mes.service.IMesProductModelService;

/**
 * 产品建模Service业务层处理
 * 
 * @author neuedu
 * @date 2022-07-08
 */
@Service
public class MesProductModelServiceImpl implements IMesProductModelService 
{
    @Autowired
    private MesProductModelMapper mesProductModelMapper;

    /**
     * 查询产品建模
     * 
     * @param id 产品建模ID
     * @return 产品建模
     */
    @Override
    public MesProductModel selectMesProductModelById(Long id)
    {
        return mesProductModelMapper.selectMesProductModelById(id);
    }

    /**
     * 查询产品建模列表
     * 
     * @param mesProductModel 产品建模
     * @return 产品建模
     */
    @Override
    public List<MesProductModel> selectMesProductModelList(MesProductModel mesProductModel)
    {
        return mesProductModelMapper.selectMesProductModelList(mesProductModel);
    }

    /**
     * 新增产品建模
     * 
     * @param mesProductModel 产品建模
     * @return 结果
     */
    @Transactional
    @Override
    public int insertMesProductModel(MesProductModel mesProductModel)
    {
        int rows = mesProductModelMapper.insertMesProductModel(mesProductModel);
        insertMesProductModelDetail(mesProductModel);
        return rows;
    }

    /**
     * 修改产品建模
     * 
     * @param mesProductModel 产品建模
     * @return 结果
     */
    @Transactional
    @Override
    public int updateMesProductModel(MesProductModel mesProductModel)
    {
        mesProductModelMapper.deleteMesProductModelDetailByProductModelId(mesProductModel.getId());
        insertMesProductModelDetail(mesProductModel);
        return mesProductModelMapper.updateMesProductModel(mesProductModel);
    }

    /**
     * 批量删除产品建模
     * 
     * @param ids 需要删除的产品建模ID
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteMesProductModelByIds(Long[] ids)
    {
        mesProductModelMapper.deleteMesProductModelDetailByProductModelIds(ids);
        return mesProductModelMapper.deleteMesProductModelByIds(ids);
    }

    /**
     * 删除产品建模信息
     * 
     * @param id 产品建模ID
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteMesProductModelById(Long id)
    {
        mesProductModelMapper.deleteMesProductModelDetailByProductModelId(id);
        return mesProductModelMapper.deleteMesProductModelById(id);
    }

    /**
     * 新增产品模型明细信息
     * 
     * @param mesProductModel 产品建模对象
     */
    public void insertMesProductModelDetail(MesProductModel mesProductModel)
    {
        List<MesProductModelDetail> mesProductModelDetailList = mesProductModel.getMesProductModelDetailList();
        Long id = mesProductModel.getId();
        if (StringUtils.isNotNull(mesProductModelDetailList))
        {
            List<MesProductModelDetail> list = new ArrayList<MesProductModelDetail>();
            for (MesProductModelDetail mesProductModelDetail : mesProductModelDetailList)
            {
                mesProductModelDetail.setProductModelId(id);
                list.add(mesProductModelDetail);
            }
            if (list.size() > 0)
            {
                mesProductModelMapper.batchMesProductModelDetail(list);
            }
        }
    }
}
