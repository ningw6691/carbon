package com.neu.carbon.scm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.neu.carbon.scm.mapper.ScmSalePlanDetailMapper;
import com.neu.carbon.scm.domain.ScmSalePlanDetail;
import com.neu.carbon.scm.service.IScmSalePlanDetailService;

/**
 * 指标配置Service业务层处理
 * 
 * @author neuedu
 * @date 2022-07-13
 */
@Service
public class ScmSalePlanDetailServiceImpl implements IScmSalePlanDetailService 
{
    @Autowired
    private ScmSalePlanDetailMapper scmSalePlanDetailMapper;

    /**
     * 查询指标配置
     * 
     * @param id 指标配置ID
     * @return 指标配置
     */
    @Override
    public ScmSalePlanDetail selectScmSalePlanDetailById(Long id)
    {
        return scmSalePlanDetailMapper.selectScmSalePlanDetailById(id);
    }

    /**
     * 查询指标配置列表
     * 
     * @param scmSalePlanDetail 指标配置
     * @return 指标配置
     */
    @Override
    public List<ScmSalePlanDetail> selectScmSalePlanDetailList(ScmSalePlanDetail scmSalePlanDetail)
    {
        return scmSalePlanDetailMapper.selectScmSalePlanDetailList(scmSalePlanDetail);
    }

    /**
     * 新增指标配置
     * 
     * @param scmSalePlanDetail 指标配置
     * @return 结果
     */
    @Override
    public int insertScmSalePlanDetail(ScmSalePlanDetail scmSalePlanDetail)
    {
        return scmSalePlanDetailMapper.insertScmSalePlanDetail(scmSalePlanDetail);
    }

    /**
     * 修改指标配置
     * 
     * @param scmSalePlanDetail 指标配置
     * @return 结果
     */
    @Override
    public int updateScmSalePlanDetail(ScmSalePlanDetail scmSalePlanDetail)
    {
        return scmSalePlanDetailMapper.updateScmSalePlanDetail(scmSalePlanDetail);
    }

    /**
     * 批量删除指标配置
     * 
     * @param ids 需要删除的指标配置ID
     * @return 结果
     */
    @Override
    public int deleteScmSalePlanDetailByIds(Long[] ids)
    {
        return scmSalePlanDetailMapper.deleteScmSalePlanDetailByIds(ids);
    }

    /**
     * 删除指标配置信息
     * 
     * @param id 指标配置ID
     * @return 结果
     */
    @Override
    public int deleteScmSalePlanDetailById(Long id)
    {
        return scmSalePlanDetailMapper.deleteScmSalePlanDetailById(id);
    }
}
