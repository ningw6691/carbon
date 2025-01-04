package com.neu.carbon.wms.mapper;

import java.util.List;
import com.neu.carbon.wms.domain.WmsReplenishApply;
import com.neu.carbon.wms.domain.WmsReplenishApplyDetail;

/**
 * 补货申请Mapper接口
 * 
 * @author neuedu
 * @date 2022-07-20
 */
public interface WmsReplenishApplyMapper 
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
     * 删除补货申请
     * 
     * @param id 补货申请ID
     * @return 结果
     */
    public int deleteWmsReplenishApplyById(Long id);

    /**
     * 批量删除补货申请
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWmsReplenishApplyByIds(Long[] ids);

    /**
     * 批量删除补货申请明细
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWmsReplenishApplyDetailByApplyIds(Long[] ids);
    
    /**
     * 批量新增补货申请明细
     * 
     * @param wmsReplenishApplyDetailList 补货申请明细列表
     * @return 结果
     */
    public int batchWmsReplenishApplyDetail(List<WmsReplenishApplyDetail> wmsReplenishApplyDetailList);
    

    /**
     * 通过补货申请ID删除补货申请明细信息
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
    public int deleteWmsReplenishApplyDetailByApplyId(Long id);

    /**
     * 修改补货申请
     *
     * @param wmsReplenishApply 补货申请
     * @return 结果
     */
    public int update(WmsReplenishApply wmsReplenishApply);
}
