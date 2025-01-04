package com.neu.carbon.scm.domain;

import java.util.List;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.neu.common.annotation.Excel;
import com.neu.common.core.domain.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 采购申请对象 scm_purchase_apply
 * 
 * @author neuedu
 * @date 2022-06-29
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel("采购申请")
@Data
public class ScmPurchaseApply extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    @ApiModelProperty("编号")
    private Long id;

    /** 申请单号 */
    @ApiModelProperty("申请单号")
    @Excel(name = "申请单号")
    private String applyNo;

    /** 采购计划id */
    @ApiModelProperty("采购计划id")
    private Long planId;

    /** 采购计划no */
    @ApiModelProperty("采购计划no")
    @Excel(name = "采购计划编号")
    private String planNo;

    /** 购买申请类型 */
    @ApiModelProperty("购买申请类型 1计划申请 2补货申请")
    @Excel(name = "购买申请类型")
    private String applyType;

    /** 补货id */
    @ApiModelProperty("补货id")
    private Long replenishId;

    /** 补货编号 */
    @ApiModelProperty("补货编号")
    @Excel(name = "补货编号")
    private String replenishNo;

    /** 申请人 */
    @ApiModelProperty("申请人")
    private String applyUser;

    /** 申请日期 */
    @ApiModelProperty(value="申请日期",example = "2021-09-10 10:20:40")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "申请日期", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date applyTime;

    /** 申请状态 */
    @ApiModelProperty("申请状态")
    @Excel(name = "申请状态", dictType = "apply_status")
    private String applyStatus;

    /** 审核人 */
    @ApiModelProperty("审核人")
    private String auditUser;

    /** 审核日期 */
    @ApiModelProperty(value="审核日期",example = "2021-09-10 10:20:40")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "审核日期", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date auditTime;

    /** 审核状态 */
    @ApiModelProperty("审核状态")
    @Excel(name = "审核状态", dictType = "audit_status")
    private String auditStatus;

    /** 审核意见 */
    @ApiModelProperty("审核意见")
    private String auditComment;

    /** 采购申请明细信息 */
	@ApiModelProperty(hidden = true)
    private List<ScmPurchaseApplyDetail> scmPurchaseApplyDetailList;

}
