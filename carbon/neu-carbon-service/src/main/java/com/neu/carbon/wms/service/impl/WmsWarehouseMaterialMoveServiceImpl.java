package com.neu.carbon.wms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neu.carbon.wms.domain.WmsWarehouseMaterial;
import com.neu.carbon.wms.domain.WmsWarehouseMaterialDetail;
import com.neu.carbon.wms.domain.WmsWarehouseMaterialMove;
import com.neu.carbon.wms.mapper.WmsWarehouseMaterialMoveMapper;
import com.neu.carbon.wms.service.IWmsWarehouseMaterialDetailService;
import com.neu.carbon.wms.service.IWmsWarehouseMaterialMoveService;
import com.neu.carbon.wms.service.IWmsWarehouseMaterialService;

/**
 * 移库信息Service业务层处理
 * 
 * @author neuedu
 * @date 2022-07-05
 */
@Service
public class WmsWarehouseMaterialMoveServiceImpl implements IWmsWarehouseMaterialMoveService 
{
    @Autowired
    private WmsWarehouseMaterialMoveMapper wmsWarehouseMaterialMoveMapper;
    @Autowired
    private IWmsWarehouseMaterialService wmsWarehouseMaterialService;
    @Autowired
    private IWmsWarehouseMaterialDetailService wmsWarehouseMaterialDetailService;

    /**
     * 查询移库信息
     * 
     * @param id 移库信息ID
     * @return 移库信息
     */
    @Override
    public WmsWarehouseMaterialMove selectWmsWarehouseMaterialMoveById(Long id)
    {
        return wmsWarehouseMaterialMoveMapper.selectWmsWarehouseMaterialMoveById(id);
    }

    /**
     * 查询移库信息列表
     * 
     * @param wmsWarehouseMaterialMove 移库信息
     * @return 移库信息
     */
    @Override
    public List<WmsWarehouseMaterialMove> selectWmsWarehouseMaterialMoveList(WmsWarehouseMaterialMove wmsWarehouseMaterialMove)
    {
        return wmsWarehouseMaterialMoveMapper.selectWmsWarehouseMaterialMoveList(wmsWarehouseMaterialMove);
    }

    /**
     * 新增移库信息
     * 
     * @param wmsWarehouseMaterialMove 移库信息
     * @return 结果
     */
    @Override
    public int insertWmsWarehouseMaterialMove(WmsWarehouseMaterialMove wmsWarehouseMaterialMove)
    {
        return wmsWarehouseMaterialMoveMapper.insertWmsWarehouseMaterialMove(wmsWarehouseMaterialMove);
    }

    /**
     * 修改移库信息
     * 
     * @param wmsWarehouseMaterialMove 移库信息
     * @return 结果
     */
    @Override
    public int updateWmsWarehouseMaterialMove(WmsWarehouseMaterialMove wmsWarehouseMaterialMove)
    {
        return wmsWarehouseMaterialMoveMapper.updateWmsWarehouseMaterialMove(wmsWarehouseMaterialMove);
    }

    /**
     * 批量删除移库信息
     * 
     * @param ids 需要删除的移库信息ID
     * @return 结果
     */
    @Override
    public int deleteWmsWarehouseMaterialMoveByIds(Long[] ids)
    {
        return wmsWarehouseMaterialMoveMapper.deleteWmsWarehouseMaterialMoveByIds(ids);
    }

    /**
     * 删除移库信息信息
     * 
     * @param id 移库信息ID
     * @return 结果
     */
    @Override
    public int deleteWmsWarehouseMaterialMoveById(Long id)
    {
        return wmsWarehouseMaterialMoveMapper.deleteWmsWarehouseMaterialMoveById(id);
    }
    
