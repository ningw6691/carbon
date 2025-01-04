package com.neu.carbon.wms.domain;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.neu.common.annotation.Excel;
import com.neu.common.core.domain.BaseEntity;

import io.swagger.annotations.ApiModelProperty;

/**
 * 产品承运申请明细对象 wms_carrier_apply_detail
 * 
 * @author neuedu
 * @date 2022-07-01
 */
public class WmsCarrierApplyDetail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 承运申请单编号 */
    @ApiModelProperty("物料名称")
    private Long carrierApplyId;

    /** 物料档案 */
    @ApiModelProperty("物料名称")
    @Excel(name = "物料档案")
    private Long materialId;

    /** 生产批号 */
    @ApiModelProperty("物料名称")
    @Excel(name = "生产批号")
    private String batchNo;

    /** 仓库 */
    @ApiModelProperty("物料名称")
    @Excel(name = "仓库")
    private Long warehouseId;

    /** 库区 */
    @ApiModelProperty("物料名称")
    @Excel(name = "库区")
    private Long whRegionId;

    /** 库位 */
    @ApiModelProperty("物料名称")
    @Excel(name = "库位")
    private Long whLocationId;

    /** 发货数量 */
    @ApiModelProperty("物料名称")
    @Excel(name = "发货数量")
    private Double deliveryQuantity;
    
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
    
    @ApiModelProperty("物料单价")
    @Excel(name = "物料单价")
    private BigDecimal price;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setCarrierApplyId(Long carrierApplyId) 
    {
        this.carrierApplyId = carrierApplyId;
    }

    public Long getCarrierApplyId() 
    {
        return carrierApplyId;
    }
    public void setMaterialId(Long materialId) 
    {
        this.materialId = materialId;
    }

    public Long getMaterialId() 
    {
        return materialId;
    }
    public void setBatchNo(String batchNo) 
    {
        this.batchNo = batchNo;
    }

    public String getBatchNo() 
    {
        return batchNo;
    }
    public void setWarehouseId(Long warehouseId) 
    {
        this.warehouseId = warehouseId;
    }

    public Long getWarehouseId() 
    {
        return warehouseId;
    }
    public void setWhRegionId(Long whRegionId) 
    {
        this.whRegionId = whRegionId;
    }

    public Long getWhRegionId() 
    {
        return whRegionId;
    }
    public void setWhLocationId(Long whLocationId) 
    {
        this.whLocationId = whLocationId;
    }

    public Long getWhLocationId() 
    {
        return whLocationId;
    }
    public void setDeliveryQuantity(Double deliveryQuantity) 
    {
        this.deliveryQuantity = deliveryQuantity;
    }

    public Double getDeliveryQuantity() 
    {
        return deliveryQuantity;
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

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("carrierApplyId", getCarrierApplyId())
            .append("materialId", getMaterialId())
            .append("batchNo", getBatchNo())
            .append("warehouseId", getWarehouseId())
            .append("whRegionId", getWhRegionId())
            .append("whLocationId", getWhLocationId())
            .append("deliveryQuantity", getDeliveryQuantity())
            .toString();
    }
}
