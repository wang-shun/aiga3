package com.ai.aiga.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.web.multipart.MultipartFile;
import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.dao.ArchitectureGradingDao;
import com.ai.aiga.dao.ArchitectureThirdDao;
import com.ai.aiga.dao.jpa.Condition;
import com.ai.aiga.dao.jpa.ParameterCondition;
import com.ai.aiga.domain.ArchAigaFunctionTime;
import com.ai.aiga.domain.ArchitectureGrading;
import com.ai.aiga.domain.IndexConnect;
import com.ai.aiga.domain.SysMonthApplyReport;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.util.DateUtil;
import com.ai.aiga.util.FileUtil;
import com.ai.aiga.view.controller.archiQuesManage.dto.AmCoreIndexParams;
import com.ai.aiga.view.controller.archibaseline.dto.ArchiGradingConditionInput;
import com.ai.aiga.view.controller.archibaseline.dto.ArchiGradingConditionParam;
import com.ai.aiga.view.controller.archibaseline.dto.MonthReportOutput;
import com.ai.aiga.view.controller.archibaseline.dto.thirdReportOutput;
import com.ai.aiga.view.controller.archibaseline.dto.thirdview.ArchiThirdApplyParams;

@Service
@Transactional
public class ArchitectureGradingSv extends BaseService {
	@Autowired
	private ArchitectureGradingDao architectureGradingDao;
	@Autowired
	private ArchitectureThirdDao architectureThirdDao;
	@Autowired
	private DealFileSv dealFileSv;
	public List<ArchitectureGrading> findAll(){
		return architectureGradingDao.findAll();
	}
	
	
	public List<ArchitectureGrading> systemCheck(ArchitectureGrading condition) throws ParseException {

		List<ArchitectureGrading>list=architectureGradingDao.findByCode(condition.getCode());
		return list;
		
	}
	
	
	public List<Map> findChangeMessage (ArchiGradingConditionParam input) throws ParseException{
		String sql = "select  t.id_belong, t.sys_id, to_char(t.modify_date,'yyyy-mm') as cnt_date from ARCHITECTURE_GRADING t  where 1=1";
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
//		sql += " Group by t.id_belong, t.name ,t.sys_id, to_char(t.modify_date,'yyyy-mm'),t.description";
		
		return architectureGradingDao.searchByNativeSQL(sql);		
	}
	
