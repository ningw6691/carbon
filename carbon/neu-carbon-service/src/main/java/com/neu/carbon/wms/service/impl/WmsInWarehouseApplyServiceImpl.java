package com.neu.carbon.wms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neu.carbon.mes.domain.MesProductFinish;
import com.neu.carbon.mes.service.IMesProductFinishService;
import com.neu.carbon.scm.domain.ScmPurchaseArrive;
import com.neu.carbon.scm.domain.ScmSaleReturn;
import com.neu.carbon.scm.service.IScmPurchaseArriveService;
import com.neu.carbon.scm.service.IScmSaleReturnService;
import com.neu.carbon.wms.domain.WmsInWarehouseApply;
import com.neu.carbon.wms.domain.WmsInWarehouseApplyDetail;
import com.neu.carbon.wms.domain.WmsWarehouseMaterial;
import com.neu.carbon.wms.domain.WmsWarehouseMaterialDetail;
import com.neu.carbon.wms.mapper.WmsInWarehouseApplyDetailMapper;
import com.neu.carbon.wms.mapper.WmsInWarehouseApplyMapper;
import com.neu.carbon.wms.mapper.WmsWarehouseMaterialDetailMapper;
import com.neu.carbon.wms.mapper.WmsWarehouseMaterialMapper;
import com.neu.carbon.wms.service.IWmsInWarehouseApplyService;
import com.neu.carbon.wms.service.IWmsWarehouseMaterialService;
import com.neu.common.enums.SerialNoPrefix;
import com.neu.common.utils.StringUtils;
import com.neu.smty.utils.CodeUtil;

/**
 * 入库申请Service业务层处理
 * 
 * @author neusoft
 * @date 2022-06-27
 */
@Service
public class WmsInWarehouseApplyServiceImpl implements IWmsInWarehouseApplyService {
	@Autowired
	private WmsInWarehouseApplyMapper wmsInWarehouseApplyMapper;
	@Autowired
	private WmsInWarehouseApplyDetailMapper wmsInWarehouseApplyDetailMapper;
	@Autowired
	private WmsWarehouseMaterialMapper wmsWarehouseMaterialMapper;
	@Autowired
	private WmsWarehouseMaterialDetailMapper wmsWarehouseMaterialDetailMapper;
	@Autowired
	private IWmsWarehouseMaterialService wmsWarehouseMaterialService;
	@Autowired
	private IScmPurchaseArriveService purchaseArriveService;
	@Autowired
	private IScmSaleReturnService saleReturnService;
	@Autowired
	private IMesProductFinishService productFinishService;

	/**
	 * 查询入库申请
	 * 
	 * @param id
	 *            入库申请ID
	 * @return 入库申请
	 */
	@Override
	public WmsInWarehouseApply selectWmsInWarehouseApplyById(Long id) {
		return wmsInWarehouseApplyMapper.selectWmsInWarehouseApplyById(id);
	}

	/**
	 * 查询入库申请列表
	 * 
	 * @param wmsInWarehouseApply
	 *            入库申请
	 * @return 入库申请
	 */
	@Override
	public List<WmsInWarehouseApply> selectWmsInWarehouseApplyList(WmsInWarehouseApply wmsInWarehouseApply) {
		return wmsInWarehouseApplyMapper.selectWmsInWarehouseApplyList(wmsInWarehouseApply);
	}

	/**
	 * 新增入库申请
	 * 
	 * @param wmsInWarehouseApply
	 *            入库申请
	 * @return 结果
	 */
	@Transactional
	@Override
	public int insertWmsInWarehouseApply(WmsInWarehouseApply wmsInWarehouseApply) {
		wmsInWarehouseApply.setSerialNo(CodeUtil.getSerialNo(SerialNoPrefix.RKSQ));
		int rows = wmsInWarehouseApplyMapper.insertWmsInWarehouseApply(wmsInWarehouseApply);
		insertWmsInWarehouseApplyDetail(wmsInWarehouseApply);
		// 更新关联单据状态为入库中
		if("1".equals(wmsInWarehouseApply.getBizType())) {
			updateBizBillStatus(wmsInWarehouseApply.getBizType(), wmsInWarehouseApply.getBizBillId(), "1");
		}else {
			updateBizBillStatus(wmsInWarehouseApply.getBizType(), wmsInWarehouseApply.getBizBillId(), "2");
		}
		return rows;
	}

