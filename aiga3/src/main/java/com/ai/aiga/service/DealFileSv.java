package com.ai.aiga.service;

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
import com.ai.aiga.dao.NaFileUploadDao;
import com.ai.aiga.dao.PlanDetailManifestDao;
import com.ai.aiga.dao.jpa.Condition;
import com.ai.aiga.domain.NaFileUpload;
import com.ai.aiga.domain.PlanDetailManifest;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.ArchIndex.dto.QuestionInfoListExcel;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.util.DateUtil;
import com.ai.aiga.util.mapper.BeanMapper;
import com.ai.aiga.view.controller.plan.dto.PlanDetailManifestExcel;
import com.ai.aiga.view.util.SessionMgrUtil;

@Service
@Transactional
public class DealFileSv extends BaseService{
	
	@Autowired
	private PlanDetailManifestDao planDetailManifestDao;
	
	@Autowired
	private NaFileUploadDao naFileUploadDao;
	

	/**
	 * @ClassName: NaChangePlanOnileSv :: saveExcel
	 * @author: taoyf
	 * @date: 2017年4月11日 下午4:05:15
	 *
	 * @Description:计划上线清单
	 * @param l
	 * @param list          
	 */                
	public void saveExcel(Long planId, List<PlanDetailManifestExcel> list,String fileName,Long fileType, Date date) {
		if(planId == null || planId < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "planId");
		}
		
		if(list == null || list.size() <= 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "导入内容");
		}
		
		
		List<PlanDetailManifest> values = BeanMapper.mapList(list, PlanDetailManifestExcel.class, PlanDetailManifest.class);
		if(values != null){
			for(PlanDetailManifest v : values){
				v.setPlanId(planId);
				v.setCreatorId(SessionMgrUtil.getStaff().getOpId());
				v.setCreateTime(DateUtil.getCurrentTime());
			}
		}
		NaFileUpload fileEntity = new NaFileUpload(fileName, date, fileType, planId,
				SessionMgrUtil.getStaff().getStaffId(), 0L);
		planDetailManifestDao.save(values);
		naFileUploadDao.save(fileEntity);
	}
	
	// 查看上线交付物列表
	public Object findNaFileUpload(Long planId, Long type, int pageNumber, int pageSize, String fileName) {
		List<Condition> cons = new ArrayList<Condition>();
		StringBuilder sql = new StringBuilder("select a.*,b.name from NA_FILE_UPLOAD a , aiga_staff b where a.create_id = b.staff_id");
		if (planId != null) {
			sql = sql.append(" and a.plan_Id = " + planId);

		}
		if (type != null) {
			sql = sql.append(" and a.file_Type like '" + type + "%'");

		}
		if (StringUtils.isNotBlank(fileName)) {
			sql = sql.append(" and a.file_name like '"+ fileName+"%'");
		}
		sql.append(" order by CREATE_TIME desc");
		if (pageNumber < 0) {
			pageNumber = 0;
		}
		if (pageSize <= 0) {
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);
		return naFileUploadDao.searchByNativeSQL(sql.toString(), pageable);
	}
		
		public void saveFileInfo(Long planId, String fileName, Long fileType, Date date) {

			NaFileUpload fileEntity = new NaFileUpload(fileName, date, fileType, planId,
					SessionMgrUtil.getStaff().getStaffId(), 0L);
			naFileUploadDao.save(fileEntity);

		}


		public void saveExcelQuestionInfoList(Long planId,
				List<QuestionInfoListExcel> list, String fileName,
				Long fileType, Date date) {
			// TODO Auto-generated method stub
			
		}
}
