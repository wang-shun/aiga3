package com.ai.aiga.service.home;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import antlr.StringUtils;

import com.ai.aiga.dao.ArchitectureGradingDao;
import com.ai.aiga.dao.ArchitectureSecondDao;
import com.ai.aiga.dao.ArchitectureThirdDao;
import com.ai.aiga.dao.NaIndexAllocationDao;
import com.ai.aiga.dao.NaStaffKpiRelaDao;
import com.ai.aiga.dao.jpa.Condition;
import com.ai.aiga.domain.NaIndexAllocation;
import com.ai.aiga.domain.NaStaffKpiRela;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.home.dto.DealTaskInfo;
import com.ai.aiga.service.home.dto.TaskApplyInfo;
import com.ai.aiga.util.mapper.BeanMapper;
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
	
	@Autowired
	private ArchitectureGradingDao architectureGradingDao;
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
			cons.add(new Condition("ext2", 1, Condition.Type.EQ));
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
	
	/**
	 * @ClassName: HomeDataSv :: kpiSave
	 * @author: dongch
	 * @date: 2017年5月12日 下午3:17:58
	 *
	 * @Description:指标配置保存
	 * @param kpis
	 */
	public void kpiSave(String kpiIds) {
		if (kpiIds == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "kpiIds");
		}
		naStaffKpiRelaDao.deleteByStaffId(SessionMgrUtil.getStaff().getStaffId());
		String[] kpiId = kpiIds.split(",");
		for (int i = 0; i < kpiId.length; i++) {
			NaStaffKpiRela rela = new NaStaffKpiRela();
			rela.setKpiId(Long.valueOf(kpiId[i]).longValue());
			rela.setCreateDate(new Date());
			rela.setStaffId(SessionMgrUtil.getStaff().getStaffId());
			rela.setOpId(SessionMgrUtil.getStaff().getStaffId());
			rela.setState(1L);
			naStaffKpiRelaDao.save(rela);
		}
	}
	/**
	 * @ClassName: HomeDataSv :: dealTaskInfo
	 * @author: DuPeng
	 * @date: 2017年9月12日 20:28:32
	 *
	 * @Description:查询工作台信息
	 * @param staffId
	 */
	public DealTaskInfo dealTaskInfo(String name,String hasSysRole) {
		DealTaskInfo data = new DealTaskInfo();	
		//默认为false
		data.setHasQuesRole("false");
		data.setUserName(name);
		if(org.apache.commons.lang3.StringUtils.isBlank(name)) {
			return data;
		} else {
			String applySysSql = "SELECT sum(case when t.ext_1='1' then 1 else 0 end) as apply_first ,sum(case when t.ext_1='2' then 1 else 0 end) as apply_second, sum(case when t.ext_1='3' then 1 else 0 end) as apply_third FROM ARCHITECTURE_GRADING t WHERE t.state = '申请' and t.apply_user = '"+name+"'";	
			List<Map> applySysResults = architectureGradingDao.searchByNativeSQL(applySysSql);	
			Map applySysResult =  applySysResults.get(0);
			if(applySysResult.get("applyFirst") == null) {
				data.setApplySysData("0", "0", "0");
			} else {
				data.setApplyFirst(applySysResult.get("applyFirst").toString());			
				data.setApplySecond(applySysResult.get("applySecond").toString());
				data.setApplyThird(applySysResult.get("applyThird").toString());
			}
		}
		data.setHasSysRole(hasSysRole);
		if("true".equals(hasSysRole)) {
			String dealSysSql = "SELECT sum(case when t.ext_1='1' then 1 else 0 end) as deal_first ,sum(case when t.ext_1='2' then 1 else 0 end) as deal_second, sum(case when t.ext_1='3' then 1 else 0 end) as deal_third FROM ARCHITECTURE_GRADING t WHERE t.state = '申请'";	
			List<Map> dealSysResults = architectureGradingDao.searchByNativeSQL(dealSysSql);	
			Map dealSysResult =  dealSysResults.get(0);
			if(dealSysResult.get("dealFirst") == null) {
				data.setDealSysData("0", "0", "0");
			} else {
				data.setDealFirst(dealSysResult.get("dealFirst").toString());
				data.setDealSecond(dealSysResult.get("dealSecond").toString());
				data.setDealThird(dealSysResult.get("dealThird").toString());
			}
		}
		//问题类查询
		//TODO 等待问题的数据改造
		data.setApplyQuesData("1", "2", "3");

		data.setHasQuesRole("true");
		data.setDealQuesData("1", "2", "3");
		return data;
	}
}
