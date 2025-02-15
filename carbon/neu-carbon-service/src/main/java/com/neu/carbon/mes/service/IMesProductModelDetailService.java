package com.neu.carbon.mes.service;

import java.util.List;
import com.neu.carbon.mes.domain.MesProductModelDetail;

/**
 * 产品模型明细Service接口
 * 
 * @author neuedu
 * @date 2022-07-08
 */
public interface IMesProductModelDetailService 
{
    /**
     * 查询产品模型明细
     * 
     * @param id 产品模型明细ID
     * @return 产品模型明细
     */
    public MesProductModelDetail selectMesProductModelDetailById(Long id);

    /**
     * 查询产品模型明细列表
     * 
     * @param mesProductModelDetail 产品模型明细
     * @return 产品模型明细集合
     */
    public List<MesProductModelDetail> selectMesProductModelDetailList(MesProductModelDetail mesProductModelDetail);

    /**
     * 新增产品模型明细
     * 
     * @param mesProductModelDetail 产品模型明细
     * @return 结果
     */
    public int insertMesProductModelDetail(MesProductModelDetail mesProductModelDetail);

    /**
     * 修改产品模型明细
     * 
     * @param mesProductModelDetail 产品模型明细
     * @return 结果
     */
    public int updateMesProductModelDetail(MesProductModelDetail mesProductModelDetail);

    /**
     * 批量删除产品模型明细
     * 
     * @param ids 需要删除的产品模型明细ID
     * @return 结果
     */
    public int deleteMesProductModelDetailByIds(Long[] ids);

    /**
     * 删除产品模型明细信息
     * 
     * @param id 产品模型明细ID
     * @return 结果
     */
    public int deleteMesProductModelDetailById(Long id);
}
