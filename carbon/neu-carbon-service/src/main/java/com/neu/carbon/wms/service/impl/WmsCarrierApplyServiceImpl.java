package com.neu.carbon.wms.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

import com.neu.common.enums.SerialNoPrefix;
import com.neu.common.utils.StringUtils;
import com.neu.smty.utils.CodeUtil;

import org.springframework.transaction.annotation.Transactional;
import com.neu.carbon.wms.domain.WmsCarrierApplyDetail;
import com.neu.carbon.wms.mapper.WmsCarrierApplyMapper;
import com.neu.carbon.wms.domain.WmsCarrierApply;
import com.neu.carbon.wms.service.IWmsCarrierApplyService;

/**
 * 承运申请Service业务层处理
 * 
 * @author neuedu
 * @date 2022-07-01
 */
@Service
public class WmsCarrierApplyServiceImpl implements IWmsCarrierApplyService 
{
    @Autowired
    private WmsCarrierApplyMapper wmsCarrierApplyMapper;

    /**
     * 查询承运申请
     * 
     * @param id 承运申请ID
     * @return 承运申请
     */
    @Override
    public WmsCarrierApply selectWmsCarrierApplyById(Long id)
    {
        return wmsCarrierApplyMapper.selectWmsCarrierApplyById(id);
    }

    /**
     * 查询承运申请列表
     * 
     * @param wmsCarrierApply 承运申请
     * @return 承运申请
     */
    @Override
    public List<WmsCarrierApply> selectWmsCarrierApplyList(WmsCarrierApply wmsCarrierApply)
    {
        return wmsCarrierApplyMapper.selectWmsCarrierApplyList(wmsCarrierApply);
    }

    /**
     * 新增承运申请
     * 
     * @param wmsCarrierApply 承运申请
     * @return 结果
     */
    @Transactional
    @Override
    public int insertWmsCarrierApply(WmsCarrierApply wmsCarrierApply)
    {
    	wmsCarrierApply.setSerialNo(CodeUtil.getSerialNo(SerialNoPrefix.CYSQ));
        int rows = wmsCarrierApplyMapper.insertWmsCarrierApply(wmsCarrierApply);
        insertWmsCarrierApplyDetail(wmsCarrierApply);
        return rows;
    }

    /**
     * 修改承运申请
     * 
     * @param wmsCarrierApply 承运申请
     * @return 结果
     */
    @Transactional
    @Override
    public int updateWmsCarrierApply(WmsCarrierApply wmsCarrierApply)
    {
    	//审核时不需要更新明细表
    	if(StringUtils.isBlank(wmsCarrierApply.getAuditStatus())) {
	        wmsCarrierApplyMapper.deleteWmsCarrierApplyDetailByCarrierApplyId(wmsCarrierApply.getId());
	        insertWmsCarrierApplyDetail(wmsCarrierApply);
    	}
        return wmsCarrierApplyMapper.updateWmsCarrierApply(wmsCarrierApply);
    }

    /**
     * 批量删除承运申请
     * 
     * @param ids 需要删除的承运申请ID
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteWmsCarrierApplyByIds(Long[] ids)
    {
        wmsCarrierApplyMapper.deleteWmsCarrierApplyDetailByCarrierApplyIds(ids);
        return wmsCarrierApplyMapper.deleteWmsCarrierApplyByIds(ids);
    }

    /**
     * 删除承运申请信息
     * 
     * @param id 承运申请ID
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteWmsCarrierApplyById(Long id)
    {
        wmsCarrierApplyMapper.deleteWmsCarrierApplyDetailByCarrierApplyId(id);
        return wmsCarrierApplyMapper.deleteWmsCarrierApplyById(id);
    }

    /**
     * 新增产品承运申请明细信息
     * 
     * @param wmsCarrierApply 承运申请对象
     */
    public void insertWmsCarrierApplyDetail(WmsCarrierApply wmsCarrierApply)
    {
        List<WmsCarrierApplyDetail> wmsCarrierApplyDetailList = wmsCarrierApply.getWmsCarrierApplyDetailList();
        Long id = wmsCarrierApply.getId();
        if (StringUtils.isNotNull(wmsCarrierApplyDetailList))
        {
            List<WmsCarrierApplyDetail> list = new ArrayList<WmsCarrierApplyDetail>();
            for (WmsCarrierApplyDetail wmsCarrierApplyDetail : wmsCarrierApplyDetailList)
            {
                wmsCarrierApplyDetail.setCarrierApplyId(id);
                list.add(wmsCarrierApplyDetail);
            }
            if (list.size() > 0)
            {
                wmsCarrierApplyMapper.batchWmsCarrierApplyDetail(list);
            }
        }
    }
}
