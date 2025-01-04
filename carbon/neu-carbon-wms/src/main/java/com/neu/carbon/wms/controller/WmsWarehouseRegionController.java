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
 * 库区管理Controller
 * 
 * @author neusoft
 * @date 2022-06-24
 */
@Api(tags = {"库区管理"})
@RestController
@RequestMapping("/warehouse/warehouseRegion")
public class WmsWarehouseRegionController extends BaseController
{
    @Autowired
    private IWmsWarehouseRegionService wmsWarehouseRegionService;
    @Autowired
    private IWmsWarehouseService wmsWarehouseService;
    @Autowired
    private IWmsWarehouseLocationService wmsWarehouseLocationService;

    /**
     * 查询库区管理列表
     */
    @ApiOperation("查询库区管理列表")
    @DynamicResponseParameters(properties = {
	        @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = WmsWarehouseRegion.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @GetMapping("/list")
    public TableDataInfo list(WmsWarehouseRegion wmsWarehouseRegion)
    {
        startPage();
        List<WmsWarehouseRegion> list = wmsWarehouseRegionService.selectWmsWarehouseRegionList(wmsWarehouseRegion);
        list.stream().forEach(region->{
        	Long whId = region.getWarehouseId();
        	WmsWarehouse wh = wmsWarehouseService.selectWmsWarehouseById(whId);
        	region.setWarehouseName(wh.getName());
        });
        return getDataTable(list);
    }

    /**
     * 导出库区管理列表
     */
    @ApiOperation("导出库区管理列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "文件名")
    })
    @PreAuthorize("@ss.hasPermi('warehouse:warehouseRegion:export')")
    @Log(title = "库区管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(WmsWarehouseRegion wmsWarehouseRegion)
    {
        List<WmsWarehouseRegion> list = wmsWarehouseRegionService.selectWmsWarehouseRegionList(wmsWarehouseRegion);
        ExcelUtil<WmsWarehouseRegion> util = new ExcelUtil<WmsWarehouseRegion>(WmsWarehouseRegion.class);
        return util.exportExcel(list, "warehouseRegion");
    }

    /**
     * 获取库区管理详细信息
     */
    @ApiOperation("获取库区管理详细信息")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "data", value = "返回业务数据", dataTypeClass = WmsWarehouseRegion.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('warehouse:warehouseRegion:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(wmsWarehouseRegionService.selectWmsWarehouseRegionById(id));
    }

    /**
     * 新增库区管理
     */
    @ApiOperation("新增库区管理")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('warehouse:warehouseRegion:add')")
    @Log(title = "库区管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmsWarehouseRegion wmsWarehouseRegion)
    {
        return toAjax(wmsWarehouseRegionService.insertWmsWarehouseRegion(wmsWarehouseRegion));
    }

    /**
     * 修改库区管理
     */
    @ApiOperation("修改库区管理")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('warehouse:warehouseRegion:edit')")
    @Log(title = "库区管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmsWarehouseRegion wmsWarehouseRegion)
    {
        return toAjax(wmsWarehouseRegionService.updateWmsWarehouseRegion(wmsWarehouseRegion));
    }

    /**
     * 删除库区管理
     */
    @ApiOperation("删除库区管理")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('warehouse:warehouseRegion:remove')")
    @Log(title = "库区管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
    	for(Long id:ids) {
    		WmsWarehouseLocation location = new WmsWarehouseLocation();
    		location.setRegionId(id);
    		List<WmsWarehouseLocation> locationList = wmsWarehouseLocationService.selectWmsWarehouseLocationList(location);
    		if(locationList!=null&&!locationList.isEmpty()) {
    			return AjaxResult.error("编号"+id+"库区中有库位不能删除");
			}
		}
		return toAjax(wmsWarehouseRegionService.deleteWmsWarehouseRegionByIds(ids));
	}
}
