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
import com.neu.carbon.scm.domain.ScmSaleOrderDetail;
import com.neu.carbon.scm.service.IScmSaleOrderDetailService;
import com.neu.common.utils.poi.ExcelUtil;
import com.neu.common.core.page.TableDataInfo;

/**
 * 订单明细Controller
 * 
 * @author neuedu
 * @date 2022-07-03
 */
@Api(tags = {"订单明细"})
@RestController
@RequestMapping("/sale/orderDetail")
public class ScmSaleOrderDetailController extends BaseController
{
    @Autowired
    private IScmSaleOrderDetailService scmSaleOrderDetailService;

    /**
     * 查询订单明细列表
     */
    @GetMapping("/list")
    @ApiOperation("查询订单明细列表")
    @DynamicResponseParameters(properties = {
	        @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = ScmSaleOrderDetail.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list(ScmSaleOrderDetail scmSaleOrderDetail)
    {
        startPage();
        List<ScmSaleOrderDetail> list = scmSaleOrderDetailService.selectScmSaleOrderDetailList(scmSaleOrderDetail);
        return getDataTable(list);
    }

    /**
     * 导出订单明细列表
     */
    @ApiOperation("导出订单明细列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "文件名")
    })
    @PreAuthorize("@ss.hasPermi('sale:orderDetail:export')")
    @Log(title = "订单明细", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ScmSaleOrderDetail scmSaleOrderDetail)
    {
        List<ScmSaleOrderDetail> list = scmSaleOrderDetailService.selectScmSaleOrderDetailList(scmSaleOrderDetail);
        ExcelUtil<ScmSaleOrderDetail> util = new ExcelUtil<ScmSaleOrderDetail>(ScmSaleOrderDetail.class);
        return util.exportExcel(list, "orderDetail");
    }

    /**
     * 获取订单明细详细信息
     */
    @ApiOperation("获取订单明细详细信息")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "data", value = "返回业务数据", dataTypeClass = ScmSaleOrderDetail.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(scmSaleOrderDetailService.selectScmSaleOrderDetailById(id));
    }

    /**
     * 新增订单明细
     */
    @ApiOperation("新增订单明细")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('sale:orderDetail:add')")
    @Log(title = "订单明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ScmSaleOrderDetail scmSaleOrderDetail)
    {
        return toAjax(scmSaleOrderDetailService.insertScmSaleOrderDetail(scmSaleOrderDetail));
    }

    /**
     * 修改订单明细
     */
    @ApiOperation("修改订单明细")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('sale:orderDetail:edit')")
    @Log(title = "订单明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ScmSaleOrderDetail scmSaleOrderDetail)
    {
        return toAjax(scmSaleOrderDetailService.updateScmSaleOrderDetail(scmSaleOrderDetail));
    }

    /**
     * 删除订单明细
     */
    @ApiOperation("删除订单明细")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('sale:orderDetail:remove')")
    @Log(title = "订单明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(scmSaleOrderDetailService.deleteScmSaleOrderDetailByIds(ids));
    }
}
