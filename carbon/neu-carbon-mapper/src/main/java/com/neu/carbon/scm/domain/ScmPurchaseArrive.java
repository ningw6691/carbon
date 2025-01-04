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
 * 采购到货对象 scm_purchase_arrive
 * 
 * @author neuedu
 * @date 2022-07-06
 */
@ApiModel("采购到货")
public class ScmPurchaseArrive extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    @ApiModelProperty("编号")
    private Long id;

    /** 到货单号 */
    @ApiModelProperty("到货单号")
    @Excel(name = "到货单号")
    private String arriveNo;

    /** 合同id */
    @ApiModelProperty("合同id")
    private Long contractId;

    /** 合同编号 */
    @ApiModelProperty("合同编号")
    @Excel(name = "合同编号")
    private String contractNo;

    /** 供应商id */
    @ApiModelProperty("供应商id")
    private Long supplierId;

    /** 供应商名称 */
    @ApiModelProperty("供应商名称")
    @Excel(name = "供应商名称")
    private String supplierName;

    /** 联系人 */
    @ApiModelProperty("联系人")
    @Excel(name = "联系人")
    private String contact;

    /** 联系电话 */
    @ApiModelProperty("联系电话")
    @Excel(name = "联系电话")
    private String contactTel;


    /** 到货日期 */
    @ApiModelProperty(value="到货日期",example = "2021-09-10")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "到货日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date arriveDate;

    /** 状态 */
    @ApiModelProperty("状态")
    @Excel(name = "状态", dictType = "scm_in_warehouse_status")
    private String status;

    /** 到货明细信息 */
	@ApiModelProperty(hidden = true)
    private List<ScmPurchaseArriveDetail> scmPurchaseArriveDetailList;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setArriveNo(String arriveNo) 
    {
        this.arriveNo = arriveNo;
    }

    public String getArriveNo() 
    {
        return arriveNo;
    }
    public void setContractId(Long contractId)
    {
        this.contractId = contractId;
    }

    public Long getContractId()
    {
        return contractId;
    }
    public void setContractNo(String contractNo) 
    {
        this.contractNo = contractNo;
    }

    public String getContractNo() 
    {
        return contractNo;
    }
    public void setSupplierId(Long supplierId) 
    {
        this.supplierId = supplierId;
    }

    public Long getSupplierId() 
    {
        return supplierId;
    }
    public void setArriveDate(Date arriveDate) 
    {
        this.arriveDate = arriveDate;
    }

    public Date getArriveDate() 
    {
        return arriveDate;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    public List<ScmPurchaseArriveDetail> getScmPurchaseArriveDetailList()
    {
        return scmPurchaseArriveDetailList;
    }

    public void setScmPurchaseArriveDetailList(List<ScmPurchaseArriveDetail> scmPurchaseArriveDetailList)
    {
        this.scmPurchaseArriveDetailList = scmPurchaseArriveDetailList;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContactTel() {
        return contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("arriveNo", getArriveNo())
            .append("contractId", getContractId())
            .append("contractNo", getContractNo())
            .append("supplierId", getSupplierId())
            .append("arriveDate", getArriveDate())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("scmPurchaseArriveDetailList", getScmPurchaseArriveDetailList())
            .toString();
    }
}
