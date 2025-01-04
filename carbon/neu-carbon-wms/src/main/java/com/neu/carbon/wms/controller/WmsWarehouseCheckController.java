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
import com.neu.carbon.wms.domain.WmsMaterialInfo;
import com.neu.carbon.wms.domain.WmsWarehouse;
import com.neu.carbon.wms.domain.WmsWarehouseCheck;
import com.neu.carbon.wms.domain.WmsWarehouseLocation;
import com.neu.carbon.wms.domain.WmsWarehouseMaterialDetail;
import com.neu.carbon.wms.domain.WmsWarehouseRegion;
import com.neu.carbon.wms.service.IWmsMaterialInfoService;
import com.neu.carbon.wms.service.IWmsWarehouseCheckService;
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
 * 盘点信息Controller
 * 
 * @author neuedu
 * @date 2022-07-06
 */
@Api(tags = { "盘点信息" })
@RestController
@RequestMapping("/warehouse/warehouseCheck")
public class WmsWarehouseCheckController extends BaseController {
	@Autowired
	private IWmsWarehouseCheckService wmsWarehouseCheckService;
	@Autowired
	private IWmsWarehouseLocationService wmsWarehouseLocationService;
	@Autowired
	private IWmsWarehouseRegionService wmsWarehouseRegionService;
	@Autowired
	private IWmsWarehouseService wmsWarehouseService;
	@Autowired
	private IWmsMaterialInfoService wmsMaterialInfoService;
	@Autowired
	private IWmsWarehouseMaterialDetailService wmsWarehouseMaterialDetailService;

