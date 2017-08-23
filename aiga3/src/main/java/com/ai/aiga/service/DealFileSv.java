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
import com.ai.aiga.dao.NaImageUploadDao;
import com.ai.aiga.dao.QuestionInfoDao;
import com.ai.aiga.dao.jpa.Condition;
import com.ai.aiga.domain.NaFileUpload;
import com.ai.aiga.domain.NaImageUpload;
import com.ai.aiga.domain.QuestionInfo;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.ArchIndex.dto.QuestionInfoListExcel;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.util.mapper.BeanMapper;
import com.ai.aiga.view.util.SessionMgrUtil;

@Service
@Transactional
public class DealFileSv extends BaseService{
	
	@Autowired
	private NaFileUploadDao naFileUploadDao;
	@Autowired
	private QuestionInfoDao questionInfoDao;
	@Autowired
	private NaImageUploadDao naImageUploadDao;
	
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
			
			List<NaFileUpload> fileList = naFileUploadDao.findByPlanId(planId);
			if(fileList.size()>0) {
				BusinessException.throwBusinessException(ErrorCode.Parameter_only, "planId");
				return;
			}
			NaFileUpload fileEntity = new NaFileUpload(fileName, date, fileType, planId,
					SessionMgrUtil.getStaff().getStaffId(), 0L);
			naFileUploadDao.save(fileEntity);

		}
		
		public void saveImageInfo(String fileName, String isShared, Long fileType, Date date) {
			
			NaImageUpload imageEntity = new NaImageUpload(fileName, isShared,
					date, fileType, SessionMgrUtil.getStaff().getStaffId());
			naImageUploadDao.save(imageEntity);
			
		}


		/**
		 *  批量上传QuestionInfo
		 * @param planId
		 * @param list
		 * @param fileName
		 * @param fileType
		 * @param date
		 */
		public void saveExcelQuestionInfoList(Long planId, List<QuestionInfoListExcel> list, String fileName,
				Long fileType, Date date) {
			if (planId == null || planId < 0) {
				BusinessException.throwBusinessException(ErrorCode.Parameter_null, "planId");
			}

			if (list == null || list.size() <= 0) {
				BusinessException.throwBusinessException(ErrorCode.Parameter_null, "导入内容");
			}

			// 根据计划删除表中信息
//			questionInfoDao.deleteByQuesId(planId);

			// 把excel信息解析到表里面
			List<QuestionInfo> values = BeanMapper.mapList(list, QuestionInfoListExcel.class,
					QuestionInfo.class);
			if (values != null) {
				long quesId = 4100000000L;
				for (QuestionInfo v : values) {
					v.setQuesId(quesId);
					quesId++;
				}
			}
			NaFileUpload fileEntity = new NaFileUpload(fileName, date, fileType, planId,
					SessionMgrUtil.getStaff().getStaffId(), 0L);
			questionInfoDao.save(values);
			naFileUploadDao.save(fileEntity);

		}

}
