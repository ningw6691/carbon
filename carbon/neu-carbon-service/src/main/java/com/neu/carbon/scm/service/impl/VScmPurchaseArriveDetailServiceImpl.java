package com.neu.carbon.scm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.neu.carbon.scm.mapper.VScmPurchaseArriveDetailMapper;
import com.neu.carbon.scm.domain.VScmPurchaseArriveDetail;
import com.neu.carbon.scm.service.IVScmPurchaseArriveDetailService;

/**
 * 采购到货详细报表Service业务层处理
 * 
 * @author neuedu
 * @date 2022-08-02
 */
@Service
public class VScmPurchaseArriveDetailServiceImpl implements IVScmPurchaseArriveDetailService 
{
    @Autowired
    private VScmPurchaseArriveDetailMapper vScmPurchaseArriveDetailMapper;

    /**
     * 查询采购到货详细报表列表
     * 
     * @param vScmPurchaseArriveDetail 采购到货详细报表
     * @return 采购到货详细报表
     */
    @Override
    public List<VScmPurchaseArriveDetail> selectVScmPurchaseArriveDetailList(VScmPurchaseArriveDetail vScmPurchaseArriveDetail)
    {
        return vScmPurchaseArriveDetailMapper.selectVScmPurchaseArriveDetailList(vScmPurchaseArriveDetail);
    }

}
