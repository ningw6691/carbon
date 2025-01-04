package com.neu.carbon.scm.service.impl;

import com.neu.carbon.scm.domain.*;
import com.neu.carbon.scm.mapper.ScmPurchaseArriveDetailMapper;
import com.neu.carbon.scm.mapper.ScmPurchaseReturnMapper;
import com.neu.carbon.scm.service.IScmPurchaseReturnService;
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
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 采购退货Service业务层处理
 *
 * @author neuedu
 * @date 2022-07-07
 */
@Service
public class ScmPurchaseReturnServiceImpl implements IScmPurchaseReturnService {
    @Autowired
    private ScmPurchaseReturnMapper scmPurchaseReturnMapper;

    @Autowired
    private ScmPurchaseArriveDetailMapper scmPurchaseArriveDetailMapper;

    /**
     * 查询采购退货
     *
     * @param id 采购退货ID
     * @return 采购退货
     */
    @Override
    public ScmPurchaseReturn selectScmPurchaseReturnById(Long id) {
        return scmPurchaseReturnMapper.selectScmPurchaseReturnById(id);
    }

    /**
     * 查询采购退货列表
     *
     * @param scmPurchaseReturn 采购退货
     * @return 采购退货
     */
    @Override
    public List<ScmPurchaseReturn> selectScmPurchaseReturnList(ScmPurchaseReturn scmPurchaseReturn) {
        return scmPurchaseReturnMapper.selectScmPurchaseReturnList(scmPurchaseReturn);
    }

    /**
     * 新增采购退货
     *
     * @param scmPurchaseReturn 采购退货
     * @return 结果
     */
    @Transactional
    @Override
    public int insertScmPurchaseReturn(ScmPurchaseReturn scmPurchaseReturn) {
        scmPurchaseReturn.setReturnNo(CodeUtil.getSerialNo(SerialNoPrefix.CGTH));
        scmPurchaseReturn.setCreateTime(DateUtils.getNowDate());
        // 计算退款金额
        setReturnAmount(scmPurchaseReturn);
        int rows = scmPurchaseReturnMapper.insertScmPurchaseReturn(scmPurchaseReturn);
        insertScmPurchaseReturnDetail(scmPurchaseReturn);
        if (rows > 0) {
            updateArriveDetailList(scmPurchaseReturn);
        }
        return rows;
    }

    /**
     * 修改采购退货
     *
     * @param scmPurchaseReturn 采购退货
     * @return 结果
     */
    @Transactional
    @Override
    public int updateScmPurchaseReturn(ScmPurchaseReturn scmPurchaseReturn) {
        // 计算退款金额
        setReturnAmount(scmPurchaseReturn);
        scmPurchaseReturnMapper.deleteScmPurchaseReturnDetailByReturnId(scmPurchaseReturn.getId());
        insertScmPurchaseReturnDetail(scmPurchaseReturn);
        int r = scmPurchaseReturnMapper.updateScmPurchaseReturn(scmPurchaseReturn);
        if (r > 0) {
            updateArriveDetailList(scmPurchaseReturn);
        }
        return r;
    }

    /**
     * 批量删除采购退货
     *
     * @param ids 需要删除的采购退货ID
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteScmPurchaseReturnByIds(Long[] ids) {
        scmPurchaseReturnMapper.deleteScmPurchaseReturnDetailByReturnIds(ids);
        return scmPurchaseReturnMapper.deleteScmPurchaseReturnByIds(ids);
    }

    /**
     * 删除采购退货信息
     *
     * @param id 采购退货ID
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteScmPurchaseReturnById(Long id) {
        scmPurchaseReturnMapper.deleteScmPurchaseReturnDetailByReturnId(id);
        return scmPurchaseReturnMapper.deleteScmPurchaseReturnById(id);
    }

    /**
     * 新增退货明细信息
     *
     * @param scmPurchaseReturn 采购退货对象
     */
    public void insertScmPurchaseReturnDetail(ScmPurchaseReturn scmPurchaseReturn) {
        List<ScmPurchaseReturnDetail> scmPurchaseReturnDetailList = scmPurchaseReturn.getScmPurchaseReturnDetailList();
        Long id = scmPurchaseReturn.getId();
        if (StringUtils.isNotNull(scmPurchaseReturnDetailList)) {
            List<ScmPurchaseReturnDetail> list = new ArrayList<ScmPurchaseReturnDetail>();
            for (ScmPurchaseReturnDetail scmPurchaseReturnDetail : scmPurchaseReturnDetailList) {
                scmPurchaseReturnDetail.setReturnId(id);
                list.add(scmPurchaseReturnDetail);
            }
            if (list.size() > 0) {
                scmPurchaseReturnMapper.batchScmPurchaseReturnDetail(list);
            }
        }
    }


