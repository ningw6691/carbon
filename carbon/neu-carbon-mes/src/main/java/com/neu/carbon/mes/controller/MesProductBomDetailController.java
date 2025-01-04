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
import com.neu.carbon.mes.domain.MesProductBomDetail;
import com.neu.carbon.mes.service.IMesProductBomDetailService;
import com.neu.common.utils.poi.ExcelUtil;
import com.neu.common.core.page.TableDataInfo;

/**
 * BOM单明细Controller
 * 
 * @author neuedu
 * @date 2022-07-11
 */
@Api(tags = {"BOM单明细"})
@RestController
@RequestMapping("/mesPlan/nesBomDetail")
public class MesProductBomDetailController extends BaseController
{
    @Autowired
    private IMesProductBomDetailService mesProductBomDetailService;

    /**
     * 查询BOM单明细列表
     */
    @GetMapping("/list")
    @ApiOperation("查询BOM单明细列表")
    @DynamicResponseParameters(properties = {
	        @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = MesProductBomDetail.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list(MesProductBomDetail mesProductBomDetail)
    {
        startPage();
        List<MesProductBomDetail> list = mesProductBomDetailService.selectMesProductBomDetailList(mesProductBomDetail);
        return getDataTable(list);
    }

    /**
     * 导出BOM单明细列表
     */
    @ApiOperation("导出BOM单明细列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "文件名")
    })
    @PreAuthorize("@ss.hasPermi('mesPlan:nesBomDetail:export')")
    @Log(title = "BOM单明细", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(MesProductBomDetail mesProductBomDetail)
    {
        List<MesProductBomDetail> list = mesProductBomDetailService.selectMesProductBomDetailList(mesProductBomDetail);
        ExcelUtil<MesProductBomDetail> util = new ExcelUtil<MesProductBomDetail>(MesProductBomDetail.class);
        return util.exportExcel(list, "nesBomDetail");
    }

    /**
     * 获取BOM单明细详细信息
     */
    @ApiOperation("获取BOM单明细详细信息")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "data", value = "返回业务数据", dataTypeClass = MesProductBomDetail.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(mesProductBomDetailService.selectMesProductBomDetailById(id));
    }

    /**
     * 新增BOM单明细
     */
    @ApiOperation("新增BOM单明细")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('mesPlan:nesBomDetail:add')")
    @Log(title = "BOM单明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MesProductBomDetail mesProductBomDetail)
    {
        return toAjax(mesProductBomDetailService.insertMesProductBomDetail(mesProductBomDetail));
    }

    /**
     * 修改BOM单明细
     */
    @ApiOperation("修改BOM单明细")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('mesPlan:nesBomDetail:edit')")
    @Log(title = "BOM单明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MesProductBomDetail mesProductBomDetail)
    {
        return toAjax(mesProductBomDetailService.updateMesProductBomDetail(mesProductBomDetail));
    }

    /**
     * 删除BOM单明细
     */
    @ApiOperation("删除BOM单明细")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('mesPlan:nesBomDetail:remove')")
    @Log(title = "BOM单明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(mesProductBomDetailService.deleteMesProductBomDetailByIds(ids));
    }
}
