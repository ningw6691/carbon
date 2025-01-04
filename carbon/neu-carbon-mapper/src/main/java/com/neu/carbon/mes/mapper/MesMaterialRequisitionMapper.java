package com.neu.carbon.mes.mapper;

import java.util.List;
import com.neu.carbon.mes.domain.MesMaterialRequisition;
import com.neu.carbon.mes.domain.MesMaterialRequisitionDetail;

/**
 * 领料申请Mapper接口
 * 
 * @author neuedu
 * @date 2022-07-14
 */
public interface MesMaterialRequisitionMapper 
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
     * 删除领料申请
     * 
     * @param id 领料申请ID
     * @return 结果
     */
    public int deleteMesMaterialRequisitionById(Long id);

    /**
     * 批量删除领料申请
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteMesMaterialRequisitionByIds(Long[] ids);

    /**
     * 批量删除领料单物料明细
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteMesMaterialRequisitionDetailByMaterialRequisitionIds(Long[] ids);
    
    /**
     * 批量新增领料单物料明细
     * 
     * @param mesMaterialRequisitionDetailList 领料单物料明细列表
     * @return 结果
     */
    public int batchMesMaterialRequisitionDetail(List<MesMaterialRequisitionDetail> mesMaterialRequisitionDetailList);
    

    /**
     * 通过领料申请ID删除领料单物料明细信息
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
    public int deleteMesMaterialRequisitionDetailByMaterialRequisitionId(Long id);
}
