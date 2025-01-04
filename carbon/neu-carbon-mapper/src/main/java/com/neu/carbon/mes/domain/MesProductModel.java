package com.neu.carbon.mes.domain;

import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.neu.common.annotation.Excel;
import com.neu.common.core.domain.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 产品建模对象 mes_product_model
 * 
 * @author neuedu
 * @date 2022-07-08
 */
@ApiModel("产品建模")
public class MesProductModel extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    @ApiModelProperty("编号")
    private Long id;

    /** 产品 */
    @ApiModelProperty("产品id")
    @Excel(name = "产品id")
    private Long productId;

    /** 模型名称 */
    @ApiModelProperty("模型名称")
    @Excel(name = "模型名称")
    private String modelName;
    
    /** 产品 */
    @ApiModelProperty("产品名称")
    @Excel(name = "产品名称")
    private String productName;
    
    /** 物料编码 */
    @ApiModelProperty("产品编码")
    @Excel(name = "产品编码")
    private String productCode;

    /** 型号 */
    @ApiModelProperty("产品型号")
    @Excel(name = "产品型号")
    private String productModel;

    /** 规格 */
    @ApiModelProperty("产品规格")
    @Excel(name = "产品规格")
    private String productSpecification;

    /** 单位 */
    @ApiModelProperty("产品单位")
    @Excel(name = "产品单位")
    private String productUnit;

    /** 产品模型明细信息 */
	@ApiModelProperty(hidden = true)
    private List<MesProductModelDetail> mesProductModelDetailList;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setProductId(Long materialId) 
    {
        this.productId = materialId;
    }

    public Long getProductId() 
    {
        return productId;
    }
    public void setModelName(String modelName) 
    {
        this.modelName = modelName;
    }

    public String getModelName() 
    {
        return modelName;
    }

    public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
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

	public String getProductUnit() {
		return productUnit;
	}

	public void setProductUnit(String productUnit) {
		this.productUnit = productUnit;
	}

	public List<MesProductModelDetail> getMesProductModelDetailList()
    {
        return mesProductModelDetailList;
    }

    public void setMesProductModelDetailList(List<MesProductModelDetail> mesProductModelDetailList)
    {
        this.mesProductModelDetailList = mesProductModelDetailList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("materialId", getProductId())
            .append("modelName", getModelName())
            .append("remark", getRemark())
            .append("mesProductModelDetailList", getMesProductModelDetailList())
            .toString();
    }
}
