package com.neu.carbon.wms.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.neu.carbon.wms.mapper.WmsWarehouseCheckMapper;
import com.neu.carbon.wms.domain.WmsWarehouseCheck;
import com.neu.carbon.wms.service.IWmsWarehouseCheckService;
import com.neu.common.enums.SerialNoPrefix;
import com.neu.smty.utils.CodeUtil;

/**
 * 盘点信息Service业务层处理
 * 
 * @author neuedu
 * @date 2022-07-06
 */
@Service
public class WmsWarehouseCheckServiceImpl implements IWmsWarehouseCheckService 
{
    @Autowired
    private WmsWarehouseCheckMapper wmsWarehouseCheckMapper;

    /**
     * 查询盘点信息
     * 
     * @param id 盘点信息ID
     * @return 盘点信息
     */
    @Override
    public WmsWarehouseCheck selectWmsWarehouseCheckById(Long id)
    {
        return wmsWarehouseCheckMapper.selectWmsWarehouseCheckById(id);
    }

    /**
     * 查询盘点信息列表
     * 
     * @param wmsWarehouseCheck 盘点信息
     * @return 盘点信息
     */
    @Override
    public List<WmsWarehouseCheck> selectWmsWarehouseCheckList(WmsWarehouseCheck wmsWarehouseCheck)
    {
        return wmsWarehouseCheckMapper.selectWmsWarehouseCheckList(wmsWarehouseCheck);
    }

    /**
     * 新增盘点信息
     * 
     * @param wmsWarehouseCheck 盘点信息
     * @return 结果
     */
    @Override
    public int insertWmsWarehouseCheck(WmsWarehouseCheck wmsWarehouseCheck)
    {
    	wmsWarehouseCheck.setSerialNo(CodeUtil.getSerialNo(SerialNoPrefix.PD));
        return wmsWarehouseCheckMapper.insertWmsWarehouseCheck(wmsWarehouseCheck);
    }

    /**
     * 修改盘点信息
     * 
     * @param wmsWarehouseCheck 盘点信息
     * @return 结果
     */
    @Override
    public int updateWmsWarehouseCheck(WmsWarehouseCheck wmsWarehouseCheck)
    {
        return wmsWarehouseCheckMapper.updateWmsWarehouseCheck(wmsWarehouseCheck);
    }

    /**
     * 批量删除盘点信息
     * 
     * @param ids 需要删除的盘点信息ID
     * @return 结果
     */
    @Override
    public int deleteWmsWarehouseCheckByIds(Long[] ids)
    {
        return wmsWarehouseCheckMapper.deleteWmsWarehouseCheckByIds(ids);
    }

    /**
     * 删除盘点信息信息
     * 
     * @param id 盘点信息ID
     * @return 结果
     */
    @Override
    public int deleteWmsWarehouseCheckById(Long id)
    {
        return wmsWarehouseCheckMapper.deleteWmsWarehouseCheckById(id);
    }
}
