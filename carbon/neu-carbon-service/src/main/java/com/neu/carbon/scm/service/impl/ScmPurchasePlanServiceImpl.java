package com.neu.carbon.scm.service.impl;

import java.util.List;

import com.neu.common.enums.SerialNoPrefix;
import com.neu.smty.utils.CodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.neu.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.neu.carbon.scm.domain.ScmPurchasePlanDetail;
import com.neu.carbon.scm.mapper.ScmPurchasePlanMapper;
import com.neu.carbon.scm.domain.ScmPurchasePlan;
import com.neu.carbon.scm.service.IScmPurchasePlanService;

/**
 * 采购计划Service业务层处理
 * 
 * @author neuedu
 * @date 2022-06-28
 */
@Service
public class ScmPurchasePlanServiceImpl implements IScmPurchasePlanService 
{
    @Autowired
    private ScmPurchasePlanMapper scmPurchasePlanMapper;

    /**
     * 查询采购计划
     * 
     * @param id 采购计划ID
     * @return 采购计划
     */
    @Override
    public ScmPurchasePlan selectScmPurchasePlanById(Long id)
    {
        return scmPurchasePlanMapper.selectScmPurchasePlanById(id);
    }

    /**
     * 查询采购计划列表
     * 
     * @param scmPurchasePlan 采购计划
     * @return 采购计划
     */
    @Override
    public List<ScmPurchasePlan> selectScmPurchasePlanList(ScmPurchasePlan scmPurchasePlan)
    {
        return scmPurchasePlanMapper.selectScmPurchasePlanList(scmPurchasePlan);
    }

    /**
     * 新增采购计划
     * 
     * @param scmPurchasePlan 采购计划
     * @return 结果
     */
    @Transactional
    @Override
    public int insertScmPurchasePlan(ScmPurchasePlan scmPurchasePlan)
    {
        scmPurchasePlan.setPurchasePlanNo(CodeUtil.getSerialNo(SerialNoPrefix.CGJH));
        int rows = scmPurchasePlanMapper.insertScmPurchasePlan(scmPurchasePlan);
        insertScmPurchasePlanDetail(scmPurchasePlan);
        return rows;
    }

    /**
     * 修改采购计划
     * 
     * @param scmPurchasePlan 采购计划
     * @return 结果
     */
    @Transactional
    @Override
    public int updateScmPurchasePlan(ScmPurchasePlan scmPurchasePlan)
    {
        //审核时不需要更新明细表
        if(StringUtils.isBlank(scmPurchasePlan.getAuditStatus())) {
            scmPurchasePlanMapper.deleteScmPurchasePlanDetailByPurchasePlanId(scmPurchasePlan.getId());
            insertScmPurchasePlanDetail(scmPurchasePlan);
        }
        return scmPurchasePlanMapper.updateScmPurchasePlan(scmPurchasePlan);
    }

    /**
     * 批量删除采购计划
     * 
     * @param ids 需要删除的采购计划ID
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteScmPurchasePlanByIds(Long[] ids)
    {
        scmPurchasePlanMapper.deleteScmPurchasePlanDetailByPurchasePlanIds(ids);
        return scmPurchasePlanMapper.deleteScmPurchasePlanByIds(ids);
    }

    /**
     * 删除采购计划信息
     * 
     * @param id 采购计划ID
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteScmPurchasePlanById(Long id)
    {
        scmPurchasePlanMapper.deleteScmPurchasePlanDetailByPurchasePlanId(id);
        return scmPurchasePlanMapper.deleteScmPurchasePlanById(id);
    }

    /**
     * 新增采购计划详细信息
     * 
     * @param scmPurchasePlan 采购计划对象
     */
    public void insertScmPurchasePlanDetail(ScmPurchasePlan scmPurchasePlan)
    {
        List<ScmPurchasePlanDetail> scmPurchasePlanDetailList = scmPurchasePlan.getScmPurchasePlanDetailList();
        Long id = scmPurchasePlan.getId();
        if (StringUtils.isNotNull(scmPurchasePlanDetailList))
        {
            List<ScmPurchasePlanDetail> list = new ArrayList<ScmPurchasePlanDetail>();
            for (ScmPurchasePlanDetail scmPurchasePlanDetail : scmPurchasePlanDetailList)
            {
                scmPurchasePlanDetail.setPurchasePlanId(id);
                list.add(scmPurchasePlanDetail);
            }
            if (list.size() > 0)
            {
                scmPurchasePlanMapper.batchScmPurchasePlanDetail(list);
            }
        }
    }
}
