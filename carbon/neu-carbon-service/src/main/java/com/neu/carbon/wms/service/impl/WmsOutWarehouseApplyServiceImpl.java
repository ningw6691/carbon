package com.neu.carbon.wms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neu.carbon.mes.domain.MesMaterialRequisition;
import com.neu.carbon.mes.service.IMesMaterialRequisitionService;
import com.neu.carbon.scm.domain.ScmSaleDelivery;
import com.neu.carbon.scm.service.IScmSaleDeliveryService;
import com.neu.carbon.wms.domain.WmsCarrierApply;
import com.neu.carbon.wms.domain.WmsCarrierApplyDetail;
import com.neu.carbon.wms.domain.WmsOutWarehouseApply;
import com.neu.carbon.wms.domain.WmsOutWarehouseApplyDetail;
import com.neu.carbon.wms.domain.WmsWarehouseMaterialDetail;
import com.neu.carbon.wms.mapper.WmsOutWarehouseApplyDetailMapper;
import com.neu.carbon.wms.mapper.WmsOutWarehouseApplyMapper;
import com.neu.carbon.wms.mapper.WmsWarehouseMaterialDetailMapper;
import com.neu.carbon.wms.service.IWmsCarrierApplyService;
import com.neu.carbon.wms.service.IWmsOutWarehouseApplyService;
import com.neu.carbon.wms.service.IWmsWarehouseMaterialService;
import com.neu.common.constant.UserConstants;
import com.neu.common.enums.SerialNoPrefix;
import com.neu.common.utils.SecurityUtils;
import com.neu.common.utils.StringUtils;
import com.neu.smty.utils.CodeUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 出库申请Service业务层处理
 * 
 * @author neuedu
 * @date 2022-06-29
 */
@Service
@Slf4j
public class WmsOutWarehouseApplyServiceImpl implements IWmsOutWarehouseApplyService {
	@Autowired
	private WmsOutWarehouseApplyMapper wmsOutWarehouseApplyMapper;
	@Autowired
	private WmsOutWarehouseApplyDetailMapper wmsOutWarehouseApplyDetailMapper;
	@Autowired
	private WmsWarehouseMaterialDetailMapper wmsWarehouseMaterialDetailMapper;
	@Autowired
	private IWmsWarehouseMaterialService wmsWarehouseMaterialService;
	@Autowired
	private IWmsCarrierApplyService wmsCarrierApplyService;
	@Autowired
	private IScmSaleDeliveryService saleDeliveryService;
	@Autowired
	private IMesMaterialRequisitionService requisitionService;

	/**
	 * 查询出库申请
	 * 
	 * @param id
	 *            出库申请ID
	 * @return 出库申请
	 */
	@Override
	public WmsOutWarehouseApply selectWmsOutWarehouseApplyById(Long id) {
		return wmsOutWarehouseApplyMapper.selectWmsOutWarehouseApplyById(id);
	}

	/**
	 * 查询出库申请列表
	 * 
	 * @param wmsOutWarehouseApply
	 *            出库申请
	 * @return 出库申请
	 */
	@Override
	public List<WmsOutWarehouseApply> selectWmsOutWarehouseApplyList(WmsOutWarehouseApply wmsOutWarehouseApply) {
		return wmsOutWarehouseApplyMapper.selectWmsOutWarehouseApplyList(wmsOutWarehouseApply);
	}

	/**
	 * 新增出库申请
	 * 
	 * @param wmsOutWarehouseApply
	 *            出库申请
	 * @return 结果
	 */
	@Transactional
	@Override
	public int insertWmsOutWarehouseApply(WmsOutWarehouseApply wmsOutWarehouseApply) {
		wmsOutWarehouseApply.setSerialNo(CodeUtil.getSerialNo(SerialNoPrefix.CKSQ));
		int rows = wmsOutWarehouseApplyMapper.insertWmsOutWarehouseApply(wmsOutWarehouseApply);
		insertWmsOutWarehouseApplyDetail(wmsOutWarehouseApply);
		// 更新关联单据状态
		String bizType = wmsOutWarehouseApply.getBizType();
		// 更新生产领料单
		if ("3".equals(bizType)) {
			updateBizBillStatus(bizType, wmsOutWarehouseApply.getBizBillId(), "4");
		}
		// 更新销售发货单
		if ("4".equals(bizType)) {
			updateBizBillStatus(bizType, wmsOutWarehouseApply.getBizBillId(), "1");
		}
		return rows;
	}

	/**
	 * 修改出库申请
	 * 
	 * @param wmsOutWarehouseApply
	 *            出库申请
	 * @return 结果
	 */
	@Transactional
	@Override
	public int updateWmsOutWarehouseApply(WmsOutWarehouseApply wmsOutWarehouseApply) {
		// 审核时不需要更新明细表
		if (StringUtils.isBlank(wmsOutWarehouseApply.getAuditStatus())) {
			wmsOutWarehouseApplyMapper.deleteWmsOutWarehouseApplyDetailByApplyId(wmsOutWarehouseApply.getId());
			insertWmsOutWarehouseApplyDetail(wmsOutWarehouseApply);
		}
		return wmsOutWarehouseApplyMapper.updateWmsOutWarehouseApply(wmsOutWarehouseApply);
	}

