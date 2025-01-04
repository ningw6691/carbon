package com.neu.carbon.wms.controller;

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
import com.neu.carbon.wms.domain.WmsDeliveryBill;
import com.neu.carbon.wms.service.IWmsDeliveryBillService;
import com.neu.common.annotation.Log;
import com.neu.common.core.controller.BaseController;
import com.neu.common.core.domain.AjaxResult;
import com.neu.common.core.page.TableDataInfo;
import com.neu.common.enums.BusinessType;
import com.neu.common.utils.poi.ExcelUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 配送单Controller
 * 
 * @author neuedu
 * @date 2022-07-05
 */
@Api(tags = {"配送单"})
@RestController
@RequestMapping("/transportApply/deliveryBill")
public class WmsDeliveryBillController extends BaseController
{
    @Autowired
    private IWmsDeliveryBillService wmsDeliveryBillService;

    /**
     * 查询配送单列表
     */
    @GetMapping("/list")
    @ApiOperation("查询配送单列表")
    @DynamicResponseParameters(properties = {
	        @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = WmsDeliveryBill.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list(WmsDeliveryBill wmsDeliveryBill)
    {
        startPage();
        List<WmsDeliveryBill> list = wmsDeliveryBillService.selectWmsDeliveryBillList(wmsDeliveryBill);
        return getDataTable(list);
    }

    /**
     * 导出配送单列表
     */
    @ApiOperation("导出配送单列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "文件名")
    })
    @PreAuthorize("@ss.hasPermi('transportApply:deliveryBill:export')")
    @Log(title = "配送单", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(WmsDeliveryBill wmsDeliveryBill)
    {
        List<WmsDeliveryBill> list = wmsDeliveryBillService.selectWmsDeliveryBillList(wmsDeliveryBill);
        ExcelUtil<WmsDeliveryBill> util = new ExcelUtil<WmsDeliveryBill>(WmsDeliveryBill.class);
        return util.exportExcel(list, "deliveryBill");
    }

    /**
     * 获取配送单详细信息
     */
    @ApiOperation("获取配送单详细信息")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "data", value = "返回业务数据", dataTypeClass = WmsDeliveryBill.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(wmsDeliveryBillService.selectWmsDeliveryBillById(id));
    }

    /**
     * 新增配送单
     */
    @ApiOperation("新增配送单")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('transportApply:deliveryBill:add')")
    @Log(title = "配送单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WmsDeliveryBill wmsDeliveryBill)
    {
    	WmsDeliveryBill cond = new WmsDeliveryBill();
    	cond.setPlateNo(wmsDeliveryBill.getPlateNo());
    	cond.setDispatchBillId(wmsDeliveryBill.getDispatchBillId());
    	List<WmsDeliveryBill> list = wmsDeliveryBillService.selectWmsDeliveryBillList(cond);
    	if(list!=null&&!list.isEmpty()) {
    		return AjaxResult.error("车辆：["+wmsDeliveryBill.getPlateNo()+"]已有配送单，无需再添加");
    	}
        return toAjax(wmsDeliveryBillService.insertWmsDeliveryBill(wmsDeliveryBill));
    }

    /**
     * 修改配送单
     */
    @ApiOperation("修改配送单")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('transportApply:deliveryBill:edit')")
    @Log(title = "配送单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WmsDeliveryBill wmsDeliveryBill)
    {
        return toAjax(wmsDeliveryBillService.updateWmsDeliveryBill(wmsDeliveryBill));
    }
    
    /**
     * 修改配送单状态
     */
    @ApiOperation("修改配送单状态")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('transportApply:deliveryBill:edit')")
    @Log(title = "配送单", businessType = BusinessType.UPDATE)
    @PostMapping("/update/status")
    public AjaxResult updateStatus(@RequestBody WmsDeliveryBill wmsDeliveryBill)
    {
    	WmsDeliveryBill bill = wmsDeliveryBillService.selectWmsDeliveryBillById(wmsDeliveryBill.getId());
    	bill.setBillStatus(wmsDeliveryBill.getBillStatus());
    	if("1".equals(wmsDeliveryBill.getBillStatus())) {
    		bill.setArriveTime(new Date());
    	}
        return toAjax(wmsDeliveryBillService.updateWmsDeliveryBill(bill));
    }

    /**
     * 删除配送单
     */
    @ApiOperation("删除配送单")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('transportApply:deliveryBill:remove')")
    @Log(title = "配送单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(wmsDeliveryBillService.deleteWmsDeliveryBillByIds(ids));
    }
}
