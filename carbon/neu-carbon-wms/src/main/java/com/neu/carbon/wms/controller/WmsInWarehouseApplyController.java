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
import com.neu.carbon.mes.domain.MesProductFinish;
import com.neu.carbon.mes.service.IMesProductFinishService;
import com.neu.carbon.scm.domain.ScmPurchaseArrive;
import com.neu.carbon.scm.domain.ScmSaleReturn;
import com.neu.carbon.scm.service.IScmPurchaseArriveService;
import com.neu.carbon.scm.service.IScmSaleReturnService;
import com.neu.carbon.wms.domain.WmsInWarehouseApply;
import com.neu.carbon.wms.domain.WmsInWarehouseApplyDetail;
import com.neu.carbon.wms.domain.WmsMaterialInfo;
import com.neu.carbon.wms.service.IWmsInWarehouseApplyService;
import com.neu.carbon.wms.service.IWmsMaterialInfoService;
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
 * 入库申请Controller
 * 
 * @author neusoft
 * @date 2022-06-27
 */
@Api(tags = {"入库申请"})
@RestController
@RequestMapping("/wmsApply/inWarahouseApply")
public class WmsInWarehouseApplyController extends BaseController
{
    @Autowired
    private IWmsInWarehouseApplyService wmsInWarehouseApplyService;
    @Autowired
    private IWmsMaterialInfoService wmsMaterialInfoService;
    @Autowired
    private IScmSaleReturnService saleReturnService;
    @Autowired
    private IScmPurchaseArriveService purchaseArriveService;
    @Autowired
    private IMesProductFinishService mesProductFinishService;

