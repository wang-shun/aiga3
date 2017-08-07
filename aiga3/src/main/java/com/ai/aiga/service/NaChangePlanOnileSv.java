package com.ai.aiga.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.dao.AigaBossTestResultDao;
import com.ai.aiga.dao.NaChangePlanOnileDao;
import com.ai.aiga.dao.NaFileUploadDao;
import com.ai.aiga.dao.PlanDetailManifestDao;
import com.ai.aiga.domain.NaChangePlanOnile;
import com.ai.aiga.domain.NaFileUpload;
import com.ai.aiga.domain.PlanDetailManifest;
import com.ai.aiga.domain.SysRole;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.service.enums.WorkFlowNewEnum;
import com.ai.aiga.service.onlineProcess.NodeRecordSv;
import com.ai.aiga.util.DateUtil;
import com.ai.aiga.util.FileUtil;
import com.ai.aiga.util.TimeUtil;
import com.ai.aiga.util.mapper.BeanMapper;
import com.ai.aiga.view.controller.plan.dto.PlanDetailManifestExcel;
import com.ai.aiga.view.json.NaChangePlanOnileRequest;
import com.ai.aiga.view.util.SessionMgrUtil;
import org.springframework.beans.factory.annotation.Value;


@Service
@Transactional
public class NaChangePlanOnileSv extends BaseService{
	
	@Value("${app.ftp.path}")
	private String ftpPath;
	
	@Autowired
	private NaChangePlanOnileDao  naChangePlanOnileDao ;
	
	@Autowired
	private PlanDetailManifestDao planDetailManifestDao;
	
	@Autowired
	private NaChangePlanOnileSv   naChangePlanOnileSv;
	
	@Autowired
	private AigaBossTestResultDao aigaBossTestResultDao;
	
	@Autowired
	private    NodeRecordSv    nodeRecordSv;
	
	@Autowired
	private NaFileUploadDao naFileUploadDao;
	
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
		naChangePlanOnile.setCreateDate(new Date());
		naChangePlanOnile.setTimely(request.getTimely());
		naChangePlanOnile.setRemark(request.getRemark());
		naChangePlanOnile.setSign(Byte.parseByte("0"));
		//处理状态默认是1:新增
		naChangePlanOnile.setPlanState(1L);
		naChangePlanOnile.setCreateOpId(request.getCreateOpId());
		//如果是变更,设置交付物截至时间是计划变更的前一天
		if(WorkFlowNewEnum.CHANGE_PLAN_PLANCHANGE.getValue()==(long)request.getTypes()||WorkFlowNewEnum.CHANGE_PLAN_EMERGENTCHANGE.getValue()==(long)request.getTypes()){
			naChangePlanOnile.setFileUploadLastTime(TimeUtil.getDayBefore(request.getPlanDate(), 1));
		}
		//如果是上线，则前台设置
		else if(WorkFlowNewEnum.CHANGE_PLAN_PLANONLINE.getValue()==(long)request.getTypes()||WorkFlowNewEnum.CHANGE_PLAN_EMERGENTONLINE.getValue()==(long)request.getTypes()){
			naChangePlanOnile.setFileUploadLastTime(request.getFileUploadLastTime());
		}
		naChangePlanOnileDao.save(naChangePlanOnile);
		
		nodeRecordSv.saveChangeBegin(request.getOnlinePlanName());
		
