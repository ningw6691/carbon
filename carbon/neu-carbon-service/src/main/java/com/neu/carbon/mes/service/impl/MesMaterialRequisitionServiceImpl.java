package com.neu.carbon.mes.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neu.carbon.mes.domain.MesMaterialRequisition;
import com.neu.carbon.mes.domain.MesMaterialRequisitionDetail;
import com.neu.carbon.mes.domain.MesProductSchedule;
import com.neu.carbon.mes.mapper.MesMaterialRequisitionMapper;
import com.neu.carbon.mes.service.IMesMaterialRequisitionService;
import com.neu.carbon.mes.service.IMesProductScheduleService;
import com.neu.common.enums.SerialNoPrefix;
import com.neu.common.utils.StringUtils;
import com.neu.smty.utils.CodeUtil;

/**
 * 领料申请Service业务层处理
 * 
 * @author neuedu
 * @date 2022-07-14
 */
@Service
public class MesMaterialRequisitionServiceImpl implements IMesMaterialRequisitionService {
	@Autowired
	private MesMaterialRequisitionMapper mesMaterialRequisitionMapper;
	@Autowired
	private IMesProductScheduleService productScheduleService;

	/**
	 * 查询领料申请
	 * 
	 * @param id
	 *            领料申请ID
	 * @return 领料申请
	 */
	@Override
	public MesMaterialRequisition selectMesMaterialRequisitionById(Long id) {
		return mesMaterialRequisitionMapper.selectMesMaterialRequisitionById(id);
	}

	/**
	 * 查询领料申请列表
	 * 
	 * @param mesMaterialRequisition
	 *            领料申请
	 * @return 领料申请
	 */
	@Override
	public List<MesMaterialRequisition> selectMesMaterialRequisitionList(
			MesMaterialRequisition mesMaterialRequisition) {
		return mesMaterialRequisitionMapper.selectMesMaterialRequisitionList(mesMaterialRequisition);
	}

	/**
	 * 新增领料申请
	 * 
	 * @param mesMaterialRequisition
	 *            领料申请
	 * @return 结果
	 */
	@Transactional
	@Override
	public int insertMesMaterialRequisition(MesMaterialRequisition mesMaterialRequisition) {
		mesMaterialRequisition.setSerialNo(CodeUtil.getSerialNo(SerialNoPrefix.LLSQ));
		int rows = mesMaterialRequisitionMapper.insertMesMaterialRequisition(mesMaterialRequisition);
		insertMesMaterialRequisitionDetail(mesMaterialRequisition);
		// 更新排产领料状态
		MesProductSchedule schedule = productScheduleService
				.selectMesProductScheduleById(mesMaterialRequisition.getScheduleId());
		schedule.setReceiveStatus("1");
		productScheduleService.updateMesProductSchedule(schedule);
		return rows;
	}

	/**
	 * 修改领料申请
	 * 
	 * @param mesMaterialRequisition
	 *            领料申请
	 * @return 结果
	 */
	@Transactional
	@Override
	public int updateMesMaterialRequisition(MesMaterialRequisition mesMaterialRequisition) {
		mesMaterialRequisitionMapper
				.deleteMesMaterialRequisitionDetailByMaterialRequisitionId(mesMaterialRequisition.getId());
		insertMesMaterialRequisitionDetail(mesMaterialRequisition);
		return mesMaterialRequisitionMapper.updateMesMaterialRequisition(mesMaterialRequisition);
	}

	@Override
	public int updateMesMaterialRequisitionStatus(MesMaterialRequisition mesMaterialRequisition) {
		return mesMaterialRequisitionMapper.updateMesMaterialRequisition(mesMaterialRequisition);
	}

