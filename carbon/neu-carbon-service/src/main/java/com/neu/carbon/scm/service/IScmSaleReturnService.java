package com.neu.carbon.scm.service;

import java.util.List;
import com.neu.carbon.scm.domain.ScmSaleReturn;

/**
 * 销售退货Service接口
 * 
 * @author neuedu
 * @date 2022-07-08
 */
public interface IScmSaleReturnService 
{
    /**
     * 查询销售退货
     * 
     * @param id 销售退货ID
     * @return 销售退货
     */
    public ScmSaleReturn selectScmSaleReturnById(Long id);

    /**
     * 查询销售退货列表
     * 
     * @param scmSaleReturn 销售退货
     * @return 销售退货集合
     */
    public List<ScmSaleReturn> selectScmSaleReturnList(ScmSaleReturn scmSaleReturn);

    /**
     * 新增销售退货
     * 
     * @param scmSaleReturn 销售退货
     * @return 结果
     */
    public int insertScmSaleReturn(ScmSaleReturn scmSaleReturn);

    /**
     * 修改销售退货
     * 
     * @param scmSaleReturn 销售退货
     * @return 结果
     */
    public int updateScmSaleReturn(ScmSaleReturn scmSaleReturn);

    /**
     * 批量删除销售退货
     * 
     * @param ids 需要删除的销售退货ID
     * @return 结果
     */
    public int deleteScmSaleReturnByIds(Long[] ids);

    /**
     * 删除销售退货信息
     * 
     * @param id 销售退货ID
     * @return 结果
     */
    public int deleteScmSaleReturnById(Long id);
}
