package com.neu.carbon.mes.mapper;

import java.util.List;
import com.neu.carbon.mes.domain.MesFactoryModel;
import com.neu.carbon.mes.domain.MesFactoryModelDetail;

/**
 * 工厂建模Mapper接口
 * 
 * @author neuedu
 * @date 2022-07-07
 */
public interface MesFactoryModelMapper 
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
     * 删除工厂建模
     * 
     * @param id 工厂建模ID
     * @return 结果
     */
    public int deleteMesFactoryModelById(Long id);

    /**
     * 批量删除工厂建模
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteMesFactoryModelByIds(Long[] ids);

    /**
     * 批量删除工厂建模明细
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteMesFactoryModelDetailByProductLineIds(Long[] ids);
    
    /**
     * 批量新增工厂建模明细
     * 
     * @param mesFactoryModelDetailList 工厂建模明细列表
     * @return 结果
     */
    public int batchMesFactoryModelDetail(List<MesFactoryModelDetail> mesFactoryModelDetailList);
    

    /**
     * 通过工厂建模ID删除工厂建模明细信息
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
    public int deleteMesFactoryModelDetailByProductLineId(Long id);
}