	/**
	 * 更新关联单状态
	 * 
	 * @param bizType
	 * @param billId
	 * @param status
	 */
	private void updateBizBillStatus(String bizType, Long billId, String status) {
		// 更新生产完工单
		if ("1".equals(bizType)) {
			MesProductFinish finish = productFinishService.selectMesProductFinishById(billId);
			if(finish!=null) {
				finish.setStatus(status);
				productFinishService.updateMesProductFinish(finish);
			}
		}
		// 更新采购到货单
		if ("2".equals(bizType)) {
			ScmPurchaseArrive arrive = purchaseArriveService.selectScmPurchaseArriveById(billId);
			if (arrive != null) {
				arrive.setStatus(status);
				purchaseArriveService.updateScmPurchaseArrive(arrive);
			}
		}
		//更新销售退货单
		if ("5".equals(bizType)) {
			ScmSaleReturn saleReturn = saleReturnService.selectScmSaleReturnById(billId);
			if (saleReturn != null) {
				saleReturn.setStatus(status);
				saleReturnService.updateScmSaleReturn(saleReturn);
			}
		}
	}

	/**
	 * 修改入库申请
	 * 
	 * @param wmsInWarehouseApply
	 *            入库申请
	 * @return 结果
	 */
	@Transactional
	@Override
	public int updateWmsInWarehouseApply(WmsInWarehouseApply wmsInWarehouseApply) {
		// 审核时不需要更新明细表
		if (StringUtils.isBlank(wmsInWarehouseApply.getAuditStatus())) {
			wmsInWarehouseApplyMapper.deleteWmsInWarehouseApplyDetailByApplyId(wmsInWarehouseApply.getId());
			insertWmsInWarehouseApplyDetail(wmsInWarehouseApply);
		}
		return wmsInWarehouseApplyMapper.updateWmsInWarehouseApply(wmsInWarehouseApply);
	}

	/**
	 * 批量删除入库申请
	 * 
	 * @param ids
	 *            需要删除的入库申请ID
	 * @return 结果
	 */
	@Transactional
	@Override
	public int deleteWmsInWarehouseApplyByIds(Long[] ids) {
		for (Long id : ids) {
			WmsInWarehouseApply apply = wmsInWarehouseApplyMapper.selectWmsInWarehouseApplyById(id);
			
			if ("1".equals(apply.getBizType())) {
				// 更新完工单状态为检验合格
				updateBizBillStatus(apply.getBizType(), apply.getBizBillId(), "3");
			}else {	
				//更新关联单据状态为未入库
				updateBizBillStatus(apply.getBizType(), apply.getBizBillId(), "0");
			}
		}
		wmsInWarehouseApplyMapper.deleteWmsInWarehouseApplyDetailByApplyIds(ids);
		return wmsInWarehouseApplyMapper.deleteWmsInWarehouseApplyByIds(ids);
	}

	/**
	 * 删除入库申请信息
	 * 
	 * @param id
	 *            入库申请ID
	 * @return 结果
	 */
	@Transactional
	@Override
	public int deleteWmsInWarehouseApplyById(Long id) {
		WmsInWarehouseApply apply = wmsInWarehouseApplyMapper.selectWmsInWarehouseApplyById(id);
		if ("1".equals(apply.getBizType())) {
			// 更新完工单状态为检验合格
			updateBizBillStatus(apply.getBizType(), apply.getBizBillId(), "3");
		}else {	
			//更新关联单据状态为未入库
			updateBizBillStatus(apply.getBizType(), apply.getBizBillId(), "0");
		}
		wmsInWarehouseApplyMapper.deleteWmsInWarehouseApplyDetailByApplyId(id);
		return wmsInWarehouseApplyMapper.deleteWmsInWarehouseApplyById(id);
	}

	/**
	 * 新增入库申请明细信息
	 * 
	 * @param wmsInWarehouseApply
	 *            入库申请对象
	 */
	public void insertWmsInWarehouseApplyDetail(WmsInWarehouseApply wmsInWarehouseApply) {
		List<WmsInWarehouseApplyDetail> wmsInWarehouseApplyDetailList = wmsInWarehouseApply
				.getWmsInWarehouseApplyDetailList();
		Long id = wmsInWarehouseApply.getId();
		if (StringUtils.isNotNull(wmsInWarehouseApplyDetailList)) {
			List<WmsInWarehouseApplyDetail> list = new ArrayList<WmsInWarehouseApplyDetail>();
			for (WmsInWarehouseApplyDetail wmsInWarehouseApplyDetail : wmsInWarehouseApplyDetailList) {
				wmsInWarehouseApplyDetail.setApplyId(id);
				list.add(wmsInWarehouseApplyDetail);
			}
			if (list.size() > 0) {
				wmsInWarehouseApplyMapper.batchWmsInWarehouseApplyDetail(list);
			}
		}
	}

