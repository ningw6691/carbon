package com.neu.carbon.mes.service;

import java.util.List;
import com.neu.carbon.mes.domain.MesMaterialRequisition;

/**
 * 领料申请Service接口
 * 
 * @author neuedu
 * @date 2022-07-14
 */
public interface IMesMaterialRequisitionService 
{
    /**
     * 查询领料申请
     * 
     * @param id 领料申请ID
     * @return 领料申请
     */
    public MesMaterialRequisition selectMesMaterialRequisitionById(Long id);

    /**
     * 查询领料申请列表
     * 
     * @param mesMaterialRequisition 领料申请
     * @return 领料申请集合
     */
    public List<MesMaterialRequisition> selectMesMaterialRequisitionList(MesMaterialRequisition mesMaterialRequisition);

    /**
     * 新增领料申请
     * 
     * @param mesMaterialRequisition 领料申请
     * @return 结果
     */
    public int insertMesMaterialRequisition(MesMaterialRequisition mesMaterialRequisition);

    /**
     * 修改领料申请
     * 
     * @param mesMaterialRequisition 领料申请
     * @return 结果
     */
    public int updateMesMaterialRequisition(MesMaterialRequisition mesMaterialRequisition);

    /**
     * 批量删除领料申请
     * 
     * @param ids 需要删除的领料申请ID
     * @return 结果
     */
    public int deleteMesMaterialRequisitionByIds(Long[] ids);

    /**
     * 删除领料申请信息
     * 
     * @param id 领料申请ID
     * @return 结果
     */
    public int deleteMesMaterialRequisitionById(Long id);
    
    /**
     * 设置领料单检验状态
     * @param mesMaterialRequisition
     */
    public int checkMaterialRequisition(MesMaterialRequisition mesMaterialRequisition);
    
    /**
     * 修改领料单状态
     * @param mesMaterialRequisition
     * @return
     */
    public int updateMesMaterialRequisitionStatus(MesMaterialRequisition mesMaterialRequisition);
}
