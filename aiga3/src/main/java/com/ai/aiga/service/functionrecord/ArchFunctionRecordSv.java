package com.ai.aiga.service.functionrecord;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.dao.ArchFunctionRecordDao;
import com.ai.aiga.dao.jpa.Condition;
import com.ai.aiga.domain.ArchFunctionRecord;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.view.controller.function.dto.FunctionRecord;
import com.ai.aiga.view.controller.function.dto.TopDataOutPut;
import com.ai.aiga.view.controller.function.dto.WeekDataOutPut;
@Service
@Transactional
public class ArchFunctionRecordSv extends BaseService {
	@Autowired
    private ArchFunctionRecordDao archFunctionRecordDao;
	
	public List<WeekDataOutPut> weekData(FunctionRecord request) {
		if(StringUtils.isBlank(request.getStartTime())||StringUtils.isBlank(request.getEndTime())) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "time");
		}
		String sql = "SELECT TO_CHAR(t.record_time,'yyyy-mm-dd') as data_time,t.menu_name as data_name,count(*) as data_num "
					+"FROM ARCH_FUNCTION_RECORD t "
					+"where TO_CHAR(t.record_time,'yyyymmdd') >= "+request.getStartTime().replace("-", "")+" and TO_CHAR(t.record_time,'yyyymmdd') <= "+request.getEndTime().replace("-", "")
					+" group by TO_CHAR(t.record_time,'yyyy-mm-dd') , t.menu_name ";

		return archFunctionRecordDao.searchByNativeSQL(sql,WeekDataOutPut.class);
	}
	
	/**
	 * 条件查询
	 * @return
	 * @throws ParseException 
	 */
 	public List<ArchFunctionRecord> findByCondition(FunctionRecord input) throws ParseException {
		List<Condition> cons = new ArrayList<Condition>();
		
		if(StringUtils.isNotBlank(input.getUserName())) {
			cons.add(new Condition("userName", "%".concat(input.getUserName()).concat("%"), Condition.Type.LIKE));
		}	
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		if(StringUtils.isNoneBlank(input.getStartTime())){
		  String dateFir = input.getStartTime()+" 00:00:00";
		  Date beginDate = format.parse(dateFir);	
		  cons.add(new Condition("recordTime", beginDate, Condition.Type.GT));
		}
		if(StringUtils.isNoneBlank(input.getEndTime())){
		  String dateSec = input.getEndTime()+" 23:59:59";
		  Date endDate = format.parse(dateSec);	
		  cons.add(new Condition("recordTime", endDate, Condition.Type.LT));
		}		
		return archFunctionRecordDao.search(cons);		
 	}
	/**
	 * 活跃用户，热门菜单TOP10  查询
	 * @return
	 */	
	public List<TopDataOutPut> queryTopData(String type) {
		String sql="";
		if("menuName".equals(type)) {
			sql = "select * from (SELECT t.menu_name as data_name,count(*) as data_num FROM ARCH_FUNCTION_RECORD t group by t.menu_name order by count(*) DESC) where rownum <= 10";
		} else if ("userName".equals(type)) {
			sql = "select * from (SELECT t.user_name as data_name,count(*) as data_num FROM ARCH_FUNCTION_RECORD t group by t.user_name order by count(*) DESC) where rownum <= 10";
		} else if ("menuNameWeek".equals(type)){
			sql = "select * from (SELECT t.user_name as data_name,count(*) as data_num FROM ARCH_FUNCTION_RECORD t where sysdate-7 <= t.record_time group by t.user_name order by count(*) DESC) where rownum <= 10";
		}else{
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "type");
		}
		return archFunctionRecordDao.searchByNativeSQL(sql,TopDataOutPut.class);
	} 
	
	/**
	 * 条件查询
	 * @return
	 */	
	public Page<ArchFunctionRecord> findByConditionPage(FunctionRecord request,int pageNumber,int pageSize) throws ParseException {
		List<Condition> cons = new ArrayList<Condition>();
		
		if(StringUtils.isNotBlank(request.getUserName())) {
			cons.add(new Condition("userName", "%".concat(request.getUserName()).concat("%"), Condition.Type.LIKE));
		}	
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		if(StringUtils.isNoneBlank(request.getStartTime())){
		  String dateFir = request.getStartTime()+" 00:00:00";
		  Date beginDate = format.parse(dateFir);	
		  cons.add(new Condition("recordTime", beginDate, Condition.Type.GT));
		}
		if(StringUtils.isNoneBlank(request.getEndTime())){
		  String dateSec = request.getEndTime()+" 23:59:59";
		  Date endDate = format.parse(dateSec);	
		  cons.add(new Condition("recordTime", endDate, Condition.Type.LT));
		}		
		if(pageNumber < 0){
			pageNumber = 0;
		}		
		if(pageSize <= 0){
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}
		Pageable pageable = new PageRequest(pageNumber, pageSize);
		return archFunctionRecordDao.search(cons,pageable);	
	}
	
	
	public List<ArchFunctionRecord>findAll(){
		return archFunctionRecordDao.findAll();
	}
	
	//add
	public void save(ArchFunctionRecord request){
		archFunctionRecordDao.save(request);
	}
	
	//delete
	public void delete(Long id){
		if(id==null||id<0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		archFunctionRecordDao.delete(id);
	}

	//update
	public void update(ArchFunctionRecord request){
		archFunctionRecordDao.save(request);
	}
}