	public MonthReportOutput sysMonthReport(ArchAigaFunctionTime condition){
		MonthReportOutput output = new MonthReportOutput();
		//申请      applytime   申请  通过  不通过  撤销
		//
		String time="";
		if (condition.getApplyTime() != null) {
			time=condition.getApplyTime();
		}
		StringBuilder nativeSql = new StringBuilder(
			"select distinct (select count(state) from ARCHITECTURE_GRADING t where not (t.apply_user='lizhiyong6' or t.apply_user='xianan' or t.apply_user='dongjian3' or t.apply_user='wangliefeng2' or t.apply_user='liwei48' or t.apply_user='chenting13' ) and to_char(t.modify_date,'yyyyMM') = "+ time +" and ext_1 = '3') as applycount,"+
			"(select count(state) from ARCHITECTURE_GRADING t where not (t.apply_user='lizhiyong6' or t.apply_user='xianan' or t.apply_user='dongjian3' or t.apply_user='wangliefeng2' or t.apply_user='liwei48' or t.apply_user='chenting13' ) and t.state ='审批通过' and to_char(t.modify_date,'yyyyMM') = "+ time +" and ext_1 = '3') as tongGuo ,"+
			"(select count(name) from ARCHITECTURE_GRADING t where not (t.apply_user='lizhiyong6' or t.apply_user='xianan' or t.apply_user='dongjian3' or t.apply_user='wangliefeng2' or t.apply_user='liwei48' or t.apply_user='chenting13' ) and (t.state = '审批未通过' or t.state = '已撤销') and to_char(t.modify_date,'yyyyMM') = "+ time +" and ext_1 = '3')  as boHui,"+
			"(select count(name) from ARCHITECTURE_THIRD t where not (t.apply_user='lizhiyong6' or t.apply_user='xianan' or t.apply_user='dongjian3' or t.apply_user='wangliefeng2' or t.apply_user='liwei48' or t.apply_user='chenting13' ) and to_char(t.create_date,'yyyyMM') = "+ time +" and ext_1 = '3') as xinZeng ,"+
			"(select count(name) from ARCHITECTURE_THIRD t where not (t.apply_user='lizhiyong6' or t.apply_user='xianan' or t.apply_user='dongjian3' or t.apply_user='wangliefeng2' or t.apply_user='liwei48' or t.apply_user='chenting13' ) and id_third like '1%'  and to_char(t.create_date,'yyyyMM') = "+ time +" and ext_1 = '3') as yewu,"+
			"(select count(name) from ARCHITECTURE_THIRD t where not (t.apply_user='lizhiyong6' or t.apply_user='xianan' or t.apply_user='dongjian3' or t.apply_user='wangliefeng2' or t.apply_user='liwei48' or t.apply_user='chenting13' ) and id_third like '2%'  and to_char(t.create_date,'yyyyMM') = "+ time +" and ext_1 = '3') as guanxin,"+
			"(select count(name) from ARCHITECTURE_THIRD t where not (t.apply_user='lizhiyong6' or t.apply_user='xianan' or t.apply_user='dongjian3' or t.apply_user='wangliefeng2' or t.apply_user='liwei48' or t.apply_user='chenting13' ) and id_third like '3%'  and to_char(t.create_date,'yyyyMM') = "+ time +" and ext_1 = '3') as bomc,"+
			"(select count(name) from ARCHITECTURE_THIRD t where not (t.apply_user='lizhiyong6' or t.apply_user='xianan' or t.apply_user='dongjian3' or t.apply_user='wangliefeng2' or t.apply_user='liwei48' or t.apply_user='chenting13' ) and id_third like '5%'  and to_char(t.create_date,'yyyyMM') = "+ time +" and ext_1 = '3') as shuju,"+
			"(select count(name) from ARCHITECTURE_THIRD t where not (t.apply_user='lizhiyong6' or t.apply_user='xianan' or t.apply_user='dongjian3' or t.apply_user='wangliefeng2' or t.apply_user='liwei48' or t.apply_user='chenting13' ) and id_third like '4%'  and to_char(t.create_date,'yyyyMM') = "+ time +" and ext_1 = '3') as anquan,"+
			"(select count(name) from ARCHITECTURE_THIRD t where not (t.apply_user='lizhiyong6' or t.apply_user='xianan' or t.apply_user='dongjian3' or t.apply_user='wangliefeng2' or t.apply_user='liwei48' or t.apply_user='chenting13' ) and id_third like '6%'  and to_char(t.create_date,'yyyyMM') = "+ time +" and ext_1 = '3') as gonggong,"+
			"(select count(name) from ARCHITECTURE_THIRD t where not (t.apply_user='lizhiyong6' or t.apply_user='xianan' or t.apply_user='dongjian3' or t.apply_user='wangliefeng2' or t.apply_user='liwei48' or t.apply_user='chenting13' ) and id_third like '7%'  and to_char(t.create_date,'yyyyMM') = "+ time +" and ext_1 = '3') as wangluo,"+
			"(select count(name) from ARCHITECTURE_THIRD t where not (t.apply_user='lizhiyong6' or t.apply_user='xianan' or t.apply_user='dongjian3' or t.apply_user='wangliefeng2' or t.apply_user='liwei48' or t.apply_user='chenting13' ) and id_third like '8%'  and to_char(t.create_date,'yyyyMM') = "+ time +" and ext_1 = '3') as dishi,"+
			"(select count(name) from ARCHITECTURE_THIRD t where not (t.apply_user='lizhiyong6' or t.apply_user='xianan' or t.apply_user='dongjian3' or t.apply_user='wangliefeng2' or t.apply_user='liwei48' or t.apply_user='chenting13' ) and id_third like '9%'  and to_char(t.create_date,'yyyyMM') = "+ time +" and ext_1 = '3') as kaifang,"+				
			"(select count(state) from ARCHITECTURE_GRADING t where  not (t.apply_user='lizhiyong6' or t.apply_user='xianan' or t.apply_user='dongjian3' or t.apply_user='wangliefeng2' or t.apply_user='liwei48' or t.apply_user='chenting13' ) and to_char(t.apply_time,'yyyyMM') between '201708' and '"+time+"' and ext_1 = '3') as totalcount,"+
			"(select count(state) from ARCHITECTURE_GRADING t where not (t.apply_user='lizhiyong6' or t.apply_user='xianan' or t.apply_user='dongjian3' or t.apply_user='wangliefeng2' or t.apply_user='liwei48' or t.apply_user='chenting13' ) and t.state = '审批通过' and to_char(t.modify_date,'yyyyMM') between '201708' and '"+time+"' and ext_1 = '3') as totalguo,"+
			"(select count(state) from ARCHITECTURE_GRADING t where not (t.apply_user='lizhiyong6' or t.apply_user='xianan' or t.apply_user='dongjian3' or t.apply_user='wangliefeng2' or t.apply_user='liwei48' or t.apply_user='chenting13' ) and t.state = '审批未通过' and to_char(t.modify_date,'yyyyMM') between '201708' and '"+time+"' and ext_1 = '3') as totalnotguo,"+
			"(select count(name) from ARCHITECTURE_THIRD a where not (a.apply_user='lizhiyong6' or a.apply_user='xianan' or a.apply_user='dongjian3' or a.apply_user='wangliefeng2' or a.apply_user='liwei48' or a.apply_user='chenting13' ) and to_char(a.create_date,'yyyyMM') <= '"+time+"' and ext_1 = '3') as totalzeng"+
			" from dual,ARCHITECTURE_GRADING t "
		);
		List<ParameterCondition>params = new ArrayList<ParameterCondition>();

		if (condition.getApplyTime() != null) {			
			String applyIdSql = "select count(apply_id) as applyCount from ARCHITECTURE_GRADING t";
			List<Map> a = architectureGradingDao.searchByNativeSQL(applyIdSql);
			if(a != null){
				a.get(0).get("applyCount");
			}
		}
		List<SysMonthApplyReport> monthApplyCount = architectureGradingDao.searchByNativeSQL(nativeSql.toString(), params, SysMonthApplyReport.class);
		if(monthApplyCount.size()>0){
			output.setSysMonthApplyReport(monthApplyCount.get(0));		
		}	
		return output;
	}
	
