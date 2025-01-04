package com.neu.carbon.mes.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.neu.common.annotation.Excel;
import com.neu.common.core.domain.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 质检标准对象 mes_check_standard
 *
 * @author neuedu
 * @date 2022-07-12
 */
@ApiModel("质检标准")
public class  MesCheckStandard extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    @ApiModelProperty("编号")
    private Long id;

    /** 物料id */
    @ApiModelProperty("物料id")
    @Excel(name = "物料id")
    private Long materialId;

    /** 检验项目名称 */
    @ApiModelProperty("检验项目名称")
    @Excel(name = "检验项目名称")
    private String checkItemName;

    /** 检验类型：1来料检验2产品检验3生产采样 */
    @ApiModelProperty("检验类型：1来料检验2产品检验3生产采样")
    @Excel(name = "检验类型", dictType = "mes_check_type")
    private String checkType;

    /** 标准值上限 */
    @ApiModelProperty("标准值上限")
    @Excel(name = "标准值上限")
    private Double maxValue;

    /** 标准值下限 */
    @ApiModelProperty("标准值下限")
    @Excel(name = "标准值下限")
    private Double minValue;

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

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setMaterialId(Long materialId)
    {
        this.materialId = materialId;
    }

    public Long getMaterialId()
    {
        return materialId;
    }
    public void setCheckItemName(String checkItemName)
    {
        this.checkItemName = checkItemName;
    }

    public String getCheckItemName()
    {
        return checkItemName;
    }
    public void setCheckType(String checkType)
    {
        this.checkType = checkType;
    }

    public String getCheckType()
    {
        return checkType;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("materialId", getMaterialId())
                .append("checkItemName", getCheckItemName())
                .append("checkType", getCheckType())
                .append("maxValue", getMaxValue())
                .append("minValue", getMinValue())
                .append("remark", getRemark())
                .toString();
    }
}
