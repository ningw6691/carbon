package com.neu.carbon.wms.domain;

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
 * 承运申请对象 wms_carrier_apply
 * 
 * @author neuedu
 * @date 2022-07-01
 */
@ApiModel("承运申请")
public class WmsCarrierApply extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    @ApiModelProperty("编号")
    private Long id;

    /** 单据号 */
    @ApiModelProperty("单据号")
    @Excel(name = "单据号")
    private String serialNo;

    /** 客户 */
    @ApiModelProperty("客户")
    @Excel(name = "客户")
    private Long customerId;

    /** 申请人 */
    @ApiModelProperty("申请人")
    //@Excel(name = "申请人")
    private String applyUser;

    /** 申请日期 */
    @ApiModelProperty(value="申请日期",example = "2021-09-10 10:20:40")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "申请日期", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date applyTime;

    /** 申请状态 */
    @ApiModelProperty("申请状态")
    @Excel(name = "申请状态",dictType="apply_status")
    private String applyStatus;

    /** 审核人 */
    @ApiModelProperty("审核人")
    //@Excel(name = "审核人")
    private String auditUser;

    /** 审核日期 */
    @ApiModelProperty(value="审核日期",example = "2021-09-10 10:20:40")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "审核日期", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date auditTime;

    /** 审核状态 */
    @ApiModelProperty("审核状态")
    @Excel(name = "审核状态",dictType="audit_status")
    private String auditStatus;

    /** 出库单id */
    @ApiModelProperty("出库单id")
    private Long outWarehouseId;

    /** 出库单号 */
    @ApiModelProperty("出库单号")
    @Excel(name = "出库单号")
    private String outWarehouseNo;

    /** 销售发货单id */
    @ApiModelProperty("销售发货单id")
    private Long saleDeliveryId;

    /** 销售发货单号 */
    @ApiModelProperty("销售发货单号")
    @Excel(name = "销售发货单号")
    private String saleDeliveryNo;

    /** 收货地址 */
    @ApiModelProperty("收货地址")
    @Excel(name = "收货地址")
    private String shippingAddress;

    /** 联系人 */
    @ApiModelProperty("联系人")
    @Excel(name = "联系人")
    private String contact;

    /** 联系电话 */
    @ApiModelProperty("联系电话")
    @Excel(name = "联系电话")
    private String contactTel;

    /** 承运人 */
    @ApiModelProperty("承运人")
    @Excel(name = "承运人")
    private String carrierUser;

    /** 运输距离 */
    @ApiModelProperty("运输距离")
    @Excel(name = "运输距离")
    private Double distance;

    /** 审核意见 */
    @ApiModelProperty("审核意见")
    @Excel(name = "审核意见")
    private String auditComment;
    
    /** 发货日期 */
    @ApiModelProperty("发货日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发货日期",dateFormat = "yyyy-MM-dd")
    private Date deliveryDate;

    /** 产品承运申请明细信息 */
	@ApiModelProperty(hidden = true)
    private List<WmsCarrierApplyDetail> wmsCarrierApplyDetailList;
	
	private String hasDelivery;

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
    public void setCustomerId(Long customerId) 
    {
        this.customerId = customerId;
    }

    public Long getCustomerId() 
    {
        return customerId;
    }
    public void setApplyUser(String applyUser) 
    {
        this.applyUser = applyUser;
    }

    public String getApplyUser() 
    {
        return applyUser;
    }
    public void setApplyTime(Date applyTime) 
    {
        this.applyTime = applyTime;
    }

    public Date getApplyTime() 
    {
        return applyTime;
    }
    public void setApplyStatus(String applyStatus) 
    {
        this.applyStatus = applyStatus;
    }

    public String getApplyStatus() 
    {
        return applyStatus;
    }
    public void setAuditUser(String auditUser) 
    {
        this.auditUser = auditUser;
    }

    public String getAuditUser() 
    {
        return auditUser;
    }
    public void setAuditTime(Date auditTime) 
    {
        this.auditTime = auditTime;
    }

    public Date getAuditTime() 
    {
        return auditTime;
    }
    public void setAuditStatus(String auditStatus) 
    {
        this.auditStatus = auditStatus;
    }

    public String getAuditStatus() 
    {
        return auditStatus;
    }
    public void setOutWarehouseId(Long outWarehouseId) 
    {
        this.outWarehouseId = outWarehouseId;
    }

    public Long getOutWarehouseId() 
    {
        return outWarehouseId;
    }
    public void setOutWarehouseNo(String outWarehouseNo) 
    {
        this.outWarehouseNo = outWarehouseNo;
    }

    public String getOutWarehouseNo() 
    {
        return outWarehouseNo;
    }
    public void setSaleDeliveryNo(String saleDeliveryNo) 
    {
        this.saleDeliveryNo = saleDeliveryNo;
    }

    public String getSaleDeliveryNo() 
    {
        return saleDeliveryNo;
    }
    public void setShippingAddress(String shippingAddress) 
    {
        this.shippingAddress = shippingAddress;
    }

    public String getShippingAddress() 
    {
        return shippingAddress;
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
    public void setCarrierUser(String carrierUser) 
    {
        this.carrierUser = carrierUser;
    }

    public String getCarrierUser() 
    {
        return carrierUser;
    }
    public void setDistance(Double distance) 
    {
        this.distance = distance;
    }

    public Double getDistance() 
    {
        return distance;
    }
    public void setAuditComment(String auditComment) 
    {
        this.auditComment = auditComment;
    }

    public String getAuditComment() 
    {
        return auditComment;
    }

    public Long getSaleDeliveryId() {
		return saleDeliveryId;
	}

	public void setSaleDeliveryId(Long saleDeliveryId) {
		this.saleDeliveryId = saleDeliveryId;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getHasDelivery() {
		return hasDelivery;
	}

	public void setHasDelivery(String hasDelivery) {
		this.hasDelivery = hasDelivery;
	}

	public List<WmsCarrierApplyDetail> getWmsCarrierApplyDetailList()
    {
        return wmsCarrierApplyDetailList;
    }

    public void setWmsCarrierApplyDetailList(List<WmsCarrierApplyDetail> wmsCarrierApplyDetailList)
    {
        this.wmsCarrierApplyDetailList = wmsCarrierApplyDetailList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("serialNo", getSerialNo())
            .append("customerId", getCustomerId())
            .append("applyUser", getApplyUser())
            .append("applyTime", getApplyTime())
            .append("applyStatus", getApplyStatus())
            .append("auditUser", getAuditUser())
            .append("auditTime", getAuditTime())
            .append("auditStatus", getAuditStatus())
            .append("outWarehouseId", getOutWarehouseId())
            .append("outWarehouseNo", getOutWarehouseNo())
            .append("saleDeliveryId", getSaleDeliveryId())
            .append("saleDeliveryNo", getSaleDeliveryNo())
            .append("shippingAddress", getShippingAddress())
            .append("contact", getContact())
            .append("contactTel", getContactTel())
            .append("carrierUser", getCarrierUser())
            .append("distance", getDistance())
            .append("remark", getRemark())
            .append("auditComment", getAuditComment())
            .append("wmsCarrierApplyDetailList", getWmsCarrierApplyDetailList())
            .toString();
    }
}