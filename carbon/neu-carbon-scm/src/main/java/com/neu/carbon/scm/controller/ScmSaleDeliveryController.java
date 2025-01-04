package com.neu.carbon.scm.controller;

import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicResponseParameters;
import com.neu.carbon.scm.domain.*;
import com.neu.carbon.scm.service.IScmSaleDeliveryService;
import com.neu.carbon.wms.domain.WmsMaterialInfo;
import com.neu.carbon.wms.service.IWmsMaterialInfoService;
import com.neu.common.annotation.Log;
import com.neu.common.core.controller.BaseController;
import com.neu.common.core.domain.AjaxResult;
import com.neu.common.core.page.TableDataInfo;
import com.neu.common.enums.BusinessType;
import com.neu.common.utils.SecurityUtils;
import com.neu.common.utils.poi.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 销售发货单Controller
 *
 * @author neuedu
 * @date 2022-07-05
 */
@Api(tags = {"销售发货单"})
@RestController
@RequestMapping("/sale/delivery")
public class ScmSaleDeliveryController extends BaseController {
    @Autowired
    private IScmSaleDeliveryService scmSaleDeliveryService;
    @Autowired
    private IWmsMaterialInfoService wmsMaterialInfoService;

    /**
     * 查询销售发货单列表
     */
    @GetMapping("/list")
    @ApiOperation("查询销售发货单列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = ScmSaleDelivery.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list(ScmSaleDelivery scmSaleDelivery) {
        Long userId = this.getUserId();
        if (!SecurityUtils.isAdmin(userId)) {
            scmSaleDelivery.setCreateBy(String.valueOf(userId));
        }
        startPage();
        List<ScmSaleDelivery> list = scmSaleDeliveryService.selectScmSaleDeliveryList(scmSaleDelivery);
        return getDataTable(list);
    }

    /**
     * 导出销售发货单列表
     */
    @ApiOperation("导出销售发货单列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "文件名")
    })
    @PreAuthorize("@ss.hasPermi('sale:delivery:export')")
    @Log(title = "销售发货单", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ScmSaleDelivery scmSaleDelivery) {
        List<ScmSaleDelivery> list = scmSaleDeliveryService.selectScmSaleDeliveryList(scmSaleDelivery);
        ExcelUtil<ScmSaleDelivery> util = new ExcelUtil<ScmSaleDelivery>(ScmSaleDelivery.class);
        return util.exportExcel(list, "delivery");
    }

    /**
     * 获取销售发货单详细信息
     */
    @ApiOperation("获取销售发货单详细信息")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "data", value = "返回业务数据", dataTypeClass = ScmSaleDelivery.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        ScmSaleDelivery delivery = scmSaleDeliveryService.selectScmSaleDeliveryById(id);
        List<ScmSaleDeliveryDetail> detailList = delivery.getScmSaleDeliveryDetailList();
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
        return AjaxResult.success(delivery);
    }

    /**
     * 新增销售发货单
     */
    @ApiOperation("新增销售发货单")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('sale:delivery:add')")
    @Log(title = "销售发货单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ScmSaleDelivery scmSaleDelivery) {
        return toAjax(scmSaleDeliveryService.insertScmSaleDelivery(scmSaleDelivery));
    }

    /**
     * 修改销售发货单
     */
    @ApiOperation("修改销售发货单")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('sale:delivery:edit')")
    @Log(title = "销售发货单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ScmSaleDelivery scmSaleDelivery) {
        return toAjax(scmSaleDeliveryService.updateScmSaleDelivery(scmSaleDelivery));
    }

    /**
     * 删除销售发货单
     */
    @ApiOperation("删除销售发货单")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('sale:delivery:remove')")
    @Log(title = "销售发货单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(scmSaleDeliveryService.deleteScmSaleDeliveryByIds(ids));
    }
}
