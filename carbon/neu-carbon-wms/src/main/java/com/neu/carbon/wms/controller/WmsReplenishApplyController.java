package com.neu.carbon.wms.controller;

import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicResponseParameters;
import com.neu.carbon.wms.domain.WmsMaterialInfo;
import com.neu.carbon.wms.domain.WmsOutWarehouseApply;
import com.neu.carbon.wms.domain.WmsReplenishApply;
import com.neu.carbon.wms.domain.WmsReplenishApplyDetail;
import com.neu.carbon.wms.service.IWmsMaterialInfoService;
import com.neu.carbon.wms.service.IWmsReplenishApplyService;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 补货申请Controller
 *
 * @author neuedu
 * @date 2022-07-20
 */
@Api(tags = {"补货申请"})
@RestController
@RequestMapping("/wmsApply/replenishApply")
public class WmsReplenishApplyController extends BaseController {
    @Autowired
    private IWmsReplenishApplyService wmsReplenishApplyService;
    @Autowired
    private IWmsMaterialInfoService wmsMaterialInfoService;

    /**
     * 查询补货申请列表
     */
    @GetMapping("/list")
    @ApiOperation("查询补货申请列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = WmsReplenishApply.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list(WmsReplenishApply wmsReplenishApply) {
        startPage();
        Long loginUserId = SecurityUtils.getLoginUser().getUser().getUserId();
        if (!SecurityUtils.isAdmin(loginUserId)) {
            wmsReplenishApply.setApplyUser(String.valueOf(loginUserId));
        }
        List<WmsReplenishApply> list = wmsReplenishApplyService.selectWmsReplenishApplyList(wmsReplenishApply);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('wmsApply:replenishApply:audit')")
    @GetMapping("/audit/list")
    @ApiOperation("查询待审核和已审核补货申请列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = WmsOutWarehouseApply.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list4Audit(WmsReplenishApply wmsReplenishApply) {
        startPage();
        //默认查询待审核
        List<String> applyStatusList = new ArrayList<String>();
        applyStatusList.add(UserConstants.APPLY_STATUS_WAIT_AUDIT);
        applyStatusList.add(UserConstants.APPLY_STATUS_HAS_AUDIT);
        wmsReplenishApply.getParams().put("applyStatusList", applyStatusList);
        if (StringUtils.isBlank(wmsReplenishApply.getApplyStatus()) && StringUtils.isBlank(wmsReplenishApply.getAuditStatus())) {
            wmsReplenishApply.setApplyStatus(UserConstants.APPLY_STATUS_WAIT_AUDIT);
        }
        List<WmsReplenishApply> list = wmsReplenishApplyService.selectWmsReplenishApplyList(wmsReplenishApply);
        return getDataTable(list);
    }

    /**
     * 导出补货申请列表
     */
    @ApiOperation("导出补货申请列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "文件名")
    })
    @PreAuthorize("@ss.hasPermi('wmsApply:replenishApply:export')")
    @Log(title = "补货申请", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(WmsReplenishApply wmsReplenishApply) {
        List<WmsReplenishApply> list = wmsReplenishApplyService.selectWmsReplenishApplyList(wmsReplenishApply);
        ExcelUtil<WmsReplenishApply> util = new ExcelUtil<WmsReplenishApply>(WmsReplenishApply.class);
        return util.exportExcel(list, "replenishApply");
    }

    /**
     * 获取补货申请详细信息
     */
    @ApiOperation("获取补货申请详细信息")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "data", value = "返回业务数据", dataTypeClass = WmsReplenishApply.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        WmsReplenishApply apply = wmsReplenishApplyService.selectWmsReplenishApplyById(id);
        List<WmsReplenishApplyDetail> applyDetail = apply.getWmsReplenishApplyDetailList();
        if (applyDetail != null && !applyDetail.isEmpty()) {
            //获取物料档案信息
            applyDetail.stream().forEach(detail -> {
                WmsMaterialInfo material = wmsMaterialInfoService.selectWmsMaterialInfoById(detail.getMaterialId());
                if (material != null) {
                    detail.setMaterialCode(material.getCode());
                    detail.setMaterialModel(material.getModel());
                    detail.setMaterialName(material.getName());
                    detail.setMaterialSpecification(material.getSpecification());
                    detail.setMaterialUnit(material.getUnit());
                }
            });
        }
        return AjaxResult.success(apply);
    }

