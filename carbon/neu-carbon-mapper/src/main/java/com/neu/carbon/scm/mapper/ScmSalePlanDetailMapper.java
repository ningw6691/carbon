package com.neu.carbon.scm.mapper;

import java.util.List;
import com.neu.carbon.scm.domain.ScmSalePlanDetail;

/**
 * 指标配置Mapper接口
 * 
 * @author neuedu
 * @date 2022-07-13
 */
public interface ScmSalePlanDetailMapper 
{
    /**
     * 查询指标配置
     * 
     * @param id 指标配置ID
     * @return 指标配置
     */
    public ScmSalePlanDetail selectScmSalePlanDetailById(Long id);

    /**
     * 查询指标配置列表
     * 
     * @param scmSalePlanDetail 指标配置
     * @return 指标配置集合
     */
    public List<ScmSalePlanDetail> selectScmSalePlanDetailList(ScmSalePlanDetail scmSalePlanDetail);

    /**
     * 新增指标配置
     * 
     * @param scmSalePlanDetail 指标配置
     * @return 结果
     */
    public int insertScmSalePlanDetail(ScmSalePlanDetail scmSalePlanDetail);

    /**
     * 修改指标配置
     * 
     * @param scmSalePlanDetail 指标配置
     * @return 结果
     */
    public int updateScmSalePlanDetail(ScmSalePlanDetail scmSalePlanDetail);

    /**
     * 删除指标配置
     * 
     * @param id 指标配置ID
     * @return 结果
     */
    public int deleteScmSalePlanDetailById(Long id);

    /**
     * 批量删除指标配置
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteScmSalePlanDetailByIds(Long[] ids);
}
