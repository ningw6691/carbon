package com.neu.carbon.scm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.neu.carbon.scm.mapper.ScmSaleContractDetailMapper;
import com.neu.carbon.scm.domain.ScmSaleContractDetail;
import com.neu.carbon.scm.service.IScmSaleContractDetailService;

/**
 * 合同明细Service业务层处理
 * 
 * @author neuedu
 * @date 2022-07-04
 */
@Service
public class ScmSaleContractDetailServiceImpl implements IScmSaleContractDetailService 
{
    @Autowired
    private ScmSaleContractDetailMapper scmSaleContractDetailMapper;

    /**
     * 查询合同明细
     * 
     * @param id 合同明细ID
     * @return 合同明细
     */
    @Override
    public ScmSaleContractDetail selectScmSaleContractDetailById(Long id)
    {
        return scmSaleContractDetailMapper.selectScmSaleContractDetailById(id);
    }

    /**
     * 查询合同明细列表
     * 
     * @param scmSaleContractDetail 合同明细
     * @return 合同明细
     */
    @Override
    public List<ScmSaleContractDetail> selectScmSaleContractDetailList(ScmSaleContractDetail scmSaleContractDetail)
    {
        return scmSaleContractDetailMapper.selectScmSaleContractDetailList(scmSaleContractDetail);
    }

    /**
     * 新增合同明细
     * 
     * @param scmSaleContractDetail 合同明细
     * @return 结果
     */
    @Override
    public int insertScmSaleContractDetail(ScmSaleContractDetail scmSaleContractDetail)
    {
        return scmSaleContractDetailMapper.insertScmSaleContractDetail(scmSaleContractDetail);
    }

    /**
     * 修改合同明细
     * 
     * @param scmSaleContractDetail 合同明细
     * @return 结果
     */
    @Override
    public int updateScmSaleContractDetail(ScmSaleContractDetail scmSaleContractDetail)
    {
        return scmSaleContractDetailMapper.updateScmSaleContractDetail(scmSaleContractDetail);
    }

    /**
     * 批量删除合同明细
     * 
     * @param ids 需要删除的合同明细ID
     * @return 结果
     */
    @Override
    public int deleteScmSaleContractDetailByIds(Long[] ids)
    {
        return scmSaleContractDetailMapper.deleteScmSaleContractDetailByIds(ids);
    }

    /**
     * 删除合同明细信息
     * 
     * @param id 合同明细ID
     * @return 结果
     */
    @Override
    public int deleteScmSaleContractDetailById(Long id)
    {
        return scmSaleContractDetailMapper.deleteScmSaleContractDetailById(id);
    }
}
