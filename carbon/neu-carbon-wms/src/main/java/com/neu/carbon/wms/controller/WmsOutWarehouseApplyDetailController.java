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
import com.neu.carbon.wms.domain.WmsOutWarehouseApplyDetail;
import com.neu.carbon.wms.service.IWmsOutWarehouseApplyDetailService;
import com.neu.common.utils.poi.ExcelUtil;
import com.neu.common.core.page.TableDataInfo;

/**
 * 出库申请明细Controller
 * 
 * @author neuedu
 * @date 2022-06-29
 */
@Api(tags = {"出库申请明细"})
@RestController
@RequestMapping("/wmsApply/outWarehouseApplyDetail")
public class WmsOutWarehouseApplyDetailController extends BaseController
{
    @Autowired
    private IWmsOutWarehouseApplyDetailService wmsOutWarehouseApplyDetailService;

    /**
     * 查询出库申请明细列表
     */
    @GetMapping("/list")
    @ApiOperation("查询出库申请明细列表")
    @DynamicResponseParameters(properties = {
	        @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = WmsOutWarehouseApplyDetail.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list(WmsOutWarehouseApplyDetail wmsOutWarehouseApplyDetail)
    {
        startPage();
        List<WmsOutWarehouseApplyDetail> list = wmsOutWarehouseApplyDetailService.selectWmsOutWarehouseApplyDetailList(wmsOutWarehouseApplyDetail);
        return getDataTable(list);
    }

    /**
     * 导出出库申请明细列表
     */
    @ApiOperation("导出出库申请明细列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "文件名")
    })
    @PreAuthorize("@ss.hasPermi('wmsApply:outWarehouseApplyDetail:export')")
    @Log(title = "出库申请明细", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(WmsOutWarehouseApplyDetail wmsOutWarehouseApplyDetail)
    {
        List<WmsOutWarehouseApplyDetail> list = wmsOutWarehouseApplyDetailService.selectWmsOutWarehouseApplyDetailList(wmsOutWarehouseApplyDetail);
        ExcelUtil<WmsOutWarehouseApplyDetail> util = new ExcelUtil<WmsOutWarehouseApplyDetail>(WmsOutWarehouseApplyDetail.class);
        return util.exportExcel(list, "outWarehouseApplyDetail");
    }

    /**
     * 获取出库申请明细详细信息
     */
    @ApiOperation("获取出库申请明细详细信息")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "data", value = "返回业务数据", dataTypeClass = WmsOutWarehouseApplyDetail.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(wmsOutWarehouseApplyDetailService.selectWmsOutWarehouseApplyDetailById(id));
    }

    /**
     * 新增出库申请明细
     */
    @ApiOperation("新增出库申请明细")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('wmsApply:outWarehouseApplyDetail:add')")
    @Log(title = "出库申请明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmsOutWarehouseApplyDetail wmsOutWarehouseApplyDetail)
    {
        return toAjax(wmsOutWarehouseApplyDetailService.insertWmsOutWarehouseApplyDetail(wmsOutWarehouseApplyDetail));
    }

    /**
     * 修改出库申请明细
     */
    @ApiOperation("修改出库申请明细")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('wmsApply:outWarehouseApplyDetail:edit')")
    @Log(title = "出库申请明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmsOutWarehouseApplyDetail wmsOutWarehouseApplyDetail)
    {
        return toAjax(wmsOutWarehouseApplyDetailService.updateWmsOutWarehouseApplyDetail(wmsOutWarehouseApplyDetail));
    }

    /**
     * 删除出库申请明细
     */
    @ApiOperation("删除出库申请明细")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('wmsApply:outWarehouseApplyDetail:remove')")
    @Log(title = "出库申请明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(wmsOutWarehouseApplyDetailService.deleteWmsOutWarehouseApplyDetailByIds(ids));
    }
}
