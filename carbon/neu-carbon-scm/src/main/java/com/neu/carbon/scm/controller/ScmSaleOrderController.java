package com.neu.carbon.scm.controller;

import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicResponseParameters;
import com.neu.carbon.scm.domain.ScmSaleCustomer;
import com.neu.carbon.scm.domain.ScmSaleOrder;
import com.neu.carbon.scm.domain.ScmSaleOrderDetail;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 销售订单Controller
 *
 * @author neuedu
 * @date 2022-07-03
 */
@Api(tags = {"销售订单"})
@RestController
@RequestMapping("/sale/order")
public class ScmSaleOrderController extends BaseController {
    @Autowired
    private IScmSaleOrderService scmSaleOrderService;
    @Autowired
    private IWmsMaterialInfoService wmsMaterialInfoService;
    @Autowired
    private IScmSaleCustomerService scmSaleCustomerService;

    /**
     * 查询销售订单列表
     */
    @GetMapping("/list")
    @ApiOperation("查询销售订单列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = ScmSaleOrder.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list(ScmSaleOrder scmSaleOrder) {
        Long userId = this.getUserId();
        if (!SecurityUtils.isAdmin(userId)) {
            scmSaleOrder.setApplyUser(String.valueOf(userId));
        }
        startPage();
        List<ScmSaleOrder> list = scmSaleOrderService.selectScmSaleOrderList(scmSaleOrder);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('purchase:plan:audit')")
    @GetMapping("/audit/list")
    @ApiOperation("查询待审核和已审核销售订单列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = ScmSaleOrder.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list4Audit(ScmSaleOrder scmSaleOrder) {
        //默认查询待审核
        List<String> applyStatusList = new ArrayList<>();
        applyStatusList.add(UserConstants.APPLY_STATUS_WAIT_AUDIT);
        applyStatusList.add(UserConstants.APPLY_STATUS_HAS_AUDIT);
        scmSaleOrder.getParams().put("applyStatusList", applyStatusList);
        if(StringUtils.isBlank(scmSaleOrder.getApplyStatus())&&StringUtils.isBlank(scmSaleOrder.getAuditStatus())) {
            scmSaleOrder.setApplyStatus(UserConstants.APPLY_STATUS_WAIT_AUDIT);
        }
        startPage();
        List<ScmSaleOrder> list = scmSaleOrderService.selectScmSaleOrderList(scmSaleOrder);
        return getDataTable(list);
    }

    /**
     * 导出销售订单列表
     */
    @ApiOperation("导出销售订单列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "文件名")
    })
    @PreAuthorize("@ss.hasPermi('sale:order:export')")
    @Log(title = "销售订单", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ScmSaleOrder scmSaleOrder) {
        List<ScmSaleOrder> list = scmSaleOrderService.selectScmSaleOrderList(scmSaleOrder);
        ExcelUtil<ScmSaleOrder> util = new ExcelUtil<ScmSaleOrder>(ScmSaleOrder.class);
        return util.exportExcel(list, "order");
    }

    /**
     * 获取销售订单详细信息
     */
    @ApiOperation("获取销售订单详细信息")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "data", value = "返回业务数据", dataTypeClass = ScmSaleOrder.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        ScmSaleOrder saleOrder = scmSaleOrderService.selectScmSaleOrderById(id);
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
            saleOrder.setContact(customer.getContact());
            saleOrder.setContactTel(customer.getContactTel());
        }
        return AjaxResult.success(saleOrder);
    }

    /**
     * 新增销售订单
     */
    @ApiOperation("新增销售订单")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('sale:order:add')")
    @Log(title = "销售订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ScmSaleOrder scmSaleOrder) {
        scmSaleOrder.setApplyUser(String.valueOf(this.getUserId()));
        scmSaleOrder.setApplyStatus(UserConstants.APPLY_STATUS_NO_SUBMIT);
        scmSaleOrder.setApplyTime(new Date());
        return toAjax(scmSaleOrderService.insertScmSaleOrder(scmSaleOrder));
    }

    /**
     * 修改销售订单
     */
    @ApiOperation("修改销售订单")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('sale:order:edit')")
    @Log(title = "销售订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ScmSaleOrder scmSaleOrder) {
        scmSaleOrder.setApplyStatus(UserConstants.APPLY_STATUS_NO_SUBMIT);
        scmSaleOrder.setAuditUser("");
        scmSaleOrder.setAuditStatus("");
        scmSaleOrder.setAuditTime(null);
        scmSaleOrder.setAuditComment("");
        return toAjax(scmSaleOrderService.updateScmSaleOrder(scmSaleOrder));
    }

    /**
     * 删除销售订单
     */
    @ApiOperation("删除销售订单")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('sale:order:remove')")
    @Log(title = "销售订单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(scmSaleOrderService.deleteScmSaleOrderByIds(ids));
    }

    @ApiOperation("提交销售订单")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('sale:order:edit')")
    @Log(title = "提交销售订单", businessType = BusinessType.UPDATE)
    @PutMapping("/submit")
    public AjaxResult submit(@RequestBody ScmSaleOrder scmSaleOrder) {
        scmSaleOrder.setApplyStatus(UserConstants.APPLY_STATUS_WAIT_AUDIT);
        scmSaleOrder.setApplyTime(DateUtils.getNowDate());
        if (scmSaleOrder.getId() != null) {
            ScmSaleOrder order = scmSaleOrderService.selectScmSaleOrderById(scmSaleOrder.getId());
            if (order == null) {
                return AjaxResult.error("此销售订单不存在，不能提交！");
            }
            scmSaleOrder.setAuditUser("");
            scmSaleOrder.setAuditStatus("");
            scmSaleOrder.setAuditTime(null);
            scmSaleOrder.setAuditComment("");
            return toAjax(scmSaleOrderService.updateScmSaleOrder(scmSaleOrder));
        } else {
            scmSaleOrder.setApplyUser(String.valueOf(this.getUserId()));
            scmSaleOrder.setApplyTime(new Date());
            return toAjax(scmSaleOrderService.insertScmSaleOrder(scmSaleOrder));
        }
    }

    @ApiOperation("审核销售订单")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('sale:order:audit')")
    @Log(title = "销售订单审核", businessType = BusinessType.UPDATE)
    @PutMapping("/audit")
    public AjaxResult audit(@RequestBody ScmSaleOrder scmSaleOrder) {
        scmSaleOrder.setApplyStatus(UserConstants.APPLY_STATUS_HAS_AUDIT);
        scmSaleOrder.setAuditTime(DateUtils.getNowDate());
        Long loginUserId = SecurityUtils.getLoginUser().getUser().getUserId();
        scmSaleOrder.setAuditUser(String.valueOf(loginUserId));
        return toAjax(scmSaleOrderService.updateScmSaleOrder(scmSaleOrder));
    }


}
