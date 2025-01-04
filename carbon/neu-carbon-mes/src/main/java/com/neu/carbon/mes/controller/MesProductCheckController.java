package com.neu.carbon.mes.controller;

import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicResponseParameters;
import com.neu.carbon.mes.domain.MesProductCheck;
import com.neu.carbon.mes.service.IMesProductCheckService;
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
 * 产品检验单Controller
 *
 * @author neuedu
 * @date 2022-07-12
 */
@Api(tags = {"产品检验单"})
@RestController
@RequestMapping("/mesCheck/productCheck")
public class MesProductCheckController extends BaseController {
    @Autowired
    private IMesProductCheckService mesProductCheckService;

    /**
     * 查询产品检验单列表
     */
    @GetMapping("/list")
    @ApiOperation("查询产品检验单列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = MesProductCheck.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list(MesProductCheck mesProductCheck) {
        startPage();
        List<MesProductCheck> list = mesProductCheckService.selectMesProductCheckList(mesProductCheck);
        return getDataTable(list);
    }

    /**
     * 导出产品检验单列表
     */
    @ApiOperation("导出产品检验单列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "文件名")
    })
    @PreAuthorize("@ss.hasPermi('mesCheck:productCheck:export')")
    @Log(title = "产品检验单", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(MesProductCheck mesProductCheck) {
        List<MesProductCheck> list = mesProductCheckService.selectMesProductCheckList(mesProductCheck);
        ExcelUtil<MesProductCheck> util = new ExcelUtil<MesProductCheck>(MesProductCheck.class);
        return util.exportExcel(list, "productCheck");
    }

    /**
     * 获取产品检验单详细信息
     */
    @ApiOperation("获取产品检验单详细信息")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "data", value = "返回业务数据", dataTypeClass = MesProductCheck.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(mesProductCheckService.selectMesProductCheckById(id));
    }

    /**
     * 新增产品检验单
     */
    @ApiOperation("新增产品检验单")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('mesCheck:productCheck:add')")
    @Log(title = "产品检验单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MesProductCheck mesProductCheck) {
        mesProductCheck.setCheckUser(String.valueOf(this.getUserId()));
        return toAjax(mesProductCheckService.insertMesProductCheck(mesProductCheck));
    }

    /**
     * 修改产品检验单
     */
    @ApiOperation("修改产品检验单")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('mesCheck:productCheck:edit')")
    @Log(title = "产品检验单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MesProductCheck mesProductCheck) {
        return toAjax(mesProductCheckService.updateMesProductCheck(mesProductCheck));
    }

    /**
     * 删除产品检验单
     */
    @ApiOperation("删除产品检验单")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('mesCheck:productCheck:remove')")
    @Log(title = "产品检验单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(mesProductCheckService.deleteMesProductCheckByIds(ids));
    }
}
