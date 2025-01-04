package com.neu.carbon.wms.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.neu.common.annotation.Excel;
import com.neu.common.core.domain.BaseEntity;

/**
 * 配送单明细对象 wms_delivery_bill_detail
 * 
 * @author neuedu
 * @date 2022-07-05
 */
public class WmsDeliveryBillDetail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 配送单号 */
    private Long deliveryBillId;

    /** 物料 */
    @Excel(name = "物料")
    private Long materialId;

    /** 生产批号 */
    @Excel(name = "生产批号")
    private String batchNo;

    /** 仓库 */
    @Excel(name = "仓库")
    private Long warehouseId;

    /** 库区 */
    @Excel(name = "库区")
    private Long whRegionId;

    /** 库位 */
    @Excel(name = "库位")
    private Long whLocationId;

    /** 发货数量 */
    @Excel(name = "发货数量")
    private Double deliveryQuantity;

    /** 价格 */
    @Excel(name = "价格")
    private BigDecimal price;

    /** 备注 */
    @Excel(name = "备注")
    private String detailRemark;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setDeliveryBillId(Long deliveryBillId) 
    {
        this.deliveryBillId = deliveryBillId;
    }

    public Long getDeliveryBillId() 
    {
        return deliveryBillId;
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
    public void setPrice(BigDecimal price) 
    {
        this.price = price;
    }

    public BigDecimal getPrice() 
    {
        return price;
    }
    public void setDetailRemark(String detailRemark) 
    {
        this.detailRemark = detailRemark;
    }

    public String getDetailRemark() 
    {
        return detailRemark;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("deliveryBillId", getDeliveryBillId())
            .append("materialId", getMaterialId())
            .append("batchNo", getBatchNo())
            .append("warehouseId", getWarehouseId())
            .append("whRegionId", getWhRegionId())
            .append("whLocationId", getWhLocationId())
            .append("deliveryQuantity", getDeliveryQuantity())
            .append("price", getPrice())
            .append("detailRemark", getDetailRemark())
            .toString();
    }
}
