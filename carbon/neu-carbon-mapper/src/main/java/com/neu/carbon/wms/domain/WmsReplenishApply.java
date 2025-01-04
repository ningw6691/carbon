package com.neu.carbon.wms.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.neu.common.annotation.Excel;
import com.neu.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 补货申请对象 wms_replenish_apply
 *
 * @author neuedu
 * @date 2022-07-20
 */
@ApiModel("补货申请")
@Data
public class WmsReplenishApply extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    @ApiModelProperty("编号")
    private Long id;

    /** 流水号 */
    @ApiModelProperty("流水号")
    @Excel(name = "流水号")
    private String serialNo;

    /** 状态：1未采购 2采购中 3已采购 */
    @ApiModelProperty("状态：1未采购 2采购中 3已采购")
    @Excel(name = "状态：1未采购 2采购中 3已采购")
    private String status;

    /** 申请人 */
    @ApiModelProperty("申请人")
    @Excel(name = "申请人")
    private String applyUser;

    /** 申请日期 */
    @ApiModelProperty(value="申请日期",example = "2021-09-10 10:20:40")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "申请日期", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date applyTime;

    /** 申请状态：0未提交1待审核2已审核 */
    @ApiModelProperty("申请状态：0未提交1待审核2已审核")
    @Excel(name = "申请状态：0未提交1待审核2已审核")
    private String applyStatus;

    /** 审核人 */
    @ApiModelProperty("审核人")
    @Excel(name = "审核人")
    private String auditUser;

    /** 审核日期 */
    @ApiModelProperty(value="审核日期",example = "2021-09-10 10:20:40")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "审核日期", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date auditTime;

    /** 审核状态：0驳回1通过 */
    @ApiModelProperty("审核状态：0驳回1通过")
    @Excel(name = "审核状态：0驳回1通过")
    private String auditStatus;

    /** 审核意见 */
    @ApiModelProperty("审核意见")
    private String auditComment;

    /** 补货申请明细信息 */
	@ApiModelProperty(hidden = true)
    private List<WmsReplenishApplyDetail> wmsReplenishApplyDetailList;


    /** 是否生成采购申请1是0否 */
    @ApiModelProperty("是否生成采购申请1是0否")
    @Excel(name = "是否生成采购申请1是0否")
    private String purchaseApplyFlag;

}
