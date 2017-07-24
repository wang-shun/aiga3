package com.ai.aiga.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import com.ai.aiga.dao.ArchitectureGradingDao;
import com.ai.aiga.dao.jpa.Condition;
import com.ai.aiga.domain.ArchitectureGrading;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.view.controller.archibaseline.dto.ArchiGradingConditionParam;

@Service
@Transactional
public class ArchitectureGradingSv extends BaseService {
	@Autowired
	private ArchitectureGradingDao architectureGradingDao;
	public List<ArchitectureGrading> findAll(){
		return architectureGradingDao.findAll();
	}
	
	public List<Map> findChangeMessage (ArchiGradingConditionParam input) throws ParseException{
		String sql = "select  t.id_belong, t.sys_id, to_char(t.modify_date,'yyyy-mm') from aiam.ARCHITECTURE_GRADING t  where 1=1";
		if(StringUtils.isNotBlank(input.getState())) {
			sql += " and t.state = '"+input.getState()+"'";
		}
		if(StringUtils.isNotBlank(input.getExt1())) {
			sql += " and t.ext_1 = '"+input.getExt1()+"'";
		}
		if(StringUtils.isNotBlank(input.getBegainTime())) {
			sql += " and to_char(t.modify_date,'yyyy-mm') >= '"+input.getBegainTime()+"'";
		}
		if(StringUtils.isNotBlank(input.getEndTime())) {
			sql += " and to_char(t.modify_date,'yyyy-mm') <= '"+input.getEndTime()+"'";
		}
		sql += " Group by t.id_belong, t.sys_id, to_char(t.modify_date,'yyyy-mm')";
		
		return architectureGradingDao.searchByNativeSQL(sql);		
	}
	
//	public List<ArchitectureGrading> findChangeMessage (ArchiGradingConditionParam input) throws ParseException{
//		List<Condition> cons = new ArrayList<Condition>();
//		
//		if(StringUtils.isNoneBlank(input.getExt1())){
//			cons.add(new Condition("ext1", input.getExt1(), Condition.Type.EQ));
//		}
//
//		if(StringUtils.isNoneBlank(input.getState())){
//			cons.add(new Condition("state", input.getState(), Condition.Type.EQ));
//		}
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
//		if(StringUtils.isNoneBlank(input.getBegainTime())){
//			String  dateFir = input.getBegainTime()+"-01 00:00:00";
//			Date beginDate = format.parse(dateFir);	
//			cons.add(new Condition("applyTime", beginDate, Condition.Type.GT));
//		}
//		if(StringUtils.isNoneBlank(input.getEndTime())){
//			//需要查询endtime当月的记录，故月份加一
//		 	String dateSec = input.getEndTime()+"-01 00:00:00";
//		 	Date endDate = format.parse(dateSec);	
//		 	Calendar end = Calendar.getInstance(); 
//		 	end.setTime(endDate);
//		 	end.add(Calendar.MONTH, +1);
//		 	Date endDateIncrease = end.getTime();
//		 	cons.add(new Condition("applyTime", endDateIncrease, Condition.Type.LT));
//		}
//		return architectureGradingDao.search(cons);		
//	}
	
	public List<ArchitectureGrading> findTableCondition(ArchitectureGrading input){
		List<Condition> cons = new ArrayList<Condition>();
		if(input.getApplyId()>0) {
			cons.add(new Condition("applyId", input.getApplyId(), Condition.Type.EQ));
		}		
		if(input.getSysId()>0){
			cons.add(new Condition("sysId", input.getSysId(), Condition.Type.EQ));
		}
		
		if(StringUtils.isNoneBlank(input.getName())){
			cons.add(new Condition("name", "%".concat(input.getName()).concat("%"), Condition.Type.LIKE));
		}

		if(StringUtils.isNoneBlank(input.getExt1())){
			cons.add(new Condition("ext1", input.getExt1(), Condition.Type.EQ));
		}

		if(StringUtils.isNoneBlank(input.getState())){
			cons.add(new Condition("state", input.getState(), Condition.Type.EQ));
		}

		if(StringUtils.isNoneBlank(input.getApplyUser())){
		    cons.add(new Condition("applyUser", input.getApplyUser(), Condition.Type.EQ));
		}
		return architectureGradingDao.search(cons);		
	}
	
	public Page<ArchitectureGrading> findByConditionPage(ArchiGradingConditionParam input,int pageNumber,int pageSize) throws ParseException{
		List<Condition> cons = new ArrayList<Condition>();
		
		if(StringUtils.isNoneBlank(input.getName())){
		  cons.add(new Condition("name", "%".concat(input.getName()).concat("%"), Condition.Type.LIKE));
		}

		if(StringUtils.isNoneBlank(input.getExt1())){
		  cons.add(new Condition("ext1", input.getExt1(), Condition.Type.EQ));
		}

		if(StringUtils.isNoneBlank(input.getState())){
		  cons.add(new Condition("state", input.getState(), Condition.Type.EQ));
		}

		if(StringUtils.isNoneBlank(input.getApplyUser())){
		  cons.add(new Condition("applyUser", input.getApplyUser(), Condition.Type.EQ));
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		if(StringUtils.isNoneBlank(input.getBegainTime())){
		  String  dateFir = input.getBegainTime()+" 00:00:00";
		  Date beginDate = format.parse(dateFir);	
		  cons.add(new Condition("applyTime", beginDate, Condition.Type.GT));
		}
		if(StringUtils.isNoneBlank(input.getEndTime())){
		  String dateSec = input.getEndTime()+" 23:59:59";
		  Date endDate = format.parse(dateSec);	
		  cons.add(new Condition("applyTime", endDate, Condition.Type.LT));
		}
		
		if(pageNumber < 0){
			pageNumber = 0;
		}
		
		if(pageSize <= 0){
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);
		return architectureGradingDao.search(cons,pageable);		
	}
	
	public Page<ArchitectureGrading> findAllConditionPage(int pageNumber, int pageSize){
		List<Condition> cons = new ArrayList<Condition>();
		if(pageNumber < 0){
			pageNumber = 0;
		}
		
		if(pageSize <= 0){
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);
		return architectureGradingDao.search(cons, pageable);
	}
	
	public ArchitectureGrading findOne(Long id){
		if(id==null||id<0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		return architectureGradingDao.findOne(id);
	}
	
	public void delete(Long id){
		if(id==null||id<0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		architectureGradingDao.delete(id);
	}
	
	public void save(ArchitectureGrading architectureGrading){
			
		architectureGradingDao.save(architectureGrading);
	}
	
	public void update(ArchitectureGrading architectureGrading){
			
		architectureGradingDao.save(architectureGrading);
	}
}
