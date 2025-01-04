package com.neu.carbon.scm.service.impl;

import java.util.List;
import com.neu.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.neu.carbon.scm.mapper.ScmSaleCustomerMapper;
import com.neu.carbon.scm.domain.ScmSaleCustomer;
import com.neu.carbon.scm.service.IScmSaleCustomerService;

/**
 * 客户档案Service业务层处理
 * 
 * @author neuedu
 * @date 2022-07-02
 */
@Service
public class ScmSaleCustomerServiceImpl implements IScmSaleCustomerService 
{
    @Autowired
    private ScmSaleCustomerMapper scmSaleCustomerMapper;

    /**
     * 查询客户档案
     * 
     * @param id 客户档案ID
     * @return 客户档案
     */
    @Override
    public ScmSaleCustomer selectScmSaleCustomerById(Long id)
    {
        return scmSaleCustomerMapper.selectScmSaleCustomerById(id);
    }

    /**
     * 查询客户档案列表
     * 
     * @param scmSaleCustomer 客户档案
     * @return 客户档案
     */
    @Override
    public List<ScmSaleCustomer> selectScmSaleCustomerList(ScmSaleCustomer scmSaleCustomer)
    {
        return scmSaleCustomerMapper.selectScmSaleCustomerList(scmSaleCustomer);
    }

    /**
     * 新增客户档案
     * 
     * @param scmSaleCustomer 客户档案
     * @return 结果
     */
    @Override
    public int insertScmSaleCustomer(ScmSaleCustomer scmSaleCustomer)
    {
        scmSaleCustomer.setCreateTime(DateUtils.getNowDate());
        return scmSaleCustomerMapper.insertScmSaleCustomer(scmSaleCustomer);
    }

    /**
     * 修改客户档案
     * 
     * @param scmSaleCustomer 客户档案
     * @return 结果
     */
    @Override
    public int updateScmSaleCustomer(ScmSaleCustomer scmSaleCustomer)
    {
        scmSaleCustomer.setUpdateTime(DateUtils.getNowDate());
        return scmSaleCustomerMapper.updateScmSaleCustomer(scmSaleCustomer);
    }

    /**
     * 批量删除客户档案
     * 
     * @param ids 需要删除的客户档案ID
     * @return 结果
     */
    @Override
    public int deleteScmSaleCustomerByIds(Long[] ids)
    {
        return scmSaleCustomerMapper.deleteScmSaleCustomerByIds(ids);
    }

    /**
     * 删除客户档案信息
     * 
     * @param id 客户档案ID
     * @return 结果
     */
    @Override
    public int deleteScmSaleCustomerById(Long id)
    {
        return scmSaleCustomerMapper.deleteScmSaleCustomerById(id);
    }
}
