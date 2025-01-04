package com.neu.carbon.mes.controller;

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
import com.neu.carbon.mes.domain.MesCheckStandard;
import com.neu.carbon.mes.service.IMesCheckStandardService;
import com.neu.common.utils.poi.ExcelUtil;
import com.neu.common.core.page.TableDataInfo;

/**
 * 质检标准Controller
 * 
 * @author neuedu
 * @date 2022-07-12
 */
@Api(tags = {"质检标准"})
@RestController
@RequestMapping("/mesCheck/standard")
public class MesCheckStandardController extends BaseController
{
    @Autowired
    private IMesCheckStandardService mesCheckStandardService;

    /**
     * 查询质检标准列表
     */
    @GetMapping("/list")
    @ApiOperation("查询质检标准列表")
    @DynamicResponseParameters(properties = {
	        @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = MesCheckStandard.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list(MesCheckStandard mesCheckStandard)
    {
        startPage();
        List<MesCheckStandard> list = mesCheckStandardService.selectMesCheckStandardList(mesCheckStandard);
        return getDataTable(list);
    }

    /**
     * 导出质检标准列表
     */
    @ApiOperation("导出质检标准列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "文件名")
    })
    @PreAuthorize("@ss.hasPermi('mesCheck:standard:export')")
    @Log(title = "质检标准", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(MesCheckStandard mesCheckStandard)
    {
        List<MesCheckStandard> list = mesCheckStandardService.selectMesCheckStandardList(mesCheckStandard);
        ExcelUtil<MesCheckStandard> util = new ExcelUtil<MesCheckStandard>(MesCheckStandard.class);
        return util.exportExcel(list, "standard");
    }

    /**
     * 获取质检标准详细信息
     */
    @ApiOperation("获取质检标准详细信息")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "data", value = "返回业务数据", dataTypeClass = MesCheckStandard.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(mesCheckStandardService.selectMesCheckStandardById(id));
    }

    /**
     * 新增质检标准
     */
    @ApiOperation("新增质检标准")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('mesCheck:standard:add')")
    @Log(title = "质检标准", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MesCheckStandard mesCheckStandard)
    {
        return toAjax(mesCheckStandardService.insertMesCheckStandard(mesCheckStandard));
    }

    /**
     * 修改质检标准
     */
    @ApiOperation("修改质检标准")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('mesCheck:standard:edit')")
    @Log(title = "质检标准", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MesCheckStandard mesCheckStandard)
    {
        return toAjax(mesCheckStandardService.updateMesCheckStandard(mesCheckStandard));
    }

    /**
     * 删除质检标准
     */
    @ApiOperation("删除质检标准")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('mesCheck:standard:remove')")
    @Log(title = "质检标准", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(mesCheckStandardService.deleteMesCheckStandardByIds(ids));
    }
}
