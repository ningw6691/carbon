package com.neu.carbon.scm.mapper;

import java.util.List;
import com.neu.carbon.scm.domain.ScmSaleContract;
import com.neu.carbon.scm.domain.ScmSaleContractDetail;

/**
 * 销售合同Mapper接口
 * 
 * @author neuedu
 * @date 2022-07-04
 */
public interface ScmSaleContractMapper 
{
    /**
     * 查询销售合同
     * 
     * @param id 销售合同ID
     * @return 销售合同
     */
    public ScmSaleContract selectScmSaleContractById(Long id);

    /**
     * 查询销售合同列表
     * 
     * @param scmSaleContract 销售合同
     * @return 销售合同集合
     */
    public List<ScmSaleContract> selectScmSaleContractList(ScmSaleContract scmSaleContract);

    /**
     * 新增销售合同
     * 
     * @param scmSaleContract 销售合同
     * @return 结果
     */
    public int insertScmSaleContract(ScmSaleContract scmSaleContract);

    /**
     * 修改销售合同
     * 
     * @param scmSaleContract 销售合同
     * @return 结果
     */
    public int updateScmSaleContract(ScmSaleContract scmSaleContract);

    /**
     * 删除销售合同
     * 
     * @param id 销售合同ID
     * @return 结果
     */
    public int deleteScmSaleContractById(Long id);

    /**
     * 批量删除销售合同
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteScmSaleContractByIds(Long[] ids);

    /**
     * 批量删除合同明细
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteScmSaleContractDetailByContractIds(Long[] ids);
    
    /**
     * 批量新增合同明细
     * 
     * @param scmSaleContractDetailList 合同明细列表
     * @return 结果
     */
    public int batchScmSaleContractDetail(List<ScmSaleContractDetail> scmSaleContractDetailList);
    

    /**
     * 通过销售合同ID删除合同明细信息
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
    public int deleteScmSaleContractDetailByContractId(Long id);


    /**
     * 修改销售合同
     *
     * @param scmSaleContract 销售合同
     * @return 结果
     */
    public int update(ScmSaleContract scmSaleContract);
}
