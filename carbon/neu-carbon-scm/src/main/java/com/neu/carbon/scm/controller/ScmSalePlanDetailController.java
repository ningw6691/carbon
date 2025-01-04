package com.neu.carbon.scm.controller;

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
import com.neu.carbon.scm.domain.ScmSalePlanDetail;
import com.neu.carbon.scm.service.IScmSalePlanDetailService;
import com.neu.common.utils.poi.ExcelUtil;
import com.neu.common.core.page.TableDataInfo;

/**
 * 指标配置Controller
 * 
 * @author neuedu
 * @date 2022-07-13
 */
@Api(tags = {"指标配置"})
@RestController
@RequestMapping("/sale/planDetail")
public class ScmSalePlanDetailController extends BaseController
{
    @Autowired
    private IScmSalePlanDetailService scmSalePlanDetailService;

    /**
     * 查询指标配置列表
     */
    @GetMapping("/list")
    @ApiOperation("查询指标配置列表")
    @DynamicResponseParameters(properties = {
	        @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = ScmSalePlanDetail.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list(ScmSalePlanDetail scmSalePlanDetail)
    {
        startPage();
        List<ScmSalePlanDetail> list = scmSalePlanDetailService.selectScmSalePlanDetailList(scmSalePlanDetail);
        return getDataTable(list);
    }

    /**
     * 导出指标配置列表
     */
    @ApiOperation("导出指标配置列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "文件名")
    })
    @PreAuthorize("@ss.hasPermi('sale:planDetail:export')")
    @Log(title = "指标配置", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ScmSalePlanDetail scmSalePlanDetail)
    {
        List<ScmSalePlanDetail> list = scmSalePlanDetailService.selectScmSalePlanDetailList(scmSalePlanDetail);
        ExcelUtil<ScmSalePlanDetail> util = new ExcelUtil<ScmSalePlanDetail>(ScmSalePlanDetail.class);
        return util.exportExcel(list, "planDetail");
    }

    /**
     * 获取指标配置详细信息
     */
    @ApiOperation("获取指标配置详细信息")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "data", value = "返回业务数据", dataTypeClass = ScmSalePlanDetail.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(scmSalePlanDetailService.selectScmSalePlanDetailById(id));
    }

    /**
     * 新增指标配置
     */
    @ApiOperation("新增指标配置")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('sale:planDetail:add')")
    @Log(title = "指标配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ScmSalePlanDetail scmSalePlanDetail)
    {
        return toAjax(scmSalePlanDetailService.insertScmSalePlanDetail(scmSalePlanDetail));
    }

    /**
     * 修改指标配置
     */
    @ApiOperation("修改指标配置")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('sale:planDetail:edit')")
    @Log(title = "指标配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ScmSalePlanDetail scmSalePlanDetail)
    {
        return toAjax(scmSalePlanDetailService.updateScmSalePlanDetail(scmSalePlanDetail));
    }

    /**
     * 删除指标配置
     */
    @ApiOperation("删除指标配置")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('sale:planDetail:remove')")
    @Log(title = "指标配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(scmSalePlanDetailService.deleteScmSalePlanDetailByIds(ids));
    }
}
