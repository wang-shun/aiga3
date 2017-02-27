package com.ai.aiga.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.dao.AigaFunctionDao;
import com.ai.aiga.domain.AigaFunction;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.view.json.FunctionRequest;

@Service
@Transactional
public class FunctionSv extends BaseService{
	
	public static final int TOP_PARENT_ID = 0;
	public static final int DEFAULTS_STATE_YES = 1;
	
	@Autowired
	private AigaFunctionDao aigaFunctionDao;

	public List<AigaFunction> findFunctions() {
//		Sort sort = new Sort(new Order(Direction.ASC, "funcLevel"), 
//				new Order(Direction.ASC, "funcSeq"));
		
		return aigaFunctionDao.findAll();
	}
	
	public AigaFunction findOne(Long funcId) {
		if(funcId == null || funcId < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "funcId");
		}
		
		return aigaFunctionDao.findOne(funcId);
	}

	public void save(FunctionRequest request) {
		
		if(request == null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_com_null);
		}
		
		if(request.getParentId() == null || request.getParentId() < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "parentId");
		}
		
		if(StringUtils.isBlank(request.getFuncCode())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "funcCode");
		}
		
		if(StringUtils.isBlank(request.getName())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "name");
		}
		
		if(request.getFuncType() == null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "funcType");
		}
		
		
		long parentId = -1;
		byte funcLevel = -1;
		if(request.getParentId() == TOP_PARENT_ID){
			parentId = TOP_PARENT_ID;
			funcLevel = 1;
		}else{
			parentId = request.getParentId();
			
			AigaFunction parentFunc = aigaFunctionDao.findOne(parentId);
			
			if(parentFunc == null){
				BusinessException.throwBusinessException(ErrorCode.Parameter_invalid, "parentId");
			}else{
				funcLevel = (byte) (parentFunc.getFuncLevel() + 1);
			}
		}
		
		List<AigaFunction> exitFunc = aigaFunctionDao.getByParentIdAndName(parentId, request.getName());
		if(exitFunc == null || exitFunc.size() > 0){
			BusinessException.throwBusinessException("在同一级菜单中, 已经存在name为[" + request.getName() + "]的菜单.");
		}
		
		
		AigaFunction aigaFuction = new AigaFunction();
		
		aigaFuction.setCreateDate(new Date(System.currentTimeMillis()));
		aigaFuction.setDllPath(request.getDllPath());
		aigaFuction.setFuncArg(request.getFuncArg());
		aigaFuction.setFuncCode(request.getFuncCode());
		aigaFuction.setFuncImg(request.getFuncImg());
		aigaFuction.setFuncLevel(funcLevel);
		aigaFuction.setFuncType(request.getFuncType());
		
		Short max = aigaFunctionDao.getMaxFunseqByParentId(parentId);
		int funSeq = 1;
		if(max != null){
			funSeq = max + 1;
		}
		
		aigaFuction.setFunSeq((short)funSeq);
		
		aigaFuction.setName(request.getName());
		aigaFuction.setNotes(request.getNotes());
		aigaFuction.setParentId(parentId);
		aigaFuction.setState((byte)DEFAULTS_STATE_YES);
		aigaFuction.setViewname(request.getViewname());
		
		aigaFunctionDao.save(aigaFuction);
	}

	public void update(FunctionRequest request) {
		
		if(request == null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_com_null);
		}
		
		if(request.getFuncId() == null || request.getFuncId() < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "funcId");
		}
		
		if(StringUtils.isBlank(request.getFuncCode())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "funcCode");
		}
		
		if(StringUtils.isBlank(request.getName())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "name");
		}
		
		if(request.getFuncType() == null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "funcType");
		}
		
//		if(request.getState() == null || !(0 == request.getState() || 1 == request.getState())){
//			BusinessException.throwBusinessException(ErrorCode.Parameter_invalid, "state");
//		}
		
		AigaFunction aigaFuction = aigaFunctionDao.findOne(request.getFuncId());
		if(aigaFuction == null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_invalid, "funcId");
		}
		
		if(StringUtils.isNotEmpty(request.getName())){
			
			List<AigaFunction> exitFunc = aigaFunctionDao.getByParentIdAndNameAndFuncIdNot(aigaFuction.getParentId(), request.getName(), aigaFuction.getFuncId());
			if(exitFunc == null || exitFunc.size() > 0){
				BusinessException.throwBusinessException("在同一级菜单中, 已经存在name为[" + request.getName() + "]的菜单.");
			}
			
			aigaFuction.setName(request.getName());
		}
		
		aigaFuction.setFuncCode(request.getFuncCode());
		aigaFuction.setFuncImg(request.getFuncImg());
		aigaFuction.setFuncType(request.getFuncType());
		aigaFuction.setFuncArg(request.getFuncArg());
		aigaFuction.setDllPath(request.getDllPath());
		aigaFuction.setViewname(request.getViewname());
		aigaFuction.setNotes(request.getNotes());
		//aigaFuction.setState(request.getState());
		
		aigaFuction.setDoneDate(new Date(System.currentTimeMillis()));
		
		aigaFunctionDao.save(aigaFuction);
		
	}

	public void delete(Long funcId) {
		
		if(funcId == null || funcId < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "funcId");
		}
		
		int count = aigaFunctionDao.getCountByParentId(funcId);
		
		if(count > 0){
			BusinessException.throwBusinessException("该菜单下还有子菜单, 请先删除子菜单.");
		}
		
		aigaFunctionDao.delete(funcId);
		
	}


	
	

}
