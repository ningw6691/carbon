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
import com.neu.carbon.wms.domain.WmsReplenishApplyDetail;
import com.neu.carbon.wms.service.IWmsReplenishApplyDetailService;
import com.neu.common.utils.poi.ExcelUtil;
import com.neu.common.core.page.TableDataInfo;

/**
 * 补货申请明细Controller
 * 
 * @author neuedu
 * @date 2022-07-20
 */
@Api(tags = {"补货申请明细"})
@RestController
@RequestMapping("/wmsApply/replenishApplyDetail")
public class WmsReplenishApplyDetailController extends BaseController
{
    @Autowired
    private IWmsReplenishApplyDetailService wmsReplenishApplyDetailService;

    /**
     * 查询补货申请明细列表
     */
    @GetMapping("/list")
    @ApiOperation("查询补货申请明细列表")
    @DynamicResponseParameters(properties = {
	        @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = WmsReplenishApplyDetail.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list(WmsReplenishApplyDetail wmsReplenishApplyDetail)
    {
        startPage();
        List<WmsReplenishApplyDetail> list = wmsReplenishApplyDetailService.selectWmsReplenishApplyDetailList(wmsReplenishApplyDetail);
        return getDataTable(list);
    }

    /**
     * 导出补货申请明细列表
     */
    @ApiOperation("导出补货申请明细列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "文件名")
    })
    @PreAuthorize("@ss.hasPermi('wmsApply:replenishApplyDetail:export')")
    @Log(title = "补货申请明细", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(WmsReplenishApplyDetail wmsReplenishApplyDetail)
    {
        List<WmsReplenishApplyDetail> list = wmsReplenishApplyDetailService.selectWmsReplenishApplyDetailList(wmsReplenishApplyDetail);
        ExcelUtil<WmsReplenishApplyDetail> util = new ExcelUtil<WmsReplenishApplyDetail>(WmsReplenishApplyDetail.class);
        return util.exportExcel(list, "replenishApplyDetail");
    }

    /**
     * 获取补货申请明细详细信息
     */
    @ApiOperation("获取补货申请明细详细信息")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "data", value = "返回业务数据", dataTypeClass = WmsReplenishApplyDetail.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(wmsReplenishApplyDetailService.selectWmsReplenishApplyDetailById(id));
    }

    /**
     * 新增补货申请明细
     */
    @ApiOperation("新增补货申请明细")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('wmsApply:replenishApplyDetail:add')")
    @Log(title = "补货申请明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmsReplenishApplyDetail wmsReplenishApplyDetail)
    {
        return toAjax(wmsReplenishApplyDetailService.insertWmsReplenishApplyDetail(wmsReplenishApplyDetail));
    }

    /**
     * 修改补货申请明细
     */
    @ApiOperation("修改补货申请明细")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('wmsApply:replenishApplyDetail:edit')")
    @Log(title = "补货申请明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmsReplenishApplyDetail wmsReplenishApplyDetail)
    {
        return toAjax(wmsReplenishApplyDetailService.updateWmsReplenishApplyDetail(wmsReplenishApplyDetail));
    }

    /**
     * 删除补货申请明细
     */
    @ApiOperation("删除补货申请明细")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('wmsApply:replenishApplyDetail:remove')")
    @Log(title = "补货申请明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(wmsReplenishApplyDetailService.deleteWmsReplenishApplyDetailByIds(ids));
    }
}
