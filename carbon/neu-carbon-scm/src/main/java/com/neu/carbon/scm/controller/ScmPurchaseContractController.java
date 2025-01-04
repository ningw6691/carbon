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
import com.neu.carbon.scm.domain.ScmPurchaseContract;
import com.neu.carbon.scm.domain.ScmPurchaseContractDetail;
import com.neu.carbon.scm.domain.ScmPurchaseSupplier;
import com.neu.carbon.scm.service.IScmPurchaseApplyDetailService;
import com.neu.carbon.scm.service.IScmPurchaseContractService;
import com.neu.carbon.scm.service.IScmPurchaseSupplierService;
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
 * 采购合同Controller
 *
 * @author neuedu
 * @date 2022-06-30
 */
@Api(tags = {"采购合同"})
@RestController
@RequestMapping("/purchase/contract")
public class ScmPurchaseContractController extends BaseController {
    @Autowired
    private IScmPurchaseContractService scmPurchaseContractService;
    @Autowired
    private IWmsMaterialInfoService wmsMaterialInfoService;
    @Autowired
    private IScmPurchaseSupplierService scmPurchaseSupplierService;

    /**
     * 查询采购合同列表
     */
    @GetMapping("/list")
    @ApiOperation("查询采购合同列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = ScmPurchaseContract.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list(ScmPurchaseContract scmPurchaseContract) {
        Long userId = this.getUserId();
        if (!SecurityUtils.isAdmin(userId)) {
            scmPurchaseContract.setApplyUser(String.valueOf(userId));
        }
        startPage();
        List<ScmPurchaseContract> list = scmPurchaseContractService.selectScmPurchaseContractList(scmPurchaseContract);
        list.forEach(contract -> {
            ScmPurchaseSupplier supplier = scmPurchaseSupplierService.selectScmPurchaseSupplierById(contract.getSupplierId());
            if (supplier != null) {
                contract.setSupplierName(supplier.getName());
                contract.setSupplierContact(supplier.getContact());
                contract.setSupplierContactTel(supplier.getContactTel());
            }
        });
        return getDataTable(list);
    }


    @PreAuthorize("@ss.hasPermi('purchase:plan:audit')")
    @GetMapping("/audit/list")
    @ApiOperation("查询待审核和已审核采购合同列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = ScmPurchaseContract.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list4Audit(ScmPurchaseContract scmPurchaseContract) {
        //默认查询待审核
        List<String> applyStatusList = new ArrayList<>();
        applyStatusList.add(UserConstants.APPLY_STATUS_WAIT_AUDIT);
        applyStatusList.add(UserConstants.APPLY_STATUS_HAS_AUDIT);
        scmPurchaseContract.getParams().put("applyStatusList", applyStatusList);
        if(StringUtils.isBlank(scmPurchaseContract.getApplyStatus())&&StringUtils.isBlank(scmPurchaseContract.getAuditStatus())) {
            scmPurchaseContract.setApplyStatus(UserConstants.APPLY_STATUS_WAIT_AUDIT);
        }
        startPage();
        List<ScmPurchaseContract> list = scmPurchaseContractService.selectScmPurchaseContractList(scmPurchaseContract);
        list.forEach(contract -> {
            ScmPurchaseSupplier supplier = scmPurchaseSupplierService.selectScmPurchaseSupplierById(contract.getSupplierId());
            if (supplier != null) {
                contract.setSupplierName(supplier.getName());
                contract.setSupplierContact(supplier.getContact());
                contract.setSupplierContactTel(supplier.getContactTel());
            }
        });
        return getDataTable(list);
    }

