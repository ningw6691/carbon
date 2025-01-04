package com.neu.carbon.wms.controller;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicResponseParameters;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.neu.common.annotation.Log;
import com.neu.common.core.controller.BaseController;
import com.neu.common.core.domain.AjaxResult;
import com.neu.common.enums.BusinessType;
import com.neu.carbon.wms.domain.WmsDeliveryBillDetail;
import com.neu.carbon.wms.service.IWmsDeliveryBillDetailService;
import com.neu.common.utils.poi.ExcelUtil;
import com.neu.common.core.page.TableDataInfo;

/**
 * 配送单明细Controller
 * 
 * @author neuedu
 * @date 2022-07-05
 */
@Api(tags = {"配送单明细"})
@RestController
@RequestMapping("/transportApply/deliveryBillDetail")
public class WmsDeliveryBillDetailController extends BaseController
{
    @Autowired
    private IWmsDeliveryBillDetailService wmsDeliveryBillDetailService;

    /**
     * 查询配送单明细列表
     */
    @GetMapping("/list")
    @ApiOperation("查询配送单明细列表")
    @DynamicResponseParameters(properties = {
	        @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = WmsDeliveryBillDetail.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list(WmsDeliveryBillDetail wmsDeliveryBillDetail)
    {
        startPage();
        List<WmsDeliveryBillDetail> list = wmsDeliveryBillDetailService.selectWmsDeliveryBillDetailList(wmsDeliveryBillDetail);
        return getDataTable(list);
    }

    /**
     * 导出配送单明细列表
     */
    @ApiOperation("导出配送单明细列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "文件名")
    })
    @PreAuthorize("@ss.hasPermi('transportApply:deliveryBillDetail:export')")
    @Log(title = "配送单明细", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(WmsDeliveryBillDetail wmsDeliveryBillDetail)
    {
        List<WmsDeliveryBillDetail> list = wmsDeliveryBillDetailService.selectWmsDeliveryBillDetailList(wmsDeliveryBillDetail);
        ExcelUtil<WmsDeliveryBillDetail> util = new ExcelUtil<WmsDeliveryBillDetail>(WmsDeliveryBillDetail.class);
        return util.exportExcel(list, "deliveryBillDetail");
    }

    /**
     * 获取配送单明细详细信息
     */
    @ApiOperation("获取配送单明细详细信息")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "data", value = "返回业务数据", dataTypeClass = WmsDeliveryBillDetail.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(wmsDeliveryBillDetailService.selectWmsDeliveryBillDetailById(id));
    }

    /**
     * 新增配送单明细
     */
    @ApiOperation("新增配送单明细")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('transportApply:deliveryBillDetail:add')")
    @Log(title = "配送单明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmsDeliveryBillDetail wmsDeliveryBillDetail)
    {
        return toAjax(wmsDeliveryBillDetailService.insertWmsDeliveryBillDetail(wmsDeliveryBillDetail));
    }

    /**
     * 修改配送单明细
     */
    @ApiOperation("修改配送单明细")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('transportApply:deliveryBillDetail:edit')")
    @Log(title = "配送单明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmsDeliveryBillDetail wmsDeliveryBillDetail)
    {
        return toAjax(wmsDeliveryBillDetailService.updateWmsDeliveryBillDetail(wmsDeliveryBillDetail));
    }

    /**
     * 删除配送单明细
     */
    @ApiOperation("删除配送单明细")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('transportApply:deliveryBillDetail:remove')")
    @Log(title = "配送单明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(wmsDeliveryBillDetailService.deleteWmsDeliveryBillDetailByIds(ids));
    }
}
