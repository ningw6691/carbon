package com.neu.carbon.wms.service.impl;

import com.neu.carbon.scm.domain.ScmPurchaseApply;
import com.neu.carbon.scm.domain.ScmPurchaseApplyDetail;
import com.neu.carbon.scm.service.IScmPurchaseApplyService;
import com.neu.carbon.wms.domain.WmsMaterialInfo;
import com.neu.carbon.wms.domain.WmsReplenishApply;
import com.neu.carbon.wms.domain.WmsReplenishApplyDetail;
import com.neu.carbon.wms.mapper.WmsMaterialInfoMapper;
import com.neu.carbon.wms.mapper.WmsReplenishApplyMapper;
import com.neu.carbon.wms.service.IWmsReplenishApplyService;
import com.neu.common.constant.UserConstants;
import com.neu.common.enums.SerialNoPrefix;
import com.neu.common.utils.StringUtils;
import com.neu.smty.utils.CodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 补货申请Service业务层处理
 *
 * @author neuedu
 * @date 2022-07-20
 */
@Service
public class WmsReplenishApplyServiceImpl implements IWmsReplenishApplyService {
    @Autowired
    private WmsReplenishApplyMapper wmsReplenishApplyMapper;
    @Autowired
    private IScmPurchaseApplyService scmPurchaseApplyService;
    @Autowired
    private WmsMaterialInfoMapper wmsMaterialInfoMapper;

    /**
     * 查询补货申请
     *
     * @param id 补货申请ID
     * @return 补货申请
     */
    @Override
    public WmsReplenishApply selectWmsReplenishApplyById(Long id) {
        return wmsReplenishApplyMapper.selectWmsReplenishApplyById(id);
    }

    /**
     * 查询补货申请列表
     *
     * @param wmsReplenishApply 补货申请
     * @return 补货申请
     */
    @Override
    public List<WmsReplenishApply> selectWmsReplenishApplyList(WmsReplenishApply wmsReplenishApply) {
        return wmsReplenishApplyMapper.selectWmsReplenishApplyList(wmsReplenishApply);
    }

    /**
     * 新增补货申请
     *
     * @param wmsReplenishApply 补货申请
     * @return 结果
     */
    @Transactional
    @Override
    public int insertWmsReplenishApply(WmsReplenishApply wmsReplenishApply) {
        wmsReplenishApply.setSerialNo(CodeUtil.getSerialNo(SerialNoPrefix.BHSQ));
        int rows = wmsReplenishApplyMapper.insertWmsReplenishApply(wmsReplenishApply);
        insertWmsReplenishApplyDetail(wmsReplenishApply);
        return rows;
    }

    /**
     * 修改补货申请
     *
     * @param wmsReplenishApply 补货申请
     * @return 结果
     */
    @Transactional
    @Override
    public int updateWmsReplenishApply(WmsReplenishApply wmsReplenishApply) {
        //审核时不需要更新明细表
        if (StringUtils.isBlank(wmsReplenishApply.getAuditStatus())) {
            wmsReplenishApplyMapper.deleteWmsReplenishApplyDetailByApplyId(wmsReplenishApply.getId());
            insertWmsReplenishApplyDetail(wmsReplenishApply);
        }
        return wmsReplenishApplyMapper.updateWmsReplenishApply(wmsReplenishApply);
    }

    /**
     * 批量删除补货申请
     *
     * @param ids 需要删除的补货申请ID
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteWmsReplenishApplyByIds(Long[] ids) {
        wmsReplenishApplyMapper.deleteWmsReplenishApplyDetailByApplyIds(ids);
        return wmsReplenishApplyMapper.deleteWmsReplenishApplyByIds(ids);
    }

    /**
     * 删除补货申请信息
     *
     * @param id 补货申请ID
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteWmsReplenishApplyById(Long id) {
        wmsReplenishApplyMapper.deleteWmsReplenishApplyDetailByApplyId(id);
        return wmsReplenishApplyMapper.deleteWmsReplenishApplyById(id);
    }

    /**
     * 新增补货申请明细信息
     *
     * @param wmsReplenishApply 补货申请对象
     */
    public void insertWmsReplenishApplyDetail(WmsReplenishApply wmsReplenishApply) {
        List<WmsReplenishApplyDetail> wmsReplenishApplyDetailList = wmsReplenishApply.getWmsReplenishApplyDetailList();
        Long id = wmsReplenishApply.getId();
        if (StringUtils.isNotNull(wmsReplenishApplyDetailList)) {
            List<WmsReplenishApplyDetail> list = new ArrayList<WmsReplenishApplyDetail>();
            for (WmsReplenishApplyDetail wmsReplenishApplyDetail : wmsReplenishApplyDetailList) {
                wmsReplenishApplyDetail.setApplyId(id);
                list.add(wmsReplenishApplyDetail);
            }
            if (list.size() > 0) {
                wmsReplenishApplyMapper.batchWmsReplenishApplyDetail(list);
            }
        }
    }

    /**
     * 修改补货申请
     *
     * @param wmsReplenishApply 补货申请
     * @return 结果
     */
    @Override
    public int update(WmsReplenishApply wmsReplenishApply) {
        return wmsReplenishApplyMapper.update(wmsReplenishApply);
    }

    /**
     * 生成采购申请
     *
     * @param id
     * @return 结果
     */
    public int genPurchaseApply(Long id) {
        WmsReplenishApply replenishApply = wmsReplenishApplyMapper.selectWmsReplenishApplyById(id);
        //  根据补货申请封装购买申请
        ScmPurchaseApply purchaseApply = new ScmPurchaseApply();
        purchaseApply.setApplyType(UserConstants.PURCHASE_APPLY_REPLENISH);
        purchaseApply.setReplenishId(replenishApply.getId());
        purchaseApply.setReplenishNo(replenishApply.getSerialNo());
        purchaseApply.setApplyUser(replenishApply.getApplyUser());
        purchaseApply.setApplyTime(new Date());
        purchaseApply.setApplyStatus(UserConstants.APPLY_STATUS_NO_SUBMIT);
        purchaseApply.setRemark(replenishApply.getRemark());
        // 根据补货申请详情列表封装购买申请详情列表
        List<ScmPurchaseApplyDetail> applyDetailList = new ArrayList<>();
        List<WmsReplenishApplyDetail> detailList = replenishApply.getWmsReplenishApplyDetailList();
        for (WmsReplenishApplyDetail item : detailList) {
            ScmPurchaseApplyDetail detail = new ScmPurchaseApplyDetail();
            detail.setMaterialId(item.getMaterialId());
            detail.setQuantity(item.getQuantity());
            detail.setRequireQuantity(item.getQuantity());
            detail.setDetailRemark(item.getDetailRemark());
            // 设置价格
            WmsMaterialInfo materialInfo = wmsMaterialInfoMapper.selectWmsMaterialInfoById(item.getMaterialId());
            if (materialInfo != null) {
                detail.setPrice(materialInfo.getPrice());
            }
            applyDetailList.add(detail);
        }
        purchaseApply.setScmPurchaseApplyDetailList(applyDetailList);
        int r = scmPurchaseApplyService.insertScmPurchaseApply(purchaseApply);
        if (r > 0) {
            WmsReplenishApply up = new WmsReplenishApply();
            up.setId(replenishApply.getId());
            // 设置状态采购中
            up.setStatus(UserConstants.REPLENISH_STATUS_BUYING);
            // 设置已生成采购申请
            up.setPurchaseApplyFlag("1");
            wmsReplenishApplyMapper.update(up);
        }
        return r;
    }
}
