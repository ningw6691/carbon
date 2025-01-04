package com.neu.carbon.scm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.neu.carbon.scm.mapper.ScmPurchaseReturnDetailMapper;
import com.neu.carbon.scm.domain.ScmPurchaseReturnDetail;
import com.neu.carbon.scm.service.IScmPurchaseReturnDetailService;

/**
 * 退货明细Service业务层处理
 * 
 * @author neuedu
 * @date 2022-07-07
 */
@Service
public class ScmPurchaseReturnDetailServiceImpl implements IScmPurchaseReturnDetailService 
{
    @Autowired
    private ScmPurchaseReturnDetailMapper scmPurchaseReturnDetailMapper;

    /**
     * 查询退货明细
     * 
     * @param id 退货明细ID
     * @return 退货明细
     */
    @Override
    public ScmPurchaseReturnDetail selectScmPurchaseReturnDetailById(Long id)
    {
        return scmPurchaseReturnDetailMapper.selectScmPurchaseReturnDetailById(id);
    }

    /**
     * 查询退货明细列表
     * 
     * @param scmPurchaseReturnDetail 退货明细
     * @return 退货明细
     */
    @Override
    public List<ScmPurchaseReturnDetail> selectScmPurchaseReturnDetailList(ScmPurchaseReturnDetail scmPurchaseReturnDetail)
    {
        return scmPurchaseReturnDetailMapper.selectScmPurchaseReturnDetailList(scmPurchaseReturnDetail);
    }

    /**
     * 新增退货明细
     * 
     * @param scmPurchaseReturnDetail 退货明细
     * @return 结果
     */
    @Override
    public int insertScmPurchaseReturnDetail(ScmPurchaseReturnDetail scmPurchaseReturnDetail)
    {
        return scmPurchaseReturnDetailMapper.insertScmPurchaseReturnDetail(scmPurchaseReturnDetail);
    }

    /**
     * 修改退货明细
     * 
     * @param scmPurchaseReturnDetail 退货明细
     * @return 结果
     */
    @Override
    public int updateScmPurchaseReturnDetail(ScmPurchaseReturnDetail scmPurchaseReturnDetail)
    {
        return scmPurchaseReturnDetailMapper.updateScmPurchaseReturnDetail(scmPurchaseReturnDetail);
    }

    /**
     * 批量删除退货明细
     * 
     * @param ids 需要删除的退货明细ID
     * @return 结果
     */
    @Override
    public int deleteScmPurchaseReturnDetailByIds(Long[] ids)
    {
        return scmPurchaseReturnDetailMapper.deleteScmPurchaseReturnDetailByIds(ids);
    }

    /**
     * 删除退货明细信息
     * 
     * @param id 退货明细ID
     * @return 结果
     */
    @Override
    public int deleteScmPurchaseReturnDetailById(Long id)
    {
        return scmPurchaseReturnDetailMapper.deleteScmPurchaseReturnDetailById(id);
    }
}
