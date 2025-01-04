package com.neu.carbon.scm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.neu.carbon.scm.mapper.ScmPurchaseApplyDetailMapper;
import com.neu.carbon.scm.domain.ScmPurchaseApplyDetail;
import com.neu.carbon.scm.service.IScmPurchaseApplyDetailService;

/**
 * 采购申请明细Service业务层处理
 * 
 * @author neuedu
 * @date 2022-06-29
 */
@Service
public class ScmPurchaseApplyDetailServiceImpl implements IScmPurchaseApplyDetailService 
{
    @Autowired
    private ScmPurchaseApplyDetailMapper scmPurchaseApplyDetailMapper;

    /**
     * 查询采购申请明细
     * 
     * @param id 采购申请明细ID
     * @return 采购申请明细
     */
    @Override
    public ScmPurchaseApplyDetail selectScmPurchaseApplyDetailById(Long id)
    {
        return scmPurchaseApplyDetailMapper.selectScmPurchaseApplyDetailById(id);
    }

    /**
     * 查询采购申请明细列表
     * 
     * @param scmPurchaseApplyDetail 采购申请明细
     * @return 采购申请明细
     */
    @Override
    public List<ScmPurchaseApplyDetail> selectScmPurchaseApplyDetailList(ScmPurchaseApplyDetail scmPurchaseApplyDetail)
    {
        return scmPurchaseApplyDetailMapper.selectScmPurchaseApplyDetailList(scmPurchaseApplyDetail);
    }

    /**
     * 新增采购申请明细
     * 
     * @param scmPurchaseApplyDetail 采购申请明细
     * @return 结果
     */
    @Override
    public int insertScmPurchaseApplyDetail(ScmPurchaseApplyDetail scmPurchaseApplyDetail)
    {
        return scmPurchaseApplyDetailMapper.insertScmPurchaseApplyDetail(scmPurchaseApplyDetail);
    }

    /**
     * 修改采购申请明细
     * 
     * @param scmPurchaseApplyDetail 采购申请明细
     * @return 结果
     */
    @Override
    public int updateScmPurchaseApplyDetail(ScmPurchaseApplyDetail scmPurchaseApplyDetail)
    {
        return scmPurchaseApplyDetailMapper.updateScmPurchaseApplyDetail(scmPurchaseApplyDetail);
    }

    /**
     * 批量删除采购申请明细
     * 
     * @param ids 需要删除的采购申请明细ID
     * @return 结果
     */
    @Override
    public int deleteScmPurchaseApplyDetailByIds(Long[] ids)
    {
        return scmPurchaseApplyDetailMapper.deleteScmPurchaseApplyDetailByIds(ids);
    }

    /**
     * 删除采购申请明细信息
     * 
     * @param id 采购申请明细ID
     * @return 结果
     */
    @Override
    public int deleteScmPurchaseApplyDetailById(Long id)
    {
        return scmPurchaseApplyDetailMapper.deleteScmPurchaseApplyDetailById(id);
    }
}
