package com.ai.aiga.service;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.dao.NaChangePlanOnileDao;
import com.ai.aiga.domain.NaChangePlanOnile;
import com.ai.aiga.domain.SysRole;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.view.json.NaChangePlanOnileRequest;

@Service
@Transactional
public class NaChangePlanOnileSv extends BaseService{
	@Autowired
	private NaChangePlanOnileDao  naChangePlanOnileDao ;
	@Autowired
	private NaChangePlanOnileSv   naChangePlanOnileSv;
	public NaChangePlanOnile saveChangePlanOnile(NaChangePlanOnileRequest request){
		if(request == null){ 
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		if(StringUtils.isBlank(request.getOnlinePlanName())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "onlinePlanName");
		}
		if(StringUtils.isBlank(request.getTypes().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "types");
		}
		NaChangePlanOnile naChangePlanOnile=new NaChangePlanOnile();
		naChangePlanOnile.setOnlinePlanName(request.getOnlinePlanName());
		naChangePlanOnile.setPlanDate(request.getPlanDate());
		naChangePlanOnile.setTypes(request.getTypes());
		naChangePlanOnile.setTimely(request.getTimely());
		naChangePlanOnile.setRemark(request.getRemark());
		naChangePlanOnileDao.save(naChangePlanOnile);
		return naChangePlanOnile;
			
	}
	/*
	public void  updatesummaryChangePlanOnile(NaChangePlanOnileRequest request){
		if(request == null){ 
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		NaChangePlanOnile naChangePlanOnile=naChangePlanOnileDao.findById(request.getOnlinePlan());
		if(naChangePlanOnile == null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_invalid);
		}
		
		if(naChangePlanOnile != null){
			if(StringUtils.isNotBlank(request.getResult().toString())){
				naChangePlanOnile.setResult(request.getResult());
			}
			naChangePlanOnile.setOnlinePlan(request.getOnlinePlan());
			naChangePlanOnile.setOnlinePlanName(request.getOnlinePlanName());
			naChangePlanOnile.setPlanDate(request.getPlanDate());
			naChangePlanOnile.setTypes(request.getTypes());
			naChangePlanOnile.setTimely(request.getTimely());
			naChangePlanOnile.setRemark(request.getRemark());
			naChangePlanOnile.setExt1(request.getExt1());
			naChangePlanOnile.setDoneDate(request.getDoneDate());
			naChangePlanOnileDao.save(naChangePlanOnile);
		}	
	}*/
	//修改
	public NaChangePlanOnile  summaryChangePlanOnile(NaChangePlanOnileRequest request){
		if(request == null){ 
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		
	/*if(StringUtils.isBlank(request.getOnlinePlan().toString())){
			
		}*/
	/*if(StringUtils.isBlank(request.getOnlinePlanName())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "onlinePlanName");
		}
		if(request.getPlanState()<0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "");
		}
		if(StringUtils.isBlank(request.getTypes().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "types");
		}
		if(StringUtils.isBlank(request.getPlanDate().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "");
		}
		if(StringUtils.isBlank(request.getTimely().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "");
		}
		if(StringUtils.isBlank(request.getDoneDate().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "");
		}*/
		if(StringUtils.isBlank(request.getResult().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "");
		}
		
		NaChangePlanOnile naChangePlanOnile=new NaChangePlanOnile();
		naChangePlanOnile.setOnlinePlanName(request.getOnlinePlanName());
		naChangePlanOnile.setPlanDate(request.getPlanDate());
		naChangePlanOnile.setTypes(request.getTypes());
		naChangePlanOnile.setTimely(request.getTimely());
		naChangePlanOnile.setRemark(request.getRemark());
		naChangePlanOnile.setExt1(request.getExt1());
		naChangePlanOnile.setResult(request.getResult());
		naChangePlanOnile.setDoneDate(request.getDoneDate());
		naChangePlanOnileDao.save(naChangePlanOnile);
		return naChangePlanOnile;
			
	}
	public String  select( String ext1,NaChangePlanOnileRequest request){
		//修改
		if(ext1=="1"){
			NaChangePlanOnile naChangePlanOnile = naChangePlanOnileDao.findOne(request.getOnlinePlan());
			
			//提交
			if(naChangePlanOnile.getExt1().equals("2")){
				return "no";
			}else{
				naChangePlanOnileSv.summaryChangePlanOnile(request);
				return "true";
			}
		}else{
			NaChangePlanOnile naChangePlanOnile = naChangePlanOnileDao.findOne(request.getOnlinePlan());
			naChangePlanOnile.setExt1("2");
			naChangePlanOnileDao.save(naChangePlanOnile);
			return "true";
		}
		//return ext1;
		
	}
	public  NaChangePlanOnile findOne(Long onlinePlan) {
		if(onlinePlan == null || onlinePlan < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "roleId");
		}
		
		return naChangePlanOnileDao.findById(onlinePlan);
	}
	public void updateChangePlanOnile(NaChangePlanOnileRequest request){
		if(request == null){ 
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		NaChangePlanOnile naChangePlanOnile=naChangePlanOnileDao.findOne(request.getOnlinePlan());
		if(naChangePlanOnile == null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_invalid);
		}
		
		if(naChangePlanOnile != null){
			
			if(StringUtils.isBlank(request.getOnlinePlanName())){
				naChangePlanOnile.setOnlinePlanName(request.getOnlinePlanName());
			}
			/*if(StringUtils.isBlank(request.getTypes().toString())){
				naChangePlanOnile.setTypes(request.getTypes());
			}*/
			naChangePlanOnile.setCreateDate(new Date(System.currentTimeMillis()));
			naChangePlanOnile.setDoneDate(request.getDoneDate());
			naChangePlanOnile.setTimely(request.getTimely());
			naChangePlanOnile.setRemark(request.getRemark());
			naChangePlanOnile.setPlanDate(request.getPlanDate());
			naChangePlanOnile.setResult(request.getResult());
			naChangePlanOnile.setPlanState(request.getPlanState());
		}
		
	}
    public void abandonChangePlanOnile1(Long onlinePlan){
		if(onlinePlan == null || onlinePlan < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "onlinePlan");
		}
		
		naChangePlanOnileDao.delete(onlinePlan);
	}
	public void abandonChangePlanOnile(Long onlinePlan){
		if(onlinePlan == null || onlinePlan < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "onlinePlan");
		}
		naChangePlanOnileDao.abandonChangePlanOnile(onlinePlan);
		}
	public void delectChangePlanOnile(Long onlinePlan){
		if(onlinePlan == null || onlinePlan < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "onlinePlan");
		}
		naChangePlanOnileDao.delectChangePlanOnile(onlinePlan);
	}
}
