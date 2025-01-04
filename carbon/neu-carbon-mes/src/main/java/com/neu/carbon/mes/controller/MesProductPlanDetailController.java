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
import com.neu.carbon.mes.domain.MesProductPlanDetail;
import com.neu.carbon.mes.service.IMesProductPlanDetailService;
import com.neu.common.utils.poi.ExcelUtil;
import com.neu.common.core.page.TableDataInfo;

/**
 * 生产计划明细Controller
 * 
 * @author neuedu
 * @date 2022-07-12
 */
@Api(tags = {"生产计划明细"})
@RestController
@RequestMapping("/mesPlan/productPlanDetail")
public class MesProductPlanDetailController extends BaseController
{
    @Autowired
    private IMesProductPlanDetailService mesProductPlanDetailService;

    /**
     * 查询生产计划明细列表
     */
    @GetMapping("/list")
    @ApiOperation("查询生产计划明细列表")
    @DynamicResponseParameters(properties = {
	        @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = MesProductPlanDetail.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list(MesProductPlanDetail mesProductPlanDetail)
    {
        startPage();
        List<MesProductPlanDetail> list = mesProductPlanDetailService.selectMesProductPlanDetailList(mesProductPlanDetail);
        return getDataTable(list);
    }

    /**
     * 导出生产计划明细列表
     */
    @ApiOperation("导出生产计划明细列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "文件名")
    })
    @PreAuthorize("@ss.hasPermi('mesPlan:productPlanDetail:export')")
    @Log(title = "生产计划明细", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(MesProductPlanDetail mesProductPlanDetail)
    {
        List<MesProductPlanDetail> list = mesProductPlanDetailService.selectMesProductPlanDetailList(mesProductPlanDetail);
        ExcelUtil<MesProductPlanDetail> util = new ExcelUtil<MesProductPlanDetail>(MesProductPlanDetail.class);
        return util.exportExcel(list, "productPlanDetail");
    }

    /**
     * 获取生产计划明细详细信息
     */
    @ApiOperation("获取生产计划明细详细信息")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "data", value = "返回业务数据", dataTypeClass = MesProductPlanDetail.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(mesProductPlanDetailService.selectMesProductPlanDetailById(id));
    }

    /**
     * 新增生产计划明细
     */
    @ApiOperation("新增生产计划明细")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('mesPlan:productPlanDetail:add')")
    @Log(title = "生产计划明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MesProductPlanDetail mesProductPlanDetail)
    {
        return toAjax(mesProductPlanDetailService.insertMesProductPlanDetail(mesProductPlanDetail));
    }

    /**
     * 修改生产计划明细
     */
    @ApiOperation("修改生产计划明细")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('mesPlan:productPlanDetail:edit')")
    @Log(title = "生产计划明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MesProductPlanDetail mesProductPlanDetail)
    {
        return toAjax(mesProductPlanDetailService.updateMesProductPlanDetail(mesProductPlanDetail));
    }

    /**
     * 删除生产计划明细
     */
    @ApiOperation("删除生产计划明细")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('mesPlan:productPlanDetail:remove')")
    @Log(title = "生产计划明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(mesProductPlanDetailService.deleteMesProductPlanDetailByIds(ids));
    }
}
