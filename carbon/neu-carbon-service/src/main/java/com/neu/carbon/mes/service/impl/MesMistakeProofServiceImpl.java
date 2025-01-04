package com.neu.carbon.mes.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.neu.carbon.mes.mapper.MesMistakeProofMapper;
import com.neu.carbon.mes.domain.MesMistakeProof;
import com.neu.carbon.mes.service.IMesMistakeProofService;

/**
 * 防错管理Service业务层处理
 * 
 * @author neuedu
 * @date 2022-07-19
 */
@Service
public class MesMistakeProofServiceImpl implements IMesMistakeProofService 
{
    @Autowired
    private MesMistakeProofMapper mesMistakeProofMapper;

    /**
     * 查询防错管理
     * 
     * @param id 防错管理ID
     * @return 防错管理
     */
    @Override
    public MesMistakeProof selectMesMistakeProofById(Long id)
    {
        return mesMistakeProofMapper.selectMesMistakeProofById(id);
    }

    /**
     * 查询防错管理列表
     * 
     * @param mesMistakeProof 防错管理
     * @return 防错管理
     */
    @Override
    public List<MesMistakeProof> selectMesMistakeProofList(MesMistakeProof mesMistakeProof)
    {
        return mesMistakeProofMapper.selectMesMistakeProofList(mesMistakeProof);
    }

    /**
     * 新增防错管理
     * 
     * @param mesMistakeProof 防错管理
     * @return 结果
     */
    @Override
    public int insertMesMistakeProof(MesMistakeProof mesMistakeProof)
    {
        return mesMistakeProofMapper.insertMesMistakeProof(mesMistakeProof);
    }

    /**
     * 修改防错管理
     * 
     * @param mesMistakeProof 防错管理
     * @return 结果
     */
    @Override
    public int updateMesMistakeProof(MesMistakeProof mesMistakeProof)
    {
        return mesMistakeProofMapper.updateMesMistakeProof(mesMistakeProof);
    }

    /**
     * 批量删除防错管理
     * 
     * @param ids 需要删除的防错管理ID
     * @return 结果
     */
    @Override
    public int deleteMesMistakeProofByIds(Long[] ids)
    {
        return mesMistakeProofMapper.deleteMesMistakeProofByIds(ids);
    }

    /**
     * 删除防错管理信息
     * 
     * @param id 防错管理ID
     * @return 结果
     */
    @Override
    public int deleteMesMistakeProofById(Long id)
    {
        return mesMistakeProofMapper.deleteMesMistakeProofById(id);
    }
}
