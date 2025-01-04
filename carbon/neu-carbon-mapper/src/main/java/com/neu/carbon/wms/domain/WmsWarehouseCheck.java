package com.neu.carbon.wms.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.neu.common.annotation.Excel;
import com.neu.common.core.domain.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 盘点信息对象 wms_warehouse_check
 * 
 * @author neuedu
 * @date 2022-07-06
 */
@ApiModel("盘点信息")
public class WmsWarehouseCheck extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    @ApiModelProperty("编号")
    private Long id;

    /** 单据号 */
    @ApiModelProperty("单据号")
    @Excel(name = "单据号")
    private String serialNo;

    /** 物料 */
    @ApiModelProperty("物料")
    @Excel(name = "物料")
    private Long materialId;

    /** 仓库 */
    @ApiModelProperty("仓库")
    @Excel(name = "仓库")
    private Long warehouseId;

    /** 库区 */
    @ApiModelProperty("库区")
    @Excel(name = "库区")
    private Long whRegionId;

    /** 库位 */
    @ApiModelProperty("库位")
    @Excel(name = "库位")
    private Long whLocationId;

    /** 批号 */
    @ApiModelProperty("批号")
    @Excel(name = "批号")
    private String batchNo;

    /** 盘点类型 */
    @ApiModelProperty("盘点类型")
    @Excel(name = "盘点类型")
    private String checkType;

    /** 库存月份 */
    @ApiModelProperty("库存月份")
    @Excel(name = "库存月份")
    private String stockMonth;

    /** 盘点日期 */
    @ApiModelProperty(value="盘点日期",example = "2021-09-10 10:20:40")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "盘点日期", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date checkDate;

    /** 负责人 */
    @ApiModelProperty("负责人")
    @Excel(name = "负责人")
    private String checkUser;

    /** 现库存 */
    @ApiModelProperty("现库存")
    @Excel(name = "现库存")
    private Double inventory;

    /** 实际库存 */
    @ApiModelProperty("实际库存")
    @Excel(name = "实际库存")
    private Double actualInventory;

    /** 原因 */
    @ApiModelProperty("原因")
    @Excel(name = "原因")
    private String reason;
    
    /** 物料名称 */
    @ApiModelProperty("物料名称")
    @Excel(name = "物料名称")
    private String materialName;
    
    /** 仓库 */
    @ApiModelProperty("仓库名")
    @Excel(name = "仓库名")
    private String warehouseName;

    /** 库区 */
    @ApiModelProperty("库区名")
    @Excel(name = "库区名")
    private String whRegionName;

    /** 库位 */
    @ApiModelProperty("库位名")
    @Excel(name = "库位名")
    private String whLocationName;

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
    public void setMaterialId(Long materialId) 
    {
        this.materialId = materialId;
    }

    public Long getMaterialId() 
    {
        return materialId;
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
    public void setBatchNo(String batchNo) 
    {
        this.batchNo = batchNo;
    }

    public String getBatchNo() 
    {
        return batchNo;
    }
    public void setCheckType(String checkType) 
    {
        this.checkType = checkType;
    }

    public String getCheckType() 
    {
        return checkType;
    }
    public void setStockMonth(String stockMonth) 
    {
        this.stockMonth = stockMonth;
    }

    public String getStockMonth() 
    {
        return stockMonth;
    }
    public void setCheckDate(Date checkDate) 
    {
        this.checkDate = checkDate;
    }

    public Date getCheckDate() 
    {
        return checkDate;
    }
    public void setCheckUser(String checkUser) 
    {
        this.checkUser = checkUser;
    }

    public String getCheckUser() 
    {
        return checkUser;
    }
    public void setInventory(Double inventory) 
    {
        this.inventory = inventory;
    }

    public Double getInventory() 
    {
        return inventory;
    }
    public void setActualInventory(Double actualInventory) 
    {
        this.actualInventory = actualInventory;
    }

    public Double getActualInventory() 
    {
        return actualInventory;
    }
    public void setReason(String reason) 
    {
        this.reason = reason;
    }

    public String getReason() 
    {
        return reason;
    }

    public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public String getWhRegionName() {
		return whRegionName;
	}

	public void setWhRegionName(String whRegionName) {
		this.whRegionName = whRegionName;
	}

	public String getWhLocationName() {
		return whLocationName;
	}

	public void setWhLocationName(String whLocationName) {
		this.whLocationName = whLocationName;
	}

	@Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("serialNo", getSerialNo())
            .append("materialId", getMaterialId())
            .append("warehouseId", getWarehouseId())
            .append("whRegionId", getWhRegionId())
            .append("whLocationId", getWhLocationId())
            .append("batchNo", getBatchNo())
            .append("checkType", getCheckType())
            .append("stockMonth", getStockMonth())
            .append("checkDate", getCheckDate())
            .append("checkUser", getCheckUser())
            .append("inventory", getInventory())
            .append("actualInventory", getActualInventory())
            .append("reason", getReason())
            .toString();
    }
}
