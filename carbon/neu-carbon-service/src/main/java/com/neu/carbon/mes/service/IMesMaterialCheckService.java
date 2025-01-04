package com.neu.carbon.mes.service;

import java.util.List;
import com.neu.carbon.mes.domain.MesMaterialCheck;

/**
 * 物料检验单Service接口
 * 
 * @author neuedu
 * @date 2022-07-12
 */
public interface IMesMaterialCheckService 
{
    /**
     * 查询物料检验单
     * 
     * @param id 物料检验单ID
     * @return 物料检验单
     */
    public MesMaterialCheck selectMesMaterialCheckById(Long id);

    /**
     * 查询物料检验单列表
     * 
     * @param mesMaterialCheck 物料检验单
     * @return 物料检验单集合
     */
    public List<MesMaterialCheck> selectMesMaterialCheckList(MesMaterialCheck mesMaterialCheck);

    /**
     * 新增物料检验单
     * 
     * @param mesMaterialCheck 物料检验单
     * @return 结果
     */
    public int insertMesMaterialCheck(MesMaterialCheck mesMaterialCheck);

    /**
     * 修改物料检验单
     * 
     * @param mesMaterialCheck 物料检验单
     * @return 结果
     */
    public int updateMesMaterialCheck(MesMaterialCheck mesMaterialCheck);

    /**
     * 批量删除物料检验单
     * 
     * @param ids 需要删除的物料检验单ID
     * @return 结果
     */
    public int deleteMesMaterialCheckByIds(Long[] ids);

    /**
     * 删除物料检验单信息
     * 
     * @param id 物料检验单ID
     * @return 结果
     */
    public int deleteMesMaterialCheckById(Long id);
}
