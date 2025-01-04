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
 * 入库申请对象 wms_in_warehouse_apply
 * 
 * @author neusoft
 * @date 2022-06-27
 */
@ApiModel("入库申请")
public class WmsInWarehouseApply extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    @ApiModelProperty("编号")
    private Long id;

    /** 流水号 */
    @ApiModelProperty("流水号")
    @Excel(name = "流水号")
    private String serialNo;

    /** 业务单据流水号 */
    @ApiModelProperty("业务单据流id")
    @Excel(name = "业务单据流id")
    private Long bizBillId;

    /** 业务类型 */
    @ApiModelProperty("业务类型")
    @Excel(name = "业务类型",dictType="event_type")
    private String bizType;

    /** 供应商 */
    @ApiModelProperty("供应商")
    //@Excel(name = "供应商")
    private Long supplierId;

    /** 合同id */
    @ApiModelProperty("合同id")
    //@Excel(name = "合同id")
    private Long contractId;

    /** 合同类型 */
    @ApiModelProperty("合同类型")
    @Excel(name = "合同类型",dictType="contract_type")
    private String contractType;

    /** 申请类型 */
    @ApiModelProperty("申请类型")
    @Excel(name = "申请类型",dictType="wms_apply_type")
    private String applyType;

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

    /** 审核意见 */
    @ApiModelProperty("审核意见")
    @Excel(name = "审核意见")
    private String auditComment;
    
    /** 单据状态：0未入库1已入库 */
    @ApiModelProperty("单据状态：0未入库1已入库")
    @Excel(name = "单据状态：0未入库1已入库",dictType="in_warehouse_status")
    private String billStatus;
    
    /** 业务单据流水号 */
    @ApiModelProperty("业务单据流水号")
    @Excel(name = "业务单据流水号")
    private String bizBillNo;
    
    /** 合同号 */
    @ApiModelProperty("合同号")
    @Excel(name = "合同号")
    private String contractNo;

    /** 入库申请明细信息 */
	@ApiModelProperty(hidden = true)
    private List<WmsInWarehouseApplyDetail> wmsInWarehouseApplyDetailList;

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
    public void setBizBillId(Long bizBillId) 
    {
        this.bizBillId = bizBillId;
    }

    public Long getBizBillId() 
    {
        return bizBillId;
    }
    public void setBizType(String bizType) 
    {
        this.bizType = bizType;
    }

    public String getBizType() 
    {
        return bizType;
    }
    public void setSupplierId(Long supplierId) 
    {
        this.supplierId = supplierId;
    }

    public Long getSupplierId() 
    {
        return supplierId;
    }
    public void setContractId(Long contractId) 
    {
        this.contractId = contractId;
    }

    public Long getContractId() 
    {
        return contractId;
    }
    public void setContractType(String contractType) 
    {
        this.contractType = contractType;
    }

    public String getContractType() 
    {
        return contractType;
    }
    public void setApplyType(String applyType) 
    {
        this.applyType = applyType;
    }

    public String getApplyType() 
    {
        return applyType;
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
    public void setAuditComment(String auditComment) 
    {
        this.auditComment = auditComment;
    }

    public String getAuditComment() 
    {
        return auditComment;
    }

    public String getBillStatus() {
		return billStatus;
	}

	public void setBillStatus(String billStatus) {
		this.billStatus = billStatus;
	}

	public String getBizBillNo() {
		return bizBillNo;
	}

	public void setBizBillNo(String bizBillNo) {
		this.bizBillNo = bizBillNo;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public List<WmsInWarehouseApplyDetail> getWmsInWarehouseApplyDetailList()
    {
        return wmsInWarehouseApplyDetailList;
    }

    public void setWmsInWarehouseApplyDetailList(List<WmsInWarehouseApplyDetail> wmsInWarehouseApplyDetailList)
    {
        this.wmsInWarehouseApplyDetailList = wmsInWarehouseApplyDetailList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("serialNo", getSerialNo())
            .append("bizBillId", getBizBillId())
            .append("bizType", getBizType())
            .append("supplierId", getSupplierId())
            .append("contractId", getContractId())
            .append("contractType", getContractType())
            .append("applyType", getApplyType())
            .append("applyUser", getApplyUser())
            .append("applyTime", getApplyTime())
            .append("applyStatus", getApplyStatus())
            .append("auditUser", getAuditUser())
            .append("auditTime", getAuditTime())
            .append("auditStatus", getAuditStatus())
            .append("auditComment", getAuditComment())
            .append("remark", getRemark())
            .append("wmsInWarehouseApplyDetailList", getWmsInWarehouseApplyDetailList())
            .toString();
    }
}
