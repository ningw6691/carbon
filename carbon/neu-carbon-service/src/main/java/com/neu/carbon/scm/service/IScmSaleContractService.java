package com.neu.carbon.scm.service;

import java.util.List;
import com.neu.carbon.scm.domain.ScmSaleContract;

/**
 * 销售合同Service接口
 * 
 * @author neuedu
 * @date 2022-07-04
 */
public interface IScmSaleContractService 
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
     * 批量删除销售合同
     * 
     * @param ids 需要删除的销售合同ID
     * @return 结果
     */
    public int deleteScmSaleContractByIds(Long[] ids);

    /**
     * 删除销售合同信息
     * 
     * @param id 销售合同ID
     * @return 结果
     */
    public int deleteScmSaleContractById(Long id);

    /**
     * 修改销售合同
     *
     * @param scmSaleContract 销售合同
     * @return 结果
     */
    public int update(ScmSaleContract scmSaleContract);

    /**
     * 生成发货单
     * @param scmSaleContract
     * @return
     */
    public int genDelivery(ScmSaleContract scmSaleContract);
}
