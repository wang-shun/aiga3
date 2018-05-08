package com.ai.aiga.service.staff;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.dao.AigaStaffDao;
import com.ai.aiga.dao.ArchStaffGradDao;
import com.ai.aiga.dao.jpa.Condition;
import com.ai.aiga.domain.AigaStaff;
import com.ai.aiga.domain.ArchStaffGrad;
import com.ai.aiga.service.base.BaseService;
@Service
@Transactional
public class ArchStaffGradSv extends BaseService {
	@Autowired
	private ArchStaffGradDao archStaffGradDao;
	@Autowired
	private AigaStaffDao aigaStaffDao;
	
	public String check(ArchStaffGrad request) {
		//实例表校验
		AigaStaff srcStaff = aigaStaffDao.findByCode(request.getStaffCode());
		if(srcStaff!=null){
			return "对不起，账号已存在!";
		}
		AigaStaff srcStaff2 = aigaStaffDao.findByBillId(request.getBillId());
		if(srcStaff2!=null){
			return "对不起，手机号码已存在";
		}
		List<AigaStaff> srcStaff3 = aigaStaffDao.findByEmail(request.getEmail());
		if(srcStaff3.size()>0 ){
			return "对不起，邮箱已存在";
		}
		//申请表校验
		//sql拼装
		String querysql = "SELECT T.* FROM ARCH_STAFF_GRAD t where "+" t.state = '1' " 
				+ " and (t.staff_code = '"+request.getStaffCode()+"' or t.email = '"+request.getEmail()+"' or t.bill_id = '"+request.getBillId()+"' )";
		
		List<ArchStaffGrad> result = archStaffGradDao.searchByNativeSQL(querysql, ArchStaffGrad.class);
		if(result.size()>0) {
			return "账号申请中，请勿重新申请！";
		}
		return "true";
	}
	public Page<ArchStaffGrad> findApply(int pageNumber,int pageSize) {
		List<Condition> cons = new ArrayList<Condition>();		
		cons.add(new Condition("state", "1", Condition.Type.EQ));
		if(pageNumber < 0){
			pageNumber = 0;
		}
		
		if(pageSize <= 0){
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);
		return archStaffGradDao.search(cons, pageable);
	}
	public String getRejectEmail(Long applyId) {
		ArchStaffGrad back = archStaffGradDao.findOne(applyId);
		return back.getEmail();
	}

	public String reject(Long applyId) {
		String outMessage = "true";
		if(applyId != null && applyId !=0L) {
			ArchStaffGrad back = archStaffGradDao.findOne(applyId);
			if(back == null) {
				outMessage = "申请单不存在";
			} else if("1".equals(back.getState())) {
				back.setState("3");
				back.setModifyDate(new Date());
				archStaffGradDao.save(back);
			} else {
				outMessage = "申请单已被审批";
			}
		} else {
			outMessage  = "申请单编号为空";
		}
		return outMessage;
	}
	
	public void acceptSave(ArchStaffGrad request) {
		ArchStaffGrad back = archStaffGradDao.findOne(request.getApplyId());
		back.setState("2");
		back.setModifyDate(new Date());
		back.setRoleId(request.getRoleId());
		archStaffGradDao.save(back);
	}

	
	public String acceptCheck(ArchStaffGrad request) {
		String outMessage = "true";
		if(request.getApplyId() !=0L) {
			ArchStaffGrad back = archStaffGradDao.findOne(request.getApplyId());
			if(back == null) {
				outMessage = "申请单不存在";
			} else if("1".equals(back.getState())) {

			} else {
				outMessage = "申请单已被审批";
			}
		} else {
			outMessage  = "申请单编号为空";
		}
		return outMessage;
	}
	
	public ArchStaffGrad save(ArchStaffGrad archStaffGrad) {
		return archStaffGradDao.save(archStaffGrad);	
	}
	
	public ArchStaffGrad update(ArchStaffGrad archStaffGrad) {
		return archStaffGradDao.save(archStaffGrad);	
	}
	
	public List<ArchStaffGrad> findAll() {
		return archStaffGradDao.findAll();	
	}
	
	public ArchStaffGrad findOne(Long applyId) {
		return archStaffGradDao.findOne(applyId);	
	}
}