	public String[][] thirdAddReport(ArchAigaFunctionTime condition){

		String[][] output = new String[9][200];
		for(int i=0;i<output.length;i++){
			for(int j=0;j<output[i].length;j++){
				output[i][j]="";
			}
		}
		int[] cnt = new int[9];
		for(int i=0;i<cnt.length;i++){
			cnt[i]=0;
		}
		String time="";
		if (condition.getApplyTime() != null) {
			time=condition.getApplyTime();
		}
		StringBuilder nativeSql = new StringBuilder(
			"select name, id_third from ARCHITECTURE_THIRD t where not (t.apply_user='lizhiyong6' or t.apply_user='xianan' or t.apply_user='dongjian3' or t.apply_user='wangliefeng2' or t.apply_user='liwei48' or t.apply_user='chenting13' ) and to_char(t.create_date,'yyyyMM') = "+ time
		);
		List<ParameterCondition>params = new ArrayList<ParameterCondition>();

		List<thirdReportOutput> list = architectureThirdDao.searchByNativeSQL(nativeSql.toString(), thirdReportOutput.class);
		for(int i = 0;i<list.size();i++){
			thirdReportOutput base = list.get(i);
			long data = base.getIdThird();
			int index = (int)(data/10000000)-1;
			output[index][cnt[index]]=base.getName();
			cnt[index] ++;
		}
		return output;
	}
	
