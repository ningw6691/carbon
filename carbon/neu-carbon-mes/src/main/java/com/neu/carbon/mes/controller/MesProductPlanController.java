package com.neu.carbon.mes.controller;

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
import com.neu.carbon.mes.domain.MesProductPlan;
import com.neu.carbon.mes.domain.MesProductPlanDetail;
import com.neu.carbon.mes.service.IMesProductPlanService;
import com.neu.carbon.scm.domain.ScmSaleOrder;
import com.neu.carbon.scm.service.IScmSaleOrderService;
import com.neu.carbon.wms.domain.WmsMaterialInfo;
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
 * 生产计划Controller
 * 
 * @author neuedu
 * @date 2022-07-12
 */
@Api(tags = {"生产计划"})
@RestController
@RequestMapping("/mesPlan/productPlan")
public class MesProductPlanController extends BaseController
{
    @Autowired
    private IMesProductPlanService mesProductPlanService;
    @Autowired
    private IScmSaleOrderService orderService;
    @Autowired
    private IWmsMaterialInfoService wmsMaterialInfoService;

    /**
     * 查询生产计划列表
     */
    @GetMapping("/list")
    @ApiOperation("查询生产计划列表")
    @DynamicResponseParameters(properties = {
	        @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = MesProductPlan.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list(MesProductPlan mesProductPlan)
    {
        startPage();
        Long loginUserId = SecurityUtils.getLoginUser().getUser().getUserId();
        if(!SecurityUtils.isAdmin(loginUserId)) {
        	mesProductPlan.setApplyUser(String.valueOf(loginUserId));
        }
        List<MesProductPlan> list = mesProductPlanService.selectMesProductPlanList(mesProductPlan);
        list.stream().forEach(plan->{
        	ScmSaleOrder order = orderService.selectScmSaleOrderById(plan.getOrderId());
        	if(order!=null) {
        		plan.setOrderNo(order.getOrderNo());
        	}
        });
        return getDataTable(list);
    }
    
    @PreAuthorize("@ss.hasPermi('mesPlan:productPlan:audit')")
    @GetMapping("/audit/list")
    @ApiOperation("查询待审核和已审核生产计划列表")
    @DynamicResponseParameters(properties = {
	        @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = MesProductPlan.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list4Audit(MesProductPlan mesProductPlan)
    {
        startPage();
        //默认查询待审核
        List<String> applyStatusList = new ArrayList<String>();
        applyStatusList.add(UserConstants.APPLY_STATUS_WAIT_AUDIT);
        applyStatusList.add(UserConstants.APPLY_STATUS_HAS_AUDIT);
        mesProductPlan.getParams().put("applyStatusList", applyStatusList);
        if(StringUtils.isBlank(mesProductPlan.getAuditStatus())&&StringUtils.isBlank(mesProductPlan.getApplyStatus())) {
        	mesProductPlan.setApplyStatus(UserConstants.APPLY_STATUS_WAIT_AUDIT);
        }
        List<MesProductPlan> list = mesProductPlanService.selectMesProductPlanList(mesProductPlan);
        return getDataTable(list);
    }

    /**
     * 导出生产计划列表
     */
    @ApiOperation("导出生产计划列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "文件名")
    })
    @PreAuthorize("@ss.hasPermi('mesPlan:productPlan:export')")
    @Log(title = "生产计划", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(MesProductPlan mesProductPlan)
    {
        List<MesProductPlan> list = mesProductPlanService.selectMesProductPlanList(mesProductPlan);
        ExcelUtil<MesProductPlan> util = new ExcelUtil<MesProductPlan>(MesProductPlan.class);
        return util.exportExcel(list, "productPlan");
    }

    /**
     * 获取生产计划详细信息
     */
    @ApiOperation("获取生产计划详细信息")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "data", value = "返回业务数据", dataTypeClass = MesProductPlan.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
    	MesProductPlan plan = mesProductPlanService.selectMesProductPlanById(id);
    	ScmSaleOrder order = orderService.selectScmSaleOrderById(plan.getOrderId());
    	if(order!=null) {
    		plan.setOrderNo(order.getOrderNo());
    	}
    	List<MesProductPlanDetail> detailList = plan.getMesProductPlanDetailList();
    	detailList.stream().forEach(detail->{
    		WmsMaterialInfo material = wmsMaterialInfoService.selectWmsMaterialInfoById(detail.getMaterialId());
        	if(material!=null) {
        		detail.setMaterialCode(material.getCode());
        		detail.setMaterialModel(material.getModel());
        		detail.setMaterialName(material.getName());
        		detail.setMaterialSpecification(material.getSpecification());
        		detail.setMaterialUnit(material.getUnit());
        	}
    	});
        return AjaxResult.success(plan);
    }

    /**
     * 新增生产计划
     */
    @ApiOperation("新增生产计划")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('mesPlan:productPlan:add')")
    @Log(title = "生产计划", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MesProductPlan mesProductPlan)
    {
    	mesProductPlan.setApplyStatus(UserConstants.APPLY_STATUS_NO_SUBMIT);
    	Long loginUserId = SecurityUtils.getLoginUser().getUser().getUserId();
    	mesProductPlan.setApplyUser(String.valueOf(loginUserId));
        return toAjax(mesProductPlanService.insertMesProductPlan(mesProductPlan));
    }

    /**
     * 修改生产计划
     */
    @ApiOperation("修改生产计划")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('mesPlan:productPlan:edit')")
    @Log(title = "生产计划", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MesProductPlan mesProductPlan)
    {
    	mesProductPlan.setApplyStatus(UserConstants.APPLY_STATUS_NO_SUBMIT);
    	mesProductPlan.setAuditUser("");
    	mesProductPlan.setAuditStatus("");
		mesProductPlan.setAuditTime(null);
		mesProductPlan.setAuditComment("");
        return toAjax(mesProductPlanService.updateMesProductPlan(mesProductPlan));
    }

    /**
     * 删除生产计划
     */
    @ApiOperation("删除生产计划")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('mesPlan:productPlan:remove')")
    @Log(title = "生产计划", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(mesProductPlanService.deleteMesProductPlanByIds(ids));
    }
    
    @ApiOperation("提交生产计划")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('mesPlan:productPlan:edit')")
    @Log(title = "生产计划", businessType = BusinessType.UPDATE)
    @PutMapping("/submit")
    public AjaxResult submit(@RequestBody MesProductPlan mesProductPlan)
    {
    	mesProductPlan.setApplyStatus(UserConstants.APPLY_STATUS_WAIT_AUDIT);
    	mesProductPlan.setApplyTime(DateUtils.getNowDate());
    	if(mesProductPlan.getId()!=null) {
    		MesProductPlan apply = mesProductPlanService.selectMesProductPlanById(mesProductPlan.getId());
    		if(apply==null) {
    			return AjaxResult.error("此生产计划不存在，不能提交！");
    		}
    		mesProductPlan.setAuditUser("");
    		mesProductPlan.setAuditStatus("");
    		mesProductPlan.setAuditTime(null);
    		mesProductPlan.setAuditComment("");
    		return toAjax(mesProductPlanService.updateMesProductPlan(mesProductPlan));
    	}else {
    		Long loginUserId = SecurityUtils.getLoginUser().getUser().getUserId();
    		mesProductPlan.setApplyUser(String.valueOf(loginUserId));
    		return toAjax(mesProductPlanService.insertMesProductPlan(mesProductPlan));
    	}
    }
    
    @ApiOperation("审核生产计划")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('mesPlan:productPlan:audit')")
    @Log(title = "生产计划", businessType = BusinessType.UPDATE)
    @PutMapping("/audit")
    public AjaxResult audit(@RequestBody MesProductPlan mesProductPlan) {
    	mesProductPlan.setApplyStatus(UserConstants.APPLY_STATUS_HAS_AUDIT);
    	mesProductPlan.setAuditTime(DateUtils.getNowDate());
    	Long loginUserId = SecurityUtils.getLoginUser().getUser().getUserId();
    	mesProductPlan.setAuditUser(String.valueOf(loginUserId));
    	return toAjax(mesProductPlanService.updateMesProductPlan(mesProductPlan));
    }
}
