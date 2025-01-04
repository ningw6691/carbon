package com.neu.carbon.wms.service;

import java.util.List;
import com.neu.carbon.wms.domain.WmsCarrierApply;

/**
 * 承运申请Service接口
 * 
 * @author neuedu
 * @date 2022-07-01
 */
public interface IWmsCarrierApplyService 
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
     * 批量删除承运申请
     * 
     * @param ids 需要删除的承运申请ID
     * @return 结果
     */
    public int deleteWmsCarrierApplyByIds(Long[] ids);

    /**
     * 删除承运申请信息
     * 
     * @param id 承运申请ID
     * @return 结果
     */
    public int deleteWmsCarrierApplyById(Long id);
}
