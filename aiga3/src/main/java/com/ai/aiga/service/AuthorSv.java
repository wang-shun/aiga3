package com.ai.aiga.service;

import com.ai.aiga.dao.AigaAuthorDao;
import com.ai.aiga.domain.AigaAuthor;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.view.json.AuthorRoleRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * 员工角色业务逻辑处理
 *
 * @author defaultekey
 * @date 2017/2/22
 */
@Service
@Transactional
public class AuthorSv {

    @Autowired
    private AigaAuthorDao aigaAuthorDao;

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 根据员工ID获取所有角色
     * @param authorRoleRequest
     * @return
     */
    public List<AigaAuthor> findByStaffId(AuthorRoleRequest authorRoleRequest){
        if (authorRoleRequest == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_com_null);
        }
        if (authorRoleRequest.getStaffId() == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "staffId");
        }
        return aigaAuthorDao.findByStaffId(authorRoleRequest.getStaffId());
    }

    /**
     * 先根据staffId删除数据后保存
     * @param authorRoleRequest
     */
    public void beforeDelAfterSave(AuthorRoleRequest authorRoleRequest){
        if (authorRoleRequest == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_com_null);
        }
        if (authorRoleRequest.getStaffId() == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "staffId");
        }
        if (authorRoleRequest.getRoleIds() == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "roleIds");
        }
        /*先根据员工ID删除数据*/
        aigaAuthorDao.deleteByStaffId(authorRoleRequest.getStaffId());
        /*通过 , 解析roleIds*/
        String[]roleIdAry=authorRoleRequest.getRoleIds().split(",");
        for(String roleId:roleIdAry){
            AigaAuthor author=new AigaAuthor();
            author.setStaffId(authorRoleRequest.getStaffId());
            author.setRoleId(Long.parseLong(roleId));
            entityManager.persist(author);
        }
        entityManager.flush();
        entityManager.clear();
    }
}
