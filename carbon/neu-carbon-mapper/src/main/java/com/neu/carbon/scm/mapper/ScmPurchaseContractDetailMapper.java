package com.neu.carbon.scm.mapper;

import java.util.List;
import com.neu.carbon.scm.domain.ScmPurchaseContractDetail;

/**
 * 合同物料明细Mapper接口
 * 
 * @author neuedu
 * @date 2022-06-30
 */
public interface ScmPurchaseContractDetailMapper 
{
    /**
     * 查询合同物料明细
     * 
     * @param id 合同物料明细ID
     * @return 合同物料明细
     */
    public ScmPurchaseContractDetail selectScmPurchaseContractDetailById(Long id);

    /**
     * 查询合同物料明细列表
     * 
     * @param scmPurchaseContractDetail 合同物料明细
     * @return 合同物料明细集合
     */
    public List<ScmPurchaseContractDetail> selectScmPurchaseContractDetailList(ScmPurchaseContractDetail scmPurchaseContractDetail);

    /**
     * 新增合同物料明细
     * 
     * @param scmPurchaseContractDetail 合同物料明细
     * @return 结果
     */
    public int insertScmPurchaseContractDetail(ScmPurchaseContractDetail scmPurchaseContractDetail);

    /**
     * 修改合同物料明细
     * 
     * @param scmPurchaseContractDetail 合同物料明细
     * @return 结果
     */
    public int updateScmPurchaseContractDetail(ScmPurchaseContractDetail scmPurchaseContractDetail);

    /**
     * 删除合同物料明细
     * 
     * @param id 合同物料明细ID
     * @return 结果
     */
    public int deleteScmPurchaseContractDetailById(Long id);

    /**
     * 批量删除合同物料明细
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteScmPurchaseContractDetailByIds(Long[] ids);
}
