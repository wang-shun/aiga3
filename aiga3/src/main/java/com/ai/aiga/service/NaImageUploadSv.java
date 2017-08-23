package com.ai.aiga.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.dao.NaImageUploadDao;
import com.ai.aiga.domain.NaImageUpload;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.view.controller.photoWall.dto.NaImageUploadRequest;
@Service
@Transactional
public class NaImageUploadSv extends BaseService {

	@Autowired
	private NaImageUploadDao naImageUploadDao;
	
	public NaImageUpload findFileName(Long quesId){
		return naImageUploadDao.selectFileName(quesId);
	}
	
	public NaImageUpload findByPlanIdAndFileType(Long planId, Long fileType){
		return naImageUploadDao.findByPlanIdAndFileType(planId, fileType);
	}
	
	public List<NaImageUpload>findAll(){
		return naImageUploadDao.findAll();
	}
	
	public void saveImage(NaImageUploadRequest request){
		NaImageUpload naImageUpload = new NaImageUpload();

		naImageUpload.setId(request.getId());
		naImageUpload.setFileName(request.getFileName());
		naImageUpload.setImgSrc(request.getImgSrc());
		naImageUpload.setTitle(request.getTitle());
		naImageUpload.setDescription(request.getDescription());
		naImageUpload.setLikeCount(request.getLikeCount());
		naImageUpload.setCommentCount(request.getCommentCount());
		naImageUpload.setIsShared(request.getIsShared());
		naImageUpload.setCreateTime(request.getCreateTime());
		naImageUpload.setPlanId(request.getPlanId());
		naImageUpload.setFileType(request.getFileType());
		naImageUpload.setCreateId(request.getCreateId());
		naImageUpload.setExt1(request.getExt1());
		naImageUpload.setExt2(request.getExt2());
		naImageUpload.setExt3(request.getExt3());
		
		naImageUploadDao.save(naImageUpload);
	}
	
	public void updateIsSharedState(NaImageUploadRequest request){
		NaImageUpload naImageUpload = new NaImageUpload();
		
		naImageUpload.setId(request.getId());
		naImageUpload.setFileName(request.getFileName());
		naImageUpload.setImgSrc(request.getImgSrc());
		naImageUpload.setTitle(request.getTitle());
		naImageUpload.setDescription(request.getDescription());
		naImageUpload.setLikeCount(request.getLikeCount());
		naImageUpload.setCommentCount(request.getCommentCount());
		//
		naImageUpload.setIsShared(request.getIsShared());
		naImageUpload.setCreateTime(request.getCreateTime());
		naImageUpload.setPlanId(request.getPlanId());
		naImageUpload.setFileType(request.getFileType());
		naImageUpload.setCreateId(request.getCreateId());
		naImageUpload.setExt1(request.getExt1());
		naImageUpload.setExt2(request.getExt2());
		naImageUpload.setExt3(request.getExt3());

		naImageUploadDao.save(naImageUpload);
	}
	
	public List<NaImageUpload>findMyImages(String isShared, Long createId){
		return naImageUploadDao.findByIsSharedAndCreateId(isShared, createId);
	}
	
	public List<NaImageUpload>findCommonImages(String isShared){
		return naImageUploadDao.findByIsShared(isShared);
	}
	
	
}
