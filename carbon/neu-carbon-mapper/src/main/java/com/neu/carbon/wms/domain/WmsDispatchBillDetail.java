package com.neu.carbon.wms.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.neu.common.annotation.Excel;
import com.neu.common.core.domain.BaseEntity;

/**
 * 车辆调度单明细对象 wms_dispatch_bill_detail
 * 
 * @author neuedu
 * @date 2022-07-04
 */
public class WmsDispatchBillDetail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 调度单id */
    private Long dispatchBillId;

    /** 车牌号 */
    @Excel(name = "车牌号")
    private String plateNo;

    /** 驾驶员 */
    @Excel(name = "驾驶员")
    private String driver;

    /** 联系电话 */
    @Excel(name = "联系电话")
    private String contactTel;

    /** 载重 */
    @Excel(name = "载重")
    private Double truckLoad;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setDispatchBillId(Long dispatchBillId) 
    {
        this.dispatchBillId = dispatchBillId;
    }

    public Long getDispatchBillId() 
    {
        return dispatchBillId;
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
    

    public Double getTruckLoad() {
		return truckLoad;
	}

	public void setTruckLoad(Double truckLoad) {
		this.truckLoad = truckLoad;
	}

	@Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("dispatchBillId", getDispatchBillId())
            .append("plateNo", getPlateNo())
            .append("driver", getDriver())
            .append("contactTel", getContactTel())
            .append("load", getTruckLoad())
            .append("remark", getRemark())
            .toString();
    }
}
