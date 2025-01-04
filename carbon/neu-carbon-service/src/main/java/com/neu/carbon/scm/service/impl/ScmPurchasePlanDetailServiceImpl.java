package com.neu.carbon.scm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.neu.carbon.scm.mapper.ScmPurchasePlanDetailMapper;
import com.neu.carbon.scm.domain.ScmPurchasePlanDetail;
import com.neu.carbon.scm.service.IScmPurchasePlanDetailService;

/**
 * 采购计划详细Service业务层处理
 * 
 * @author neuedu
 * @date 2022-06-28
 */
@Service
public class ScmPurchasePlanDetailServiceImpl implements IScmPurchasePlanDetailService 
{
    @Autowired
    private ScmPurchasePlanDetailMapper scmPurchasePlanDetailMapper;

    /**
     * 查询采购计划详细
     * 
     * @param id 采购计划详细ID
     * @return 采购计划详细
     */
    @Override
    public ScmPurchasePlanDetail selectScmPurchasePlanDetailById(Long id)
    {
        return scmPurchasePlanDetailMapper.selectScmPurchasePlanDetailById(id);
    }

    /**
     * 查询采购计划详细列表
     * 
     * @param scmPurchasePlanDetail 采购计划详细
     * @return 采购计划详细
     */
    @Override
    public List<ScmPurchasePlanDetail> selectScmPurchasePlanDetailList(ScmPurchasePlanDetail scmPurchasePlanDetail)
    {
        return scmPurchasePlanDetailMapper.selectScmPurchasePlanDetailList(scmPurchasePlanDetail);
    }

    /**
     * 新增采购计划详细
     * 
     * @param scmPurchasePlanDetail 采购计划详细
     * @return 结果
     */
    @Override
    public int insertScmPurchasePlanDetail(ScmPurchasePlanDetail scmPurchasePlanDetail)
    {
        return scmPurchasePlanDetailMapper.insertScmPurchasePlanDetail(scmPurchasePlanDetail);
    }

    /**
     * 修改采购计划详细
     * 
     * @param scmPurchasePlanDetail 采购计划详细
     * @return 结果
     */
    @Override
    public int updateScmPurchasePlanDetail(ScmPurchasePlanDetail scmPurchasePlanDetail)
    {
        return scmPurchasePlanDetailMapper.updateScmPurchasePlanDetail(scmPurchasePlanDetail);
    }

    /**
     * 批量删除采购计划详细
     * 
     * @param ids 需要删除的采购计划详细ID
     * @return 结果
     */
    @Override
    public int deleteScmPurchasePlanDetailByIds(Long[] ids)
    {
        return scmPurchasePlanDetailMapper.deleteScmPurchasePlanDetailByIds(ids);
    }

    /**
     * 删除采购计划详细信息
     * 
     * @param id 采购计划详细ID
     * @return 结果
     */
    @Override
    public int deleteScmPurchasePlanDetailById(Long id)
    {
        return scmPurchasePlanDetailMapper.deleteScmPurchasePlanDetailById(id);
    }
}
