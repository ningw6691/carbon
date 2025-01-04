package com.neu.carbon.scm.domain;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.neu.common.annotation.Excel;
import com.neu.common.core.domain.BaseEntity;

/**
 * 退货明细对象 scm_sale_return_detail
 * 
 * @author neuedu
 * @date 2022-07-08
 */
@ApiModel("退货单明细")
public class ScmSaleReturnDetail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    @ApiModelProperty("编号")
    private Long id;

    /** 退货id */
    @ApiModelProperty("退货id")
    private Long returnId;

    /** 物料id */
    @ApiModelProperty("物料id")
    @Excel(name = "物料id")
    private Long materialId;

    /** 价格 */
    @ApiModelProperty("订货数量")
    @Excel(name = "价格")
    private BigDecimal price;

    /** 订货数量 */
    @ApiModelProperty("订货数量")
    @Excel(name = "订货数量")
    private Double bookQuantity;

    /** 发货数量 */
    @ApiModelProperty("发货数量")
    @Excel(name = "发货数量")
    private Double deliveryQuantity;

    /** 退货数量 */
    @ApiModelProperty("退货数量")
    @Excel(name = "退货数量")
    private Double returnQuantity;

    /** 备注 */
    @ApiModelProperty("备注")
    private String detailRemark;

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

    /** 单价 */
    @ApiModelProperty("物料单价")
    @Excel(name = "物料单价")
    private BigDecimal materialPrice;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setReturnId(Long returnId) 
    {
        this.returnId = returnId;
    }

    public Long getReturnId() 
    {
        return returnId;
    }
    public void setMaterialId(Long materialId) 
    {
        this.materialId = materialId;
    }

    public Long getMaterialId() 
    {
        return materialId;
    }
    public void setPrice(BigDecimal price) 
    {
        this.price = price;
    }

    public BigDecimal getPrice() 
    {
        return price;
    }
    public void setBookQuantity(Double bookQuantity)
    {
        this.bookQuantity = bookQuantity;
    }

    public Double getBookQuantity()
    {
        return bookQuantity;
    }
    public void setDeliveryQuantity(Double deliveryQuantity)
    {
        this.deliveryQuantity = deliveryQuantity;
    }

    public Double getDeliveryQuantity()
    {
        return deliveryQuantity;
    }
    public void setReturnQuantity(Double returnQuantity)
    {
        this.returnQuantity = returnQuantity;
    }

    public Double getReturnQuantity()
    {
        return returnQuantity;
    }
    public void setDetailRemark(String detailRemark) 
    {
        this.detailRemark = detailRemark;
    }

    public String getDetailRemark() 
    {
        return detailRemark;
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

    public BigDecimal getMaterialPrice() {
        return materialPrice;
    }

    public void setMaterialPrice(BigDecimal materialPrice) {
        this.materialPrice = materialPrice;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("returnId", getReturnId())
            .append("materialId", getMaterialId())
            .append("price", getPrice())
            .append("bookQuantity", getBookQuantity())
            .append("deliveryQuantity", getDeliveryQuantity())
            .append("returnQuantity", getReturnQuantity())
            .append("detailRemark", getDetailRemark())
            .toString();
    }
}
