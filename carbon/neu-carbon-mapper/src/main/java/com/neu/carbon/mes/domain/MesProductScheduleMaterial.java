package com.neu.carbon.mes.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.neu.common.annotation.Excel;
import com.neu.common.core.domain.BaseEntity;

import io.swagger.annotations.ApiModelProperty;

/**
 * 排产物料信息对象 mes_product_schedule_material
 * 
 * @author neuedu
 * @date 2022-07-13
 */
public class MesProductScheduleMaterial extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 排产编号 */
    @Excel(name = "排产编号")
    private Long scheduleId;

    /** 物料 */
    @Excel(name = "物料")
    private Long materialId;

    /** 需求数量 */
    @Excel(name = "需求数量")
    private Double requireQuantity;

    /** 已用数量 */
    @Excel(name = "已用数量")
    private Double usageQuantity;

    /** 剩余数量 */
    @Excel(name = "剩余数量")
    private Double leftQuantity;
    
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

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setScheduleId(Long scheduleId) 
    {
        this.scheduleId = scheduleId;
    }

    public Long getScheduleId() 
    {
        return scheduleId;
    }
    public void setMaterialId(Long materialId) 
    {
        this.materialId = materialId;
    }

    public Long getMaterialId() 
    {
        return materialId;
    }
    public void setRequireQuantity(Double requireQuantity) 
    {
        this.requireQuantity = requireQuantity;
    }

    public Double getRequireQuantity() 
    {
        return requireQuantity;
    }
    public void setUsageQuantity(Double usageQuantity) 
    {
        this.usageQuantity = usageQuantity;
    }

    public Double getUsageQuantity() 
    {
        return usageQuantity;
    }
    public void setLeftQuantity(Double leftQuantity) 
    {
        this.leftQuantity = leftQuantity;
    }

    public Double getLeftQuantity() 
    {
        return leftQuantity;
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

	@Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("scheduleId", getScheduleId())
            .append("materialId", getMaterialId())
            .append("requireQuantity", getRequireQuantity())
            .append("usageQuantity", getUsageQuantity())
            .append("leftQuantity", getLeftQuantity())
            .append("remark", getRemark())
            .toString();
    }
}
