package com.neu.carbon.scm.service.impl;

import java.util.List;

import com.neu.common.enums.SerialNoPrefix;
import com.neu.common.utils.DateUtils;
import com.neu.smty.utils.CodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.neu.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.neu.carbon.scm.domain.ScmSaleDeliveryDetail;
import com.neu.carbon.scm.mapper.ScmSaleDeliveryMapper;
import com.neu.carbon.scm.domain.ScmSaleDelivery;
import com.neu.carbon.scm.service.IScmSaleDeliveryService;

/**
 * 销售发货单Service业务层处理
 * 
 * @author neuedu
 * @date 2022-07-05
 */
@Service
public class ScmSaleDeliveryServiceImpl implements IScmSaleDeliveryService 
{
    @Autowired
    private ScmSaleDeliveryMapper scmSaleDeliveryMapper;

    /**
     * 查询销售发货单
     * 
     * @param id 销售发货单ID
     * @return 销售发货单
     */
    @Override
    public ScmSaleDelivery selectScmSaleDeliveryById(Long id)
    {
        return scmSaleDeliveryMapper.selectScmSaleDeliveryById(id);
    }

    /**
     * 查询销售发货单列表
     * 
     * @param scmSaleDelivery 销售发货单
     * @return 销售发货单
     */
    @Override
    public List<ScmSaleDelivery> selectScmSaleDeliveryList(ScmSaleDelivery scmSaleDelivery)
    {
        return scmSaleDeliveryMapper.selectScmSaleDeliveryList(scmSaleDelivery);
    }

    /**
     * 新增销售发货单
     * 
     * @param scmSaleDelivery 销售发货单
     * @return 结果
     */
    @Transactional
    @Override
    public int insertScmSaleDelivery(ScmSaleDelivery scmSaleDelivery)
    {
        scmSaleDelivery.setDeliveryNo(CodeUtil.getSerialNo(SerialNoPrefix.XSFH));
        scmSaleDelivery.setCreateTime(DateUtils.getNowDate());
        int rows = scmSaleDeliveryMapper.insertScmSaleDelivery(scmSaleDelivery);
        insertScmSaleDeliveryDetail(scmSaleDelivery);
        return rows;
    }

    /**
     * 修改销售发货单
     * 
     * @param scmSaleDelivery 销售发货单
     * @return 结果
     */
    @Transactional
    @Override
    public int updateScmSaleDelivery(ScmSaleDelivery scmSaleDelivery)
    {
//        scmSaleDeliveryMapper.deleteScmSaleDeliveryDetailByDeliveryId(scmSaleDelivery.getId());
//        insertScmSaleDeliveryDetail(scmSaleDelivery);
        return scmSaleDeliveryMapper.updateScmSaleDelivery(scmSaleDelivery);
    }

    /**
     * 批量删除销售发货单
     * 
     * @param ids 需要删除的销售发货单ID
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteScmSaleDeliveryByIds(Long[] ids)
    {
        scmSaleDeliveryMapper.deleteScmSaleDeliveryDetailByDeliveryIds(ids);
        return scmSaleDeliveryMapper.deleteScmSaleDeliveryByIds(ids);
    }

    /**
     * 删除销售发货单信息
     * 
     * @param id 销售发货单ID
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteScmSaleDeliveryById(Long id)
    {
        scmSaleDeliveryMapper.deleteScmSaleDeliveryDetailByDeliveryId(id);
        return scmSaleDeliveryMapper.deleteScmSaleDeliveryById(id);
    }

    /**
     * 新增发货单明细信息
     * 
     * @param scmSaleDelivery 销售发货单对象
     */
    public void insertScmSaleDeliveryDetail(ScmSaleDelivery scmSaleDelivery)
    {
        List<ScmSaleDeliveryDetail> scmSaleDeliveryDetailList = scmSaleDelivery.getScmSaleDeliveryDetailList();
        Long id = scmSaleDelivery.getId();
        if (StringUtils.isNotNull(scmSaleDeliveryDetailList))
        {
            List<ScmSaleDeliveryDetail> list = new ArrayList<ScmSaleDeliveryDetail>();
            for (ScmSaleDeliveryDetail scmSaleDeliveryDetail : scmSaleDeliveryDetailList)
            {
                scmSaleDeliveryDetail.setDeliveryId(id);
                list.add(scmSaleDeliveryDetail);
            }
            if (list.size() > 0)
            {
                scmSaleDeliveryMapper.batchScmSaleDeliveryDetail(list);
            }
        }
    }
}
