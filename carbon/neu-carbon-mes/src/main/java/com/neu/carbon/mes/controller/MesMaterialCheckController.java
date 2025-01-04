package com.neu.carbon.mes.controller;

import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicResponseParameters;
import com.neu.carbon.mes.domain.MesMaterialCheck;
import com.neu.carbon.mes.service.IMesMaterialCheckService;
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
 * 物料检验单Controller
 *
 * @author neuedu
 * @date 2022-07-12
 */
@Api(tags = {"物料检验单"})
@RestController
@RequestMapping("/mesCheck/materialCheck")
public class MesMaterialCheckController extends BaseController {
    @Autowired
    private IMesMaterialCheckService mesMaterialCheckService;

    /**
     * 查询物料检验单列表
     */
    @GetMapping("/list")
    @ApiOperation("查询物料检验单列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = MesMaterialCheck.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list(MesMaterialCheck mesMaterialCheck) {
        startPage();
        List<MesMaterialCheck> list = mesMaterialCheckService.selectMesMaterialCheckList(mesMaterialCheck);
        return getDataTable(list);
    }

    /**
     * 导出物料检验单列表
     */
    @ApiOperation("导出物料检验单列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "文件名")
    })
    @PreAuthorize("@ss.hasPermi('mesCheck:materialCheck:export')")
    @Log(title = "物料检验单", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(MesMaterialCheck mesMaterialCheck) {
        List<MesMaterialCheck> list = mesMaterialCheckService.selectMesMaterialCheckList(mesMaterialCheck);
        ExcelUtil<MesMaterialCheck> util = new ExcelUtil<MesMaterialCheck>(MesMaterialCheck.class);
        return util.exportExcel(list, "materialCheck");
    }

    /**
     * 获取物料检验单详细信息
     */
    @ApiOperation("获取物料检验单详细信息")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "data", value = "返回业务数据", dataTypeClass = MesMaterialCheck.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(mesMaterialCheckService.selectMesMaterialCheckById(id));
    }

    /**
     * 新增物料检验单
     */
    @ApiOperation("新增物料检验单")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('mesCheck:materialCheck:add')")
    @Log(title = "物料检验单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MesMaterialCheck mesMaterialCheck) {
        mesMaterialCheck.setCheckUser(String.valueOf(this.getUserId()));
        return toAjax(mesMaterialCheckService.insertMesMaterialCheck(mesMaterialCheck));
    }

    /**
     * 修改物料检验单
     */
    @ApiOperation("修改物料检验单")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('mesCheck:materialCheck:edit')")
    @Log(title = "物料检验单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MesMaterialCheck mesMaterialCheck) {
        return toAjax(mesMaterialCheckService.updateMesMaterialCheck(mesMaterialCheck));
    }

    /**
     * 删除物料检验单
     */
    @ApiOperation("删除物料检验单")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('mesCheck:materialCheck:remove')")
    @Log(title = "物料检验单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(mesMaterialCheckService.deleteMesMaterialCheckByIds(ids));
    }
}
