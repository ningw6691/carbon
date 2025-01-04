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
import com.neu.carbon.mes.domain.MesProcessModel;
import com.neu.carbon.mes.service.IMesProcessModelService;
import com.neu.carbon.wms.domain.WmsMaterialInfo;
import com.neu.carbon.wms.service.IWmsMaterialInfoService;
import com.neu.common.utils.poi.ExcelUtil;
import com.neu.common.core.page.TableDataInfo;

/**
 * 工艺建模Controller
 * 
 * @author neuedu
 * @date 2022-07-11
 */
@Api(tags = {"工艺建模"})
@RestController
@RequestMapping("/mesModel/processModel")
public class MesProcessModelController extends BaseController
{
    @Autowired
    private IMesProcessModelService mesProcessModelService;
    @Autowired
    private IWmsMaterialInfoService wmsMaterialInfoService;


    /**
     * 查询工艺建模列表
     */
    @GetMapping("/list")
    @ApiOperation("查询工艺建模列表")
    @DynamicResponseParameters(properties = {
	        @DynamicParameter(name = "total", value = "总记录数"),
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "rows", value = "返回业务数据（数组类型）", dataTypeClass = MesProcessModel.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    public TableDataInfo list(MesProcessModel mesProcessModel)
    {
        startPage();
        List<MesProcessModel> list = mesProcessModelService.selectMesProcessModelList(mesProcessModel);
        list.stream().forEach(model->{
        	WmsMaterialInfo material = wmsMaterialInfoService.selectWmsMaterialInfoById(model.getMaterialId());
        	if(material!=null) {
        		model.setMaterialCode(material.getCode());
        		model.setMaterialModel(material.getModel());
        		model.setMaterialName(material.getName());
        		model.setMaterialSpecification(material.getSpecification());
        		model.setMaterialUnit(material.getUnit());
        	}
        });
        return getDataTable(list);
    }

    /**
     * 导出工艺建模列表
     */
    @ApiOperation("导出工艺建模列表")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "文件名")
    })
    @PreAuthorize("@ss.hasPermi('mesModel:processModel:export')")
    @Log(title = "工艺建模", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(MesProcessModel mesProcessModel)
    {
        List<MesProcessModel> list = mesProcessModelService.selectMesProcessModelList(mesProcessModel);
        list.stream().forEach(model->{
            WmsMaterialInfo material = wmsMaterialInfoService.selectWmsMaterialInfoById(model.getMaterialId());
            if(material!=null) {
                model.setMaterialCode(material.getCode());
                model.setMaterialModel(material.getModel());
                model.setMaterialName(material.getName());
                model.setMaterialSpecification(material.getSpecification());
                model.setMaterialUnit(material.getUnit());
            }
        });
        ExcelUtil<MesProcessModel> util = new ExcelUtil<MesProcessModel>(MesProcessModel.class);
        return util.exportExcel(list, "processModel");
    }

    /**
     * 获取工艺建模详细信息
     */
    @ApiOperation("获取工艺建模详细信息")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "data", value = "返回业务数据", dataTypeClass = MesProcessModel.class),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
    	MesProcessModel model = mesProcessModelService.selectMesProcessModelById(id);
    	WmsMaterialInfo material = wmsMaterialInfoService.selectWmsMaterialInfoById(model.getMaterialId());
    	if(material!=null) {
    		model.setMaterialCode(material.getCode());
    		model.setMaterialModel(material.getModel());
    		model.setMaterialName(material.getName());
    		model.setMaterialSpecification(material.getSpecification());
    		model.setMaterialUnit(material.getUnit());
    	}
        return AjaxResult.success(model);
    }

    /**
     * 新增工艺建模
     */
    @ApiOperation("新增工艺建模")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('mesModel:processModel:add')")
    @Log(title = "工艺建模", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MesProcessModel mesProcessModel)
    {
        return toAjax(mesProcessModelService.insertMesProcessModel(mesProcessModel));
    }

    /**
     * 修改工艺建模
     */
    @ApiOperation("修改工艺建模")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('mesModel:processModel:edit')")
    @Log(title = "工艺建模", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MesProcessModel mesProcessModel)
    {
        return toAjax(mesProcessModelService.updateMesProcessModel(mesProcessModel));
    }

    /**
     * 删除工艺建模
     */
    @ApiOperation("删除工艺建模")
    @DynamicResponseParameters(properties = {
            @DynamicParameter(name = "code", value = "状态码，200正确，其他错误"),
            @DynamicParameter(name = "msg", value = "返回消息内容")
    })
    @PreAuthorize("@ss.hasPermi('mesModel:processModel:remove')")
    @Log(title = "工艺建模", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(mesProcessModelService.deleteMesProcessModelByIds(ids));
    }
}
