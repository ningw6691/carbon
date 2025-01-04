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
import com.neu.carbon.mes.domain.MesProductJob;
import com.neu.carbon.mes.domain.MesProductJobMaterial;
import com.neu.carbon.mes.domain.MesProductPlan;
import com.neu.carbon.mes.domain.MesProductSchedule;
import com.neu.carbon.mes.domain.MesProductScheduleMaterial;
import com.neu.carbon.mes.service.IMesProductJobService;
import com.neu.carbon.mes.service.IMesProductPlanService;
import com.neu.carbon.mes.service.IMesProductScheduleService;
import com.neu.carbon.wms.domain.WmsMaterialInfo;
import com.neu.carbon.wms.service.IWmsMaterialInfoService;
import com.neu.common.annotation.Log;
import com.neu.common.core.controller.BaseController;
import com.neu.common.core.domain.AjaxResult;
import com.neu.common.core.page.TableDataInfo;
import com.neu.common.enums.BusinessType;
import com.neu.common.utils.StringUtils;
import com.neu.common.utils.poi.ExcelUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 生产作业Controller
 * 
 * @author neuedu
 * @date 2022-07-15
 */
@Api(tags = { "生产作业" })
@RestController
@RequestMapping("/mesProduct/productJob")
public class MesProductJobController extends BaseController {
	@Autowired
	private IMesProductJobService mesProductJobService;
	@Autowired
	private IMesProductPlanService mesProductPlanService;
	@Autowired
	private IWmsMaterialInfoService wmsMaterialInfoService;
	@Autowired
	private IMesProductScheduleService mesProductScheduleService;

