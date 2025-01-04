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
import com.neu.carbon.scm.domain.ScmSaleReturnDetail;
import com.neu.carbon.scm.service.IScmSaleReturnDetailService;
import com.neu.common.utils.poi.ExcelUtil;
import com.neu.common.core.page.TableDataInfo;

/**
 * 退货明细Controller
 * 
 * @author neuedu
 * @date 2022-07-08
 */
@Api(tags = {"退货明细"})
@RestController
@RequestMapping("/sale/returnDetail")
public class ScmSaleReturnDetailController extends BaseController
{
    @Autowired
    private IScmSaleReturnDetailService scmSaleReturnDetailService;

    /**
     * 查询退货明细列表
     */
    @GetMapping("/list")
    @ApiOperation("查询退货明细列表")
    @DynamicResponseParameters(properties = {
	        @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = ScmSaleReturnDetail.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list(ScmSaleReturnDetail scmSaleReturnDetail)
    {
        startPage();
        List<ScmSaleReturnDetail> list = scmSaleReturnDetailService.selectScmSaleReturnDetailList(scmSaleReturnDetail);
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
    @PreAuthorize("@ss.hasPermi('sale:returnDetail:export')")
    @Log(title = "退货明细", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ScmSaleReturnDetail scmSaleReturnDetail)
    {
        List<ScmSaleReturnDetail> list = scmSaleReturnDetailService.selectScmSaleReturnDetailList(scmSaleReturnDetail);
        ExcelUtil<ScmSaleReturnDetail> util = new ExcelUtil<ScmSaleReturnDetail>(ScmSaleReturnDetail.class);
        return util.exportExcel(list, "returnSale");
    }

    /**
     * 获取退货明细详细信息
     */
    @ApiOperation("获取退货明细详细信息")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "data", value = "返回业务数据", dataTypeClass = ScmSaleReturnDetail.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(scmSaleReturnDetailService.selectScmSaleReturnDetailById(id));
    }

    /**
     * 新增退货明细
     */
    @ApiOperation("新增退货明细")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('sale:returnDetail:add')")
    @Log(title = "退货明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ScmSaleReturnDetail scmSaleReturnDetail)
    {
        return toAjax(scmSaleReturnDetailService.insertScmSaleReturnDetail(scmSaleReturnDetail));
    }

    /**
     * 修改退货明细
     */
    @ApiOperation("修改退货明细")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('sale:returnDetail:edit')")
    @Log(title = "退货明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ScmSaleReturnDetail scmSaleReturnDetail)
    {
        return toAjax(scmSaleReturnDetailService.updateScmSaleReturnDetail(scmSaleReturnDetail));
    }

    /**
     * 删除退货明细
     */
    @ApiOperation("删除退货明细")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('sale:returnDetail:remove')")
    @Log(title = "退货明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(scmSaleReturnDetailService.deleteScmSaleReturnDetailByIds(ids));
    }
}
