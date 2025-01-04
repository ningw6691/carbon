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
import com.neu.carbon.wms.domain.WmsInWarehouseApplyDetail;
import com.neu.carbon.wms.service.IWmsInWarehouseApplyDetailService;
import com.neu.common.utils.poi.ExcelUtil;
import com.neu.common.core.page.TableDataInfo;

/**
 * 入库申请明细Controller
 * 
 * @author neusoft
 * @date 2022-06-27
 */
@Api(tags = {"入库申请明细"})
@RestController
@RequestMapping("/wmsApply/inWarehouseApplyDetail")
public class WmsInWarehouseApplyDetailController extends BaseController
{
    @Autowired
    private IWmsInWarehouseApplyDetailService wmsInWarehouseApplyDetailService;

    /**
     * 查询入库申请明细列表
     */
    @PreAuthorize("@ss.hasPermi('wmsApply:inWarehouseApplyDetail:list')")
    @GetMapping("/list")
    @ApiOperation("查询入库申请明细列表")
    @DynamicResponseParameters(properties = {
	        @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = WmsInWarehouseApplyDetail.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list(WmsInWarehouseApplyDetail wmsInWarehouseApplyDetail)
    {
        startPage();
        List<WmsInWarehouseApplyDetail> list = wmsInWarehouseApplyDetailService.selectWmsInWarehouseApplyDetailList(wmsInWarehouseApplyDetail);
        return getDataTable(list);
    }

    /**
     * 导出入库申请明细列表
     */
    @ApiOperation("导出入库申请明细列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "文件名")
    })
    @PreAuthorize("@ss.hasPermi('wmsApply:inWarehouseApplyDetail:export')")
    @Log(title = "入库申请明细", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(WmsInWarehouseApplyDetail wmsInWarehouseApplyDetail)
    {
        List<WmsInWarehouseApplyDetail> list = wmsInWarehouseApplyDetailService.selectWmsInWarehouseApplyDetailList(wmsInWarehouseApplyDetail);
        ExcelUtil<WmsInWarehouseApplyDetail> util = new ExcelUtil<WmsInWarehouseApplyDetail>(WmsInWarehouseApplyDetail.class);
        return util.exportExcel(list, "inWarehouseApplyDetail");
    }

    /**
     * 获取入库申请明细详细信息
     */
    @ApiOperation("获取入库申请明细详细信息")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "data", value = "返回业务数据", dataTypeClass = WmsInWarehouseApplyDetail.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('wmsApply:inWarehouseApplyDetail:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(wmsInWarehouseApplyDetailService.selectWmsInWarehouseApplyDetailById(id));
    }

    /**
     * 新增入库申请明细
     */
    @ApiOperation("新增入库申请明细")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('wmsApply:inWarehouseApplyDetail:add')")
    @Log(title = "入库申请明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmsInWarehouseApplyDetail wmsInWarehouseApplyDetail)
    {
        return toAjax(wmsInWarehouseApplyDetailService.insertWmsInWarehouseApplyDetail(wmsInWarehouseApplyDetail));
    }

    /**
     * 修改入库申请明细
     */
    @ApiOperation("修改入库申请明细")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('wmsApply:inWarehouseApplyDetail:edit')")
    @Log(title = "入库申请明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmsInWarehouseApplyDetail wmsInWarehouseApplyDetail)
    {
        return toAjax(wmsInWarehouseApplyDetailService.updateWmsInWarehouseApplyDetail(wmsInWarehouseApplyDetail));
    }

    /**
     * 删除入库申请明细
     */
    @ApiOperation("删除入库申请明细")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('wmsApply:inWarehouseApplyDetail:remove')")
    @Log(title = "入库申请明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(wmsInWarehouseApplyDetailService.deleteWmsInWarehouseApplyDetailByIds(ids));
    }
}