	/**
	 * 批量删除出库申请
	 * 
	 * @param ids
	 *            需要删除的出库申请ID
	 * @return 结果
	 */
	@Transactional
	@Override
	public int deleteWmsOutWarehouseApplyByIds(Long[] ids) {
		for (Long id : ids) {
			WmsOutWarehouseApply apply = wmsOutWarehouseApplyMapper.selectWmsOutWarehouseApplyById(id);
			// 更新关联单据状态
			updateBizBillStatus(apply.getBizType(), apply.getBizBillId(), "0");
		}
		wmsOutWarehouseApplyMapper.deleteWmsOutWarehouseApplyDetailByApplyIds(ids);
		return wmsOutWarehouseApplyMapper.deleteWmsOutWarehouseApplyByIds(ids);
	}

	/**
	 * 删除出库申请信息
	 * 
	 * @param id
	 *            出库申请ID
	 * @return 结果
	 */
	@Transactional
	@Override
	public int deleteWmsOutWarehouseApplyById(Long id) {
		WmsOutWarehouseApply apply = wmsOutWarehouseApplyMapper.selectWmsOutWarehouseApplyById(id);
		// 更新关联单据状态
		updateBizBillStatus(apply.getBizType(), apply.getBizBillId(), "0");
		wmsOutWarehouseApplyMapper.deleteWmsOutWarehouseApplyDetailByApplyId(id);
		return wmsOutWarehouseApplyMapper.deleteWmsOutWarehouseApplyById(id);
	}

	/**
	 * 新增出库申请明细信息
	 * 
	 * @param wmsOutWarehouseApply
	 *            出库申请对象
	 */
	public void insertWmsOutWarehouseApplyDetail(WmsOutWarehouseApply wmsOutWarehouseApply) {
		List<WmsOutWarehouseApplyDetail> wmsOutWarehouseApplyDetailList = wmsOutWarehouseApply
				.getWmsOutWarehouseApplyDetailList();
		Long id = wmsOutWarehouseApply.getId();
		if (StringUtils.isNotNull(wmsOutWarehouseApplyDetailList)) {
			List<WmsOutWarehouseApplyDetail> list = new ArrayList<WmsOutWarehouseApplyDetail>();
			for (WmsOutWarehouseApplyDetail wmsOutWarehouseApplyDetail : wmsOutWarehouseApplyDetailList) {
				wmsOutWarehouseApplyDetail.setApplyId(id);
				list.add(wmsOutWarehouseApplyDetail);
			}
			if (list.size() > 0) {
				wmsOutWarehouseApplyMapper.batchWmsOutWarehouseApplyDetail(list);
			}
		}
	}

	@Transactional
	@Override
	public void performOutWarehouse(Long id) {
		WmsOutWarehouseApplyDetail wmsOutWarehouseApplyDetail = new WmsOutWarehouseApplyDetail();
		wmsOutWarehouseApplyDetail.setApplyId(id);
		List<WmsOutWarehouseApplyDetail> applyDetail = wmsOutWarehouseApplyDetailMapper
				.selectWmsOutWarehouseApplyDetailList(wmsOutWarehouseApplyDetail);
		applyDetail.stream().forEach(detail -> {
			WmsWarehouseMaterialDetail wmsWarehouseMaterialDetail = new WmsWarehouseMaterialDetail();
			wmsWarehouseMaterialDetail.setWarehouseId(detail.getWarehouseId());
			wmsWarehouseMaterialDetail.setWhRegionId(detail.getWhRegionId());
			wmsWarehouseMaterialDetail.setWhLocationId(detail.getWhLocationId());
			wmsWarehouseMaterialDetail.setMaterialId(detail.getMaterialId());
			wmsWarehouseMaterialDetail.setBatchNo(detail.getBatchNo());
			List<WmsWarehouseMaterialDetail> whMaterialDetail = wmsWarehouseMaterialDetailMapper
					.selectWmsWarehouseMaterialDetailList(wmsWarehouseMaterialDetail);
			if (whMaterialDetail != null && !whMaterialDetail.isEmpty()) {
				WmsWarehouseMaterialDetail material = whMaterialDetail.get(0);
				double total = material.getInventory() == null ? 0 : material.getInventory();
				double lock = material.getLockInventory() == null ? 0 : material.getLockInventory();
				// 现有库存=总库存-锁定库存
				double inventory = total - lock;
				// 出库量
				double outInventory = detail.getOutQuantity() == null ? 0 : detail.getOutQuantity();
				// 实际出库量
				double factOutInventory = Math.min(inventory, outInventory);
				// 更新库位总库存
				material.setInventory(inventory - factOutInventory);
				wmsWarehouseMaterialDetailMapper.updateWmsWarehouseMaterialDetail(material);
				// 更新仓库库存
				wmsWarehouseMaterialService.updateMaterialTotalInventory(material.getWarehouseId(),
						material.getMaterialId());
			}
		});
		// 更新出库单状态为已拣货
		WmsOutWarehouseApply apply = wmsOutWarehouseApplyMapper.selectWmsOutWarehouseApplyById(id);
		apply.setBillStatus("1");
		wmsOutWarehouseApplyMapper.updateWmsOutWarehouseApply(apply);
	}

