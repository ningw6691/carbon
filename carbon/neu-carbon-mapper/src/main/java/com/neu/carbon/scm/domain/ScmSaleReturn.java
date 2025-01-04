package com.neu.carbon.scm.domain;

import java.math.BigDecimal;
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
 * 销售退货对象 scm_sale_return
 * 
 * @author neuedu
 * @date 2022-07-08
 */
@ApiModel("销售退货")
public class ScmSaleReturn extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    @ApiModelProperty("编号")
    private Long id;

    /** 退货单号 */
    @ApiModelProperty("退货单号")
    @Excel(name = "退货单号")
    private String returnNo;

    /** 发货id */
    @ApiModelProperty("发货id")
    private Long deliveryId;

    /** 发货单号 */
    @ApiModelProperty("发货单号")
    @Excel(name = "发货单号")
    private String deliveryNo;

    /** 合同id */
    @ApiModelProperty("合同id")
    private Long contractId;

    /** 合同编号 */
    @ApiModelProperty("合同编号")
    @Excel(name = "合同编号")
    private String contractNo;

    /** 客户id */
    @ApiModelProperty("客户id")
    private Long customerId;

    /** 客户名称 */
    @ApiModelProperty("客户名称")
    @Excel(name = "客户名称")
    private String customerName;

    /** 退货日期 */
    @ApiModelProperty(value="退货日期",example = "2021-09-10")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "退货日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date returnDate;

    /** 退款金额 */
    @ApiModelProperty("退款金额")
    @Excel(name = "退款金额")
    private BigDecimal returnAmount;

    /** 退款原因 */
    @ApiModelProperty("退款原因")
    private String returnReason;

    /** 入库状态 */
    @ApiModelProperty("入库状态")
    @Excel(name = "入库状态")
    private String status;

    /** 退货明细信息 */
	@ApiModelProperty(hidden = true)
    private List<ScmSaleReturnDetail> scmSaleReturnDetailList;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setReturnNo(String returnNo) 
    {
        this.returnNo = returnNo;
    }

    public String getReturnNo() 
    {
        return returnNo;
    }
    public void setDeliveryId(Long deliveryId) 
    {
        this.deliveryId = deliveryId;
    }

    public Long getDeliveryId() 
    {
        return deliveryId;
    }
    public void setDeliveryNo(String deliveryNo) 
    {
        this.deliveryNo = deliveryNo;
    }

    public String getDeliveryNo() 
    {
        return deliveryNo;
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
    public void setCustomerId(Long customerId) 
    {
        this.customerId = customerId;
    }

    public Long getCustomerId() 
    {
        return customerId;
    }
    public void setCustomerName(String customerName) 
    {
        this.customerName = customerName;
    }

    public String getCustomerName() 
    {
        return customerName;
    }
    public void setReturnDate(Date returnDate) 
    {
        this.returnDate = returnDate;
    }

    public Date getReturnDate() 
    {
        return returnDate;
    }
    public void setReturnAmount(BigDecimal returnAmount) 
    {
        this.returnAmount = returnAmount;
    }

    public BigDecimal getReturnAmount() 
    {
        return returnAmount;
    }
    public void setReturnReason(String returnReason) 
    {
        this.returnReason = returnReason;
    }

    public String getReturnReason() 
    {
        return returnReason;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    public List<ScmSaleReturnDetail> getScmSaleReturnDetailList()
    {
        return scmSaleReturnDetailList;
    }

    public void setScmSaleReturnDetailList(List<ScmSaleReturnDetail> scmSaleReturnDetailList)
    {
        this.scmSaleReturnDetailList = scmSaleReturnDetailList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("returnNo", getReturnNo())
            .append("deliveryId", getDeliveryId())
            .append("deliveryNo", getDeliveryNo())
            .append("contractId", getContractId())
            .append("contractNo", getContractNo())
            .append("customerId", getCustomerId())
            .append("customerName", getCustomerName())
            .append("returnDate", getReturnDate())
            .append("returnAmount", getReturnAmount())
            .append("returnReason", getReturnReason())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("remark", getRemark())
            .append("scmSaleReturnDetailList", getScmSaleReturnDetailList())
            .toString();
    }
}
