package com.neu.carbon.scm.mapper;

import java.util.List;
import com.neu.carbon.scm.domain.ScmSaleOrderDetail;

/**
 * 订单明细Mapper接口
 * 
 * @author neuedu
 * @date 2022-07-03
 */
public interface ScmSaleOrderDetailMapper 
{
    /**
     * 查询订单明细
     * 
     * @param id 订单明细ID
     * @return 订单明细
     */
    public ScmSaleOrderDetail selectScmSaleOrderDetailById(Long id);

    /**
     * 查询订单明细列表
     * 
     * @param scmSaleOrderDetail 订单明细
     * @return 订单明细集合
     */
    public List<ScmSaleOrderDetail> selectScmSaleOrderDetailList(ScmSaleOrderDetail scmSaleOrderDetail);

    /**
     * 新增订单明细
     * 
     * @param scmSaleOrderDetail 订单明细
     * @return 结果
     */
    public int insertScmSaleOrderDetail(ScmSaleOrderDetail scmSaleOrderDetail);

    /**
     * 修改订单明细
     * 
     * @param scmSaleOrderDetail 订单明细
     * @return 结果
     */
    public int updateScmSaleOrderDetail(ScmSaleOrderDetail scmSaleOrderDetail);

    /**
     * 删除订单明细
     * 
     * @param id 订单明细ID
     * @return 结果
     */
    public int deleteScmSaleOrderDetailById(Long id);

    /**
     * 批量删除订单明细
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteScmSaleOrderDetailByIds(Long[] ids);
}
