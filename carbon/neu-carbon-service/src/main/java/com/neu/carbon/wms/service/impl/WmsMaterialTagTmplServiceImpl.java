package com.neu.carbon.wms.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.neu.carbon.wms.mapper.WmsMaterialTagTmplMapper;
import com.neu.carbon.wms.domain.WmsMaterialTagTmpl;
import com.neu.carbon.wms.service.IWmsMaterialTagTmplService;

/**
 * 物料标签模板Service业务层处理
 * 
 * @author neuedu
 * @date 2022-07-07
 */
@Service
public class WmsMaterialTagTmplServiceImpl implements IWmsMaterialTagTmplService 
{
    @Autowired
    private WmsMaterialTagTmplMapper wmsMaterialTagTmplMapper;

    /**
     * 查询物料标签模板
     * 
     * @param id 物料标签模板ID
     * @return 物料标签模板
     */
    @Override
    public WmsMaterialTagTmpl selectWmsMaterialTagTmplById(Long id)
    {
        return wmsMaterialTagTmplMapper.selectWmsMaterialTagTmplById(id);
    }

    /**
     * 查询物料标签模板列表
     * 
     * @param wmsMaterialTagTmpl 物料标签模板
     * @return 物料标签模板
     */
    @Override
    public List<WmsMaterialTagTmpl> selectWmsMaterialTagTmplList(WmsMaterialTagTmpl wmsMaterialTagTmpl)
    {
        return wmsMaterialTagTmplMapper.selectWmsMaterialTagTmplList(wmsMaterialTagTmpl);
    }

    /**
     * 新增物料标签模板
     * 
     * @param wmsMaterialTagTmpl 物料标签模板
     * @return 结果
     */
    @Override
    public int insertWmsMaterialTagTmpl(WmsMaterialTagTmpl wmsMaterialTagTmpl)
    {
        return wmsMaterialTagTmplMapper.insertWmsMaterialTagTmpl(wmsMaterialTagTmpl);
    }

    /**
     * 修改物料标签模板
     * 
     * @param wmsMaterialTagTmpl 物料标签模板
     * @return 结果
     */
    @Override
    public int updateWmsMaterialTagTmpl(WmsMaterialTagTmpl wmsMaterialTagTmpl)
    {
        return wmsMaterialTagTmplMapper.updateWmsMaterialTagTmpl(wmsMaterialTagTmpl);
    }

    /**
     * 批量删除物料标签模板
     * 
     * @param ids 需要删除的物料标签模板ID
     * @return 结果
     */
    @Override
    public int deleteWmsMaterialTagTmplByIds(Long[] ids)
    {
        return wmsMaterialTagTmplMapper.deleteWmsMaterialTagTmplByIds(ids);
    }

    /**
     * 删除物料标签模板信息
     * 
     * @param id 物料标签模板ID
     * @return 结果
     */
    @Override
    public int deleteWmsMaterialTagTmplById(Long id)
    {
        return wmsMaterialTagTmplMapper.deleteWmsMaterialTagTmplById(id);
    }
}
