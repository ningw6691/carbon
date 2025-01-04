package com.neu.carbon.scm.mapper;

import java.util.List;
import com.neu.carbon.scm.domain.ScmPurchaseApplyDetail;

/**
 * 采购申请明细Mapper接口
 * 
 * @author neuedu
 * @date 2022-06-29
 */
public interface ScmPurchaseApplyDetailMapper 
{
    /**
     * 查询采购申请明细
     * 
     * @param id 采购申请明细ID
     * @return 采购申请明细
     */
    public ScmPurchaseApplyDetail selectScmPurchaseApplyDetailById(Long id);

    /**
     * 查询采购申请明细列表
     * 
     * @param scmPurchaseApplyDetail 采购申请明细
     * @return 采购申请明细集合
     */
    public List<ScmPurchaseApplyDetail> selectScmPurchaseApplyDetailList(ScmPurchaseApplyDetail scmPurchaseApplyDetail);

    /**
     * 新增采购申请明细
     * 
     * @param scmPurchaseApplyDetail 采购申请明细
     * @return 结果
     */
    public int insertScmPurchaseApplyDetail(ScmPurchaseApplyDetail scmPurchaseApplyDetail);

    /**
     * 修改采购申请明细
     * 
     * @param scmPurchaseApplyDetail 采购申请明细
     * @return 结果
     */
    public int updateScmPurchaseApplyDetail(ScmPurchaseApplyDetail scmPurchaseApplyDetail);

    /**
     * 删除采购申请明细
     * 
     * @param id 采购申请明细ID
     * @return 结果
     */
    public int deleteScmPurchaseApplyDetailById(Long id);

    /**
     * 批量删除采购申请明细
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteScmPurchaseApplyDetailByIds(Long[] ids);
}
