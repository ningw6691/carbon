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
import com.neu.carbon.mes.domain.MesProductFinish;
import com.neu.carbon.mes.service.IMesProductFinishService;
import com.neu.common.utils.poi.ExcelUtil;
import com.neu.common.core.page.TableDataInfo;

/**
 * 生产完工单Controller
 * 
 * @author neuedu
 * @date 2022-07-17
 */
@Api(tags = {"生产完工单"})
@RestController
@RequestMapping("/mesProduct/productFinish")
public class MesProductFinishController extends BaseController
{
    @Autowired
    private IMesProductFinishService mesProductFinishService;

    /**
     * 查询生产完工单列表
     */
    @GetMapping("/list")
    @ApiOperation("查询生产完工单列表")
    @DynamicResponseParameters(properties = {
	        @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = MesProductFinish.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list(MesProductFinish mesProductFinish)
    {
        startPage();
        List<MesProductFinish> list = mesProductFinishService.selectMesProductFinishList(mesProductFinish);
        return getDataTable(list);
    }

    /**
     * 导出生产完工单列表
     */
    @ApiOperation("导出生产完工单列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "文件名")
    })
    @PreAuthorize("@ss.hasPermi('mesProduct:productFinish:export')")
    @Log(title = "生产完工单", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(MesProductFinish mesProductFinish)
    {
        List<MesProductFinish> list = mesProductFinishService.selectMesProductFinishList(mesProductFinish);
        ExcelUtil<MesProductFinish> util = new ExcelUtil<MesProductFinish>(MesProductFinish.class);
        return util.exportExcel(list, "productFinish");
    }

    /**
     * 获取生产完工单详细信息
     */
    @ApiOperation("获取生产完工单详细信息")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "data", value = "返回业务数据", dataTypeClass = MesProductFinish.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(mesProductFinishService.selectMesProductFinishById(id));
    }

    /**
     * 新增生产完工单
     */
    @ApiOperation("新增生产完工单")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('mesProduct:productFinish:add')")
    @Log(title = "生产完工单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MesProductFinish mesProductFinish)
    {
        return toAjax(mesProductFinishService.insertMesProductFinish(mesProductFinish));
    }

    /**
     * 修改生产完工单
     */
    @ApiOperation("修改生产完工单")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('mesProduct:productFinish:edit')")
    @Log(title = "生产完工单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MesProductFinish mesProductFinish)
    {
        return toAjax(mesProductFinishService.updateMesProductFinish(mesProductFinish));
    }

    /**
     * 删除生产完工单
     */
    @ApiOperation("删除生产完工单")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('mesProduct:productFinish:remove')")
    @Log(title = "生产完工单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(mesProductFinishService.deleteMesProductFinishByIds(ids));
    }
    
    /**
     * 修改生产完工单状态
     */
    @ApiOperation("修改生产完工单状态")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('mesProduct:productFinish:edit')")
    @Log(title = "生产完工单", businessType = BusinessType.UPDATE)
    @PutMapping("/update/status")
    public AjaxResult updateStatus(@RequestBody MesProductFinish mesProductFinish)
    {
    	MesProductFinish bill = mesProductFinishService.selectMesProductFinishById(mesProductFinish.getId());
    	bill.setStatus(mesProductFinish.getStatus());
    	return toAjax(mesProductFinishService.updateMesProductFinish(bill));
    }
}
