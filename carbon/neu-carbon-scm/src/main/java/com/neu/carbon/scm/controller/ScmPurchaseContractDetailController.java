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
import com.neu.carbon.scm.domain.ScmPurchaseContractDetail;
import com.neu.carbon.scm.service.IScmPurchaseContractDetailService;
import com.neu.common.utils.poi.ExcelUtil;
import com.neu.common.core.page.TableDataInfo;

/**
 * 合同物料明细Controller
 * 
 * @author neuedu
 * @date 2022-06-30
 */
@Api(tags = {"合同物料明细"})
@RestController
@RequestMapping("/purchase/contractDetail")
public class ScmPurchaseContractDetailController extends BaseController
{
    @Autowired
    private IScmPurchaseContractDetailService scmPurchaseContractDetailService;

    /**
     * 查询合同物料明细列表
     */
    @GetMapping("/list")
    @ApiOperation("查询合同物料明细列表")
    @DynamicResponseParameters(properties = {
	        @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = ScmPurchaseContractDetail.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list(ScmPurchaseContractDetail scmPurchaseContractDetail)
    {
        startPage();
        List<ScmPurchaseContractDetail> list = scmPurchaseContractDetailService.selectScmPurchaseContractDetailList(scmPurchaseContractDetail);
        return getDataTable(list);
    }

    /**
     * 导出合同物料明细列表
     */
    @ApiOperation("导出合同物料明细列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "文件名")
    })
    @PreAuthorize("@ss.hasPermi('purchase:contractDetail:export')")
    @Log(title = "合同物料明细", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ScmPurchaseContractDetail scmPurchaseContractDetail)
    {
        List<ScmPurchaseContractDetail> list = scmPurchaseContractDetailService.selectScmPurchaseContractDetailList(scmPurchaseContractDetail);
        ExcelUtil<ScmPurchaseContractDetail> util = new ExcelUtil<ScmPurchaseContractDetail>(ScmPurchaseContractDetail.class);
        return util.exportExcel(list, "contractDetail");
    }

    /**
     * 获取合同物料明细详细信息
     */
    @ApiOperation("获取合同物料明细详细信息")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "data", value = "返回业务数据", dataTypeClass = ScmPurchaseContractDetail.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(scmPurchaseContractDetailService.selectScmPurchaseContractDetailById(id));
    }

    /**
     * 新增合同物料明细
     */
    @ApiOperation("新增合同物料明细")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('purchase:contractDetail:add')")
    @Log(title = "合同物料明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ScmPurchaseContractDetail scmPurchaseContractDetail)
    {
        return toAjax(scmPurchaseContractDetailService.insertScmPurchaseContractDetail(scmPurchaseContractDetail));
    }

    /**
     * 修改合同物料明细
     */
    @ApiOperation("修改合同物料明细")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('purchase:contractDetail:edit')")
    @Log(title = "合同物料明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ScmPurchaseContractDetail scmPurchaseContractDetail)
    {
        return toAjax(scmPurchaseContractDetailService.updateScmPurchaseContractDetail(scmPurchaseContractDetail));
    }

    /**
     * 删除合同物料明细
     */
    @ApiOperation("删除合同物料明细")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('purchase:contractDetail:remove')")
    @Log(title = "合同物料明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(scmPurchaseContractDetailService.deleteScmPurchaseContractDetailByIds(ids));
    }
}
