package com.neu.carbon.mes.controller;

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
import com.neu.carbon.mes.domain.MesProductBom;
import com.neu.carbon.mes.domain.MesProductBomDetail;
import com.neu.carbon.mes.service.IMesProductBomService;
import com.neu.carbon.wms.domain.WmsMaterialInfo;
import com.neu.carbon.wms.service.IWmsMaterialInfoService;
import com.neu.common.utils.poi.ExcelUtil;
import com.neu.common.core.page.TableDataInfo;

/**
 * BOM管理Controller
 * 
 * @author neuedu
 * @date 2022-07-11
 */
@Api(tags = {"BOM管理"})
@RestController
@RequestMapping("/mesPlan/mesBom")
public class MesProductBomController extends BaseController
{
    @Autowired
    private IMesProductBomService mesProductBomService;
    @Autowired
    private IWmsMaterialInfoService wmsMaterialInfoService;

    /**
     * 查询BOM管理列表
     */
    @GetMapping("/list")
    @ApiOperation("查询BOM管理列表")
    @DynamicResponseParameters(properties = {
	        @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = MesProductBom.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list(MesProductBom mesProductBom)
    {
        startPage();
        List<MesProductBom> list = mesProductBomService.selectMesProductBomList(mesProductBom);
        list.stream().forEach(bom->{
        	WmsMaterialInfo material = wmsMaterialInfoService.selectWmsMaterialInfoById(bom.getProductId());
        	if(material!=null) {
        		bom.setProductCode(material.getCode());
        		bom.setProductModel(material.getModel());
        		bom.setProductName(material.getName());
        		bom.setProductSpecification(material.getSpecification());
        		bom.setProductUnit(material.getUnit());
        	}
        });
        return getDataTable(list);
    }

    /**
     * 导出BOM管理列表
     */
    @ApiOperation("导出BOM管理列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "文件名")
    })
    @PreAuthorize("@ss.hasPermi('mesPlan:mesBom:export')")
    @Log(title = "BOM管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(MesProductBom mesProductBom)
    {
        List<MesProductBom> list = mesProductBomService.selectMesProductBomList(mesProductBom);
        list.stream().forEach(bom->{
            WmsMaterialInfo material = wmsMaterialInfoService.selectWmsMaterialInfoById(bom.getProductId());
            if(material!=null) {
                bom.setProductCode(material.getCode());
                bom.setProductModel(material.getModel());
                bom.setProductName(material.getName());
                bom.setProductSpecification(material.getSpecification());
                bom.setProductUnit(material.getUnit());
            }
        });
        ExcelUtil<MesProductBom> util = new ExcelUtil<MesProductBom>(MesProductBom.class);
        return util.exportExcel(list, "mesBom");
    }

    /**
     * 获取BOM管理详细信息
     */
    @ApiOperation("获取BOM管理详细信息")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "data", value = "返回业务数据", dataTypeClass = MesProductBom.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
    	MesProductBom bom = mesProductBomService.selectMesProductBomById(id);
    	WmsMaterialInfo product = wmsMaterialInfoService.selectWmsMaterialInfoById(bom.getProductId());
    	if(product!=null) {
    		bom.setProductCode(product.getCode());
    		bom.setProductModel(product.getModel());
    		bom.setProductName(product.getName());
    		bom.setProductSpecification(product.getSpecification());
    		bom.setProductUnit(product.getUnit());
    	}
    	List<MesProductBomDetail> detailList = bom.getMesProductBomDetailList();
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
        return AjaxResult.success(bom);
    }

    /**
     * 新增BOM管理
     */
    @ApiOperation("新增BOM管理")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('mesPlan:mesBom:add')")
    @Log(title = "BOM管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MesProductBom mesProductBom)
    {
        return toAjax(mesProductBomService.insertMesProductBom(mesProductBom));
    }

    /**
     * 修改BOM管理
     */
    @ApiOperation("修改BOM管理")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('mesPlan:mesBom:edit')")
    @Log(title = "BOM管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MesProductBom mesProductBom)
    {
        return toAjax(mesProductBomService.updateMesProductBom(mesProductBom));
    }

    /**
     * 删除BOM管理
     */
    @ApiOperation("删除BOM管理")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('mesPlan:mesBom:remove')")
    @Log(title = "BOM管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(mesProductBomService.deleteMesProductBomByIds(ids));
    }
}
