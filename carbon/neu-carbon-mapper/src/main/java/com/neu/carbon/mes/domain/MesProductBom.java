package com.neu.carbon.mes.domain;

import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.neu.common.annotation.Excel;
import com.neu.common.core.domain.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * BOM管理对象 mes_product_bom
 * 
 * @author neuedu
 * @date 2022-07-11
 */
@ApiModel("BOM管理")
public class MesProductBom extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    @ApiModelProperty("编号")
    private Long id;

    /** 名称 */
    @ApiModelProperty("名称")
    @Excel(name = "名称")
    private String name;

    /** 产品 */
    @ApiModelProperty("产品ID")
    private Long productId;
    
    /** 产品 */
    @ApiModelProperty("产品名称")
    @Excel(name = "产品名称")
    private String productName;
    
    /** 物料编码 */
    @ApiModelProperty("产品编码")
    private String productCode;

    /** 型号 */
    @ApiModelProperty("型号")
    @Excel(name = "产品型号")
    private String productModel;

    /** 规格 */
    @ApiModelProperty("规格")
    @Excel(name = "产品规格")
    private String productSpecification;

    /** 单位 */
    @ApiModelProperty("单位")
    @Excel(name = "产品单位")
    private String productUnit;

    /** 描述 */
    @ApiModelProperty("描述")
    private String description;

    /** BOM单明细信息 */
	@ApiModelProperty(hidden = true)
    private List<MesProductBomDetail> mesProductBomDetailList;

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
    public void setProductId(Long productId) 
    {
        this.productId = productId;
    }

    public Long getProductId() 
    {
        return productId;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
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

	public List<MesProductBomDetail> getMesProductBomDetailList()
    {
        return mesProductBomDetailList;
    }

    public void setMesProductBomDetailList(List<MesProductBomDetail> mesProductBomDetailList)
    {
        this.mesProductBomDetailList = mesProductBomDetailList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("productId", getProductId())
            .append("description", getDescription())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("mesProductBomDetailList", getMesProductBomDetailList())
            .toString();
    }
}
