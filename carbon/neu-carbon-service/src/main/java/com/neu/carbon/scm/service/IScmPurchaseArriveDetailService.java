package com.neu.carbon.scm.service;

import java.util.List;
import com.neu.carbon.scm.domain.ScmPurchaseArriveDetail;

/**
 * 到货明细Service接口
 * 
 * @author neuedu
 * @date 2022-07-06
 */
public interface IScmPurchaseArriveDetailService 
{
    /**
     * 查询到货明细
     * 
     * @param id 到货明细ID
     * @return 到货明细
     */
    public ScmPurchaseArriveDetail selectScmPurchaseArriveDetailById(Long id);

    /**
     * 查询到货明细列表
     * 
     * @param scmPurchaseArriveDetail 到货明细
     * @return 到货明细集合
     */
    public List<ScmPurchaseArriveDetail> selectScmPurchaseArriveDetailList(ScmPurchaseArriveDetail scmPurchaseArriveDetail);

    /**
     * 新增到货明细
     * 
     * @param scmPurchaseArriveDetail 到货明细
     * @return 结果
     */
    public int insertScmPurchaseArriveDetail(ScmPurchaseArriveDetail scmPurchaseArriveDetail);

    /**
     * 修改到货明细
     * 
     * @param scmPurchaseArriveDetail 到货明细
     * @return 结果
     */
    public int updateScmPurchaseArriveDetail(ScmPurchaseArriveDetail scmPurchaseArriveDetail);

    /**
     * 批量删除到货明细
     * 
     * @param ids 需要删除的到货明细ID
     * @return 结果
     */
    public int deleteScmPurchaseArriveDetailByIds(Long[] ids);

    /**
     * 删除到货明细信息
     * 
     * @param id 到货明细ID
     * @return 结果
     */
    public int deleteScmPurchaseArriveDetailById(Long id);
}
