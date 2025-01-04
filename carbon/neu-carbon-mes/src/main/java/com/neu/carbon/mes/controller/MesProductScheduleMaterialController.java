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
import com.neu.carbon.mes.domain.MesProductScheduleMaterial;
import com.neu.carbon.mes.service.IMesProductScheduleMaterialService;
import com.neu.common.utils.poi.ExcelUtil;
import com.neu.common.core.page.TableDataInfo;

/**
 * 排产物料信息Controller
 * 
 * @author neuedu
 * @date 2022-07-13
 */
@Api(tags = {"排产物料信息"})
@RestController
@RequestMapping("/mesPlan/productScheduleMaterial")
public class MesProductScheduleMaterialController extends BaseController
{
    @Autowired
    private IMesProductScheduleMaterialService mesProductScheduleMaterialService;

    /**
     * 查询排产物料信息列表
     */
    @GetMapping("/list")
    @ApiOperation("查询排产物料信息列表")
    @DynamicResponseParameters(properties = {
	        @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = MesProductScheduleMaterial.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list(MesProductScheduleMaterial mesProductScheduleMaterial)
    {
        startPage();
        List<MesProductScheduleMaterial> list = mesProductScheduleMaterialService.selectMesProductScheduleMaterialList(mesProductScheduleMaterial);
        return getDataTable(list);
    }

    /**
     * 导出排产物料信息列表
     */
    @ApiOperation("导出排产物料信息列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "文件名")
    })
    @PreAuthorize("@ss.hasPermi('mesPlan:productScheduleMaterial:export')")
    @Log(title = "排产物料信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(MesProductScheduleMaterial mesProductScheduleMaterial)
    {
        List<MesProductScheduleMaterial> list = mesProductScheduleMaterialService.selectMesProductScheduleMaterialList(mesProductScheduleMaterial);
        ExcelUtil<MesProductScheduleMaterial> util = new ExcelUtil<MesProductScheduleMaterial>(MesProductScheduleMaterial.class);
        return util.exportExcel(list, "productScheduleMaterial");
    }

    /**
     * 获取排产物料信息详细信息
     */
    @ApiOperation("获取排产物料信息详细信息")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "data", value = "返回业务数据", dataTypeClass = MesProductScheduleMaterial.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(mesProductScheduleMaterialService.selectMesProductScheduleMaterialById(id));
    }

    /**
     * 新增排产物料信息
     */
    @ApiOperation("新增排产物料信息")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('mesPlan:productScheduleMaterial:add')")
    @Log(title = "排产物料信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MesProductScheduleMaterial mesProductScheduleMaterial)
    {
        return toAjax(mesProductScheduleMaterialService.insertMesProductScheduleMaterial(mesProductScheduleMaterial));
    }

    /**
     * 修改排产物料信息
     */
    @ApiOperation("修改排产物料信息")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('mesPlan:productScheduleMaterial:edit')")
    @Log(title = "排产物料信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MesProductScheduleMaterial mesProductScheduleMaterial)
    {
        return toAjax(mesProductScheduleMaterialService.updateMesProductScheduleMaterial(mesProductScheduleMaterial));
    }

    /**
     * 删除排产物料信息
     */
    @ApiOperation("删除排产物料信息")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('mesPlan:productScheduleMaterial:remove')")
    @Log(title = "排产物料信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(mesProductScheduleMaterialService.deleteMesProductScheduleMaterialByIds(ids));
    }
}
