package com.neu.carbon.report.controller;

import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicResponseParameters;
import com.neu.carbon.scm.domain.RepContractDeliveryReturnDetail;
import com.neu.carbon.scm.domain.ScmSalePlan;
import com.neu.carbon.scm.domain.VScmSaleContract;
import com.neu.carbon.scm.domain.VScmSaleContractDetail;
import com.neu.carbon.scm.service.IVScmSaleContractDetailService;
import com.neu.carbon.scm.service.IVScmSaleContractService;
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
 * 采购报表统计Controller
 *
 * @author neuedu
 * @date 2022-07-25
 */
@Api(tags = {"销售报表统计"})
@RestController
@RequestMapping("/report/scmSale")
public class ScmSaleReportController extends BaseController {
    @Autowired
    private IVScmSaleContractDetailService vScmSaleContractDetailService;
    @Autowired
    private IVScmSaleContractService vScmSaleContractService;

    /**
     * 查询销售合同明细列表
     */
    @GetMapping("/contractDetail/list")
    @ApiOperation("查询销售合同明细列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = VScmSaleContractDetail.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list(VScmSaleContractDetail vScmSaleContractDetail) {
        startPage();
        List<VScmSaleContractDetail> list = vScmSaleContractDetailService.selectVScmSaleContractDetailList(vScmSaleContractDetail);
        return getDataTable(list);
    }

    /**
     * 导出销售合同明细列表
     */
    @ApiOperation("导出销售合同明细列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "文件名")
    })
    @Log(title = "销售合同明细", businessType = BusinessType.EXPORT)
    @GetMapping("/contractDetail/export")
    public AjaxResult export(VScmSaleContractDetail vScmSaleContractDetail) {
        List<VScmSaleContractDetail> list = vScmSaleContractDetailService.selectVScmSaleContractDetailList(vScmSaleContractDetail);
        ExcelUtil<VScmSaleContractDetail> util = new ExcelUtil<VScmSaleContractDetail>(VScmSaleContractDetail.class);
        return util.exportExcel(list, "contractDetail");
    }


    /**
     * 查询销售合同明细列表
     */
    @GetMapping("/saleBook/list")
    @ApiOperation("查询销售合同明细列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = VScmSaleContractDetail.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo saleBookList(VScmSaleContractDetail vScmSaleContractDetail) {
        startPage();
        List<VScmSaleContractDetail> list = vScmSaleContractDetailService.selectSaleBookReport(vScmSaleContractDetail);
        return getDataTable(list);
    }

    /**
     * 导出销售合同明细列表
     */
    @ApiOperation("导出销售合同明细列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "文件名")
    })
    @Log(title = "销售合同明细", businessType = BusinessType.EXPORT)
    @GetMapping("/saleBook/export")
    public AjaxResult exportSaleBook(VScmSaleContractDetail vScmSaleContractDetail) {
        List<VScmSaleContractDetail> list = vScmSaleContractDetailService.selectSaleBookReport(vScmSaleContractDetail);
        ExcelUtil<VScmSaleContractDetail> util = new ExcelUtil<VScmSaleContractDetail>(VScmSaleContractDetail.class);
        return util.exportExcel(list, "saleBook");
    }


    /**
     * 查询月销售合同报表列表
     */
    @GetMapping("/saleContractReport/byMonth")
    @ApiOperation("查询销售合同报表列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = VScmSaleContract.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo selectMonthSaleContractReport(VScmSaleContract vScmSaleContract) {
        startPage();
        List<VScmSaleContract> list = vScmSaleContractService.selectMonthSaleContractReport(vScmSaleContract);
        return getDataTable(list);
    }

    /**
     * 导出销售合同报表列表
     */
    @ApiOperation("导出销售合同报表列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "文件名")
    })
    @Log(title = "销售合同报表", businessType = BusinessType.EXPORT)
    @GetMapping("/saleContractReport/month/export")
    public AjaxResult export(VScmSaleContract vScmSaleContract) {
        List<VScmSaleContract> list = vScmSaleContractService.selectVScmSaleContractList(vScmSaleContract);
        ExcelUtil<VScmSaleContract> util = new ExcelUtil<VScmSaleContract>(VScmSaleContract.class);
        return util.exportExcel(list, "saleContractReport");
    }

    @GetMapping("/contractDeliveryReturnReport/byMonth")
    @ApiOperation("按月份统计销售-发货-退货报表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = RepContractDeliveryReturnDetail.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo selectContractDeliveryReturnMonthReport(RepContractDeliveryReturnDetail contractDeliveryReturnDetail) {
        startPage();
        List<RepContractDeliveryReturnDetail> list = vScmSaleContractDetailService.selectContractDeliveryReturnMonthReport(contractDeliveryReturnDetail);
        return getDataTable(list);
    }

    @GetMapping("/contractDeliveryReturnReport/byQuarter")
    @ApiOperation("按季度统计销售-发货-退货报表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = RepContractDeliveryReturnDetail.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo selectContractDeliveryReturnQuarterReport(RepContractDeliveryReturnDetail contractDeliveryReturnDetail) {
        startPage();
        List<RepContractDeliveryReturnDetail> list = vScmSaleContractDetailService.selectContractDeliveryReturnQuarterReport(contractDeliveryReturnDetail);
        return getDataTable(list);
    }




}
