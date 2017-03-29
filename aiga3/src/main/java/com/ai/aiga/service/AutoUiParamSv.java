package com.ai.aiga.service;

import com.ai.aiga.dao.NaAutoUiParamDao;
import com.ai.aiga.dao.NaUiParamDao;
import com.ai.aiga.domain.NaAutoUiComp;
import com.ai.aiga.domain.NaAutoUiParam;
import com.ai.aiga.domain.NaUiParam;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.util.mapper.BeanMapper;
import com.ai.aiga.view.json.AutoUiParamRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 自动化用例组件参数
 *+
 * @author defaultekey
 * @date 2017/3/9
 */
@Service
@Transactional
public class AutoUiParamSv {

    @Autowired
    private NaAutoUiParamDao autoUiParamDao;

    @Autowired
    private NaUiParamDao uiParamDao;

    /**
     * 根据传递的请求参数信息批量保存参数信息
     * @param paramRequestList 参数集合列表
     * @param comp 组件对象
     */
    public void saveParamList(List<AutoUiParamRequest> paramRequestList, NaAutoUiComp comp){
        if (paramRequestList == null || paramRequestList.size()==0) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_com_null);
        }
        if (comp == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_com_null);
        }
        //批量保存
        List<Object> paramList=new ArrayList<Object>();
        for (AutoUiParamRequest paramRequest:paramRequestList){
            NaAutoUiParam param= BeanMapper.map(paramRequest,NaAutoUiParam.class);
            param.setCompOrder(comp.getCompOrder());
            param.setUpdateTime(Calendar.getInstance().getTime());
            param.setAutoId(comp.getAutoId());
            param.setCompId(comp.getCompId());
//            param.setCreatorId();
            paramList.add(param);
        }
        autoUiParamDao.saveList(paramList);
    }

    /**
     * 保存操作(唯一入口)
     * @param param
     */
    public NaAutoUiParam save(NaAutoUiParam param){
        if (param == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_com_null);
        }
        if(param.getCompId()==null){
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "compId");
        }
        if (param.getAutoId() == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "autoId");
        }
        param.setUpdateTime(Calendar.getInstance().getTime());
//        param.setCreatorId();
        return autoUiParamDao.save(param);
    }

    /**
     * 通过请求参数保存参数(单个)
     * @param request
     * @return
     */
    public NaAutoUiParam save(AutoUiParamRequest request){
        if (request == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_com_null);
        }
        NaAutoUiParam param=BeanMapper.map(request,NaAutoUiParam.class);
        return this.save(param);
    }

    /**
     * 根据自动化用例复制参数
     * @param autoUiComp
     */
    public void copyParamList(NaAutoUiComp autoUiComp){
        if (autoUiComp == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_com_null);
        }
        if (autoUiComp.getCompId() == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "compId");
        }
        if (autoUiComp.getAutoId()==null){
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "autoId");
        }
        List<NaUiParam>paramList=uiParamDao.findByCompId(autoUiComp.getCompId());
        //复制参数
        List<Object> list = new ArrayList<Object>();
        if(paramList!=null&&paramList.size()>0){
            for (NaUiParam uiParam:paramList){
                NaAutoUiParam autoUiParam=BeanMapper.map(uiParam,NaAutoUiParam.class);
                //将主键置为空
                autoUiParam.setParamId(null);
                autoUiParam.setCompOrder(autoUiComp.getCompOrder());
                autoUiParam.setCompId(autoUiComp.getCompId());
                autoUiParam.setAutoId(autoUiComp.getAutoId());
                autoUiParam.setUpdateTime(Calendar.getInstance().getTime());
                autoUiParam.setParamLevel(1L);//参数等级，默认为1
//                autoUiParam.setCreatorId();
                //保存参数
                list.add(autoUiParam);
            }
        }
        autoUiParamDao.saveList(list);
    }

    /**
     * 根据自动化用例ID删除（唯一入口）
     * @param autoId
     */
    public void deleteByAutoId(Long autoId){
        if (autoId == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "autoId");
        }
        autoUiParamDao.deleteByAutoId(autoId);
    }

    /**
     * 根据自动化用例ID和组件ID删除（唯一入口）
     * @param autoId
     * @param compId
     */
    public void deleteByAutoComp(Long autoId,Long compId){
        if (autoId == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "autoId");
        }
        if (compId == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "compId");
        }
        autoUiParamDao.deleteByAutoComp(autoId,compId);
    }
    
    /**
     * 根据自动化用例ID和组件ID查询（唯一入口）
     * @param autoId
     * @param compId
     * @return
     */
    public List<NaAutoUiParam> findByAutoComp(Long autoId,Long compId){
        if (autoId == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "autoId");
        }
        if (compId == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "compId");
        }
        return autoUiParamDao.findByAutoIdAndCompId(autoId,compId);
    }

    public List<NaAutoUiParam> findByAutoComp(AutoUiParamRequest request){
        if (request == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_com_null);
        }
        return this.findByAutoComp(request.getAutoId(),request.getCompId());
    }

    /**
     * 根据自动化用例ID查询（唯一入口）
     * @param autoId
     * @return
     */
    public List<NaAutoUiParam> findByAutoId(Long autoId){
        if (autoId == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "autoId");
        }
        return autoUiParamDao.findByAutoId(autoId);
    }

    /**
     * 根据自动化用例ID和组件ID查询返回AutoUiParamRequest
     * @param autoId
     * @param compId
     * @return
     */
    public  List<AutoUiParamRequest> findByAutoCompRequest(Long autoId,Long compId){
        List<NaAutoUiParam> paramList=this.findByAutoComp(autoId,compId);
        List<AutoUiParamRequest> requestList=new ArrayList<AutoUiParamRequest>();
        if (paramList!=null && paramList.size()>0){
            for (NaAutoUiParam param:paramList){
                requestList.add(BeanMapper.map(param,AutoUiParamRequest.class));
            }
        }
        return requestList;
    }

    /**
     * 根据主键删除（唯一入口）
     * @param paramId
     */
    public void delete(Long paramId){
        if (paramId == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "paramId");
        }
        this.autoUiParamDao.delete(paramId);
    }

    /**
     * 通过请求参数删除
     * @param request
     */
    public void delete(AutoUiParamRequest request){
        if (request == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_com_null);
        }
        this.delete(request.getParamId());
    }
    
}
