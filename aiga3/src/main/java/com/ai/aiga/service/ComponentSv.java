package com.ai.aiga.service;

import java.math.BigDecimal;
import java.sql.Clob;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.dao.NaUiCompCtrlDao;
import com.ai.aiga.dao.NaUiComponentDao;
import com.ai.aiga.dao.NaUiParamDao;
import com.ai.aiga.dao.jpa.Condition;
import com.ai.aiga.domain.NaUiCompCtrl;
import com.ai.aiga.domain.NaUiComponent;
import com.ai.aiga.domain.NaUiParam;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.view.json.CompTreeResponse;
import com.ai.aiga.view.json.CtrlTreeResponse;
import com.ai.aiga.view.json.NaUiComponentRequest;
import com.ai.aiga.view.json.NaUiComponentResponse;
import com.ai.aiga.view.json.NaUiParamRequest;
import com.ai.aiga.view.json.StaffListResponse;

@Service
@Transactional
public class ComponentSv {
	
	@Autowired
	private NaUiComponentDao naUiComponentDao;
	@Autowired
	private NaUiCompCtrlDao naUiCompCtrlDao;
	@Autowired
	private NaUiParamDao naUiParamDao;
	
	public List<CompTreeResponse> compTree() {
		
		List<Object[]> list = naUiComponentDao.compTree();
		List<CompTreeResponse> responses = new ArrayList<CompTreeResponse>(list.size());
		if(list != null && list.size() > 0){
			for(int i = 0;i < list.size();i++){
				CompTreeResponse bean = new CompTreeResponse();
				Object[] object =(Object[]) list.get(i);
				bean.setId(((BigDecimal)object[0]).longValue());
				bean.setpId(((BigDecimal)object[1]).longValue());
				bean.setName(object[2].toString());
				bean.setIfLeaf((Character) object[3]);
				responses.add(bean);
			}
		}
		
		return responses;
	}

	public Object listByFun(NaUiComponent condition,int pageNumber,int pageSize) {
		
		List<Condition> cons = new ArrayList<Condition>();
		if(condition != null){
			
			if(condition.getParentId() != null){
				cons.add(new Condition("parentId", condition.getParentId(), Condition.Type.EQ));
			}
		}
		
		if(pageNumber < 0){
			pageNumber = 0;
		}
		
		if(pageSize <= 0){
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}
		Pageable pageable = new PageRequest(pageNumber, pageSize);
		return naUiComponentDao.search(cons, pageable);
	}

