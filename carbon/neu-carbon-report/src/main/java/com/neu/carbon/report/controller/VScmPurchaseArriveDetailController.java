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
import com.neu.carbon.scm.domain.VScmPurchaseArriveDetail;
import com.neu.carbon.scm.service.IVScmPurchaseArriveDetailService;
import com.neu.common.utils.poi.ExcelUtil;
import com.neu.common.core.page.TableDataInfo;

/**
 * 采购到货详细报表Controller
 * 
 * @author neuedu
 * @date 2022-08-02
 */
@Api(tags = {"采购入库详细报表"})
@RestController
@RequestMapping("/purchaseReport/purchaseArriveDetailReport")
public class VScmPurchaseArriveDetailController extends BaseController
{
    @Autowired
    private IVScmPurchaseArriveDetailService vScmPurchaseArriveDetailService;

    @GetMapping("/list")
    @ApiOperation("查询采购入库详细报表列表")
    @DynamicResponseParameters(properties = {
	        @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = VScmPurchaseArriveDetail.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list(VScmPurchaseArriveDetail vScmPurchaseArriveDetail)
    {
        startPage();
        List<VScmPurchaseArriveDetail> list = vScmPurchaseArriveDetailService.selectVScmPurchaseArriveDetailList(vScmPurchaseArriveDetail);
        return getDataTable(list);
    }

    @ApiOperation("导出采购入库详细报表列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "文件名")
    })
    @PreAuthorize("@ss.hasPermi('purchaseReport:purchaseArriveDetailReport:export')")
    @Log(title = "采购入库详细报表", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(VScmPurchaseArriveDetail vScmPurchaseArriveDetail)
    {
        List<VScmPurchaseArriveDetail> list = vScmPurchaseArriveDetailService.selectVScmPurchaseArriveDetailList(vScmPurchaseArriveDetail);
        ExcelUtil<VScmPurchaseArriveDetail> util = new ExcelUtil<VScmPurchaseArriveDetail>(VScmPurchaseArriveDetail.class);
        return util.exportExcel(list, "purchaseArriveDetailReport");
    }

}
