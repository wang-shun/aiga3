package com.ai.aiga.service;

import com.ai.aiga.dao.NaAutoUiCompDao;
import com.ai.aiga.dao.NaUiComponentDao;
import com.ai.aiga.domain.NaAutoCase;
import com.ai.aiga.domain.NaAutoTemplateComp;
import com.ai.aiga.domain.NaAutoUiComp;
import com.ai.aiga.domain.NaUiComponent;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.util.mapper.BeanMapper;
import com.ai.aiga.view.json.AutoUiCompRequest;
import com.ai.aiga.view.json.AutoUiParamRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 自动化用例组件
 *
 * @author defaultekey
 * @date 2017/3/9
 */
@Service
@Transactional
public class AutoUiCompSv {

    @Autowired
    private NaAutoUiCompDao autoUiCompDao;

    @Autowired
    private AutoUiParamSv autoUiParamSv;

    @Autowired
    private NaUiComponentDao componentDao;

    @Autowired
    private AutoTemplateCompSv autoTemplateCompSv;

    /**
     * 根据传递的请求参数信息批量保存组件信息、参数信息
     * @param compRequestList  组件对象集合
     * @param autoCase 自动化用例
     */
    public void saveCompList(List<AutoUiCompRequest> compRequestList , NaAutoCase autoCase){
        if (compRequestList == null ||compRequestList.size()==0) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_com_null);
        }
        if (autoCase == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_com_null);
        }
        if (autoCase.getAutoId() == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "autoId");
        }
        //批量保存组件信息
        for (AutoUiCompRequest compRequest:compRequestList){
            compRequest.setAutoId(autoCase.getAutoId());
            this.save(compRequest);
        }
    }
    /**
     * 保存操作(唯一入口)
     * @param comp
     * @return
     */
    public NaAutoUiComp save(NaAutoUiComp comp){
        if (comp == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_com_null);
        }
        if (comp.getAutoId() == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "autoId");
        }
        if (comp.getCompId() == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "compId");
        }
//        comp.setCreatorId();
        comp.setUpdateTime(Calendar.getInstance().getTime());
        return autoUiCompDao.save(comp);
    }

    /**
     * AutoUiCompRequest保存操作
     * @param request
     * @return
     */
    public NaAutoUiComp save(AutoUiCompRequest request){
        if (request == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_com_null);
        }
        NaAutoUiComp comp=BeanMapper.map(request,NaAutoUiComp.class);
        this.save(comp);
        List<AutoUiParamRequest> paramRequestList=request.getParamList();
        //如果带有参数，则保存参数信息
        if (paramRequestList != null&&paramRequestList.size()>0) {
            autoUiParamSv.saveParamList(paramRequestList,comp);
        }
        return comp;
    }

    /**
     * 根据自动化用例复制组件信息
     * @param autoCase
     */
    public void copyCompList(NaAutoCase autoCase){
        if (autoCase == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_com_null);
        }
        if(autoCase.getTempId()==null){
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "tempId");
        }
        if(autoCase.getAutoId()==null){
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "autoId");
        }
        List<NaAutoTemplateComp> compList=autoTemplateCompSv.findByTempId(autoCase.getTempId());
        //复制组件
        if(compList!=null&&compList.size()>0) {
            for (NaAutoTemplateComp autoTemplateComp : compList) {
                NaAutoUiComp autoUiComp = new NaAutoUiComp();
                autoUiComp.setAutoId(autoCase.getAutoId());
                autoUiComp.setCompId(autoTemplateComp.getCompId());
                autoUiComp.setCompOrder(autoTemplateComp.getCompOrder());
                //保存组件
                autoUiComp=this.save(autoUiComp);
                //复制参数
                autoUiParamSv.copyParamList(autoUiComp);
            }
        }
    }

    /**
     * 根据自动化用例ID删除组件（唯一入口）
     * @param autoId
     */
    public void deleteByAutoId(Long autoId){
        if (autoId == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "autoId");
        }
        autoUiCompDao.deleteByAutoId(autoId);
    }

    /**
     * 根据自动化用例ID查询组件（唯一入口）
     * @param autoId
     * @return
     */
    public List<NaAutoUiComp> findByAutoId(Long autoId){
        if (autoId == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "autoId");
        }
        return autoUiCompDao.findByAutoIdOrderByCompOrder(autoId);
    }

    /**
     * 根据自动化用例ID查询组件参数(包含组件参数详细信息)
     * @param autoId
     * @return
     */
    public List<AutoUiCompRequest> findByAutoIdRequest(Long autoId){
        List<AutoUiCompRequest> compRequestList=new ArrayList<AutoUiCompRequest>();
        List<NaAutoUiComp> compList=this.findByAutoId(autoId);
        NaUiComponent component;
        if (compList != null && compList.size()>0) {
            for (NaAutoUiComp comp:compList){
                AutoUiCompRequest compRequest=BeanMapper.map(comp,AutoUiCompRequest.class);
                //填充组件详细描述
                component=componentDao.findOne(compRequest.getCompId());
                compRequest.setCompDesc(component.getCompDesc());
                compRequest.setCompName(component.getCompName());
                compRequest.setCompScript(component.getCompScript());
                //查询组件下参数
                compRequest.setParamList(this.autoUiParamSv.findByAutoCompRequest(autoId,compRequest.getCompId(),compRequest.getCompOrder()));
                compRequestList.add(compRequest);
            }
        }
        return compRequestList;
    }

    /**
     * 根据请求参数的自动化用例ID查询组件参数(包含组件参数详细信息)
     * @param request
     * @return
     */
    public List<AutoUiCompRequest> findByAutoIdRequest(AutoUiCompRequest request){
        if (request == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_com_null);
        }
        if (request.getAutoId() == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "autoId");
        }
        return this.findByAutoIdRequest(request.getAutoId());
    }

    /**
     * 根据主键删除（唯一入口）
     * @param relaId
     */
    public void delete(Long relaId){
        if (relaId == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_com_null);
        }
        NaAutoUiComp comp=this.autoUiCompDao.findOne(relaId);
        //删除参数
        this.autoUiParamSv.deleteByAutoComp(comp.getAutoId(),comp.getCompId());
        //删除组件
        this.autoUiCompDao.delete(relaId);
    }

    /**
     * 通过请求参数删除
     * @param request
     */
    public void delete(AutoUiCompRequest request){
        if (request == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_com_null);
        }
        this.delete(request.getRelaId());
    }


}
