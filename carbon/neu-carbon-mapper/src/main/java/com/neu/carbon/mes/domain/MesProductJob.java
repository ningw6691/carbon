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
 * 生产作业对象 mes_product_job
 * 
 * @author neuedu
 * @date 2022-07-15
 */
@ApiModel("生产作业")
public class MesProductJob extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    @ApiModelProperty("编号")
    private Long id;

    /** 作业名称 */
    @ApiModelProperty("作业名称")
    @Excel(name = "作业名称")
    private String name;

    /** 生产计划 */
    @ApiModelProperty("生产计划")
    private Long planId;
    
    /** 计划号 */
    @ApiModelProperty("计划号")
    @Excel(name = "生产计划编号")
    private String planNo;
    
    /** 排产号 */
    @ApiModelProperty("排产号")
    @Excel(name = "排产号")
    private String scheduleNo;

    /** 计划排产 */
    @ApiModelProperty("计划排产")
    private Long scheduleId;

    /** 产品 */
    @ApiModelProperty("产品")
    private Long productId;

    /** 开始时间 */
    @ApiModelProperty(value="开始时间",example = "2021-09-10 10:20:40")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /** 结束时间 */
    @ApiModelProperty(value="结束时间",example = "2021-09-10 10:20:40")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /** 生产数量 */
    @ApiModelProperty("生产数量")
    @Excel(name = "生产数量")
    private Double productQuantity;

    /** 作业状态 */
    @ApiModelProperty("作业状态")
    @Excel(name = "作业状态", dictType = "product_job_status")
    private String status;
    
    /** 物料编码 */
    @ApiModelProperty("物料编码")
    @Excel(name = "物料编码")
    private String materialCode;

    /** 物料名称 */
    @ApiModelProperty("物料名称")
    @Excel(name = "物料名称")
    private String productName;

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
    
    /** 待产数量 */
    @ApiModelProperty("待产数量")
    //@Excel(name = "待产数量")
    private Double requireQuantity;
    
    /** 待产数量 */
    @ApiModelProperty("已产数量")
    //@Excel(name = "已产数量")
    private Double finishQuantity;

    /** 生产作业物料信息 */
	@ApiModelProperty(hidden = true)
    private List<MesProductJobMaterial> mesProductJobMaterialList;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
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
    public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public void setStartTime(Date startTime) 
    {
        this.startTime = startTime;
    }

    public Date getStartTime() 
    {
        return startTime;
    }
    public void setEndTime(Date endTime) 
    {
        this.endTime = endTime;
    }

    public Date getEndTime() 
    {
        return endTime;
    }
    public void setProductQuantity(Double productQuantity) 
    {
        this.productQuantity = productQuantity;
    }

    public Double getProductQuantity() 
    {
        return productQuantity;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
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

	public Double getRequireQuantity() {
		return requireQuantity;
	}

	public void setRequireQuantity(Double requireQuantity) {
		this.requireQuantity = requireQuantity;
	}

	public Double getFinishQuantity() {
		return finishQuantity;
	}

	public void setFinishQuantity(Double finishQuantity) {
		this.finishQuantity = finishQuantity;
	}

	public List<MesProductJobMaterial> getMesProductJobMaterialList()
    {
        return mesProductJobMaterialList;
    }

    public void setMesProductJobMaterialList(List<MesProductJobMaterial> mesProductJobMaterialList)
    {
        this.mesProductJobMaterialList = mesProductJobMaterialList;
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
            .append("name", getName())
            .append("planId", getPlanId())
            .append("scheduleId", getScheduleId())
            .append("productId", getProductId())
            .append("startTime", getStartTime())
            .append("endTime", getEndTime())
            .append("productQuantity", getProductQuantity())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("mesProductJobMaterialList", getMesProductJobMaterialList())
            .toString();
    }
}
