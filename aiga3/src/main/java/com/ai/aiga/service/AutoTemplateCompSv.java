package com.ai.aiga.service;

import com.ai.aiga.dao.NaAutoTemplateCompDao;
import com.ai.aiga.dao.NaUiComponentDao;
import com.ai.aiga.domain.NaAutoTemplateComp;
import com.ai.aiga.domain.NaUiComponent;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.view.json.AutoTemplateCompRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

/**
 * 自动化用例模板与组件关联关系
 *
 * @author defaultekey
 * @date 2017/3/5
 */
@Service
@Transactional
public class AutoTemplateCompSv {

    @Autowired
    private NaAutoTemplateCompDao templateCompDao;

    @Autowired
    private NaUiComponentDao componentDao;

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 根据模板ID查询出所有关联组件信息(只包含组件ID)
     * @param templateCompRequest
     * @return
     */
    public List<NaAutoTemplateComp> findByTempId(AutoTemplateCompRequest templateCompRequest){
        if (templateCompRequest == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_com_null);
        }
        if (templateCompRequest.getTempId() == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "tempId");
        }
        return templateCompDao.findByTempId(templateCompRequest.getTempId());
    }

    /**
     * 根据模板ID查询出所有关联组件信息(包含组件详细描述)
     * @param templateCompRequest
     * @return
     */
    public List<AutoTemplateCompRequest> findByTempIdInfo(AutoTemplateCompRequest templateCompRequest){
        if (templateCompRequest == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_com_null);
        }
        if (templateCompRequest.getTempId() == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "tempId");
        }
        List<NaAutoTemplateComp> compList=templateCompDao.findByTempId(templateCompRequest.getTempId());
        List<AutoTemplateCompRequest> requestList=new ArrayList<AutoTemplateCompRequest>();
        if(compList != null && compList.size()>0){
            for(NaAutoTemplateComp comp:compList){
                AutoTemplateCompRequest request=new AutoTemplateCompRequest();
                request.setTempId(comp.getTempId());
                request.setCompOrder(comp.getCompOrder());
                request.setCompId(comp.getCompId());
                request.setRelaId(comp.getRelaId());
                //填充组件具体信息
                NaUiComponent component=componentDao.findOne(comp.getCompId());
                if(component!=null){
                    request.setCompDesc(component.getCompDesc());
                    request.setCompName(component.getCompName());
                    request.setCompScript(component.getCompScript());
                }

                requestList.add(request);
            }
        }
        return  requestList;
    }

    /**
     * 保存模板与组件关系(单个保存)
     * @param compRequest
     * @return
     */
    public NaAutoTemplateComp save(AutoTemplateCompRequest compRequest){
        if (compRequest == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_com_null);
        }
        if (compRequest.getTempId() == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "tempId");
        }
        if (compRequest.getCompId() == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "compId");
        }
        NaAutoTemplateComp comp;
        //如果没有主键，则新增；如果有主键，则修改。
        if (compRequest.getRelaId() == null) {
            comp =new NaAutoTemplateComp();
        }else{
            comp=templateCompDao.findOne(compRequest.getRelaId());
        }
        comp.setCompId(compRequest.getCompId());
        comp.setTempId(compRequest.getTempId());
        comp.setCompOrder(compRequest.getCompOrder());
        comp=templateCompDao.save(comp);
        return comp;
    }

    /**
     * 保存模板与组件关系(批量保存)
     * @param requestList
     */
    public void saveList(List<AutoTemplateCompRequest> requestList){
        if (requestList == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_com_null);
        }
        if (requestList.size()>0){
            //校验数据是否正确
            for (AutoTemplateCompRequest request:requestList){
                if (request.getTempId() == null) {
                    BusinessException.throwBusinessException(ErrorCode.Parameter_null, "tempId");
                }
                if (request.getCompId()== null) {
                    BusinessException.throwBusinessException(ErrorCode.Parameter_null, "compId");
                }
            }
            //根据tempId删除旧关联关系在批量保存
            templateCompDao.deleteByTempId(requestList.get(0).getTempId());
            for (AutoTemplateCompRequest request:requestList){
                NaAutoTemplateComp comp=new NaAutoTemplateComp();
                comp.setCompId(request.getCompId());
                comp.setCompOrder(request.getCompOrder());
                comp.setTempId(request.getTempId());
                entityManager.persist(comp);
            }
            entityManager.flush();
            entityManager.clear();
        }
    }

    /**
     * 根据主键删除
     * @param request
     */
    public void delete(AutoTemplateCompRequest request){
        if (request == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_com_null);
        }
        if (request.getRelaId() == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "relaId");
        }
        templateCompDao.delete(request.getRelaId());
    }

    /**
     * 根据自动化用例模板主键删除
     * @param request
     */
    public  void deleteByTempId(AutoTemplateCompRequest request){
        if (request == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_com_null);
        }
        if (request.getTempId() == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "tempId");
        }
        templateCompDao.deleteByTempId(request.getTempId());
    }
}