    /**
     * 查询入库申请列表
     */
    @PreAuthorize("@ss.hasPermi('wmsApply:inWarahouseApply:list')")
    @GetMapping("/list")
    @ApiOperation("查询入库申请列表")
    @DynamicResponseParameters(properties = {
	        @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = WmsInWarehouseApply.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list(WmsInWarehouseApply wmsInWarehouseApply)
    {
        startPage();
        Long loginUserId = SecurityUtils.getLoginUser().getUser().getUserId();
        if(!SecurityUtils.isAdmin(loginUserId)) {
        	wmsInWarehouseApply.setApplyUser(String.valueOf(loginUserId));
        }
        List<WmsInWarehouseApply> list = wmsInWarehouseApplyService.selectWmsInWarehouseApplyList(wmsInWarehouseApply);
        list.stream().forEach(apply->{
        	getBizBillNo(apply);
        });
        return getDataTable(list);
    }
    
    private void getBizBillNo(WmsInWarehouseApply apply) {
    	//生产入库
    	if("1".equals(apply.getBizType())) {
    		//获取生产完工单号
    		MesProductFinish finish = mesProductFinishService.selectMesProductFinishById(apply.getBizBillId());
    		if(finish!=null) {
    			apply.setBizBillNo(finish.getSerialNo());
    		}
    	}
    	//采购入库
    	if("2".equals(apply.getBizType())) {
    		ScmPurchaseArrive arrive = purchaseArriveService.selectScmPurchaseArriveById(apply.getBizBillId());
    		if(arrive!=null) {
    			apply.setBizBillNo(arrive.getArriveNo());
    			apply.setContractNo(arrive.getContractNo());
    		}
    	}
    	//退货入库
    	if("5".equals(apply.getBizType())) {
    		ScmSaleReturn saleReturn = saleReturnService.selectScmSaleReturnById(apply.getBizBillId());
    		if(saleReturn!=null) {
    			apply.setBizBillNo(saleReturn.getReturnNo());
    			apply.setContractNo(saleReturn.getContractNo());
    		}
    	}
    }
    
    @PreAuthorize("@ss.hasPermi('wmsApply:inWarahouseApply:audit')")
    @GetMapping("/audit/list")
    @ApiOperation("查询待审核和已审核入库申请列表")
    @DynamicResponseParameters(properties = {
	        @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = WmsInWarehouseApply.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list4Audit(WmsInWarehouseApply wmsInWarehouseApply)
    {
        startPage();
        //默认查询待审核
        List<String> applyStatusList = new ArrayList<String>();
        applyStatusList.add(UserConstants.APPLY_STATUS_WAIT_AUDIT);
        applyStatusList.add(UserConstants.APPLY_STATUS_HAS_AUDIT);
        wmsInWarehouseApply.getParams().put("applyStatusList", applyStatusList);
        if(StringUtils.isBlank(wmsInWarehouseApply.getApplyStatus())&&StringUtils.isBlank(wmsInWarehouseApply.getAuditStatus())) {
        	wmsInWarehouseApply.setApplyStatus(UserConstants.APPLY_STATUS_WAIT_AUDIT);
        }
        List<WmsInWarehouseApply> list = wmsInWarehouseApplyService.selectWmsInWarehouseApplyList(wmsInWarehouseApply);
        list.stream().forEach(apply->{
        	getBizBillNo(apply);
        });
        return getDataTable(list);
    }

    /**
     * 导出入库申请列表
     */
    @ApiOperation("导出入库申请列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "文件名")
    })
    @PreAuthorize("@ss.hasPermi('wmsApply:inWarahouseApply:export')")
    @Log(title = "入库申请", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(WmsInWarehouseApply wmsInWarehouseApply)
    {
        List<WmsInWarehouseApply> list = wmsInWarehouseApplyService.selectWmsInWarehouseApplyList(wmsInWarehouseApply);
        list.stream().forEach(apply->{
        	getBizBillNo(apply);
        });
        ExcelUtil<WmsInWarehouseApply> util = new ExcelUtil<WmsInWarehouseApply>(WmsInWarehouseApply.class);
        return util.exportExcel(list, "inWarahouseApply");
    }

    /**
     * 获取入库申请详细信息
     */
    @ApiOperation("获取入库申请详细信息")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "data", value = "返回业务数据", dataTypeClass = WmsInWarehouseApply.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
    	WmsInWarehouseApply apply = wmsInWarehouseApplyService.selectWmsInWarehouseApplyById(id);
    	this.getBizBillNo(apply);
    	List<WmsInWarehouseApplyDetail> applyDetail = apply.getWmsInWarehouseApplyDetailList();
    	if(applyDetail!=null&&!applyDetail.isEmpty()) {
    		//获取物料档案信息
    		applyDetail.stream().forEach(detail->{
            	WmsMaterialInfo material = wmsMaterialInfoService.selectWmsMaterialInfoById(detail.getMaterialId());
            	if(material!=null) {
            		detail.setMaterialCode(material.getCode());
                	detail.setMaterialModel(material.getModel());
                	detail.setMaterialName(material.getName());
                	detail.setMaterialPrice(material.getPrice());
                	detail.setMaterialSpecification(material.getSpecification());
                	detail.setMaterialUnit(material.getUnit());
            	}
            });
    	}
        return AjaxResult.success(apply);
    }

    /**
     * 新增入库申请
     */
    @ApiOperation("新增入库申请")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('wmsApply:inWarahouseApply:add')")
    @Log(title = "入库申请", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmsInWarehouseApply wmsInWarehouseApply)
    {
    	wmsInWarehouseApply.setApplyStatus(UserConstants.APPLY_STATUS_NO_SUBMIT);
    	Long loginUserId = SecurityUtils.getLoginUser().getUser().getUserId();
		wmsInWarehouseApply.setApplyUser(String.valueOf(loginUserId));
        return toAjax(wmsInWarehouseApplyService.insertWmsInWarehouseApply(wmsInWarehouseApply));
    }

    /**
     * 修改入库申请
     */
    @ApiOperation("修改入库申请")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('wmsApply:inWarahouseApply:edit')")
    @Log(title = "入库申请", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmsInWarehouseApply wmsInWarehouseApply)
    {
    	wmsInWarehouseApply.setApplyStatus(UserConstants.APPLY_STATUS_NO_SUBMIT);
    	wmsInWarehouseApply.setAuditUser("");
		wmsInWarehouseApply.setAuditStatus("");
		wmsInWarehouseApply.setAuditTime(null);
		wmsInWarehouseApply.setAuditComment("");
        return toAjax(wmsInWarehouseApplyService.updateWmsInWarehouseApply(wmsInWarehouseApply));
    }
    
    @ApiOperation("提交入库申请")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('wmsApply:inWarahouseApply:edit')")
    @Log(title = "入库申请", businessType = BusinessType.UPDATE)
    @PutMapping("/submit")
    public AjaxResult submit(@RequestBody WmsInWarehouseApply wmsInWarehouseApply) {
    	wmsInWarehouseApply.setApplyStatus(UserConstants.APPLY_STATUS_WAIT_AUDIT);
		wmsInWarehouseApply.setApplyTime(DateUtils.getNowDate());
    	if(wmsInWarehouseApply.getId()!=null) {
    		WmsInWarehouseApply apply = wmsInWarehouseApplyService.selectWmsInWarehouseApplyById(wmsInWarehouseApply.getId());
    		if(apply==null) {
    			return AjaxResult.error("此入库申请不存在，不能提交！");
    		}
    		wmsInWarehouseApply.setAuditUser("");
    		wmsInWarehouseApply.setAuditStatus("");
    		wmsInWarehouseApply.setAuditTime(null);
    		wmsInWarehouseApply.setAuditComment("");
    		return toAjax(wmsInWarehouseApplyService.updateWmsInWarehouseApply(wmsInWarehouseApply));
    	}else {
    		Long loginUserId = SecurityUtils.getLoginUser().getUser().getUserId();
    		wmsInWarehouseApply.setApplyUser(String.valueOf(loginUserId));
    		return toAjax(wmsInWarehouseApplyService.insertWmsInWarehouseApply(wmsInWarehouseApply));
    	}
    }
    
    @ApiOperation("审核入库申请")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('wmsApply:inWarahouseApply:audit')")
    @Log(title = "入库申请", businessType = BusinessType.UPDATE)
    @PutMapping("/audit")
    public AjaxResult audit(@RequestBody WmsInWarehouseApply wmsInWarehouseApply) {
    	wmsInWarehouseApply.setApplyStatus(UserConstants.APPLY_STATUS_HAS_AUDIT);
    	wmsInWarehouseApply.setAuditTime(DateUtils.getNowDate());
    	Long loginUserId = SecurityUtils.getLoginUser().getUser().getUserId();
    	wmsInWarehouseApply.setAuditUser(String.valueOf(loginUserId));
    	return toAjax(wmsInWarehouseApplyService.updateWmsInWarehouseApply(wmsInWarehouseApply));
    }

    /**
     * 删除入库申请
     */
    @ApiOperation("删除入库申请")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('wmsApply:inWarahouseApply:remove')")
    @Log(title = "入库申请", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(wmsInWarehouseApplyService.deleteWmsInWarehouseApplyByIds(ids));
    }
    
    @ApiOperation("执行入库")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('wmsApply:inWarahouseApply:add')")
    @Log(title = "执行入库", businessType = BusinessType.INSERT)
    @PostMapping("/inWarehouse")
    public AjaxResult inWarehouse(@RequestBody WmsInWarehouseApply wmsInWarehouseApply)
    {
    	wmsInWarehouseApplyService.performInWarehouse(wmsInWarehouseApply.getId());
        return AjaxResult.success();
    }
}
