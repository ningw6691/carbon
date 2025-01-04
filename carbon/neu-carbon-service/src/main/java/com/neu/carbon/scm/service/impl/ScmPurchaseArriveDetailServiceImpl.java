package com.neu.carbon.scm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.neu.carbon.scm.mapper.ScmPurchaseArriveDetailMapper;
import com.neu.carbon.scm.domain.ScmPurchaseArriveDetail;
import com.neu.carbon.scm.service.IScmPurchaseArriveDetailService;

/**
 * 到货明细Service业务层处理
 * 
 * @author neuedu
 * @date 2022-07-06
 */
@Service
public class ScmPurchaseArriveDetailServiceImpl implements IScmPurchaseArriveDetailService 
{
    @Autowired
    private ScmPurchaseArriveDetailMapper scmPurchaseArriveDetailMapper;

    /**
     * 查询到货明细
     * 
     * @param id 到货明细ID
     * @return 到货明细
     */
    @Override
    public ScmPurchaseArriveDetail selectScmPurchaseArriveDetailById(Long id)
    {
        return scmPurchaseArriveDetailMapper.selectScmPurchaseArriveDetailById(id);
    }

    /**
     * 查询到货明细列表
     * 
     * @param scmPurchaseArriveDetail 到货明细
     * @return 到货明细
     */
    @Override
    public List<ScmPurchaseArriveDetail> selectScmPurchaseArriveDetailList(ScmPurchaseArriveDetail scmPurchaseArriveDetail)
    {
        return scmPurchaseArriveDetailMapper.selectScmPurchaseArriveDetailList(scmPurchaseArriveDetail);
    }

    /**
     * 新增到货明细
     * 
     * @param scmPurchaseArriveDetail 到货明细
     * @return 结果
     */
    @Override
    public int insertScmPurchaseArriveDetail(ScmPurchaseArriveDetail scmPurchaseArriveDetail)
    {
        return scmPurchaseArriveDetailMapper.insertScmPurchaseArriveDetail(scmPurchaseArriveDetail);
    }

    /**
     * 修改到货明细
     * 
     * @param scmPurchaseArriveDetail 到货明细
     * @return 结果
     */
    @Override
    public int updateScmPurchaseArriveDetail(ScmPurchaseArriveDetail scmPurchaseArriveDetail)
    {
        return scmPurchaseArriveDetailMapper.updateScmPurchaseArriveDetail(scmPurchaseArriveDetail);
    }

    /**
     * 批量删除到货明细
     * 
     * @param ids 需要删除的到货明细ID
     * @return 结果
     */
    @Override
    public int deleteScmPurchaseArriveDetailByIds(Long[] ids)
    {
        return scmPurchaseArriveDetailMapper.deleteScmPurchaseArriveDetailByIds(ids);
    }

    /**
     * 删除到货明细信息
     * 
     * @param id 到货明细ID
     * @return 结果
     */
    @Override
    public int deleteScmPurchaseArriveDetailById(Long id)
    {
        return scmPurchaseArriveDetailMapper.deleteScmPurchaseArriveDetailById(id);
    }
}
