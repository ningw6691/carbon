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
import com.neu.carbon.wms.domain.WmsPurchaseOwner;
import com.neu.carbon.wms.service.IWmsPurchaseOwnerService;
import com.neu.common.utils.poi.ExcelUtil;
import com.neu.common.core.page.TableDataInfo;

/**
 * 货主信息Controller
 * 
 * @author neuedu
 * @date 2023-05-10
 */
@Api(tags = {"货主信息"})
@RestController
@RequestMapping("/wms/owner")
public class WmsPurchaseOwnerController extends BaseController
{
    @Autowired
    private IWmsPurchaseOwnerService wmsPurchaseOwnerService;

    /**
     * 查询货主信息列表
     */
    @GetMapping("/list")
    @ApiOperation("查询货主信息列表")
    @DynamicResponseParameters(properties = {
	        @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = WmsPurchaseOwner.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list(WmsPurchaseOwner wmsPurchaseOwner)
    {
        startPage();
        List<WmsPurchaseOwner> list = wmsPurchaseOwnerService.selectWmsPurchaseOwnerList(wmsPurchaseOwner);
        return getDataTable(list);
    }

    /**
     * 导出货主信息列表
     */
    @ApiOperation("导出货主信息列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "文件名")
    })
    @PreAuthorize("@ss.hasPermi('wms:owner:export')")
    @Log(title = "货主信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(WmsPurchaseOwner wmsPurchaseOwner)
    {
        List<WmsPurchaseOwner> list = wmsPurchaseOwnerService.selectWmsPurchaseOwnerList(wmsPurchaseOwner);
        ExcelUtil<WmsPurchaseOwner> util = new ExcelUtil<WmsPurchaseOwner>(WmsPurchaseOwner.class);
        return util.exportExcel(list, "owner");
    }

    /**
     * 获取货主信息详细信息
     */
    @ApiOperation("获取货主信息详细信息")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "data", value = "返回业务数据", dataTypeClass = WmsPurchaseOwner.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(wmsPurchaseOwnerService.selectWmsPurchaseOwnerById(id));
    }

    /**
     * 新增货主信息
     */
    @ApiOperation("新增货主信息")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('wms:owner:add')")
    @Log(title = "货主信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmsPurchaseOwner wmsPurchaseOwner)
    {
        return toAjax(wmsPurchaseOwnerService.insertWmsPurchaseOwner(wmsPurchaseOwner));
    }

    /**
     * 修改货主信息
     */
    @ApiOperation("修改货主信息")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('wms:owner:edit')")
    @Log(title = "货主信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmsPurchaseOwner wmsPurchaseOwner)
    {
        return toAjax(wmsPurchaseOwnerService.updateWmsPurchaseOwner(wmsPurchaseOwner));
    }

    /**
     * 删除货主信息
     */
    @ApiOperation("删除货主信息")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('wms:owner:remove')")
    @Log(title = "货主信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(wmsPurchaseOwnerService.deleteWmsPurchaseOwnerByIds(ids));
    }
}
