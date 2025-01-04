package com.neu.carbon.report.controller;

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
import com.neu.carbon.scm.domain.VScmSaleDeliveryDetail;
import com.neu.carbon.scm.service.IVScmSaleDeliveryDetailService;
import com.neu.common.utils.poi.ExcelUtil;
import com.neu.common.core.page.TableDataInfo;

/**
 * 销售发货退货明细报表Controller
 * 
 * @author neuedu
 * @date 2022-08-02
 */
@Api(tags = {"销售发货退货明细报表"})
@RestController
@RequestMapping("/saleReport/saleDeliveryDetailReport")
public class VScmSaleDeliveryDetailController extends BaseController
{
    @Autowired
    private IVScmSaleDeliveryDetailService vScmSaleDeliveryDetailService;

    /**
     * 查询销售发货退货明细报表列表
     */
    @GetMapping("/list")
    @ApiOperation("查询销售发货退货明细报表列表")
    @DynamicResponseParameters(properties = {
	        @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = VScmSaleDeliveryDetail.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list(VScmSaleDeliveryDetail vScmSaleDeliveryDetail)
    {
        startPage();
        List<VScmSaleDeliveryDetail> list = vScmSaleDeliveryDetailService.selectVScmSaleDeliveryDetailList(vScmSaleDeliveryDetail);
        return getDataTable(list);
    }

    /**
     * 导出销售发货退货明细报表列表
     */
    @ApiOperation("导出销售发货退货明细报表列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "文件名")
    })
    @PreAuthorize("@ss.hasPermi('saleReport:saleDeliveryDetailReport:export')")
    @Log(title = "销售发货退货明细报表", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(VScmSaleDeliveryDetail vScmSaleDeliveryDetail)
    {
        List<VScmSaleDeliveryDetail> list = vScmSaleDeliveryDetailService.selectVScmSaleDeliveryDetailList(vScmSaleDeliveryDetail);
        ExcelUtil<VScmSaleDeliveryDetail> util = new ExcelUtil<VScmSaleDeliveryDetail>(VScmSaleDeliveryDetail.class);
        return util.exportExcel(list, "saleDeliveryDetailReport");
    }

    /**
     * 获取销售发货退货明细报表详细信息
     */
    @ApiOperation("获取销售发货退货明细报表详细信息")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "data", value = "返回业务数据", dataTypeClass = VScmSaleDeliveryDetail.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @GetMapping(value = "/{contractNo}")
    public AjaxResult getInfo(@PathVariable("contractNo") String contractNo)
    {
        return AjaxResult.success(vScmSaleDeliveryDetailService.selectVScmSaleDeliveryDetailById(contractNo));
    }

    /**
     * 新增销售发货退货明细报表
     */
    @ApiOperation("新增销售发货退货明细报表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('saleReport:saleDeliveryDetailReport:add')")
    @Log(title = "销售发货退货明细报表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody VScmSaleDeliveryDetail vScmSaleDeliveryDetail)
    {
        return toAjax(vScmSaleDeliveryDetailService.insertVScmSaleDeliveryDetail(vScmSaleDeliveryDetail));
    }

    /**
     * 修改销售发货退货明细报表
     */
    @ApiOperation("修改销售发货退货明细报表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('saleReport:saleDeliveryDetailReport:edit')")
    @Log(title = "销售发货退货明细报表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody VScmSaleDeliveryDetail vScmSaleDeliveryDetail)
    {
        return toAjax(vScmSaleDeliveryDetailService.updateVScmSaleDeliveryDetail(vScmSaleDeliveryDetail));
    }

    /**
     * 删除销售发货退货明细报表
     */
    @ApiOperation("删除销售发货退货明细报表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('saleReport:saleDeliveryDetailReport:remove')")
    @Log(title = "销售发货退货明细报表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{contractNos}")
    public AjaxResult remove(@PathVariable String[] contractNos)
    {
        return toAjax(vScmSaleDeliveryDetailService.deleteVScmSaleDeliveryDetailByIds(contractNos));
    }
}
