package com.neu.carbon.scm.controller;

import java.util.ArrayList;
import java.util.Date;
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
import com.neu.carbon.scm.domain.ScmSaleCustomer;
import com.neu.carbon.scm.domain.ScmSaleOrder;
import com.neu.carbon.scm.domain.ScmSaleOrderDetail;
import com.neu.carbon.scm.service.IScmSaleContractService;
import com.neu.carbon.scm.service.IScmSaleCustomerService;
import com.neu.carbon.scm.service.IScmSaleOrderService;
import com.neu.carbon.wms.domain.WmsMaterialInfo;
import com.neu.carbon.wms.service.IWmsMaterialInfoService;
import com.neu.common.annotation.Log;
import com.neu.common.constant.UserConstants;
import com.neu.common.core.controller.BaseController;
import com.neu.common.core.domain.AjaxResult;
import com.neu.common.core.page.TableDataInfo;
import com.neu.common.enums.BusinessType;
import com.neu.common.utils.DateUtils;
import com.neu.common.utils.SecurityUtils;
import com.neu.common.utils.StringUtils;
import com.neu.common.utils.poi.ExcelUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 销售合同Controller
 *
 * @author neuedu
 * @date 2022-07-04
 */
@Api(tags = {"销售合同"})
@RestController
@RequestMapping("/sale/contract")
public class ScmSaleContractController extends BaseController {
    @Autowired
    private IScmSaleContractService scmSaleContractService;
    @Autowired
    private IWmsMaterialInfoService wmsMaterialInfoService;
    @Autowired
    private IScmSaleCustomerService scmSaleCustomerService;
    @Autowired
    private IScmSaleOrderService scmSaleOrderService;

    /**
     * 查询销售合同列表
     */
    @GetMapping("/list")
    @ApiOperation("查询销售合同列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = ScmSaleContract.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list(ScmSaleContract scmSaleContract) {
        Long userId = this.getUserId();
        if (!SecurityUtils.isAdmin(userId)) {
            scmSaleContract.setApplyUser(String.valueOf(userId));
        }
        startPage();
        List<ScmSaleContract> list = scmSaleContractService.selectScmSaleContractList(scmSaleContract);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('sale:contract:audit')")
    @GetMapping("/audit/list")
    @ApiOperation("查询待审核和已审核销售合同列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = ScmSaleContract.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list4Audit(ScmSaleContract scmSaleContract) {
        //默认查询待审核
        List<String> applyStatusList = new ArrayList<>();
        applyStatusList.add(UserConstants.APPLY_STATUS_WAIT_AUDIT);
        applyStatusList.add(UserConstants.APPLY_STATUS_HAS_AUDIT);
        scmSaleContract.getParams().put("applyStatusList", applyStatusList);
        if (StringUtils.isBlank(scmSaleContract.getApplyStatus()) && StringUtils.isBlank(scmSaleContract.getAuditStatus())) {
            scmSaleContract.setApplyStatus(UserConstants.APPLY_STATUS_WAIT_AUDIT);
        }
        startPage();
        List<ScmSaleContract> list = scmSaleContractService.selectScmSaleContractList(scmSaleContract);
        return getDataTable(list);
    }

    /**
     * 导出销售合同列表
     */
    @ApiOperation("导出销售合同列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "文件名")
    })
    @PreAuthorize("@ss.hasPermi('sale:contract:export')")
    @Log(title = "销售合同", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ScmSaleContract scmSaleContract) {
        List<ScmSaleContract> list = scmSaleContractService.selectScmSaleContractList(scmSaleContract);
        ExcelUtil<ScmSaleContract> util = new ExcelUtil<ScmSaleContract>(ScmSaleContract.class);
        return util.exportExcel(list, "contract");
    }

