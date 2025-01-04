package com.neu.carbon.mes.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neu.carbon.mes.domain.MesProductFinish;
import com.neu.carbon.mes.domain.MesProductJob;
import com.neu.carbon.mes.domain.MesProductJobMaterial;
import com.neu.carbon.mes.domain.MesProductSchedule;
import com.neu.carbon.mes.domain.MesProductScheduleMaterial;
import com.neu.carbon.mes.mapper.MesProductJobMapper;
import com.neu.carbon.mes.service.IMesProductFinishService;
import com.neu.carbon.mes.service.IMesProductJobService;
import com.neu.carbon.mes.service.IMesProductScheduleService;
import com.neu.common.enums.SerialNoPrefix;
import com.neu.common.utils.DateUtils;
import com.neu.common.utils.StringUtils;
import com.neu.smty.utils.CodeUtil;

/**
 * 生产作业Service业务层处理
 * 
 * @author neuedu
 * @date 2022-07-15
 */
@Service
public class MesProductJobServiceImpl implements IMesProductJobService {
	@Autowired
	private MesProductJobMapper mesProductJobMapper;
	@Autowired
	private IMesProductScheduleService productScheduleService;
	@Autowired
	private IMesProductFinishService productFinishService;

	/**
	 * 查询生产作业
	 * 
	 * @param id
	 *            生产作业ID
	 * @return 生产作业
	 */
	@Override
	public MesProductJob selectMesProductJobById(Long id) {
		return mesProductJobMapper.selectMesProductJobById(id);
	}

	/**
	 * 查询生产作业列表
	 * 
	 * @param mesProductJob
	 *            生产作业
	 * @return 生产作业
	 */
	@Override
	public List<MesProductJob> selectMesProductJobList(MesProductJob mesProductJob) {
		return mesProductJobMapper.selectMesProductJobList(mesProductJob);
	}

	/**
	 * 新增生产作业
	 * 
	 * @param mesProductJob
	 *            生产作业
	 * @return 结果
	 */
	@Transactional
	@Override
	public int insertMesProductJob(MesProductJob mesProductJob) {
		int rows = mesProductJobMapper.insertMesProductJob(mesProductJob);
		insertMesProductJobMaterial(mesProductJob);
		return rows;
	}

	/**
	 * 修改生产作业
	 * 
	 * @param mesProductJob
	 *            生产作业
	 * @return 结果
	 */
	@Transactional
	@Override
	public int updateMesProductJob(MesProductJob mesProductJob) {
		mesProductJobMapper.deleteMesProductJobMaterialByJobId(mesProductJob.getId());
		insertMesProductJobMaterial(mesProductJob);
		return mesProductJobMapper.updateMesProductJob(mesProductJob);
	}

	/**
	 * 批量删除生产作业
	 * 
	 * @param ids
	 *            需要删除的生产作业ID
	 * @return 结果
	 */
	@Transactional
	@Override
	public int deleteMesProductJobByIds(Long[] ids) {
		mesProductJobMapper.deleteMesProductJobMaterialByJobIds(ids);
		return mesProductJobMapper.deleteMesProductJobByIds(ids);
	}

	/**
	 * 删除生产作业信息
	 * 
	 * @param id
	 *            生产作业ID
	 * @return 结果
	 */
	@Transactional
	@Override
	public int deleteMesProductJobById(Long id) {
		mesProductJobMapper.deleteMesProductJobMaterialByJobId(id);
		return mesProductJobMapper.deleteMesProductJobById(id);
	}

	/**
	 * 新增生产作业物料信息
	 * 
	 * @param mesProductJob
	 *            生产作业对象
	 */
	public void insertMesProductJobMaterial(MesProductJob mesProductJob) {
		List<MesProductJobMaterial> mesProductJobMaterialList = mesProductJob.getMesProductJobMaterialList();
		Long id = mesProductJob.getId();
		if (StringUtils.isNotNull(mesProductJobMaterialList)) {
			List<MesProductJobMaterial> list = new ArrayList<MesProductJobMaterial>();
			for (MesProductJobMaterial mesProductJobMaterial : mesProductJobMaterialList) {
				mesProductJobMaterial.setJobId(id);
				list.add(mesProductJobMaterial);
			}
			if (list.size() > 0) {
				mesProductJobMapper.batchMesProductJobMaterial(list);
			}
		}
	}

	@Transactional
	@Override
	public int updateProductJobStatus(MesProductJob mesProductJob) {
		String status = mesProductJob.getStatus();
		MesProductJob job = mesProductJobMapper.selectMesProductJobById(mesProductJob.getId());
		job.setStatus(status);
		// 执行后更细排产细表的物料使用数量
		if ("1".equals(status)) {
			job.setStartTime(DateUtils.getNowDate());
			MesProductSchedule schedule = productScheduleService.selectMesProductScheduleById(job.getScheduleId());
			List<MesProductScheduleMaterial> list = schedule.getMesProductScheduleMaterialList();
			List<MesProductJobMaterial> jobDetail = job.getMesProductJobMaterialList();
			list.stream().forEach(material -> {
				List<MesProductJobMaterial> fList = jobDetail.stream()
						.filter(detail -> detail.getMaterialId().longValue() == material.getMaterialId().longValue())
						.collect(Collectors.toList());
				if (fList != null && !fList.isEmpty()) {
					MesProductJobMaterial fMaterial = fList.get(0);
					double usage = material.getUsageQuantity() == null ? 0 : material.getUsageQuantity();
					double left = material.getLeftQuantity() == null ? 0 : material.getLeftQuantity();
					double consume = fMaterial.getUsageQuantity() == null ? 0 : fMaterial.getUsageQuantity();
					material.setLeftQuantity(left - consume);
					material.setUsageQuantity(usage + consume);
				}
			});
			productScheduleService.updateMesProductSchedule(schedule);
		}
		// 完成后更细排产的生产数量
		if ("2".equals(status)) {
			job.setEndTime(DateUtils.getNowDate());
			MesProductSchedule schedule = productScheduleService.selectMesProductScheduleById(job.getScheduleId());
			double quantity = schedule.getProductQuantity() == null ? 0 : schedule.getProductQuantity();
			schedule.setProductQuantity(quantity + job.getProductQuantity());
			productScheduleService.updateMesProductSchedule(schedule);
			//生成完工单
			MesProductFinish mesProductFinish = new MesProductFinish();
			mesProductFinish.setSerialNo(CodeUtil.getSerialNo(SerialNoPrefix.SCWG));
			mesProductFinish.setPlanId(job.getPlanId());
			mesProductFinish.setJobId(job.getId());
			mesProductFinish.setScheduleId(job.getScheduleId());
			mesProductFinish.setMaterialId(job.getProductId());
			mesProductFinish.setFinishDate(job.getEndTime());
			mesProductFinish.setFinishQuantity(job.getProductQuantity());
			mesProductFinish.setBatchNo(CodeUtil.getSerialNo(SerialNoPrefix.CPB));
			mesProductFinish.setStatus("0");
			productFinishService.insertMesProductFinish(mesProductFinish);
		}
		int r = mesProductJobMapper.updateMesProductJob(job);
		return r;
	}
}
