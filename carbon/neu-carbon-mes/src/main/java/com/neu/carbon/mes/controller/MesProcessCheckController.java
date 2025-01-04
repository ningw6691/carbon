package com.neu.carbon.mes.controller;

import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicResponseParameters;
import com.neu.carbon.mes.domain.MesProcessCheck;
import com.neu.carbon.mes.service.IMesProcessCheckService;
import com.neu.common.annotation.Log;
import com.neu.common.core.controller.BaseController;
import com.neu.common.core.domain.AjaxResult;
import com.neu.common.core.page.TableDataInfo;
import com.neu.common.enums.BusinessType;
import com.neu.common.utils.poi.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 生产采样单Controller
 *
 * @author neuedu
 * @date 2022-07-12
 */
@Api(tags = {"生产采样单"})
@RestController
@RequestMapping("/mesCheck/processCheck")
public class MesProcessCheckController extends BaseController {
    @Autowired
    private IMesProcessCheckService mesProcessCheckService;

    /**
     * 查询生产采样单列表
     */
    @GetMapping("/list")
    @ApiOperation("查询生产采样单列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = MesProcessCheck.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list(MesProcessCheck mesProcessCheck) {
        startPage();
        List<MesProcessCheck> list = mesProcessCheckService.selectMesProcessCheckList(mesProcessCheck);
        return getDataTable(list);
    }

    /**
     * 导出生产采样单列表
     */
    @ApiOperation("导出生产采样单列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "文件名")
    })
    @PreAuthorize("@ss.hasPermi('mesCheck:processCheck:export')")
    @Log(title = "生产采样单", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(MesProcessCheck mesProcessCheck) {
        List<MesProcessCheck> list = mesProcessCheckService.selectMesProcessCheckList(mesProcessCheck);
        ExcelUtil<MesProcessCheck> util = new ExcelUtil<MesProcessCheck>(MesProcessCheck.class);
        return util.exportExcel(list, "processCheck");
    }

    /**
     * 获取生产采样单详细信息
     */
    @ApiOperation("获取生产采样单详细信息")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "data", value = "返回业务数据", dataTypeClass = MesProcessCheck.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(mesProcessCheckService.selectMesProcessCheckById(id));
    }

    /**
     * 新增生产采样单
     */
    @ApiOperation("新增生产采样单")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('mesCheck:processCheck:add')")
    @Log(title = "生产采样单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MesProcessCheck mesProcessCheck) {
        mesProcessCheck.setCheckUser(String.valueOf(this.getUserId()));
        return toAjax(mesProcessCheckService.insertMesProcessCheck(mesProcessCheck));
    }

    /**
     * 修改生产采样单
     */
    @ApiOperation("修改生产采样单")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('mesCheck:processCheck:edit')")
    @Log(title = "生产采样单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MesProcessCheck mesProcessCheck) {
        return toAjax(mesProcessCheckService.updateMesProcessCheck(mesProcessCheck));
    }

    /**
     * 删除生产采样单
     */
    @ApiOperation("删除生产采样单")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('mesCheck:processCheck:remove')")
    @Log(title = "生产采样单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(mesProcessCheckService.deleteMesProcessCheckByIds(ids));
    }
}
