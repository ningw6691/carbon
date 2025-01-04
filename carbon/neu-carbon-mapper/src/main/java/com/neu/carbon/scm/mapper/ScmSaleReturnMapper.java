package com.neu.carbon.scm.mapper;

import java.util.List;
import com.neu.carbon.scm.domain.ScmSaleReturn;
import com.neu.carbon.scm.domain.ScmSaleReturnDetail;

/**
 * 销售退货Mapper接口
 * 
 * @author neuedu
 * @date 2022-07-08
 */
public interface ScmSaleReturnMapper 
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
     * 删除销售退货
     * 
     * @param id 销售退货ID
     * @return 结果
     */
    public int deleteScmSaleReturnById(Long id);

    /**
     * 批量删除销售退货
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteScmSaleReturnByIds(Long[] ids);

    /**
     * 批量删除退货明细
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteScmSaleReturnDetailByReturnIds(Long[] ids);
    
    /**
     * 批量新增退货明细
     * 
     * @param scmSaleReturnDetailList 退货明细列表
     * @return 结果
     */
    public int batchScmSaleReturnDetail(List<ScmSaleReturnDetail> scmSaleReturnDetailList);
    

    /**
     * 通过销售退货ID删除退货明细信息
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
    public int deleteScmSaleReturnDetailByReturnId(Long id);
}
