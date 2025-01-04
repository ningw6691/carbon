package com.neu.carbon.scm.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.neu.common.annotation.Excel;
import com.neu.common.core.domain.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 供应商对象 scm_purchase_supplier
 *
 * @author neuedu
 * @date 2022-07-01
 */
@ApiModel("供应商")
public class ScmPurchaseSupplier extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    @ApiModelProperty("编号")
    private Long id;

    /** 供应商编号 */
    @ApiModelProperty("供应商编号")
    @Excel(name = "供应商编号")
    private String supplierNo;

    /** 供应商名称 */
    @ApiModelProperty("供应商名称")
    @Excel(name = "供应商名称")
    private String name;

    /** 供应商简称 */
    @ApiModelProperty("供应商简称")
    @Excel(name = "供应商名称")
    private String shortName;

    /** 地址 */
    @ApiModelProperty("地址")
    @Excel(name = "地址")
    private String addr;

    /** 电话 */
    @ApiModelProperty("电话")
    private String tel;

    /** 开户银行 */
    @ApiModelProperty("开户银行")
    private String bank;

    /** 银行账号 */
    @ApiModelProperty("银行账号")
    private String account;

    /** 税号 */
    @ApiModelProperty("税号")
    private String taxNo;

    /** 联系人 */
    @ApiModelProperty("联系人")
    @Excel(name = "联系人")
    private String contact;

    /** 联系电话 */
    @ApiModelProperty("联系电话")
    @Excel(name = "联系电话")
    private String contactTel;

    /** 供应商分类 */
    @ApiModelProperty("供应商分类")
    @Excel(name = "供应商分类")
    private String type;

    /** 供应商等级 */
    @ApiModelProperty("供应商等级")
    @Excel(name = "供应商等级")
    private String level;

    /** 状态 */
    @ApiModelProperty("状态")
    @Excel(name = "状态")
    private String status;

    /** 申请人 */
    @ApiModelProperty("申请人")
    @Excel(name = "申请人")
    private String applyUser;

    /** 申请时间 */
    @ApiModelProperty(value="申请时间",example = "2021-09-10 10:20:40")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date applyTime;

    /** 申请状态 */
    @ApiModelProperty("申请状态")
    private String applyStatus;

    /** 审核人 */
    @ApiModelProperty("审核人")
    private String auditUser;

    /** 审核时间 */
    @ApiModelProperty(value="审核时间",example = "2021-09-10 10:20:40")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date auditTime;

    /** 审核状态 */
    @ApiModelProperty("审核状态")
    @Excel(name = "审核状态")
    private String auditStatus;

    /** 审核意见 */
    @ApiModelProperty("审核意见")
    private String auditComment;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setSupplierNo(String supplierNo)
    {
        this.supplierNo = supplierNo;
    }

    public String getSupplierNo()
    {
        return supplierNo;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setAddr(String addr)
    {
        this.addr = addr;
    }

    public String getAddr()
    {
        return addr;
    }
    public void setTel(String tel)
    {
        this.tel = tel;
    }

    public String getTel()
    {
        return tel;
    }
    public void setBank(String bank)
    {
        this.bank = bank;
    }

    public String getBank()
    {
        return bank;
    }
    public void setAccount(String account)
    {
        this.account = account;
    }

    public String getAccount()
    {
        return account;
    }
    public void setTaxNo(String taxNo)
    {
        this.taxNo = taxNo;
    }

    public String getTaxNo()
    {
        return taxNo;
    }
    public void setContact(String contact)
    {
        this.contact = contact;
    }

    public String getContact()
    {
        return contact;
    }
    public void setContactTel(String contactTel)
    {
        this.contactTel = contactTel;
    }

    public String getContactTel()
    {
        return contactTel;
    }
    public void setType(String type)
    {
        this.type = type;
    }

    public String getType()
    {
        return type;
    }
    public void setLevel(String level)
    {
        this.level = level;
    }

    public String getLevel()
    {
        return level;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
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


    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("supplierNo", getSupplierNo())
                .append("name", getName())
                .append("addr", getAddr())
                .append("tel", getTel())
                .append("bank", getBank())
                .append("account", getAccount())
                .append("taxNo", getTaxNo())
                .append("contact", getContact())
                .append("contactTel", getContactTel())
                .append("type", getType())
                .append("level", getLevel())
                .append("status", getStatus())
                .append("applyUser", getApplyUser())
                .append("applyTime", getApplyTime())
                .append("applyStatus", getApplyStatus())
                .append("auditUser", getAuditUser())
                .append("auditTime", getAuditTime())
                .append("auditStatus", getAuditStatus())
                .append("auditComment", getAuditComment())
                .append("remark", getRemark())
                .toString();
    }
}
