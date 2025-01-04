package com.neu.carbon.mes.mapper;

import java.util.List;
import com.neu.carbon.mes.domain.MesProductBom;
import com.neu.carbon.mes.domain.MesProductBomDetail;

/**
 * BOM管理Mapper接口
 * 
 * @author neuedu
 * @date 2022-07-11
 */
public interface MesProductBomMapper 
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
     * 删除BOM管理
     * 
     * @param id BOM管理ID
     * @return 结果
     */
    public int deleteMesProductBomById(Long id);

    /**
     * 批量删除BOM管理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteMesProductBomByIds(Long[] ids);

    /**
     * 批量删除BOM单明细
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteMesProductBomDetailByBomIds(Long[] ids);
    
    /**
     * 批量新增BOM单明细
     * 
     * @param mesProductBomDetailList BOM单明细列表
     * @return 结果
     */
    public int batchMesProductBomDetail(List<MesProductBomDetail> mesProductBomDetailList);
    

    /**
     * 通过BOM管理ID删除BOM单明细信息
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
    public int deleteMesProductBomDetailByBomId(Long id);
}