    /**
     * 新增补货申请
     */
    @ApiOperation("新增补货申请")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('wmsApply:replenishApply:add')")
    @Log(title = "补货申请", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmsReplenishApply wmsReplenishApply) {
        wmsReplenishApply.setApplyUser(String.valueOf(this.getUserId()));
        wmsReplenishApply.setApplyStatus(UserConstants.APPLY_STATUS_NO_SUBMIT);
        wmsReplenishApply.setApplyTime(DateUtils.getNowDate());
        return toAjax(wmsReplenishApplyService.insertWmsReplenishApply(wmsReplenishApply));
    }

    /**
     * 修改补货申请
     */
    @ApiOperation("修改补货申请")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('wmsApply:replenishApply:edit')")
    @Log(title = "补货申请", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmsReplenishApply wmsReplenishApply) {
        wmsReplenishApply.setApplyStatus(UserConstants.APPLY_STATUS_NO_SUBMIT);
        wmsReplenishApply.setAuditUser("");
        wmsReplenishApply.setAuditStatus("");
        wmsReplenishApply.setAuditTime(null);
        wmsReplenishApply.setAuditComment("");
        return toAjax(wmsReplenishApplyService.updateWmsReplenishApply(wmsReplenishApply));
    }

    @ApiOperation("提交补货申请")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('wmsApply:replenishApply:edit')")
    @Log(title = "补货申请", businessType = BusinessType.UPDATE)
    @PutMapping("/submit")
    public AjaxResult submit(@RequestBody WmsReplenishApply wmsReplenishApply) {
        wmsReplenishApply.setApplyStatus(UserConstants.APPLY_STATUS_WAIT_AUDIT);
        wmsReplenishApply.setApplyTime(DateUtils.getNowDate());
        if (wmsReplenishApply.getId() != null) {
            WmsReplenishApply apply = wmsReplenishApplyService.selectWmsReplenishApplyById(wmsReplenishApply.getId());
            if (apply == null) {
                return AjaxResult.error("此入库申请不存在，不能提交！");
            }
            wmsReplenishApply.setAuditUser("");
            wmsReplenishApply.setAuditStatus("");
            wmsReplenishApply.setAuditTime(null);
            wmsReplenishApply.setAuditComment("");
            return toAjax(wmsReplenishApplyService.updateWmsReplenishApply(wmsReplenishApply));
        } else {
            Long loginUserId = SecurityUtils.getLoginUser().getUser().getUserId();
            wmsReplenishApply.setApplyUser(String.valueOf(loginUserId));
            return toAjax(wmsReplenishApplyService.insertWmsReplenishApply(wmsReplenishApply));
        }
    }

    @ApiOperation("审核补货申请")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('wmsApply:replenishApply:audit')")
    @Log(title = "补货申请", businessType = BusinessType.UPDATE)
    @PutMapping("/audit")
    public AjaxResult audit(@RequestBody WmsReplenishApply wmsReplenishApply) {
        wmsReplenishApply.setApplyStatus(UserConstants.APPLY_STATUS_HAS_AUDIT);
        wmsReplenishApply.setAuditTime(DateUtils.getNowDate());
        Long loginUserId = SecurityUtils.getLoginUser().getUser().getUserId();
        wmsReplenishApply.setAuditUser(String.valueOf(loginUserId));
        return toAjax(wmsReplenishApplyService.updateWmsReplenishApply(wmsReplenishApply));
    }


    /**
     * 删除补货申请
     */
    @ApiOperation("删除补货申请")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('wmsApply:replenishApply:remove')")
    @Log(title = "补货申请", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(wmsReplenishApplyService.deleteWmsReplenishApplyByIds(ids));
    }


    @ApiOperation("生成采购申请")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('wmsApply:replenishApply:edit')")
    @Log(title = "生成采购申请", businessType = BusinessType.INSERT)
    @PostMapping("/genPurchaseApply")
    @Transactional
    public AjaxResult genPurchaseApply(@RequestBody WmsReplenishApply wmsReplenishApply) {
        return toAjax(wmsReplenishApplyService.genPurchaseApply(wmsReplenishApply.getId()));
    }
}
