package com.ai.aiga.service;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.dao.NaAutoTemplateDao;
import com.ai.aiga.dao.NaCaseTemplateDao;
import com.ai.aiga.dao.jpa.Condition;
import com.ai.aiga.domain.NaAutoTemplate;
import com.ai.aiga.domain.NaCaseTemplate;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.util.mapper.BeanMapper;
import com.ai.aiga.view.json.AutoTemplateRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 自动化用例模板
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

    @Autowired
    private AutoTemplateCompSv autoTemplateCompSv;

    /**
     * 保存操作（唯一入口）
     * @param autoTemplate
     * @return
     */
    public NaAutoTemplate save(NaAutoTemplate autoTemplate){
        if (autoTemplate == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_com_null);
        }
        if (autoTemplate.getCaseId() == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "caseId");
        }
        if (StringUtils.isBlank(autoTemplate.getTempName())) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "tempName");
        }
        String tempName=autoTemplate.getTempName();//模板名称
        Long tempId=autoTemplate.getTempId();//主键
        //校验是否存在名称一样的自动化用例模板
        if (tempId != null ? (isExisting(tempName,tempId))  :  (autoTemplateDao.findByTempName(tempName)!=null)) {
            BusinessException.throwBusinessException("autoTemplate Name already  existing! please change......");
        }
        if (autoTemplate.getCaseType() == 0) {
            autoTemplate.setCaseType((byte)1);//默认UI
        }
        if (autoTemplate.getImportant() == null) {
            autoTemplate.setImportant((short) 4);//默认4级
        }
//        if(tempId==null)autoTemplate.setCreatorId();
//        autoTemplate.setUpdateId();
        autoTemplate.setUpdateTime(Calendar.getInstance().getTime());
        return autoTemplateDao.save(autoTemplate);
    }
    
    /**
     * 通过请求参数保存自动化用例模板信息
     * @param autoTemplateRequest
     * @return
     */
    public NaAutoTemplate saveOrUpdate(AutoTemplateRequest autoTemplateRequest){
        if (autoTemplateRequest == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_com_null);
        }
        return this.save(BeanMapper.map(autoTemplateRequest,NaAutoTemplate.class));
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
        //查询自动化用例模板
        NaAutoTemplate autoTemplate= this.findById(autoTemplateRequest.getTempId());
        autoTemplateRequest= BeanMapper.map(autoTemplate,AutoTemplateRequest.class);
        //查询用例模板名称
        NaCaseTemplate caseTemplate=this.caseTemplateDao.findOne(autoTemplate.getCaseId());
        if(caseTemplate !=null){
            autoTemplateRequest.setOperateDesc(caseTemplate.getOperateDesc());
        }
        //查询自动化用例模板关联的组件
        return autoTemplateRequest;
    }

    /**
     * 根据主键查询自动化用例模板信息（唯一入口）
     * @param tempId
     * @return
     */
    public NaAutoTemplate findById(Long tempId){
        if (tempId == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "tempId");
        }
        return autoTemplateDao.findOne(tempId);
    }

    /**
     * 查询所有自动化用例模板信息（唯一入口）
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
    public Object listTemplate(AutoTemplateRequest condition, int pageNumber, int pageSize) {

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
    public Object listbyNativeSQL(AutoTemplateRequest condition, int pageNumber, int pageSize){
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
     * 通过请求参数先复制数据到删除记录表，再执行删除操作
     * @param request
     */
    public void delete(AutoTemplateRequest request){
        if (request == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_com_null);
        }
        this.delete(request.getTempId());
    }

    /**
     * 根据主键先复制数据到删除记录表，再执行删除操作（唯一入口）
     * @param tempId
     */
    public void delete(Long tempId){
        if (tempId == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "tempId");
        }
        NaAutoTemplate autoTemplate=this.findById(tempId);
        if(autoTemplate==null){
            BusinessException.throwBusinessException("can not found autoTemplate !please make sure the tempId is valid......");
        }
        //先复制数据到删除记录表，再执行删除操作
        autoTemplateDao.copyDataToDel(autoTemplate.getTempId());
        autoTemplateDao.delete(autoTemplate.getTempId());
    }

    /**
     * 根据用例模板ID和自动化用例模板名称复制数据
     * @param caseId
     * @param tempName
     * @return
     */
    private NaAutoTemplate copyCaseToAuto(Long caseId,String tempName){
        //校验用例模板数据是否存在
        NaCaseTemplate caseTemplate=caseTemplateDao.findOne(caseId);
        if (caseTemplate == null) {
            BusinessException.throwBusinessException(" can not found caseTemplate! please make sure the caseId :"+caseId);
        }
        NaAutoTemplate autoTemplate=BeanMapper.map(caseTemplate,NaAutoTemplate.class);
        autoTemplate.setTempName(tempName);
        return this.save(autoTemplate);
    }

    /**
     * 保存自动化用例模板与组件关系
     * @param templateRequest
     * @return
     */
    public NaAutoTemplate saveList(AutoTemplateRequest templateRequest){
        if (templateRequest == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_com_null);
        }
        if (StringUtils.isBlank(templateRequest.getTempName())) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "tempName");
        }
        if (templateRequest.getCaseId() == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "caseId");
        }
        NaAutoTemplate autoTemplate;
        if(templateRequest.getTempId()==null) {
            //生成自动化用例模板
            autoTemplate = this.copyCaseToAuto(templateRequest.getCaseId(), templateRequest.getTempName());
        }else{
            autoTemplate=this.findById(templateRequest.getTempId());
            autoTemplate.setTempName(templateRequest.getTempName());
            //更新自动化用例模板
            this.save(autoTemplate);
            //删除组件关系
            autoTemplateCompSv.deleteByTempId(templateRequest.getTempId());
        }
        //保存组件
        if(templateRequest.getCompRequestList()!=null) {
            autoTemplateCompSv.saveList(templateRequest.getCompRequestList(), autoTemplate.getTempId());
        }
        return autoTemplate;
    }


    /**
     * 判断是否存在重复名称的自动化用例模板(存在返回true，不存在false)
     * @param tempName
     * @param tempId
     * @return
     */
    public boolean isExisting(String tempName,Long tempId){
        NaAutoTemplate template=autoTemplateDao.findByTempName(tempName);
        return template!=null ? !template.getTempId().equals(tempId) : false;
    }
}
