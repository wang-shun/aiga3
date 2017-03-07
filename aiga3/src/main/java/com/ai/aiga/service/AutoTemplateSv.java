package com.ai.aiga.service;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.dao.NaAutoTemplateDao;
import com.ai.aiga.dao.NaCaseTemplateDao;
import com.ai.aiga.dao.jpa.Condition;
import com.ai.aiga.domain.NaAutoTemplate;
import com.ai.aiga.domain.NaCaseTemplate;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.view.json.AutoTemplateRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * &#x81ea;&#x52a8;&#x5316;&#x7528;&#x4f8b;&#x6a21;&#x677f;
 *
 * @author defaultekey
 * @date 2017/3/3
 */
@Service
@Transactional
public class AutoTemplateSv {
    @Autowired
    private NaAutoTemplateDao autoTemplateDao;

    @Autowired
    private NaCaseTemplateDao caseTemplateDao;
    /**
     * 保存自动化用例模板信息
     * @param autoTemplateRequest
     * @return
     */
    public NaAutoTemplate save(AutoTemplateRequest autoTemplateRequest){
        if (autoTemplateRequest == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_com_null);
        }
        if (autoTemplateRequest.getCaseId() == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "caseId");
        }
        if (autoTemplateRequest.getCaseType() == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "caseType");
        }
        if (!StringUtils.isNoneBlank(autoTemplateRequest.getTempName())) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "tempName");
        }
        //判断该名字是否已有自动化用例模板存在
        if(autoTemplateDao.findByTempName(autoTemplateRequest.getTempName()) != null){
            BusinessException.throwBusinessException("autoTemplate Name already  existing! please change......");
        }
        NaAutoTemplate autoTemplate=new NaAutoTemplate();
        autoTemplate.setFunId(autoTemplateRequest.getFunId());
        autoTemplate.setBusiId(autoTemplateRequest.getBusiId());
        autoTemplate.setCaseId(autoTemplateRequest.getCaseId());
        autoTemplate.setCaseType(autoTemplateRequest.getCaseType());
        autoTemplate.setImportant(autoTemplateRequest.getImportant());
        autoTemplate.setScId(autoTemplateRequest.getScId());
        autoTemplate.setSysId(autoTemplateRequest.getSysId());
        autoTemplate.setSysSubId(autoTemplateRequest.getSysSubId());
        autoTemplate.setUpdateTime(Calendar.getInstance().getTime());
        autoTemplate.setTestType(autoTemplateRequest.getTestType());
//        autoTemplate.setCreatorId();
//        autoTemplate.setUpdateId();
        return autoTemplateDao.save(autoTemplate);
    }

    /**
     * 修改自动化用例模板信息
     * @param autoTemplateRequest
     * @return
     */
    public NaAutoTemplate update(AutoTemplateRequest autoTemplateRequest){
        if (autoTemplateRequest == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_com_null);
        }
        if (autoTemplateRequest.getCaseId() == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "caseId");
        }
        if (autoTemplateRequest.getCaseType() == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "caseType");
        }
        if (!StringUtils.isNoneBlank(autoTemplateRequest.getTempName())) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "tempName");
        }
        if (autoTemplateRequest.getTempId() == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "tempId");
        }
        //判断该名字是否已有自动化用例模板存在
        NaAutoTemplate autoTemplate=autoTemplateDao.findByTempName(autoTemplateRequest.getTempName());
        if(autoTemplate != null&&autoTemplate.getTempId()!=autoTemplateRequest.getTempId()){
            BusinessException.throwBusinessException("autoTemplate Name already  existing! please change......");
        }
        autoTemplate=autoTemplateDao.findOne(autoTemplateRequest.getTempId());
        autoTemplate.setFunId(autoTemplateRequest.getFunId());
        autoTemplate.setBusiId(autoTemplateRequest.getBusiId());
        autoTemplate.setCaseId(autoTemplateRequest.getCaseId());
        autoTemplate.setCaseType(autoTemplateRequest.getCaseType());
        autoTemplate.setImportant(autoTemplateRequest.getImportant());
        autoTemplate.setScId(autoTemplateRequest.getScId());
        autoTemplate.setSysId(autoTemplateRequest.getSysId());
        autoTemplate.setSysSubId(autoTemplateRequest.getSysSubId());
        autoTemplate.setUpdateTime(Calendar.getInstance().getTime());
        autoTemplate.setTestType(autoTemplateRequest.getTestType());
