package com.neu.carbon.mes.service.impl;

import com.neu.carbon.mes.domain.MesMaterialCheck;
import com.neu.carbon.mes.mapper.MesMaterialCheckMapper;
import com.neu.carbon.mes.service.IMesMaterialCheckService;
import com.neu.common.enums.SerialNoPrefix;
import com.neu.smty.utils.CodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 物料检验单Service业务层处理
 *
 * @author neuedu
 * @date 2022-07-12
 */
@Service
public class MesMaterialCheckServiceImpl implements IMesMaterialCheckService {
    @Autowired
    private MesMaterialCheckMapper mesMaterialCheckMapper;

    /**
     * 查询物料检验单
     *
     * @param id 物料检验单ID
     * @return 物料检验单
     */
    @Override
    public MesMaterialCheck selectMesMaterialCheckById(Long id) {
        return mesMaterialCheckMapper.selectMesMaterialCheckById(id);
    }

    /**
     * 查询物料检验单列表
     *
     * @param mesMaterialCheck 物料检验单
     * @return 物料检验单
     */
    @Override
    public List<MesMaterialCheck> selectMesMaterialCheckList(MesMaterialCheck mesMaterialCheck) {
        return mesMaterialCheckMapper.selectMesMaterialCheckList(mesMaterialCheck);
    }

    /**
     * 新增物料检验单
     *
     * @param mesMaterialCheck 物料检验单
     * @return 结果
     */
    @Override
    public int insertMesMaterialCheck(MesMaterialCheck mesMaterialCheck) {
        mesMaterialCheck.setSerialNo(CodeUtil.getSerialNo(SerialNoPrefix.WLJY));
        mesMaterialCheck.setCheckDate(new Date());
        return mesMaterialCheckMapper.insertMesMaterialCheck(mesMaterialCheck);
    }

    /**
     * 修改物料检验单
     *
     * @param mesMaterialCheck 物料检验单
     * @return 结果
     */
    @Override
    public int updateMesMaterialCheck(MesMaterialCheck mesMaterialCheck) {
        return mesMaterialCheckMapper.updateMesMaterialCheck(mesMaterialCheck);
    }

    /**
     * 批量删除物料检验单
     *
     * @param ids 需要删除的物料检验单ID
     * @return 结果
     */
    @Override
    public int deleteMesMaterialCheckByIds(Long[] ids) {
        return mesMaterialCheckMapper.deleteMesMaterialCheckByIds(ids);
    }

    /**
     * 删除物料检验单信息
     *
     * @param id 物料检验单ID
     * @return 结果
     */
    @Override
    public int deleteMesMaterialCheckById(Long id) {
        return mesMaterialCheckMapper.deleteMesMaterialCheckById(id);
    }
}
