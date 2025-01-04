package com.neu.carbon.scm.service.impl;

import java.util.List;

import com.neu.common.enums.SerialNoPrefix;
import com.neu.common.utils.DateUtils;
import com.neu.smty.utils.CodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.neu.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.neu.carbon.scm.domain.ScmSalePlanDetail;
import com.neu.carbon.scm.mapper.ScmSalePlanMapper;
import com.neu.carbon.scm.domain.ScmSalePlan;
import com.neu.carbon.scm.service.IScmSalePlanService;

/**
 * 销售计划Service业务层处理
 * 
 * @author neuedu
 * @date 2022-07-13
 */
@Service
public class ScmSalePlanServiceImpl implements IScmSalePlanService 
{
    @Autowired
    private ScmSalePlanMapper scmSalePlanMapper;

    /**
     * 查询销售计划
     * 
     * @param id 销售计划ID
     * @return 销售计划
     */
    @Override
    public ScmSalePlan selectScmSalePlanById(Long id)
    {
        return scmSalePlanMapper.selectScmSalePlanById(id);
    }

    /**
     * 查询销售计划列表
     * 
     * @param scmSalePlan 销售计划
     * @return 销售计划
     */
    @Override
    public List<ScmSalePlan> selectScmSalePlanList(ScmSalePlan scmSalePlan)
    {
        return scmSalePlanMapper.selectScmSalePlanList(scmSalePlan);
    }

    /**
     * 新增销售计划
     * 
     * @param scmSalePlan 销售计划
     * @return 结果
     */
    @Transactional
    @Override
    public int insertScmSalePlan(ScmSalePlan scmSalePlan)
    {
        scmSalePlan.setPlanNo(CodeUtil.getSerialNo(SerialNoPrefix.XSJH));
        scmSalePlan.setCreateTime(DateUtils.getNowDate());
        int rows = scmSalePlanMapper.insertScmSalePlan(scmSalePlan);
        insertScmSalePlanDetail(scmSalePlan);
        return rows;
    }

    /**
     * 修改销售计划
     * 
     * @param scmSalePlan 销售计划
     * @return 结果
     */
    @Transactional
    @Override
    public int updateScmSalePlan(ScmSalePlan scmSalePlan)
    {
        scmSalePlanMapper.deleteScmSalePlanDetailByPlanId(scmSalePlan.getId());
        insertScmSalePlanDetail(scmSalePlan);
        return scmSalePlanMapper.updateScmSalePlan(scmSalePlan);
    }

    /**
     * 批量删除销售计划
     * 
     * @param ids 需要删除的销售计划ID
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteScmSalePlanByIds(Long[] ids)
    {
        scmSalePlanMapper.deleteScmSalePlanDetailByPlanIds(ids);
        return scmSalePlanMapper.deleteScmSalePlanByIds(ids);
    }

    /**
     * 删除销售计划信息
     * 
     * @param id 销售计划ID
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteScmSalePlanById(Long id)
    {
        scmSalePlanMapper.deleteScmSalePlanDetailByPlanId(id);
        return scmSalePlanMapper.deleteScmSalePlanById(id);
    }

    /**
     * 新增指标配置信息
     * 
     * @param scmSalePlan 销售计划对象
     */
    public void insertScmSalePlanDetail(ScmSalePlan scmSalePlan)
    {
        List<ScmSalePlanDetail> scmSalePlanDetailList = scmSalePlan.getScmSalePlanDetailList();
        Long id = scmSalePlan.getId();
        if (StringUtils.isNotNull(scmSalePlanDetailList))
        {
            List<ScmSalePlanDetail> list = new ArrayList<ScmSalePlanDetail>();
            for (ScmSalePlanDetail scmSalePlanDetail : scmSalePlanDetailList)
            {
                scmSalePlanDetail.setPlanId(id);
                list.add(scmSalePlanDetail);
            }
            if (list.size() > 0)
            {
                scmSalePlanMapper.batchScmSalePlanDetail(list);
            }
        }
    }
}