	public NaUiComponent save(NaUiComponentRequest naUiComponentRequest) {
		
		if(naUiComponentRequest == null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		if(StringUtils.isBlank(naUiComponentRequest.getParentId().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		if(StringUtils.isBlank(naUiComponentRequest.getCompName())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		if(StringUtils.isBlank(naUiComponentRequest.getCompDesc())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
//		if(StringUtils.isBlank(naUiComponentRequest.getCompScript().toString())){
//			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
//		}
		if(StringUtils.isBlank(naUiComponentRequest.getCreatorId().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		if(StringUtils.isBlank(naUiComponentRequest.getUpdateId().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		if(StringUtils.isBlank(naUiComponentRequest.getCreateTime().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		
		NaUiComponent naUiComponent = new NaUiComponent();
		naUiComponent.setParentId(naUiComponentRequest.getParentId());
		naUiComponent.setCompName(naUiComponentRequest.getCompName());
		naUiComponent.setCompDesc(naUiComponentRequest.getCompDesc());
		naUiComponent.setCreateTime(naUiComponentRequest.getCreateTime());
		naUiComponent.setCompScript(naUiComponentRequest.getCompScript());
		
		naUiComponent.setCreatorId(naUiComponentRequest.getCreatorId());
		naUiComponent.setUpdateId(naUiComponentRequest.getUpdateId());
		
		naUiComponentDao.save(naUiComponent);
		return naUiComponent;
	}
	public void saveCompCtrl(NaUiComponentRequest naUiComponentRequest,Long ctrlId){
		
		NaUiComponent naUiComponent = save(naUiComponentRequest);
		if(naUiComponent == null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		if(naUiComponent.getCompId() <0 || naUiComponent.getCompId() ==null)  { 
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "compId");
		}
		if(ctrlId == null || ctrlId<0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "ctrlId");
		}
		System.out.println("***8"+naUiComponent.getCompId());
		NaUiCompCtrl naUiCompCtrl = new NaUiCompCtrl();
		naUiCompCtrl.setCompId(naUiComponent.getCompId());
		naUiCompCtrl.setCtrlId(ctrlId);
		naUiCompCtrlDao.save(naUiCompCtrl);
	}

	public void update(NaUiComponentRequest naUiComponentRequest) {
		
		if(naUiComponentRequest == null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		if(naUiComponentRequest.getCompId() == null || naUiComponentRequest.getCompId() < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "compId");
		}
		naUiComponentDao.backUps(naUiComponentRequest.getCompId());
		NaUiComponent naUiComponent = naUiComponentDao.findOne(naUiComponentRequest.getCompId());
		if(naUiComponent == null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		if(naUiComponent != null){
			if(StringUtils.isNotBlank(naUiComponentRequest.getCompName())){
				naUiComponent.setCompName(naUiComponentRequest.getCompName());
			}
			if(StringUtils.isNotBlank(naUiComponentRequest.getCompDesc())){
				naUiComponent.setCompDesc(naUiComponentRequest.getCompDesc());
			}
//			if(StringUtils.isNotBlank(naUiComponentRequest.getCompScript().toString())){
//				naUiComponent.setCompScript(naUiComponentRequest.getCompScript());
//			}
			if(StringUtils.isNotBlank(naUiComponentRequest.getCreateTime().toString())){
				naUiComponent.setCreateTime(naUiComponentRequest.getCreateTime());
			}
			if(StringUtils.isNotBlank(naUiComponentRequest.getCreatorId().toString())){
				naUiComponent.setCreatorId(naUiComponentRequest.getCreatorId());
			}
			if(StringUtils.isNotBlank(naUiComponentRequest.getUpdateId().toString())){
				naUiComponent.setUpdateId(naUiComponentRequest.getUpdateId());
			}
			naUiComponent.setUpdateTime(new Date(System.currentTimeMillis()));
			naUiComponentDao.save(naUiComponent);
		}
	}

	public void addCompCtrl(Long compId, Long ctrlId) {
		
		if(compId == null || compId < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "compId");
		}
		if(ctrlId == null || ctrlId < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "ctrlId");
		}
		NaUiCompCtrl naUiCompCtrl = new NaUiCompCtrl();
		naUiCompCtrl.setCompId(compId);
		naUiCompCtrl.setCtrlId(ctrlId);
		naUiCompCtrlDao.save(naUiCompCtrl);
	}

	public void delete(Long compId) {
		if(compId == null || compId < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "compId");
		}
		System.out.println("***8"+compId);
		naUiComponentDao.backUps(compId);
		naUiComponentDao.delete(compId);
	}

	public Clob ctrlScript(Long ctrlId) {
		if(ctrlId == null || ctrlId < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "ctrlId");
		}
		Clob ctrlscript = naUiCompCtrlDao.ctrlScript(ctrlId);
		return ctrlscript;
		
	}

	
	public Object listByParam(Date  createTime1, Date  createTime2, NaUiComponent condition, int pageNumber, int pageSize)  {
		
		List<Condition> cons = new ArrayList<Condition>();
		if(condition != null){
			if(StringUtils.isNotBlank(condition.getCompName())){
				cons.add(new Condition("compName", "%".concat(condition.getCompName()).concat("%"), Condition.Type.LIKE));
			}
			
			if(condition.getCreatorId() != null){
				cons.add(new Condition("creatorId", condition.getCreatorId(), Condition.Type.EQ));
			}
			
			if(createTime1 != null){
				cons.add(new Condition("createTime", createTime1, Condition.Type.GT));
			}
			if(createTime2 != null){
				cons.add(new Condition("createTime", createTime2, Condition.Type.LT));
			}
		}
		
		if(pageNumber < 0){
			pageNumber = 0;
		}
		
		if(pageSize <= 0){
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}
		Pageable pageable = new PageRequest(pageNumber, pageSize);
		return naUiComponentDao.search(cons, pageable);
	}

	public List<NaUiParam> compParamList(Long compId) {
		if(compId == null || compId < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "compId");
		}
		List<Object[]> list = naUiParamDao.compParamList(compId);
		List<NaUiParam> responses = new ArrayList<NaUiParam>(list.size());
		if(list !=null && list.size() > 0){
			for(int i = 0; i < list.size(); i++){
				NaUiParam bean = new NaUiParam();
				Object[] objects = (Object[]) list.get(i);
				bean.setParamId(((BigDecimal)objects[0]).longValue());
				bean.setParamName(objects[1].toString());
				bean.setParamValue((Clob) objects[2]);
				bean.setParamDesc(objects[3].toString());
				bean.setParamSql(objects[4].toString());
				bean.setParamExpect(objects[5].toString());
				responses.add(bean);
			}
			
		}
		return responses;
	}

	public void compParamSave(NaUiParamRequest naUiParamRequest) {
		
		if(naUiParamRequest == null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		if(StringUtils.isBlank(naUiParamRequest.getCompId().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		if(StringUtils.isBlank(naUiParamRequest.getParamName())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		if(StringUtils.isBlank(naUiParamRequest.getParamValue().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		if(StringUtils.isBlank(naUiParamRequest.getParamDesc())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		if(StringUtils.isBlank(naUiParamRequest.getParamSql())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		if(StringUtils.isBlank(naUiParamRequest.getParamExpect())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		if(StringUtils.isBlank(naUiParamRequest.getCreatorId().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		NaUiParam NaUiParam = new NaUiParam();
		NaUiParam.setCompId(naUiParamRequest.getCompId());
		NaUiParam.setParamName(naUiParamRequest.getParamName());
		NaUiParam.setParamValue(naUiParamRequest.getParamValue());
		NaUiParam.setParamDesc(naUiParamRequest.getParamDesc());
		NaUiParam.setParamSql(naUiParamRequest.getParamSql());
		NaUiParam.setParamExpect(naUiParamRequest.getParamExpect());
		NaUiParam.setCreatorId(naUiParamRequest.getCreatorId());
		NaUiParam.setCreateTime(new Date(System.currentTimeMillis()));
		naUiParamDao.save(NaUiParam);
	}

	public void compParamDel(Long compId, Long paramId) {
		
		naUiParamDao.compParamDel(compId, paramId);
		
	}

	public NaUiComponent findOne(Long compId) {
		
		if(compId ==null || compId < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "compId");
		}
		NaUiComponent  naUiComponent = naUiComponentDao.findOne(compId);
		return naUiComponent;
	}

	public NaUiParam paramFindOne(Long paramId) {
		
		if(paramId == null || paramId < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "paramId");
		}
		NaUiParam naUiParam = naUiParamDao.findOne(paramId);
		return naUiParam;
	}

	public void compParamUpdate(NaUiParamRequest naUiParamRequest) {
		
		if(naUiParamRequest == null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		if(naUiParamRequest.getParamId() == null || naUiParamRequest.getParamId() < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		NaUiParam naUiParam = naUiParamDao.findOne(naUiParamRequest.getParamId());
		if(StringUtils.isNotBlank(naUiParamRequest.getParamDesc())){
			naUiParam.setParamDesc(naUiParamRequest.getParamDesc());
		}
		if(StringUtils.isNotBlank(naUiParamRequest.getParamName())){
			naUiParam.setParamName(naUiParamRequest.getParamName());
		}
		if(StringUtils.isNoneBlank(naUiParamRequest.getParamExpect())){
			naUiParam.setParamExpect(naUiParamRequest.getParamExpect());
		}
		if(StringUtils.isNotBlank(naUiParamRequest.getParamSql())){
			naUiParam.setParamSql(naUiParamRequest.getParamSql());
		}
		if(StringUtils.isNotBlank(naUiParamRequest.getParamValue().toString())){
			naUiParam.setParamValue(naUiParamRequest.getParamValue());
		}
		naUiParamDao.save(naUiParam);
		
	}

	public List<CtrlTreeResponse> ctrlTree() {
		
		List<Object[]> list = naUiComponentDao.ctrlTree();
		List<CtrlTreeResponse> responses = new ArrayList<CtrlTreeResponse>(list.size());
		if(list != null && list.size() > 0){
			for(int i = 0;i < list.size();i++){
				CtrlTreeResponse bean = new CtrlTreeResponse();
				Object[] object =(Object[]) list.get(i);
				bean.setId(((BigDecimal)object[0]).longValue());
				bean.setpId(((BigDecimal)object[1]).longValue());
				bean.setName(object[2].toString());
				bean.setIfLeaf((Character) object[3]);
				bean.setScript(object[4].toString());
				responses.add(bean);
			}
		}
		
		return responses;
		
	}
}