		return naChangePlanOnile;
			
	}

	
	//修改
	public NaChangePlanOnile  summaryChangePlanOnile(NaChangePlanOnileRequest request){
		if(request == null){ 
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		
	
		if(StringUtils.isBlank(request.getResult().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "");
		}
		
		NaChangePlanOnile naChangePlanOnile=naChangePlanOnileDao.findOne(request.getOnlinePlan());
		naChangePlanOnile.setOnlinePlanName(request.getOnlinePlanName());
		naChangePlanOnile.setPlanDate(request.getPlanDate());
		naChangePlanOnile.setTypes(request.getTypes());
		naChangePlanOnile.setTimely(request.getTimely());
		naChangePlanOnile.setRemark(request.getRemark());
		naChangePlanOnile.setExt3(request.getExt3());
		naChangePlanOnile.setResult(request.getResult());
		naChangePlanOnile.setDoneDate(request.getDoneDate());
		naChangePlanOnile.setCreateDate(request.getCreateDate());
		naChangePlanOnile.setPlanState(request.getPlanState());
		naChangePlanOnile.setFileUploadLastTime(request.getFileUploadLastTime());
		naChangePlanOnileDao.save(naChangePlanOnile);
		return naChangePlanOnile;
			
	}
	
	
	
	public void  select( NaChangePlanOnileRequest request){
		Long node=8L;
		//修改
		NaChangePlanOnile naChangePlanOnile = naChangePlanOnileDao.findOne(request.getOnlinePlan());
		
		if(!request.getExt3().equals("1")){
			naChangePlanOnile.setPlanState(3L);
			naChangePlanOnile.setDoneDate( new Date());
			naChangePlanOnile.setExt2(request.getExt2());
			naChangePlanOnile.setResult(request.getResult());
			naChangePlanOnileDao.save(naChangePlanOnile);
			nodeRecordSv.commit(request.getOnlinePlan(), node);
		}else{
		naChangePlanOnile.setExt2(request.getExt2());
		
		naChangePlanOnile.setResult(request.getResult());
		naChangePlanOnileDao.save(naChangePlanOnile);
		
	      nodeRecordSv.update(request.getOnlinePlan(),node);
		
		
		}
		
		
		
		
	}
	
	
	
	public  NaChangePlanOnile findOne1(Long onlinePlan) {
		if(onlinePlan == null || onlinePlan < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "");
		}
		
		return naChangePlanOnileDao.findOne(onlinePlan);
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
			naChangePlanOnile.setOnlinePlanName(request.getOnlinePlanName());

			naChangePlanOnile.setCreateDate(new Date(System.currentTimeMillis()));
			naChangePlanOnile.setDoneDate(request.getDoneDate());
			naChangePlanOnile.setTimely(request.getTimely());
			naChangePlanOnile.setRemark(request.getRemark());
			naChangePlanOnile.setPlanDate(request.getPlanDate());
			naChangePlanOnile.setPlanState(request.getPlanState());
			naChangePlanOnile.setResult(request.getResult());
			naChangePlanOnile.setPlanState(request.getPlanState());
			naChangePlanOnileDao.save(naChangePlanOnile);
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
	
	
	/**
	 * @ClassName: NaChangePlanOnileSv :: saveExcel
	 * @author: taoyf
	 * @date: 2017年4月11日 下午4:05:15
	 *
	 * @Description:
	 * @param l
	 * @param list          
	 */
	public void saveExcel(Long planId, List<PlanDetailManifestExcel> list) {
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
		
		planDetailManifestDao.save(values);
	}
	
	
	/**
	 * 查询包含其他任务的计划
	 * @param type
	 * @return
	 */
		public Object getOtherPlan(Long type) {
			if(type==null){
				BusinessException.throwBusinessException(ErrorCode.Parameter_null, "type");
			}
			StringBuilder s = new StringBuilder();// 
			s.append("select distinct online_plan, online_plan_name from (");
			s.append("  select a.online_plan, a.online_plan_name   from na_change_plan_onile a left join na_online_task_distribute b   on a.online_plan = b.online_plan where b.parent_task_id = 0  and a.sign=0  and  a.plan_state in (1,2) and b.task_type = 2  ");
				if(type==0){
				//新增
					s.append(" and a.online_plan not in (select online_plan  from AIGA_BOSS_TEST_RESULT)");
			}
				s.append("order by a.plan_date desc )");
				
			return aigaBossTestResultDao.searchByNativeSQL(s.toString());
		}
		
		/**
		 * 下载文档
		 * 
		 * @param fileName
		 * @return
		 */
		public ResponseEntity downloadFile(String fileName, Long id) {

			if (StringUtils.isBlank(fileName)) {
				BusinessException.throwBusinessException(ErrorCode.Parameter_null, "fileName");
			}

			ResponseEntity entity = null;
			if (id != null) {
				// 更新下载信息
				NaFileUpload file = naFileUploadDao.findOne(id);
				file.setDownLoadTime(new Date());
				naFileUploadDao.save(file);
			}

			try {
				entity = FileUtil.downloadFile(fileName);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return entity;

		}

		/**
		 * 批量下载
		 * 
		 * @param ids
		 * @return
		 * @throws Exception
		 */
		public ResponseEntity downloadFileBatch(String ids) throws Exception {
			if (!StringUtils.isNotBlank(ids)) {
				BusinessException.throwBusinessException(ErrorCode.Parameter_null, "请选中要下载的文档");
			}

			ResponseEntity entity = null;
			String basePath = ftpPath + File.separator;// 下载基本路径
			String downloadPath = null;// 真正的下载路径

			String fileName;
			// 临时文件存储路径
			String uri = ftpPath + File.separator + "下载文件.zip";

			File zipFile = new File(uri); // 定义压缩文件名称
			ZipOutputStream zipOut = null;// 声明压缩流对象
			InputStream input = null; // 读取下载文件的读取流
			
			String[] idArray = ids.split(",");
			// 指定压缩输出流将要输出到哪个压缩文件
			// 注意：要在循环外，会在指定目录创建或覆盖文件，否则每次都是新的写入流
			zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
			for (String id : idArray) {
				// 更新下载信息
				NaFileUpload fileInfo = naFileUploadDao.findOne(Long.parseLong(id));
				fileInfo.setDownLoadTime(new Date());
				naFileUploadDao.save(fileInfo);

				// 获取下载路径
				fileName = new String(fileInfo.getFileName().getBytes("utf-8"), "utf-8");
				SimpleDateFormat simpleDateFormat= new SimpleDateFormat("yyyyMMddHHmmss");
				//上传文件的后缀
				String time =simpleDateFormat.format(fileInfo.getCreateTime());
				downloadPath = basePath +fileName+"_"+time;
				// file：要下载的文件
				File file = new File(downloadPath);
				input = new FileInputStream(file);// 读取文件
				//zipOut.putNextEntry(new ZipEntry(fileName)); // 设置ZipEntry对象
				zipOut.putNextEntry(new ZipEntry(fileName)); // 设置压缩文件指定的当前条目

				// 将下载文件读取流的内容写到压缩文件制定的当前条目中
				int len=0;
				byte[] buff = new byte[1024];
				while((len = input.read(buff))!=-1){
					zipOut.write(buff, 0, len);
				}
				input.close();
				zipOut.closeEntry();//关闭压缩文件的当前条目写入流
			}
			zipOut.close() ; 

			// ---------- 执行到这里说明压缩文件已经有内容了，设置返回即可-----------
			if (!zipFile.exists()) {
				throw new Exception("下载文件失败！");
			}
			// 设置头信息
			HttpHeaders headers = new HttpHeaders();
			headers.setContentDispositionFormData("attachment", new String("下载文件.zip".getBytes("gb2312"), "iso8859-1"));
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			// 获得文件byte数组
			byte[] content = null;
			content = fileToByteArray(zipFile);
			
			zipFile.delete();        //将生成的服务器端文件删除
			return new ResponseEntity<byte[]>(content, headers, HttpStatus.CREATED);
		}

		/**
		 * 根据要下载的文件名找到文件并转成byte数组
		 * 
		 * @param name
		 *            文件名
		 * @return 转换后的结果
		 * @throws Exception
		 *             文件不存在，溢出
		 */
		public byte[] fileToByteArray(File file) throws Exception {
			// 获得输入和输出流
			FileInputStream stream = null;
			ByteArrayOutputStream out = null;
			try {
				stream = new FileInputStream(file);
				out = new ByteArrayOutputStream(10000);
				byte[] content = new byte[10000];
				int length = 0;
				while ((length = stream.read(content)) != -1) {
					out.write(content, 0, length);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				stream.close();
				out.close();
			}
			return out.toByteArray();
		}
}
