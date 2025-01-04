package com.neu.carbon.mes.service;

import java.util.List;
import com.neu.carbon.mes.domain.MesProductScheduleMaterial;

/**
 * 排产物料信息Service接口
 * 
 * @author neuedu
 * @date 2022-07-13
 */
public interface IMesProductScheduleMaterialService 
{
    /**
     * 查询排产物料信息
     * 
     * @param id 排产物料信息ID
     * @return 排产物料信息
     */
    public MesProductScheduleMaterial selectMesProductScheduleMaterialById(Long id);

    /**
     * 查询排产物料信息列表
     * 
     * @param mesProductScheduleMaterial 排产物料信息
     * @return 排产物料信息集合
     */
    public List<MesProductScheduleMaterial> selectMesProductScheduleMaterialList(MesProductScheduleMaterial mesProductScheduleMaterial);

    /**
     * 新增排产物料信息
     * 
     * @param mesProductScheduleMaterial 排产物料信息
     * @return 结果
     */
    public int insertMesProductScheduleMaterial(MesProductScheduleMaterial mesProductScheduleMaterial);

    /**
     * 修改排产物料信息
     * 
     * @param mesProductScheduleMaterial 排产物料信息
     * @return 结果
     */
    public int updateMesProductScheduleMaterial(MesProductScheduleMaterial mesProductScheduleMaterial);

    /**
     * 批量删除排产物料信息
     * 
     * @param ids 需要删除的排产物料信息ID
     * @return 结果
     */
    public int deleteMesProductScheduleMaterialByIds(Long[] ids);

    /**
     * 删除排产物料信息信息
     * 
     * @param id 排产物料信息ID
     * @return 结果
     */
    public int deleteMesProductScheduleMaterialById(Long id);
}
