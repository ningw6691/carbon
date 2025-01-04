package com.neu.carbon.mes.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.neu.common.annotation.Excel;
import com.neu.common.core.domain.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 工艺建模对象 mes_process_model
 * 
 * @author neuedu
 * @date 2022-07-11
 */
@ApiModel("工艺建模")
public class MesProcessModel extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    @ApiModelProperty("编号")
    private Long id;

    /** 产品 */
    @ApiModelProperty("产品")
    @Excel(name = "产品")
    private Long materialId;

    /** 工艺名称 */
    @ApiModelProperty("工艺名称")
    @Excel(name = "工艺名称")
    private String processName;

    /** 工艺描述 */
    @ApiModelProperty("工艺描述")
    private String description;

    /** 耗电量 */
    @ApiModelProperty("耗电量")
    @Excel(name = "耗电量")
    private Double powerConsume;
    
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

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setMaterialId(Long materialId) 
    {
        this.materialId = materialId;
    }

    public Long getMaterialId() 
    {
        return materialId;
    }
    public void setProcessName(String processName) 
    {
        this.processName = processName;
    }

    public String getProcessName() 
    {
        return processName;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }
    public void setPowerConsume(Double powerConsume) 
    {
        this.powerConsume = powerConsume;
    }

    public Double getPowerConsume() 
    {
        return powerConsume;
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
            .append("materialId", getMaterialId())
            .append("processName", getProcessName())
            .append("description", getDescription())
            .append("powerConsume", getPowerConsume())
            .append("remark", getRemark())
            .toString();
    }
}
