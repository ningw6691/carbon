package com.neu.carbon.wms.controller;

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
import com.neu.carbon.wms.domain.WmsWarehouse;
import com.neu.carbon.wms.domain.WmsWarehouseLocation;
import com.neu.carbon.wms.domain.WmsWarehouseRegion;
import com.neu.carbon.wms.service.IWmsWarehouseLocationService;
import com.neu.carbon.wms.service.IWmsWarehouseRegionService;
import com.neu.carbon.wms.service.IWmsWarehouseService;
import com.neu.common.annotation.Log;
import com.neu.common.core.controller.BaseController;
import com.neu.common.core.domain.AjaxResult;
import com.neu.common.core.page.TableDataInfo;
import com.neu.common.enums.BusinessType;
import com.neu.common.utils.poi.ExcelUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 库位管理Controller
 * 
 * @author neusoft
 * @date 2022-06-24
 */
@Api(tags = {"库位管理"})
@RestController
@RequestMapping("/warehouse/warehouseLocation")
public class WmsWarehouseLocationController extends BaseController
{
    @Autowired
    private IWmsWarehouseLocationService wmsWarehouseLocationService;
    @Autowired
    private IWmsWarehouseRegionService wmsWarehouseRegionService;
    @Autowired
    private IWmsWarehouseService wmsWarehouseService;

    /**
     * 查询库位管理列表
     */
    @GetMapping("/list")
    @ApiOperation("查询库位管理列表")
    @DynamicResponseParameters(properties = {
	        @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = WmsWarehouseLocation.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list(WmsWarehouseLocation wmsWarehouseLocation)
    {
        startPage();
        List<WmsWarehouseLocation> list = wmsWarehouseLocationService.selectWmsWarehouseLocationList(wmsWarehouseLocation);
        list.stream().forEach(location->{
        	Long regionId = location.getRegionId();
        	//获取库区
        	WmsWarehouseRegion region = wmsWarehouseRegionService.selectWmsWarehouseRegionById(regionId);
        	//获取仓库
        	WmsWarehouse warehouse = wmsWarehouseService.selectWmsWarehouseById(region.getWarehouseId());
        	location.setWarehouseName(warehouse.getName());
        	location.setRegionName(region.getName());
        });
        return getDataTable(list);
    }

    /**
     * 导出库位管理列表
     */
    @ApiOperation("导出库位管理列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "文件名")
    })
    @PreAuthorize("@ss.hasPermi('warehouse:warehouseLocation:export')")
    @Log(title = "库位管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(WmsWarehouseLocation wmsWarehouseLocation)
    {
        List<WmsWarehouseLocation> list = wmsWarehouseLocationService.selectWmsWarehouseLocationList(wmsWarehouseLocation);
        ExcelUtil<WmsWarehouseLocation> util = new ExcelUtil<WmsWarehouseLocation>(WmsWarehouseLocation.class);
        return util.exportExcel(list, "warehouseLocation");
    }

    /**
     * 获取库位管理详细信息
     */
    @ApiOperation("获取库位管理详细信息")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "data", value = "返回业务数据", dataTypeClass = WmsWarehouseLocation.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('warehouse:warehouseLocation:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(wmsWarehouseLocationService.selectWmsWarehouseLocationById(id));
    }

    /**
     * 新增库位管理
     */
    @ApiOperation("新增库位管理")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('warehouse:warehouseLocation:add')")
    @Log(title = "库位管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmsWarehouseLocation wmsWarehouseLocation)
    {
        return toAjax(wmsWarehouseLocationService.insertWmsWarehouseLocation(wmsWarehouseLocation));
    }

    /**
     * 修改库位管理
     */
    @ApiOperation("修改库位管理")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('warehouse:warehouseLocation:edit')")
    @Log(title = "库位管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmsWarehouseLocation wmsWarehouseLocation)
    {
        return toAjax(wmsWarehouseLocationService.updateWmsWarehouseLocation(wmsWarehouseLocation));
    }

    /**
     * 删除库位管理
     */
    @ApiOperation("删除库位管理")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('warehouse:warehouseLocation:remove')")
    @Log(title = "库位管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(wmsWarehouseLocationService.deleteWmsWarehouseLocationByIds(ids));
    }
}