package com.neu.carbon.scm.mapper;

import java.util.List;
import com.neu.carbon.scm.domain.ScmPurchaseSupplier;

/**
 * 供应商Mapper接口
 * 
 * @author neuedu
 * @date 2022-06-27
 */
public interface ScmPurchaseSupplierMapper 
{
    /**
     * 查询供应商
     * 
     * @param id 供应商ID
     * @return 供应商
     */
    public ScmPurchaseSupplier selectScmPurchaseSupplierById(Long id);

    /**
     * 查询供应商列表
     * 
     * @param scmPurchaseSupplier 供应商
     * @return 供应商集合
     */
    public List<ScmPurchaseSupplier> selectScmPurchaseSupplierList(ScmPurchaseSupplier scmPurchaseSupplier);

    /**
     * 新增供应商
     * 
     * @param scmPurchaseSupplier 供应商
     * @return 结果
     */
    public int insertScmPurchaseSupplier(ScmPurchaseSupplier scmPurchaseSupplier);

    /**
     * 修改供应商
     * 
     * @param scmPurchaseSupplier 供应商
     * @return 结果
     */
    public int updateScmPurchaseSupplier(ScmPurchaseSupplier scmPurchaseSupplier);

    /**
     * 删除供应商
     * 
     * @param id 供应商ID
     * @return 结果
     */
    public int deleteScmPurchaseSupplierById(Long id);

    /**
     * 批量删除供应商
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteScmPurchaseSupplierByIds(Long[] ids);
}
