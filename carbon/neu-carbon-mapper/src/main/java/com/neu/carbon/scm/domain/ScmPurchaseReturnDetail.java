package com.neu.carbon.scm.domain;

import java.math.BigDecimal;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.neu.common.annotation.Excel;
import com.neu.common.core.domain.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 退货明细对象 scm_purchase_return_detail
 * 
 * @author neuedu
 * @date 2022-07-07
 */
@ApiModel("退货明细")
@Data
public class ScmPurchaseReturnDetail extends BaseEntity
{
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

    /** 生产批号 */
    @ApiModelProperty("生产批号")
    @Excel(name = "生产批号")
    private String batchNo;

    /** 单价 */
    @ApiModelProperty("单价")
    @Excel(name = "单价")
    private BigDecimal price;

    /** 订货数量 */
    @ApiModelProperty("订货数量")
    @Excel(name = "订货数量")
    private Double bookQuantity;

    /** 到货数量 */
    @ApiModelProperty("到货数量")
    @Excel(name = "到货数量")
    private Double arriveQuantity;

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

}
