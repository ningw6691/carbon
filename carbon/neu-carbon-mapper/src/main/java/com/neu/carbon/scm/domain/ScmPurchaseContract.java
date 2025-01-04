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
 * 采购合同对象 scm_purchase_contract
 * 
 * @author neuedu
 * @date 2022-06-30
 */
@ApiModel("采购合同")
public class ScmPurchaseContract extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    @ApiModelProperty("编号")
    private Long id;

    /** 合同编号 */
    @ApiModelProperty("合同编号")
    @Excel(name = "合同编号")
    private String contractNo;

    /** 申请id */
    @ApiModelProperty("申请id")
    @Excel(name = "申请id")
    private Long applyId;

    /** 申请编号 */
    @ApiModelProperty("申请编号")
    @Excel(name = "申请编号")
    private String applyNo;

    /** 供应商id */
    @ApiModelProperty("供应商id")
    @Excel(name = "供应商id")
    private Long supplierId;

    /** 供应商id */
    @ApiModelProperty("供应商id")
    @Excel(name = "供应商id")
    private String supplierName;

    /** 联系人 */
    @ApiModelProperty("联系人")
    @Excel(name = "联系人")
    private String supplierContact;

    /** 联系电话 */
    @ApiModelProperty("联系电话")
    @Excel(name = "联系电话")
    private String supplierContactTel;

    /** 金额 */
    @ApiModelProperty("金额")
    @Excel(name = "金额")
    private BigDecimal amount;

    /** 送货方式 */
    @ApiModelProperty("送货方式")
    @Excel(name = "送货方式", dictType = "scm_delivery_type")
    private String deliveryType;

    /** 签约日期 */
    @ApiModelProperty(value="签约日期",example = "2021-09-10")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "签约日期", width = 30, dateFormat = "yyyy-MM-dd")
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
    @Excel(name = "审核意见")
    private String auditComment;

    /** 合同物料明细信息 */
	@ApiModelProperty(hidden = true)
    private List<ScmPurchaseContractDetail> scmPurchaseContractDetailList;

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
    public void setApplyId(Long applyId) 
    {
        this.applyId = applyId;
    }

    public Long getApplyId() 
    {
        return applyId;
    }
    public void setApplyNo(String applyNo) 
    {
        this.applyNo = applyNo;
    }

    public String getApplyNo() 
    {
        return applyNo;
    }
    public void setSupplierId(Long supplierId) 
    {
        this.supplierId = supplierId;
    }

    public Long getSupplierId() 
    {
        return supplierId;
    }
    public void setAmount(BigDecimal amount) 
    {
        this.amount = amount;
    }

    public BigDecimal getAmount() 
    {
        return amount;
    }
    public void setDeliveryType(String deliveryType) 
    {
        this.deliveryType = deliveryType;
    }

    public String getDeliveryType() 
    {
        return deliveryType;
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

    public List<ScmPurchaseContractDetail> getScmPurchaseContractDetailList()
    {
        return scmPurchaseContractDetailList;
    }

    public void setScmPurchaseContractDetailList(List<ScmPurchaseContractDetail> scmPurchaseContractDetailList)
    {
        this.scmPurchaseContractDetailList = scmPurchaseContractDetailList;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierContact() {
        return supplierContact;
    }

    public void setSupplierContact(String supplierContact) {
        this.supplierContact = supplierContact;
    }

    public String getSupplierContactTel() {
        return supplierContactTel;
    }

    public void setSupplierContactTel(String supplierContactTel) {
        this.supplierContactTel = supplierContactTel;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("contractNo", getContractNo())
            .append("applyId", getApplyId())
            .append("applyNo", getApplyNo())
            .append("supplierId", getSupplierId())
            .append("amount", getAmount())
            .append("deliveryType", getDeliveryType())
            .append("signDate", getSignDate())
            .append("applyUser", getApplyUser())
            .append("applyTime", getApplyTime())
            .append("applyStatus", getApplyStatus())
            .append("auditUser", getAuditUser())
            .append("auditTime", getAuditTime())
            .append("auditStatus", getAuditStatus())
            .append("auditComment", getAuditComment())
            .append("createTime", getCreateTime())
            .append("remark", getRemark())
            .append("scmPurchaseContractDetailList", getScmPurchaseContractDetailList())
            .toString();
    }
}
