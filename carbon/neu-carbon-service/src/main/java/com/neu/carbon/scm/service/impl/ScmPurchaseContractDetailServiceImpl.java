package com.neu.carbon.scm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.neu.carbon.scm.mapper.ScmPurchaseContractDetailMapper;
import com.neu.carbon.scm.domain.ScmPurchaseContractDetail;
import com.neu.carbon.scm.service.IScmPurchaseContractDetailService;

/**
 * 合同物料明细Service业务层处理
 * 
 * @author neuedu
 * @date 2022-06-30
 */
@Service
public class ScmPurchaseContractDetailServiceImpl implements IScmPurchaseContractDetailService 
{
    @Autowired
    private ScmPurchaseContractDetailMapper scmPurchaseContractDetailMapper;

    /**
     * 查询合同物料明细
     * 
     * @param id 合同物料明细ID
     * @return 合同物料明细
     */
    @Override
    public ScmPurchaseContractDetail selectScmPurchaseContractDetailById(Long id)
    {
        return scmPurchaseContractDetailMapper.selectScmPurchaseContractDetailById(id);
    }

    /**
     * 查询合同物料明细列表
     * 
     * @param scmPurchaseContractDetail 合同物料明细
     * @return 合同物料明细
     */
    @Override
    public List<ScmPurchaseContractDetail> selectScmPurchaseContractDetailList(ScmPurchaseContractDetail scmPurchaseContractDetail)
    {
        return scmPurchaseContractDetailMapper.selectScmPurchaseContractDetailList(scmPurchaseContractDetail);
    }

    /**
     * 新增合同物料明细
     * 
     * @param scmPurchaseContractDetail 合同物料明细
     * @return 结果
     */
    @Override
    public int insertScmPurchaseContractDetail(ScmPurchaseContractDetail scmPurchaseContractDetail)
    {
        return scmPurchaseContractDetailMapper.insertScmPurchaseContractDetail(scmPurchaseContractDetail);
    }

    /**
     * 修改合同物料明细
     * 
     * @param scmPurchaseContractDetail 合同物料明细
     * @return 结果
     */
    @Override
    public int updateScmPurchaseContractDetail(ScmPurchaseContractDetail scmPurchaseContractDetail)
    {
        return scmPurchaseContractDetailMapper.updateScmPurchaseContractDetail(scmPurchaseContractDetail);
    }

    /**
     * 批量删除合同物料明细
     * 
     * @param ids 需要删除的合同物料明细ID
     * @return 结果
     */
    @Override
    public int deleteScmPurchaseContractDetailByIds(Long[] ids)
    {
        return scmPurchaseContractDetailMapper.deleteScmPurchaseContractDetailByIds(ids);
    }

    /**
     * 删除合同物料明细信息
     * 
     * @param id 合同物料明细ID
     * @return 结果
     */
    @Override
    public int deleteScmPurchaseContractDetailById(Long id)
    {
        return scmPurchaseContractDetailMapper.deleteScmPurchaseContractDetailById(id);
    }
}