    /**
     * 获取销售合同详细信息
     */
    @ApiOperation("获取销售合同详细信息")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "data", value = "返回业务数据", dataTypeClass = ScmSaleContract.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        ScmSaleContract contract = scmSaleContractService.selectScmSaleContractById(id);
        List<ScmSaleContractDetail> detailList = contract.getScmSaleContractDetailList();
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
        ScmSaleCustomer customer = scmSaleCustomerService.selectScmSaleCustomerById(contract.getCustomerId());
        if (customer != null) {
            contract.setCustomerName(customer.getName());
        }
        return AjaxResult.success(contract);
    }

    /**
     * 新增销售合同
     */
    @ApiOperation("新增销售合同")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('sale:contract:add')")
    @Log(title = "销售合同", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ScmSaleContract scmSaleContract) {
        scmSaleContract.setApplyUser(String.valueOf(this.getUserId()));
        scmSaleContract.setApplyStatus(UserConstants.APPLY_STATUS_NO_SUBMIT);
        scmSaleContract.setApplyTime(new Date());
        scmSaleContract.setWorkStaff(String.valueOf(this.getUserId()));
        return toAjax(scmSaleContractService.insertScmSaleContract(scmSaleContract));
    }

    /**
     * 修改销售合同
     */
    @ApiOperation("修改销售合同")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('sale:contract:edit')")
    @Log(title = "销售合同", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ScmSaleContract scmSaleContract) {
        scmSaleContract.setApplyStatus(UserConstants.APPLY_STATUS_NO_SUBMIT);
        scmSaleContract.setAuditUser("");
        scmSaleContract.setAuditStatus("");
        scmSaleContract.setAuditTime(null);
        scmSaleContract.setAuditComment("");
        return toAjax(scmSaleContractService.updateScmSaleContract(scmSaleContract));
    }

    /**
     * 删除销售合同
     */
    @ApiOperation("删除销售合同")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('sale:contract:remove')")
    @Log(title = "销售合同", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(scmSaleContractService.deleteScmSaleContractByIds(ids));
    }

    @ApiOperation("提交销售合同")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('sale:contract:edit')")
    @Log(title = "提交销售合同", businessType = BusinessType.UPDATE)
    @PutMapping("/submit")
    public AjaxResult submit(@RequestBody ScmSaleContract scmSaleContract) {
        scmSaleContract.setApplyStatus(UserConstants.APPLY_STATUS_WAIT_AUDIT);
        scmSaleContract.setApplyTime(DateUtils.getNowDate());
        if (scmSaleContract.getId() != null) {
            ScmSaleContract contract = scmSaleContractService.selectScmSaleContractById(scmSaleContract.getId());
            if (contract == null) {
                return AjaxResult.error("此销售合同不存在，不能提交！");
            }
            scmSaleContract.setAuditUser("");
            scmSaleContract.setAuditStatus("");
            scmSaleContract.setAuditTime(null);
            scmSaleContract.setAuditComment("");
            return toAjax(scmSaleContractService.updateScmSaleContract(scmSaleContract));
        } else {
            scmSaleContract.setApplyUser(String.valueOf(this.getUserId()));
            scmSaleContract.setApplyTime(new Date());
            return toAjax(scmSaleContractService.insertScmSaleContract(scmSaleContract));
        }
    }

    @ApiOperation("审核销售合同")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('sale:contract:audit')")
    @Log(title = "销售合同审核", businessType = BusinessType.UPDATE)
    @PutMapping("/audit")
    public AjaxResult audit(@RequestBody ScmSaleContract scmSaleContract) {
        scmSaleContract.setApplyStatus(UserConstants.APPLY_STATUS_HAS_AUDIT);
        scmSaleContract.setAuditTime(DateUtils.getNowDate());
        Long loginUserId = SecurityUtils.getLoginUser().getUser().getUserId();
        scmSaleContract.setAuditUser(String.valueOf(loginUserId));
        return toAjax(scmSaleContractService.updateScmSaleContract(scmSaleContract));
    }


    @ApiOperation("生成销售发货单")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('sale:contract:edit')")
    @Log(title = "生成销售发货单", businessType = BusinessType.INSERT)
    @PostMapping("/genDelivery")
    public AjaxResult genDelivery(@RequestBody ScmSaleContract scmSaleContract) {
        return toAjax(scmSaleContractService.genDelivery(scmSaleContract));
    }


    @GetMapping("/genContractInfo/{orderId}")
    @ApiOperation("根据订单封装采购合同信息")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = ScmSaleContract.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public AjaxResult genContractInfoByOrderId(@PathVariable("orderId") Long orderId) {
        ScmSaleOrder saleOrder = scmSaleOrderService.selectScmSaleOrderById(orderId);
        List<ScmSaleOrderDetail> orderDetailList = saleOrder.getScmSaleOrderDetailList();
        if (orderDetailList != null && !orderDetailList.isEmpty()) {
            //获取物料档案信息
            orderDetailList.stream().forEach(detail -> {
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
        ScmSaleCustomer customer = scmSaleCustomerService.selectScmSaleCustomerById(saleOrder.getCustomerId());
        if (customer != null) {
            saleOrder.setCustomerName(customer.getName());
            saleOrder.setAddress(customer.getAddr());
            saleOrder.setContact(customer.getContact());
            saleOrder.setContactTel(customer.getContactTel());
        }
        ScmSaleContract saleContract = assembleContract(saleOrder);
        return AjaxResult.success(saleContract);
    }

    /**
     * 根据销售订单封装销售合同
     *
     * @param saleOrder
     * @return
     */
    private ScmSaleContract assembleContract(ScmSaleOrder saleOrder) {
        ScmSaleContract saleContract = new ScmSaleContract();
        saleContract.setAddress(saleOrder.getAddress());
        saleContract.setCustomerName(saleOrder.getCustomerName());
        saleContract.setAddress(saleOrder.getAddress());
        saleContract.setContact(saleOrder.getContact());
        saleContract.setContactTel(saleOrder.getContactTel());
        saleContract.setDeliveryDate(saleOrder.getDeliveryDate());
        List<ScmSaleContractDetail> contractDetailList = new ArrayList<>();
        List<ScmSaleOrderDetail> orderDetailList = saleOrder.getScmSaleOrderDetailList();
        if (orderDetailList != null && !orderDetailList.isEmpty()) {
            for (ScmSaleOrderDetail orderDetail : orderDetailList) {
                ScmSaleContractDetail contractDetail = new ScmSaleContractDetail();
                contractDetail.setMaterialId(orderDetail.getMaterialId());
                contractDetail.setPrice(orderDetail.getPrice());
                contractDetail.setQuantity(orderDetail.getQuantity());
                contractDetail.setAmount(orderDetail.getAmount());
                contractDetail.setDetailRemark(orderDetail.getDetailRemark());
                contractDetail.setMaterialCode(orderDetail.getMaterialCode());
                contractDetail.setMaterialModel(orderDetail.getMaterialModel());
                contractDetail.setMaterialSpecification(orderDetail.getMaterialSpecification());
                contractDetail.setMaterialName(orderDetail.getMaterialName());
                contractDetail.setMaterialUnit(orderDetail.getMaterialUnit());
                contractDetailList.add(contractDetail);
            }
        }
        saleContract.setScmSaleContractDetailList(contractDetailList);
        return saleContract;
    }

}
