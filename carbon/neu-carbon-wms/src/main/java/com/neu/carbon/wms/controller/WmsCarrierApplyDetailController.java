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
import com.neu.carbon.wms.domain.WmsCarrierApplyDetail;
import com.neu.carbon.wms.service.IWmsCarrierApplyDetailService;
import com.neu.common.utils.poi.ExcelUtil;
import com.neu.common.core.page.TableDataInfo;

/**
 * 产品承运申请明细Controller
 * 
 * @author neuedu
 * @date 2022-07-01
 */
@Api(tags = {"产品承运申请明细"})
@RestController
@RequestMapping("/transportApply/carrierApplyDetail")
public class WmsCarrierApplyDetailController extends BaseController
{
    @Autowired
    private IWmsCarrierApplyDetailService wmsCarrierApplyDetailService;

    /**
     * 查询产品承运申请明细列表
     */
    @GetMapping("/list")
    @ApiOperation("查询产品承运申请明细列表")
    @DynamicResponseParameters(properties = {
	        @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = WmsCarrierApplyDetail.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list(WmsCarrierApplyDetail wmsCarrierApplyDetail)
    {
        startPage();
        List<WmsCarrierApplyDetail> list = wmsCarrierApplyDetailService.selectWmsCarrierApplyDetailList(wmsCarrierApplyDetail);
        return getDataTable(list);
    }

    /**
     * 导出产品承运申请明细列表
     */
    @ApiOperation("导出产品承运申请明细列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "文件名")
    })
    @PreAuthorize("@ss.hasPermi('transportApply:carrierApplyDetail:export')")
    @Log(title = "产品承运申请明细", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(WmsCarrierApplyDetail wmsCarrierApplyDetail)
    {
        List<WmsCarrierApplyDetail> list = wmsCarrierApplyDetailService.selectWmsCarrierApplyDetailList(wmsCarrierApplyDetail);
        ExcelUtil<WmsCarrierApplyDetail> util = new ExcelUtil<WmsCarrierApplyDetail>(WmsCarrierApplyDetail.class);
        return util.exportExcel(list, "carrierApplyDetail");
    }

    /**
     * 获取产品承运申请明细详细信息
     */
    @ApiOperation("获取产品承运申请明细详细信息")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "data", value = "返回业务数据", dataTypeClass = WmsCarrierApplyDetail.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(wmsCarrierApplyDetailService.selectWmsCarrierApplyDetailById(id));
    }

    /**
     * 新增产品承运申请明细
     */
    @ApiOperation("新增产品承运申请明细")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('transportApply:carrierApplyDetail:add')")
    @Log(title = "产品承运申请明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmsCarrierApplyDetail wmsCarrierApplyDetail)
    {
        return toAjax(wmsCarrierApplyDetailService.insertWmsCarrierApplyDetail(wmsCarrierApplyDetail));
    }

    /**
     * 修改产品承运申请明细
     */
    @ApiOperation("修改产品承运申请明细")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('transportApply:carrierApplyDetail:edit')")
    @Log(title = "产品承运申请明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmsCarrierApplyDetail wmsCarrierApplyDetail)
    {
        return toAjax(wmsCarrierApplyDetailService.updateWmsCarrierApplyDetail(wmsCarrierApplyDetail));
    }

    /**
     * 删除产品承运申请明细
     */
    @ApiOperation("删除产品承运申请明细")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('transportApply:carrierApplyDetail:remove')")
    @Log(title = "产品承运申请明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(wmsCarrierApplyDetailService.deleteWmsCarrierApplyDetailByIds(ids));
    }
}
