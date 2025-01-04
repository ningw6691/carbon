package com.neu.carbon.scm.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.neu.common.annotation.Excel;
import com.neu.common.core.domain.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 采购计划详细对象 scm_purchase_plan_detail
 * 
 * @author neusoft
 * @date 2022-06-28
 */
@ApiModel("采购计划详细")
public class ScmPurchasePlanDetail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    @ApiModelProperty("编号")
    private Long id;

    /** 计划id */
    @ApiModelProperty("计划id")
    @Excel(name = "计划id")
    private Long purchasePlanId;

    /** 物料id */
    @ApiModelProperty("物料id")
    @Excel(name = "物料id")
    private Long materialId;

    /** 采购数量 */
    @ApiModelProperty("采购数量")
    @Excel(name = "采购数量")
    private Double quantity;

    /** 需求数量 */
    @ApiModelProperty("需求数量")
    @Excel(name = "需求数量")
    private Double requireQuantity;

    /** 需求日期 */
    @ApiModelProperty(value="需求日期",example = "2021-09-10")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "需求日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date requireDate;

    /** 备注 */
    @ApiModelProperty("备注")
    @Excel(name = "备注")
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
    public void setPurchasePlanId(Long purchasePlanId) 
    {
        this.purchasePlanId = purchasePlanId;
    }

    public Long getPurchasePlanId() 
    {
        return purchasePlanId;
    }
    public void setMaterialId(Long materialId) 
    {
        this.materialId = materialId;
    }

    public Long getMaterialId() 
    {
        return materialId;
    }
    public void setQuantity(Double quantity)
    {
        this.quantity = quantity;
    }

    public Double getQuantity()
    {
        return quantity;
    }
    public void setRequireQuantity(Double requireQuantity)
    {
        this.requireQuantity = requireQuantity;
    }

    public Double getRequireQuantity()
    {
        return requireQuantity;
    }
    public void setRequireDate(Date requireDate) 
    {
        this.requireDate = requireDate;
    }

    public Date getRequireDate() 
    {
        return requireDate;
    }

    public String getDetailRemark() {
        return detailRemark;
    }

    public void setDetailRemark(String detailRemark) {
        this.detailRemark = detailRemark;
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
            .append("purchasePlanId", getPurchasePlanId())
            .append("materialId", getMaterialId())
            .append("quantity", getQuantity())
            .append("requireQuantity", getRequireQuantity())
            .append("requireDate", getRequireDate())
            .append("detailRemark", getDetailRemark())
            .toString();
    }
}
