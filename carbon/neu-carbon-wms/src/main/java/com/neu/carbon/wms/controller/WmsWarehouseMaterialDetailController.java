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
import com.neu.carbon.wms.domain.WmsWarehouseMaterialDetail;
import com.neu.carbon.wms.domain.WmsWarehouseRegion;
import com.neu.carbon.wms.service.IWmsWarehouseLocationService;
import com.neu.carbon.wms.service.IWmsWarehouseMaterialDetailService;
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
 * 仓库物料明细Controller
 * 
 * @author neusoft
 * @date 2022-06-24
 */
@Api(tags = {"仓库物料明细"})
@RestController
@RequestMapping("/material/materialInventoryDetail")
public class WmsWarehouseMaterialDetailController extends BaseController
{
    @Autowired
    private IWmsWarehouseMaterialDetailService wmsWarehouseMaterialDetailService;
    @Autowired
    private IWmsWarehouseLocationService wmsWarehouseLocationService;
    @Autowired
    private IWmsWarehouseRegionService wmsWarehouseRegionService;
    @Autowired
    private IWmsWarehouseService wmsWarehouseService;

    /**
     * 查询仓库物料明细列表
     */
    @GetMapping("/list")
    @ApiOperation("查询仓库物料明细列表")
    @DynamicResponseParameters(properties = {
	        @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = WmsWarehouseMaterialDetail.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list(WmsWarehouseMaterialDetail wmsWarehouseMaterialDetail)
    {
        startPage();
        List<WmsWarehouseMaterialDetail> list = wmsWarehouseMaterialDetailService.selectWmsWarehouseMaterialDetailList(wmsWarehouseMaterialDetail);
        list.stream().forEach(material->{
        	WmsWarehouse wh = wmsWarehouseService.selectWmsWarehouseById(material.getWarehouseId());
        	WmsWarehouseRegion whRegion = wmsWarehouseRegionService.selectWmsWarehouseRegionById(material.getWhRegionId());
        	WmsWarehouseLocation whLocation = wmsWarehouseLocationService.selectWmsWarehouseLocationById(material.getWhLocationId());
        	if(wh!=null) {
        		material.setWarehouseName(wh.getName());
        	}
        	if(whRegion!=null) {
        		material.setWhRegionName(whRegion.getName());
        	}
    		if(whLocation!=null) {
    			material.setWhLocationName(whLocation.getName());
    		}
        });
        return getDataTable(list);
    }

    /**
     * 导出仓库物料明细列表
     */
    @ApiOperation("导出仓库物料明细列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "文件名")
    })
    @PreAuthorize("@ss.hasPermi('material:materialInventoryDetail:export')")
    @Log(title = "仓库物料明细", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(WmsWarehouseMaterialDetail wmsWarehouseMaterialDetail)
    {
        List<WmsWarehouseMaterialDetail> list = wmsWarehouseMaterialDetailService.selectWmsWarehouseMaterialDetailList(wmsWarehouseMaterialDetail);
        ExcelUtil<WmsWarehouseMaterialDetail> util = new ExcelUtil<WmsWarehouseMaterialDetail>(WmsWarehouseMaterialDetail.class);
        return util.exportExcel(list, "materialInventoryDetail");
    }

    /**
     * 获取仓库物料明细详细信息
     */
    @ApiOperation("获取仓库物料明细详细信息")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "data", value = "返回业务数据", dataTypeClass = WmsWarehouseMaterialDetail.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(wmsWarehouseMaterialDetailService.selectWmsWarehouseMaterialDetailById(id));
    }

    /**
     * 新增仓库物料明细
     */
    @ApiOperation("新增仓库物料明细")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('material:materialInventoryDetail:add')")
    @Log(title = "仓库物料明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmsWarehouseMaterialDetail wmsWarehouseMaterialDetail)
    {
        return toAjax(wmsWarehouseMaterialDetailService.insertWmsWarehouseMaterialDetail(wmsWarehouseMaterialDetail));
    }

    /**
     * 修改仓库物料明细
     */
    @ApiOperation("修改仓库物料明细")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('material:materialInventoryDetail:edit')")
    @Log(title = "仓库物料明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmsWarehouseMaterialDetail wmsWarehouseMaterialDetail)
    {
        return toAjax(wmsWarehouseMaterialDetailService.updateWmsWarehouseMaterialDetail(wmsWarehouseMaterialDetail));
    }

    /**
     * 删除仓库物料明细
     */
    @ApiOperation("删除仓库物料明细")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('material:materialInventoryDetail:remove')")
    @Log(title = "仓库物料明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(wmsWarehouseMaterialDetailService.deleteWmsWarehouseMaterialDetailByIds(ids));
    }
}
