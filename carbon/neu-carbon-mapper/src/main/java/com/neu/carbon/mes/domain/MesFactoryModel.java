package com.neu.carbon.mes.domain;

import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.neu.common.annotation.Excel;
import com.neu.common.core.domain.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 工厂建模对象 mes_factory_model
 * 
 * @author neuedu
 * @date 2022-07-07
 */
@ApiModel("工厂建模")
public class MesFactoryModel extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    @ApiModelProperty("编号")
    private Long id;

    /** 物料 */
    @ApiModelProperty("物料")
    @Excel(name = "物料")
    private Long materialId;

    /** 生产线名称 */
    @ApiModelProperty("生产线名称")
    @Excel(name = "生产线名称")
    private String productLineName;
    
    /** 物料名称 */
    @ApiModelProperty("物料名称")
    @Excel(name = "物料名称")
    private String materialName;
    
    /** 型号 */
    @ApiModelProperty("产品型号")
    @Excel(name = "产品型号")
    private String productModel;

    /** 规格 */
    @ApiModelProperty("产品规格")
    @Excel(name = "产品规格")
    private String productSpecification;

    /** 工厂建模明细信息 */
	@ApiModelProperty(hidden = true)
    private List<MesFactoryModelDetail> mesFactoryModelDetailList;

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
    public void setProductLineName(String productLineName) 
    {
        this.productLineName = productLineName;
    }

    public String getProductLineName() 
    {
        return productLineName;
    }

    public List<MesFactoryModelDetail> getMesFactoryModelDetailList()
    {
        return mesFactoryModelDetailList;
    }

    public String getMaterialName() {
		return materialName;
	}

	public String getProductModel() {
		return productModel;
	}

	public void setProductModel(String productModel) {
		this.productModel = productModel;
	}

	public String getProductSpecification() {
		return productSpecification;
	}

	public void setProductSpecification(String productSpecification) {
		this.productSpecification = productSpecification;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public void setMesFactoryModelDetailList(List<MesFactoryModelDetail> mesFactoryModelDetailList)
    {
        this.mesFactoryModelDetailList = mesFactoryModelDetailList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("materialId", getMaterialId())
            .append("productLineName", getProductLineName())
            .append("remark", getRemark())
            .append("mesFactoryModelDetailList", getMesFactoryModelDetailList())
            .toString();
    }
}
