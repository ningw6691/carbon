package com.neu.carbon.mes.mapper;

import java.util.List;
import com.neu.carbon.mes.domain.MesMaterialRequisitionDetail;

/**
 * 领料单物料明细Mapper接口
 * 
 * @author neuedu
 * @date 2022-07-14
 */
public interface MesMaterialRequisitionDetailMapper 
{
    /**
     * 查询领料单物料明细
     * 
     * @param id 领料单物料明细ID
     * @return 领料单物料明细
     */
    public MesMaterialRequisitionDetail selectMesMaterialRequisitionDetailById(Long id);

    /**
     * 查询领料单物料明细列表
     * 
     * @param mesMaterialRequisitionDetail 领料单物料明细
     * @return 领料单物料明细集合
     */
    public List<MesMaterialRequisitionDetail> selectMesMaterialRequisitionDetailList(MesMaterialRequisitionDetail mesMaterialRequisitionDetail);

    /**
     * 新增领料单物料明细
     * 
     * @param mesMaterialRequisitionDetail 领料单物料明细
     * @return 结果
     */
    public int insertMesMaterialRequisitionDetail(MesMaterialRequisitionDetail mesMaterialRequisitionDetail);

    /**
     * 修改领料单物料明细
     * 
     * @param mesMaterialRequisitionDetail 领料单物料明细
     * @return 结果
     */
    public int updateMesMaterialRequisitionDetail(MesMaterialRequisitionDetail mesMaterialRequisitionDetail);

    /**
     * 删除领料单物料明细
     * 
     * @param id 领料单物料明细ID
     * @return 结果
     */
    public int deleteMesMaterialRequisitionDetailById(Long id);

    /**
     * 批量删除领料单物料明细
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteMesMaterialRequisitionDetailByIds(Long[] ids);
}
