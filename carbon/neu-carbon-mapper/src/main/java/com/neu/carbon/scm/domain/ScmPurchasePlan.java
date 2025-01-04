package com.neu.carbon.scm.domain;

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
 * 采购计划对象 scm_purchase_plan
 * 
 * @author neuedu
 * @date 2022-06-28
 */
@ApiModel("采购计划")
public class ScmPurchasePlan extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    @ApiModelProperty("编号")
    private Long id;

    /** 单据编号 */
    @ApiModelProperty("单据编号")
    @Excel(name = "单据编号")
    private String purchasePlanNo;

    /** 申请人 */
    @ApiModelProperty("申请人")
    private String applyUser;

    /** 申请时间 */
    @ApiModelProperty(value="申请时间",example = "2021-09-10 10:20:40")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "申请时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date applyTime;

    /** 申请状态：0未提交1待审核 */
    @ApiModelProperty("申请状态：0未提交1待审核")
    @Excel(name = "申请状态：0未提交1待审核", dictType = "apply_status")
    private String applyStatus;

    /** 审核人 */
    @ApiModelProperty("审核人")
    private String auditUser;

    /** 审核日期 */
    @ApiModelProperty(value="审核日期",example = "2021-09-10 10:20:40")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "审核日期", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date auditTime;

    /** 审核状态：0审核未通过1审核通过 */
    @ApiModelProperty("审核状态：0审核未通过1审核通过")
    @Excel(name = "审核状态：0审核未通过1审核通过", dictType = "audit_status")
    private String auditStatus;

    /** 审核意见 */
    @ApiModelProperty("审核意见")
    @Excel(name = "审核意见")
    private String auditComment;

    /** 采购计划详细信息 */
	@ApiModelProperty(hidden = true)
    private List<ScmPurchasePlanDetail> scmPurchasePlanDetailList;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setPurchasePlanNo(String purchasePlanNo) 
    {
        this.purchasePlanNo = purchasePlanNo;
    }

    public String getPurchasePlanNo() 
    {
        return purchasePlanNo;
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

    public List<ScmPurchasePlanDetail> getScmPurchasePlanDetailList()
    {
        return scmPurchasePlanDetailList;
    }

    public void setScmPurchasePlanDetailList(List<ScmPurchasePlanDetail> scmPurchasePlanDetailList)
    {
        this.scmPurchasePlanDetailList = scmPurchasePlanDetailList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("purchasePlanNo", getPurchasePlanNo())
            .append("applyUser", getApplyUser())
            .append("applyTime", getApplyTime())
            .append("applyStatus", getApplyStatus())
            .append("auditUser", getAuditUser())
            .append("auditTime", getAuditTime())
            .append("auditStatus", getAuditStatus())
            .append("auditComment", getAuditComment())
            .append("remark", getRemark())
            .append("scmPurchasePlanDetailList", getScmPurchasePlanDetailList())
            .toString();
    }
}