	public List<ArchitectureGrading> findTableCondition(ArchitectureGrading input){
		List<Condition> cons = new ArrayList<Condition>();
		if(input.getApplyId()>0) {
			cons.add(new Condition("applyId", input.getApplyId(), Condition.Type.EQ));
		}	
		
		if(input.getSysId()>0){
			cons.add(new Condition("sysId", input.getSysId(), Condition.Type.EQ));
		}
		
		if(input.getIdBelong()!=null && input.getIdBelong()>0) {
			cons.add(new Condition("idBelong", input.getIdBelong(), Condition.Type.EQ));
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
		if(StringUtils.isNoneBlank(input.getCloudOrderId())){
			cons.add(new Condition("cloudOrderId", input.getCloudOrderId(), Condition.Type.EQ));
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
	
	public List<ArchitectureGrading> findByCondition(ArchiGradingConditionInput input) throws ParseException{
		List<Condition> cons = new ArrayList<Condition>();
		
		if(input.getApplyId()==0){
			cons.add(new Condition("applyId", input.getName(), Condition.Type.GT));
		}
		if(input.getApplyId()!=0){
			cons.add(new Condition("applyId", input.getName(), Condition.Type.EQ));
		}
		
		if(StringUtils.isNoneBlank(input.getName())){
			cons.add(new Condition("name", "%".concat(input.getName()).concat("%"), Condition.Type.LIKE));
		}
		
		if(input.getOnlysysId()!=null){
			cons.add(new Condition("onlysysId", input.getOnlysysId(), Condition.Type.EQ));
		}
		
		if(StringUtils.isNoneBlank(input.getState())){
			cons.add(new Condition("state", input.getState(), Condition.Type.EQ));
		}
		
		if(StringUtils.isNoneBlank(input.getCloudOrderId())){
			cons.add(new Condition("cloudOrderId", input.getCloudOrderId(), Condition.Type.EQ));
		}
		
		return architectureGradingDao.search(cons);		
	}
	
	public ArchitectureGrading findOne(Long id){
		if(id==null||id<0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		return architectureGradingDao.findOne(id);
	}
	
	public ArchitectureGrading findByCloudOrderId(String cloudOrderId){
		if(cloudOrderId==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		return architectureGradingDao.findByCloudOrderId(cloudOrderId);
	}
	
	public ArchitectureGrading findByCloudOrderIdAndState(String cloudOrderId){
		if(cloudOrderId==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		String state = "申请";
		return architectureGradingDao.findByCloudOrderIdAndState(cloudOrderId,state);
	}
	
	public List<ArchitectureGrading> findByName(String name){
		if(name==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		return architectureGradingDao.findByName(name);
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
	
	public void newSave(ArchiThirdApplyParams request){
		ArchitectureGrading architectureGrading = new ArchitectureGrading();
		//层级数组
		String[] ruleLevels = new String[]{"跨层","SaaS","BPaaS","UPaaS","DPaaS","IPaaS","TPaaS","IaaS"};
		int index = 0;		
		Date date = new Date();
		String belongLevel = request.getBelongLevel();
		if(!belongLevel.contains(",")) {
			for(int i=0;i<ruleLevels.length;i++) {
				if(ruleLevels[i].equals(belongLevel)) {
					index = i;
					break;
				}
			}
		}
		architectureGrading.setSysId(request.getIdSecond()/100000*10+index);		
		architectureGrading.setCloudOrderId(request.getCloudOrderId());
		architectureGrading.setName(request.getName());
		architectureGrading.setSystemFunction(request.getSystemFunction());
		architectureGrading.setDescription(request.getDescription());
		architectureGrading.setCode(request.getCode());
		architectureGrading.setIdBelong(request.getIdSecond());
		architectureGrading.setBelongLevel(request.getBelongLevel());
		architectureGrading.setDepartment(request.getDepartment());
		architectureGrading.setProjectInfo(request.getProjectInfo());
		architectureGrading.setDesignInfo(request.getDesignInfo());
		architectureGrading.setSysState(request.getSysState());
		architectureGrading.setState("申请");
		architectureGrading.setRankInfo(request.getRankInfo());
		architectureGrading.setApplyUser(request.getApplyUser().getName());
		architectureGrading.setApplyTime(date);
		architectureGrading.setModifyDate(date);
		architectureGrading.setCreateDate(date);
		architectureGrading.setExt1("3");
		architectureGrading.setExt2(request.getSysStateTime());
		architectureGrading.setExt3(request.getMedia().toLowerCase());
//		architectureGrading.setFileId(new BigDecimal(date.getTime()));
		architectureGrading.setDeveloper(request.getDeveloper());
		architectureGrading.setApplyUserInfo(request.getApplyUser().toString());
		try {
			architectureGradingDao.save(architectureGrading);
		} catch (Exception e) {
			BusinessException.throwBusinessException(e.getMessage());
		} finally {
			MultipartFile file = request.getFile();
			if(file!=null){
				// 获取文件名称
				String fileName = file.getOriginalFilename();
				// 设置主机上的文件名
				String fileNameNew = fileName + "_" + DateUtil.getDateStringByDate(date, DateUtil.YYYYMMDDHHMMSS);
				// 把文件上传到主机
				FileUtil.uploadFile(file, fileNameNew);
				//约定planId=3/fileType=3
				dealFileSv.saveFileInfo(3L, fileName, 3L, date);
			}
		}
	}
	
	public void update(ArchitectureGrading architectureGrading){
		architectureGradingDao.save(architectureGrading);
	}
}
