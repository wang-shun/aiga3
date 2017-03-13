package com.ai.aiga.service;

import com.ai.aiga.dao.NaAutoCaseDao;
import com.ai.aiga.domain.*;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.util.mapper.BeanMapper;
import com.ai.aiga.view.json.AutoCaseRequest;
import com.ai.aiga.view.json.AutoUiCompRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
