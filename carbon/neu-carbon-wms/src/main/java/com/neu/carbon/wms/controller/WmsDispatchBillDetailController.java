package com.neu.carbon.wms.controller;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicResponseParameters;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.neu.common.annotation.Log;
import com.neu.common.core.controller.BaseController;
import com.neu.common.core.domain.AjaxResult;
import com.neu.common.enums.BusinessType;
import com.neu.carbon.wms.domain.WmsDispatchBillDetail;
import com.neu.carbon.wms.service.IWmsDispatchBillDetailService;
import com.neu.common.utils.poi.ExcelUtil;
import com.neu.common.core.page.TableDataInfo;

/**
 * 车辆调度单明细Controller
 * 
 * @author neuedu
 * @date 2022-07-04
 */
@Api(tags = {"车辆调度单明细"})
@RestController
@RequestMapping("/transportApply/dispatchBillDetail")
public class WmsDispatchBillDetailController extends BaseController
{
    @Autowired
    private IWmsDispatchBillDetailService wmsDispatchBillDetailService;

    /**
     * 查询车辆调度单明细列表
     */
    @GetMapping("/list")
    @ApiOperation("查询车辆调度单明细列表")
    @DynamicResponseParameters(properties = {
	        @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = WmsDispatchBillDetail.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list(WmsDispatchBillDetail wmsDispatchBillDetail)
    {
        startPage();
        List<WmsDispatchBillDetail> list = wmsDispatchBillDetailService.selectWmsDispatchBillDetailList(wmsDispatchBillDetail);
        return getDataTable(list);
    }

    /**
     * 导出车辆调度单明细列表
     */
    @ApiOperation("导出车辆调度单明细列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "文件名")
    })
    @PreAuthorize("@ss.hasPermi('transportApply:dispatchBillDetail:export')")
    @Log(title = "车辆调度单明细", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(WmsDispatchBillDetail wmsDispatchBillDetail)
    {
        List<WmsDispatchBillDetail> list = wmsDispatchBillDetailService.selectWmsDispatchBillDetailList(wmsDispatchBillDetail);
        ExcelUtil<WmsDispatchBillDetail> util = new ExcelUtil<WmsDispatchBillDetail>(WmsDispatchBillDetail.class);
        return util.exportExcel(list, "dispatchBillDetail");
    }

    /**
     * 获取车辆调度单明细详细信息
     */
    @ApiOperation("获取车辆调度单明细详细信息")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "data", value = "返回业务数据", dataTypeClass = WmsDispatchBillDetail.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(wmsDispatchBillDetailService.selectWmsDispatchBillDetailById(id));
    }

    /**
     * 新增车辆调度单明细
     */
    @ApiOperation("新增车辆调度单明细")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('transportApply:dispatchBillDetail:add')")
    @Log(title = "车辆调度单明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmsDispatchBillDetail wmsDispatchBillDetail)
    {
        return toAjax(wmsDispatchBillDetailService.insertWmsDispatchBillDetail(wmsDispatchBillDetail));
    }

    /**
     * 修改车辆调度单明细
     */
    @ApiOperation("修改车辆调度单明细")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('transportApply:dispatchBillDetail:edit')")
    @Log(title = "车辆调度单明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmsDispatchBillDetail wmsDispatchBillDetail)
    {
        return toAjax(wmsDispatchBillDetailService.updateWmsDispatchBillDetail(wmsDispatchBillDetail));
    }

    /**
     * 删除车辆调度单明细
     */
    @ApiOperation("删除车辆调度单明细")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('transportApply:dispatchBillDetail:remove')")
    @Log(title = "车辆调度单明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(wmsDispatchBillDetailService.deleteWmsDispatchBillDetailByIds(ids));
    }
}
