package com.neu.carbon.mes.service;

import java.util.List;
import com.neu.carbon.mes.domain.MesProductModel;

/**
 * 产品建模Service接口
 * 
 * @author neuedu
 * @date 2022-07-08
 */
public interface IMesProductModelService 
{
    /**
     * 查询产品建模
     * 
     * @param id 产品建模ID
     * @return 产品建模
     */
    public MesProductModel selectMesProductModelById(Long id);

    /**
     * 查询产品建模列表
     * 
     * @param mesProductModel 产品建模
     * @return 产品建模集合
     */
    public List<MesProductModel> selectMesProductModelList(MesProductModel mesProductModel);

    /**
     * 新增产品建模
     * 
     * @param mesProductModel 产品建模
     * @return 结果
     */
    public int insertMesProductModel(MesProductModel mesProductModel);

    /**
     * 修改产品建模
     * 
     * @param mesProductModel 产品建模
     * @return 结果
     */
    public int updateMesProductModel(MesProductModel mesProductModel);

    /**
     * 批量删除产品建模
     * 
     * @param ids 需要删除的产品建模ID
     * @return 结果
     */
    public int deleteMesProductModelByIds(Long[] ids);

    /**
     * 删除产品建模信息
     * 
     * @param id 产品建模ID
     * @return 结果
     */
    public int deleteMesProductModelById(Long id);
}
