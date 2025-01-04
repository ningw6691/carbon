package com.neu.carbon.mes.mapper;

import java.util.List;
import com.neu.carbon.mes.domain.MesProductBomDetail;

/**
 * BOM单明细Mapper接口
 * 
 * @author neuedu
 * @date 2022-07-11
 */
public interface MesProductBomDetailMapper 
{
    /**
     * 查询BOM单明细
     * 
     * @param id BOM单明细ID
     * @return BOM单明细
     */
    public MesProductBomDetail selectMesProductBomDetailById(Long id);

    /**
     * 查询BOM单明细列表
     * 
     * @param mesProductBomDetail BOM单明细
     * @return BOM单明细集合
     */
    public List<MesProductBomDetail> selectMesProductBomDetailList(MesProductBomDetail mesProductBomDetail);

    /**
     * 新增BOM单明细
     * 
     * @param mesProductBomDetail BOM单明细
     * @return 结果
     */
    public int insertMesProductBomDetail(MesProductBomDetail mesProductBomDetail);

    /**
     * 修改BOM单明细
     * 
     * @param mesProductBomDetail BOM单明细
     * @return 结果
     */
    public int updateMesProductBomDetail(MesProductBomDetail mesProductBomDetail);

    /**
     * 删除BOM单明细
     * 
     * @param id BOM单明细ID
     * @return 结果
     */
    public int deleteMesProductBomDetailById(Long id);

    /**
     * 批量删除BOM单明细
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteMesProductBomDetailByIds(Long[] ids);
}
