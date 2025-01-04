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
import com.neu.carbon.scm.domain.ScmPurchaseApply;
import com.neu.carbon.scm.domain.ScmPurchaseApplyDetail;
import com.neu.carbon.scm.domain.ScmPurchasePlanDetail;
import com.neu.carbon.scm.service.IScmPurchaseApplyService;
import com.neu.carbon.scm.service.IScmPurchasePlanDetailService;
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
 * 采购申请Controller
 * 
 * @author neuedu
 * @date 2022-06-29
 */
@Api(tags = {"采购申请"})
@RestController
@RequestMapping("/purchase/apply")
public class ScmPurchaseApplyController extends BaseController
{
    @Autowired
    private IScmPurchaseApplyService scmPurchaseApplyService;
    @Autowired
    private IWmsMaterialInfoService wmsMaterialInfoService;

    @Autowired
    private IScmPurchasePlanDetailService scmPurchasePlanDetailService;

    /**
     * 查询采购申请列表
     */
    @GetMapping("/list")
    @ApiOperation("查询采购申请列表")
    @DynamicResponseParameters(properties = {
	        @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = ScmPurchaseApply.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list(ScmPurchaseApply scmPurchaseApply)
    {
        Long userId = this.getUserId();
        if (!SecurityUtils.isAdmin(userId)) {
            scmPurchaseApply.setApplyUser(String.valueOf(userId));
        }
        startPage();
        List<ScmPurchaseApply> list = scmPurchaseApplyService.selectScmPurchaseApplyList(scmPurchaseApply);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('purchase:apply:audit')")
    @GetMapping("/audit/list")
    @ApiOperation("查询待审核和已审核采购计划列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = ScmPurchaseApply.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list4Audit(ScmPurchaseApply scmPurchaseApply) {
        //默认查询待审核
        List<String> applyStatusList = new ArrayList<>();
        applyStatusList.add(UserConstants.APPLY_STATUS_WAIT_AUDIT);
        applyStatusList.add(UserConstants.APPLY_STATUS_HAS_AUDIT);
        scmPurchaseApply.getParams().put("applyStatusList", applyStatusList);
        if(StringUtils.isBlank(scmPurchaseApply.getApplyStatus())&&StringUtils.isBlank(scmPurchaseApply.getAuditStatus())) {
            scmPurchaseApply.setApplyStatus(UserConstants.APPLY_STATUS_WAIT_AUDIT);
        }
        startPage();
        List<ScmPurchaseApply> list = scmPurchaseApplyService.selectScmPurchaseApplyList(scmPurchaseApply);
        return getDataTable(list);
    }

    /**
     * 导出采购申请列表
     */
    @ApiOperation("导出采购申请列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "文件名")
    })
    @PreAuthorize("@ss.hasPermi('purchase:apply:export')")
    @Log(title = "采购申请", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ScmPurchaseApply scmPurchaseApply)
    {
        List<ScmPurchaseApply> list = scmPurchaseApplyService.selectScmPurchaseApplyList(scmPurchaseApply);
        ExcelUtil<ScmPurchaseApply> util = new ExcelUtil<ScmPurchaseApply>(ScmPurchaseApply.class);
        return util.exportExcel(list, "apply");
    }

    /**
     * 获取采购申请详细信息
     */
    @ApiOperation("获取采购申请详细信息")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "data", value = "返回业务数据", dataTypeClass = ScmPurchaseApply.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        ScmPurchaseApply apply = scmPurchaseApplyService.selectScmPurchaseApplyById(id);
        List<ScmPurchaseApplyDetail> detailList = apply.getScmPurchaseApplyDetailList();
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
        return AjaxResult.success(apply);
    }

    /**
     * 新增采购申请
     */
    @ApiOperation("新增采购申请")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('purchase:apply:add')")
    @Log(title = "采购申请", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ScmPurchaseApply scmPurchaseApply)
    {
        scmPurchaseApply.setApplyUser(String.valueOf(this.getUserId()));
        scmPurchaseApply.setApplyStatus(UserConstants.APPLY_STATUS_NO_SUBMIT);
        scmPurchaseApply.setApplyTime(DateUtils.getNowDate());
        return toAjax(scmPurchaseApplyService.insertScmPurchaseApply(scmPurchaseApply));
    }

    /**
     * 修改采购申请
     */
    @ApiOperation("修改采购申请")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('purchase:apply:edit')")
    @Log(title = "采购申请", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ScmPurchaseApply scmPurchaseApply)
    {
        scmPurchaseApply.setApplyStatus(UserConstants.APPLY_STATUS_NO_SUBMIT);
        scmPurchaseApply.setAuditUser("");
        scmPurchaseApply.setAuditStatus("");
        scmPurchaseApply.setAuditTime(null);
        scmPurchaseApply.setAuditComment("");
        return toAjax(scmPurchaseApplyService.updateScmPurchaseApply(scmPurchaseApply));
    }

    /**
     * 删除采购申请
     */
    @ApiOperation("删除采购申请")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('purchase:apply:remove')")
    @Log(title = "采购申请", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(scmPurchaseApplyService.deleteScmPurchaseApplyByIds(ids));
    }

    @ApiOperation("提交采购计划")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('purchase:plan:edit')")
    @Log(title = "提交采购申请", businessType = BusinessType.UPDATE)
    @PutMapping("/submit")
    public AjaxResult submit(@RequestBody ScmPurchaseApply scmPurchaseApply) {
        scmPurchaseApply.setApplyStatus(UserConstants.APPLY_STATUS_WAIT_AUDIT);
        if (scmPurchaseApply.getId() != null) {
            ScmPurchaseApply apply = scmPurchaseApplyService.selectScmPurchaseApplyById(scmPurchaseApply.getId());
            if (apply == null) {
                return AjaxResult.error("此采购申请不存在，不能提交！");
            }
            scmPurchaseApply.setAuditUser("");
            scmPurchaseApply.setAuditStatus("");
            scmPurchaseApply.setAuditTime(null);
            scmPurchaseApply.setAuditComment("");
            return toAjax(scmPurchaseApplyService.updateScmPurchaseApply(scmPurchaseApply));
        } else {
            scmPurchaseApply.setApplyUser(String.valueOf(this.getUserId()));
            scmPurchaseApply.setApplyTime(new Date());
            return toAjax(scmPurchaseApplyService.insertScmPurchaseApply(scmPurchaseApply));
        }
    }

    @ApiOperation("审核采购申请")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('purchase:apply:audit')")
    @Log(title = "采购申请审核", businessType = BusinessType.UPDATE)
    @PutMapping("/audit")
    public AjaxResult audit(@RequestBody ScmPurchaseApply scmPurchaseApply) {
        scmPurchaseApply.setApplyStatus(UserConstants.APPLY_STATUS_HAS_AUDIT);
        scmPurchaseApply.setAuditTime(DateUtils.getNowDate());
        scmPurchaseApply.setAuditUser(String.valueOf(this.getUserId()));
        return toAjax(scmPurchaseApplyService.updateScmPurchaseApply(scmPurchaseApply));
    }

    @GetMapping("/applyDetailList/{planId}")
    @ApiOperation("查询采购计划详细列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = ScmPurchaseApply.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public AjaxResult getApplyDetailListByPlanId(@PathVariable("planId") Long planId)
    {
        // 根据计划id获取PlanDetail列表
        ScmPurchasePlanDetail cond = new ScmPurchasePlanDetail();
        cond.setPurchasePlanId(planId);
        List<ScmPurchasePlanDetail> planDetailList = scmPurchasePlanDetailService.selectScmPurchasePlanDetailList(cond);
        List<ScmPurchaseApplyDetail> applyDetailList = new ArrayList<>();
        // 根据PlanDetail列表封装ApplyDetail列表
        for (ScmPurchasePlanDetail planDetail : planDetailList) {
            ScmPurchaseApplyDetail applyDetail = new ScmPurchaseApplyDetail();
            applyDetail.setMaterialId(planDetail.getMaterialId());
            applyDetail.setQuantity(planDetail.getQuantity());
            applyDetail.setRequireQuantity(planDetail.getRequireQuantity());
            applyDetail.setRequireDate(planDetail.getRequireDate());
            WmsMaterialInfo material = wmsMaterialInfoService.selectWmsMaterialInfoById(planDetail.getMaterialId());
            if (material != null) {
                applyDetail.setPrice(material.getPrice());
                applyDetail.setMaterialCode(material.getCode());
                applyDetail.setMaterialModel(material.getModel());
                applyDetail.setMaterialName(material.getName());
                applyDetail.setMaterialPrice(material.getPrice());
                applyDetail.setMaterialSpecification(material.getSpecification());
                applyDetail.setMaterialUnit(material.getUnit());
            }
            applyDetailList.add(applyDetail);
        }
        return AjaxResult.success(applyDetailList);
    }
}
