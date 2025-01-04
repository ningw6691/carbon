package com.neu.carbon.scm.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.neu.common.annotation.Excel;
import com.neu.common.core.domain.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 销售合同对象 scm_sale_contract
 * 
 * @author neuedu
 * @date 2022-07-04
 */
@ApiModel("销售合同")
public class ScmSaleContract extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    @ApiModelProperty("编号")
    private Long id;

    /** 合同编号 */
    @ApiModelProperty("合同编号")
    @Excel(name = "合同编号")
    private String contractNo;

    /** 订单id */
    @ApiModelProperty("订单id")
    private Long orderId;

    /** 订单编号 */
    @ApiModelProperty("订单编号")
    @Excel(name = "订单编号")
    private String orderNo;

    /** 业务员 */
    @ApiModelProperty("业务员")
    private String workStaff;

    /** 客户id */
    @ApiModelProperty("客户id")
    private Long customerId;

    /** 交货日期 */
    @ApiModelProperty(value="交货日期",example = "2021-09-10")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "交货日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date deliveryDate;

    /** 收货地址 */
    @ApiModelProperty("收货地址")
    @Excel(name = "收货地址")
    private String address;

    /** 送货方式 */
    @ApiModelProperty("送货方式")
    @Excel(name = "送货方式")
    private String deliveryType;

    /** 合同金额 */
    @ApiModelProperty("合同金额")
    @Excel(name = "合同金额")
    private BigDecimal contractAmount;

    /** 签订日期 */
    @ApiModelProperty(value="签订日期",example = "2021-09-10 10:20:40")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "签订日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date signDate;

    /** 申请人 */
    @ApiModelProperty("申请人")
    private String applyUser;

    /** 申请时间 */
    @ApiModelProperty(value="申请时间",example = "2021-09-10 10:20:40")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "申请时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date applyTime;

    /** 申请状态 */
    @ApiModelProperty("申请状态")
    @Excel(name = "申请状态", dictType = "apply_status")
    private String applyStatus;

    /** 审核人 */
    @ApiModelProperty("审核人")
    private String auditUser;

    /** 审核时间 */
    @ApiModelProperty(value="审核时间",example = "2021-09-10 10:20:40")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "审核时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date auditTime;

    /** 审核状态 */
    @ApiModelProperty("审核状态")
    @Excel(name = "审核状态", dictType = "audit_status")
    private String auditStatus;

    /** 审核意见 */
    @ApiModelProperty("审核意见")
    private String auditComment;

    /** 客户名称 */
    @ApiModelProperty("客户名称")
    @Excel(name = "客户名称")
    private String customerName;

    /** 联系人 */
    @ApiModelProperty("联系人")
    @Excel(name = "联系人")
    private String contact;

    /** 联系电话 */
    @ApiModelProperty("联系电话")
    @Excel(name = "联系电话")
    private String contactTel;


    /** 是否生成发货单 */
    @ApiModelProperty("是否生成发货单")
    @Excel(name = "是否生成发货单")
    private String deliveryFlag;

    /** 合同明细信息 */
	@ApiModelProperty(hidden = true)
    private List<ScmSaleContractDetail> scmSaleContractDetailList;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setContractNo(String contractNo) 
    {
        this.contractNo = contractNo;
    }

    public String getContractNo() 
    {
        return contractNo;
    }
    public void setOrderId(Long orderId) 
    {
        this.orderId = orderId;
    }

    public Long getOrderId() 
    {
        return orderId;
    }
    public void setOrderNo(String orderNo) 
    {
        this.orderNo = orderNo;
    }

    public String getOrderNo() 
    {
        return orderNo;
    }
    public void setWorkStaff(String workStaff) 
    {
        this.workStaff = workStaff;
    }

    public String getWorkStaff() 
    {
        return workStaff;
    }
    public void setCustomerId(Long customerId) 
    {
        this.customerId = customerId;
    }

    public Long getCustomerId() 
    {
        return customerId;
    }
    public void setDeliveryDate(Date deliveryDate) 
    {
        this.deliveryDate = deliveryDate;
    }

    public Date getDeliveryDate() 
    {
        return deliveryDate;
    }
    public void setAddress(String address) 
    {
        this.address = address;
    }

    public String getAddress() 
    {
        return address;
    }
    public void setDeliveryType(String deliveryType) 
    {
        this.deliveryType = deliveryType;
    }

    public String getDeliveryType() 
    {
        return deliveryType;
    }
    public void setContractAmount(BigDecimal contractAmount) 
    {
        this.contractAmount = contractAmount;
    }

    public BigDecimal getContractAmount() 
    {
        return contractAmount;
    }
    public void setSignDate(Date signDate) 
    {
        this.signDate = signDate;
    }

    public Date getSignDate() 
    {
        return signDate;
    }
    public void setApplyUser(String applyUser)
    {
        this.applyUser = applyUser;
    }

    public String getApplyUser()
    {
        return applyUser;
    }
    public void setApplyTime(Date applyTime) 
    {
        this.applyTime = applyTime;
    }

    public Date getApplyTime() 
    {
        return applyTime;
    }
    public void setApplyStatus(String applyStatus) 
    {
        this.applyStatus = applyStatus;
    }

    public String getApplyStatus() 
    {
        return applyStatus;
    }
    public void setAuditUser(String auditUser) 
    {
        this.auditUser = auditUser;
    }

    public String getAuditUser() 
    {
        return auditUser;
    }
    public void setAuditTime(Date auditTime) 
    {
        this.auditTime = auditTime;
    }

    public Date getAuditTime() 
    {
        return auditTime;
    }
    public void setAuditStatus(String auditStatus) 
    {
        this.auditStatus = auditStatus;
    }

    public String getAuditStatus() 
    {
        return auditStatus;
    }
    public void setAuditComment(String auditComment) 
    {
        this.auditComment = auditComment;
    }

    public String getAuditComment() 
    {
        return auditComment;
    }

    public List<ScmSaleContractDetail> getScmSaleContractDetailList()
    {
        return scmSaleContractDetailList;
    }

    public void setScmSaleContractDetailList(List<ScmSaleContractDetail> scmSaleContractDetailList)
    {
        this.scmSaleContractDetailList = scmSaleContractDetailList;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContactTel() {
        return contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }

    public String getDeliveryFlag() {
        return deliveryFlag;
    }

    public void setDeliveryFlag(String deliveryFlag) {
        this.deliveryFlag = deliveryFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("contractNo", getContractNo())
            .append("orderId", getOrderId())
            .append("orderNo", getOrderNo())
            .append("workStaff", getWorkStaff())
            .append("customerId", getCustomerId())
            .append("deliveryDate", getDeliveryDate())
            .append("address", getAddress())
            .append("deliveryType", getDeliveryType())
            .append("contractAmount", getContractAmount())
            .append("signDate", getSignDate())
            .append("applyUser", getApplyUser())
            .append("applyTime", getApplyTime())
            .append("applyStatus", getApplyStatus())
            .append("auditUser", getAuditUser())
            .append("auditTime", getAuditTime())
            .append("auditStatus", getAuditStatus())
            .append("auditComment", getAuditComment())
            .append("remark", getRemark())
            .append("scmSaleContractDetailList", getScmSaleContractDetailList())
            .toString();
    }
}
