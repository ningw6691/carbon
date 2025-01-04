package com.neu.carbon.scm.domain;

import com.neu.common.annotation.Excel;
import com.neu.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 发货单明细对象 scm_sale_delivery_detail
 *
 * @author neuedu
 * @date 2022-07-05
 */
@ApiModel("发货单明细")
@Data
public class ScmSaleDeliveryDetail extends BaseEntity
{

    /** 编号 */
    @ApiModelProperty("编号")
    private Long id;

    /** 发货id */
    @ApiModelProperty("发货id")
    @Excel(name = "发货id")
    private Long deliveryId;

    /** 物料id */
    @ApiModelProperty("物料id")
    @Excel(name = "物料id")
    private Long materialId;

    /** 发货数量 */
    @ApiModelProperty("发货数量")
    @Excel(name = "发货数量")
    private Double quantity;

    /** 退货数量 */
    @ApiModelProperty("退货数量")
    @Excel(name = "退货数量")
    private Double returnQuantity;

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
}
