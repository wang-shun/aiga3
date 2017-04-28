package com.ai.aiga.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.dao.NaUiControlDao;
import com.ai.aiga.dao.jpa.Condition;
import com.ai.aiga.domain.NaCaseTemplate;
import com.ai.aiga.domain.NaUiComponent;
import com.ai.aiga.domain.NaUiControl;
import com.ai.aiga.domain.SysConstant;
import com.ai.aiga.domain.SysRole;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.view.controller.role.dto.RoleRequest;
import com.ai.aiga.view.json.ControlRequest;

@Service
@Transactional
public class ControlSv extends BaseService{
	@Autowired
	private NaUiControlDao nauicontroldao;
	/*public NaUiControl findControl(String ctrlName,Long creatorId,Date createTime){
		if(ctrlName == null ){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "funId");
	}
		NaUiControl naUiControl=nauicontroldao.findByName(ctrlName,creatorId,createTime);
		return naUiControl;
	}*/
	
   public Object listControl(int pageNumber, int pageSize,String time1, String time2,NaUiControl condition ) throws ParseException {
	   List<String> list = new ArrayList<String>();
		list.add("ctrlId");
		list.add("ctrlName");
		list.add("creatorName");
		list.add("updateName");
		list.add("createTime");
		list.add("updateTime");
	   String sql = "select a.ctrl_id, a.ctrl_name, b.name as creator_name,"
				+ " (select name from aiga_staff where staff_id = a.update_id) as update_name, a.create_time,"
				+ " a.update_time from na_ui_control a left join aiga_staff b on a.creator_id = b.staff_id where 1=1 ";
	
			if(StringUtils.isNotBlank(condition.getCtrlName())){
				sql += " and a.ctrl_name like '%"+condition.getCtrlName()+"%'";
			}
			if(condition.getFunId()!= null){
				sql += " and a.fun_id = "+condition.getFunId();
			}
			
			if(time1 != null && !time1.equals("")){
			
				sql += " and a.create_time > to_date('"+time1+"','YYYY-MM-DD HH24:MI:SS')";
				
			}
			
			if(time2 != null && !time2.equals("")){
				sql += " and a.create_time < to_date('"+time2+"','YYYY-MM-DD HH24:MI:SS')";
			}
		
		if(pageNumber < 0){
			pageNumber = 0;
		}
		
		if(pageSize <= 0){
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);
		
		return nauicontroldao.searchByNativeSQL(sql, pageable, list);
	}

	public List<NaUiControl> findControlreeList(){
		return nauicontroldao.findAll();
		
	}
	public int backControl(Long Ctrl_id){
		if(Ctrl_id == null || Ctrl_id < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "ctrlId");
		}
		
		return nauicontroldao.backControl(Ctrl_id);
		
	}
	public Object  showList(Long funId,NaUiControl condition,int pageNumber, int pageSize){
		List<Condition> cons = new ArrayList<Condition>();
		if(condition != null){
			if(funId!= null){
				cons.add(new Condition("funId", funId, Condition.Type.EQ));
			}
		}
		
		
		if(pageNumber < 0){
			pageNumber = 0;
		}
		
		if(pageSize <= 0){
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);
		
		return nauicontroldao.search(cons, pageable);
		
		
		//return responses;
	}
	public NaUiControl saveControl(ControlRequest controlRequest) {
		if(controlRequest == null){ 
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
//		if(controlRequest.getCtrlId()<0||StringUtils.isBlank(controlRequest.getCtrlId().toString())){
//			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "ctrlId");
//		}
		//nauicontroldao.backControl(controlRequest.getCtrlId());
		/*if(controlRequest.getParentId()<0||StringUtils.isBlank(controlRequest.getParentId().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "parentId");
		}
	*/
		if(StringUtils.isBlank(controlRequest.getCtrlName())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "ctrlName");
		}
//		if(StringUtils.isBlank(controlRequest.getIfLeaf())){
//			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "ifLeaf");
//		}
		if(StringUtils.isBlank(controlRequest.getCtrlXpath())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "ctrlXpath");
		}
		if(StringUtils.isBlank(controlRequest.getCtrlDesc())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "ctrlDesc");
		}
		
		if(StringUtils.isBlank(controlRequest.getCtrlType())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "ctrlType");
		}

	

	
		NaUiControl nauicontrol=new NaUiControl();
		//nauicontrol.setCtrlId(controlRequest.getCtrlId());
		nauicontrol.setCreatorId(controlRequest.getCreatorId());
		nauicontrol.setCreateTime(controlRequest.getCreateTime());
		//nauicontrol.setUpdateTime(controlRequest.getUpdateTime());
		nauicontrol.setUpdateId(controlRequest.getUpdateId());
		nauicontrol.setCtrlDesc(controlRequest.getCtrlDesc());
		nauicontrol.setCtrlName(controlRequest.getCtrlName());
		nauicontrol.setCtrlType(controlRequest.getCtrlType());
		nauicontrol.setCtrlXpath(controlRequest.getCtrlXpath());
		nauicontrol.setIfLeaf("Y");
/*		nauicontrol.setParentId(controlRequest.getParentId());*/
		nauicontrol.setFunId(controlRequest.getFunId());
		nauicontroldao.save(nauicontrol);
		return nauicontrol;
}
public void updateControl(ControlRequest controlRequest) {
		
		if(controlRequest == null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		
		NaUiControl nauicontrol=nauicontroldao.findOne(controlRequest.getCtrlId());
		
		if(nauicontrol == null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_invalid);
		}
		
		if(nauicontrol != null){
			nauicontroldao.backControl(controlRequest.getCtrlId());
			//nauicontrol.setCtrlId(controlRequest.getCtrlId());
			if(StringUtils.isNotBlank(controlRequest.getCtrlName())){
				nauicontrol.setCtrlName(controlRequest.getCtrlName());
			}
			
			if(StringUtils.isNotBlank(controlRequest.getCtrlType())){
				nauicontrol.setCtrlType(controlRequest.getCtrlType());
			}
			
			if(StringUtils.isNotBlank(controlRequest.getCtrlDesc())){
				nauicontrol.setCtrlDesc(controlRequest.getCtrlDesc());
			}
			if(StringUtils.isNotBlank(controlRequest.getCtrlXpath())){
				nauicontrol.setCtrlXpath(controlRequest.getCtrlXpath());
			}
			
				nauicontrol.setCreatorId(controlRequest.getCreatorId());
			
		
				nauicontrol.setIfLeaf(controlRequest.getIfLeaf());
			
			
				nauicontrol.setUpdateId(controlRequest.getUpdateId());
			
			nauicontrol.setUpdateTime(new Date());
			
			if(StringUtils.isNotBlank(controlRequest.getFunId().toString())){
				nauicontrol.setFunId(controlRequest.getFunId());
			}
			
			nauicontroldao.save(nauicontrol);
		}
	}

public void deleteControl(Long ctrlId) {
		
		if(ctrlId == null || ctrlId < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "ctrlId");
		}
		nauicontroldao.backControl(ctrlId);
		nauicontroldao.delete(ctrlId);
	}


public SysConstant constant(String ctrlType) {
	if(ctrlType == null ){
		BusinessException.throwBusinessException(ErrorCode.Parameter_null, "ctrlType");
	}
	nauicontroldao.showConstant(ctrlType);
	return null;
}

}