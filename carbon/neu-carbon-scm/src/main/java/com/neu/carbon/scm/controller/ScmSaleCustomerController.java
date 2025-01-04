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
import com.neu.carbon.scm.domain.ScmSaleCustomer;
import com.neu.carbon.scm.service.IScmSaleCustomerService;
import com.neu.common.utils.poi.ExcelUtil;
import com.neu.common.core.page.TableDataInfo;

/**
 * 客户档案Controller
 * 
 * @author neuedu
 * @date 2022-07-02
 */
@Api(tags = {"客户档案"})
@RestController
@RequestMapping("/sale/customer")
public class ScmSaleCustomerController extends BaseController
{
    @Autowired
    private IScmSaleCustomerService scmSaleCustomerService;

    /**
     * 查询客户档案列表
     */
    @GetMapping("/list")
    @ApiOperation("查询客户档案列表")
    @DynamicResponseParameters(properties = {
	        @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = ScmSaleCustomer.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list(ScmSaleCustomer scmSaleCustomer)
    {
        startPage();
        List<ScmSaleCustomer> list = scmSaleCustomerService.selectScmSaleCustomerList(scmSaleCustomer);
        return getDataTable(list);
    }

    /**
     * 导出客户档案列表
     */
    @ApiOperation("导出客户档案列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "文件名")
    })
    @PreAuthorize("@ss.hasPermi('sale:customer:export')")
    @Log(title = "客户档案", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ScmSaleCustomer scmSaleCustomer)
    {
        List<ScmSaleCustomer> list = scmSaleCustomerService.selectScmSaleCustomerList(scmSaleCustomer);
        ExcelUtil<ScmSaleCustomer> util = new ExcelUtil<ScmSaleCustomer>(ScmSaleCustomer.class);
        return util.exportExcel(list, "customer");
    }

    /**
     * 获取客户档案详细信息
     */
    @ApiOperation("获取客户档案详细信息")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "data", value = "返回业务数据", dataTypeClass = ScmSaleCustomer.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(scmSaleCustomerService.selectScmSaleCustomerById(id));
    }

    /**
     * 新增客户档案
     */
    @ApiOperation("新增客户档案")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('sale:customer:add')")
    @Log(title = "客户档案", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ScmSaleCustomer scmSaleCustomer)
    {
        return toAjax(scmSaleCustomerService.insertScmSaleCustomer(scmSaleCustomer));
    }

    /**
     * 修改客户档案
     */
    @ApiOperation("修改客户档案")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('sale:customer:edit')")
    @Log(title = "客户档案", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ScmSaleCustomer scmSaleCustomer)
    {
        return toAjax(scmSaleCustomerService.updateScmSaleCustomer(scmSaleCustomer));
    }

    /**
     * 删除客户档案
     */
    @ApiOperation("删除客户档案")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('sale:customer:remove')")
    @Log(title = "客户档案", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(scmSaleCustomerService.deleteScmSaleCustomerByIds(ids));
    }
}
