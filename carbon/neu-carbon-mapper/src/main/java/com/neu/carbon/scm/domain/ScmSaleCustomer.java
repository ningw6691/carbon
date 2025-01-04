package com.neu.carbon.scm.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.neu.common.annotation.Excel;
import com.neu.common.core.domain.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 客户档案对象 scm_sale_customer
 * 
 * @author neuedu
 * @date 2022-07-02
 */
@ApiModel("客户档案")
public class ScmSaleCustomer extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    @ApiModelProperty("编号")
    private Long id;

    /** 客户名称 */
    @ApiModelProperty("客户名称")
    @Excel(name = "客户名称")
    private String name;

    /** 简称 */
    @ApiModelProperty("简称")
    private String shortName;

    /** 公司地址 */
    @ApiModelProperty("公司地址")
    @Excel(name = "公司地址")
    private String addr;

    /** 邮编 */
    @ApiModelProperty("邮编")
    private String postcode;

    /** 电话 */
    @ApiModelProperty("电话")
    private String tel;

    /** 开户银行 */
    @ApiModelProperty("开户银行")
    private String bank;

    /** 银行账号 */
    @ApiModelProperty("银行账号")
    private String account;

    /** 税号 */
    @ApiModelProperty("税号")
    private String taxNo;

    /** 联系人 */
    @ApiModelProperty("联系人")
    @Excel(name = "联系人")
    private String contact;

    /** 联系电话 */
    @ApiModelProperty("联系电话")
    @Excel(name = "联系电话")
    private String contactTel;

    /** 客户分类 */
    @ApiModelProperty("客户分类")
    @Excel(name = "客户分类", dictType = "scm_customer_type")
    private String type;

    /** 信用等级 */
    @ApiModelProperty("信用等级")
    @Excel(name = "信用等级", dictType = "scm_customer_level")
    private String creditLevel;

    /** 状态 */
    @ApiModelProperty("状态")
    @Excel(name = "状态", dictType = "valid_status")
    private String status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setShortName(String shortName) 
    {
        this.shortName = shortName;
    }

    public String getShortName() 
    {
        return shortName;
    }
    public void setAddr(String addr) 
    {
        this.addr = addr;
    }

    public String getAddr() 
    {
        return addr;
    }
    public void setPostcode(String postcode) 
    {
        this.postcode = postcode;
    }

    public String getPostcode() 
    {
        return postcode;
    }
    public void setTel(String tel) 
    {
        this.tel = tel;
    }

    public String getTel() 
    {
        return tel;
    }
    public void setBank(String bank) 
    {
        this.bank = bank;
    }

    public String getBank() 
    {
        return bank;
    }
    public void setAccount(String account) 
    {
        this.account = account;
    }

    public String getAccount() 
    {
        return account;
    }
    public void setTaxNo(String taxNo) 
    {
        this.taxNo = taxNo;
    }

    public String getTaxNo() 
    {
        return taxNo;
    }
    public void setContact(String contact) 
    {
        this.contact = contact;
    }

    public String getContact() 
    {
        return contact;
    }
    public void setContactTel(String contactTel) 
    {
        this.contactTel = contactTel;
    }

    public String getContactTel() 
    {
        return contactTel;
    }
    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }
    public void setCreditLevel(String creditLevel) 
    {
        this.creditLevel = creditLevel;
    }

    public String getCreditLevel() 
    {
        return creditLevel;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("shortName", getShortName())
            .append("addr", getAddr())
            .append("postcode", getPostcode())
            .append("tel", getTel())
            .append("bank", getBank())
            .append("account", getAccount())
            .append("taxNo", getTaxNo())
            .append("contact", getContact())
            .append("contactTel", getContactTel())
            .append("type", getType())
            .append("creditLevel", getCreditLevel())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
