package com.ai.am.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.am.constant.BusiConstant;
import com.ai.am.dao.ArchitectureThirdDao;
import com.ai.am.dao.jpa.Condition;
import com.ai.am.domain.ArchitectureThird;
import com.ai.am.exception.BusinessException;
import com.ai.am.exception.ErrorCode;
import com.ai.am.service.base.BaseService;
import com.ai.am.view.controller.archiQuesManage.dto.ArchitectureThirdRequest;

@Service
@Transactional
public class ArchitectureThirdSv extends BaseService {

	@Autowired
	private ArchitectureThirdDao architectureThirdDao;
	//nmsn
	public List<Map> findWelcomePie(){
		String sql = "select count(*) as sum,b.name,substr(b.id_first,0,1) as rank from architecture_third a,architecture_first b where substr(a.id_third,0,1) = substr(b.id_first,0,1)group by b.name,substr(b.id_first,0,1) order by substr(b.id_first,0,1)";
		return architectureThirdDao.searchByNativeSQL(sql);	
	}
	
	public List<Map> findByFirst(Long idFirst) {
		String sql = "select b.belong_level as third_belong_level, b.name, b.id_third, a.name as sec_name, d.name as fir_name, a.belong_level, b.system_function, b.department, b.project_info, b.design_info, c.code_name, c.ext1 as bg_coloe ,b.ext_3 as media_type"
				+" from architecture_first d inner join ( architecture_second a inner join ( architecture_third b inner join architecture_static_data c on c.code_type = 'SYS_BUILDING_STATE' and b.sys_state = c.code_value ) on a.id_second= b.id_second ) on a.id_first = d.id_first"
				+" where 1=1";
		if(idFirst == 0) {
			return null;
		} else {
			sql += " and a.id_first = "+idFirst;
		}		
		return architectureThirdDao.searchByNativeSQL(sql);	
	}
	
	public List<Map> excelExport(Long idThird,String name) {
		
		String sql = "select b.name, b.id_third, a.name as sec_name, d.name as fir_name, b.belong_level, b.system_function, b.department, b.project_info, b.design_info, c.code_name "
				+" from architecture_first d inner join ( architecture_second a inner join ( architecture_third b inner join architecture_static_data c on c.code_type = 'SYS_BUILDING_STATE' and b.sys_state = c.code_value ) on a.id_second= b.id_second ) on a.id_first = d.id_first"
				+" where 1=1";

		if(idThird>0){
			sql += " and b.id_third = "+idThird;
		}
		if(StringUtils.isNotBlank(name)){
			sql += " and b.name like '%"+name+"%'";
		}
		return architectureThirdDao.searchByNativeSQL(sql);
		
	}
	
	public Object findThirdTransInfo(Long idThird,String name,int pageNumber,int pageSize) {
		
		String sql = "select b.*, a.name as sec_name, d.name as fir_name, c.code_name "
				+" from architecture_first d inner join ( architecture_second a inner join ( architecture_third b inner join architecture_static_data c on c.code_type = 'SYS_BUILDING_STATE' and b.sys_state = c.code_value ) on a.id_second= b.id_second ) on a.id_first = d.id_first"
				+" where 1=1";

		if(idThird>0){
			sql += " and b.id_third = "+idThird;
		}
		if(StringUtils.isNotBlank(name)){
			sql += " and b.name like '%"+name+"%'";
		}
		
		if(pageNumber < 0){
			pageNumber = 0;
		}
		
		if(pageSize <= 0){
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}
		Pageable pageable = new PageRequest(pageNumber, pageSize);
		return architectureThirdDao.searchByNativeSQL(sql, pageable);
		
	}
	
	public List<ArchitectureThird>findByIdThirds(long idThird){
		return architectureThirdDao.findByIdThird(idThird);
	}
	
	public List<ArchitectureThird>findArchitectureThirds(){
		return architectureThirdDao.findAll();
	}
	
	public List<ArchitectureThird> findbyCodition(Long idThird,String name){
		List<Condition> cons = new ArrayList<Condition>();
		if(StringUtils.isNoneBlank(name)){
			 cons.add(new Condition("name", "%".concat(name).concat("%"), Condition.Type.LIKE));
		}
		if(idThird != null && idThird > 0) {
			cons.add(new Condition("idThird", idThird, Condition.Type.EQ));
		}
		return architectureThirdDao.search(cons);
	}
	
