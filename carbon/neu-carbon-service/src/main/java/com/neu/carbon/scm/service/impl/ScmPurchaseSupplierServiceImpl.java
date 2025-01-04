package com.neu.carbon.scm.service.impl;

import com.neu.carbon.scm.domain.ScmPurchaseSupplier;
import com.neu.carbon.scm.mapper.ScmPurchaseSupplierMapper;
import com.neu.carbon.scm.service.IScmPurchaseSupplierService;
import com.neu.common.utils.DateUtils;
import com.neu.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 供应商Service业务层处理
 *
 * @author neuedu
 * @date 2022-06-27
 */
@Service
public class ScmPurchaseSupplierServiceImpl implements IScmPurchaseSupplierService {
    @Autowired
    private ScmPurchaseSupplierMapper scmPurchaseSupplierMapper;

    /**
     * 查询供应商
     *
     * @param id 供应商ID
     * @return 供应商
     */
    @Override
    public ScmPurchaseSupplier selectScmPurchaseSupplierById(Long id) {
        return scmPurchaseSupplierMapper.selectScmPurchaseSupplierById(id);
    }

    /**
     * 查询供应商列表
     *
     * @param scmPurchaseSupplier 供应商
     * @return 供应商
     */
    @Override
    public List<ScmPurchaseSupplier> selectScmPurchaseSupplierList(ScmPurchaseSupplier scmPurchaseSupplier) {
        return scmPurchaseSupplierMapper.selectScmPurchaseSupplierList(scmPurchaseSupplier);
    }

    /**
     * 新增供应商
     *
     * @param scmPurchaseSupplier 供应商
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertScmPurchaseSupplier(ScmPurchaseSupplier scmPurchaseSupplier) {
        scmPurchaseSupplier.setCreateTime(DateUtils.getNowDate());
        // 新增供应商信息
        return scmPurchaseSupplierMapper.insertScmPurchaseSupplier(scmPurchaseSupplier);
    }

    /**
     * 修改供应商
     *
     * @param scmPurchaseSupplier 供应商
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateScmPurchaseSupplier(ScmPurchaseSupplier scmPurchaseSupplier) {
        scmPurchaseSupplier.setUpdateTime(DateUtils.getNowDate());
        return scmPurchaseSupplierMapper.updateScmPurchaseSupplier(scmPurchaseSupplier);
    }


    /**
     * 批量删除供应商
     *
     * @param ids 需要删除的供应商ID
     * @return 结果
     */
    @Override
    public int deleteScmPurchaseSupplierByIds(Long[] ids) {
        return scmPurchaseSupplierMapper.deleteScmPurchaseSupplierByIds(ids);
    }

    /**
     * 删除供应商信息
     *
     * @param id 供应商ID
     * @return 结果
     */
    @Override
    public int deleteScmPurchaseSupplierById(Long id) {
        return scmPurchaseSupplierMapper.deleteScmPurchaseSupplierById(id);
    }


    /**
     * 审核供应商
     *
     * @param scmPurchaseSupplier 供应商
     * @return 结果
     */
    @Override
    public int auditScmPurchaseSupplier(ScmPurchaseSupplier scmPurchaseSupplier) {
        return scmPurchaseSupplierMapper.updateScmPurchaseSupplier(scmPurchaseSupplier);
    }
}
