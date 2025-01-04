package com.neu.carbon.scm.controller;

import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicResponseParameters;
import com.neu.carbon.scm.domain.ScmSalePlan;
import com.neu.carbon.scm.domain.ScmSalePlanDetail;
import com.neu.carbon.scm.service.IScmSalePlanService;
import com.neu.carbon.wms.domain.WmsMaterialInfo;
import com.neu.carbon.wms.service.IWmsMaterialInfoService;
import com.neu.common.annotation.Log;
import com.neu.common.core.controller.BaseController;
import com.neu.common.core.domain.AjaxResult;
import com.neu.common.core.page.TableDataInfo;
import com.neu.common.enums.BusinessType;
import com.neu.common.utils.poi.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 销售计划Controller
 *
 * @author neuedu
 * @date 2022-07-13
 */
@Api(tags = {"销售计划"})
@RestController
@RequestMapping("/sale/plan")
public class ScmSalePlanController extends BaseController {
    @Autowired
    private IScmSalePlanService scmSalePlanService;
    @Autowired
    private IWmsMaterialInfoService wmsMaterialInfoService;

    /**
     * 查询销售计划列表
     */
    @GetMapping("/list")
    @ApiOperation("查询销售计划列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = ScmSalePlan.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list(ScmSalePlan scmSalePlan) {
        startPage();
        List<ScmSalePlan> list = scmSalePlanService.selectScmSalePlanList(scmSalePlan);
        return getDataTable(list);
    }

    /**
     * 导出销售计划列表
     */
    @ApiOperation("导出销售计划列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "文件名")
    })
    @PreAuthorize("@ss.hasPermi('sale:plan:export')")
    @Log(title = "销售计划", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ScmSalePlan scmSalePlan) {
        List<ScmSalePlan> list = scmSalePlanService.selectScmSalePlanList(scmSalePlan);
        ExcelUtil<ScmSalePlan> util = new ExcelUtil<ScmSalePlan>(ScmSalePlan.class);
        return util.exportExcel(list, "plan");
    }

    /**
     * 获取销售计划详细信息
     */
    @ApiOperation("获取销售计划详细信息")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "data", value = "返回业务数据", dataTypeClass = ScmSalePlan.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        ScmSalePlan salePlan = scmSalePlanService.selectScmSalePlanById(id);
        List<ScmSalePlanDetail> detailList = salePlan.getScmSalePlanDetailList();
        if (detailList != null && !detailList.isEmpty()) {
            //获取物料档案信息
            detailList.stream().forEach(detail -> {
                WmsMaterialInfo material = wmsMaterialInfoService.selectWmsMaterialInfoById(detail.getMaterialId());
                if (material != null) {
                    detail.setMaterialCode(material.getCode());
                    detail.setMaterialModel(material.getModel());
                    detail.setMaterialName(material.getName());
                    detail.setMaterialPrice(material.getPrice());
                    detail.setMaterialSpecification(material.getSpecification());
                    detail.setMaterialUnit(material.getUnit());
                }
            });
        }
        return AjaxResult.success(salePlan);
    }

    /**
     * 新增销售计划
     */
    @ApiOperation("新增销售计划")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('sale:plan:add')")
    @Log(title = "销售计划", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ScmSalePlan scmSalePlan) {
        return toAjax(scmSalePlanService.insertScmSalePlan(scmSalePlan));
    }

    /**
     * 修改销售计划
     */
    @ApiOperation("修改销售计划")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('sale:plan:edit')")
    @Log(title = "销售计划", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ScmSalePlan scmSalePlan) {
        return toAjax(scmSalePlanService.updateScmSalePlan(scmSalePlan));
    }

    /**
     * 删除销售计划
     */
    @ApiOperation("删除销售计划")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('sale:plan:remove')")
    @Log(title = "销售计划", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(scmSalePlanService.deleteScmSalePlanByIds(ids));
    }
}