    /**
     * 导出采购合同列表
     */
    @ApiOperation("导出采购合同列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "文件名")
    })
    @PreAuthorize("@ss.hasPermi('purchase:contract:export')")
    @Log(title = "采购合同", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ScmPurchaseContract scmPurchaseContract) {
        List<ScmPurchaseContract> list = scmPurchaseContractService.selectScmPurchaseContractList(scmPurchaseContract);
        ExcelUtil<ScmPurchaseContract> util = new ExcelUtil<ScmPurchaseContract>(ScmPurchaseContract.class);
        return util.exportExcel(list, "contract");
    }

    /**
     * 获取采购合同详细信息
     */
    @ApiOperation("获取采购合同详细信息")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "data", value = "返回业务数据", dataTypeClass = ScmPurchaseContract.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        ScmPurchaseContract contract = scmPurchaseContractService.selectScmPurchaseContractById(id);
        List<ScmPurchaseContractDetail> detailList = contract.getScmPurchaseContractDetailList();
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
        return AjaxResult.success(contract);
    }

    /**
     * 新增采购合同
     */
    @ApiOperation("新增采购合同")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('purchase:contract:add')")
    @Log(title = "采购合同", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ScmPurchaseContract scmPurchaseContract) {
        scmPurchaseContract.setApplyUser(String.valueOf(this.getUserId()));
        scmPurchaseContract.setApplyTime(new Date());
        scmPurchaseContract.setApplyStatus(UserConstants.APPLY_STATUS_NO_SUBMIT);
        return toAjax(scmPurchaseContractService.insertScmPurchaseContract(scmPurchaseContract));
    }

    /**
     * 修改采购合同
     */
    @ApiOperation("修改采购合同")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('purchase:contract:edit')")
    @Log(title = "采购合同", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ScmPurchaseContract scmPurchaseContract) {
        scmPurchaseContract.setApplyStatus(UserConstants.APPLY_STATUS_NO_SUBMIT);
        scmPurchaseContract.setAuditUser("");
        scmPurchaseContract.setAuditStatus("");
        scmPurchaseContract.setAuditTime(null);
        return toAjax(scmPurchaseContractService.updateScmPurchaseContract(scmPurchaseContract));
    }

    /**
     * 删除采购合同
     */
    @ApiOperation("删除采购合同")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('purchase:contract:remove')")
    @Log(title = "采购合同", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(scmPurchaseContractService.deleteScmPurchaseContractByIds(ids));
    }

    @ApiOperation("提交采购计划")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('purchase:plan:edit')")
    @Log(title = "提交采购计划", businessType = BusinessType.UPDATE)
    @PutMapping("/submit")
    public AjaxResult submit(@RequestBody ScmPurchaseContract scmPurchaseContract) {
        scmPurchaseContract.setApplyStatus(UserConstants.APPLY_STATUS_WAIT_AUDIT);
        scmPurchaseContract.setApplyTime(DateUtils.getNowDate());
        if (scmPurchaseContract.getId() != null) {
            ScmPurchaseContract contract = scmPurchaseContractService.selectScmPurchaseContractById(scmPurchaseContract.getId());
            if (contract == null) {
                return AjaxResult.error("此采购合同不存在，不能提交！");
            }
            scmPurchaseContract.setAuditUser("");
            scmPurchaseContract.setAuditStatus("");
            scmPurchaseContract.setAuditTime(null);
            scmPurchaseContract.setAuditComment("");
            return toAjax(scmPurchaseContractService.updateScmPurchaseContract(scmPurchaseContract));
        } else {
            scmPurchaseContract.setApplyUser(String.valueOf(this.getUserId()));
            scmPurchaseContract.setApplyTime(new Date());
            return toAjax(scmPurchaseContractService.insertScmPurchaseContract(scmPurchaseContract));
        }
    }

    @ApiOperation("审核采购合同")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('purchase:contract:audit')")
    @Log(title = "采购合同审核", businessType = BusinessType.UPDATE)
    @PutMapping("/audit")
    public AjaxResult audit(@RequestBody ScmPurchaseContract scmPurchaseContract) {
        scmPurchaseContract.setApplyStatus(UserConstants.APPLY_STATUS_HAS_AUDIT);
        scmPurchaseContract.setAuditTime(DateUtils.getNowDate());
        scmPurchaseContract.setAuditUser(String.valueOf(this.getUserId()));
        return toAjax(scmPurchaseContractService.updateScmPurchaseContract(scmPurchaseContract));
    }

}
