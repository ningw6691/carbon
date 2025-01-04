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
import com.neu.carbon.scm.domain.ScmSaleContractDetail;
import com.neu.carbon.scm.service.IScmSaleContractDetailService;
import com.neu.common.utils.poi.ExcelUtil;
import com.neu.common.core.page.TableDataInfo;

/**
 * 合同明细Controller
 * 
 * @author neuedu
 * @date 2022-07-04
 */
@Api(tags = {"合同明细"})
@RestController
@RequestMapping("/sale/contractDetail")
public class ScmSaleContractDetailController extends BaseController
{
    @Autowired
    private IScmSaleContractDetailService scmSaleContractDetailService;

    /**
     * 查询合同明细列表
     */
    @GetMapping("/list")
    @ApiOperation("查询合同明细列表")
    @DynamicResponseParameters(properties = {
	        @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = ScmSaleContractDetail.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list(ScmSaleContractDetail scmSaleContractDetail)
    {
        startPage();
        List<ScmSaleContractDetail> list = scmSaleContractDetailService.selectScmSaleContractDetailList(scmSaleContractDetail);
        return getDataTable(list);
    }

    /**
     * 导出合同明细列表
     */
    @ApiOperation("导出合同明细列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "文件名")
    })
    @PreAuthorize("@ss.hasPermi('sale:contractDetail:export')")
    @Log(title = "合同明细", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ScmSaleContractDetail scmSaleContractDetail)
    {
        List<ScmSaleContractDetail> list = scmSaleContractDetailService.selectScmSaleContractDetailList(scmSaleContractDetail);
        ExcelUtil<ScmSaleContractDetail> util = new ExcelUtil<ScmSaleContractDetail>(ScmSaleContractDetail.class);
        return util.exportExcel(list, "contractDetail");
    }

    /**
     * 获取合同明细详细信息
     */
    @ApiOperation("获取合同明细详细信息")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "data", value = "返回业务数据", dataTypeClass = ScmSaleContractDetail.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(scmSaleContractDetailService.selectScmSaleContractDetailById(id));
    }

    /**
     * 新增合同明细
     */
    @ApiOperation("新增合同明细")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('sale:contractDetail:add')")
    @Log(title = "合同明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ScmSaleContractDetail scmSaleContractDetail)
    {
        return toAjax(scmSaleContractDetailService.insertScmSaleContractDetail(scmSaleContractDetail));
    }

    /**
     * 修改合同明细
     */
    @ApiOperation("修改合同明细")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('sale:contractDetail:edit')")
    @Log(title = "合同明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ScmSaleContractDetail scmSaleContractDetail)
    {
        return toAjax(scmSaleContractDetailService.updateScmSaleContractDetail(scmSaleContractDetail));
    }

    /**
     * 删除合同明细
     */
    @ApiOperation("删除合同明细")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('sale:contractDetail:remove')")
    @Log(title = "合同明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(scmSaleContractDetailService.deleteScmSaleContractDetailByIds(ids));
    }
}
