package com.ai.aiga.service;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.dao.NaAutoCaseDao;
import com.ai.aiga.domain.*;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.util.mapper.BeanMapper;
import com.ai.aiga.view.json.AutoCaseRequest;
import com.ai.aiga.view.json.AutoUiCompRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 自动化用例
 *
 * @author defaultekey
 * @date 2017/3/9
 */
@Service
@Transactional
public class AutoCaseSv {

    @Autowired
    private NaAutoCaseDao autoCaseDao;

    @Autowired
    private AutoTemplateSv autoTemplateSv;

    @Autowired
    private AutoUiCompSv autoUiCompSv;

    @Autowired
    private  AutoUiParamSv autoUiParamSv;

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 保存操作(唯一入口)
     * @param autoCase
     * @return
     */
    public NaAutoCase save(NaAutoCase autoCase){
        if (autoCase == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_com_null);
        }
        if (StringUtils.isBlank(autoCase.getAutoName())) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "autoName");
        }
        if (autoCase.getEnvironmentType() == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "EnvironmentType");
        }
        if (autoCase.getCaseType() == 0) {
            autoCase.setCaseType((byte) 1);//默认ui类
        }
        if (autoCase.getImportant()==null){
            autoCase.setImportant((short) 4);//默认为4级
        }
        if (autoCase.getStatus() == null) {
            autoCase.setStatus(1L);//默认可用
        }
        if (autoCase.getHasAuto() == null) {
            autoCase.setHasAuto(0L);//默认未实现自动化
        }
        if (autoCase.getParamLevel() == null) {
            autoCase.setParamLevel(1L);//默认为1级
        }
        //校验是否存在名称一样的用例
        if (autoCase.getAutoId() != null ? (this.isExisting(autoCase.getAutoName(),autoCase.getAutoId()))  :  (autoCaseDao.findByAutoName(autoCase.getAutoName())!=null)){
            BusinessException.throwBusinessException("autoName already existing! please change......");
        }
//       if (autoCase.getAutoId()==null)autoCase.setCreatorId();
//        autoCase.setUpdateId();
        autoCase.setUpdateTime(Calendar.getInstance().getTime());
        return autoCaseDao.save(autoCase);
    }

    /**
     * 根据自动化用例模板ID复制数据生成自动化用例基础信息(只复制用例基础属性，不复制组件参数)
     * @param tempId
     * @return
     */
    private NaAutoCase copyCaseByTempId(Long tempId){
        if (tempId == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "autoId");
        }
        NaAutoTemplate autoTemplate=autoTemplateSv.findById(tempId);
        if (autoTemplate == null) {
            BusinessException.throwBusinessException("can not found the autoTemplate! please make sure the tempId:"+tempId);
        }
        NaAutoCase autoCase= BeanMapper.map(autoTemplate,NaAutoCase.class);
        autoCase.setAutoName(autoTemplate.getTempName());//根据模板名称生成填充名字，需由后续调用方法者覆盖
