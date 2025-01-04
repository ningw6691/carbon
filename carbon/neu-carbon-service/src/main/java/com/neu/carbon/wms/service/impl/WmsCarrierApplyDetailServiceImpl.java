package com.neu.carbon.wms.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.neu.carbon.wms.mapper.WmsCarrierApplyDetailMapper;
import com.neu.carbon.wms.domain.WmsCarrierApplyDetail;
import com.neu.carbon.wms.service.IWmsCarrierApplyDetailService;

/**
 * 产品承运申请明细Service业务层处理
 * 
 * @author neuedu
 * @date 2022-07-01
 */
@Service
public class WmsCarrierApplyDetailServiceImpl implements IWmsCarrierApplyDetailService 
{
    @Autowired
    private WmsCarrierApplyDetailMapper wmsCarrierApplyDetailMapper;

    /**
     * 查询产品承运申请明细
     * 
     * @param id 产品承运申请明细ID
     * @return 产品承运申请明细
     */
    @Override
    public WmsCarrierApplyDetail selectWmsCarrierApplyDetailById(Long id)
    {
        return wmsCarrierApplyDetailMapper.selectWmsCarrierApplyDetailById(id);
    }

    /**
     * 查询产品承运申请明细列表
     * 
     * @param wmsCarrierApplyDetail 产品承运申请明细
     * @return 产品承运申请明细
     */
    @Override
    public List<WmsCarrierApplyDetail> selectWmsCarrierApplyDetailList(WmsCarrierApplyDetail wmsCarrierApplyDetail)
    {
        return wmsCarrierApplyDetailMapper.selectWmsCarrierApplyDetailList(wmsCarrierApplyDetail);
    }

    /**
     * 新增产品承运申请明细
     * 
     * @param wmsCarrierApplyDetail 产品承运申请明细
     * @return 结果
     */
    @Override
    public int insertWmsCarrierApplyDetail(WmsCarrierApplyDetail wmsCarrierApplyDetail)
    {
        return wmsCarrierApplyDetailMapper.insertWmsCarrierApplyDetail(wmsCarrierApplyDetail);
    }

    /**
     * 修改产品承运申请明细
     * 
     * @param wmsCarrierApplyDetail 产品承运申请明细
     * @return 结果
     */
    @Override
    public int updateWmsCarrierApplyDetail(WmsCarrierApplyDetail wmsCarrierApplyDetail)
    {
        return wmsCarrierApplyDetailMapper.updateWmsCarrierApplyDetail(wmsCarrierApplyDetail);
    }

    /**
     * 批量删除产品承运申请明细
     * 
     * @param ids 需要删除的产品承运申请明细ID
     * @return 结果
     */
    @Override
    public int deleteWmsCarrierApplyDetailByIds(Long[] ids)
    {
        return wmsCarrierApplyDetailMapper.deleteWmsCarrierApplyDetailByIds(ids);
    }

    /**
     * 删除产品承运申请明细信息
     * 
     * @param id 产品承运申请明细ID
     * @return 结果
     */
    @Override
    public int deleteWmsCarrierApplyDetailById(Long id)
    {
        return wmsCarrierApplyDetailMapper.deleteWmsCarrierApplyDetailById(id);
    }
}