	public Page<ArchitectureThird> findbyCoditionPage(Long idThird,String name,int pageNumber,int pageSize){
		List<Condition> cons = new ArrayList<Condition>();
		if(StringUtils.isNoneBlank(name)){
			 cons.add(new Condition("name", "%".concat(name).concat("%"), Condition.Type.LIKE));
		}
		if(idThird != null && idThird > 0) {
			cons.add(new Condition("idThird", idThird, Condition.Type.EQ));
		}
		
		if(pageNumber < 0){
			pageNumber = 0;
		}
		
		if(pageSize <= 0){
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);
		return architectureThirdDao.search(cons,pageable);
	}
	
	public Page<ArchitectureThird> findbySecPage(Long idSecond,int pageNumber,int pageSize){
		List<Condition> cons = new ArrayList<Condition>();

		if(idSecond != null && idSecond > 0) {
			cons.add(new Condition("idSecond", idSecond, Condition.Type.EQ));
		}
		
		if(pageNumber < 0){
			pageNumber = 0;
		}
		
		if(pageSize <= 0){
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);
		return architectureThirdDao.search(cons,pageable);
	}
	
	public List<ArchitectureThird> findbySec(Long idSecond){
		if(idSecond==null||idSecond<0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		return architectureThirdDao.findByIdSecond(idSecond);
	}
	
	public ArchitectureThird findOne(Long onlysysId){
		if(onlysysId==null||onlysysId<0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		return architectureThirdDao.findOne(onlysysId);
	}
	
	public void delete(Long onlysysId){
		if(onlysysId==null||onlysysId<0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		architectureThirdDao.delete(onlysysId);
	}
	
	public void save(ArchitectureThirdRequest request){
		
		ArchitectureThird architectureThird = new ArchitectureThird();
		architectureThird.setOnlysysId(request.getOnlysysId());
		architectureThird.setIdThird(request.getIdThird());
		architectureThird.setName(request.getName());
		architectureThird.setSystemCode(request.getSystemCode());
		architectureThird.setSystemFunction(request.getSystemFunction());
		architectureThird.setDescription(request.getDescription());
		architectureThird.setCode(request.getCode());
		architectureThird.setIdSecond(request.getIdSecond());
		architectureThird.setBelongLevel(request.getBelongLevel());
		architectureThird.setDepartment(request.getDepartment());
		architectureThird.setProjectInfo(request.getProjectInfo());
		architectureThird.setDesignInfo(request.getDesignInfo());
		architectureThird.setState(request.getState());
		architectureThird.setApplyId(request.getApplyId());
		architectureThird.setApplyUser(request.getApplyUser());
		architectureThird.setCreateDate(request.getCreateDate());
		architectureThird.setModifyDate(request.getModifyDate());
		architectureThird.setIdentifiedInfo(request.getIdentifiedInfo());
		architectureThird.setFileInfo(request.getFileInfo());
		architectureThird.setExt1(request.getExt1());
		architectureThird.setExt2(request.getExt2());
		architectureThird.setExt3(request.getExt3());
		architectureThird.setRankInfo(request.getRankInfo());
		architectureThird.setSysState(request.getSysState());
		architectureThirdDao.save(architectureThird);
	}
	
	public void update(ArchitectureThirdRequest request){
		
		ArchitectureThird architectureThird = new ArchitectureThird();
		architectureThird.setOnlysysId(request.getOnlysysId());
		architectureThird.setIdThird(request.getIdThird());
		architectureThird.setName(request.getName());
		architectureThird.setSystemCode(request.getSystemCode());
		architectureThird.setSystemFunction(request.getSystemFunction());
		architectureThird.setDescription(request.getDescription());
		architectureThird.setCode(request.getCode());
		architectureThird.setIdSecond(request.getIdSecond());
		architectureThird.setBelongLevel(request.getBelongLevel());
		architectureThird.setDepartment(request.getDepartment());
		architectureThird.setProjectInfo(request.getProjectInfo());
		architectureThird.setDesignInfo(request.getDesignInfo());
		architectureThird.setState(request.getState());
		architectureThird.setApplyId(request.getApplyId());
		architectureThird.setApplyUser(request.getApplyUser());
		architectureThird.setCreateDate(request.getCreateDate());
		architectureThird.setModifyDate(request.getModifyDate());
		architectureThird.setIdentifiedInfo(request.getIdentifiedInfo());
		architectureThird.setFileInfo(request.getFileInfo());
		architectureThird.setExt1(request.getExt1());
		architectureThird.setExt2(request.getExt2());
		architectureThird.setExt3(request.getExt3());
		
		architectureThirdDao.save(architectureThird);
	}
}
