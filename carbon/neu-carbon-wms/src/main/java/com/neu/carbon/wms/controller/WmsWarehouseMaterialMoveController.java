package com.neu.carbon.wms.controller;

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
import com.neu.carbon.wms.domain.WmsMaterialInfo;
import com.neu.carbon.wms.domain.WmsWarehouse;
import com.neu.carbon.wms.domain.WmsWarehouseLocation;
import com.neu.carbon.wms.domain.WmsWarehouseMaterialDetail;
import com.neu.carbon.wms.domain.WmsWarehouseMaterialMove;
import com.neu.carbon.wms.domain.WmsWarehouseRegion;
import com.neu.carbon.wms.service.IWmsMaterialInfoService;
import com.neu.carbon.wms.service.IWmsWarehouseLocationService;
import com.neu.carbon.wms.service.IWmsWarehouseMaterialDetailService;
import com.neu.carbon.wms.service.IWmsWarehouseMaterialMoveService;
import com.neu.carbon.wms.service.IWmsWarehouseRegionService;
import com.neu.carbon.wms.service.IWmsWarehouseService;
import com.neu.common.annotation.Log;
import com.neu.common.core.controller.BaseController;
import com.neu.common.core.domain.AjaxResult;
import com.neu.common.core.page.TableDataInfo;
import com.neu.common.enums.BusinessType;
import com.neu.common.utils.DateUtils;
import com.neu.common.utils.StringUtils;
import com.neu.common.utils.poi.ExcelUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 移库信息Controller
 * 
 * @author neuedu
 * @date 2022-07-05
 */
@Api(tags = { "移库信息" })
@RestController
@RequestMapping("/warehouse/warehouseMove")
public class WmsWarehouseMaterialMoveController extends BaseController {
	@Autowired
	private IWmsWarehouseMaterialMoveService wmsWarehouseMaterialMoveService;
	@Autowired
	private IWmsWarehouseMaterialDetailService wmsWarehouseMaterialDetailService;
	@Autowired
	private IWmsWarehouseLocationService wmsWarehouseLocationService;
	@Autowired
	private IWmsWarehouseRegionService wmsWarehouseRegionService;
	@Autowired
	private IWmsWarehouseService wmsWarehouseService;
	@Autowired
	private IWmsMaterialInfoService wmsMaterialInfoService;