    /**
     * 更新采购到货单详细退货数量
     *
     * @param scmPurchaseReturn
     */
    private void updateArriveDetailList(ScmPurchaseReturn scmPurchaseReturn) {
        Long arriveId = scmPurchaseReturn.getArriveId();
        if (arriveId != null) {
            List<ScmPurchaseReturnDetail> scmPurchaseReturnDetailList = scmPurchaseReturn.getScmPurchaseReturnDetailList();

            Map<Long, List<ScmPurchaseReturnDetail>> groupMap = scmPurchaseReturnDetailList.stream().filter(item -> StringUtils.isNotNull(item.getReturnQuantity())).collect(Collectors.groupingBy(ScmPurchaseReturnDetail::getMaterialId));
            groupMap.forEach((materialId, returnDetailList) -> {
                ScmPurchaseArriveDetail cond = new ScmPurchaseArriveDetail();
                cond.setArriveId(arriveId);
                cond.setMaterialId(materialId);
                List<ScmPurchaseArriveDetail> list = scmPurchaseArriveDetailMapper.selectScmPurchaseArriveDetailList(cond);
                if (list != null && list.size() > 0) {
                    double returnTotal = returnDetailList.stream().mapToDouble(ScmPurchaseReturnDetail::getReturnQuantity).sum();
                    ScmPurchaseArriveDetail detail = list.get(0);
                    ScmPurchaseArriveDetail up = new ScmPurchaseArriveDetail();
                    up.setId(detail.getId());
                    up.setReturnQuantity(returnTotal);
                    scmPurchaseArriveDetailMapper.updateScmPurchaseArriveDetail(up);
                }
            });

            // 遍历退货单明细，更新到货单明细退货数据
            for (ScmPurchaseReturnDetail item : scmPurchaseReturnDetailList) {
                ScmPurchaseArriveDetail cond = new ScmPurchaseArriveDetail();
                cond.setArriveId(arriveId);
                cond.setBatchNo(item.getBatchNo());
                cond.setMaterialId(item.getMaterialId());
                List<ScmPurchaseArriveDetail> list = scmPurchaseArriveDetailMapper.selectScmPurchaseArriveDetailList(cond);
                if (list != null && list.size() > 0) {
                    ScmPurchaseArriveDetail detail = list.get(0);
                    ScmPurchaseArriveDetail up = new ScmPurchaseArriveDetail();
                    up.setId(detail.getId());
                    up.setReturnQuantity(item.getReturnQuantity());
                    scmPurchaseArriveDetailMapper.updateScmPurchaseArriveDetail(up);
                }
            }
        }
    }

    /**
     * 计算退款金额
     *
     * @param scmPurchaseReturn
     */
    private void setReturnAmount(ScmPurchaseReturn scmPurchaseReturn) {
        List<ScmPurchaseReturnDetail> detailList = scmPurchaseReturn.getScmPurchaseReturnDetailList();
        BigDecimal amount = new BigDecimal("0");
        if (detailList != null && detailList.size() > 0) {
            for (ScmPurchaseReturnDetail detail : detailList) {
                amount = amount.add((detail.getPrice().multiply(new BigDecimal(detail.getReturnQuantity()))));
            }
        }
        scmPurchaseReturn.setReturnAmount(amount);
    }
}
