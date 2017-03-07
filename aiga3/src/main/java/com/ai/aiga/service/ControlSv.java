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
import com.ai.aiga.domain.NaUiControl;
import com.ai.aiga.domain.SysConstant;
import com.ai.aiga.domain.SysRole;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.view.json.ControlRequest;
import com.ai.aiga.view.json.RoleRequest;
import com.ai.aiga.view.json.StaffListResponse;

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
	
   public Object listControl(Date createTime1, Date createTime2,NaUiControl condition, int pageNumber, int pageSize) {
		
		List<Condition> cons = new ArrayList<Condition>();
		
		if(condition != null){
			if(StringUtils.isNotBlank(condition.getCtrlName())){
				cons.add(new Condition("ctrlName", "%".concat(condition.getCtrlName()).concat("%"), Condition.Type.LIKE));
			}
			
			if(condition.getCreatorId()!= null){
				cons.add(new Condition("creatorId", condition.getCreatorId(), Condition.Type.EQ));
			}
			
			if(createTime1 != null){
				//SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
				cons.add(new Condition("createTime", createTime1, Condition.Type.GT));
			}
			if(createTime2 != null){
				//SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
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
		
		return nauicontroldao.search(cons, pageable);
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
	public List<NaUiControl> showList(Long funId){
		if(funId == null || funId < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "funId");
		}
		List<Object[]> list = nauicontroldao.findByFun(funId);
		
		
		List<NaUiControl> responses = new ArrayList<NaUiControl>(list.size());
		for(int i = 0; i < list.size(); i++){
			NaUiControl bean  = new NaUiControl();
			Object[] object =(Object[]) list.get(i);
		 
		   bean.setCtrlId(((BigDecimal)object[0]).longValue());
			bean.setParentId(((BigDecimal)object[1]).longValue());
			bean.setIfLeaf(((String) object[2]));
			bean.setCtrlName((String) object[3]);
			bean.setCtrlType((String) object[4]);
			bean.setCtrlXpath((String) object[5]);
			bean.setCtrlDesc((String) object[6]);
			bean.setSysId(((BigDecimal)object[7]).longValue());
			bean.setSysSubId(((BigDecimal)object[8]).longValue());
			bean.setFunId(((BigDecimal)object[9]).longValue());
			bean.setCreatorId(((BigDecimal)object[10]).longValue());
			bean.setCreateTime((Date)object[11]);
			bean.setUpdateId(((BigDecimal)object[12]).longValue());
			bean.setUpdateTime((Date)object[13]);
			
			responses.add(bean);
		}
		return responses;
	}
	public void saveControl(ControlRequest controlRequest) {
		if(controlRequest == null){ 
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
//		if(controlRequest.getCtrlId()<0||StringUtils.isBlank(controlRequest.getCtrlId().toString())){
//			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "ctrlId");
//		}
		//nauicontroldao.backControl(controlRequest.getCtrlId());
		if(controlRequest.getParentId()<0||StringUtils.isBlank(controlRequest.getParentId().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "parentId");
		}
	
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

		if(controlRequest.getUpdateId()==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "updateId");
		}

		if(StringUtils.isBlank(controlRequest.getSysId().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "sysId");
		}
		if(StringUtils.isBlank(controlRequest.getSysSubId().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "sysSubId");
		}
		if(StringUtils.isBlank(controlRequest.getFunId().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "funId");
		}
		NaUiControl nauicontrol=new NaUiControl();
		nauicontrol.setCtrlId(controlRequest.getCtrlId());
		nauicontrol.setCreatorId(controlRequest.getCreatorId());
		nauicontrol.setCreateTime(new Date());
		nauicontrol.setUpdateId(controlRequest.getUpdateId());
		nauicontrol.setCtrlDesc(controlRequest.getCtrlDesc());
		nauicontrol.setCtrlName(controlRequest.getCtrlName());
		nauicontrol.setCtrlType(controlRequest.getCtrlType());
		nauicontrol.setCtrlXpath(controlRequest.getCtrlXpath());
		nauicontrol.setIfLeaf("Y");
		nauicontrol.setParentId(controlRequest.getParentId());
		nauicontrol.setFunId(controlRequest.getFunId());
		nauicontrol.setSysId(controlRequest.getSysId());
		nauicontrol.setSysSubId(controlRequest.getSysSubId());
		nauicontroldao.save(nauicontrol);
}
public void updateControl(ControlRequest controlRequest) {
		
		if(controlRequest == null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		
		if( controlRequest.getCtrlId() < 0||StringUtils.isBlank(controlRequest.getCtrlId().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "ctrlId");
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
			if(StringUtils.isNotBlank(controlRequest.getCreatorId().toString())){
				nauicontrol.setCreatorId(controlRequest.getCreatorId());
			}
			if(StringUtils.isNotBlank(controlRequest.getIfLeaf())){
				nauicontrol.setIfLeaf(controlRequest.getIfLeaf());
			}
			if(StringUtils.isNotBlank(controlRequest.getUpdateId().toString())){
				nauicontrol.setUpdateId(controlRequest.getUpdateId());
			}
			nauicontrol.setUpdateTime(new Date());
			if(StringUtils.isNotBlank(controlRequest.getParentId().toString())){
				nauicontrol.setParentId(controlRequest.getParentId());
				System.out.println("*****"+controlRequest.getParentId());
				System.out.println("11111"+nauicontrol.getParentId());
			}
			if(StringUtils.isNotBlank(controlRequest.getFunId().toString())){
				nauicontrol.setFunId(controlRequest.getFunId());
			}
			if(StringUtils.isNotBlank(controlRequest.getSysId().toString())){
				nauicontrol.setSysId(controlRequest.getSysId());
			}
			if(StringUtils.isNotBlank(controlRequest.getSysSubId().toString())){
				nauicontrol.setSysSubId(controlRequest.getSysSubId());
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