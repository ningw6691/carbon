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
import com.neu.carbon.mes.domain.MesProductModelDetail;
import com.neu.carbon.mes.service.IMesProductModelDetailService;
import com.neu.common.utils.poi.ExcelUtil;
import com.neu.common.core.page.TableDataInfo;

/**
 * 产品模型明细Controller
 * 
 * @author neuedu
 * @date 2022-07-08
 */
@Api(tags = {"产品模型明细"})
@RestController
@RequestMapping("/mesModel/productModelDetail")
public class MesProductModelDetailController extends BaseController
{
    @Autowired
    private IMesProductModelDetailService mesProductModelDetailService;

    /**
     * 查询产品模型明细列表
     */
    @GetMapping("/list")
    @ApiOperation("查询产品模型明细列表")
    @DynamicResponseParameters(properties = {
	        @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = MesProductModelDetail.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list(MesProductModelDetail mesProductModelDetail)
    {
        startPage();
        List<MesProductModelDetail> list = mesProductModelDetailService.selectMesProductModelDetailList(mesProductModelDetail);
        return getDataTable(list);
    }

    /**
     * 导出产品模型明细列表
     */
    @ApiOperation("导出产品模型明细列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "文件名")
    })
    @PreAuthorize("@ss.hasPermi('mesModel:productModelDetail:export')")
    @Log(title = "产品模型明细", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(MesProductModelDetail mesProductModelDetail)
    {
        List<MesProductModelDetail> list = mesProductModelDetailService.selectMesProductModelDetailList(mesProductModelDetail);
        ExcelUtil<MesProductModelDetail> util = new ExcelUtil<MesProductModelDetail>(MesProductModelDetail.class);
        return util.exportExcel(list, "productModelDetail");
    }

    /**
     * 获取产品模型明细详细信息
     */
    @ApiOperation("获取产品模型明细详细信息")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "data", value = "返回业务数据", dataTypeClass = MesProductModelDetail.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(mesProductModelDetailService.selectMesProductModelDetailById(id));
    }

    /**
     * 新增产品模型明细
     */
    @ApiOperation("新增产品模型明细")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('mesModel:productModelDetail:add')")
    @Log(title = "产品模型明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MesProductModelDetail mesProductModelDetail)
    {
        return toAjax(mesProductModelDetailService.insertMesProductModelDetail(mesProductModelDetail));
    }

    /**
     * 修改产品模型明细
     */
    @ApiOperation("修改产品模型明细")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('mesModel:productModelDetail:edit')")
    @Log(title = "产品模型明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MesProductModelDetail mesProductModelDetail)
    {
        return toAjax(mesProductModelDetailService.updateMesProductModelDetail(mesProductModelDetail));
    }

    /**
     * 删除产品模型明细
     */
    @ApiOperation("删除产品模型明细")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('mesModel:productModelDetail:remove')")
    @Log(title = "产品模型明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(mesProductModelDetailService.deleteMesProductModelDetailByIds(ids));
    }
}
