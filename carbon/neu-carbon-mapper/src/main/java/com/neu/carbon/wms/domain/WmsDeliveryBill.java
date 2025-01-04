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
 * 配送管理对象 wms_delivery_bill
 * 
 * @author neuedu
 * @date 2022-07-05
 */
@ApiModel("配送管理")
public class WmsDeliveryBill extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    @ApiModelProperty("编号")
    private Long id;

    /** 单据号 */
    @ApiModelProperty("单据号")
    @Excel(name = "单据号")
    private String serialNo;

    /** 调度id */
    @ApiModelProperty("调度id")
    private Long dispatchBillId;

    /** 调度单号 */
    @ApiModelProperty("调度单号")
    @Excel(name = "调度单号")
    private String dispatchBillNo;

    /** 车牌号 */
    @ApiModelProperty("车牌号")
    @Excel(name = "车牌号")
    private String plateNo;

    /** 司机 */
    @ApiModelProperty("司机")
    @Excel(name = "司机")
    private String driver;

    /** 司机联系电话 */
    @ApiModelProperty("司机联系电话")
    @Excel(name = "司机联系电话")
    private String contactTel;

    /** 配送状态 */
    @ApiModelProperty("配送状态")
    @Excel(name = "配送状态")
    private String billStatus;

    /** 配送时间 */
    @ApiModelProperty(value="配送时间",example = "2021-09-10 10:20:40")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "配送时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date deliveryTime;

    /** 到达时间 */
    @ApiModelProperty(value="到达时间",example = "2021-09-10 10:20:40")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "到达时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date arriveTime;

    /** 客户 */
    @ApiModelProperty("客户")
    @Excel(name = "客户")
    private Long customerId;

    /** 客户联系人 */
    @ApiModelProperty("客户联系人")
    @Excel(name = "客户联系人")
    private String customerContact;

    /** 客户联系电话 */
    @ApiModelProperty("客户联系电话")
    @Excel(name = "客户联系电话")
    private String customerContactTel;

    /** 配送地址 */
    @ApiModelProperty("配送地址")
    @Excel(name = "配送地址")
    private String deliveryAddress;

    /** 发货地址 */
    @ApiModelProperty("发货地址")
    @Excel(name = "发货地址")
    private String shipAddress;

    /** 配送单明细信息 */
	@ApiModelProperty(hidden = true)
    private List<WmsDeliveryBillDetail> wmsDeliveryBillDetailList;

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
    public void setDispatchBillId(Long dispatchBillId) 
    {
        this.dispatchBillId = dispatchBillId;
    }

    public Long getDispatchBillId() 
    {
        return dispatchBillId;
    }
    public void setDispatchBillNo(String dispatchBillNo) 
    {
        this.dispatchBillNo = dispatchBillNo;
    }

    public String getDispatchBillNo() 
    {
        return dispatchBillNo;
    }
    public void setPlateNo(String plateNo) 
    {
        this.plateNo = plateNo;
    }

    public String getPlateNo() 
    {
        return plateNo;
    }
    public void setDriver(String driver) 
    {
        this.driver = driver;
    }

    public String getDriver() 
    {
        return driver;
    }
    public void setContactTel(String contactTel) 
    {
        this.contactTel = contactTel;
    }

    public String getContactTel() 
    {
        return contactTel;
    }
    public void setBillStatus(String billStatus) 
    {
        this.billStatus = billStatus;
    }

    public String getBillStatus() 
    {
        return billStatus;
    }
    public void setDeliveryTime(Date deliveryTime) 
    {
        this.deliveryTime = deliveryTime;
    }

    public Date getDeliveryTime() 
    {
        return deliveryTime;
    }
    public void setArriveTime(Date arriveTime) 
    {
        this.arriveTime = arriveTime;
    }

    public Date getArriveTime() 
    {
        return arriveTime;
    }
    public void setCustomerId(Long customerId) 
    {
        this.customerId = customerId;
    }

    public Long getCustomerId() 
    {
        return customerId;
    }
    public void setCustomerContact(String customerContact) 
    {
        this.customerContact = customerContact;
    }

    public String getCustomerContact() 
    {
        return customerContact;
    }
    public void setCustomerContactTel(String customerContactTel) 
    {
        this.customerContactTel = customerContactTel;
    }

    public String getCustomerContactTel() 
    {
        return customerContactTel;
    }
    public void setDeliveryAddress(String deliveryAddress) 
    {
        this.deliveryAddress = deliveryAddress;
    }

    public String getDeliveryAddress() 
    {
        return deliveryAddress;
    }
    public void setShipAddress(String shipAddress) 
    {
        this.shipAddress = shipAddress;
    }

    public String getShipAddress() 
    {
        return shipAddress;
    }

    public List<WmsDeliveryBillDetail> getWmsDeliveryBillDetailList()
    {
        return wmsDeliveryBillDetailList;
    }

    public void setWmsDeliveryBillDetailList(List<WmsDeliveryBillDetail> wmsDeliveryBillDetailList)
    {
        this.wmsDeliveryBillDetailList = wmsDeliveryBillDetailList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("serialNo", getSerialNo())
            .append("dispatchBillId", getDispatchBillId())
            .append("dispatchBillNo", getDispatchBillNo())
            .append("plateNo", getPlateNo())
            .append("driver", getDriver())
            .append("contactTel", getContactTel())
            .append("billStatus", getBillStatus())
            .append("deliveryTime", getDeliveryTime())
            .append("arriveTime", getArriveTime())
            .append("customerId", getCustomerId())
            .append("customerContact", getCustomerContact())
            .append("customerContactTel", getCustomerContactTel())
            .append("deliveryAddress", getDeliveryAddress())
            .append("shipAddress", getShipAddress())
            .append("remark", getRemark())
            .append("wmsDeliveryBillDetailList", getWmsDeliveryBillDetailList())
            .toString();
    }
}