	@Transactional
	@Override
	public void performInWarehouse(Long id) {
		WmsInWarehouseApplyDetail detailCond = new WmsInWarehouseApplyDetail();
		detailCond.setApplyId(id);
		List<WmsInWarehouseApplyDetail> detailList = wmsInWarehouseApplyDetailMapper
				.selectWmsInWarehouseApplyDetailList(detailCond);
		detailList.stream().forEach(detail -> {
			Long warehouseId = detail.getWarehouseId();
			Long materialId = detail.getMaterialId();
			// 计算增加的库存
			double in = detail.getInQuantity() == null ? 0 : detail.getInQuantity();
			double receive = detail.getTotalQuantity() == null ? 0 : detail.getTotalQuantity();
			double increase = Math.min(receive, in);
			// 检查仓库中是否有物料库存
			WmsWarehouseMaterial whmCond = new WmsWarehouseMaterial();
			whmCond.setWarehouseId(warehouseId);
			whmCond.setMaterialId(materialId);
			List<WmsWarehouseMaterial> whmList = wmsWarehouseMaterialMapper.selectWmsWarehouseMaterialList(whmCond);
			if (whmList != null && !whmList.isEmpty()) {
				WmsWarehouseMaterial whMaterial = whmList.get(0);
				WmsWarehouseMaterialDetail whmdCond = new WmsWarehouseMaterialDetail();
				whmdCond.setWhRegionId(detail.getWhRegionId());
				whmdCond.setWhLocationId(detail.getWhLocationId());
				whmdCond.setWhMaterialId(whMaterial.getId());
				whmdCond.setBatchNo(detail.getBatchNo());
				List<WmsWarehouseMaterialDetail> whmDetailList = wmsWarehouseMaterialDetailMapper
						.selectWmsWarehouseMaterialDetailList(whmdCond);
				if (whmDetailList != null && !whmDetailList.isEmpty()) {
					// 修改明细库存
					WmsWarehouseMaterialDetail whMaterialDetail = whmDetailList.get(0);
					double inventory = whMaterialDetail.getInventory() == null ? 0 : whMaterialDetail.getInventory();
					whMaterialDetail.setInventory(inventory + increase);
					wmsWarehouseMaterialDetailMapper.updateWmsWarehouseMaterialDetail(whMaterialDetail);
				} else {
					// 新增库存明细
					insertWarehouseMaterialDetail(whMaterial, detail);
				}
				// 更新仓库总库存
				wmsWarehouseMaterialService.updateMaterialTotalInventory(whMaterial.getId());
			} else {
				// 新增库存
				WmsWarehouseMaterial whMaterial = new WmsWarehouseMaterial();
				whMaterial.setWarehouseId(warehouseId);
				whMaterial.setMaterialId(materialId);
				wmsWarehouseMaterialMapper.insertWmsWarehouseMaterial(whMaterial);
				// 新增库存明细
				insertWarehouseMaterialDetail(whMaterial, detail);
				// 更新仓库总库存
				wmsWarehouseMaterialService.updateMaterialTotalInventory(whMaterial.getId());
			}
		});
		// 更新入库状态
		WmsInWarehouseApply apply = wmsInWarehouseApplyMapper.selectWmsInWarehouseApplyById(id);
		apply.setBillStatus("1");
		wmsInWarehouseApplyMapper.updateWmsInWarehouseApply(apply);
		// 更新业务单据状态为已入库
		if("1".equals(apply.getBizType())) {
			updateBizBillStatus(apply.getBizType(), apply.getBizBillId(), "2");
		}else {
			updateBizBillStatus(apply.getBizType(), apply.getBizBillId(), "1");
		}
	}

	private void insertWarehouseMaterialDetail(WmsWarehouseMaterial whMaterial,
			WmsInWarehouseApplyDetail inWarehouseApplydetail) {
		WmsWarehouseMaterialDetail whMaterialDetail = new WmsWarehouseMaterialDetail();
		whMaterialDetail.setWhId(inWarehouseApplydetail.getWarehouseId());
		whMaterialDetail.setWhRegionId(inWarehouseApplydetail.getWhRegionId());
		whMaterialDetail.setWhLocationId(inWarehouseApplydetail.getWhLocationId());
		whMaterialDetail.setWhMaterialId(whMaterial.getId());
		whMaterialDetail.setProductDate(inWarehouseApplydetail.getProductDate());
		whMaterialDetail.setManufacturer(inWarehouseApplydetail.getManufacturer());
		whMaterialDetail.setBatchNo(inWarehouseApplydetail.getBatchNo());
		whMaterialDetail.setInventory(inWarehouseApplydetail.getInQuantity());
		wmsWarehouseMaterialDetailMapper.insertWmsWarehouseMaterialDetail(whMaterialDetail);
	}
}
