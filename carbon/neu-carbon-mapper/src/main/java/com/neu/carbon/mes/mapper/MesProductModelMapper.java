package com.neu.carbon.mes.mapper;

import java.util.List;
import com.neu.carbon.mes.domain.MesProductModel;
import com.neu.carbon.mes.domain.MesProductModelDetail;

/**
 * 产品建模Mapper接口
 * 
 * @author neuedu
 * @date 2022-07-08
 */
public interface MesProductModelMapper 
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
     * 删除产品建模
     * 
     * @param id 产品建模ID
     * @return 结果
     */
    public int deleteMesProductModelById(Long id);

    /**
     * 批量删除产品建模
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteMesProductModelByIds(Long[] ids);

    /**
     * 批量删除产品模型明细
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteMesProductModelDetailByProductModelIds(Long[] ids);
    
    /**
     * 批量新增产品模型明细
     * 
     * @param mesProductModelDetailList 产品模型明细列表
     * @return 结果
     */
    public int batchMesProductModelDetail(List<MesProductModelDetail> mesProductModelDetailList);
    

    /**
     * 通过产品建模ID删除产品模型明细信息
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
    public int deleteMesProductModelDetailByProductModelId(Long id);
}
