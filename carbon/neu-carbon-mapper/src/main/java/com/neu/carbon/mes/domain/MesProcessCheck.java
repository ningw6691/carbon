package com.neu.carbon.mes.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.neu.common.annotation.Excel;
import com.neu.common.core.domain.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 生产采样单对象 mes_process_check
 * 
 * @author neuedu
 * @date 2022-07-12
 */
@ApiModel("生产采样单")
@Data
public class MesProcessCheck extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    @ApiModelProperty("编号")
    private Long id;

    /** 流水号 */
    @ApiModelProperty("流水号")
    @Excel(name = "流水号")
    private String serialNo;

    /** 生产计划编号 */
    @ApiModelProperty("生产计划编号")
    @Excel(name = "生产计划编号")
    private Long productPlanId;

    /** 计划排产编号 */
    @ApiModelProperty("计划排产编号")
    @Excel(name = "计划排产编号")
    private Long productScheduleId;

    /** 生产线编号 */
    @ApiModelProperty("生产线编号")
    @Excel(name = "生产线编号")
    private Long productLineId;

    /** 生产作业编号 */
    @ApiModelProperty("生产作业编号")
    @Excel(name = "生产作业编号")
    private Long productJobId;

    /** 物料档案编号 */
    @ApiModelProperty("物料档案编号")
    @Excel(name = "物料档案编号")
    private Long materialId;

    /** 检验标准编号 */
    @ApiModelProperty("检验标准编号")
    @Excel(name = "检验标准编号")
    private Long checkStandardId;

    /** 生产数量 */
    @ApiModelProperty("生产数量")
    @Excel(name = "生产数量")
    private Double productQuantity;

    /** 采样数量 */
    @ApiModelProperty("采样数量")
    @Excel(name = "采样数量")
    private Double checkQuantity;

    /** 检验值 */
    @ApiModelProperty("检验值")
    @Excel(name = "检验值")
    private Double checkValue;

    /** 标准值上限 */
    @ApiModelProperty("标准值上限")
    @Excel(name = "标准值上限")
    private Double maxValue;

    /** 标准值下限 */
    @ApiModelProperty("标准值下限")
    @Excel(name = "标准值下限")
    private Double minValue;

    /** 检验结果：0不合格1合格 */
    @ApiModelProperty("检验结果：0不合格1合格")
    @Excel(name = "检验结果：0不合格1合格")
    private String checkResult;

    /** 检验人 */
    @ApiModelProperty("检验人")
    @Excel(name = "检验人")
    private String checkUser;

    /** 检验时间 */
    @ApiModelProperty(value="检验时间",example = "2021-09-10 10:20:40")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "检验时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date checkDate;

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

    /** 作业名称 */
    @ApiModelProperty("作业名称")
    @Excel(name = "作业名称")
    private String productJobName;

    /** 计划编号 */
    @ApiModelProperty("计划编号")
    @Excel(name = "计划编号")
    private String productPlanNo;

    @ApiModelProperty("检验项")
    @Excel(name = "检验项")
    private String checkItemName;

}