//        autoTemplate.setUpdateId();
        return autoTemplateDao.save(autoTemplate);
    }

    /**
     * 根据主键获取单个自动化用例模板信息，并将属性ID转为具体值
     * @param autoTemplateRequest
     * @return
     */
    public AutoTemplateRequest findById(AutoTemplateRequest autoTemplateRequest){
        if (autoTemplateRequest == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_com_null);
        }
        if (autoTemplateRequest.getTempId() == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "tempId");
        }
        NaAutoTemplate autoTemplate= this.autoTemplateDao.findOne(autoTemplateRequest.getTempId());
        NaCaseTemplate caseTemplate=this.caseTemplateDao.findOne(autoTemplate.getCaseId());
        if(caseTemplate !=null){
            autoTemplateRequest.setOperateDesc(caseTemplate.getOperateDesc());
        }
        autoTemplateRequest.setSysSubId(autoTemplate.getSysSubId());
        autoTemplateRequest.setSysSubName("");
        autoTemplateRequest.setTestType(autoTemplate.getTestType());
        autoTemplateRequest.setTestTypeDesc("");
        autoTemplateRequest.setSysId(autoTemplate.getSysId());
        autoTemplateRequest.setSysName("");
        autoTemplateRequest.setScId(autoTemplate.getScId());
        autoTemplateRequest.setScName("");
        autoTemplateRequest.setBusiId(autoTemplate.getBusiId());
        autoTemplateRequest.setBusiName("");
        autoTemplateRequest.setCaseId(autoTemplate.getCaseId());
        autoTemplateRequest.setCaseName("");
        autoTemplateRequest.setFunId(autoTemplate.getFunId());
        autoTemplateRequest.setFunName("");
        autoTemplateRequest.setCaseType(autoTemplate.getCaseType());
        autoTemplateRequest.setCaseTypeDesc("");
        autoTemplateRequest.setImportant(autoTemplate.getImportant());
        autoTemplateRequest.setImportantDesc("");
        autoTemplateRequest.setTempName(autoTemplate.getTempName());

        return autoTemplateRequest;
    }

    /**
     * 查询所有自动化用例模板信息
     * @return
     */
    public List<NaAutoTemplate> list(){
        return autoTemplateDao.findAll();
    }

    /**
     * 分页根据条件查询自动化用例模板(属性都是ID，不转换为描述)
     * @param condition
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public Object listTemplate(NaAutoTemplate condition, int pageNumber, int pageSize) {

        List<Condition> cons = new ArrayList<Condition>();

        if(condition != null){
            if(StringUtils.isNotBlank(condition.getTempName())){
                cons.add(new Condition("tempName", "%".concat(condition.getTempName()).concat("%"), Condition.Type.LIKE));
            }

            if(condition.getSysId() != null){
                cons.add(new Condition("sysId", condition.getSysId(), Condition.Type.EQ));
            }

            if(condition.getSysSubId() != null){
                cons.add(new Condition("sysSubId", condition.getSysSubId(), Condition.Type.EQ));
            }

            if(condition.getFunId() != null){
                cons.add(new Condition("funId", condition.getFunId(), Condition.Type.EQ));
            }

            if(condition.getBusiId() != null){
                cons.add(new Condition("busiId", condition.getBusiId(), Condition.Type.EQ));
            }

            if(condition.getScId() != null){
                cons.add(new Condition("scId", condition.getScId(), Condition.Type.EQ));
            }

            if(condition.getImportant() != null){
                cons.add(new Condition("important", condition.getImportant(), Condition.Type.EQ));
            }
        }


        if(pageNumber < 0){
            pageNumber = 0;
        }

        if(pageSize <= 0){
            pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
        }

        Pageable pageable = new PageRequest(pageNumber, pageSize);

        return autoTemplateDao.search(cons, pageable);
    }

    /**
     * 根据原生SQL分页查询自动化用例模板信息(包括属性ID的描述信息)
     * @param condition
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public Object listbyNativeSQL(NaAutoTemplate condition, int pageNumber, int pageSize){
        StringBuilder nativeSql=new StringBuilder("select a.temp_Id,a.case_Id,e.case_Name,a.case_Type,a.test_Type,\n" +
                "a.temp_Name,a.sys_Id,b.sys_Name sysName,a.sys_sub_id ,c.sys_name sysSubName,\n" +
                "a.busi_id,a.sc_id,a.fun_id,d.sys_name funName,a.important,a.creator_id,\n" +
                "f.name,a.update_time\n" +
                " from Na_Auto_Template a \n" +
                "left join Aiga_System_Folder b on a.sys_Id=b.sys_Id\n" +
                "left join Aiga_Sub_Sys_Folder c on a.sys_Sub_Id=c.subsys_Id\n" +
                "left join Aiga_Fun_Folder d on a.fun_Id=d.fun_Id\n" +
                "left join Na_Case_Template e on a.case_Id=e.case_Id\n" +
                "left join Aiga_Staff f on a.creator_Id=f.staff_Id\n" +
                "where 1=1 \n");
        List<String> keyList=new ArrayList<String>();
        keyList.add("tempId");
        keyList.add("caseId");
        keyList.add("caseName");
        keyList.add("caseType");
        keyList.add("testType");
        keyList.add("tempName");
        keyList.add("sysId");
        keyList.add("sysName");
        keyList.add("sysSubId");
        keyList.add("sysSubName");
        keyList.add("busiId");
        keyList.add("scId");
        keyList.add("funId");
        keyList.add("funName");
        keyList.add("important");
        keyList.add("creatorId");
        keyList.add("StaffName");
        keyList.add("updateTime");
        if(condition != null){
            if(StringUtils.isNotBlank(condition.getTempName())){
                nativeSql.append(" and a.temp_name like '%").append(condition.getTempName()).append("%'");
            }

            if(condition.getSysId() != null){
                nativeSql.append(" and a.sys_id=").append(condition.getSysId());
            }

            if(condition.getSysSubId() != null){
                nativeSql.append(" and a.sys_sub_id=").append(condition.getSysSubId());
            }

            if(condition.getFunId() != null){
                nativeSql.append(" and a.fun_id=").append(condition.getFunId());
            }

            if(condition.getBusiId() != null){
                nativeSql.append(" and a.busi_id=").append(condition.getBusiId());
            }

            if(condition.getScId() != null){
                nativeSql.append(" and a.sc_id=").append(condition.getScId());
            }

            if(condition.getImportant() != null){
                nativeSql.append(" and a.important=").append(condition.getImportant());
            }
        }
        if(pageNumber < 0){
            pageNumber = 0;
        }

        if(pageSize <= 0){
            pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
        }
        Pageable pageable = new PageRequest(pageNumber, pageSize);
        return autoTemplateDao.search(nativeSql.toString(),pageable);
    }

    /**
     * 先复制数据到删除记录表，再执行删除操作
     * @param request
     */
    public void delete(AutoTemplateRequest request){
        if (request == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_com_null);
        }
        if (request.getTempId() == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "tempId");
        }
        NaAutoTemplate autoTemplate=autoTemplateDao.findOne(request.getTempId());
        if(autoTemplate==null){
            BusinessException.throwBusinessException("can not found autoTemplate !please make sure the tempId is valid......");
        }
        //先复制数据到删除记录表，再执行删除操作
        autoTemplateDao.copyDataToDel(autoTemplate.getTempId());
        autoTemplateDao.delete(autoTemplate.getTempId());
        //将自动化用例模板数据复制到删除记录表中

    }
    
}
