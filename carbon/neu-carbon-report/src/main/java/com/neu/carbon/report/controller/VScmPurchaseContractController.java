package com.neu.carbon.report.controller;

import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicResponseParameters;
import com.neu.carbon.scm.domain.VScmPurchaseContract;
import com.neu.carbon.scm.service.IVScmPurchaseContractService;
import com.neu.common.annotation.Log;
import com.neu.common.core.controller.BaseController;
import com.neu.common.core.domain.AjaxResult;
import com.neu.common.core.page.TableDataInfo;
import com.neu.common.enums.BusinessType;
import com.neu.common.utils.poi.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 采购合同报表Controller
 *
 * @author neuedu
 * @date 2022-07-29
 */
@Api(tags = {"采购合同报表"})
@RestController
@RequestMapping("/purchaseReport/purchaseContractReport")
public class VScmPurchaseContractController extends BaseController {
    @Autowired
    private IVScmPurchaseContractService vScmPurchaseContractService;

    /**
     * 查询采购合同报表列表
     */
    @GetMapping("/list")
    @ApiOperation("查询采购合同报表列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = VScmPurchaseContract.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list(VScmPurchaseContract vScmPurchaseContract) {
        startPage();
        List<VScmPurchaseContract> list = vScmPurchaseContractService.selectVScmPurchaseContractList(vScmPurchaseContract);
        return getDataTable(list);
    }

    /**
     * 导出采购合同报表列表
     */
    @ApiOperation("导出采购合同报表列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "文件名")
    })
    @PreAuthorize("@ss.hasPermi('purchaseReport:purchaseContractReport:export')")
    @Log(title = "采购合同报表", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(VScmPurchaseContract vScmPurchaseContract) {
        List<VScmPurchaseContract> list = vScmPurchaseContractService.selectVScmPurchaseContractList(vScmPurchaseContract);
        ExcelUtil<VScmPurchaseContract> util = new ExcelUtil<VScmPurchaseContract>(VScmPurchaseContract.class);
        return util.exportExcel(list, "purchaseContractReport");
    }

    @GetMapping("/byMonth")
    @ApiOperation("月份统计采购付款")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = VScmPurchaseContract.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo selectMonthReport(VScmPurchaseContract vScmPurchaseContract) {
        startPage();
        List<VScmPurchaseContract> list = vScmPurchaseContractService.selectMonthReport(vScmPurchaseContract);
        return getDataTable(list);
    }


    @GetMapping("/byQuarter")
    @ApiOperation("季度统计采购付款")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = VScmPurchaseContract.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo selectQuarterReport(VScmPurchaseContract vScmPurchaseContract) {
        startPage();
        List<VScmPurchaseContract> list = vScmPurchaseContractService.selectQuarterReport(vScmPurchaseContract);
        return getDataTable(list);
    }

    @GetMapping("/supplier/byMonth")
    @ApiOperation("月份统计供应商对账")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = VScmPurchaseContract.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo selectMonthSupplierReport(VScmPurchaseContract vScmPurchaseContract) {
        startPage();
        List<VScmPurchaseContract> list = vScmPurchaseContractService.selectMonthSupplierReport(vScmPurchaseContract);
        return getDataTable(list);
    }

    @GetMapping("/supplier/byQuarter")
    @ApiOperation("季度统计供应商对账")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = VScmPurchaseContract.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo selectQuarterSupplierReport(VScmPurchaseContract vScmPurchaseContract) {
        startPage();
        List<VScmPurchaseContract> list = vScmPurchaseContractService.selectQuarterSupplierReport(vScmPurchaseContract);
        return getDataTable(list);
    }


}