	/**
	 * 查询盘点信息列表
	 */
	@GetMapping("/list")
	@ApiOperation("查询盘点信息列表")
	@DynamicResponseParameters(properties = { @DynamicParameter(name = "total", value = "总记录数"),
			@DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
			@DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = WmsWarehouseCheck.class),
			@DynamicParameter(name = "msg", value = "返回消息内容") })
	public TableDataInfo list(WmsWarehouseCheck wmsWarehouseCheck) {
		startPage();
		List<WmsWarehouseCheck> list = wmsWarehouseCheckService.selectWmsWarehouseCheckList(wmsWarehouseCheck);
		list.stream().forEach(check -> {
			WmsWarehouse wh = wmsWarehouseService.selectWmsWarehouseById(check.getWarehouseId());
			if (wh != null) {
				check.setWarehouseName(wh.getName());
			}
			WmsWarehouseLocation location = wmsWarehouseLocationService
					.selectWmsWarehouseLocationById(check.getWhLocationId());
			if (location != null) {
				check.setWhLocationName(location.getName());
			}
			WmsWarehouseRegion region = wmsWarehouseRegionService.selectWmsWarehouseRegionById(check.getWhRegionId());
			if (region != null) {
				check.setWhRegionName(region.getName());
			}
			WmsMaterialInfo material = wmsMaterialInfoService.selectWmsMaterialInfoById(check.getMaterialId());
			if (material != null) {
				check.setMaterialName(material.getName());
			}
		});
		return getDataTable(list);
	}

	/**
	 * 导出盘点信息列表
	 */
	@ApiOperation("导出盘点信息列表")
	@DynamicResponseParameters(properties = { @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
			@DynamicParameter(name = "msg", value = "文件名") })
	@PreAuthorize("@ss.hasPermi('warehouse:warehouseCheck:export')")
	@Log(title = "盘点信息", businessType = BusinessType.EXPORT)
	@GetMapping("/export")
	public AjaxResult export(WmsWarehouseCheck wmsWarehouseCheck) {
		List<WmsWarehouseCheck> list = wmsWarehouseCheckService.selectWmsWarehouseCheckList(wmsWarehouseCheck);
		list.stream().forEach(check -> {
			WmsWarehouse wh = wmsWarehouseService.selectWmsWarehouseById(check.getWarehouseId());
			if (wh != null) {
				check.setWarehouseName(wh.getName());
			}
			WmsWarehouseLocation location = wmsWarehouseLocationService
					.selectWmsWarehouseLocationById(check.getWhLocationId());
			if (location != null) {
				check.setWhLocationName(location.getName());
			}
			WmsWarehouseRegion region = wmsWarehouseRegionService.selectWmsWarehouseRegionById(check.getWhRegionId());
			if (region != null) {
				check.setWhRegionName(region.getName());
			}
			WmsMaterialInfo material = wmsMaterialInfoService.selectWmsMaterialInfoById(check.getMaterialId());
			if (material != null) {
				check.setMaterialName(material.getName());
			}
		});
		ExcelUtil<WmsWarehouseCheck> util = new ExcelUtil<WmsWarehouseCheck>(WmsWarehouseCheck.class);
		return util.exportExcel(list, "warehouseCheck");
	}

	/**
	 * 获取盘点信息详细信息
	 */
	@ApiOperation("获取盘点信息详细信息")
	@DynamicResponseParameters(properties = { @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
			@DynamicParameter(name = "data", value = "返回业务数据", dataTypeClass = WmsWarehouseCheck.class),
			@DynamicParameter(name = "msg", value = "返回消息内容") })
	@GetMapping(value = "/{id}")
	public AjaxResult getInfo(@PathVariable("id") Long id) {
		WmsWarehouseCheck check = wmsWarehouseCheckService.selectWmsWarehouseCheckById(id);
		WmsMaterialInfo material = wmsMaterialInfoService.selectWmsMaterialInfoById(check.getMaterialId());
		if (material != null) {
			check.setMaterialName(material.getName());
		}
		return AjaxResult.success(check);
	}

	/**
	 * 新增盘点信息
	 */
	@ApiOperation("新增盘点信息")
	@DynamicResponseParameters(properties = { @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
			@DynamicParameter(name = "msg", value = "返回消息内容") })
	@PreAuthorize("@ss.hasPermi('warehouse:warehouseCheck:add')")
	@Log(title = "盘点信息", businessType = BusinessType.INSERT)
	@PostMapping
	public AjaxResult add(@RequestBody WmsWarehouseCheck wmsWarehouseCheck) {
		WmsWarehouseMaterialDetail whMtDetail = new WmsWarehouseMaterialDetail();
		whMtDetail.setMaterialId(wmsWarehouseCheck.getMaterialId());
		whMtDetail.setWhId(wmsWarehouseCheck.getWarehouseId());
		whMtDetail.setWhRegionId(wmsWarehouseCheck.getWhRegionId());
		whMtDetail.setWhLocationId(wmsWarehouseCheck.getWhLocationId());
		whMtDetail.setBatchNo(wmsWarehouseCheck.getBatchNo());
		List<WmsWarehouseMaterialDetail> list = wmsWarehouseMaterialDetailService
				.selectWmsWarehouseMaterialDetailList(whMtDetail);
		if (list == null || list.isEmpty()) {
			return AjaxResult.error("在当前仓库的库位未找到物料：[" + wmsWarehouseCheck.getMaterialName() + "]");
		}
		calcCheckType(wmsWarehouseCheck);
		return toAjax(wmsWarehouseCheckService.insertWmsWarehouseCheck(wmsWarehouseCheck));
	}

	private void calcCheckType(WmsWarehouseCheck wmsWarehouseCheck) {
		double inventory = wmsWarehouseCheck.getInventory() == null ? 0 : wmsWarehouseCheck.getInventory();
		double actInventory = wmsWarehouseCheck.getActualInventory() == null ? 0
				: wmsWarehouseCheck.getActualInventory();
		if (actInventory > inventory) {
			wmsWarehouseCheck.setCheckType("1");
		}
		if (actInventory < inventory) {
			wmsWarehouseCheck.setCheckType("0");
		}
	}

	/**
	 * 修改盘点信息
	 */
	@ApiOperation("修改盘点信息")
	@DynamicResponseParameters(properties = { @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
			@DynamicParameter(name = "msg", value = "返回消息内容") })
	@PreAuthorize("@ss.hasPermi('warehouse:warehouseCheck:edit')")
	@Log(title = "盘点信息", businessType = BusinessType.UPDATE)
	@PutMapping
	public AjaxResult edit(@RequestBody WmsWarehouseCheck wmsWarehouseCheck) {
		WmsWarehouseMaterialDetail whMtDetail = new WmsWarehouseMaterialDetail();
		whMtDetail.setMaterialId(wmsWarehouseCheck.getMaterialId());
		whMtDetail.setWhId(wmsWarehouseCheck.getWarehouseId());
		whMtDetail.setWhRegionId(wmsWarehouseCheck.getWhRegionId());
		whMtDetail.setWhLocationId(wmsWarehouseCheck.getWhLocationId());
		whMtDetail.setBatchNo(wmsWarehouseCheck.getBatchNo());
		List<WmsWarehouseMaterialDetail> list = wmsWarehouseMaterialDetailService
				.selectWmsWarehouseMaterialDetailList(whMtDetail);
		if (list == null || list.isEmpty()) {
			return AjaxResult.error("在当前仓库的库位未找到物料：[" + wmsWarehouseCheck.getMaterialName() + "]");
		}
		calcCheckType(wmsWarehouseCheck);
		return toAjax(wmsWarehouseCheckService.updateWmsWarehouseCheck(wmsWarehouseCheck));
	}

	/**
	 * 删除盘点信息
	 */
	@ApiOperation("删除盘点信息")
	@DynamicResponseParameters(properties = { @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
			@DynamicParameter(name = "msg", value = "返回消息内容") })
	@PreAuthorize("@ss.hasPermi('warehouse:warehouseCheck:remove')")
	@Log(title = "盘点信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
	public AjaxResult remove(@PathVariable Long[] ids) {
		return toAjax(wmsWarehouseCheckService.deleteWmsWarehouseCheckByIds(ids));
	}
}
