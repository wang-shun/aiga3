package com.ai.aiga.service;

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
import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.dao.ArchitectureSecondDao;
import com.ai.aiga.dao.ArchitectureThirdDao;
import com.ai.aiga.dao.jpa.Condition;
import com.ai.aiga.domain.ArchitectureSecond;
import com.ai.aiga.domain.ArchitectureThird;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.util.mapper.BeanMapper;
import com.ai.aiga.view.controller.archiQuesManage.dto.ArchiThirdConditionParam;
import com.ai.aiga.view.controller.archiQuesManage.dto.ArchitectureThirdRequest;

@Service
@Transactional
public class ArchitectureThirdSv extends BaseService {

	@Autowired
	private ArchitectureThirdDao architectureThirdDao;

	@Autowired
	private ArchitectureSecondDao architectureSecondDao;
	public List<Map> findWelcomePie(){
		String sql = "select count(*) as sum,b.id_first as id,b.name,substr(b.id_first,0,1) as rank "
					+" from architecture_third a,architecture_first b "
					+" where substr(a.id_third,0,1) = substr(b.id_first,0,1)"
					+" group by b.id_first,b.name,substr(b.id_first,0,1) order by substr(b.id_first,0,1)";
		return architectureThirdDao.searchByNativeSQL(sql);	
	}
	public List<Map> findIdThirds(){
		String sql = "select onlysys_id as onlysysId from aiam.architecture_third";
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
	
	public List<Map> getSystemIdNow(Long idThird) {
		String sql = "SELECT max(substr(t.id_third,5,2)) as sys_index FROM  AIAM.Architecture_Third t where t.id_third like '"+idThird+"%'";	
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
	
	public Object findThirdTransInfo(Long idSecond, Long onlysysId, Long idThird,String name,int pageNumber,int pageSize) {
		
		String sql = "select b.*, a.name as sec_name, d.name as fir_name, c.code_name "
				+" from architecture_first d inner join ( architecture_second a inner join ( architecture_third b inner join architecture_static_data c on c.code_type = 'SYS_BUILDING_STATE' and b.sys_state = c.code_value ) on a.id_second= b.id_second ) on a.id_first = d.id_first"
				+" where 1=1";
		if(onlysysId != null && onlysysId>0){
			sql += " and b.onlysys_id = "+onlysysId;
		}
		if(idThird != null && idThird>0){
			sql += " and b.id_third = "+idThird;
		}
		if(StringUtils.isNotBlank(name)){
			sql += " and (b.name like '%"+name+"%' or b.code like '%"+name+"%')";
		}
		if(idSecond != null && idSecond>0) {
			sql += " and b.id_second = "+idSecond;
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
	
	public List<ArchitectureThird> querybyCodition(ArchiThirdConditionParam input){
		List<Condition> cons = new ArrayList<Condition>();
		if(input.getIdThird()!=null && input.getIdThird() > 0){
			cons.add(new Condition("idThird", input.getIdThird(), Condition.Type.EQ));
		}
		
		if(StringUtils.isNoneBlank(input.getName())){
			cons.add(new Condition("name", "%".concat(input.getName()).concat("%"), Condition.Type.LIKE));
		}
		
		if(input.getOnlysysId()!=null && input.getOnlysysId() > 0){
			cons.add(new Condition("onlysysId", input.getOnlysysId(), Condition.Type.EQ));
		}
		
		if(input.getIdSecond()!=null && input.getIdSecond() > 0){
			cons.add(new Condition("idSecond", input.getIdSecond(), Condition.Type.EQ));
		}
		return architectureThirdDao.search(cons);		
	}
	
	public List<ArchitectureThird> querybyCodition(Long idThird,String name){
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
		try {
			architectureThirdDao.delete(onlysysId);
		} catch (Exception e) {
			BusinessException.throwBusinessException(e.getMessage());
		}
	}
	
	public ArchitectureThirdRequest save(ArchitectureThirdRequest request){
		ArchitectureThird architectureThird = BeanMapper.map(request, ArchitectureThird.class);
		ArchitectureThird outData = new ArchitectureThird();
		try {
			 outData = architectureThirdDao.save(architectureThird);
		} catch (Exception e) {
			BusinessException.throwBusinessException(e.getMessage());
		}
		return BeanMapper.map(outData, ArchitectureThirdRequest.class);
	}
	
	public void update(ArchitectureThirdRequest request){
		ArchitectureThird architectureThird = BeanMapper.map(request, ArchitectureThird.class);
		try {
			architectureThirdDao.save(architectureThird);
		} catch (Exception e) {
			BusinessException.throwBusinessException(e.getMessage());
		}		
	}
}
