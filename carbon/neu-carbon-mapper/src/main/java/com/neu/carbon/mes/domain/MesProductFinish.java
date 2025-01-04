package com.neu.carbon.mes.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.neu.common.annotation.Excel;
import com.neu.common.core.domain.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 生产完工单对象 mes_product_finish
 * 
 * @author neuedu
 * @date 2022-07-17
 */
@ApiModel("生产完工单")
public class MesProductFinish extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    @ApiModelProperty("编号")
    private Long id;
    
    /** 生产完工单号 */
    @ApiModelProperty("生产完工单号")
    @Excel(name = "生产完工单号")
    private String serialNo;

    /** 生产计划 */
    @ApiModelProperty("生产计划")
    @Excel(name = "生产计划")
    private Long planId;

    /** 计划排产 */
    @ApiModelProperty("计划排产")
    @Excel(name = "计划排产")
    private Long scheduleId;

    /** 生产作业 */
    @ApiModelProperty("生产作业")
    @Excel(name = "生产作业")
    private Long jobId;

    /** 产品 */
    @ApiModelProperty("产品")
    @Excel(name = "产品")
    private Long materialId;

    /** 完工日期 */
    @ApiModelProperty(value="完工日期",example = "2021-09-10")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "完工日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date finishDate;

    /** 生产数量 */
    @ApiModelProperty("生产数量")
    @Excel(name = "生产数量")
    private Double finishQuantity;
    
    /** 计划号 */
    @ApiModelProperty("计划号")
    @Excel(name = "计划号")
    private String planNo;
    
    /** 物料编码 */
    @ApiModelProperty("物料编码")
    @Excel(name = "物料编码")
    private String materialCode;

    /** 物料名称 */
    @ApiModelProperty("物料名称")
    @Excel(name = "物料名称")
    private String materialName;

    /** 型号 */
    @ApiModelProperty("物料型号")
    @Excel(name = "物料型号")
    private String materialModel;

    /** 规格 */
    @ApiModelProperty("物料规格")
    @Excel(name = "物料规格")
    private String materialSpecification;

    /** 单位 */
    @ApiModelProperty("物料单位")
    @Excel(name = "物料单位")
    private String materialUnit;
    
    /** 计划名称 */
    @ApiModelProperty("计划名称")
    @Excel(name = "计划名称")
    private String planName;
    
    /** 作业名称 */
    @ApiModelProperty("作业名称")
    @Excel(name = "作业名称")
    private String jobName;
    
    /** 生产批号 */
    @ApiModelProperty("生产批号")
    @Excel(name = "生产批号")
    private String batchNo;
    
    /** 状态 */
    @ApiModelProperty("状态")
    @Excel(name = "状态",dictType="product_finish_status")
    private String status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
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
    public void setJobId(Long jobId) 
    {
        this.jobId = jobId;
    }

    public Long getJobId() 
    {
        return jobId;
    }
    public void setMaterialId(Long materialId) 
    {
        this.materialId = materialId;
    }

    public Long getMaterialId() 
    {
        return materialId;
    }
    public void setFinishDate(Date finishDate) 
    {
        this.finishDate = finishDate;
    }

    public Date getFinishDate() 
    {
        return finishDate;
    }
    public void setFinishQuantity(Double finishQuantity) 
    {
        this.finishQuantity = finishQuantity;
    }

    public Double getFinishQuantity() 
    {
        return finishQuantity;
    }

    public String getPlanNo() {
		return planNo;
	}

	public void setPlanNo(String planNo) {
		this.planNo = planNo;
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

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	@Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("planId", getPlanId())
            .append("scheduleId", getScheduleId())
            .append("jobId", getJobId())
            .append("materialId", getMaterialId())
            .append("finishDate", getFinishDate())
            .append("finishQuantity", getFinishQuantity())
            .append("remark", getRemark())
            .toString();
    }
}
