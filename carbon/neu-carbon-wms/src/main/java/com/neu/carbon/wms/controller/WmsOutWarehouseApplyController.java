package com.neu.carbon.wms.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicResponseParameters;
import com.neu.carbon.mes.domain.MesMaterialRequisition;
import com.neu.carbon.mes.service.IMesMaterialRequisitionService;
import com.neu.carbon.scm.domain.ScmSaleDelivery;
import com.neu.carbon.scm.service.IScmSaleDeliveryService;
import com.neu.carbon.wms.domain.WmsMaterialInfo;
import com.neu.carbon.wms.domain.WmsOutWarehouseApply;
import com.neu.carbon.wms.domain.WmsOutWarehouseApplyDetail;
import com.neu.carbon.wms.domain.WmsWarehouseMaterialDetail;
import com.neu.carbon.wms.service.IWmsMaterialInfoService;
import com.neu.carbon.wms.service.IWmsOutWarehouseApplyService;
import com.neu.carbon.wms.service.IWmsWarehouseMaterialDetailService;
import com.neu.common.annotation.Log;
import com.neu.common.constant.UserConstants;
import com.neu.common.core.controller.BaseController;
import com.neu.common.core.domain.AjaxResult;
import com.neu.common.core.page.TableDataInfo;
import com.neu.common.enums.BusinessType;
import com.neu.common.utils.DateUtils;
import com.neu.common.utils.SecurityUtils;
import com.neu.common.utils.StringUtils;
import com.neu.common.utils.poi.ExcelUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 出库申请Controller
 * 
 * @author neuedu
 * @date 2022-06-29
 */
@Api(tags = {"出库申请"})
@RestController
@RequestMapping("/wmsApply/outWarehouseApply")
public class WmsOutWarehouseApplyController extends BaseController
{
    @Autowired
    private IWmsOutWarehouseApplyService wmsOutWarehouseApplyService;
    @Autowired
    private IWmsMaterialInfoService wmsMaterialInfoService;
    @Autowired
    private IWmsWarehouseMaterialDetailService wmsWarehouseMaterialDetailService;
    @Autowired
    private IScmSaleDeliveryService saleDeliveryService;
    @Autowired
    private IMesMaterialRequisitionService requisitionService;

