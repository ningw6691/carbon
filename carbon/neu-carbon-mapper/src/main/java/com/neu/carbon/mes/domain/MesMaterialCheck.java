package com.neu.carbon.mes.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.neu.common.annotation.Excel;
import com.neu.common.core.domain.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 物料检验单对象 mes_material_check
 * 
 * @author neuedu
 * @date 2022-07-12
 */
@ApiModel("物料检验单")
public class MesMaterialCheck extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    @ApiModelProperty("编号")
    @Excel(name = "编号")
    private Long id;

    /** 流水号 */
    @ApiModelProperty("流水号")
    @Excel(name = "流水号")
    private String serialNo;

    /** 领料单编号 */
    @ApiModelProperty("领料单编号")
    private Long materialRequisitionId;

    /** 领料单流水 */
    @ApiModelProperty("领料单流水")
    @Excel(name = "领料单流水")
    private String materialRequisitionNo;

    /** 物料档案编号 */
    @ApiModelProperty("物料档案编号")
    private Long materialId;

    /** 检验标准编号 */
    @ApiModelProperty("检验标准编号")
    private Long checkStandardId;

    /** 需求数量 */
    @ApiModelProperty("需求数量")
    @Excel(name = "需求数量")
    private Double requireQuantity;

    /** 已领数量 */
    @ApiModelProperty("已领数量")
    @Excel(name = "已领数量")
    private Double receiveQuantity;

    /** 抽检数量 */
    @ApiModelProperty("抽检数量")
    @Excel(name = "抽检数量")
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
    @Excel(name = "检验结果", dictType = "mes_check_result")
    private String checkResult;

    /** 检验人 */
    @ApiModelProperty("检验人")
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

    @ApiModelProperty("检验项")
    @Excel(name = "检验项")
    private String checkItemName;

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
    public void setMaterialRequisitionId(Long materialRequisitionId) 
    {
        this.materialRequisitionId = materialRequisitionId;
    }

    public Long getMaterialRequisitionId() 
    {
        return materialRequisitionId;
    }
    public void setMaterialRequisitionNo(String materialRequisitionNo) 
    {
        this.materialRequisitionNo = materialRequisitionNo;
    }

    public String getMaterialRequisitionNo() 
    {
        return materialRequisitionNo;
    }
    public void setMaterialId(Long materialId) 
    {
        this.materialId = materialId;
    }

    public Long getMaterialId() 
    {
        return materialId;
    }
    public void setCheckStandardId(Long checkStandardId) 
    {
        this.checkStandardId = checkStandardId;
    }

    public Long getCheckStandardId() 
    {
        return checkStandardId;
    }
    public void setRequireQuantity(Double requireQuantity) 
    {
        this.requireQuantity = requireQuantity;
    }

    public Double getRequireQuantity() 
    {
        return requireQuantity;
    }
    public void setReceiveQuantity(Double receiveQuantity) 
    {
        this.receiveQuantity = receiveQuantity;
    }

    public Double getReceiveQuantity() 
    {
        return receiveQuantity;
    }
    public void setCheckQuantity(Double checkQuantity) 
    {
        this.checkQuantity = checkQuantity;
    }

    public Double getCheckQuantity() 
    {
        return checkQuantity;
    }
    public void setCheckValue(Double checkValue) 
    {
        this.checkValue = checkValue;
    }

    public Double getCheckValue() 
    {
        return checkValue;
    }
    public void setMaxValue(Double maxValue) 
    {
        this.maxValue = maxValue;
    }

    public Double getMaxValue() 
    {
        return maxValue;
    }
    public void setMinValue(Double minValue) 
    {
        this.minValue = minValue;
    }

    public Double getMinValue() 
    {
        return minValue;
    }
    public void setCheckResult(String checkResult) 
    {
        this.checkResult = checkResult;
    }

    public String getCheckResult() 
    {
        return checkResult;
    }
    public void setCheckUser(String checkUser) 
    {
        this.checkUser = checkUser;
    }

    public String getCheckUser() 
    {
        return checkUser;
    }
    public void setCheckDate(Date checkDate) 
    {
        this.checkDate = checkDate;
    }

    public Date getCheckDate() 
    {
        return checkDate;
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

    public String getCheckItemName() {
        return checkItemName;
    }

    public void setCheckItemName(String checkItemName) {
        this.checkItemName = checkItemName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("serialNo", getSerialNo())
            .append("materialRequisitionId", getMaterialRequisitionId())
            .append("materialRequisitionNo", getMaterialRequisitionNo())
            .append("materialId", getMaterialId())
            .append("checkStandardId", getCheckStandardId())
            .append("requireQuantity", getRequireQuantity())
            .append("receiveQuantity", getReceiveQuantity())
            .append("checkQuantity", getCheckQuantity())
            .append("checkValue", getCheckValue())
            .append("maxValue", getMaxValue())
            .append("minValue", getMinValue())
            .append("checkResult", getCheckResult())
            .append("remark", getRemark())
            .append("checkUser", getCheckUser())
            .append("checkDate", getCheckDate())
            .toString();
    }
}
