package com.ai.aiga.service.home;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.dao.NaIndexAllocationDao;
import com.ai.aiga.dao.NaStaffKpiRelaDao;
import com.ai.aiga.dao.jpa.Condition;
import com.ai.aiga.domain.NaIndexAllocation;
import com.ai.aiga.domain.NaStaffKpiRela;
import com.ai.aiga.view.util.SessionMgrUtil;

/**
 * @ClassName: HomeDataSv
 * @author: dongch
 * @date: 2017年5月3日 下午3:54:16
 * @Description:
 * 
 */
@Service
@Transactional
public class HomeDataSv {

	@Autowired
	private NaStaffKpiRelaDao naStaffKpiRelaDao;

	@Autowired
	private NaIndexAllocationDao naIndexAllocationDao;

	/**
	 * @ClassName: HomeDataSv :: caseCount
	 * @author: dongch
	 * @date: 2017年5月3日 下午4:26:38
	 *
	 * @Description:首页用例数统计
	 * @return
	 */

	public List<NaIndexAllocation> kpiList() {
		//获取当前用户关联的指标，但是不包括指标的具体信息，只有id
		List<NaStaffKpiRela> relaList = naStaffKpiRelaDao.findByStaffIdAndState(SessionMgrUtil.getStaff().getStaffId(),
				1L);
		//初始化全部的指标默认不显示
		naIndexAllocationDao.update();
		//获取全部的指标的具体信息
		List<NaIndexAllocation> kpiList = naIndexAllocationDao.findAll();
		//如果当前用户有指标
		if (relaList != null && relaList.size() > 0) {
			for (NaStaffKpiRela rela : relaList) {
				for (int i = 0; i < kpiList.size(); i++) {
					NaIndexAllocation kpi = kpiList.get(i);
					//找到当前用户的指标id
					if (kpi.getKpiId() == rela.getKpiId()) {
						//设置当前用户的指标显示
						kpi.setIsShow(1L);
					}
				}

			}
		}else{   //如果当前用户没有指标，加载默认
			List<Condition> cons = new ArrayList<Condition>();
			cons.add(new Condition("isDefault", 1, Condition.Type.EQ));
			kpiList=naIndexAllocationDao.search(cons );
			for (int i = 0; i < kpiList.size(); i++) {
				NaIndexAllocation kpi = kpiList.get(i);
				kpi.setIsShow(1L);
			}
		}
		return kpiList;
	}

	/**
	 * @ClassName: HomeDataSv :: kpi
	 * @author: dongch
	 * @date: 2017年5月12日 下午5:38:18
	 *
	 * @Description:
	 */
	public List<NaIndexAllocation> kpi() {

		List<NaIndexAllocation> list = naIndexAllocationDao.findAll();
		return list;
	}
}
