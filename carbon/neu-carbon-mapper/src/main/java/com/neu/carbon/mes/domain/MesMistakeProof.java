package com.neu.carbon.mes.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.neu.common.annotation.Excel;
import com.neu.common.core.domain.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 防错管理对象 mes_mistake_proof
 * 
 * @author neuedu
 * @date 2022-07-19
 */
@ApiModel("防错管理")
public class MesMistakeProof extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    @ApiModelProperty("编号")
    private Long id;

    /** 错误类型 */
    @ApiModelProperty("错误类型")
    @Excel(name = "错误类型")
    private String type;

    /** 错误项 */
    @ApiModelProperty("错误项")
    @Excel(name = "错误项")
    private String mistakeItem;

    /** 导致错误结果 */
    @ApiModelProperty("导致错误结果")
    private String mistakeResult;

    /** 预防手段 */
    @ApiModelProperty("预防手段")
    private String preventMethod;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }
    public void setMistakeItem(String mistakeItem) 
    {
        this.mistakeItem = mistakeItem;
    }

    public String getMistakeItem() 
    {
        return mistakeItem;
    }
    public void setMistakeResult(String mistakeResult) 
    {
        this.mistakeResult = mistakeResult;
    }

    public String getMistakeResult() 
    {
        return mistakeResult;
    }
    public void setPreventMethod(String preventMethod) 
    {
        this.preventMethod = preventMethod;
    }

    public String getPreventMethod() 
    {
        return preventMethod;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("type", getType())
            .append("mistakeItem", getMistakeItem())
            .append("mistakeResult", getMistakeResult())
            .append("preventMethod", getPreventMethod())
            .toString();
    }
}
