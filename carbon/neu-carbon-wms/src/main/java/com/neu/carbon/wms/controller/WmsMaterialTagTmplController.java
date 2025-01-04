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
import com.neu.carbon.wms.domain.WmsMaterialTagTmpl;
import com.neu.carbon.wms.service.IWmsMaterialTagTmplService;
import com.neu.common.utils.poi.ExcelUtil;
import com.neu.common.core.page.TableDataInfo;

/**
 * 物料标签模板Controller
 * 
 * @author neuedu
 * @date 2022-07-07
 */
@Api(tags = {"物料标签模板"})
@RestController
@RequestMapping("/material/materialTag")
public class WmsMaterialTagTmplController extends BaseController
{
    @Autowired
    private IWmsMaterialTagTmplService wmsMaterialTagTmplService;

    /**
     * 查询物料标签模板列表
     */
    @GetMapping("/list")
    @ApiOperation("查询物料标签模板列表")
    @DynamicResponseParameters(properties = {
	        @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = WmsMaterialTagTmpl.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list(WmsMaterialTagTmpl wmsMaterialTagTmpl)
    {
        startPage();
        List<WmsMaterialTagTmpl> list = wmsMaterialTagTmplService.selectWmsMaterialTagTmplList(wmsMaterialTagTmpl);
        return getDataTable(list);
    }

    /**
     * 导出物料标签模板列表
     */
    @ApiOperation("导出物料标签模板列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "文件名")
    })
    @PreAuthorize("@ss.hasPermi('material:materialTag:export')")
    @Log(title = "物料标签模板", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(WmsMaterialTagTmpl wmsMaterialTagTmpl)
    {
        List<WmsMaterialTagTmpl> list = wmsMaterialTagTmplService.selectWmsMaterialTagTmplList(wmsMaterialTagTmpl);
        ExcelUtil<WmsMaterialTagTmpl> util = new ExcelUtil<WmsMaterialTagTmpl>(WmsMaterialTagTmpl.class);
        return util.exportExcel(list, "materialTag");
    }

    /**
     * 获取物料标签模板详细信息
     */
    @ApiOperation("获取物料标签模板详细信息")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "data", value = "返回业务数据", dataTypeClass = WmsMaterialTagTmpl.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(wmsMaterialTagTmplService.selectWmsMaterialTagTmplById(id));
    }

    /**
     * 新增物料标签模板
     */
    @ApiOperation("新增物料标签模板")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('material:materialTag:add')")
    @Log(title = "物料标签模板", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmsMaterialTagTmpl wmsMaterialTagTmpl)
    {
        return toAjax(wmsMaterialTagTmplService.insertWmsMaterialTagTmpl(wmsMaterialTagTmpl));
    }

    /**
     * 修改物料标签模板
     */
    @ApiOperation("修改物料标签模板")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('material:materialTag:edit')")
    @Log(title = "物料标签模板", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmsMaterialTagTmpl wmsMaterialTagTmpl)
    {
        return toAjax(wmsMaterialTagTmplService.updateWmsMaterialTagTmpl(wmsMaterialTagTmpl));
    }

    /**
     * 删除物料标签模板
     */
    @ApiOperation("删除物料标签模板")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('material:materialTag:remove')")
    @Log(title = "物料标签模板", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(wmsMaterialTagTmplService.deleteWmsMaterialTagTmplByIds(ids));
    }
}
