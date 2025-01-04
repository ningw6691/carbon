package com.neu.carbon.wms.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.neu.carbon.wms.mapper.WmsPurchaseOwnerMapper;
import com.neu.carbon.wms.domain.WmsPurchaseOwner;
import com.neu.carbon.wms.service.IWmsPurchaseOwnerService;

/**
 * 货主信息Service业务层处理
 * 
 * @author neuedu
 * @date 2023-05-10
 */
@Service
public class WmsPurchaseOwnerServiceImpl implements IWmsPurchaseOwnerService 
{
    @Autowired
    private WmsPurchaseOwnerMapper wmsPurchaseOwnerMapper;

    /**
     * 查询货主信息
     * 
     * @param id 货主信息ID
     * @return 货主信息
     */
    @Override
    public WmsPurchaseOwner selectWmsPurchaseOwnerById(Long id)
    {
        return wmsPurchaseOwnerMapper.selectWmsPurchaseOwnerById(id);
    }

    /**
     * 查询货主信息列表
     * 
     * @param wmsPurchaseOwner 货主信息
     * @return 货主信息
     */
    @Override
    public List<WmsPurchaseOwner> selectWmsPurchaseOwnerList(WmsPurchaseOwner wmsPurchaseOwner)
    {
        return wmsPurchaseOwnerMapper.selectWmsPurchaseOwnerList(wmsPurchaseOwner);
    }

    /**
     * 新增货主信息
     * 
     * @param wmsPurchaseOwner 货主信息
     * @return 结果
     */
    @Override
    public int insertWmsPurchaseOwner(WmsPurchaseOwner wmsPurchaseOwner)
    {
        return wmsPurchaseOwnerMapper.insertWmsPurchaseOwner(wmsPurchaseOwner);
    }

    /**
     * 修改货主信息
     * 
     * @param wmsPurchaseOwner 货主信息
     * @return 结果
     */
    @Override
    public int updateWmsPurchaseOwner(WmsPurchaseOwner wmsPurchaseOwner)
    {
        return wmsPurchaseOwnerMapper.updateWmsPurchaseOwner(wmsPurchaseOwner);
    }

    /**
     * 批量删除货主信息
     * 
     * @param ids 需要删除的货主信息ID
     * @return 结果
     */
    @Override
    public int deleteWmsPurchaseOwnerByIds(Long[] ids)
    {
        return wmsPurchaseOwnerMapper.deleteWmsPurchaseOwnerByIds(ids);
    }

    /**
     * 删除货主信息信息
     * 
     * @param id 货主信息ID
     * @return 结果
     */
    @Override
    public int deleteWmsPurchaseOwnerById(Long id)
    {
        return wmsPurchaseOwnerMapper.deleteWmsPurchaseOwnerById(id);
    }
}
