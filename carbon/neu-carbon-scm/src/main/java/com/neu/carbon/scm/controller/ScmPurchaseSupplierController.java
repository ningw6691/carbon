package com.neu.carbon.scm.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
import com.neu.carbon.scm.domain.ScmPurchaseSupplier;
import com.neu.carbon.scm.service.IScmPurchaseSupplierService;
import com.neu.common.annotation.Log;
import com.neu.common.constant.UserConstants;
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
 * 供应商Controller
 *
 * @author neuedu
 * @date 2022-06-27
 */
@Api(tags = {"供应商"})
@RestController
@RequestMapping("/purchase/supplier")
public class ScmPurchaseSupplierController extends BaseController {
    @Autowired
    private IScmPurchaseSupplierService scmPurchaseSupplierService;

    /**
     * 查询供应商列表
     */
    @PreAuthorize("@ss.hasPermi('purchase:supplier:list')")
    @GetMapping("/list")
    @ApiOperation("查询供应商列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = ScmPurchaseSupplier.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list(ScmPurchaseSupplier scmPurchaseSupplier) {
        startPage();
        List<ScmPurchaseSupplier> list = scmPurchaseSupplierService.selectScmPurchaseSupplierList(scmPurchaseSupplier);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('purchase:supplier:audit')")
    @GetMapping("/audit/list")
    @ApiOperation("查询待审核和已审核采购合同列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = ScmPurchaseSupplier.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list4Audit(ScmPurchaseSupplier scmPurchaseSupplier) {
        //默认查询待审核
        List<String> applyStatusList = new ArrayList<>();
        applyStatusList.add(UserConstants.APPLY_STATUS_WAIT_AUDIT);
        applyStatusList.add(UserConstants.APPLY_STATUS_HAS_AUDIT);
        scmPurchaseSupplier.getParams().put("applyStatusList", applyStatusList);
        if(StringUtils.isBlank(scmPurchaseSupplier.getApplyStatus())&&StringUtils.isBlank(scmPurchaseSupplier.getAuditStatus())) {
            scmPurchaseSupplier.setApplyStatus(UserConstants.APPLY_STATUS_WAIT_AUDIT);
        }
        startPage();
        List<ScmPurchaseSupplier> list = scmPurchaseSupplierService.selectScmPurchaseSupplierList(scmPurchaseSupplier);
        return getDataTable(list);
    }

    /**
     * 导出供应商列表
     */
    @ApiOperation("导出供应商列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "文件名")
    })
    @PreAuthorize("@ss.hasPermi('purchase:supplier:export')")
    @Log(title = "供应商", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ScmPurchaseSupplier scmPurchaseSupplier) {
        List<ScmPurchaseSupplier> list = scmPurchaseSupplierService.selectScmPurchaseSupplierList(scmPurchaseSupplier);
        ExcelUtil<ScmPurchaseSupplier> util = new ExcelUtil<ScmPurchaseSupplier>(ScmPurchaseSupplier.class);
        return util.exportExcel(list, "supplier");
    }

    /**
     * 获取供应商详细信息
     */
    @ApiOperation("获取供应商详细信息")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "data", value = "返回业务数据", dataTypeClass = ScmPurchaseSupplier.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('purchase:supplier:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        ScmPurchaseSupplier scmPurchaseSupplier = scmPurchaseSupplierService.selectScmPurchaseSupplierById(id);
        return AjaxResult.success(scmPurchaseSupplier);
    }

    /**
     * 新增供应商
     */
    @ApiOperation("新增供应商")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('purchase:supplier:add')")
    @Log(title = "供应商", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ScmPurchaseSupplier scmPurchaseSupplier) {
        scmPurchaseSupplier.setApplyUser(String.valueOf(this.getUserId()));
        scmPurchaseSupplier.setApplyTime(new Date());
        scmPurchaseSupplier.setApplyStatus(UserConstants.APPLY_STATUS_NO_SUBMIT);
        return toAjax(scmPurchaseSupplierService.insertScmPurchaseSupplier(scmPurchaseSupplier));
    }

    /**
     * 修改供应商
     */
    @ApiOperation("修改供应商")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('purchase:supplier:edit')")
    @Log(title = "供应商", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ScmPurchaseSupplier scmPurchaseSupplier) {
        scmPurchaseSupplier.setApplyStatus(UserConstants.APPLY_STATUS_NO_SUBMIT);
        scmPurchaseSupplier.setAuditUser("");
        scmPurchaseSupplier.setAuditStatus("");
        scmPurchaseSupplier.setAuditTime(null);
        scmPurchaseSupplier.setAuditComment("");
        return toAjax(scmPurchaseSupplierService.updateScmPurchaseSupplier(scmPurchaseSupplier));
    }

    /**
     * 删除供应商
     */
    @ApiOperation("删除供应商")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('purchase:supplier:remove')")
    @Log(title = "供应商", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(scmPurchaseSupplierService.deleteScmPurchaseSupplierByIds(ids));
    }

    @ApiOperation("提交采购计划")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('purchase:plan:edit')")
    @Log(title = "提交采购申请", businessType = BusinessType.UPDATE)
    @PutMapping("/submit")
    public AjaxResult submit(@RequestBody ScmPurchaseSupplier scmPurchaseSupplier) {
        scmPurchaseSupplier.setApplyStatus(UserConstants.APPLY_STATUS_WAIT_AUDIT);
        if (scmPurchaseSupplier.getId() != null) {
            ScmPurchaseSupplier supplier = scmPurchaseSupplierService.selectScmPurchaseSupplierById(scmPurchaseSupplier.getId());
            if (supplier == null) {
                return AjaxResult.error("此供应商不存在，不能提交！");
            }
            scmPurchaseSupplier.setAuditUser("");
            scmPurchaseSupplier.setAuditStatus("");
            scmPurchaseSupplier.setAuditTime(null);
            scmPurchaseSupplier.setAuditComment("");
            return toAjax(scmPurchaseSupplierService.updateScmPurchaseSupplier(scmPurchaseSupplier));
        } else {
            scmPurchaseSupplier.setApplyUser(String.valueOf(this.getUserId()));
            scmPurchaseSupplier.setApplyTime(new Date());
            return toAjax(scmPurchaseSupplierService.insertScmPurchaseSupplier(scmPurchaseSupplier));
        }
    }



    @ApiOperation("审核供应商")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('purchase:supplier:audit')")
    @Log(title = "供应商审核", businessType = BusinessType.UPDATE)
    @PutMapping("/audit")
    public AjaxResult audit(@RequestBody ScmPurchaseSupplier scmPurchaseSupplier) {
        scmPurchaseSupplier.setApplyStatus(UserConstants.APPLY_STATUS_HAS_AUDIT);
        scmPurchaseSupplier.setAuditTime(DateUtils.getNowDate());
        scmPurchaseSupplier.setAuditUser(String.valueOf(this.getUserId()));
        return toAjax(scmPurchaseSupplierService.updateScmPurchaseSupplier(scmPurchaseSupplier));
    }



}
