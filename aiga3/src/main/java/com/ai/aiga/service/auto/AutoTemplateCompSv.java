package com.ai.aiga.service.auto;

import com.ai.aiga.dao.NaAutoTemplateCompDao;
import com.ai.aiga.dao.NaUiComponentDao;
import com.ai.aiga.domain.NaAutoTemplateComp;
import com.ai.aiga.domain.NaUiComponent;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.util.mapper.BeanMapper;
import com.ai.aiga.view.json.auto.AutoTemplateCompRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * 根据模板ID查询出所有关联组件信息(只包含组件ID)
     * @param templateCompRequest
     * @return
     */
    public List<NaAutoTemplateComp> findByTempId(AutoTemplateCompRequest templateCompRequest){
        if (templateCompRequest == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_com_null);
        }
        return this.findByTempId(templateCompRequest.getTempId());
    }

    /**
     * 根据模板ID查询出所有关联组件信息(只包含组件ID)
     * @param tempId
     * @return
     */
    public List<NaAutoTemplateComp> findByTempId(Long tempId){
        if (tempId == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "tempId");
        }
        return templateCompDao.findByTempId(tempId);
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
        return this.findByTempIdInfo(templateCompRequest.getTempId());
    }

    /***
     * 根据模板ID查询出所有关联组件信息(包含组件详细描述)
     * @param tempId
     * @return
     */
    public List<AutoTemplateCompRequest> findByTempIdInfo(Long tempId){
        if (tempId == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "tempId");
        }
        List<NaAutoTemplateComp> compList=templateCompDao.findByTempId(tempId);
        List<AutoTemplateCompRequest> requestList=new ArrayList<AutoTemplateCompRequest>();
        if(compList != null && compList.size()>0){
            for(NaAutoTemplateComp comp:compList){
                AutoTemplateCompRequest request=BeanMapper.map(comp,AutoTemplateCompRequest.class);
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
        NaAutoTemplateComp comp= BeanMapper.map(compRequest,NaAutoTemplateComp.class);
        comp=templateCompDao.save(comp);
        return comp;
    }


    /**
     * 批量保存自动化用例模板与组件关系
     * @param requestList
     * @param tempId
     */
    public void saveList(List<AutoTemplateCompRequest> requestList,Long tempId){
        if (requestList == null||requestList.size()==0) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_com_null);
        }
        //校验数据是否正确
        for (AutoTemplateCompRequest request:requestList) {
            if (request.getCompId() == null) {
                BusinessException.throwBusinessException(ErrorCode.Parameter_null, "compId");
            }
        }
        //根据tempId删除旧关联关系
        this.deleteByTempId(tempId);
        //批量保存新的组件关系
        List<NaAutoTemplateComp> compList=new ArrayList<NaAutoTemplateComp>();
        for (AutoTemplateCompRequest request:requestList){
            NaAutoTemplateComp comp=BeanMapper.map(request,NaAutoTemplateComp.class);
            comp.setTempId(tempId);
            compList.add(comp);
        }
        templateCompDao.save(compList);
    }

    /**
     * 根据主键删除
     * @param request
     */
    public void delete(AutoTemplateCompRequest request){
        if (request == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_com_null);
        }
        this.delete(request.getRelaId());
    }

    /**
     * 根据主键删除(唯一删除入口)
     * @param relaId
     */
    public void delete(Long relaId){
        if (relaId== null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "relaId");
        }
        templateCompDao.delete(relaId);
    }

    /**
     * 根据自动化用例模板主键删除（唯一批量删除入口）
     * @param tempId
     */
    public  void deleteByTempId(Long tempId){
        if (tempId == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "tempId");
        }
        templateCompDao.deleteByTempId(tempId);
    }
}