    @Transactional
	@Override
	public void move(List<WmsWarehouseMaterialMove> materialMoveList) {
    	materialMoveList.stream().forEach(move->{
    		//从源仓库扣减明细库存
    		WmsWarehouseMaterialDetail detailCond = new WmsWarehouseMaterialDetail();
    		detailCond.setMaterialId(move.getMaterialId());
    		detailCond.setWhId(move.getSourceWarehouseId());
    		detailCond.setWhRegionId(move.getSourceRegionId());
    		detailCond.setWhLocationId(move.getSourceLocationId());
    		detailCond.setBatchNo(move.getBatchNo());
    		List<WmsWarehouseMaterialDetail> inventoryList = wmsWarehouseMaterialDetailService.selectWmsWarehouseMaterialDetailList(detailCond);
    		WmsWarehouseMaterialDetail source = inventoryList.get(0);
    		double total = source.getInventory()==null?0:source.getInventory();
    		double moveQuantity = move.getMoveQuantity()==null?0:move.getMoveQuantity();
    		double newTotal = total - moveQuantity;
    		source.setInventory(newTotal);
    		wmsWarehouseMaterialDetailService.updateWmsWarehouseMaterialDetail(source);
    		//更新源仓库总库存
    		wmsWarehouseMaterialService.updateMaterialTotalInventory(source.getWhMaterialId());
    		//目标仓库加库存
    		WmsWarehouseMaterial whmCond = new WmsWarehouseMaterial();
    		whmCond.setWarehouseId(move.getTargetWarehouseId());
    		whmCond.setMaterialId(move.getMaterialId());
    		//检查目标仓库是否有此物料
    		List<WmsWarehouseMaterial> whMaterialList = wmsWarehouseMaterialService.selectWmsWarehouseMaterialList(whmCond);
    		if(whMaterialList!=null&&!whMaterialList.isEmpty()) {
    			WmsWarehouseMaterial targetWhMaterial = whMaterialList.get(0);
    			//有此物料直接更新明细库存
    			WmsWarehouseMaterialDetail targetDetailCond = new WmsWarehouseMaterialDetail();
    			targetDetailCond.setMaterialId(move.getMaterialId());
    			targetDetailCond.setWhId(move.getTargetWarehouseId());
    			targetDetailCond.setWhRegionId(move.getTargetRegionId());
    			targetDetailCond.setWhLocationId(move.getTargetLocationId());
    			targetDetailCond.setBatchNo(move.getBatchNo());
    			//检查是否有此批号的明细库存
        		List<WmsWarehouseMaterialDetail> targetInventoryList = wmsWarehouseMaterialDetailService.selectWmsWarehouseMaterialDetailList(targetDetailCond);
        		if(targetInventoryList!=null&&!targetInventoryList.isEmpty()) {
        			//有此批号的明细库存则增加库存
        			WmsWarehouseMaterialDetail whmDetail = targetInventoryList.get(0);
        			double inventory = whmDetail.getInventory()==null?0:whmDetail.getInventory();
        			whmDetail.setInventory(inventory+moveQuantity);
        			wmsWarehouseMaterialDetailService.updateWmsWarehouseMaterialDetail(whmDetail);
        		}else {
        			//无此批号明细库存则新增
        			WmsWarehouseMaterialDetail whmDetail = new WmsWarehouseMaterialDetail();
        			whmDetail.setWhMaterialId(targetWhMaterial.getId());
        			whmDetail.setWhId(move.getTargetWarehouseId());
        			whmDetail.setWhRegionId(move.getTargetRegionId());
        			whmDetail.setWhLocationId(move.getTargetLocationId());
        			whmDetail.setBatchNo(move.getBatchNo());
        			whmDetail.setInventory(moveQuantity);
        			whmDetail.setMaterialId(move.getMaterialId());
        			whmDetail.setProductDate(source.getProductDate());
        			whmDetail.setManufacturer(source.getManufacturer());
        			wmsWarehouseMaterialDetailService.insertWmsWarehouseMaterialDetail(whmDetail);
        		}
        		//更新目标仓库总库存
        		wmsWarehouseMaterialService.updateMaterialTotalInventory(targetWhMaterial.getId());
    		}else {
    			//目标仓库无此物料则新增
    			WmsWarehouseMaterial whMaterial = new WmsWarehouseMaterial();
    			whMaterial.setMaterialId(move.getMaterialId());
    			whMaterial.setWarehouseId(move.getTargetWarehouseId());
    			List<WmsWarehouseMaterialDetail> whMaterialDetail = new ArrayList<>();
    			WmsWarehouseMaterialDetail whmDetail = new WmsWarehouseMaterialDetail();
    			whmDetail.setWhId(move.getTargetWarehouseId());
    			whmDetail.setWhRegionId(move.getTargetRegionId());
    			whmDetail.setWhLocationId(move.getTargetLocationId());
    			whmDetail.setBatchNo(move.getBatchNo());
    			whmDetail.setInventory(moveQuantity);
    			whmDetail.setMaterialId(move.getMaterialId());
    			whmDetail.setProductDate(source.getProductDate());
    			whmDetail.setManufacturer(source.getManufacturer());
    			whMaterialDetail.add(whmDetail);
    			whMaterial.setWmsWarehouseMaterialDetailList(whMaterialDetail);
    			wmsWarehouseMaterialService.insertWmsWarehouseMaterial(whMaterial);
    			//更新目标仓库总库存
        		wmsWarehouseMaterialService.updateMaterialTotalInventory(whMaterial.getId());
    		}
    		//新增移库记录为已执行
    		move.setStatus("1");
    		wmsWarehouseMaterialMoveMapper.insertWmsWarehouseMaterialMove(move);
    	});
	}
}
