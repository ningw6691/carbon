package com.neu.carbon.footprint.controller;

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
import com.neu.carbon.footprint.domain.CfConfigNeutral;
import com.neu.carbon.footprint.service.ICfConfigNeutralService;
import com.neu.common.utils.poi.ExcelUtil;
import com.neu.common.core.page.TableDataInfo;

/**
 * 碳中和计算比例Controller
 * 
 * @author neuedu
 * @date 2022-07-22
 */
@Api(tags = {"碳中和计算比例"})
@RestController
@RequestMapping("/cfConfig/neutral")
public class CfConfigNeutralController extends BaseController
{
    @Autowired
    private ICfConfigNeutralService cfConfigNeutralService;

    /**
     * 查询碳中和计算比例列表
     */
    @GetMapping("/list")
    @ApiOperation("查询碳中和计算比例列表")
    @DynamicResponseParameters(properties = {
	        @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = CfConfigNeutral.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list(CfConfigNeutral cfConfigNeutral)
    {
        startPage();
        List<CfConfigNeutral> list = cfConfigNeutralService.selectCfConfigNeutralList(cfConfigNeutral);
        return getDataTable(list);
    }

    /**
     * 导出碳中和计算比例列表
     */
    @ApiOperation("导出碳中和计算比例列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "文件名")
    })
    @PreAuthorize("@ss.hasPermi('cfConfig:neutral:export')")
    @Log(title = "碳中和计算比例", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(CfConfigNeutral cfConfigNeutral)
    {
        List<CfConfigNeutral> list = cfConfigNeutralService.selectCfConfigNeutralList(cfConfigNeutral);
        ExcelUtil<CfConfigNeutral> util = new ExcelUtil<CfConfigNeutral>(CfConfigNeutral.class);
        return util.exportExcel(list, "neutral");
    }

    /**
     * 获取碳中和计算比例详细信息
     */
    @ApiOperation("获取碳中和计算比例详细信息")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "data", value = "返回业务数据", dataTypeClass = CfConfigNeutral.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(cfConfigNeutralService.selectCfConfigNeutralById(id));
    }

    /**
     * 新增碳中和计算比例
     */
    @ApiOperation("新增碳中和计算比例")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('cfConfig:neutral:add')")
    @Log(title = "碳中和计算比例", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CfConfigNeutral cfConfigNeutral)
    {
        return toAjax(cfConfigNeutralService.insertCfConfigNeutral(cfConfigNeutral));
    }

    /**
     * 修改碳中和计算比例
     */
    @ApiOperation("修改碳中和计算比例")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('cfConfig:neutral:edit')")
    @Log(title = "碳中和计算比例", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CfConfigNeutral cfConfigNeutral)
    {
        return toAjax(cfConfigNeutralService.updateCfConfigNeutral(cfConfigNeutral));
    }

    /**
     * 删除碳中和计算比例
     */
    @ApiOperation("删除碳中和计算比例")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('cfConfig:neutral:remove')")
    @Log(title = "碳中和计算比例", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(cfConfigNeutralService.deleteCfConfigNeutralByIds(ids));
    }
}
