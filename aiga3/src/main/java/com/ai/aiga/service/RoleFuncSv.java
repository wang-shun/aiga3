package com.ai.aiga.service;

import com.ai.aiga.dao.AigaRoleFuncDao;
import com.ai.aiga.domain.AigaRoleFunc;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.view.json.RoleFuncRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

/**
 * 角色菜单权限配置
 *
 * @author defaultekey
 * @date 2017/2/23
 */
@Service
@Transactional
public class RoleFuncSv {

    @Autowired
    private AigaRoleFuncDao aigaRoleFuncDao;

    /**
     * 根据roleId查询所有有权限的菜单
     * @param roleFuncRequest
     * @return
     */
    public List<AigaRoleFunc> findByRoleId(RoleFuncRequest roleFuncRequest){
        if (roleFuncRequest == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_com_null);
        }
        if (roleFuncRequest.getRoleId() == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "roleId");
        }
       return aigaRoleFuncDao.findByRoleId(roleFuncRequest.getRoleId());
    }

    /**
     * 先删除所有关系，后保存现有关系
     * @param roleFuncRequest
     */
    public void beforeDelAfterSave(RoleFuncRequest roleFuncRequest){
        if (roleFuncRequest == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_com_null);
        }
        if (roleFuncRequest.getFuncIds() == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "funcIds");
        }
        if (roleFuncRequest.getRoleId() == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "roleId");
        }
        /*先根据roleId删除*/
        aigaRoleFuncDao.deleteByRoleId(roleFuncRequest.getRoleId());
        /*根据 , 解析funcIds*/
        String[]funIdAry=roleFuncRequest.getFuncIds().split(",");
        /*批量保存*/
        List<Object> funcList=new ArrayList<Object>();
        for (String funcId:funIdAry) {
            AigaRoleFunc aigaRoleFunc=new AigaRoleFunc();
            aigaRoleFunc.setRoleId(roleFuncRequest.getRoleId());
            aigaRoleFunc.setFuncId(Long.parseLong(funcId));
            funcList.add(aigaRoleFunc);
        }
        aigaRoleFuncDao.saveList(funcList);
    }
}
