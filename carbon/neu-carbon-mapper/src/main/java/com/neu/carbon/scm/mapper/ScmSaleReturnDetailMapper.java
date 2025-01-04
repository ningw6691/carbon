package com.neu.carbon.scm.mapper;

import java.util.List;
import com.neu.carbon.scm.domain.ScmSaleReturnDetail;

/**
 * 退货明细Mapper接口
 * 
 * @author neuedu
 * @date 2022-07-08
 */
public interface ScmSaleReturnDetailMapper 
{
    /**
     * 查询退货明细
     * 
     * @param id 退货明细ID
     * @return 退货明细
     */
    public ScmSaleReturnDetail selectScmSaleReturnDetailById(Long id);

    /**
     * 查询退货明细列表
     * 
     * @param scmSaleReturnDetail 退货明细
     * @return 退货明细集合
     */
    public List<ScmSaleReturnDetail> selectScmSaleReturnDetailList(ScmSaleReturnDetail scmSaleReturnDetail);

    /**
     * 新增退货明细
     * 
     * @param scmSaleReturnDetail 退货明细
     * @return 结果
     */
    public int insertScmSaleReturnDetail(ScmSaleReturnDetail scmSaleReturnDetail);

    /**
     * 修改退货明细
     * 
     * @param scmSaleReturnDetail 退货明细
     * @return 结果
     */
    public int updateScmSaleReturnDetail(ScmSaleReturnDetail scmSaleReturnDetail);

    /**
     * 删除退货明细
     * 
     * @param id 退货明细ID
     * @return 结果
     */
    public int deleteScmSaleReturnDetailById(Long id);

    /**
     * 批量删除退货明细
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteScmSaleReturnDetailByIds(Long[] ids);
}
