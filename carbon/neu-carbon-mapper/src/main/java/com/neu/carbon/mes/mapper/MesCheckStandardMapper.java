package com.neu.carbon.mes.mapper;

import java.util.List;
import com.neu.carbon.mes.domain.MesCheckStandard;

/**
 * 质检标准Mapper接口
 * 
 * @author neuedu
 * @date 2022-07-12
 */
public interface MesCheckStandardMapper 
{
    /**
     * 查询质检标准
     * 
     * @param id 质检标准ID
     * @return 质检标准
     */
    public MesCheckStandard selectMesCheckStandardById(Long id);

    /**
     * 查询质检标准列表
     * 
     * @param mesCheckStandard 质检标准
     * @return 质检标准集合
     */
    public List<MesCheckStandard> selectMesCheckStandardList(MesCheckStandard mesCheckStandard);

    /**
     * 新增质检标准
     * 
     * @param mesCheckStandard 质检标准
     * @return 结果
     */
    public int insertMesCheckStandard(MesCheckStandard mesCheckStandard);

    /**
     * 修改质检标准
     * 
     * @param mesCheckStandard 质检标准
     * @return 结果
     */
    public int updateMesCheckStandard(MesCheckStandard mesCheckStandard);

    /**
     * 删除质检标准
     * 
     * @param id 质检标准ID
     * @return 结果
     */
    public int deleteMesCheckStandardById(Long id);

    /**
     * 批量删除质检标准
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteMesCheckStandardByIds(Long[] ids);
}
