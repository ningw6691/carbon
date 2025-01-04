package com.neu.carbon.scm.domain;

import java.util.List;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.neu.common.annotation.Excel;
import com.neu.common.core.domain.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 销售计划对象 scm_sale_plan
 *
 * @author neuedu
 * @date 2022-07-13
 */
@ApiModel("销售计划")
public class ScmSalePlan extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    @ApiModelProperty("编号")
    private Long id;

    /** 计划单号 */
    @ApiModelProperty("计划单号")
    @Excel(name = "计划单号")
    private String planNo;

    /** 计划标题 */
    @ApiModelProperty("计划标题")
    @Excel(name = "计划标题")
    private String title;

    /** 计划类型：1年度计划 2季度计划 3月计划 */
    @ApiModelProperty("计划类型：1年度计划 2季度计划 3月计划")
    @Excel(name = "计划类型", dictType = "sale_plan_type")
    private String type;

    /** 起始日期 */
    @ApiModelProperty(value="起始日期",example = "2021-09-10")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "起始日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startDate;

    /** 结束日期 */
    @ApiModelProperty(value="结束日期",example = "2021-09-10")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endDate;

    /** 指标配置信息 */
    @ApiModelProperty(hidden = true)
    private List<ScmSalePlanDetail> scmSalePlanDetailList;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setPlanNo(String planNo)
    {
        this.planNo = planNo;
    }

    public String getPlanNo()
    {
        return planNo;
    }
    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getTitle()
    {
        return title;
    }
    public void setType(String type)
    {
        this.type = type;
    }

    public String getType()
    {
        return type;
    }
    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    public Date getStartDate()
    {
        return startDate;
    }
    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
    }

    public Date getEndDate()
    {
        return endDate;
    }

    public List<ScmSalePlanDetail> getScmSalePlanDetailList()
    {
        return scmSalePlanDetailList;
    }

    public void setScmSalePlanDetailList(List<ScmSalePlanDetail> scmSalePlanDetailList)
    {
        this.scmSalePlanDetailList = scmSalePlanDetailList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("planNo", getPlanNo())
                .append("title", getTitle())
                .append("type", getType())
                .append("startDate", getStartDate())
                .append("endDate", getEndDate())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("remark", getRemark())
                .append("scmSalePlanDetailList", getScmSalePlanDetailList())
                .toString();
    }
}