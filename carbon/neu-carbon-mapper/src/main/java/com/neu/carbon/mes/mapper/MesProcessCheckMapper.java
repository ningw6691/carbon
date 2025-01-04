package com.neu.carbon.mes.mapper;

import java.util.List;
import com.neu.carbon.mes.domain.MesProcessCheck;

/**
 * 生产采样单Mapper接口
 * 
 * @author neuedu
 * @date 2022-07-12
 */
public interface MesProcessCheckMapper 
{
    /**
     * 查询生产采样单
     * 
     * @param id 生产采样单ID
     * @return 生产采样单
     */
    public MesProcessCheck selectMesProcessCheckById(Long id);

    /**
     * 查询生产采样单列表
     * 
     * @param mesProcessCheck 生产采样单
     * @return 生产采样单集合
     */
    public List<MesProcessCheck> selectMesProcessCheckList(MesProcessCheck mesProcessCheck);

    /**
     * 新增生产采样单
     * 
     * @param mesProcessCheck 生产采样单
     * @return 结果
     */
    public int insertMesProcessCheck(MesProcessCheck mesProcessCheck);

    /**
     * 修改生产采样单
     * 
     * @param mesProcessCheck 生产采样单
     * @return 结果
     */
    public int updateMesProcessCheck(MesProcessCheck mesProcessCheck);

    /**
     * 删除生产采样单
     * 
     * @param id 生产采样单ID
     * @return 结果
     */
    public int deleteMesProcessCheckById(Long id);

    /**
     * 批量删除生产采样单
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteMesProcessCheckByIds(Long[] ids);
}
