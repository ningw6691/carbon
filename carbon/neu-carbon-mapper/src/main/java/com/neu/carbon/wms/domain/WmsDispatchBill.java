package com.neu.carbon.wms.domain;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.neu.common.annotation.Excel;
import com.neu.common.core.domain.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 车辆调度单对象 wms_dispatch_bill
 * 
 * @author neuedu
 * @date 2022-07-04
 */
@ApiModel("车辆调度单")
public class WmsDispatchBill extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    @ApiModelProperty("编号")
    private Long id;

    /** 单据号 */
    @ApiModelProperty("单据号")
    @Excel(name = "单据号")
    private String serialNo;

    /** 承运单号 */
    @ApiModelProperty("承运单号")
    private Long carrierApplyId;

    /** 出库单号 */
    @ApiModelProperty("出库单号")
    private Long outWarehouseId;

    /** 销售订单id */
    @ApiModelProperty("销售订单id")
    @Excel(name = "销售订单id")
    private Long saleOrderId;
    
    /** 销售订单号 */
    @ApiModelProperty("销售订单号")
    @Excel(name = "销售订单号")
    private String orderNo;
    
    /** 客户id */
    @ApiModelProperty("客户id")
    @Excel(name = "客户id")
    private Long customerId;
    
    /** 发货日期 */
    @ApiModelProperty("发货日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发货日期",dateFormat = "yyyy-MM-dd")
    private Date deliveryDate;
    
    /** 业务员 */
    @ApiModelProperty("收货地址")
    @Excel(name = "收货地址")
    private String address;
    
    /** 业务员 */
    @ApiModelProperty("业务员")
    @Excel(name = "业务员")
    private String workStaff;
    
    /** 承运单号 */
    @ApiModelProperty("承运单号")
    @Excel(name = "承运单号")
    private String carrierNo;
    
    /** 承运人 */
    @ApiModelProperty("承运人")
    @Excel(name = "承运人")
    private String carrierUser;
    
    /** 运输距离 */
    @ApiModelProperty("运输距离")
    @Excel(name = "运输距离")
    private Double distance;
    
    /** 出库单号 */
    @ApiModelProperty("出库单号")
    @Excel(name = "出库单号")
    private String outWhNo;
    
    /** 联系人 */
    @ApiModelProperty("客户联系人")
    @Excel(name = "客户联系人")
    private String customerContact;

    /** 联系电话 */
    @ApiModelProperty("客户联系电话")
    @Excel(name = "客户联系电话")
    private String customerContactTel;

    /** 车辆调度单明细信息 */
	@ApiModelProperty(hidden = true)
    private List<WmsDispatchBillDetail> wmsDispatchBillDetailList;

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
    public void setCarrierApplyId(Long carrierApplyId) 
    {
        this.carrierApplyId = carrierApplyId;
    }

    public Long getCarrierApplyId() 
    {
        return carrierApplyId;
    }
    public void setOutWarehouseId(Long outWarehouseId) 
    {
        this.outWarehouseId = outWarehouseId;
    }

    public Long getOutWarehouseId() 
    {
        return outWarehouseId;
    }
    public void setSaleOrderId(Long saleOrderId) 
    {
        this.saleOrderId = saleOrderId;
    }

    public Long getSaleOrderId() 
    {
        return saleOrderId;
    }

    public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getWorkStaff() {
		return workStaff;
	}

	public void setWorkStaff(String workStaff) {
		this.workStaff = workStaff;
	}

	public String getCarrierNo() {
		return carrierNo;
	}

	public void setCarrierNo(String carrierNo) {
		this.carrierNo = carrierNo;
	}

	public String getCarrierUser() {
		return carrierUser;
	}

	public void setCarrierUser(String carrierUser) {
		this.carrierUser = carrierUser;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public String getOutWhNo() {
		return outWhNo;
	}

	public void setOutWhNo(String outWhNo) {
		this.outWhNo = outWhNo;
	}

	public String getCustomerContact() {
		return customerContact;
	}

	public void setCustomerContact(String customerContact) {
		this.customerContact = customerContact;
	}

	public String getCustomerContactTel() {
		return customerContactTel;
	}

	public void setCustomerContactTel(String customerContactTel) {
		this.customerContactTel = customerContactTel;
	}

	public List<WmsDispatchBillDetail> getWmsDispatchBillDetailList()
    {
        return wmsDispatchBillDetailList;
    }

    public void setWmsDispatchBillDetailList(List<WmsDispatchBillDetail> wmsDispatchBillDetailList)
    {
        this.wmsDispatchBillDetailList = wmsDispatchBillDetailList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("serialNo", getSerialNo())
            .append("carrierApplyId", getCarrierApplyId())
            .append("outWarehouseId", getOutWarehouseId())
            .append("saleOrderId", getSaleOrderId())
            .append("remark", getRemark())
            .append("wmsDispatchBillDetailList", getWmsDispatchBillDetailList())
            .toString();
    }
}
