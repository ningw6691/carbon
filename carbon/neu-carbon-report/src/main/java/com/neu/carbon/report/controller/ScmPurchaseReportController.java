package com.neu.carbon.report.controller;

import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicResponseParameters;
import com.neu.carbon.scm.domain.RepContractArriveReturnDetail;
import com.neu.carbon.scm.domain.RepContractDeliveryReturnDetail;
import com.neu.carbon.scm.domain.VScmPurchaseMaterial;
import com.neu.carbon.scm.domain.VScmPurchaseReturn;
import com.neu.carbon.scm.service.IVScmPurchaseMaterialService;
import com.neu.carbon.scm.service.IVScmPurchaseReturnService;
import com.neu.common.annotation.Log;
import com.neu.common.core.controller.BaseController;
import com.neu.common.core.domain.AjaxResult;
import com.neu.common.core.page.TableDataInfo;
import com.neu.common.enums.BusinessType;
import com.neu.common.utils.poi.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 采购报表统计Controller
 *
 * @author neuedu
 * @date 2022-07-25
 */
@Api(tags = {"采购报表统计"})
@RestController
@RequestMapping("/report/scmPurchase")
public class ScmPurchaseReportController extends BaseController {
    @Autowired
    private IVScmPurchaseMaterialService vScmPurchaseMaterialService;
    @Autowired
    private IVScmPurchaseReturnService vScmPurchaseReturnService;

    /**
     * 查询采购物料列表
     */
    @GetMapping("/material/list")
    @ApiOperation("查询采购物料列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = VScmPurchaseMaterial.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list(VScmPurchaseMaterial vScmPurchaseMaterial) {
        startPage();
        List<VScmPurchaseMaterial> list = vScmPurchaseMaterialService.selectVScmPurchaseMaterialList(vScmPurchaseMaterial);
        return getDataTable(list);
    }

    /**
     * 导出采购物料列表
     */
    @ApiOperation("导出采购物料列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "文件名")
    })
    @Log(title = "采购物料", businessType = BusinessType.EXPORT)
    @GetMapping("/material/export")
    public AjaxResult export(VScmPurchaseMaterial vScmPurchaseMaterial) {
        List<VScmPurchaseMaterial> list = vScmPurchaseMaterialService.selectVScmPurchaseMaterialList(vScmPurchaseMaterial);
        ExcelUtil<VScmPurchaseMaterial> util = new ExcelUtil<VScmPurchaseMaterial>(VScmPurchaseMaterial.class);
        return util.exportExcel(list, "material");
    }


    /**
     * 查询采购退货列表
     */
    @GetMapping("/return/list")
    @ApiOperation("查询采购退货列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = VScmPurchaseReturn.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list(VScmPurchaseReturn vScmPurchaseReturn) {
        startPage();
        List<VScmPurchaseReturn> list = vScmPurchaseReturnService.selectVScmPurchaseReturnList(vScmPurchaseReturn);
        return getDataTable(list);
    }

    /**
     * 导出采购退货列表
     */
    @ApiOperation("导出采购退货列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "文件名")
    })
    @Log(title = "采购退货", businessType = BusinessType.EXPORT)
    @GetMapping("/return/export")
    public AjaxResult export(VScmPurchaseReturn vScmPurchaseReturn) {
        List<VScmPurchaseReturn> list = vScmPurchaseReturnService.selectVScmPurchaseReturnList(vScmPurchaseReturn);
        ExcelUtil<VScmPurchaseReturn> util = new ExcelUtil<VScmPurchaseReturn>(VScmPurchaseReturn.class);
        return util.exportExcel(list, "return");
    }



    @GetMapping("/contractInwarehouseReturnReport/byMonth")
    @ApiOperation("按月采购-入库-退货报表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = RepContractDeliveryReturnDetail.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo selectContractDeliveryReturnMonthReport(RepContractArriveReturnDetail contractArriveReturnDetail) {
        startPage();
        List<RepContractArriveReturnDetail> list = vScmPurchaseMaterialService.selectContractArriveReturnMonthReport(contractArriveReturnDetail);
        return getDataTable(list);
    }

    @GetMapping("/contractInwarehouseReturnReport/byQuarter")
    @ApiOperation("按季度统计采购-入库-退货报表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = RepContractDeliveryReturnDetail.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo selectContractDeliveryReturnQuarterReport(RepContractArriveReturnDetail contractArriveReturnDetail) {
        startPage();
        List<RepContractArriveReturnDetail> list = vScmPurchaseMaterialService.selectContractArriveReturnQuarterReport(contractArriveReturnDetail);
        return getDataTable(list);
    }


}
