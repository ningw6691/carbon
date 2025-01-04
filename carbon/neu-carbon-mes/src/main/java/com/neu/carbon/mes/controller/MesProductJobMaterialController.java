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
import com.neu.carbon.mes.domain.MesProductJobMaterial;
import com.neu.carbon.mes.service.IMesProductJobMaterialService;
import com.neu.common.utils.poi.ExcelUtil;
import com.neu.common.core.page.TableDataInfo;

/**
 * 生产作业物料Controller
 * 
 * @author neuedu
 * @date 2022-07-15
 */
@Api(tags = {"生产作业物料"})
@RestController
@RequestMapping("/mesProduct/productJobMaterial")
public class MesProductJobMaterialController extends BaseController
{
    @Autowired
    private IMesProductJobMaterialService mesProductJobMaterialService;

    /**
     * 查询生产作业物料列表
     */
    @GetMapping("/list")
    @ApiOperation("查询生产作业物料列表")
    @DynamicResponseParameters(properties = {
	        @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = MesProductJobMaterial.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list(MesProductJobMaterial mesProductJobMaterial)
    {
        startPage();
        List<MesProductJobMaterial> list = mesProductJobMaterialService.selectMesProductJobMaterialList(mesProductJobMaterial);
        return getDataTable(list);
    }

    /**
     * 导出生产作业物料列表
     */
    @ApiOperation("导出生产作业物料列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "文件名")
    })
    @PreAuthorize("@ss.hasPermi('mesProduct:productJobMaterial:export')")
    @Log(title = "生产作业物料", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(MesProductJobMaterial mesProductJobMaterial)
    {
        List<MesProductJobMaterial> list = mesProductJobMaterialService.selectMesProductJobMaterialList(mesProductJobMaterial);
        ExcelUtil<MesProductJobMaterial> util = new ExcelUtil<MesProductJobMaterial>(MesProductJobMaterial.class);
        return util.exportExcel(list, "productJobMaterial");
    }

    /**
     * 获取生产作业物料详细信息
     */
    @ApiOperation("获取生产作业物料详细信息")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "data", value = "返回业务数据", dataTypeClass = MesProductJobMaterial.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(mesProductJobMaterialService.selectMesProductJobMaterialById(id));
    }

    /**
     * 新增生产作业物料
     */
    @ApiOperation("新增生产作业物料")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('mesProduct:productJobMaterial:add')")
    @Log(title = "生产作业物料", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MesProductJobMaterial mesProductJobMaterial)
    {
        return toAjax(mesProductJobMaterialService.insertMesProductJobMaterial(mesProductJobMaterial));
    }

    /**
     * 修改生产作业物料
     */
    @ApiOperation("修改生产作业物料")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('mesProduct:productJobMaterial:edit')")
    @Log(title = "生产作业物料", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MesProductJobMaterial mesProductJobMaterial)
    {
        return toAjax(mesProductJobMaterialService.updateMesProductJobMaterial(mesProductJobMaterial));
    }

    /**
     * 删除生产作业物料
     */
    @ApiOperation("删除生产作业物料")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('mesProduct:productJobMaterial:remove')")
    @Log(title = "生产作业物料", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(mesProductJobMaterialService.deleteMesProductJobMaterialByIds(ids));
    }
}
