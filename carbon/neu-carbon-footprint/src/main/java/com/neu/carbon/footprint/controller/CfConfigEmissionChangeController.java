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
import com.neu.carbon.footprint.domain.CfConfigEmissionChange;
import com.neu.carbon.footprint.service.ICfConfigEmissionChangeService;
import com.neu.common.utils.poi.ExcelUtil;
import com.neu.common.core.page.TableDataInfo;

/**
 * 变化碳排放Controller
 * 
 * @author neuedu
 * @date 2022-07-22
 */
@Api(tags = {"变化碳排放"})
@RestController
@RequestMapping("/cfConfig/emissionChange")
public class CfConfigEmissionChangeController extends BaseController
{
    @Autowired
    private ICfConfigEmissionChangeService cfConfigEmissionChangeService;

    /**
     * 查询变化碳排放列表
     */
    @GetMapping("/list")
    @ApiOperation("查询变化碳排放列表")
    @DynamicResponseParameters(properties = {
	        @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = CfConfigEmissionChange.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list(CfConfigEmissionChange cfConfigEmissionChange)
    {
        startPage();
        List<CfConfigEmissionChange> list = cfConfigEmissionChangeService.selectCfConfigEmissionChangeList(cfConfigEmissionChange);
        return getDataTable(list);
    }

    /**
     * 导出变化碳排放列表
     */
    @ApiOperation("导出变化碳排放列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "文件名")
    })
    @PreAuthorize("@ss.hasPermi('cfConfig:emissionChange:export')")
    @Log(title = "变化碳排放", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(CfConfigEmissionChange cfConfigEmissionChange)
    {
        List<CfConfigEmissionChange> list = cfConfigEmissionChangeService.selectCfConfigEmissionChangeList(cfConfigEmissionChange);
        ExcelUtil<CfConfigEmissionChange> util = new ExcelUtil<CfConfigEmissionChange>(CfConfigEmissionChange.class);
        return util.exportExcel(list, "emissionChange");
    }

    /**
     * 获取变化碳排放详细信息
     */
    @ApiOperation("获取变化碳排放详细信息")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "data", value = "返回业务数据", dataTypeClass = CfConfigEmissionChange.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(cfConfigEmissionChangeService.selectCfConfigEmissionChangeById(id));
    }

    /**
     * 新增变化碳排放
     */
    @ApiOperation("新增变化碳排放")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('cfConfig:emissionChange:add')")
    @Log(title = "变化碳排放", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CfConfigEmissionChange cfConfigEmissionChange)
    {
        return toAjax(cfConfigEmissionChangeService.insertCfConfigEmissionChange(cfConfigEmissionChange));
    }

    /**
     * 修改变化碳排放
     */
    @ApiOperation("修改变化碳排放")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('cfConfig:emissionChange:edit')")
    @Log(title = "变化碳排放", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CfConfigEmissionChange cfConfigEmissionChange)
    {
        return toAjax(cfConfigEmissionChangeService.updateCfConfigEmissionChange(cfConfigEmissionChange));
    }

    /**
     * 删除变化碳排放
     */
    @ApiOperation("删除变化碳排放")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('cfConfig:emissionChange:remove')")
    @Log(title = "变化碳排放", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(cfConfigEmissionChangeService.deleteCfConfigEmissionChangeByIds(ids));
    }
}
