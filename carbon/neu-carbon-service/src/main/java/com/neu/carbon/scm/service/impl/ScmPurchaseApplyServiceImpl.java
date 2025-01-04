package com.neu.carbon.scm.service.impl;

import java.util.List;

import com.neu.common.enums.SerialNoPrefix;
import com.neu.smty.utils.CodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.neu.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.neu.carbon.scm.domain.ScmPurchaseApplyDetail;
import com.neu.carbon.scm.mapper.ScmPurchaseApplyMapper;
import com.neu.carbon.scm.domain.ScmPurchaseApply;
import com.neu.carbon.scm.service.IScmPurchaseApplyService;

/**
 * 采购申请Service业务层处理
 * 
 * @author neuedu
 * @date 2022-06-29
 */
@Service
public class ScmPurchaseApplyServiceImpl implements IScmPurchaseApplyService 
{
    @Autowired
    private ScmPurchaseApplyMapper scmPurchaseApplyMapper;

    /**
     * 查询采购申请
     * 
     * @param id 采购申请ID
     * @return 采购申请
     */
    @Override
    public ScmPurchaseApply selectScmPurchaseApplyById(Long id)
    {
        return scmPurchaseApplyMapper.selectScmPurchaseApplyById(id);
    }

    /**
     * 查询采购申请列表
     * 
     * @param scmPurchaseApply 采购申请
     * @return 采购申请
     */
    @Override
    public List<ScmPurchaseApply> selectScmPurchaseApplyList(ScmPurchaseApply scmPurchaseApply)
    {
        return scmPurchaseApplyMapper.selectScmPurchaseApplyList(scmPurchaseApply);
    }

    /**
     * 新增采购申请
     * 
     * @param scmPurchaseApply 采购申请
     * @return 结果
     */
    @Transactional
    @Override
    public int insertScmPurchaseApply(ScmPurchaseApply scmPurchaseApply)
    {
        scmPurchaseApply.setApplyNo(CodeUtil.getSerialNo(SerialNoPrefix.CGSQ));
        int rows = scmPurchaseApplyMapper.insertScmPurchaseApply(scmPurchaseApply);
        insertScmPurchaseApplyDetail(scmPurchaseApply);
        return rows;
    }

    /**
     * 修改采购申请
     * 
     * @param scmPurchaseApply 采购申请
     * @return 结果
     */
    @Transactional
    @Override
    public int updateScmPurchaseApply(ScmPurchaseApply scmPurchaseApply)
    {
        //审核时不需要更新明细表
        if(StringUtils.isBlank(scmPurchaseApply.getAuditStatus())) {
            scmPurchaseApplyMapper.deleteScmPurchaseApplyDetailByApplyId(scmPurchaseApply.getId());
            insertScmPurchaseApplyDetail(scmPurchaseApply);
        }
        return scmPurchaseApplyMapper.updateScmPurchaseApply(scmPurchaseApply);
    }

    /**
     * 批量删除采购申请
     * 
     * @param ids 需要删除的采购申请ID
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteScmPurchaseApplyByIds(Long[] ids)
    {
        scmPurchaseApplyMapper.deleteScmPurchaseApplyDetailByApplyIds(ids);
        return scmPurchaseApplyMapper.deleteScmPurchaseApplyByIds(ids);
    }

    /**
     * 删除采购申请信息
     * 
     * @param id 采购申请ID
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteScmPurchaseApplyById(Long id)
    {
        scmPurchaseApplyMapper.deleteScmPurchaseApplyDetailByApplyId(id);
        return scmPurchaseApplyMapper.deleteScmPurchaseApplyById(id);
    }

    /**
     * 新增采购申请明细信息
     * 
     * @param scmPurchaseApply 采购申请对象
     */
    public void insertScmPurchaseApplyDetail(ScmPurchaseApply scmPurchaseApply)
    {
        List<ScmPurchaseApplyDetail> scmPurchaseApplyDetailList = scmPurchaseApply.getScmPurchaseApplyDetailList();
        Long id = scmPurchaseApply.getId();
        if (StringUtils.isNotNull(scmPurchaseApplyDetailList))
        {
            List<ScmPurchaseApplyDetail> list = new ArrayList<ScmPurchaseApplyDetail>();
            for (ScmPurchaseApplyDetail scmPurchaseApplyDetail : scmPurchaseApplyDetailList)
            {
                scmPurchaseApplyDetail.setApplyId(id);
                list.add(scmPurchaseApplyDetail);
            }
            if (list.size() > 0)
            {
                scmPurchaseApplyMapper.batchScmPurchaseApplyDetail(list);
            }
        }
    }
}