	/**
	 * 查询移库信息列表
	 */
	@GetMapping("/list")
	@ApiOperation("查询移库信息列表")
	@DynamicResponseParameters(properties = { @DynamicParameter(name = "total", value = "总记录数"),
			@DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
			@DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = WmsWarehouseMaterialMove.class),
			@DynamicParameter(name = "msg", value = "返回消息内容") })
	public TableDataInfo list(WmsWarehouseMaterialMove wmsWarehouseMaterialMove) {
		startPage();
		List<WmsWarehouseMaterialMove> list = wmsWarehouseMaterialMoveService
				.selectWmsWarehouseMaterialMoveList(wmsWarehouseMaterialMove);
		list.stream().forEach(move -> {
			WmsWarehouse targetWh = wmsWarehouseService.selectWmsWarehouseById(move.getTargetWarehouseId());
			if (targetWh != null) {
				move.setTargetWarehouseName(targetWh.getName());
			}
			WmsWarehouseLocation targetLocation = wmsWarehouseLocationService
					.selectWmsWarehouseLocationById(move.getTargetLocationId());
			if (targetLocation != null) {
				move.setTargetLocationName(targetLocation.getName());
			}

			WmsWarehouse sourceWh = wmsWarehouseService.selectWmsWarehouseById(move.getSourceWarehouseId());
			if (sourceWh != null) {
				move.setSourceWarehouseName(sourceWh.getName());
			}
			WmsWarehouseLocation sourceLocation = wmsWarehouseLocationService
					.selectWmsWarehouseLocationById(move.getSourceLocationId());
			if (sourceLocation != null) {
				move.setSourceLocationName(sourceLocation.getName());
			}
			WmsWarehouseRegion sourceRegion = wmsWarehouseRegionService
					.selectWmsWarehouseRegionById(move.getSourceRegionId());
			if (sourceRegion != null) {
				move.setSourceRegionName(sourceRegion.getName());
			}
			WmsMaterialInfo material = wmsMaterialInfoService.selectWmsMaterialInfoById(move.getMaterialId());
			if(material!=null) {
				move.setMaterialName(material.getName());
			}
		});
		return getDataTable(list);
	}

	/**
	 * 导出移库信息列表
	 */
	@ApiOperation("导出移库信息列表")
	@DynamicResponseParameters(properties = { @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
			@DynamicParameter(name = "msg", value = "文件名") })
	@PreAuthorize("@ss.hasPermi('warehouse:warehouseMove:export')")
	@Log(title = "移库信息", businessType = BusinessType.EXPORT)
	@GetMapping("/export")
	public AjaxResult export(WmsWarehouseMaterialMove wmsWarehouseMaterialMove) {
		List<WmsWarehouseMaterialMove> list = wmsWarehouseMaterialMoveService
				.selectWmsWarehouseMaterialMoveList(wmsWarehouseMaterialMove);
		ExcelUtil<WmsWarehouseMaterialMove> util = new ExcelUtil<WmsWarehouseMaterialMove>(
				WmsWarehouseMaterialMove.class);
		return util.exportExcel(list, "warehouseMove");
	}

	/**
	 * 获取移库信息详细信息
	 */
	@ApiOperation("获取移库信息详细信息")
	@DynamicResponseParameters(properties = { @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
			@DynamicParameter(name = "data", value = "返回业务数据", dataTypeClass = WmsWarehouseMaterialMove.class),
			@DynamicParameter(name = "msg", value = "返回消息内容") })
	@GetMapping(value = "/{id}")
	public AjaxResult getInfo(@PathVariable("id") Long id) {
		return AjaxResult.success(wmsWarehouseMaterialMoveService.selectWmsWarehouseMaterialMoveById(id));
	}

	/**
	 * 新增移库信息
	 */
	@ApiOperation("新增移库信息")
	@DynamicResponseParameters(properties = { @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
			@DynamicParameter(name = "msg", value = "返回消息内容") })
	@PreAuthorize("@ss.hasPermi('warehouse:warehouseMove:add')")
	@Log(title = "移库信息", businessType = BusinessType.INSERT)
	@PostMapping
	public AjaxResult add(@RequestBody WmsWarehouseMaterialMove wmsWarehouseMaterialMove) {
		return toAjax(wmsWarehouseMaterialMoveService.insertWmsWarehouseMaterialMove(wmsWarehouseMaterialMove));
	}

	/**
	 * 执行移库
	 */
	@ApiOperation("执行移库")
	@DynamicResponseParameters(properties = { @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
			@DynamicParameter(name = "msg", value = "返回消息内容") })
	@PreAuthorize("@ss.hasPermi('warehouse:warehouseMove:add')")
	@Log(title = "移库信息", businessType = BusinessType.INSERT)
	@PostMapping("/move")
	public AjaxResult move(@RequestBody List<WmsWarehouseMaterialMove> wmsWarehouseMaterialMoveList) {
		List<WmsWarehouseMaterialMove> moveList = new ArrayList<>();
		final StringBuilder msg = new StringBuilder();
		// 检查数据合法性
		wmsWarehouseMaterialMoveList.stream().filter(move -> {
			// 过滤掉不合法数据
			if (move.getMoveQuantity() != null && move.getTargetWarehouseId() != null
					&& move.getTargetRegionId() != null && move.getTargetLocationId() != null) {
				// 源库位和目标库位相同
				if (move.getSourceLocationId().longValue() == move.getTargetLocationId().longValue()) {
					return false;
				}
				WmsWarehouseLocation location = wmsWarehouseLocationService
						.selectWmsWarehouseLocationById(move.getTargetLocationId());
				// 库位和库区不合法
				if (move.getTargetRegionId().longValue() != location.getRegionId().longValue()) {
					return false;
				}
				// 库区和仓库不合法
				WmsWarehouseRegion region = wmsWarehouseRegionService
						.selectWmsWarehouseRegionById(move.getTargetRegionId());
				if (move.getTargetWarehouseId().longValue() != region.getWarehouseId().longValue()) {
					return false;
				}
				return true;
			}
			return false;
		}).forEach(move -> {
			// 检查源仓库库存是否充足
			WmsWarehouseMaterialDetail wmsWarehouseMaterialDetail = new WmsWarehouseMaterialDetail();
			wmsWarehouseMaterialDetail.setMaterialId(move.getMaterialId());
			wmsWarehouseMaterialDetail.setWhId(move.getSourceWarehouseId());
			wmsWarehouseMaterialDetail.setWhRegionId(move.getSourceRegionId());
			wmsWarehouseMaterialDetail.setWhLocationId(move.getSourceLocationId());
			wmsWarehouseMaterialDetail.setBatchNo(move.getBatchNo());
			List<WmsWarehouseMaterialDetail> sourceDetail = wmsWarehouseMaterialDetailService
					.selectWmsWarehouseMaterialDetailList(wmsWarehouseMaterialDetail);
			if (sourceDetail != null && !sourceDetail.isEmpty()) {
				WmsWarehouseMaterialDetail source = sourceDetail.get(0);
				double total = source.getInventory() == null ? 0 : source.getInventory();
				double lock = source.getLockInventory() == null ? 0 : source.getLockInventory();
				double outQuantity = move.getMoveQuantity();
				if (outQuantity > total - lock) {
					msg.append("物料：[").append(move.getMaterialName()).append("]在库位：")
							.append(move.getSourceWarehouseName()).append(move.getSourceRegionName())
							.append(move.getSourceLocationName()).append("库存：").append(total - lock)
							.append("，库存不足不能移库<br/>");
				} else {
					move.setOperateDate(DateUtils.getNowDate());
					move.setOperateUser(String.valueOf(this.getUserId()));
					moveList.add(move);
				}
			} else {
				msg.append("物料：[").append(move.getMaterialName()).append("]在库位：").append(move.getSourceWarehouseName())
						.append(move.getSourceRegionName()).append(move.getSourceLocationName()).append("无库存不能移库<br/>");
			}
		});
		if (StringUtils.isNotBlank(msg.toString())) {
			return AjaxResult.error(msg.toString());
		}
		wmsWarehouseMaterialMoveService.move(moveList);
		return AjaxResult.success();
	}

	/**
	 * 修改移库信息
	 */
	@ApiOperation("修改移库信息")
	@DynamicResponseParameters(properties = { @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
			@DynamicParameter(name = "msg", value = "返回消息内容") })
	@PreAuthorize("@ss.hasPermi('warehouse:warehouseMove:edit')")
	@Log(title = "移库信息", businessType = BusinessType.UPDATE)
	@PutMapping
	public AjaxResult edit(@RequestBody WmsWarehouseMaterialMove wmsWarehouseMaterialMove) {
		return toAjax(wmsWarehouseMaterialMoveService.updateWmsWarehouseMaterialMove(wmsWarehouseMaterialMove));
	}

	/**
	 * 删除移库信息
	 */
	@ApiOperation("删除移库信息")
	@DynamicResponseParameters(properties = { @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
			@DynamicParameter(name = "msg", value = "返回消息内容") })
	@PreAuthorize("@ss.hasPermi('warehouse:warehouseMove:remove')")
	@Log(title = "移库信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
	public AjaxResult remove(@PathVariable Long[] ids) {
		return toAjax(wmsWarehouseMaterialMoveService.deleteWmsWarehouseMaterialMoveByIds(ids));
	}
}
