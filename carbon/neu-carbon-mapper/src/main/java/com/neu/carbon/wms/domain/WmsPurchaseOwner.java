package com.neu.carbon.wms.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.neu.common.annotation.Excel;
import com.neu.common.core.domain.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 货主信息对象 wms_purchase_owner
 * 
 * @author neuedu
 * @date 2023-05-10
 */
@ApiModel("货主信息")
public class WmsPurchaseOwner extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @ApiModelProperty("主键")
    private Long id;

    /** 货主编号 */
    @ApiModelProperty("货主编号")
    @Excel(name = "货主编号")
    private String ownerNo;

    /** 公司名称 */
    @ApiModelProperty("公司名称")
    @Excel(name = "公司名称")
    private String name;

    /** 公司简称 */
    @ApiModelProperty("公司简称")
    @Excel(name = "公司简称")
    private String shortName;

    /** 公司地址 */
    @ApiModelProperty("公司地址")
    @Excel(name = "公司地址")
    private String addr;

    /** 电话 */
    @ApiModelProperty("电话")
    @Excel(name = "电话")
    private String tel;

    /** 开户银行 */
    @ApiModelProperty("开户银行")
    @Excel(name = "开户银行")
    private String bank;

    /** 银行账号 */
    @ApiModelProperty("银行账号")
    @Excel(name = "银行账号")
    private String account;

    /** 税号 */
    @ApiModelProperty("税号")
    @Excel(name = "税号")
    private String taxNo;

    /** 联系人 */
    @ApiModelProperty("联系人")
    @Excel(name = "联系人")
    private String contact;

    /** 联系方式 */
    @ApiModelProperty("联系方式")
    @Excel(name = "联系方式")
    private String contactTel;

    /** 分类（1核心供应商 2普通供应商） */
    @ApiModelProperty("分类")
    @Excel(name = "分类", readConverterExp = "1=核心供应商,2=普通供应商")
    private String type;

    /** 等级（1优质 2良好 3一般 4较差） */
    @ApiModelProperty("等级")
    @Excel(name = "等级", readConverterExp = "1=优质,2=良好,3=一般,4=较差")
    private String level;

    /** 状态（1正常 0停用） */
    @ApiModelProperty("状态")
    @Excel(name = "状态", readConverterExp = "1=正常,0=停用")
    private String status;

    /** 申请人 */
    @ApiModelProperty("申请人")
    @Excel(name = "申请人")
    private String applyUser;

    /** 申请时间 */
    @ApiModelProperty(value="申请时间",example = "2021-09-10")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "申请时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date applyTime;

    /** 申请状态：0未提交1待审核2已审核 */
    @ApiModelProperty("申请状态：0未提交1待审核2已审核")
    @Excel(name = "申请状态：0未提交1待审核2已审核")
    private String applyStatus;

    /** 审核人 */
    @ApiModelProperty("审核人")
    @Excel(name = "审核人")
    private String auditUser;

    /** 审核时间 */
    @ApiModelProperty(value="审核时间",example = "2021-09-10")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "审核时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date auditTime;

    /** 审核状态：0审核未通过1审核通过 */
    @ApiModelProperty("审核状态：0审核未通过1审核通过")
    @Excel(name = "审核状态：0审核未通过1审核通过")
    private String auditStatus;

    /** 审核意见 */
    @ApiModelProperty("审核意见")
    @Excel(name = "审核意见")
    private String auditComment;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setOwnerNo(String ownerNo) 
    {
        this.ownerNo = ownerNo;
    }

    public String getOwnerNo() 
    {
        return ownerNo;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setShortName(String shortName) 
    {
        this.shortName = shortName;
    }

    public String getShortName() 
    {
        return shortName;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("ownerNo", getOwnerNo())
            .append("name", getName())
            .append("shortName", getShortName())
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
