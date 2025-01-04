package com.neu.carbon.scm.controller;

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
import com.neu.carbon.scm.domain.ScmPurchaseArriveDetail;
import com.neu.carbon.scm.service.IScmPurchaseArriveDetailService;
import com.neu.common.utils.poi.ExcelUtil;
import com.neu.common.core.page.TableDataInfo;

/**
 * 到货明细Controller
 * 
 * @author neuedu
 * @date 2022-07-06
 */
@Api(tags = {"到货明细"})
@RestController
@RequestMapping("/purchase/arriveDetail")
public class ScmPurchaseArriveDetailController extends BaseController
{
    @Autowired
    private IScmPurchaseArriveDetailService scmPurchaseArriveDetailService;

    /**
     * 查询到货明细列表
     */
    @GetMapping("/list")
    @ApiOperation("查询到货明细列表")
    @DynamicResponseParameters(properties = {
	        @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = ScmPurchaseArriveDetail.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list(ScmPurchaseArriveDetail scmPurchaseArriveDetail)
    {
        startPage();
        List<ScmPurchaseArriveDetail> list = scmPurchaseArriveDetailService.selectScmPurchaseArriveDetailList(scmPurchaseArriveDetail);
        return getDataTable(list);
    }

    /**
     * 导出到货明细列表
     */
    @ApiOperation("导出到货明细列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "文件名")
    })
    @PreAuthorize("@ss.hasPermi('purchase:arriveDetail:export')")
    @Log(title = "到货明细", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ScmPurchaseArriveDetail scmPurchaseArriveDetail)
    {
        List<ScmPurchaseArriveDetail> list = scmPurchaseArriveDetailService.selectScmPurchaseArriveDetailList(scmPurchaseArriveDetail);
        ExcelUtil<ScmPurchaseArriveDetail> util = new ExcelUtil<ScmPurchaseArriveDetail>(ScmPurchaseArriveDetail.class);
        return util.exportExcel(list, "arriveDetail");
    }

    /**
     * 获取到货明细详细信息
     */
    @ApiOperation("获取到货明细详细信息")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "data", value = "返回业务数据", dataTypeClass = ScmPurchaseArriveDetail.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(scmPurchaseArriveDetailService.selectScmPurchaseArriveDetailById(id));
    }

    /**
     * 新增到货明细
     */
    @ApiOperation("新增到货明细")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('purchase:arriveDetail:add')")
    @Log(title = "到货明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ScmPurchaseArriveDetail scmPurchaseArriveDetail)
    {
        return toAjax(scmPurchaseArriveDetailService.insertScmPurchaseArriveDetail(scmPurchaseArriveDetail));
    }

    /**
     * 修改到货明细
     */
    @ApiOperation("修改到货明细")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('purchase:arriveDetail:edit')")
    @Log(title = "到货明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ScmPurchaseArriveDetail scmPurchaseArriveDetail)
    {
        return toAjax(scmPurchaseArriveDetailService.updateScmPurchaseArriveDetail(scmPurchaseArriveDetail));
    }

    /**
     * 删除到货明细
     */
    @ApiOperation("删除到货明细")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('purchase:arriveDetail:remove')")
    @Log(title = "到货明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(scmPurchaseArriveDetailService.deleteScmPurchaseArriveDetailByIds(ids));
    }
}