    /**
     * 查询出库申请列表
     */
    @GetMapping("/list")
    @ApiOperation("查询出库申请列表")
    @DynamicResponseParameters(properties = {
	        @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = WmsOutWarehouseApply.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list(WmsOutWarehouseApply wmsOutWarehouseApply)
    {
        startPage();
        Long loginUserId = SecurityUtils.getLoginUser().getUser().getUserId();
        if(!SecurityUtils.isAdmin(loginUserId)) {
        	wmsOutWarehouseApply.setApplyUser(String.valueOf(loginUserId));
        }
        List<WmsOutWarehouseApply> list = wmsOutWarehouseApplyService.selectWmsOutWarehouseApplyList(wmsOutWarehouseApply);
        list.stream().forEach(apply->{
        	this.getBizBillNo(apply);
        });
        return getDataTable(list);
    }
    
    private void getBizBillNo(WmsOutWarehouseApply apply) {
    	//领料出库
    	if("3".equals(apply.getBizType())) {
    		//获取领料单号
    		MesMaterialRequisition requisition = requisitionService.selectMesMaterialRequisitionById(apply.getBizBillId());
    		if(requisition!=null) {
    			apply.setBizBillNo(requisition.getSerialNo());
    		}
    	}
    	//销售发货出库
    	if("4".equals(apply.getBizType())) {
    		ScmSaleDelivery delivery = saleDeliveryService.selectScmSaleDeliveryById(apply.getBizBillId());
    		if(delivery!=null) {
    			apply.setBizBillNo(delivery.getDeliveryNo());
    			apply.setContractNo(delivery.getContractNo());
    		}
    	}
    }
    
    @PreAuthorize("@ss.hasPermi('wmsApply:outWarahouseApply:audit')")
    @GetMapping("/audit/list")
    @ApiOperation("查询待审核和已审核出库申请列表")
    @DynamicResponseParameters(properties = {
	        @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = WmsOutWarehouseApply.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list4Audit(WmsOutWarehouseApply wmsOutWarehouseApply)
    {
        startPage();
        //默认查询待审核
        List<String> applyStatusList = new ArrayList<String>();
        applyStatusList.add(UserConstants.APPLY_STATUS_WAIT_AUDIT);
        applyStatusList.add(UserConstants.APPLY_STATUS_HAS_AUDIT);
        wmsOutWarehouseApply.getParams().put("applyStatusList", applyStatusList);
        if(StringUtils.isBlank(wmsOutWarehouseApply.getApplyStatus())&&StringUtils.isBlank(wmsOutWarehouseApply.getAuditStatus())) {
        	wmsOutWarehouseApply.setApplyStatus(UserConstants.APPLY_STATUS_WAIT_AUDIT);
        }
        List<WmsOutWarehouseApply> list = wmsOutWarehouseApplyService.selectWmsOutWarehouseApplyList(wmsOutWarehouseApply);
        list.stream().forEach(apply->{
        	this.getBizBillNo(apply);
        });
        return getDataTable(list);
    }

    /**
     * 导出出库申请列表
     */
    @ApiOperation("导出出库申请列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "文件名")
    })
    @PreAuthorize("@ss.hasPermi('wmsApply:outWarehouseApply:export')")
    @Log(title = "出库申请", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(WmsOutWarehouseApply wmsOutWarehouseApply)
    {
    	Long loginUserId = SecurityUtils.getLoginUser().getUser().getUserId();
        if(!SecurityUtils.isAdmin(loginUserId)) {
        	wmsOutWarehouseApply.setApplyUser(String.valueOf(loginUserId));
        }
        List<WmsOutWarehouseApply> list = wmsOutWarehouseApplyService.selectWmsOutWarehouseApplyList(wmsOutWarehouseApply);
        list.stream().forEach(apply->{
        	this.getBizBillNo(apply);
        });
        ExcelUtil<WmsOutWarehouseApply> util = new ExcelUtil<WmsOutWarehouseApply>(WmsOutWarehouseApply.class);
        return util.exportExcel(list, "outWarehouseApply");
    }

    /**
     * 获取出库申请详细信息
     */
    @ApiOperation("获取出库申请详细信息")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "data", value = "返回业务数据", dataTypeClass = WmsOutWarehouseApply.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
    	WmsOutWarehouseApply apply = wmsOutWarehouseApplyService.selectWmsOutWarehouseApplyById(id);
    	if(apply==null) {
    		return AjaxResult.error("出库单不存在");
    	}
    	List<WmsOutWarehouseApplyDetail> applyDetail = apply.getWmsOutWarehouseApplyDetailList();
    	if(applyDetail!=null&&!applyDetail.isEmpty()) {
    		//获取物料档案信息
    		applyDetail.stream().forEach(detail->{
            	WmsMaterialInfo material = wmsMaterialInfoService.selectWmsMaterialInfoById(detail.getMaterialId());
            	if(material!=null) {
            		detail.setMaterialCode(material.getCode());
                	detail.setMaterialModel(material.getModel());
                	detail.setMaterialName(material.getName());
                	detail.setMaterialSpecification(material.getSpecification());
                	detail.setMaterialUnit(material.getUnit());
            	}
            });
    	}
    	this.getBizBillNo(apply);
        return AjaxResult.success(apply);
    }

    /**
     * 新增出库申请
     */
    @ApiOperation("新增出库申请")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('wmsApply:outWarehouseApply:add')")
    @Log(title = "出库申请", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmsOutWarehouseApply wmsOutWarehouseApply)
    {
    	wmsOutWarehouseApply.setApplyStatus(UserConstants.APPLY_STATUS_NO_SUBMIT);
    	Long loginUserId = SecurityUtils.getLoginUser().getUser().getUserId();
    	wmsOutWarehouseApply.setApplyUser(String.valueOf(loginUserId));
        return toAjax(wmsOutWarehouseApplyService.insertWmsOutWarehouseApply(wmsOutWarehouseApply));
    }

    /**
     * 修改出库申请
     */
    @ApiOperation("修改出库申请")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('wmsApply:outWarehouseApply:edit')")
    @Log(title = "出库申请", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmsOutWarehouseApply wmsOutWarehouseApply)
    {
    	wmsOutWarehouseApply.setApplyStatus(UserConstants.APPLY_STATUS_NO_SUBMIT);
    	wmsOutWarehouseApply.setAuditUser("");
    	wmsOutWarehouseApply.setAuditStatus("");
    	wmsOutWarehouseApply.setAuditTime(null);
    	wmsOutWarehouseApply.setAuditComment("");
        return toAjax(wmsOutWarehouseApplyService.updateWmsOutWarehouseApply(wmsOutWarehouseApply));
    }
    
    @ApiOperation("提交出库申请")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('wmsApply:outWarahouseApply:edit')")
    @Log(title = "出库申请", businessType = BusinessType.UPDATE)
    @PutMapping("/submit")
    public AjaxResult submit(@RequestBody WmsOutWarehouseApply wmsOutWarehouseApply) {
    	//校验库存是否充足
    	List<WmsOutWarehouseApplyDetail> applyList = wmsOutWarehouseApply.getWmsOutWarehouseApplyDetailList();
    	final StringBuilder error = new StringBuilder();
    	applyList.stream().forEach(detail->{
    		double outInventory = detail.getOutQuantity()==null?0:detail.getOutQuantity();
    		WmsWarehouseMaterialDetail wmsWarehouseMaterialDetail = new WmsWarehouseMaterialDetail();
    		wmsWarehouseMaterialDetail.setWarehouseId(detail.getWarehouseId());
    		wmsWarehouseMaterialDetail.setWhRegionId(detail.getWhRegionId());
    		wmsWarehouseMaterialDetail.setWhLocationId(detail.getWhLocationId());
    		wmsWarehouseMaterialDetail.setMaterialId(detail.getMaterialId());
    		wmsWarehouseMaterialDetail.setBatchNo(detail.getBatchNo());
    		List<WmsWarehouseMaterialDetail> materialDetail = wmsWarehouseMaterialDetailService.selectWmsWarehouseMaterialDetailList(wmsWarehouseMaterialDetail);
    		if(materialDetail==null||materialDetail.isEmpty()) {
    			error.append("物料【"+detail.getMaterialName()+"】当前库存：0，出库数量："+outInventory+";库存不足不能出库");
    		}else {
    			WmsWarehouseMaterialDetail material = materialDetail.get(0);
    			double total = material.getInventory()==null?0:material.getInventory();
    			double lock = material.getLockInventory()==null?0:material.getLockInventory();
    			double inventory = total - lock;
    			if(inventory<outInventory) {
    				error.append("物料【"+detail.getMaterialName()+"】当前库存："+inventory+"，出库数量："+outInventory+";库存不足不能出库");
    			}
    		}
    	});
    	String msg = error.toString();
    	if(!StringUtils.isBlank(msg)) {
    		return AjaxResult.error(msg);
    	}
    	wmsOutWarehouseApply.setApplyStatus(UserConstants.APPLY_STATUS_WAIT_AUDIT);
    	wmsOutWarehouseApply.setApplyTime(DateUtils.getNowDate());
    	
    	if(wmsOutWarehouseApply.getId()!=null) {
    		WmsOutWarehouseApply apply = wmsOutWarehouseApplyService.selectWmsOutWarehouseApplyById(wmsOutWarehouseApply.getId());
    		if(apply==null) {
    			return AjaxResult.error("此出库申请不存在，不能提交！");
    		}
    		wmsOutWarehouseApply.setAuditUser("");
    		wmsOutWarehouseApply.setAuditStatus("");
    		wmsOutWarehouseApply.setAuditTime(null);
    		wmsOutWarehouseApply.setAuditComment("");
    		return toAjax(wmsOutWarehouseApplyService.updateWmsOutWarehouseApply(wmsOutWarehouseApply));
    	}else {
    		Long loginUserId = SecurityUtils.getLoginUser().getUser().getUserId();
    		wmsOutWarehouseApply.setApplyUser(String.valueOf(loginUserId));
    		return toAjax(wmsOutWarehouseApplyService.insertWmsOutWarehouseApply(wmsOutWarehouseApply));
    	}
    }
    
    @ApiOperation("审核出库申请")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('wmsApply:outWarahouseApply:audit')")
    @Log(title = "出库申请", businessType = BusinessType.UPDATE)
    @PutMapping("/audit")
    public AjaxResult audit(@RequestBody WmsOutWarehouseApply wmsOutWarehouseApply) {
    	wmsOutWarehouseApply.setApplyStatus(UserConstants.APPLY_STATUS_HAS_AUDIT);
    	wmsOutWarehouseApply.setAuditTime(DateUtils.getNowDate());
    	Long loginUserId = SecurityUtils.getLoginUser().getUser().getUserId();
    	wmsOutWarehouseApply.setAuditUser(String.valueOf(loginUserId));
    	return toAjax(wmsOutWarehouseApplyService.updateWmsOutWarehouseApply(wmsOutWarehouseApply));
    }

    /**
     * 删除出库申请
     */
    @ApiOperation("删除出库申请")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('wmsApply:outWarehouseApply:remove')")
    @Log(title = "出库申请", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(wmsOutWarehouseApplyService.deleteWmsOutWarehouseApplyByIds(ids));
    }
    
    @ApiOperation("执行拣货")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('wmsApply:outWarahouseApply:edit')")
    @Log(title = "执行拣货", businessType = BusinessType.UPDATE)
    @PostMapping("/outWarehouse")
    public AjaxResult outWarehouse(@RequestBody WmsOutWarehouseApply wmsOutWarehouseApply)
    {
    	wmsOutWarehouseApplyService.performOutWarehouse(wmsOutWarehouseApply.getId());
        return AjaxResult.success();
    }
    
    @ApiOperation("出库盘点")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('wmsApply:outWarahouseApply:edit')")
    @Log(title = "出库盘点", businessType = BusinessType.UPDATE)
    @PostMapping("/check/outInventory")
    public AjaxResult checkOutInventory(@RequestBody WmsOutWarehouseApply wmsOutWarehouseApply)
    {
    	WmsOutWarehouseApply apply = wmsOutWarehouseApplyService.selectWmsOutWarehouseApplyById(wmsOutWarehouseApply.getId());
    	//设置状态
    	apply.setBillStatus("2");
    	return AjaxResult.success(wmsOutWarehouseApplyService.updateWmsOutWarehouseApply(apply));
    }
    
    @ApiOperation("发货")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('wmsApply:outWarahouseApply:edit')")
    @Log(title = "发货", businessType = BusinessType.UPDATE)
    @PostMapping("/delivery")
    public AjaxResult delivery(@RequestBody WmsOutWarehouseApply wmsOutWarehouseApply)
    {
    	wmsOutWarehouseApplyService.performDelivery(wmsOutWarehouseApply.getId());
    	return AjaxResult.success();
    }
}
