package com.neu.carbon.scm.service.impl;

import com.neu.carbon.scm.domain.*;
import com.neu.carbon.scm.mapper.ScmSaleContractMapper;
import com.neu.carbon.scm.service.IScmSaleContractService;
import com.neu.carbon.scm.service.IScmSaleCustomerService;
import com.neu.carbon.scm.service.IScmSaleDeliveryService;
import com.neu.common.enums.SerialNoPrefix;
import com.neu.common.utils.StringUtils;
import com.neu.smty.utils.CodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * 销售合同Service业务层处理
 *
 * @author neuedu
 * @date 2022-07-04
 */
@Service
public class ScmSaleContractServiceImpl implements IScmSaleContractService {
    @Autowired
    private ScmSaleContractMapper scmSaleContractMapper;
    @Autowired
    private IScmSaleDeliveryService scmSaleDeliveryService;
    @Autowired
    private IScmSaleCustomerService scmSaleCustomerService;

    /**
     * 查询销售合同
     *
     * @param id 销售合同ID
     * @return 销售合同
     */
    @Override
    public ScmSaleContract selectScmSaleContractById(Long id) {
        return scmSaleContractMapper.selectScmSaleContractById(id);
    }

    /**
     * 查询销售合同列表
     *
     * @param scmSaleContract 销售合同
     * @return 销售合同
     */
    @Override
    public List<ScmSaleContract> selectScmSaleContractList(ScmSaleContract scmSaleContract) {
        return scmSaleContractMapper.selectScmSaleContractList(scmSaleContract);
    }

    /**
     * 新增销售合同
     *
     * @param scmSaleContract 销售合同
     * @return 结果
     */
    @Transactional
    @Override
    public int insertScmSaleContract(ScmSaleContract scmSaleContract) {
        scmSaleContract.setContractNo(CodeUtil.getSerialNo(SerialNoPrefix.XSHT));
        // 计算合同金额及detail金额
        setContractAmount(scmSaleContract);
        int rows = scmSaleContractMapper.insertScmSaleContract(scmSaleContract);
        insertScmSaleContractDetail(scmSaleContract);
        return rows;
    }

    /**
     * 修改销售合同
     *
     * @param scmSaleContract 销售合同
     * @return 结果
     */
    @Transactional
    @Override
    public int updateScmSaleContract(ScmSaleContract scmSaleContract) {
        //审核时不需要更新明细表
        if (StringUtils.isBlank(scmSaleContract.getAuditStatus())) {
            // 计算合同金额及detail金额
            setContractAmount(scmSaleContract);
            scmSaleContractMapper.deleteScmSaleContractDetailByContractId(scmSaleContract.getId());
            insertScmSaleContractDetail(scmSaleContract);
        }
        return scmSaleContractMapper.updateScmSaleContract(scmSaleContract);
    }

    /**
     * 批量删除销售合同
     *
     * @param ids 需要删除的销售合同ID
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteScmSaleContractByIds(Long[] ids) {
        scmSaleContractMapper.deleteScmSaleContractDetailByContractIds(ids);
        return scmSaleContractMapper.deleteScmSaleContractByIds(ids);
    }

    /**
     * 删除销售合同信息
     *
     * @param id 销售合同ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteScmSaleContractById(Long id) {
        scmSaleContractMapper.deleteScmSaleContractDetailByContractId(id);
        return scmSaleContractMapper.deleteScmSaleContractById(id);
    }

    /**
     * 新增合同明细信息
     *
     * @param scmSaleContract 销售合同对象
     */
    public void insertScmSaleContractDetail(ScmSaleContract scmSaleContract) {
        List<ScmSaleContractDetail> scmSaleContractDetailList = scmSaleContract.getScmSaleContractDetailList();
        Long id = scmSaleContract.getId();
        if (StringUtils.isNotNull(scmSaleContractDetailList)) {
            List<ScmSaleContractDetail> list = new ArrayList<ScmSaleContractDetail>();
            for (ScmSaleContractDetail scmSaleContractDetail : scmSaleContractDetailList) {
                scmSaleContractDetail.setContractId(id);
                list.add(scmSaleContractDetail);
            }
            if (list.size() > 0) {
                scmSaleContractMapper.batchScmSaleContractDetail(list);
            }
        }
    }

    /**
     * 计算合同金额及详细金额
     *
     * @param scmSaleContract
     */
    private void setContractAmount(ScmSaleContract scmSaleContract) {
        List<ScmSaleContractDetail> detailList = scmSaleContract.getScmSaleContractDetailList();
        BigDecimal amount = new BigDecimal("0");
        if (detailList != null && detailList.size() > 0) {
            for (ScmSaleContractDetail detail : detailList) {
                detail.setAmount(detail.getPrice().multiply(new BigDecimal(detail.getQuantity())));
                amount = amount.add(detail.getAmount());
            }
        }
        scmSaleContract.setContractAmount(amount);
    }

    /**
     * 修改销售合同
     *
     * @param scmSaleContract 销售合同
     * @return 结果
     */
    @Override
    public int update(ScmSaleContract scmSaleContract) {
        return scmSaleContractMapper.update(scmSaleContract);
    }


    /**
     * 生成发货单
     *
     * @param scmSaleContract 销售合同
     * @return 结果
     */
    @Override
    @Transactional
    public int genDelivery(ScmSaleContract scmSaleContract) {
        ScmSaleContract contract = this.selectScmSaleContractById(scmSaleContract.getId());
        ScmSaleCustomer customer = scmSaleCustomerService.selectScmSaleCustomerById(contract.getCustomerId());
        // 封装ScmSaleDelivery
        ScmSaleDelivery delivery = new ScmSaleDelivery();
        delivery.setContractId(contract.getId());
        delivery.setContractNo(contract.getContractNo());
        delivery.setCustomerId(contract.getCustomerId());
        if (customer != null) {
            delivery.setCustomerName(customer.getName());
        }
        delivery.setAddress(contract.getAddress());
        delivery.setContact(contract.getContact());
        delivery.setContactTel(contract.getContactTel());
        delivery.setDeliveryDate(contract.getDeliveryDate());
        delivery.setDeliveryType(contract.getDeliveryType());
        delivery.setRemark(contract.getRemark());
        // 根据 ScmSaleContractDetail 封装 ScmSaleDeliveryDetail list
        List<ScmSaleDeliveryDetail> deliveryDetailList = new ArrayList<>();
        List<ScmSaleContractDetail> detailList = contract.getScmSaleContractDetailList();
        for (ScmSaleContractDetail item : detailList) {
            ScmSaleDeliveryDetail deliveryDetail = new ScmSaleDeliveryDetail();
            deliveryDetail.setQuantity(item.getQuantity());
            deliveryDetail.setMaterialId(item.getMaterialId());
            deliveryDetail.setDetailRemark(item.getDetailRemark());
            deliveryDetailList.add(deliveryDetail);
        }
        delivery.setScmSaleDeliveryDetailList(deliveryDetailList);
        int r = scmSaleDeliveryService.insertScmSaleDelivery(delivery);
        if (r > 0) {
            ScmSaleContract up = new ScmSaleContract();
            up.setId(scmSaleContract.getId());
            // 设置已生成发货单
            up.setDeliveryFlag("1");
            scmSaleContractMapper.update(up);
        }
        return r;
    }
}
