package com.neu.carbon.wms.service;

import java.util.List;
import com.neu.carbon.wms.domain.WmsReplenishApply;

/**
 * 补货申请Service接口
 * 
 * @author neuedu
 * @date 2022-07-20
 */
public interface IWmsReplenishApplyService 
{
    /**
     * 查询补货申请
     * 
     * @param id 补货申请ID
     * @return 补货申请
     */
    public WmsReplenishApply selectWmsReplenishApplyById(Long id);

    /**
     * 查询补货申请列表
     * 
     * @param wmsReplenishApply 补货申请
     * @return 补货申请集合
     */
    public List<WmsReplenishApply> selectWmsReplenishApplyList(WmsReplenishApply wmsReplenishApply);

    /**
     * 新增补货申请
     * 
     * @param wmsReplenishApply 补货申请
     * @return 结果
     */
    public int insertWmsReplenishApply(WmsReplenishApply wmsReplenishApply);

    /**
     * 修改补货申请
     * 
     * @param wmsReplenishApply 补货申请
     * @return 结果
     */
    public int updateWmsReplenishApply(WmsReplenishApply wmsReplenishApply);

    /**
     * 批量删除补货申请
     * 
     * @param ids 需要删除的补货申请ID
     * @return 结果
     */
    public int deleteWmsReplenishApplyByIds(Long[] ids);

    /**
     * 删除补货申请信息
     * 
     * @param id 补货申请ID
     * @return 结果
     */
    public int deleteWmsReplenishApplyById(Long id);


    /**
     * 修改补货申请
     *
     * @param wmsReplenishApply 补货申请
     * @return 结果
     */
    public int update(WmsReplenishApply wmsReplenishApply);

    /**
     * 生成采购申请
     *
     * @param id
     * @return 结果
     */
    public int genPurchaseApply(Long id);
}
