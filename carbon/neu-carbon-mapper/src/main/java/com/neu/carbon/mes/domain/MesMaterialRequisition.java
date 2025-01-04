package com.neu.carbon.mes.domain;

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
 * 领料申请对象 mes_material_requisition
 * 
 * @author neuedu
 * @date 2022-07-14
 */
@ApiModel("领料申请")
public class MesMaterialRequisition extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    @ApiModelProperty("编号")
    private Long id;

    /** 领料单号 */
    @ApiModelProperty("领料单号")
    @Excel(name = "领料单号")
    private String serialNo;

    /** 生产计划id */
    @ApiModelProperty("生产计划id")
    private Long planId;
    
    /** 生产计划编号 */
    @ApiModelProperty("生产计划编号 ")
    @Excel(name = "生产计划编号 ")
    private String planNo;

    /** 计划排产 */
    @ApiModelProperty("计划排产")
    private Long scheduleId;
    
    /** 计划排产编号 */
    @ApiModelProperty("计划排产编号")
    @Excel(name = "计划排产编号")
    private String scheduleNo;

    /** 产品 */
    @ApiModelProperty("产品")
    private Long productId;
    
    /** 产品编码 */
    @ApiModelProperty("产品编码")
    @Excel(name = "产品编码")
    private String materialCode;

    /** 产品名称 */
    @ApiModelProperty("产品名称")
    @Excel(name = "产品名称")
    private String materialName;

    /** 型号 */
    @ApiModelProperty("产品型号")
    @Excel(name = "产品型号")
    private String materialModel;

    /** 规格 */
    @ApiModelProperty("产品规格")
    @Excel(name = "产品规格")
    private String materialSpecification;

    /** 单位 */
    @ApiModelProperty("产品单位")
    @Excel(name = "产品单位")
    private String materialUnit;

    /** 领取日期 */
    @ApiModelProperty(value="领取日期",example = "2021-09-10")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "领取日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date requisitionDate;
    
    /** 待产数量 */
    @ApiModelProperty("待产数量")
    //@Excel(name = "待产数量")
    private Double requireQuantity;

    /** 生产日期 */
    @ApiModelProperty(value="生产日期",example = "2021-09-10")
    @JsonFormat(pattern = "yyyy-MM-dd")
    //@Excel(name = "生产日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date productDate;

    /** 申请人 */
    @ApiModelProperty("申请人")
    //@Excel(name = "申请人")
    private String applyUser;

    /** 申请时间 */
    @ApiModelProperty(value="申请时间",example = "2021-09-10 10:20:40")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "申请时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date applyTime;

    /** 申请状态 */
    @ApiModelProperty("申请状态")
    @Excel(name = "申请状态",dictType="apply_status")
    private String applyStatus;

    /** 审核人 */
    @ApiModelProperty("审核人")
    //@Excel(name = "审核人")
    private String auditUser;

    /** 审核时间 */
    @ApiModelProperty(value="审核时间",example = "2021-09-10 10:20:40")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "审核时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date auditTime;

    /** 审核状态 */
    @ApiModelProperty("审核状态")
    @Excel(name = "审核状态",dictType="audit_status")
    private String auditStatus;

    /** 状态 */
    @ApiModelProperty("状态")
    @Excel(name = "状态",dictType="receive_material_status")
    private String status;

    /** 审核意见 */
    @ApiModelProperty("审核意见")
    private String auditComment;

    /** 领料单产品明细信息 */
	@ApiModelProperty(hidden = true)
    private List<MesMaterialRequisitionDetail> mesMaterialRequisitionDetailList;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setSerialNo(String serialNo) 
    {
        this.serialNo = serialNo;
    }

    public String getSerialNo() 
    {
        return serialNo;
    }
    public void setPlanId(Long planId) 
    {
        this.planId = planId;
    }

    public Long getPlanId() 
    {
        return planId;
    }
    public void setScheduleId(Long scheduleId) 
    {
        this.scheduleId = scheduleId;
    }

    public Long getScheduleId() 
    {
        return scheduleId;
    }
    public void setProductId(Long productId) 
    {
        this.productId = productId;
    }

    public Long getProductId() 
    {
        return productId;
    }
    public void setRequisitionDate(Date requisitionDate) 
    {
        this.requisitionDate = requisitionDate;
    }

    public Date getRequisitionDate() 
    {
        return requisitionDate;
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
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setAuditComment(String auditComment) 
    {
        this.auditComment = auditComment;
    }

    public String getAuditComment() 
    {
        return auditComment;
    }

    public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getMaterialModel() {
		return materialModel;
	}

	public void setMaterialModel(String materialModel) {
		this.materialModel = materialModel;
	}

	public String getMaterialSpecification() {
		return materialSpecification;
	}

	public void setMaterialSpecification(String materialSpecification) {
		this.materialSpecification = materialSpecification;
	}

	public String getMaterialUnit() {
		return materialUnit;
	}

	public void setMaterialUnit(String materialUnit) {
		this.materialUnit = materialUnit;
	}

	public String getPlanNo() {
		return planNo;
	}

	public void setPlanNo(String planNo) {
		this.planNo = planNo;
	}

	public Double getRequireQuantity() {
		return requireQuantity;
	}

	public void setRequireQuantity(Double requireQuantity) {
		this.requireQuantity = requireQuantity;
	}

	public Date getProductDate() {
		return productDate;
	}

	public void setProductDate(Date productDate) {
		this.productDate = productDate;
	}

	public List<MesMaterialRequisitionDetail> getMesMaterialRequisitionDetailList()
    {
        return mesMaterialRequisitionDetailList;
    }

    public void setMesMaterialRequisitionDetailList(List<MesMaterialRequisitionDetail> mesMaterialRequisitionDetailList)
    {
        this.mesMaterialRequisitionDetailList = mesMaterialRequisitionDetailList;
    }

    public String getScheduleNo() {
		return scheduleNo;
	}

	public void setScheduleNo(String scheduleNo) {
		this.scheduleNo = scheduleNo;
	}

	@Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("serialNo", getSerialNo())
            .append("planId", getPlanId())
            .append("scheduleId", getScheduleId())
            .append("productId", getProductId())
            .append("requisitionDate", getRequisitionDate())
            .append("applyUser", getApplyUser())
            .append("applyTime", getApplyTime())
            .append("applyStatus", getApplyStatus())
            .append("auditUser", getAuditUser())
            .append("auditTime", getAuditTime())
            .append("auditStatus", getAuditStatus())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("auditComment", getAuditComment())
            .append("mesMaterialRequisitionDetailList", getMesMaterialRequisitionDetailList())
            .toString();
    }
}
