package com.neu.carbon.scm.mapper;

import java.util.List;
import com.neu.carbon.scm.domain.ScmPurchaseReturnDetail;

/**
 * 退货明细Mapper接口
 * 
 * @author neuedu
 * @date 2022-07-07
 */
public interface ScmPurchaseReturnDetailMapper 
{
    /**
     * 查询退货明细
     * 
     * @param id 退货明细ID
     * @return 退货明细
     */
    public ScmPurchaseReturnDetail selectScmPurchaseReturnDetailById(Long id);

    /**
     * 查询退货明细列表
     * 
     * @param scmPurchaseReturnDetail 退货明细
     * @return 退货明细集合
     */
    public List<ScmPurchaseReturnDetail> selectScmPurchaseReturnDetailList(ScmPurchaseReturnDetail scmPurchaseReturnDetail);

    /**
     * 新增退货明细
     * 
     * @param scmPurchaseReturnDetail 退货明细
     * @return 结果
     */
    public int insertScmPurchaseReturnDetail(ScmPurchaseReturnDetail scmPurchaseReturnDetail);

    /**
     * 修改退货明细
     * 
     * @param scmPurchaseReturnDetail 退货明细
     * @return 结果
     */
    public int updateScmPurchaseReturnDetail(ScmPurchaseReturnDetail scmPurchaseReturnDetail);

    /**
     * 删除退货明细
     * 
     * @param id 退货明细ID
     * @return 结果
     */
    public int deleteScmPurchaseReturnDetailById(Long id);

    /**
     * 批量删除退货明细
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteScmPurchaseReturnDetailByIds(Long[] ids);
}
