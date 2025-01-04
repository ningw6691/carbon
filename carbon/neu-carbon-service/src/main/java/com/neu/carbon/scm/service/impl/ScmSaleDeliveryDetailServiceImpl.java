package com.neu.carbon.scm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.neu.carbon.scm.mapper.ScmSaleDeliveryDetailMapper;
import com.neu.carbon.scm.domain.ScmSaleDeliveryDetail;
import com.neu.carbon.scm.service.IScmSaleDeliveryDetailService;

/**
 * 发货单明细Service业务层处理
 * 
 * @author neuedu
 * @date 2022-07-05
 */
@Service
public class ScmSaleDeliveryDetailServiceImpl implements IScmSaleDeliveryDetailService 
{
    @Autowired
    private ScmSaleDeliveryDetailMapper scmSaleDeliveryDetailMapper;

    /**
     * 查询发货单明细
     * 
     * @param id 发货单明细ID
     * @return 发货单明细
     */
    @Override
    public ScmSaleDeliveryDetail selectScmSaleDeliveryDetailById(Long id)
    {
        return scmSaleDeliveryDetailMapper.selectScmSaleDeliveryDetailById(id);
    }

    /**
     * 查询发货单明细列表
     * 
     * @param scmSaleDeliveryDetail 发货单明细
     * @return 发货单明细
     */
    @Override
    public List<ScmSaleDeliveryDetail> selectScmSaleDeliveryDetailList(ScmSaleDeliveryDetail scmSaleDeliveryDetail)
    {
        return scmSaleDeliveryDetailMapper.selectScmSaleDeliveryDetailList(scmSaleDeliveryDetail);
    }

    /**
     * 新增发货单明细
     * 
     * @param scmSaleDeliveryDetail 发货单明细
     * @return 结果
     */
    @Override
    public int insertScmSaleDeliveryDetail(ScmSaleDeliveryDetail scmSaleDeliveryDetail)
    {
        return scmSaleDeliveryDetailMapper.insertScmSaleDeliveryDetail(scmSaleDeliveryDetail);
    }

    /**
     * 修改发货单明细
     * 
     * @param scmSaleDeliveryDetail 发货单明细
     * @return 结果
     */
    @Override
    public int updateScmSaleDeliveryDetail(ScmSaleDeliveryDetail scmSaleDeliveryDetail)
    {
        return scmSaleDeliveryDetailMapper.updateScmSaleDeliveryDetail(scmSaleDeliveryDetail);
    }

    /**
     * 批量删除发货单明细
     * 
     * @param ids 需要删除的发货单明细ID
     * @return 结果
     */
    @Override
    public int deleteScmSaleDeliveryDetailByIds(Long[] ids)
    {
        return scmSaleDeliveryDetailMapper.deleteScmSaleDeliveryDetailByIds(ids);
    }

    /**
     * 删除发货单明细信息
     * 
     * @param id 发货单明细ID
     * @return 结果
     */
    @Override
    public int deleteScmSaleDeliveryDetailById(Long id)
    {
        return scmSaleDeliveryDetailMapper.deleteScmSaleDeliveryDetailById(id);
    }
}