	@Transactional
	@Override
	public void performDelivery(Long id) {
		WmsOutWarehouseApply apply = wmsOutWarehouseApplyMapper.selectWmsOutWarehouseApplyById(id);
		// 领料出库
		if ("3".equals(apply.getBizType())) {
			delivery4Product(apply);
		}
		// 销售出库生成承运单
		if ("4".equals(apply.getBizType())) {
			delivery4Sale(apply);
		}
		// 更新出库单状态
		apply.setBillStatus("3");
		wmsOutWarehouseApplyMapper.updateWmsOutWarehouseApply(apply);
	}

	private void delivery4Product(WmsOutWarehouseApply apply) {
		// 更新领料单状态为已领料
		updateBizBillStatus(apply.getBizType(), apply.getBizBillId(), "1");
		// 更新物料已领数量
		MesMaterialRequisition requisition = requisitionService.selectMesMaterialRequisitionById(apply.getBizBillId());
		requisition.getMesMaterialRequisitionDetailList().stream().forEach(rq -> {
			rq.setReceiveQuantity(rq.getRequireQuantity());
		});
		requisitionService.updateMesMaterialRequisition(requisition);
	}

	private void delivery4Sale(WmsOutWarehouseApply apply) {
		List<WmsOutWarehouseApplyDetail> applyDetail = apply.getWmsOutWarehouseApplyDetailList();
		ScmSaleDelivery saleDelivery = saleDeliveryService.selectScmSaleDeliveryById(apply.getBizBillId());
		// 创建承运申请
		WmsCarrierApply wmsCarrierApply = new WmsCarrierApply();
		wmsCarrierApply.setApplyStatus(UserConstants.APPLY_STATUS_NO_SUBMIT);
		Long loginUserId = SecurityUtils.getLoginUser().getUser().getUserId();
		wmsCarrierApply.setApplyUser(String.valueOf(loginUserId));
		wmsCarrierApply.setOutWarehouseId(apply.getId());
		wmsCarrierApply.setOutWarehouseNo(apply.getSerialNo());
		wmsCarrierApply.setCustomerId(apply.getCustomerId());
		wmsCarrierApply.setContact(saleDelivery.getContact());
		wmsCarrierApply.setContactTel(saleDelivery.getContactTel());
		wmsCarrierApply.setShippingAddress(saleDelivery.getAddress());
		wmsCarrierApply.setDeliveryDate(saleDelivery.getDeliveryDate());

		// 创建承运申请明细
		if (applyDetail == null || applyDetail.isEmpty()) {
			log.error("出库发货完成，出库单：流水[{}]，id[{}]；没有找到出库物料明细！", apply.getSerialNo(), apply.getId());
		} else {
			List<WmsCarrierApplyDetail> wmsCarrierApplyDetailList = new ArrayList<>();
			applyDetail.stream().forEach(appDetail -> {
				WmsCarrierApplyDetail carrierDetail = new WmsCarrierApplyDetail();
				carrierDetail.setMaterialId(appDetail.getMaterialId());
				carrierDetail.setBatchNo(appDetail.getBatchNo());
				carrierDetail.setDeliveryQuantity(appDetail.getOutQuantity());
				carrierDetail.setWarehouseId(appDetail.getWarehouseId());
				carrierDetail.setWhRegionId(appDetail.getWhRegionId());
				carrierDetail.setWhLocationId(appDetail.getWhLocationId());
				wmsCarrierApplyDetailList.add(carrierDetail);
			});
			wmsCarrierApply.setWmsCarrierApplyDetailList(wmsCarrierApplyDetailList);
		}
		// 创建承运申请
		wmsCarrierApplyService.insertWmsCarrierApply(wmsCarrierApply);
		// 更新销售发货单为已出库
		updateBizBillStatus(apply.getBizType(), apply.getBizBillId(), "2");
	}

	/**
	 * 更新关联单状态
	 * 
	 * @param bizType
	 * @param billId
	 * @param status
	 */
	private void updateBizBillStatus(String bizType, Long billId, String status) {
		// 更新生产领料单
		if ("3".equals(bizType)) {
			MesMaterialRequisition requisition = requisitionService.selectMesMaterialRequisitionById(billId);
			if (requisition != null) {
				// 更新领料单状态
				requisition.setStatus(status);
				requisitionService.updateMesMaterialRequisition(requisition);
			}
		}
		// 更新销售发货单
		if ("4".equals(bizType)) {
			ScmSaleDelivery delivery = saleDeliveryService.selectScmSaleDeliveryById(billId);
			if (delivery != null) {
				// 更新销售发货单状态
				delivery.setStatus(status);
				saleDeliveryService.updateScmSaleDelivery(delivery);
			}
		}
	}
}
