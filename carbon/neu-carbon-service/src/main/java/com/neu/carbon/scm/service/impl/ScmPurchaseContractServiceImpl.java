package com.neu.carbon.scm.service.impl;

import com.neu.carbon.scm.domain.ScmPurchaseApply;
import com.neu.carbon.scm.domain.ScmPurchaseContract;
import com.neu.carbon.scm.domain.ScmPurchaseContractDetail;
import com.neu.carbon.scm.mapper.ScmPurchaseApplyMapper;
import com.neu.carbon.scm.mapper.ScmPurchaseContractMapper;
import com.neu.carbon.scm.service.IScmPurchaseContractService;
import com.neu.carbon.wms.domain.WmsReplenishApply;
import com.neu.carbon.wms.mapper.WmsReplenishApplyMapper;
import com.neu.common.constant.UserConstants;
import com.neu.common.enums.SerialNoPrefix;
import com.neu.common.utils.DateUtils;
import com.neu.common.utils.StringUtils;
import com.neu.smty.utils.CodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 采购合同Service业务层处理
 *
 * @author neuedu
 * @date 2022-06-30
 */
@Service
public class ScmPurchaseContractServiceImpl implements IScmPurchaseContractService {
    @Autowired
    private ScmPurchaseContractMapper scmPurchaseContractMapper;
    @Autowired
    private ScmPurchaseApplyMapper scmPurchaseApplyMapper;
    @Autowired
    private WmsReplenishApplyMapper wmsReplenishApplyMapper;

    /**
     * 查询采购合同
     *
     * @param id 采购合同ID
     * @return 采购合同
     */
    @Override
    public ScmPurchaseContract selectScmPurchaseContractById(Long id) {
        return scmPurchaseContractMapper.selectScmPurchaseContractById(id);
    }

    /**
     * 查询采购合同列表
     *
     * @param scmPurchaseContract 采购合同
     * @return 采购合同
     */
    @Override
    public List<ScmPurchaseContract> selectScmPurchaseContractList(ScmPurchaseContract scmPurchaseContract) {
        return scmPurchaseContractMapper.selectScmPurchaseContractList(scmPurchaseContract);
    }

    /**
     * 新增采购合同
     *
     * @param scmPurchaseContract 采购合同
     * @return 结果
     */
    @Transactional
    @Override
    public int insertScmPurchaseContract(ScmPurchaseContract scmPurchaseContract) {
        scmPurchaseContract.setContractNo(CodeUtil.getSerialNo(SerialNoPrefix.CGHT));
        scmPurchaseContract.setCreateTime(DateUtils.getNowDate());
        // 计算合同金额及detail金额
        setContractAmount(scmPurchaseContract);
        int rows = scmPurchaseContractMapper.insertScmPurchaseContract(scmPurchaseContract);
        insertScmPurchaseContractDetail(scmPurchaseContract);
        return rows;
    }

    /**
     * 修改采购合同
     *
     * @param scmPurchaseContract 采购合同
     * @return 结果
     */
    @Transactional
    @Override
    public int updateScmPurchaseContract(ScmPurchaseContract scmPurchaseContract) {
        //审核时不需要更新明细表
        if (StringUtils.isBlank(scmPurchaseContract.getAuditStatus())) {
            // 计算合同金额
            setContractAmount(scmPurchaseContract);
            scmPurchaseContractMapper.deleteScmPurchaseContractDetailByContractId(scmPurchaseContract.getId());
            insertScmPurchaseContractDetail(scmPurchaseContract);
        } else {
            // 如果审核通过
            if (UserConstants.AUDIT_STATUS_PASS.equals(scmPurchaseContract.getAuditStatus())) {
                ScmPurchaseApply apply = scmPurchaseApplyMapper.selectScmPurchaseApplyById(scmPurchaseContract.getApplyId());
                // 如果是补货采购申请
                if (apply != null && UserConstants.PURCHASE_APPLY_REPLENISH.equals(apply.getApplyType())) {
                    WmsReplenishApply replenishApply = wmsReplenishApplyMapper.selectWmsReplenishApplyById(apply.getReplenishId());
                    if (replenishApply != null) {
                        // 设置已采购
                        WmsReplenishApply up = new WmsReplenishApply();
                        up.setId(replenishApply.getId());
                        up.setStatus(UserConstants.REPLENISH_STATUS_BUYED);
                        wmsReplenishApplyMapper.update(up);
                    }
                }
            }
        }
        return scmPurchaseContractMapper.updateScmPurchaseContract(scmPurchaseContract);
    }

    /**
     * 批量删除采购合同
     *
     * @param ids 需要删除的采购合同ID
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteScmPurchaseContractByIds(Long[] ids) {
        scmPurchaseContractMapper.deleteScmPurchaseContractDetailByContractIds(ids);
        return scmPurchaseContractMapper.deleteScmPurchaseContractByIds(ids);
    }

    /**
     * 删除采购合同信息
     *
     * @param id 采购合同ID
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteScmPurchaseContractById(Long id) {
        scmPurchaseContractMapper.deleteScmPurchaseContractDetailByContractId(id);
        return scmPurchaseContractMapper.deleteScmPurchaseContractById(id);
    }

    /**
     * 新增合同物料明细信息
     *
     * @param scmPurchaseContract 采购合同对象
     */
    public void insertScmPurchaseContractDetail(ScmPurchaseContract scmPurchaseContract) {
        List<ScmPurchaseContractDetail> scmPurchaseContractDetailList = scmPurchaseContract.getScmPurchaseContractDetailList();
        Long id = scmPurchaseContract.getId();
        if (StringUtils.isNotNull(scmPurchaseContractDetailList)) {
            List<ScmPurchaseContractDetail> list = new ArrayList<ScmPurchaseContractDetail>();
            for (ScmPurchaseContractDetail scmPurchaseContractDetail : scmPurchaseContractDetailList) {
                scmPurchaseContractDetail.setContractId(id);
                list.add(scmPurchaseContractDetail);
            }
            if (list.size() > 0) {
                scmPurchaseContractMapper.batchScmPurchaseContractDetail(list);
            }
        }
    }

    /**
     * 计算合同金额及详细金额
     *
     * @param scmPurchaseContract
     */
    private void setContractAmount(ScmPurchaseContract scmPurchaseContract) {
        List<ScmPurchaseContractDetail> detailList = scmPurchaseContract.getScmPurchaseContractDetailList();
        BigDecimal amount = new BigDecimal("0");
        if (detailList != null && detailList.size() > 0) {
            for (ScmPurchaseContractDetail detail : detailList) {
                detail.setAmount(new BigDecimal(detail.getQuantity()).multiply(detail.getPrice()));
                amount = amount.add(detail.getAmount());
            }
        }
        scmPurchaseContract.setAmount(amount);
    }
}
