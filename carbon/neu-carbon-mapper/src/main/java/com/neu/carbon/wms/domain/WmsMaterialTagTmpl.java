package com.neu.carbon.wms.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.neu.common.annotation.Excel;
import com.neu.common.core.domain.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 物料标签模板对象 wms_material_tag_tmpl
 * 
 * @author neuedu
 * @date 2022-07-07
 */
@ApiModel("物料标签模板")
public class WmsMaterialTagTmpl extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    @ApiModelProperty("编号")
    private Long id;

    /** 物料分类id */
    @ApiModelProperty("物料分类id")
    private Long categoryId;

    /** 显示项类型 */
    @ApiModelProperty("显示项类型")
    @Excel(name = "显示项类型")
    private String fieldType;

    /** 显示顺序号 */
    @ApiModelProperty("显示顺序号")
    @Excel(name = "显示顺序号")
    private Long sort;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setCategoryId(Long categoryId) 
    {
        this.categoryId = categoryId;
    }

    public Long getCategoryId() 
    {
        return categoryId;
    }
    public void setFieldType(String fieldType) 
    {
        this.fieldType = fieldType;
    }

    public String getFieldType() 
    {
        return fieldType;
    }
    public void setSort(Long sort) 
    {
        this.sort = sort;
    }

    public Long getSort() 
    {
        return sort;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("categoryId", getCategoryId())
            .append("fieldType", getFieldType())
            .append("sort", getSort())
            .append("remark", getRemark())
            .toString();
    }
}
