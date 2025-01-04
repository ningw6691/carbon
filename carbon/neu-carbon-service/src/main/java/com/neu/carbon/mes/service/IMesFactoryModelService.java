package com.neu.carbon.mes.service;

import java.util.List;
import com.neu.carbon.mes.domain.MesFactoryModel;

/**
 * 工厂建模Service接口
 * 
 * @author neuedu
 * @date 2022-07-07
 */
public interface IMesFactoryModelService 
{
    /**
     * 查询工厂建模
     * 
     * @param id 工厂建模ID
     * @return 工厂建模
     */
    public MesFactoryModel selectMesFactoryModelById(Long id);

    /**
     * 查询工厂建模列表
     * 
     * @param mesFactoryModel 工厂建模
     * @return 工厂建模集合
     */
    public List<MesFactoryModel> selectMesFactoryModelList(MesFactoryModel mesFactoryModel);

    /**
     * 新增工厂建模
     * 
     * @param mesFactoryModel 工厂建模
     * @return 结果
     */
    public int insertMesFactoryModel(MesFactoryModel mesFactoryModel);

    /**
     * 修改工厂建模
     * 
     * @param mesFactoryModel 工厂建模
     * @return 结果
     */
    public int updateMesFactoryModel(MesFactoryModel mesFactoryModel);

    /**
     * 批量删除工厂建模
     * 
     * @param ids 需要删除的工厂建模ID
     * @return 结果
     */
    public int deleteMesFactoryModelByIds(Long[] ids);

    /**
     * 删除工厂建模信息
     * 
     * @param id 工厂建模ID
     * @return 结果
     */
    public int deleteMesFactoryModelById(Long id);
}
