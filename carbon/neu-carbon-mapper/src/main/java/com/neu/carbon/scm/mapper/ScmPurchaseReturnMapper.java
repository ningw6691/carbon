package com.neu.carbon.scm.mapper;

import java.util.List;
import com.neu.carbon.scm.domain.ScmPurchaseReturn;
import com.neu.carbon.scm.domain.ScmPurchaseReturnDetail;

/**
 * 采购退货Mapper接口
 * 
 * @author neuedu
 * @date 2022-07-07
 */
public interface ScmPurchaseReturnMapper 
{
    /**
     * 查询采购退货
     * 
     * @param id 采购退货ID
     * @return 采购退货
     */
    public ScmPurchaseReturn selectScmPurchaseReturnById(Long id);

    /**
     * 查询采购退货列表
     * 
     * @param scmPurchaseReturn 采购退货
     * @return 采购退货集合
     */
    public List<ScmPurchaseReturn> selectScmPurchaseReturnList(ScmPurchaseReturn scmPurchaseReturn);

    /**
     * 新增采购退货
     * 
     * @param scmPurchaseReturn 采购退货
     * @return 结果
     */
    public int insertScmPurchaseReturn(ScmPurchaseReturn scmPurchaseReturn);

    /**
     * 修改采购退货
     * 
     * @param scmPurchaseReturn 采购退货
     * @return 结果
     */
    public int updateScmPurchaseReturn(ScmPurchaseReturn scmPurchaseReturn);

    /**
     * 删除采购退货
     * 
     * @param id 采购退货ID
     * @return 结果
     */
    public int deleteScmPurchaseReturnById(Long id);

    /**
     * 批量删除采购退货
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteScmPurchaseReturnByIds(Long[] ids);

    /**
     * 批量删除退货明细
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteScmPurchaseReturnDetailByReturnIds(Long[] ids);
    
    /**
     * 批量新增退货明细
     * 
     * @param scmPurchaseReturnDetailList 退货明细列表
     * @return 结果
     */
    public int batchScmPurchaseReturnDetail(List<ScmPurchaseReturnDetail> scmPurchaseReturnDetailList);
    

    /**
     * 通过采购退货ID删除退货明细信息
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
    public int deleteScmPurchaseReturnDetailByReturnId(Long id);
}
