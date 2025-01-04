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
import com.neu.carbon.scm.domain.ScmPurchaseReturnDetail;
import com.neu.carbon.scm.service.IScmPurchaseReturnDetailService;
import com.neu.common.utils.poi.ExcelUtil;
import com.neu.common.core.page.TableDataInfo;

/**
 * 退货明细Controller
 * 
 * @author neuedu
 * @date 2022-07-07
 */
@Api(tags = {"退货明细"})
@RestController
@RequestMapping("/purchase/returnDetail")
public class ScmPurchaseReturnDetailController extends BaseController
{
    @Autowired
    private IScmPurchaseReturnDetailService scmPurchaseReturnDetailService;

    /**
     * 查询退货明细列表
     */
    @GetMapping("/list")
    @ApiOperation("查询退货明细列表")
    @DynamicResponseParameters(properties = {
	        @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = ScmPurchaseReturnDetail.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list(ScmPurchaseReturnDetail scmPurchaseReturnDetail)
    {
        startPage();
        List<ScmPurchaseReturnDetail> list = scmPurchaseReturnDetailService.selectScmPurchaseReturnDetailList(scmPurchaseReturnDetail);
        return getDataTable(list);
    }

    /**
     * 导出退货明细列表
     */
    @ApiOperation("导出退货明细列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "文件名")
    })
    @PreAuthorize("@ss.hasPermi('purchase:returnDetail:export')")
    @Log(title = "退货明细", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ScmPurchaseReturnDetail scmPurchaseReturnDetail)
    {
        List<ScmPurchaseReturnDetail> list = scmPurchaseReturnDetailService.selectScmPurchaseReturnDetailList(scmPurchaseReturnDetail);
        ExcelUtil<ScmPurchaseReturnDetail> util = new ExcelUtil<ScmPurchaseReturnDetail>(ScmPurchaseReturnDetail.class);
        return util.exportExcel(list, "returnDetail");
    }

    /**
     * 获取退货明细详细信息
     */
    @ApiOperation("获取退货明细详细信息")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "data", value = "返回业务数据", dataTypeClass = ScmPurchaseReturnDetail.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(scmPurchaseReturnDetailService.selectScmPurchaseReturnDetailById(id));
    }

    /**
     * 新增退货明细
     */
    @ApiOperation("新增退货明细")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('purchase:returnDetail:add')")
    @Log(title = "退货明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ScmPurchaseReturnDetail scmPurchaseReturnDetail)
    {
        return toAjax(scmPurchaseReturnDetailService.insertScmPurchaseReturnDetail(scmPurchaseReturnDetail));
    }

    /**
     * 修改退货明细
     */
    @ApiOperation("修改退货明细")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('purchase:returnDetail:edit')")
    @Log(title = "退货明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ScmPurchaseReturnDetail scmPurchaseReturnDetail)
    {
        return toAjax(scmPurchaseReturnDetailService.updateScmPurchaseReturnDetail(scmPurchaseReturnDetail));
    }

    /**
     * 删除退货明细
     */
    @ApiOperation("删除退货明细")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('purchase:returnDetail:remove')")
    @Log(title = "退货明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(scmPurchaseReturnDetailService.deleteScmPurchaseReturnDetailByIds(ids));
    }
}
