package com.neu.carbon.scm.controller;

import java.util.ArrayList;
import java.util.Date;
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
import com.neu.carbon.scm.domain.ScmPurchasePlan;
import com.neu.carbon.scm.domain.ScmPurchasePlanDetail;
import com.neu.carbon.scm.service.IScmPurchasePlanService;
import com.neu.carbon.wms.domain.WmsMaterialInfo;
import com.neu.carbon.wms.service.IWmsMaterialInfoService;
import com.neu.common.annotation.Log;
import com.neu.common.constant.UserConstants;
import com.neu.common.core.controller.BaseController;
import com.neu.common.core.domain.AjaxResult;
import com.neu.common.core.page.TableDataInfo;
import com.neu.common.enums.BusinessType;
import com.neu.common.utils.DateUtils;
import com.neu.common.utils.SecurityUtils;
import com.neu.common.utils.StringUtils;
import com.neu.common.utils.poi.ExcelUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 采购计划Controller
 *
 * @author neuedu
 * @date 2022-06-28
 */
@Api(tags = {"采购计划"})
@RestController
@RequestMapping("/purchase/plan")
public class ScmPurchasePlanController extends BaseController {
    @Autowired
    private IScmPurchasePlanService scmPurchasePlanService;
    @Autowired
    private IWmsMaterialInfoService wmsMaterialInfoService;

    /**
     * 查询采购计划列表
     */
    @GetMapping("/list")
    @ApiOperation("查询采购计划列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = ScmPurchasePlan.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list(ScmPurchasePlan scmPurchasePlan) {
        Long userId = this.getUserId();
        if (!SecurityUtils.isAdmin(userId)) {
            scmPurchasePlan.setApplyUser(String.valueOf(userId));
        }
        startPage();
        List<ScmPurchasePlan> list = scmPurchasePlanService.selectScmPurchasePlanList(scmPurchasePlan);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('purchase:plan:audit')")
    @GetMapping("/audit/list")
    @ApiOperation("查询待审核和已审核采购计划列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = ScmPurchasePlan.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list4Audit(ScmPurchasePlan scmPurchasePlan) {
        //默认查询待审核
        List<String> applyStatusList = new ArrayList<>();
        applyStatusList.add(UserConstants.APPLY_STATUS_WAIT_AUDIT);
        applyStatusList.add(UserConstants.APPLY_STATUS_HAS_AUDIT);
        scmPurchasePlan.getParams().put("applyStatusList", applyStatusList);
        if (StringUtils.isBlank(scmPurchasePlan.getApplyStatus()) && StringUtils.isBlank(scmPurchasePlan.getAuditStatus())) {
            scmPurchasePlan.setApplyStatus(UserConstants.APPLY_STATUS_WAIT_AUDIT);
        }
        startPage();
        List<ScmPurchasePlan> list = scmPurchasePlanService.selectScmPurchasePlanList(scmPurchasePlan);
        return getDataTable(list);
    }

    /**
     * 导出采购计划列表
     */
    @ApiOperation("导出采购计划列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "文件名")
    })
    @PreAuthorize("@ss.hasPermi('purchase:plan:export')")
    @Log(title = "采购计划", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ScmPurchasePlan scmPurchasePlan) {
        List<ScmPurchasePlan> list = scmPurchasePlanService.selectScmPurchasePlanList(scmPurchasePlan);
        ExcelUtil<ScmPurchasePlan> util = new ExcelUtil<ScmPurchasePlan>(ScmPurchasePlan.class);
        return util.exportExcel(list, "plan");
    }

    /**
     * 获取采购计划详细信息
     */
    @ApiOperation("获取采购计划详细信息")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "data", value = "返回业务数据", dataTypeClass = ScmPurchasePlan.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        ScmPurchasePlan plan = scmPurchasePlanService.selectScmPurchasePlanById(id);
        List<ScmPurchasePlanDetail> planDetailList = plan.getScmPurchasePlanDetailList();
        if (planDetailList != null && !planDetailList.isEmpty()) {
            //获取物料档案信息
            planDetailList.stream().forEach(detail -> {
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
        return AjaxResult.success(plan);
    }

    /**
     * 新增采购计划
     */
    @ApiOperation("新增采购计划")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('purchase:plan:add')")
    @Log(title = "采购计划", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ScmPurchasePlan scmPurchasePlan) {
        scmPurchasePlan.setApplyUser(String.valueOf(this.getUserId()));
        scmPurchasePlan.setApplyStatus(UserConstants.APPLY_STATUS_NO_SUBMIT);
        scmPurchasePlan.setApplyTime(new Date());
        return toAjax(scmPurchasePlanService.insertScmPurchasePlan(scmPurchasePlan));
    }

    /**
     * 修改采购计划
     */
    @ApiOperation("修改采购计划")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('purchase:plan:edit')")
    @Log(title = "采购计划", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ScmPurchasePlan scmPurchasePlan) {
        scmPurchasePlan.setApplyStatus(UserConstants.APPLY_STATUS_NO_SUBMIT);
        scmPurchasePlan.setAuditUser("");
        scmPurchasePlan.setAuditStatus("");
        scmPurchasePlan.setAuditTime(null);
        scmPurchasePlan.setAuditComment("");
        return toAjax(scmPurchasePlanService.updateScmPurchasePlan(scmPurchasePlan));
    }

    /**
     * 删除采购计划
     */
    @ApiOperation("删除采购计划")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('purchase:plan:remove')")
    @Log(title = "采购计划", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(scmPurchasePlanService.deleteScmPurchasePlanByIds(ids));
    }

    @ApiOperation("提交采购计划")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('purchase:plan:edit')")
    @Log(title = "提交采购计划", businessType = BusinessType.UPDATE)
    @PutMapping("/submit")
    public AjaxResult submit(@RequestBody ScmPurchasePlan scmPurchasePlan) {
        scmPurchasePlan.setApplyStatus(UserConstants.APPLY_STATUS_WAIT_AUDIT);
        scmPurchasePlan.setApplyTime(DateUtils.getNowDate());
        if (scmPurchasePlan.getId() != null) {
            ScmPurchasePlan apply = scmPurchasePlanService.selectScmPurchasePlanById(scmPurchasePlan.getId());
            if (apply == null) {
                return AjaxResult.error("此采购计划不存在，不能提交！");
            }
            scmPurchasePlan.setAuditUser("");
            scmPurchasePlan.setAuditStatus("");
            scmPurchasePlan.setAuditTime(null);
            scmPurchasePlan.setAuditComment("");
            return toAjax(scmPurchasePlanService.updateScmPurchasePlan(scmPurchasePlan));
        } else {
            scmPurchasePlan.setApplyUser(String.valueOf(this.getUserId()));
            scmPurchasePlan.setApplyTime(new Date());
            return toAjax(scmPurchasePlanService.insertScmPurchasePlan(scmPurchasePlan));
        }
    }

    @ApiOperation("审核采购计划")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('purchase:plan:audit')")
    @Log(title = "采购计划审核", businessType = BusinessType.UPDATE)
    @PutMapping("/audit")
    public AjaxResult audit(@RequestBody ScmPurchasePlan scmPurchasePlan) {
        scmPurchasePlan.setApplyStatus(UserConstants.APPLY_STATUS_HAS_AUDIT);
        scmPurchasePlan.setAuditTime(DateUtils.getNowDate());
        Long loginUserId = SecurityUtils.getLoginUser().getUser().getUserId();
        scmPurchasePlan.setAuditUser(String.valueOf(loginUserId));
        return toAjax(scmPurchasePlanService.updateScmPurchasePlan(scmPurchasePlan));
    }


}