//        autoCase.setEnvironmentType();//根据模板ID生成不填充环境，由后续调用方法者实现
        autoCase.setHasAuto(0L);//是否实现自动化：默认为0，未实现
        autoCase.setParamLevel(1L);//参数等级：默认为1，
        autoCase.setStatus(1L);//用例状态：默认为1，可用
        //保存操作
        return this.save(autoCase);
    }

    /**
     * 根据自动化用例模板ID复制用例数据和组件参数信息
     * @param tempId
     */
    private NaAutoCase copyCaseCompByTempId(Long tempId){
        if (tempId == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "autoId");
        }
        //先复制用例
        NaAutoCase autoCase=this.copyCaseByTempId(tempId);
        //复制组件
        autoUiCompSv.copyCompList(autoCase);
        return autoCase;
    }

    /**
     *根据自动化用例模板ID复制数据生成自动化用例，根据类型判断是只生成用例基础信息还是包含组件参数
     * @param tempId 自动化用例模板ID
     * @param autoName 自动化用例名称
     * @param environmentType 环境类型
     * @param type  0 : 只复制用例基础信息 1:复制用例以及组件参数信息
     * @return
     */
    private NaAutoCase createDataByTemp(Long tempId,String autoName,Long environmentType,int type){
        if (tempId == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "tempId");
        }
        if (StringUtils.isBlank(autoName)) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "autoName");
        }
        if (environmentType == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "environmentType");
        }
        //判断是否存在名称一样的用例
        NaAutoCase autoCase=new NaAutoCase();
        //根据类型判断 0 : 只复制用例基础信息 1:复制用例以及组件参数信息
        if(type==0) {
            autoCase = this.copyCaseByTempId(tempId);
        }else
        if(type==1){
            autoCase=this.copyCaseCompByTempId(tempId);
        }else{
            BusinessException.throwBusinessException("type is do not match! you are illegal access......");
        }
        //填充用例名称和环境属性
        autoCase.setAutoName(autoName);
        autoCase.setEnvironmentType(environmentType);
        //保存操作
        return this.save(autoCase);
    }

    /**
     * 根据自动化用例模板ID复制数据生成自动化用例基础信息并填充属性(不包括组件参数)
     * @param tempId 自动化用例模板ID
     * @param autoName 自动化用例名称
     * @param environmentType 环境类型
     * @return
     */
    private NaAutoCase createAutoCaseByTemp(Long tempId,String autoName,Long environmentType){
        return this.createDataByTemp(tempId,autoName,environmentType,0);
    }

    /**
     * 根据自动化用例模板ID复制数据生成自动化用例基础信息并填充属性（包括组件参数）
     * @param tempId 自动化用例模板ID
     * @param autoName  自动化用例名称
     * @param environmentType 环境类型
     * @return
     */
    private NaAutoCase createAutoCaseCompByTemp(Long tempId,String autoName,Long environmentType){
        return  this.createDataByTemp(tempId,autoName,environmentType,1);
    }

    /**
     * 根据传递的请求参数信息保存用例、组件、参数(带有autoId则修改，不带则新增)
     * @param autoCaseRequest
     * @return
     */
    public NaAutoCase saveAutoCaseCompParam(AutoCaseRequest autoCaseRequest){
        if (autoCaseRequest == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_com_null);
        }
        NaAutoCase autoCase;
        //判断是否带有autoId(是：修改操作   否：新增操作)
        if (autoCaseRequest.getAutoId()==null){
            Long tempId=autoCaseRequest.getTempId();//自动化用例模板主键
            String autoName=autoCaseRequest.getAutoName();//自动化用例名称
            Long environmentType=autoCaseRequest.getEnvironmentType();//环境类型
            //生成自动化用例
            autoCase=this.createAutoCaseByTemp(tempId,autoName,environmentType);
        }else{
            autoCase=autoCaseDao.findOne(autoCaseRequest.getAutoId());
            if (autoCase == null) {
                BusinessException.throwBusinessException("can not found autoCase !please make sure the autoId is valid......");
            }
//            autoCase.setCreatorId();
            autoCase.setEnvironmentType(autoCaseRequest.getEnvironmentType());
            autoCase.setUpdateTime(Calendar.getInstance().getTime());
            autoCase.setAutoName(autoCaseRequest.getAutoName());
            autoCase=this.save(autoCase);
            //删除现有组件和参数关系
            autoUiCompSv.deleteByAutoId(autoCase.getAutoId());
            autoUiParamSv.deleteByAutoId(autoCase.getAutoId());
        }

        List<AutoUiCompRequest> compRequestList=autoCaseRequest.getCompList();
        //如果带有组件信息则保存组件
        if(compRequestList!=null&&compRequestList.size()>0){
            autoUiCompSv.saveCompList(compRequestList,autoCase);
        }
        return autoCase;
    }

    /**
     * 先复制数据到删除备份表，在删除原数据（唯一入口）
     * @param autoId
     */
    public void delete(Long autoId){
        if (autoId == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "autoId");
        }
        autoCaseDao.copyDataToDel(autoId);
        autoCaseDao.delete(autoId);
    }

    /**
     * 通过请求参数删除
     * @param autoCaseRequest
     */
    public void delete(AutoCaseRequest autoCaseRequest){
        if (autoCaseRequest == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_com_null);
        }
        this.delete(autoCaseRequest.getAutoId());
    }

    /**
     * 根据主键ID查询自动化用例信息（唯一入口）
     * @param autoId
     * @return
     */
    public NaAutoCase findById(Long autoId){
        if (autoId == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "autoId");
        }
        return autoCaseDao.findOne(autoId);
    }

    /**
     * 根据主键ID查询自动化用例信息（包括用例信息、组件、参数）
     * @param autoCaseRequest
     * @return
     */
    public AutoCaseRequest findById(AutoCaseRequest autoCaseRequest){
        if (autoCaseRequest == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_com_null);
        }
        if (autoCaseRequest.getAutoId() == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "autoId");
        }
        AutoCaseRequest response=BeanMapper.map(this.findById(autoCaseRequest.getAutoId()),AutoCaseRequest.class);
        //填入组件
        response.setCompList(this.autoUiCompSv.findByAutoIdRequest(autoCaseRequest.getAutoId()));
        return response;
    }

    /**
     * 根据原生SQL分页查询自动化用例信息(包括属性ID的描述信息)
     * @param condition
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public Object listbyNativeSQL(AutoCaseRequest condition,int pageNumber, int pageSize){
        StringBuilder nativeSql=new StringBuilder("select a.auto_id,a.test_id,a.temp_id,a.test_type,a.case_type,a.auto_name,\n" +
                "a.sys_id,a.sys_sub_id,a.busi_id,a.fun_id,a.sc_id,a.important,a.environment_type,\n" +
                "a.status,a.has_auto,a.auto_desc,a.param_level,a.creator_id,a.update_id,a.update_time,\n" +
                "b.temp_name,c.test_name,d.sys_name,e.sys_name as sysSubName,f.sys_name as funName,g.name\n" +
                " from na_auto_case a\n" +
                " left join Na_Auto_Template b on a.temp_id=b.temp_id\n" +
                " left join na_test_case c on a.test_id=c.test_id\n" +
                " left join Aiga_System_Folder d on a.sys_Id=d.sys_Id\n" +
                " left join Aiga_Sub_Sys_Folder e on a.sys_Sub_Id=e.subsys_Id\n" +
                " left join Aiga_Fun_Folder f on a.fun_Id=f.fun_Id\n" +
                " left join Aiga_Staff g on a.update_id=g.staff_Id where 1=1 ");
        List<String> keyList=new ArrayList<String>();
        keyList.add("autoId");
        keyList.add("testId");
        keyList.add("tempId");
        keyList.add("testType");
        keyList.add("caseType");
        keyList.add("autoName");
        keyList.add("sysId");
        keyList.add("sysSubId");
        keyList.add("busiId");
        keyList.add("funId");
        keyList.add("scId");
        keyList.add("important");
        keyList.add("environmentType");
        keyList.add("status");
        keyList.add("hasAuto");
        keyList.add("autoDesc");
        keyList.add("paramLevel");
        keyList.add("creatorId");
        keyList.add("updateId");
        keyList.add("updateTime");
        keyList.add("tempName");
        keyList.add("testName");
        keyList.add("sysName");
        keyList.add("sysSubName");
        keyList.add("funName");
        keyList.add("staffName");
        if(condition != null){
            if(StringUtils.isNotBlank(condition.getAutoName())){
                nativeSql.append(" and a.auto_name like '%").append(condition.getAutoName()).append("%'");
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
            if (condition.getStatus() != null) {
                nativeSql.append(" and a.status=").append(condition.getStatus());
            }
        }
        if(pageNumber < 0){
            pageNumber = 0;
        }

        if(pageSize <= 0){
            pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
        }
        Pageable pageable = new PageRequest(pageNumber, pageSize);
        return autoCaseDao.searchByNativeSQL(nativeSql.toString(),pageable,keyList);
    }

    /**
     * 判断是否存在重复名称的自动化用例(存在返回true，不存在false)
     * @param autoName
     * @param autoId
     * @return
     */
    public boolean isExisting(String autoName,Long autoId){
        NaAutoCase autoCase=autoCaseDao.findByAutoName(autoName);
        return autoCase!=null ? !autoCase.getAutoId().equals(autoId) : false;
    }



}
