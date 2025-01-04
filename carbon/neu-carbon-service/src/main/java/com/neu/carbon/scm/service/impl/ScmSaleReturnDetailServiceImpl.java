package com.neu.carbon.scm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.neu.carbon.scm.mapper.ScmSaleReturnDetailMapper;
import com.neu.carbon.scm.domain.ScmSaleReturnDetail;
import com.neu.carbon.scm.service.IScmSaleReturnDetailService;

/**
 * 退货明细Service业务层处理
 * 
 * @author neuedu
 * @date 2022-07-08
 */
@Service
public class ScmSaleReturnDetailServiceImpl implements IScmSaleReturnDetailService 
{
    @Autowired
    private ScmSaleReturnDetailMapper scmSaleReturnDetailMapper;

    /**
     * 查询退货明细
     * 
     * @param id 退货明细ID
     * @return 退货明细
     */
    @Override
    public ScmSaleReturnDetail selectScmSaleReturnDetailById(Long id)
    {
        return scmSaleReturnDetailMapper.selectScmSaleReturnDetailById(id);
    }

    /**
     * 查询退货明细列表
     * 
     * @param scmSaleReturnDetail 退货明细
     * @return 退货明细
     */
    @Override
    public List<ScmSaleReturnDetail> selectScmSaleReturnDetailList(ScmSaleReturnDetail scmSaleReturnDetail)
    {
        return scmSaleReturnDetailMapper.selectScmSaleReturnDetailList(scmSaleReturnDetail);
    }

    /**
     * 新增退货明细
     * 
     * @param scmSaleReturnDetail 退货明细
     * @return 结果
     */
    @Override
    public int insertScmSaleReturnDetail(ScmSaleReturnDetail scmSaleReturnDetail)
    {
        return scmSaleReturnDetailMapper.insertScmSaleReturnDetail(scmSaleReturnDetail);
    }

    /**
     * 修改退货明细
     * 
     * @param scmSaleReturnDetail 退货明细
     * @return 结果
     */
    @Override
    public int updateScmSaleReturnDetail(ScmSaleReturnDetail scmSaleReturnDetail)
    {
        return scmSaleReturnDetailMapper.updateScmSaleReturnDetail(scmSaleReturnDetail);
    }

    /**
     * 批量删除退货明细
     * 
     * @param ids 需要删除的退货明细ID
     * @return 结果
     */
    @Override
    public int deleteScmSaleReturnDetailByIds(Long[] ids)
    {
        return scmSaleReturnDetailMapper.deleteScmSaleReturnDetailByIds(ids);
    }

    /**
     * 删除退货明细信息
     * 
     * @param id 退货明细ID
     * @return 结果
     */
    @Override
    public int deleteScmSaleReturnDetailById(Long id)
    {
        return scmSaleReturnDetailMapper.deleteScmSaleReturnDetailById(id);
    }
}
