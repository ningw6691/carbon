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
import com.neu.carbon.scm.domain.ScmSaleDeliveryDetail;
import com.neu.carbon.scm.service.IScmSaleDeliveryDetailService;
import com.neu.common.utils.poi.ExcelUtil;
import com.neu.common.core.page.TableDataInfo;

/**
 * 发货单明细Controller
 * 
 * @author neuedu
 * @date 2022-07-05
 */
@Api(tags = {"发货单明细"})
@RestController
@RequestMapping("/sale/deliveryDetail")
public class ScmSaleDeliveryDetailController extends BaseController
{
    @Autowired
    private IScmSaleDeliveryDetailService scmSaleDeliveryDetailService;

    /**
     * 查询发货单明细列表
     */
    @GetMapping("/list")
    @ApiOperation("查询发货单明细列表")
    @DynamicResponseParameters(properties = {
	        @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = ScmSaleDeliveryDetail.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list(ScmSaleDeliveryDetail scmSaleDeliveryDetail)
    {
        startPage();
        List<ScmSaleDeliveryDetail> list = scmSaleDeliveryDetailService.selectScmSaleDeliveryDetailList(scmSaleDeliveryDetail);
        return getDataTable(list);
    }

    /**
     * 导出发货单明细列表
     */
    @ApiOperation("导出发货单明细列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "文件名")
    })
    @PreAuthorize("@ss.hasPermi('sale:deliveryDetail:export')")
    @Log(title = "发货单明细", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ScmSaleDeliveryDetail scmSaleDeliveryDetail)
    {
        List<ScmSaleDeliveryDetail> list = scmSaleDeliveryDetailService.selectScmSaleDeliveryDetailList(scmSaleDeliveryDetail);
        ExcelUtil<ScmSaleDeliveryDetail> util = new ExcelUtil<ScmSaleDeliveryDetail>(ScmSaleDeliveryDetail.class);
        return util.exportExcel(list, "deliveryDetail");
    }

    /**
     * 获取发货单明细详细信息
     */
    @ApiOperation("获取发货单明细详细信息")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "data", value = "返回业务数据", dataTypeClass = ScmSaleDeliveryDetail.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(scmSaleDeliveryDetailService.selectScmSaleDeliveryDetailById(id));
    }

    /**
     * 新增发货单明细
     */
    @ApiOperation("新增发货单明细")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('sale:deliveryDetail:add')")
    @Log(title = "发货单明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ScmSaleDeliveryDetail scmSaleDeliveryDetail)
    {
        return toAjax(scmSaleDeliveryDetailService.insertScmSaleDeliveryDetail(scmSaleDeliveryDetail));
    }

    /**
     * 修改发货单明细
     */
    @ApiOperation("修改发货单明细")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('sale:deliveryDetail:edit')")
    @Log(title = "发货单明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ScmSaleDeliveryDetail scmSaleDeliveryDetail)
    {
        return toAjax(scmSaleDeliveryDetailService.updateScmSaleDeliveryDetail(scmSaleDeliveryDetail));
    }

    /**
     * 删除发货单明细
     */
    @ApiOperation("删除发货单明细")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('sale:deliveryDetail:remove')")
    @Log(title = "发货单明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(scmSaleDeliveryDetailService.deleteScmSaleDeliveryDetailByIds(ids));
    }
}
