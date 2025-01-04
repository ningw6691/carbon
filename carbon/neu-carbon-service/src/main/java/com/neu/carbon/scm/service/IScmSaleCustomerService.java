package com.neu.carbon.scm.service;

import java.util.List;
import com.neu.carbon.scm.domain.ScmSaleCustomer;

/**
 * 客户档案Service接口
 * 
 * @author neuedu
 * @date 2022-07-02
 */
public interface IScmSaleCustomerService 
{
    /**
     * 查询客户档案
     * 
     * @param id 客户档案ID
     * @return 客户档案
     */
    public ScmSaleCustomer selectScmSaleCustomerById(Long id);

    /**
     * 查询客户档案列表
     * 
     * @param scmSaleCustomer 客户档案
     * @return 客户档案集合
     */
    public List<ScmSaleCustomer> selectScmSaleCustomerList(ScmSaleCustomer scmSaleCustomer);

    /**
     * 新增客户档案
     * 
     * @param scmSaleCustomer 客户档案
     * @return 结果
     */
    public int insertScmSaleCustomer(ScmSaleCustomer scmSaleCustomer);

    /**
     * 修改客户档案
     * 
     * @param scmSaleCustomer 客户档案
     * @return 结果
     */
    public int updateScmSaleCustomer(ScmSaleCustomer scmSaleCustomer);

    /**
     * 批量删除客户档案
     * 
     * @param ids 需要删除的客户档案ID
     * @return 结果
     */
    public int deleteScmSaleCustomerByIds(Long[] ids);

    /**
     * 删除客户档案信息
     * 
     * @param id 客户档案ID
     * @return 结果
     */
    public int deleteScmSaleCustomerById(Long id);
}
