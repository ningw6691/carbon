package com.neu.carbon.mes.service;

import java.util.List;
import com.neu.carbon.mes.domain.MesProcessModel;

/**
 * 工艺建模Service接口
 * 
 * @author neuedu
 * @date 2022-07-11
 */
public interface IMesProcessModelService 
{
    /**
     * 查询工艺建模
     * 
     * @param id 工艺建模ID
     * @return 工艺建模
     */
    public MesProcessModel selectMesProcessModelById(Long id);

    /**
     * 查询工艺建模列表
     * 
     * @param mesProcessModel 工艺建模
     * @return 工艺建模集合
     */
    public List<MesProcessModel> selectMesProcessModelList(MesProcessModel mesProcessModel);

    /**
     * 新增工艺建模
     * 
     * @param mesProcessModel 工艺建模
     * @return 结果
     */
    public int insertMesProcessModel(MesProcessModel mesProcessModel);

    /**
     * 修改工艺建模
     * 
     * @param mesProcessModel 工艺建模
     * @return 结果
     */
    public int updateMesProcessModel(MesProcessModel mesProcessModel);

    /**
     * 批量删除工艺建模
     * 
     * @param ids 需要删除的工艺建模ID
     * @return 结果
     */
    public int deleteMesProcessModelByIds(Long[] ids);

    /**
     * 删除工艺建模信息
     * 
     * @param id 工艺建模ID
     * @return 结果
     */
    public int deleteMesProcessModelById(Long id);
}
