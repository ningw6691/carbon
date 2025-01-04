package com.neu.carbon.mes.service;

import java.util.List;
import com.neu.carbon.mes.domain.MesProductBom;

/**
 * BOM管理Service接口
 * 
 * @author neuedu
 * @date 2022-07-11
 */
public interface IMesProductBomService 
{
    /**
     * 查询BOM管理
     * 
     * @param id BOM管理ID
     * @return BOM管理
     */
    public MesProductBom selectMesProductBomById(Long id);

    /**
     * 查询BOM管理列表
     * 
     * @param mesProductBom BOM管理
     * @return BOM管理集合
     */
    public List<MesProductBom> selectMesProductBomList(MesProductBom mesProductBom);

    /**
     * 新增BOM管理
     * 
     * @param mesProductBom BOM管理
     * @return 结果
     */
    public int insertMesProductBom(MesProductBom mesProductBom);

    /**
     * 修改BOM管理
     * 
     * @param mesProductBom BOM管理
     * @return 结果
     */
    public int updateMesProductBom(MesProductBom mesProductBom);

    /**
     * 批量删除BOM管理
     * 
     * @param ids 需要删除的BOM管理ID
     * @return 结果
     */
    public int deleteMesProductBomByIds(Long[] ids);

    /**
     * 删除BOM管理信息
     * 
     * @param id BOM管理ID
     * @return 结果
     */
    public int deleteMesProductBomById(Long id);
}
