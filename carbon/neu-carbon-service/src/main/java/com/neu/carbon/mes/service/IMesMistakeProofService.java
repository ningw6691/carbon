package com.neu.carbon.mes.service;

import java.util.List;
import com.neu.carbon.mes.domain.MesMistakeProof;

/**
 * 防错管理Service接口
 * 
 * @author neuedu
 * @date 2022-07-19
 */
public interface IMesMistakeProofService 
{
    /**
     * 查询防错管理
     * 
     * @param id 防错管理ID
     * @return 防错管理
     */
    public MesMistakeProof selectMesMistakeProofById(Long id);

    /**
     * 查询防错管理列表
     * 
     * @param mesMistakeProof 防错管理
     * @return 防错管理集合
     */
    public List<MesMistakeProof> selectMesMistakeProofList(MesMistakeProof mesMistakeProof);

    /**
     * 新增防错管理
     * 
     * @param mesMistakeProof 防错管理
     * @return 结果
     */
    public int insertMesMistakeProof(MesMistakeProof mesMistakeProof);

    /**
     * 修改防错管理
     * 
     * @param mesMistakeProof 防错管理
     * @return 结果
     */
    public int updateMesMistakeProof(MesMistakeProof mesMistakeProof);

    /**
     * 批量删除防错管理
     * 
     * @param ids 需要删除的防错管理ID
     * @return 结果
     */
    public int deleteMesMistakeProofByIds(Long[] ids);

    /**
     * 删除防错管理信息
     * 
     * @param id 防错管理ID
     * @return 结果
     */
    public int deleteMesMistakeProofById(Long id);
}
