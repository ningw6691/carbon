package com.neu.carbon.wms.mapper;

import java.util.List;
import com.neu.carbon.wms.domain.WmsPurchaseOwner;

/**
 * 货主信息Mapper接口
 * 
 * @author neuedu
 * @date 2023-05-10
 */
public interface WmsPurchaseOwnerMapper 
{
    /**
     * 查询货主信息
     * 
     * @param id 货主信息ID
     * @return 货主信息
     */
    public WmsPurchaseOwner selectWmsPurchaseOwnerById(Long id);

    /**
     * 查询货主信息列表
     * 
     * @param wmsPurchaseOwner 货主信息
     * @return 货主信息集合
     */
    public List<WmsPurchaseOwner> selectWmsPurchaseOwnerList(WmsPurchaseOwner wmsPurchaseOwner);

    /**
     * 新增货主信息
     * 
     * @param wmsPurchaseOwner 货主信息
     * @return 结果
     */
    public int insertWmsPurchaseOwner(WmsPurchaseOwner wmsPurchaseOwner);

    /**
     * 修改货主信息
     * 
     * @param wmsPurchaseOwner 货主信息
     * @return 结果
     */
    public int updateWmsPurchaseOwner(WmsPurchaseOwner wmsPurchaseOwner);

    /**
     * 删除货主信息
     * 
     * @param id 货主信息ID
     * @return 结果
     */
    public int deleteWmsPurchaseOwnerById(Long id);

    /**
     * 批量删除货主信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWmsPurchaseOwnerByIds(Long[] ids);
}
