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
import com.neu.carbon.wms.domain.WmsWarehouseRegion;
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
 * 仓库管理Controller
 * 
 * @author neusoft
 * @date 2022-06-24
 */
@Api(tags = {"仓库管理"})
@RestController
@RequestMapping("/warehouse/warehouseInfo")
public class WmsWarehouseController extends BaseController
{
    @Autowired
    private IWmsWarehouseService wmsWarehouseService;
    @Autowired
    private IWmsWarehouseRegionService wmsWarehouseRegionService;

    /**
     * 查询仓库管理列表
     */
    @PreAuthorize("@ss.hasPermi('warehouse:warehouseInfo:list')")
    @GetMapping("/list")
    @ApiOperation("查询仓库管理列表")
    @DynamicResponseParameters(properties = {
	        @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = WmsWarehouse.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list(WmsWarehouse wmsWarehouse)
    {
        startPage();
        List<WmsWarehouse> list = wmsWarehouseService.selectWmsWarehouseList(wmsWarehouse);
        return getDataTable(list);
    }

    /**
     * 导出仓库管理列表
     */
    @ApiOperation("导出仓库管理列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "文件名")
    })
    @PreAuthorize("@ss.hasPermi('warehouse:warehouseInfo:export')")
    @Log(title = "仓库管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(WmsWarehouse wmsWarehouse)
    {
        List<WmsWarehouse> list = wmsWarehouseService.selectWmsWarehouseList(wmsWarehouse);
        ExcelUtil<WmsWarehouse> util = new ExcelUtil<WmsWarehouse>(WmsWarehouse.class);
        return util.exportExcel(list, "warehouseInfo");
    }

    /**
     * 获取仓库管理详细信息
     */
    @ApiOperation("获取仓库管理详细信息")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "data", value = "返回业务数据", dataTypeClass = WmsWarehouse.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('warehouse:warehouseInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(wmsWarehouseService.selectWmsWarehouseById(id));
    }

    /**
     * 新增仓库管理
     */
    @ApiOperation("新增仓库管理")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('warehouse:warehouseInfo:add')")
    @Log(title = "仓库管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmsWarehouse wmsWarehouse)
    {
        return toAjax(wmsWarehouseService.insertWmsWarehouse(wmsWarehouse));
    }

    /**
     * 修改仓库管理
     */
    @ApiOperation("修改仓库管理")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('warehouse:warehouseInfo:edit')")
    @Log(title = "仓库管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmsWarehouse wmsWarehouse)
    {
        return toAjax(wmsWarehouseService.updateWmsWarehouse(wmsWarehouse));
    }

    /**
     * 删除仓库管理
     */
    @ApiOperation("删除仓库管理")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('warehouse:warehouseInfo:remove')")
    @Log(title = "仓库管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
    	for(Long id:ids) {
    		WmsWarehouseRegion region = new WmsWarehouseRegion();
    		region.setWarehouseId(id);
    		List<WmsWarehouseRegion> regionList = wmsWarehouseRegionService.selectWmsWarehouseRegionList(region);
    		if(regionList!=null&&!regionList.isEmpty()) {
    			return AjaxResult.error("编号"+id+"仓库中有库区不能删除");
			}
		}
        return toAjax(wmsWarehouseService.deleteWmsWarehouseByIds(ids));
    }
}