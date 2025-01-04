package com.neu.carbon.mes.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

import com.neu.common.enums.SerialNoPrefix;
import com.neu.common.utils.StringUtils;
import com.neu.smty.utils.CodeUtil;

import org.springframework.transaction.annotation.Transactional;
import com.neu.carbon.mes.domain.MesProductPlanDetail;
import com.neu.carbon.mes.mapper.MesProductPlanMapper;
import com.neu.carbon.mes.domain.MesProductPlan;
import com.neu.carbon.mes.service.IMesProductPlanService;

/**
 * 生产计划Service业务层处理
 * 
 * @author neuedu
 * @date 2022-07-12
 */
@Service
public class MesProductPlanServiceImpl implements IMesProductPlanService {
	@Autowired
	private MesProductPlanMapper mesProductPlanMapper;

	/**
	 * 查询生产计划
	 * 
	 * @param id
	 *            生产计划ID
	 * @return 生产计划
	 */
	@Override
	public MesProductPlan selectMesProductPlanById(Long id) {
		return mesProductPlanMapper.selectMesProductPlanById(id);
	}

	/**
	 * 查询生产计划列表
	 * 
	 * @param mesProductPlan
	 *            生产计划
	 * @return 生产计划
	 */
	@Override
	public List<MesProductPlan> selectMesProductPlanList(MesProductPlan mesProductPlan) {
		return mesProductPlanMapper.selectMesProductPlanList(mesProductPlan);
	}

	/**
	 * 新增生产计划
	 * 
	 * @param mesProductPlan
	 *            生产计划
	 * @return 结果
	 */
	@Transactional
	@Override
	public int insertMesProductPlan(MesProductPlan mesProductPlan) {
		mesProductPlan.setSerialNo(CodeUtil.getSerialNo(SerialNoPrefix.SCJH));
		int rows = mesProductPlanMapper.insertMesProductPlan(mesProductPlan);
		insertMesProductPlanDetail(mesProductPlan);
		return rows;
	}

	/**
	 * 修改生产计划
	 * 
	 * @param mesProductPlan
	 *            生产计划
	 * @return 结果
	 */
	@Transactional
	@Override
	public int updateMesProductPlan(MesProductPlan mesProductPlan) {
		// 审核时不需要更新明细表
		if (StringUtils.isBlank(mesProductPlan.getAuditStatus())) {
			mesProductPlanMapper.deleteMesProductPlanDetailByPlanId(mesProductPlan.getId());
			insertMesProductPlanDetail(mesProductPlan);
		}
		return mesProductPlanMapper.updateMesProductPlan(mesProductPlan);
	}

	/**
	 * 批量删除生产计划
	 * 
	 * @param ids
	 *            需要删除的生产计划ID
	 * @return 结果
	 */
	@Transactional
	@Override
	public int deleteMesProductPlanByIds(Long[] ids) {
		mesProductPlanMapper.deleteMesProductPlanDetailByPlanIds(ids);
		return mesProductPlanMapper.deleteMesProductPlanByIds(ids);
	}

	/**
	 * 删除生产计划信息
	 * 
	 * @param id
	 *            生产计划ID
	 * @return 结果
	 */
	@Transactional
	@Override
	public int deleteMesProductPlanById(Long id) {
		mesProductPlanMapper.deleteMesProductPlanDetailByPlanId(id);
		return mesProductPlanMapper.deleteMesProductPlanById(id);
	}

	/**
	 * 新增生产计划明细信息
	 * 
	 * @param mesProductPlan
	 *            生产计划对象
	 */
	public void insertMesProductPlanDetail(MesProductPlan mesProductPlan) {
		List<MesProductPlanDetail> mesProductPlanDetailList = mesProductPlan.getMesProductPlanDetailList();
		Long id = mesProductPlan.getId();
		if (StringUtils.isNotNull(mesProductPlanDetailList)) {
			List<MesProductPlanDetail> list = new ArrayList<MesProductPlanDetail>();
			for (MesProductPlanDetail mesProductPlanDetail : mesProductPlanDetailList) {
				mesProductPlanDetail.setPlanId(id);
				list.add(mesProductPlanDetail);
			}
			if (list.size() > 0) {
				mesProductPlanMapper.batchMesProductPlanDetail(list);
			}
		}
	}
}
