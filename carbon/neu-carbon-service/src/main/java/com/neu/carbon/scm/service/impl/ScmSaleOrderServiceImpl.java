package com.neu.carbon.scm.service.impl;

import com.neu.carbon.scm.domain.ScmSaleOrder;
import com.neu.carbon.scm.domain.ScmSaleOrderDetail;
import com.neu.carbon.scm.mapper.ScmSaleOrderMapper;
import com.neu.carbon.scm.service.IScmSaleOrderService;
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
 * 销售订单Service业务层处理
 *
 * @author neuedu
 * @date 2022-07-03
 */
@Service
public class ScmSaleOrderServiceImpl implements IScmSaleOrderService {
    @Autowired
    private ScmSaleOrderMapper scmSaleOrderMapper;

    /**
     * 查询销售订单
     *
     * @param id 销售订单ID
     * @return 销售订单
     */
    @Override
    public ScmSaleOrder selectScmSaleOrderById(Long id) {
        return scmSaleOrderMapper.selectScmSaleOrderById(id);
    }

    /**
     * 查询销售订单列表
     *
     * @param scmSaleOrder 销售订单
     * @return 销售订单
     */
    @Override
    public List<ScmSaleOrder> selectScmSaleOrderList(ScmSaleOrder scmSaleOrder) {
        return scmSaleOrderMapper.selectScmSaleOrderList(scmSaleOrder);
    }

    /**
     * 新增销售订单
     *
     * @param scmSaleOrder 销售订单
     * @return 结果
     */
    @Transactional
    @Override
    public int insertScmSaleOrder(ScmSaleOrder scmSaleOrder) {
        scmSaleOrder.setOrderNo(CodeUtil.getSerialNo(SerialNoPrefix.XSDD));
        // 计算合同金额及detail金额
        setOrderAmount(scmSaleOrder);
        int rows = scmSaleOrderMapper.insertScmSaleOrder(scmSaleOrder);
        insertScmSaleOrderDetail(scmSaleOrder);
        return rows;
    }

    /**
     * 修改销售订单
     *
     * @param scmSaleOrder 销售订单
     * @return 结果
     */
    @Transactional
    @Override
    public int updateScmSaleOrder(ScmSaleOrder scmSaleOrder) {
        //审核时不需要更新明细表
        if (StringUtils.isBlank(scmSaleOrder.getAuditStatus())) {
            // 计算合同金额
            setOrderAmount(scmSaleOrder);
            scmSaleOrderMapper.deleteScmSaleOrderDetailByOrderId(scmSaleOrder.getId());
            insertScmSaleOrderDetail(scmSaleOrder);
        }
        return scmSaleOrderMapper.updateScmSaleOrder(scmSaleOrder);
    }

    /**
     * 批量删除销售订单
     *
     * @param ids 需要删除的销售订单ID
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteScmSaleOrderByIds(Long[] ids) {
        scmSaleOrderMapper.deleteScmSaleOrderDetailByOrderIds(ids);
        return scmSaleOrderMapper.deleteScmSaleOrderByIds(ids);
    }

    /**
     * 删除销售订单信息
     *
     * @param id 销售订单ID
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteScmSaleOrderById(Long id) {
        scmSaleOrderMapper.deleteScmSaleOrderDetailByOrderId(id);
        return scmSaleOrderMapper.deleteScmSaleOrderById(id);
    }

    /**
     * 新增订单明细信息
     *
     * @param scmSaleOrder 销售订单对象
     */
    public void insertScmSaleOrderDetail(ScmSaleOrder scmSaleOrder) {
        List<ScmSaleOrderDetail> scmSaleOrderDetailList = scmSaleOrder.getScmSaleOrderDetailList();
        Long id = scmSaleOrder.getId();
        if (StringUtils.isNotNull(scmSaleOrderDetailList)) {
            List<ScmSaleOrderDetail> list = new ArrayList<ScmSaleOrderDetail>();
            for (ScmSaleOrderDetail scmSaleOrderDetail : scmSaleOrderDetailList) {
                scmSaleOrderDetail.setOrderId(id);
                list.add(scmSaleOrderDetail);
            }
            if (list.size() > 0) {
                scmSaleOrderMapper.batchScmSaleOrderDetail(list);
            }
        }
    }


    /**
     * 计算订单金额及详细金额
     *
     * @param scmSaleOrder
     */
    private void setOrderAmount(ScmSaleOrder scmSaleOrder) {
        List<ScmSaleOrderDetail> detailList = scmSaleOrder.getScmSaleOrderDetailList();
        BigDecimal amount = new BigDecimal("0");
        if (detailList != null && detailList.size() > 0) {
            for (ScmSaleOrderDetail detail : detailList) {
                detail.setAmount((detail.getPrice()).multiply(new BigDecimal(detail.getQuantity())));
                amount = amount.add(detail.getAmount());
            }
        }
        scmSaleOrder.setOrderAmount(amount);
    }
}
