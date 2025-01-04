package com.neu.carbon.scm.service.impl;

import com.neu.carbon.scm.domain.ScmSaleDeliveryDetail;
import com.neu.carbon.scm.domain.ScmSaleReturn;
import com.neu.carbon.scm.domain.ScmSaleReturnDetail;
import com.neu.carbon.scm.mapper.ScmSaleDeliveryDetailMapper;
import com.neu.carbon.scm.mapper.ScmSaleReturnMapper;
import com.neu.carbon.scm.service.IScmSaleReturnService;
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
 * 销售退货Service业务层处理
 *
 * @author neuedu
 * @date 2022-07-08
 */
@Service
public class ScmSaleReturnServiceImpl implements IScmSaleReturnService {
    @Autowired
    private ScmSaleReturnMapper scmSaleReturnMapper;
    @Autowired
    private ScmSaleDeliveryDetailMapper scmSaleDeliveryDetailMapper;

    /**
     * 查询销售退货
     *
     * @param id 销售退货ID
     * @return 销售退货
     */
    @Override
    public ScmSaleReturn selectScmSaleReturnById(Long id) {
        return scmSaleReturnMapper.selectScmSaleReturnById(id);
    }

    /**
     * 查询销售退货列表
     *
     * @param scmSaleReturn 销售退货
     * @return 销售退货
     */
    @Override
    public List<ScmSaleReturn> selectScmSaleReturnList(ScmSaleReturn scmSaleReturn) {
        return scmSaleReturnMapper.selectScmSaleReturnList(scmSaleReturn);
    }

    /**
     * 新增销售退货
     *
     * @param scmSaleReturn 销售退货
     * @return 结果
     */
    @Transactional
    @Override
    public int insertScmSaleReturn(ScmSaleReturn scmSaleReturn) {
        scmSaleReturn.setReturnNo(CodeUtil.getSerialNo(SerialNoPrefix.CGTH));
        scmSaleReturn.setCreateTime(DateUtils.getNowDate());
        // 计算退款金额
        setReturnAmount(scmSaleReturn);
        int rows = scmSaleReturnMapper.insertScmSaleReturn(scmSaleReturn);
        insertScmSaleReturnDetail(scmSaleReturn);
        if (rows > 0) {
            // 设置退款数量
            updateDeliveryDetailList(scmSaleReturn);
        }
        return rows;
    }

    /**
     * 修改销售退货
     *
     * @param scmSaleReturn 销售退货
     * @return 结果
     */
    @Transactional
    @Override
    public int updateScmSaleReturn(ScmSaleReturn scmSaleReturn) {
        // 计算退款金额
        setReturnAmount(scmSaleReturn);
        scmSaleReturnMapper.deleteScmSaleReturnDetailByReturnId(scmSaleReturn.getId());
        insertScmSaleReturnDetail(scmSaleReturn);
        int r = scmSaleReturnMapper.updateScmSaleReturn(scmSaleReturn);
        if (r > 0) {
            // 设置退款数量
            updateDeliveryDetailList(scmSaleReturn);
        }
        return r;
    }

    /**
     * 批量删除销售退货
     *
     * @param ids 需要删除的销售退货ID
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteScmSaleReturnByIds(Long[] ids) {
        scmSaleReturnMapper.deleteScmSaleReturnDetailByReturnIds(ids);
        return scmSaleReturnMapper.deleteScmSaleReturnByIds(ids);
    }

    /**
     * 删除销售退货信息
     *
     * @param id 销售退货ID
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteScmSaleReturnById(Long id) {
        scmSaleReturnMapper.deleteScmSaleReturnDetailByReturnId(id);
        return scmSaleReturnMapper.deleteScmSaleReturnById(id);
    }

    /**
     * 新增退货明细信息
     *
     * @param scmSaleReturn 销售退货对象
     */
    public void insertScmSaleReturnDetail(ScmSaleReturn scmSaleReturn) {
        List<ScmSaleReturnDetail> scmSaleReturnDetailList = scmSaleReturn.getScmSaleReturnDetailList();
        Long id = scmSaleReturn.getId();
        if (StringUtils.isNotNull(scmSaleReturnDetailList)) {
            List<ScmSaleReturnDetail> list = new ArrayList<ScmSaleReturnDetail>();
            for (ScmSaleReturnDetail scmSaleReturnDetail : scmSaleReturnDetailList) {
                scmSaleReturnDetail.setReturnId(id);
                list.add(scmSaleReturnDetail);
            }
            if (list.size() > 0) {
                scmSaleReturnMapper.batchScmSaleReturnDetail(list);
            }
        }
    }

    /**
     * 更新销售发货单详细退货数量
     *
     * @param scmSaleReturn
     */
    private void updateDeliveryDetailList(ScmSaleReturn scmSaleReturn) {
        Long deliveryId = scmSaleReturn.getDeliveryId();
        if (deliveryId != null) {
            List<ScmSaleReturnDetail> scmSaleReturnDetailList = scmSaleReturn.getScmSaleReturnDetailList();
            if (scmSaleReturnDetailList != null && scmSaleReturnDetailList.size() > 0) {
                Map<Long, List<ScmSaleReturnDetail>> groupMap = scmSaleReturnDetailList.stream().filter(item -> StringUtils.isNotNull(item.getReturnQuantity())).collect(Collectors.groupingBy(ScmSaleReturnDetail::getMaterialId));
                groupMap.forEach((materialId, returnDetailList) -> {
                    ScmSaleDeliveryDetail cond = new ScmSaleDeliveryDetail();
                    cond.setDeliveryId(deliveryId);
                    cond.setMaterialId(materialId);
                    List<ScmSaleDeliveryDetail> list = scmSaleDeliveryDetailMapper.selectScmSaleDeliveryDetailList(cond);
                    if (list != null && list.size() > 0) {
                        double returnQuantity = returnDetailList.stream().mapToDouble(ScmSaleReturnDetail::getReturnQuantity).sum();
                        ScmSaleDeliveryDetail detail = list.get(0);
                        ScmSaleDeliveryDetail up = new ScmSaleDeliveryDetail();
                        up.setId(detail.getId());
                        up.setReturnQuantity(returnQuantity);
                        scmSaleDeliveryDetailMapper.updateScmSaleDeliveryDetail(up);
                    }
                });
            }
        }
    }

    /**
     * 计算退款金额
     *
     * @param saleReturn
     */
    private void setReturnAmount(ScmSaleReturn saleReturn) {
        List<ScmSaleReturnDetail> detailList = saleReturn.getScmSaleReturnDetailList();
        BigDecimal amount = new BigDecimal("0");
        if (detailList != null && detailList.size() > 0) {
            for (ScmSaleReturnDetail detail : detailList) {
                amount = amount.add((detail.getPrice().multiply(new BigDecimal(detail.getReturnQuantity()))));
            }
        }
        saleReturn.setReturnAmount(amount);
    }
}
