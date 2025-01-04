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
import com.neu.carbon.scm.domain.ScmPurchaseApplyDetail;
import com.neu.carbon.scm.service.IScmPurchaseApplyDetailService;
import com.neu.common.utils.poi.ExcelUtil;
import com.neu.common.core.page.TableDataInfo;

/**
 * 采购申请明细Controller
 * 
 * @author neuedu
 * @date 2022-06-29
 */
@Api(tags = {"采购申请明细"})
@RestController
@RequestMapping("/purchase/applyDetail")
public class ScmPurchaseApplyDetailController extends BaseController
{
    @Autowired
    private IScmPurchaseApplyDetailService scmPurchaseApplyDetailService;

    /**
     * 查询采购申请明细列表
     */
    @GetMapping("/list")
    @ApiOperation("查询采购申请明细列表")
    @DynamicResponseParameters(properties = {
	        @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = ScmPurchaseApplyDetail.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list(ScmPurchaseApplyDetail scmPurchaseApplyDetail)
    {
        startPage();
        List<ScmPurchaseApplyDetail> list = scmPurchaseApplyDetailService.selectScmPurchaseApplyDetailList(scmPurchaseApplyDetail);
        return getDataTable(list);
    }

    /**
     * 导出采购申请明细列表
     */
    @ApiOperation("导出采购申请明细列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "文件名")
    })
    @PreAuthorize("@ss.hasPermi('purchase:applyDetail:export')")
    @Log(title = "采购申请明细", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ScmPurchaseApplyDetail scmPurchaseApplyDetail)
    {
        List<ScmPurchaseApplyDetail> list = scmPurchaseApplyDetailService.selectScmPurchaseApplyDetailList(scmPurchaseApplyDetail);
        ExcelUtil<ScmPurchaseApplyDetail> util = new ExcelUtil<ScmPurchaseApplyDetail>(ScmPurchaseApplyDetail.class);
        return util.exportExcel(list, "applyDetail");
    }

    /**
     * 获取采购申请明细详细信息
     */
    @ApiOperation("获取采购申请明细详细信息")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "data", value = "返回业务数据", dataTypeClass = ScmPurchaseApplyDetail.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(scmPurchaseApplyDetailService.selectScmPurchaseApplyDetailById(id));
    }

    /**
     * 新增采购申请明细
     */
    @ApiOperation("新增采购申请明细")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('purchase:applyDetail:add')")
    @Log(title = "采购申请明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ScmPurchaseApplyDetail scmPurchaseApplyDetail)
    {
        return toAjax(scmPurchaseApplyDetailService.insertScmPurchaseApplyDetail(scmPurchaseApplyDetail));
    }

    /**
     * 修改采购申请明细
     */
    @ApiOperation("修改采购申请明细")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('purchase:applyDetail:edit')")
    @Log(title = "采购申请明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ScmPurchaseApplyDetail scmPurchaseApplyDetail)
    {
        return toAjax(scmPurchaseApplyDetailService.updateScmPurchaseApplyDetail(scmPurchaseApplyDetail));
    }

    /**
     * 删除采购申请明细
     */
    @ApiOperation("删除采购申请明细")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('purchase:applyDetail:remove')")
    @Log(title = "采购申请明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(scmPurchaseApplyDetailService.deleteScmPurchaseApplyDetailByIds(ids));
    }
}
