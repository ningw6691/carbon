package com.neu.carbon.wms.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import com.neu.carbon.wms.domain.WmsDispatchBill;
import com.neu.carbon.wms.domain.WmsDispatchBillDetail;
import com.neu.carbon.wms.service.IWmsDispatchBillService;
import com.neu.common.annotation.Log;
import com.neu.common.core.controller.BaseController;
import com.neu.common.core.domain.AjaxResult;
import com.neu.common.core.page.TableDataInfo;
import com.neu.common.enums.BusinessType;
import com.neu.common.utils.poi.ExcelUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 车辆调度单Controller
 * 
 * @author neuedu
 * @date 2022-07-04
 */
@Api(tags = {"车辆调度单"})
@RestController
@RequestMapping("/transportApply/dispatchBill")
public class WmsDispatchBillController extends BaseController
{
    @Autowired
    private IWmsDispatchBillService wmsDispatchBillService;

    /**
     * 查询车辆调度单列表
     */
    @GetMapping("/list")
    @ApiOperation("查询车辆调度单列表")
    @DynamicResponseParameters(properties = {
	        @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = WmsDispatchBill.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list(WmsDispatchBill wmsDispatchBill)
    {
        startPage();
        List<WmsDispatchBill> list = wmsDispatchBillService.selectWmsDispatchBillList(wmsDispatchBill);
        return getDataTable(list);
    }

    /**
     * 导出车辆调度单列表
     */
    @ApiOperation("导出车辆调度单列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "文件名")
    })
    @PreAuthorize("@ss.hasPermi('transportApply:dispatchBill:export')")
    @Log(title = "车辆调度单", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(WmsDispatchBill wmsDispatchBill)
    {
        List<WmsDispatchBill> list = wmsDispatchBillService.selectWmsDispatchBillList(wmsDispatchBill);
        ExcelUtil<WmsDispatchBill> util = new ExcelUtil<WmsDispatchBill>(WmsDispatchBill.class);
        return util.exportExcel(list, "dispatchBill");
    }

    /**
     * 获取车辆调度单详细信息
     */
    @ApiOperation("获取车辆调度单详细信息")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "data", value = "返回业务数据", dataTypeClass = WmsDispatchBill.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(wmsDispatchBillService.selectWmsDispatchBillById(id));
    }

    /**
     * 新增车辆调度单
     */
    @ApiOperation("新增车辆调度单")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('transportApply:dispatchBill:add')")
    @Log(title = "车辆调度单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmsDispatchBill wmsDispatchBill)
    {
    	WmsDispatchBill cond = new WmsDispatchBill();
    	cond.setCarrierApplyId(wmsDispatchBill.getCarrierApplyId());
    	List<WmsDispatchBill> list = wmsDispatchBillService.selectWmsDispatchBillList(cond);
    	if(list!=null&&!list.isEmpty()) {
    		return AjaxResult.error("此承运单已调度车辆，无需再调度");
    	}
    	List<WmsDispatchBillDetail> detailList = wmsDispatchBill.getWmsDispatchBillDetailList();
    	if(detailList!=null&&!detailList.isEmpty()) {
    		//计算车牌号数量
    		Map<String,Long> plateNoCount = detailList.stream().collect(Collectors.groupingBy(WmsDispatchBillDetail::getPlateNo,Collectors.counting()));
    		//查找重复的车牌号
    		List<String> dupPlates = plateNoCount.keySet().stream().filter(plate->plateNoCount.get(plate)>1).collect(Collectors.toList());
    		if(dupPlates!=null&&!dupPlates.isEmpty()) {
    			return AjaxResult.error("车牌号："+dupPlates+"重复，不能提交");
    		}
    	}
        return toAjax(wmsDispatchBillService.insertWmsDispatchBill(wmsDispatchBill));
    }

    /**
     * 修改车辆调度单
     */
    @ApiOperation("修改车辆调度单")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('transportApply:dispatchBill:edit')")
    @Log(title = "车辆调度单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmsDispatchBill wmsDispatchBill)
    {
    	List<WmsDispatchBillDetail> detailList = wmsDispatchBill.getWmsDispatchBillDetailList();
    	if(detailList!=null&&!detailList.isEmpty()) {
    		//计算车牌号数量
    		Map<String,Long> plateNoCount = detailList.stream().collect(Collectors.groupingBy(WmsDispatchBillDetail::getPlateNo,Collectors.counting()));
    		//查找重复的车牌号
    		List<String> dupPlates = plateNoCount.keySet().stream().filter(plate->plateNoCount.get(plate)>1).collect(Collectors.toList());
    		if(dupPlates!=null&&!dupPlates.isEmpty()) {
    			return AjaxResult.error("车牌号："+dupPlates+"重复，不能提交");
    		}
    	}
        return toAjax(wmsDispatchBillService.updateWmsDispatchBill(wmsDispatchBill));
    }

    /**
     * 删除车辆调度单
     */
    @ApiOperation("删除车辆调度单")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('transportApply:dispatchBill:remove')")
    @Log(title = "车辆调度单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(wmsDispatchBillService.deleteWmsDispatchBillByIds(ids));
    }
}
