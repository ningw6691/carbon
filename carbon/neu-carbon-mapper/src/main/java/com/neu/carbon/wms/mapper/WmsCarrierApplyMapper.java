package com.neu.carbon.wms.mapper;

import java.util.List;
import com.neu.carbon.wms.domain.WmsCarrierApply;
import com.neu.carbon.wms.domain.WmsCarrierApplyDetail;

/**
 * 承运申请Mapper接口
 * 
 * @author neuedu
 * @date 2022-07-01
 */
public interface WmsCarrierApplyMapper 
{
    /**
     * 查询承运申请
     * 
     * @param id 承运申请ID
     * @return 承运申请
     */
    public WmsCarrierApply selectWmsCarrierApplyById(Long id);

    /**
     * 查询承运申请列表
     * 
     * @param wmsCarrierApply 承运申请
     * @return 承运申请集合
     */
    public List<WmsCarrierApply> selectWmsCarrierApplyList(WmsCarrierApply wmsCarrierApply);

    /**
     * 新增承运申请
     * 
     * @param wmsCarrierApply 承运申请
     * @return 结果
     */
    public int insertWmsCarrierApply(WmsCarrierApply wmsCarrierApply);

    /**
     * 修改承运申请
     * 
     * @param wmsCarrierApply 承运申请
     * @return 结果
     */
    public int updateWmsCarrierApply(WmsCarrierApply wmsCarrierApply);

    /**
     * 删除承运申请
     * 
     * @param id 承运申请ID
     * @return 结果
     */
    public int deleteWmsCarrierApplyById(Long id);

    /**
     * 批量删除承运申请
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWmsCarrierApplyByIds(Long[] ids);

    /**
     * 批量删除产品承运申请明细
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWmsCarrierApplyDetailByCarrierApplyIds(Long[] ids);
    
    /**
     * 批量新增产品承运申请明细
     * 
     * @param wmsCarrierApplyDetailList 产品承运申请明细列表
     * @return 结果
     */
    public int batchWmsCarrierApplyDetail(List<WmsCarrierApplyDetail> wmsCarrierApplyDetailList);
    

    /**
     * 通过承运申请ID删除产品承运申请明细信息
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
    public int deleteWmsCarrierApplyDetailByCarrierApplyId(Long id);
}
