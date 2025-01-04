package com.neu.carbon.mes.controller;

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
import com.neu.carbon.mes.domain.MesProductSchedule;
import com.neu.carbon.mes.domain.MesProductScheduleMaterial;
import com.neu.carbon.mes.service.IMesProductPlanService;
import com.neu.carbon.mes.service.IMesProductScheduleService;
import com.neu.carbon.wms.domain.WmsMaterialInfo;
import com.neu.carbon.wms.service.IWmsMaterialInfoService;
import com.neu.common.annotation.Log;
import com.neu.common.core.controller.BaseController;
import com.neu.common.core.domain.AjaxResult;
import com.neu.common.core.page.TableDataInfo;
import com.neu.common.enums.BusinessType;
import com.neu.common.utils.poi.ExcelUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 计划排产Controller
 * 
 * @author neuedu
 * @date 2022-07-13
 */
@Api(tags = {"计划排产"})
@RestController
@RequestMapping("/mesPlan/productSchedule")
public class MesProductScheduleController extends BaseController
{
    @Autowired
    private IMesProductScheduleService mesProductScheduleService;
    @Autowired
    private IMesProductPlanService mesProductPlanService;
    @Autowired
    private IWmsMaterialInfoService wmsMaterialInfoService;

    /**
     * 查询计划排产列表
     */
    @GetMapping("/list")
    @ApiOperation("查询计划排产列表")
    @DynamicResponseParameters(properties = {
	        @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = MesProductSchedule.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list(MesProductSchedule mesProductSchedule)
    {
        startPage();
        List<MesProductSchedule> list = mesProductScheduleService.selectMesProductScheduleList(mesProductSchedule);
        return getDataTable(list);
    }

    /**
     * 导出计划排产列表
     */
    @ApiOperation("导出计划排产列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "文件名")
    })
    @PreAuthorize("@ss.hasPermi('mesPlan:productSchedule:export')")
    @Log(title = "计划排产", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(MesProductSchedule mesProductSchedule)
    {
        List<MesProductSchedule> list = mesProductScheduleService.selectMesProductScheduleList(mesProductSchedule);
        ExcelUtil<MesProductSchedule> util = new ExcelUtil<MesProductSchedule>(MesProductSchedule.class);
        return util.exportExcel(list, "productSchedule");
    }

    /**
     * 获取计划排产详细信息
     */
    @ApiOperation("获取计划排产详细信息")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "data", value = "返回业务数据", dataTypeClass = MesProductSchedule.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
    	MesProductSchedule schedule = mesProductScheduleService.selectMesProductScheduleById(id);
    	//获取计划信息
    	MesProductPlan plan = mesProductPlanService.selectMesProductPlanById(schedule.getPlanId());
    	schedule.setPlanNo(plan.getSerialNo());
    	//获取产品信息
    	WmsMaterialInfo product = wmsMaterialInfoService.selectWmsMaterialInfoById(schedule.getMaterialId());
    	if(product!=null) {
    		schedule.setMaterialCode(product.getCode());
    		schedule.setMaterialModel(product.getModel());
    		schedule.setMaterialName(product.getName());
    		schedule.setMaterialSpecification(product.getSpecification());
    		schedule.setMaterialUnit(product.getUnit());
    	}
    	//获取明细物料信息
    	List<MesProductScheduleMaterial> detailList = schedule.getMesProductScheduleMaterialList();
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
        return AjaxResult.success(schedule);
    }

    /**
     * 新增计划排产
     */
    @ApiOperation("新增计划排产")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('mesPlan:productSchedule:add')")
    @Log(title = "计划排产", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MesProductSchedule mesProductSchedule)
    {
        return toAjax(mesProductScheduleService.insertMesProductSchedule(mesProductSchedule));
    }

    /**
     * 修改计划排产
     */
    @ApiOperation("修改计划排产")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('mesPlan:productSchedule:edit')")
    @Log(title = "计划排产", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MesProductSchedule mesProductSchedule)
    {
        return toAjax(mesProductScheduleService.updateMesProductSchedule(mesProductSchedule));
    }


    
    /**
     * 下发计划排产
     */
    @ApiOperation("发布计划排产")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('mesPlan:productSchedule:edit')")
    @Log(title = "计划排产", businessType = BusinessType.UPDATE)
    @PutMapping("/publish")
    public AjaxResult publish(@RequestBody MesProductSchedule mesProductSchedule)
    {
    	MesProductSchedule schedule = mesProductScheduleService.selectMesProductScheduleById(mesProductSchedule.getId());
    	schedule.setStatus("1");
    	return toAjax(mesProductScheduleService.updateMesProductScheduleStatus(schedule));
    }

    /**
     * 删除计划排产
     */
    @ApiOperation("删除计划排产")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('mesPlan:productSchedule:remove')")
    @Log(title = "计划排产", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(mesProductScheduleService.deleteMesProductScheduleByIds(ids));
    }
}
