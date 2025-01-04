package com.neu.carbon.wms.service;

import java.util.List;
import com.neu.carbon.wms.domain.WmsMaterialTagTmpl;

/**
 * 物料标签模板Service接口
 * 
 * @author neuedu
 * @date 2022-07-07
 */
public interface IWmsMaterialTagTmplService 
{
    /**
     * 查询物料标签模板
     * 
     * @param id 物料标签模板ID
     * @return 物料标签模板
     */
    public WmsMaterialTagTmpl selectWmsMaterialTagTmplById(Long id);

    /**
     * 查询物料标签模板列表
     * 
     * @param wmsMaterialTagTmpl 物料标签模板
     * @return 物料标签模板集合
     */
    public List<WmsMaterialTagTmpl> selectWmsMaterialTagTmplList(WmsMaterialTagTmpl wmsMaterialTagTmpl);

    /**
     * 新增物料标签模板
     * 
     * @param wmsMaterialTagTmpl 物料标签模板
     * @return 结果
     */
    public int insertWmsMaterialTagTmpl(WmsMaterialTagTmpl wmsMaterialTagTmpl);

    /**
     * 修改物料标签模板
     * 
     * @param wmsMaterialTagTmpl 物料标签模板
     * @return 结果
     */
    public int updateWmsMaterialTagTmpl(WmsMaterialTagTmpl wmsMaterialTagTmpl);

    /**
     * 批量删除物料标签模板
     * 
     * @param ids 需要删除的物料标签模板ID
     * @return 结果
     */
    public int deleteWmsMaterialTagTmplByIds(Long[] ids);

    /**
     * 删除物料标签模板信息
     * 
     * @param id 物料标签模板ID
     * @return 结果
     */
    public int deleteWmsMaterialTagTmplById(Long id);
}