	/**
	 * 查询生产作业列表
	 */
	@GetMapping("/list")
	@ApiOperation("查询生产作业列表")
	@DynamicResponseParameters(properties = { @DynamicParameter(name = "total", value = "总记录数"),
			@DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
			@DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = MesProductJob.class),
			@DynamicParameter(name = "msg", value = "返回消息内容") })
	public TableDataInfo list(MesProductJob mesProductJob) {
		startPage();
		List<MesProductJob> list = mesProductJobService.selectMesProductJobList(mesProductJob);
		list.stream().forEach(job -> {
			MesProductPlan plan = mesProductPlanService.selectMesProductPlanById(job.getPlanId());
			if (plan != null) {
				job.setPlanNo(plan.getSerialNo());
			}
			MesProductSchedule schedule = mesProductScheduleService.selectMesProductScheduleById(job.getScheduleId());
			if (schedule != null) {
				job.setScheduleNo(schedule.getSerialNo());
			}
			WmsMaterialInfo material = wmsMaterialInfoService.selectWmsMaterialInfoById(job.getProductId());
			if (material != null) {
				job.setMaterialCode(material.getCode());
				job.setMaterialModel(material.getModel());
				job.setProductName(material.getName());
				job.setMaterialSpecification(material.getSpecification());
				job.setMaterialUnit(material.getUnit());
			}
		});
		return getDataTable(list);
	}

	/**
	 * 导出生产作业列表
	 */
	@ApiOperation("导出生产作业列表")
	@DynamicResponseParameters(properties = { @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
			@DynamicParameter(name = "msg", value = "文件名") })
	@PreAuthorize("@ss.hasPermi('mesProduct:productJob:export')")
	@Log(title = "生产作业", businessType = BusinessType.EXPORT)
	@GetMapping("/export")
	public AjaxResult export(MesProductJob mesProductJob) {
		List<MesProductJob> list = mesProductJobService.selectMesProductJobList(mesProductJob);
		list.stream().forEach(job -> {
			MesProductPlan plan = mesProductPlanService.selectMesProductPlanById(job.getPlanId());
			if (plan != null) {
				job.setPlanNo(plan.getSerialNo());
			}
			MesProductSchedule schedule = mesProductScheduleService.selectMesProductScheduleById(job.getScheduleId());
			if (schedule != null) {
				job.setScheduleNo(schedule.getSerialNo());
			}
			WmsMaterialInfo material = wmsMaterialInfoService.selectWmsMaterialInfoById(job.getProductId());
			if (material != null) {
				job.setMaterialCode(material.getCode());
				job.setMaterialModel(material.getModel());
				job.setProductName(material.getName());
				job.setMaterialSpecification(material.getSpecification());
				job.setMaterialUnit(material.getUnit());
			}
		});
		ExcelUtil<MesProductJob> util = new ExcelUtil<MesProductJob>(MesProductJob.class);
		return util.exportExcel(list, "productJob");
	}

	/**
	 * 获取生产作业详细信息
	 */
	@ApiOperation("获取生产作业详细信息")
	@DynamicResponseParameters(properties = { @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
			@DynamicParameter(name = "data", value = "返回业务数据", dataTypeClass = MesProductJob.class),
			@DynamicParameter(name = "msg", value = "返回消息内容") })
	@GetMapping(value = "/{id}")
	public AjaxResult getInfo(@PathVariable("id") Long id) {
		MesProductJob job = mesProductJobService.selectMesProductJobById(id);
		MesProductPlan plan = mesProductPlanService.selectMesProductPlanById(job.getPlanId());
		if (plan != null) {
			job.setPlanNo(plan.getSerialNo());
		}
		MesProductSchedule schedule = mesProductScheduleService.selectMesProductScheduleById(job.getScheduleId());
		job.setRequireQuantity(schedule.getRequireQuantity());
		job.setFinishQuantity(schedule.getProductQuantity());
		WmsMaterialInfo material = wmsMaterialInfoService.selectWmsMaterialInfoById(job.getProductId());
		if (material != null) {
			job.setMaterialCode(material.getCode());
			job.setMaterialModel(material.getModel());
			job.setProductName(material.getName());
			job.setMaterialSpecification(material.getSpecification());
			job.setMaterialUnit(material.getUnit());
		}
		List<MesProductJobMaterial> detailList = job.getMesProductJobMaterialList();
		detailList.stream().forEach(detail -> {
			WmsMaterialInfo materialInfo = wmsMaterialInfoService.selectWmsMaterialInfoById(detail.getMaterialId());
			if (material != null) {
				detail.setMaterialCode(materialInfo.getCode());
				detail.setMaterialModel(materialInfo.getModel());
				detail.setMaterialName(materialInfo.getName());
				detail.setMaterialSpecification(materialInfo.getSpecification());
				detail.setMaterialUnit(materialInfo.getUnit());
			}
		});
		return AjaxResult.success(job);
	}

	/**
	 * 新增生产作业
	 */
	@ApiOperation("新增生产作业")
	@DynamicResponseParameters(properties = { @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
			@DynamicParameter(name = "msg", value = "返回消息内容") })
	@PreAuthorize("@ss.hasPermi('mesProduct:productJob:add')")
	@Log(title = "生产作业", businessType = BusinessType.INSERT)
	@PostMapping
	public AjaxResult add(@RequestBody MesProductJob mesProductJob) {
		// 校验排产明细物料是否充足
		MesProductSchedule schedule = mesProductScheduleService
				.selectMesProductScheduleById(mesProductJob.getScheduleId());
		List<MesProductScheduleMaterial> smList = schedule.getMesProductScheduleMaterialList();
		List<MesProductJobMaterial> pmList = mesProductJob.getMesProductJobMaterialList();
		StringBuilder error = new StringBuilder();
		for (MesProductJobMaterial pm : pmList) {
			smList.stream().filter(sm -> {
				if (sm.getMaterialId().longValue() == pm.getMaterialId().longValue()) {
					double left = sm.getLeftQuantity()==null?0:sm.getLeftQuantity().doubleValue();
					if (left < pm.getUsageQuantity().doubleValue()) {
						return true;
					}
				}
				return false;
			}).findAny().ifPresent(sm -> {
				error.append("物料[" + pm.getMaterialName() + "]剩余数量[" + sm.getLeftQuantity() + "]已不足，不能生产<br/>");
			});
		}
		String msg = error.toString();
		if (StringUtils.isNotBlank(msg)) {
			return AjaxResult.error(msg);
		}
		return toAjax(mesProductJobService.insertMesProductJob(mesProductJob));
	}

	/**
	 * 修改生产作业
	 */
	@ApiOperation("修改生产作业")
	@DynamicResponseParameters(properties = { @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
			@DynamicParameter(name = "msg", value = "返回消息内容") })
	@PreAuthorize("@ss.hasPermi('mesProduct:productJob:edit')")
	@Log(title = "生产作业", businessType = BusinessType.UPDATE)
	@PutMapping
	public AjaxResult edit(@RequestBody MesProductJob mesProductJob) {
		return toAjax(mesProductJobService.updateMesProductJob(mesProductJob));
	}

	/**
	 * 修改生产作业状态
	 */
	@ApiOperation("修改生产作业状态")
	@DynamicResponseParameters(properties = { @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
			@DynamicParameter(name = "msg", value = "返回消息内容") })
	@PreAuthorize("@ss.hasPermi('mesProduct:productJob:edit')")
	@Log(title = "生产作业", businessType = BusinessType.UPDATE)
	@PutMapping("/change/status")
	public AjaxResult changeStatus(@RequestBody MesProductJob mesProductJob) {
		String status = mesProductJob.getStatus();
		//执行生产校验物料是否充足
		if ("1".equals(status)) {
			MesProductJob job = mesProductJobService.selectMesProductJobById(mesProductJob.getId());
			MesProductSchedule schedule = mesProductScheduleService
					.selectMesProductScheduleById(job.getScheduleId());
			List<MesProductScheduleMaterial> smList = schedule.getMesProductScheduleMaterialList();
			List<MesProductJobMaterial> pmList = job.getMesProductJobMaterialList();
			StringBuilder error = new StringBuilder();
			for (MesProductJobMaterial pm : pmList) {
				smList.stream().filter(sm -> {
					if (sm.getMaterialId().longValue() == pm.getMaterialId().longValue()) {
						double left = sm.getLeftQuantity()==null?0:sm.getLeftQuantity().doubleValue();
						if (left < pm.getUsageQuantity().doubleValue()) {
							return true;
						}
					}
					return false;
				}).findAny().ifPresent(sm -> {
					error.append("物料[" + pm.getMaterialName() + "]剩余数量[" + sm.getLeftQuantity() + "]已不足，不能生产<br/>");
				});
			}
			String msg = error.toString();
			if (StringUtils.isNotBlank(msg)) {
				return AjaxResult.error(msg);
			}
		}
		return toAjax(mesProductJobService.updateProductJobStatus(mesProductJob));
	}

	/**
	 * 删除生产作业
	 */
	@ApiOperation("删除生产作业")
	@DynamicResponseParameters(properties = { @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
			@DynamicParameter(name = "msg", value = "返回消息内容") })
	@PreAuthorize("@ss.hasPermi('mesProduct:productJob:remove')")
	@Log(title = "生产作业", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
	public AjaxResult remove(@PathVariable Long[] ids) {
		return toAjax(mesProductJobService.deleteMesProductJobByIds(ids));
	}
}
