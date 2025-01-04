package com.neu.carbon.scm.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicResponseParameters;
import com.neu.carbon.scm.domain.ScmSaleContract;
import com.neu.carbon.scm.domain.ScmSaleContractDetail;
import com.neu.carbon.scm.domain.ScmSaleDelivery;
import com.neu.carbon.scm.domain.ScmSaleDeliveryDetail;
import com.neu.carbon.scm.domain.ScmSaleReturn;
import com.neu.carbon.scm.domain.ScmSaleReturnDetail;
import com.neu.carbon.scm.service.IScmSaleContractService;
import com.neu.carbon.scm.service.IScmSaleDeliveryService;
import com.neu.carbon.scm.service.IScmSaleReturnService;
import com.neu.carbon.wms.domain.WmsMaterialInfo;
import com.neu.carbon.wms.service.IWmsMaterialInfoService;
import com.neu.common.annotation.Log;
import com.neu.common.core.controller.BaseController;
import com.neu.common.core.domain.AjaxResult;
import com.neu.common.core.page.TableDataInfo;
import com.neu.common.enums.BusinessType;
import com.neu.common.utils.SecurityUtils;
import com.neu.common.utils.poi.ExcelUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 销售退货Controller
 *
 * @author neuedu
 * @date 2022-07-08
 */
@Api(tags = {"销售退货"})
@RestController
@RequestMapping("/sale/return")
public class ScmSaleReturnController extends BaseController {
    @Autowired
    private IScmSaleReturnService scmSaleReturnService;
    @Autowired
    private IWmsMaterialInfoService wmsMaterialInfoService;
    @Autowired
    private IScmSaleContractService scmSaleContractService;
    @Autowired
    private IScmSaleDeliveryService scmSaleDeliveryService;

    /**
     * 查询销售退货列表
     */
    @GetMapping("/list")
    @ApiOperation("查询销售退货列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = ScmSaleReturn.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list(ScmSaleReturn scmSaleReturn) {
        Long userId = this.getUserId();
        if (!SecurityUtils.isAdmin(userId)) {
            scmSaleReturn.setCreateBy(String.valueOf(userId));
        }
        startPage();
        List<ScmSaleReturn> list = scmSaleReturnService.selectScmSaleReturnList(scmSaleReturn);
        return getDataTable(list);
    }

    /**
     * 导出销售退货列表
     */
    @ApiOperation("导出销售退货列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "文件名")
    })
    @PreAuthorize("@ss.hasPermi('sale:return:export')")
    @Log(title = "销售退货", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ScmSaleReturn scmSaleReturn) {
        List<ScmSaleReturn> list = scmSaleReturnService.selectScmSaleReturnList(scmSaleReturn);
        ExcelUtil<ScmSaleReturn> util = new ExcelUtil<ScmSaleReturn>(ScmSaleReturn.class);
        return util.exportExcel(list, "return");
    }

    /**
     * 获取销售退货详细信息
     */
    @ApiOperation("获取销售退货详细信息")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "data", value = "返回业务数据", dataTypeClass = ScmSaleReturn.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        ScmSaleReturn scmSaleReturn = scmSaleReturnService.selectScmSaleReturnById(id);
        List<ScmSaleReturnDetail> detailList = scmSaleReturn.getScmSaleReturnDetailList();
        if (detailList != null && !detailList.isEmpty()) {
            //获取物料档案信息
            detailList.stream().forEach(detail -> {
                WmsMaterialInfo material = wmsMaterialInfoService.selectWmsMaterialInfoById(detail.getMaterialId());
                if (material != null) {
                    detail.setMaterialCode(material.getCode());
                    detail.setMaterialModel(material.getModel());
                    detail.setMaterialName(material.getName());
                    detail.setMaterialPrice(material.getPrice());
                    detail.setMaterialSpecification(material.getSpecification());
                    detail.setMaterialUnit(material.getUnit());
                }
            });
        }
        return AjaxResult.success(scmSaleReturn);
    }

    /**
     * 新增销售退货
     */
    @ApiOperation("新增销售退货")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('sale:return:add')")
    @Log(title = "销售退货", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ScmSaleReturn scmSaleReturn) {
        return toAjax(scmSaleReturnService.insertScmSaleReturn(scmSaleReturn));
    }

    /**
     * 修改销售退货
     */
    @ApiOperation("修改销售退货")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('sale:return:edit')")
    @Log(title = "销售退货", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ScmSaleReturn scmSaleReturn) {
        return toAjax(scmSaleReturnService.updateScmSaleReturn(scmSaleReturn));
    }

    /**
     * 删除销售退货
     */
    @ApiOperation("删除销售退货")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('sale:return:remove')")
    @Log(title = "销售退货", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(scmSaleReturnService.deleteScmSaleReturnByIds(ids));
    }

    @GetMapping("/detailList/{deliveryId}")
    @ApiOperation("根据发货单id查询发货单详情列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = ScmSaleReturnDetail.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public AjaxResult assembleDetailList(@PathVariable("deliveryId") Long deliveryId) {
        List<ScmSaleReturnDetail> saleReturnDetailList = new ArrayList<>();
        // 发货单信息
        ScmSaleDelivery delivery = scmSaleDeliveryService.selectScmSaleDeliveryById(deliveryId);
        List<ScmSaleDeliveryDetail> deliveryDetailList = delivery.getScmSaleDeliveryDetailList();
        // 合同信息
        ScmSaleContract contract = scmSaleContractService.selectScmSaleContractById(delivery.getContractId());
        List<ScmSaleContractDetail> contractDetailList = contract.getScmSaleContractDetailList();
        for (ScmSaleDeliveryDetail deliveryDetail : deliveryDetailList) {
            ScmSaleReturnDetail returnDetail = new ScmSaleReturnDetail();
            returnDetail.setMaterialId(deliveryDetail.getMaterialId());
            returnDetail.setDeliveryQuantity(deliveryDetail.getQuantity());
            for (ScmSaleContractDetail contractDetail : contractDetailList) {
                if (deliveryDetail.getMaterialId().equals(contractDetail.getMaterialId())) {
                    returnDetail.setBookQuantity(contractDetail.getQuantity());
                    returnDetail.setPrice(contractDetail.getPrice());
                    break;
                }
            }
            WmsMaterialInfo material = wmsMaterialInfoService.selectWmsMaterialInfoById(deliveryDetail.getMaterialId());
            if (material != null) {
                returnDetail.setMaterialCode(material.getCode());
                returnDetail.setMaterialModel(material.getModel());
                returnDetail.setMaterialName(material.getName());
                returnDetail.setMaterialPrice(material.getPrice());
                returnDetail.setMaterialSpecification(material.getSpecification());
                returnDetail.setMaterialUnit(material.getUnit());
            }
            saleReturnDetailList.add(returnDetail);
        }
        return AjaxResult.success(saleReturnDetailList);
    }


}
