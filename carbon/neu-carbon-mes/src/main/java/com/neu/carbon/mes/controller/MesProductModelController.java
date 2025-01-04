package com.neu.carbon.mes.controller;

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
import com.neu.carbon.mes.domain.MesProductModel;
import com.neu.carbon.mes.domain.MesProductModelDetail;
import com.neu.carbon.mes.service.IMesProductModelService;
import com.neu.carbon.wms.domain.WmsMaterialInfo;
import com.neu.carbon.wms.service.IWmsMaterialInfoService;
import com.neu.common.annotation.Log;
import com.neu.common.core.controller.BaseController;
import com.neu.common.core.domain.AjaxResult;
import com.neu.common.core.page.TableDataInfo;
import com.neu.common.enums.BusinessType;
import com.neu.common.utils.poi.ExcelUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 产品建模Controller
 * 
 * @author neuedu
 * @date 2022-07-08
 */
@Api(tags = {"产品建模"})
@RestController
@RequestMapping("/mesModel/productModel")
public class MesProductModelController extends BaseController
{
    @Autowired
    private IMesProductModelService mesProductModelService;
    @Autowired
    private IWmsMaterialInfoService wmsMaterialInfoService;

    /**
     * 查询产品建模列表
     */
    @GetMapping("/list")
    @ApiOperation("查询产品建模列表")
    @DynamicResponseParameters(properties = {
	        @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = MesProductModel.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list(MesProductModel mesProductModel)
    {
        startPage();
        List<MesProductModel> list = mesProductModelService.selectMesProductModelList(mesProductModel);
        list.stream().forEach(model->{
        	WmsMaterialInfo material = wmsMaterialInfoService.selectWmsMaterialInfoById(model.getProductId());
        	if(material!=null) {
        		model.setProductCode(material.getCode());
        		model.setProductModel(material.getModel());
        		model.setProductName(material.getName());
        		model.setProductSpecification(material.getSpecification());
        		model.setProductUnit(material.getUnit());
        	}
        });
        return getDataTable(list);
    }

    /**
     * 导出产品建模列表
     */
    @ApiOperation("导出产品建模列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "文件名")
    })
    @PreAuthorize("@ss.hasPermi('mesModel:productModel:export')")
    @Log(title = "产品建模", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(MesProductModel mesProductModel)
    {
        List<MesProductModel> list = mesProductModelService.selectMesProductModelList(mesProductModel);
        list.stream().forEach(model->{
            WmsMaterialInfo material = wmsMaterialInfoService.selectWmsMaterialInfoById(model.getProductId());
            if(material!=null) {
                model.setProductCode(material.getCode());
                model.setProductModel(material.getModel());
                model.setProductName(material.getName());
                model.setProductSpecification(material.getSpecification());
                model.setProductUnit(material.getUnit());
            }
        });
        ExcelUtil<MesProductModel> util = new ExcelUtil<MesProductModel>(MesProductModel.class);
        return util.exportExcel(list, "productModel");
    }

    /**
     * 获取产品建模详细信息
     */
    @ApiOperation("获取产品建模详细信息")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "data", value = "返回业务数据", dataTypeClass = MesProductModel.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
    	MesProductModel model = mesProductModelService.selectMesProductModelById(id);
    	WmsMaterialInfo product = wmsMaterialInfoService.selectWmsMaterialInfoById(model.getProductId());
    	if(product!=null) {
    		model.setProductCode(product.getCode());
    		model.setProductModel(product.getModel());
    		model.setProductName(product.getName());
    		model.setProductSpecification(product.getSpecification());
    		model.setProductUnit(product.getUnit());
    	}
    	List<MesProductModelDetail> detailList = model.getMesProductModelDetailList();
    	detailList.stream().forEach(detail->{
    		WmsMaterialInfo material = wmsMaterialInfoService.selectWmsMaterialInfoById(detail.getMaterialId());
        	if(material!=null) {
        		detail.setMaterialCode(material.getCode());
        		detail.setMaterialModel(material.getModel());
        		detail.setMaterialName(material.getName());
        		detail.setMaterialSpecification(material.getSpecification());
        		detail.setMaterialUnit(material.getUnit());
        	}
    	});
        return AjaxResult.success(model);
    }

    /**
     * 新增产品建模
     */
    @ApiOperation("新增产品建模")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('mesModel:productModel:add')")
    @Log(title = "产品建模", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MesProductModel mesProductModel)
    {
        return toAjax(mesProductModelService.insertMesProductModel(mesProductModel));
    }

    /**
     * 修改产品建模
     */
    @ApiOperation("修改产品建模")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('mesModel:productModel:edit')")
    @Log(title = "产品建模", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MesProductModel mesProductModel)
    {
        return toAjax(mesProductModelService.updateMesProductModel(mesProductModel));
    }

    /**
     * 删除产品建模
     */
    @ApiOperation("删除产品建模")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('mesModel:productModel:remove')")
    @Log(title = "产品建模", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(mesProductModelService.deleteMesProductModelByIds(ids));
    }
}
