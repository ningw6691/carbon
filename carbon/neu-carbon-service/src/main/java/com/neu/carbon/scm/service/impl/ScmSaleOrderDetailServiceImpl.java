package com.neu.carbon.scm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.neu.carbon.scm.mapper.ScmSaleOrderDetailMapper;
import com.neu.carbon.scm.domain.ScmSaleOrderDetail;
import com.neu.carbon.scm.service.IScmSaleOrderDetailService;

/**
 * 订单明细Service业务层处理
 * 
 * @author neuedu
 * @date 2022-07-03
 */
@Service
public class ScmSaleOrderDetailServiceImpl implements IScmSaleOrderDetailService 
{
    @Autowired
    private ScmSaleOrderDetailMapper scmSaleOrderDetailMapper;

    /**
     * 查询订单明细
     * 
     * @param id 订单明细ID
     * @return 订单明细
     */
    @Override
    public ScmSaleOrderDetail selectScmSaleOrderDetailById(Long id)
    {
        return scmSaleOrderDetailMapper.selectScmSaleOrderDetailById(id);
    }

    /**
     * 查询订单明细列表
     * 
     * @param scmSaleOrderDetail 订单明细
     * @return 订单明细
     */
    @Override
    public List<ScmSaleOrderDetail> selectScmSaleOrderDetailList(ScmSaleOrderDetail scmSaleOrderDetail)
    {
        return scmSaleOrderDetailMapper.selectScmSaleOrderDetailList(scmSaleOrderDetail);
    }

    /**
     * 新增订单明细
     * 
     * @param scmSaleOrderDetail 订单明细
     * @return 结果
     */
    @Override
    public int insertScmSaleOrderDetail(ScmSaleOrderDetail scmSaleOrderDetail)
    {
        return scmSaleOrderDetailMapper.insertScmSaleOrderDetail(scmSaleOrderDetail);
    }

    /**
     * 修改订单明细
     * 
     * @param scmSaleOrderDetail 订单明细
     * @return 结果
     */
    @Override
    public int updateScmSaleOrderDetail(ScmSaleOrderDetail scmSaleOrderDetail)
    {
        return scmSaleOrderDetailMapper.updateScmSaleOrderDetail(scmSaleOrderDetail);
    }

    /**
     * 批量删除订单明细
     * 
     * @param ids 需要删除的订单明细ID
     * @return 结果
     */
    @Override
    public int deleteScmSaleOrderDetailByIds(Long[] ids)
    {
        return scmSaleOrderDetailMapper.deleteScmSaleOrderDetailByIds(ids);
    }

    /**
     * 删除订单明细信息
     * 
     * @param id 订单明细ID
     * @return 结果
     */
    @Override
    public int deleteScmSaleOrderDetailById(Long id)
    {
        return scmSaleOrderDetailMapper.deleteScmSaleOrderDetailById(id);
    }
}
