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
import com.neu.carbon.wms.domain.WmsCarrierApply;
import com.neu.carbon.wms.domain.WmsCarrierApplyDetail;
import com.neu.carbon.wms.domain.WmsMaterialInfo;
import com.neu.carbon.wms.service.IWmsCarrierApplyService;
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
 * 承运申请Controller
 * 
 * @author neuedu
 * @date 2022-07-01
 */
@Api(tags = {"承运申请"})
@RestController
@RequestMapping("/transportApply/carrierApply")
public class WmsCarrierApplyController extends BaseController
{
    @Autowired
    private IWmsCarrierApplyService wmsCarrierApplyService;
    @Autowired
    private IWmsMaterialInfoService wmsMaterialInfoService;

    /**
     * 查询承运申请列表
     */
    @GetMapping("/list")
    @ApiOperation("查询承运申请列表")
    @DynamicResponseParameters(properties = {
	        @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = WmsCarrierApply.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list(WmsCarrierApply wmsCarrierApply)
    {
        startPage();
        List<WmsCarrierApply> list = wmsCarrierApplyService.selectWmsCarrierApplyList(wmsCarrierApply);
        return getDataTable(list);
    }
    
    @PreAuthorize("@ss.hasPermi('transportApply:carrierApply:audit')")
    @GetMapping("/audit/list")
    @ApiOperation("查询待审核和已审核承运申请列表")
    @DynamicResponseParameters(properties = {
	        @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = WmsCarrierApply.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list4Audit(WmsCarrierApply wmsCarrierApply)
    {
        startPage();
        //默认查询待审核
        List<String> applyStatusList = new ArrayList<String>();
        applyStatusList.add(UserConstants.APPLY_STATUS_WAIT_AUDIT);
        applyStatusList.add(UserConstants.APPLY_STATUS_HAS_AUDIT);
        wmsCarrierApply.getParams().put("applyStatusList", applyStatusList);
        if(StringUtils.isBlank(wmsCarrierApply.getApplyStatus())&&StringUtils.isBlank(wmsCarrierApply.getAuditStatus())) {
        	wmsCarrierApply.setApplyStatus(UserConstants.APPLY_STATUS_WAIT_AUDIT);
        }
        List<WmsCarrierApply> list = wmsCarrierApplyService.selectWmsCarrierApplyList(wmsCarrierApply);
        return getDataTable(list);
    }

    /**
     * 导出承运申请列表
     */
    @ApiOperation("导出承运申请列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "文件名")
    })
    @PreAuthorize("@ss.hasPermi('transportApply:carrierApply:export')")
    @Log(title = "承运申请", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(WmsCarrierApply wmsCarrierApply)
    {
        List<WmsCarrierApply> list = wmsCarrierApplyService.selectWmsCarrierApplyList(wmsCarrierApply);
        ExcelUtil<WmsCarrierApply> util = new ExcelUtil<WmsCarrierApply>(WmsCarrierApply.class);
        return util.exportExcel(list, "carrierApply");
    }

    /**
     * 获取承运申请详细信息
     */
    @ApiOperation("获取承运申请详细信息")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "data", value = "返回业务数据", dataTypeClass = WmsCarrierApply.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
    	WmsCarrierApply apply = wmsCarrierApplyService.selectWmsCarrierApplyById(id);
    	List<WmsCarrierApplyDetail> applyDetail = apply.getWmsCarrierApplyDetailList();
    	if(applyDetail!=null&&!applyDetail.isEmpty()) {
    		//获取物料档案信息
    		applyDetail.stream().forEach(detail->{
            	WmsMaterialInfo material = wmsMaterialInfoService.selectWmsMaterialInfoById(detail.getMaterialId());
            	if(material!=null) {
                	detail.setMaterialModel(material.getModel());
                	detail.setMaterialName(material.getName());
                	detail.setMaterialSpecification(material.getSpecification());
                	detail.setMaterialUnit(material.getUnit());
                	detail.setPrice(material.getPrice());
            	}
            });
    	}
        return AjaxResult.success(apply);
    }

    /**
     * 新增承运申请
     */
    @ApiOperation("新增承运申请")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('transportApply:carrierApply:add')")
    @Log(title = "承运申请", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmsCarrierApply wmsCarrierApply)
    {
    	wmsCarrierApply.setApplyStatus(UserConstants.APPLY_STATUS_NO_SUBMIT);
    	Long loginUserId = SecurityUtils.getLoginUser().getUser().getUserId();
    	wmsCarrierApply.setApplyUser(String.valueOf(loginUserId));
        return toAjax(wmsCarrierApplyService.insertWmsCarrierApply(wmsCarrierApply));
    }

    /**
     * 修改承运申请
     */
    @ApiOperation("修改承运申请")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('transportApply:carrierApply:edit')")
    @Log(title = "承运申请", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmsCarrierApply wmsCarrierApply)
    {
    	wmsCarrierApply.setApplyStatus(UserConstants.APPLY_STATUS_NO_SUBMIT);
    	wmsCarrierApply.setAuditUser("");
    	wmsCarrierApply.setAuditStatus("");
    	wmsCarrierApply.setAuditTime(null);
    	wmsCarrierApply.setAuditComment("");
        return toAjax(wmsCarrierApplyService.updateWmsCarrierApply(wmsCarrierApply));
    }
    
    @ApiOperation("提交承运申请")
    @PreAuthorize("@ss.hasPermi('transportApply:carrierApply:edit')")
    @Log(title = "承运申请", businessType = BusinessType.UPDATE)
    @PutMapping("/submit")
    public AjaxResult submit(@RequestBody WmsCarrierApply wmsCarrierApply) {
    	wmsCarrierApply.setApplyStatus(UserConstants.APPLY_STATUS_WAIT_AUDIT);
    	wmsCarrierApply.setApplyTime(DateUtils.getNowDate());
    	
    	if(wmsCarrierApply.getId()!=null) {
    		WmsCarrierApply apply = wmsCarrierApplyService.selectWmsCarrierApplyById(wmsCarrierApply.getId());
    		if(apply==null) {
    			return AjaxResult.error("此承运申请不存在，不能提交！");
    		}
    		wmsCarrierApply.setAuditUser("");
    		wmsCarrierApply.setAuditStatus("");
    		wmsCarrierApply.setAuditTime(null);
    		wmsCarrierApply.setAuditComment("");
    		return toAjax(wmsCarrierApplyService.updateWmsCarrierApply(wmsCarrierApply));
    	}else {
    		Long loginUserId = SecurityUtils.getLoginUser().getUser().getUserId();
    		wmsCarrierApply.setApplyUser(String.valueOf(loginUserId));
    		return toAjax(wmsCarrierApplyService.insertWmsCarrierApply(wmsCarrierApply));
    	}
    }
    
    @ApiOperation("审核承运申请")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('transportApply:carrierApply:edit')")
    @Log(title = "承运申请", businessType = BusinessType.UPDATE)
    @PutMapping("/audit")
    public AjaxResult audit(@RequestBody WmsCarrierApply wmsCarrierApply) {
    	wmsCarrierApply.setApplyStatus(UserConstants.APPLY_STATUS_HAS_AUDIT);
    	wmsCarrierApply.setAuditTime(DateUtils.getNowDate());
    	Long loginUserId = SecurityUtils.getLoginUser().getUser().getUserId();
    	wmsCarrierApply.setAuditUser(String.valueOf(loginUserId));
    	return toAjax(wmsCarrierApplyService.updateWmsCarrierApply(wmsCarrierApply));
    }

    /**
     * 删除承运申请
     */
    @ApiOperation("删除承运申请")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('transportApply:carrierApply:remove')")
    @Log(title = "承运申请", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(wmsCarrierApplyService.deleteWmsCarrierApplyByIds(ids));
    }
}