	/**
	 * 批量删除领料申请
	 * 
	 * @param ids
	 *            需要删除的领料申请ID
	 * @return 结果
	 */
	@Transactional
	@Override
	public int deleteMesMaterialRequisitionByIds(Long[] ids) {
		// 更新排产领料状态
		for (Long id : ids) {
			MesMaterialRequisition mesMaterialRequisition = mesMaterialRequisitionMapper
					.selectMesMaterialRequisitionById(id);
			MesProductSchedule schedule = productScheduleService
					.selectMesProductScheduleById(mesMaterialRequisition.getScheduleId());
			schedule.setReceiveStatus("0");
			productScheduleService.updateMesProductSchedule(schedule);
		}
		mesMaterialRequisitionMapper.deleteMesMaterialRequisitionDetailByMaterialRequisitionIds(ids);
		return mesMaterialRequisitionMapper.deleteMesMaterialRequisitionByIds(ids);
	}

	/**
	 * 删除领料申请信息
	 * 
	 * @param id
	 *            领料申请ID
	 * @return 结果
	 */
	@Transactional
	@Override
	public int deleteMesMaterialRequisitionById(Long id) {
		// 更新排产领料状态
		MesMaterialRequisition mesMaterialRequisition = mesMaterialRequisitionMapper
				.selectMesMaterialRequisitionById(id);
		MesProductSchedule schedule = productScheduleService
				.selectMesProductScheduleById(mesMaterialRequisition.getScheduleId());
		schedule.setReceiveStatus("0");
		productScheduleService.updateMesProductSchedule(schedule);
		mesMaterialRequisitionMapper.deleteMesMaterialRequisitionDetailByMaterialRequisitionId(id);
		return mesMaterialRequisitionMapper.deleteMesMaterialRequisitionById(id);
	}

	/**
	 * 新增领料单物料明细信息
	 * 
	 * @param mesMaterialRequisition
	 *            领料申请对象
	 */
	public void insertMesMaterialRequisitionDetail(MesMaterialRequisition mesMaterialRequisition) {
		List<MesMaterialRequisitionDetail> mesMaterialRequisitionDetailList = mesMaterialRequisition
				.getMesMaterialRequisitionDetailList();
		Long id = mesMaterialRequisition.getId();
		if (StringUtils.isNotNull(mesMaterialRequisitionDetailList)) {
			List<MesMaterialRequisitionDetail> list = new ArrayList<MesMaterialRequisitionDetail>();
			for (MesMaterialRequisitionDetail mesMaterialRequisitionDetail : mesMaterialRequisitionDetailList) {
				mesMaterialRequisitionDetail.setMaterialRequisitionId(id);
				list.add(mesMaterialRequisitionDetail);
			}
			if (list.size() > 0) {
				mesMaterialRequisitionMapper.batchMesMaterialRequisitionDetail(list);
			}
		}
	}

	@Transactional
	@Override
	public int checkMaterialRequisition(MesMaterialRequisition mesMaterialRequisition) {
		MesMaterialRequisition requisition = mesMaterialRequisitionMapper
				.selectMesMaterialRequisitionById(mesMaterialRequisition.getId());
		List<MesMaterialRequisitionDetail> rqList = requisition.getMesMaterialRequisitionDetailList();
		String status = mesMaterialRequisition.getStatus();
		requisition.setStatus(status);
		int r = mesMaterialRequisitionMapper.updateMesMaterialRequisition(requisition);
		// 检验合格
		if ("2".equals(status)) {
			// 更新排产领料状态
			MesProductSchedule schedule = productScheduleService
					.selectMesProductScheduleById(requisition.getScheduleId());
			schedule.setReceiveStatus("1");
			// 更新排产物料剩余数量
			schedule.getMesProductScheduleMaterialList().stream().forEach(detail -> {
				double left = detail.getLeftQuantity() == null ? 0 : detail.getLeftQuantity();
				// 查找物料领料数量
				List<MesMaterialRequisitionDetail> mrq = rqList.stream()
						.filter(rq -> rq.getMaterialId().longValue() == detail.getMaterialId().longValue())
						.collect(Collectors.toList());
				if (mrq != null && !mrq.isEmpty()) {
					MesMaterialRequisitionDetail rqDetail = mrq.get(0);
					double receive = rqDetail.getReceiveQuantity();
					detail.setLeftQuantity(left + receive);
				}
			});
			productScheduleService.updateMesProductSchedule(schedule);
		}
		return r;
	}
}
