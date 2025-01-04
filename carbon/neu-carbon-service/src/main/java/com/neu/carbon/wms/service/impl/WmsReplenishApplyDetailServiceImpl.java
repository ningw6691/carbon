package com.neu.carbon.wms.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.neu.carbon.wms.mapper.WmsReplenishApplyDetailMapper;
import com.neu.carbon.wms.domain.WmsReplenishApplyDetail;
import com.neu.carbon.wms.service.IWmsReplenishApplyDetailService;

/**
 * 补货申请明细Service业务层处理
 * 
 * @author neuedu
 * @date 2022-07-20
 */
@Service
public class WmsReplenishApplyDetailServiceImpl implements IWmsReplenishApplyDetailService 
{
    @Autowired
    private WmsReplenishApplyDetailMapper wmsReplenishApplyDetailMapper;

    /**
     * 查询补货申请明细
     * 
     * @param id 补货申请明细ID
     * @return 补货申请明细
     */
    @Override
    public WmsReplenishApplyDetail selectWmsReplenishApplyDetailById(Long id)
    {
        return wmsReplenishApplyDetailMapper.selectWmsReplenishApplyDetailById(id);
    }

    /**
     * 查询补货申请明细列表
     * 
     * @param wmsReplenishApplyDetail 补货申请明细
     * @return 补货申请明细
     */
    @Override
    public List<WmsReplenishApplyDetail> selectWmsReplenishApplyDetailList(WmsReplenishApplyDetail wmsReplenishApplyDetail)
    {
        return wmsReplenishApplyDetailMapper.selectWmsReplenishApplyDetailList(wmsReplenishApplyDetail);
    }

    /**
     * 新增补货申请明细
     * 
     * @param wmsReplenishApplyDetail 补货申请明细
     * @return 结果
     */
    @Override
    public int insertWmsReplenishApplyDetail(WmsReplenishApplyDetail wmsReplenishApplyDetail)
    {
        return wmsReplenishApplyDetailMapper.insertWmsReplenishApplyDetail(wmsReplenishApplyDetail);
    }

    /**
     * 修改补货申请明细
     * 
     * @param wmsReplenishApplyDetail 补货申请明细
     * @return 结果
     */
    @Override
    public int updateWmsReplenishApplyDetail(WmsReplenishApplyDetail wmsReplenishApplyDetail)
    {
        return wmsReplenishApplyDetailMapper.updateWmsReplenishApplyDetail(wmsReplenishApplyDetail);
    }

    /**
     * 批量删除补货申请明细
     * 
     * @param ids 需要删除的补货申请明细ID
     * @return 结果
     */
    @Override
    public int deleteWmsReplenishApplyDetailByIds(Long[] ids)
    {
        return wmsReplenishApplyDetailMapper.deleteWmsReplenishApplyDetailByIds(ids);
    }

    /**
     * 删除补货申请明细信息
     * 
     * @param id 补货申请明细ID
     * @return 结果
     */
    @Override
    public int deleteWmsReplenishApplyDetailById(Long id)
    {
        return wmsReplenishApplyDetailMapper.deleteWmsReplenishApplyDetailById(id);
    }
}
