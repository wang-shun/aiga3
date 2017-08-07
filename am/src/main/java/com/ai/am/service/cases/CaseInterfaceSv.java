package com.ai.am.service.cases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.am.dao.NaCaseInterfaceDao;
import com.ai.am.domain.NaCaseInterface;
import com.ai.am.exception.BusinessException;
import com.ai.am.exception.ErrorCode;
import com.ai.am.service.base.BaseService;
import com.ai.am.service.enums.CaseEnum;

/**
 * 用例接口参数
 *
 * @author defaultekey
 * @date 2017/4/27
 */
@Service
@Transactional
public class CaseInterfaceSv extends BaseService{

    @Autowired
    private NaCaseInterfaceDao interfaceDao;
    /**
     * 保存入口
     * @param caseInterface 对象
     * @return NaCaseInterface对象
     */
    public NaCaseInterface save(NaCaseInterface caseInterface){
        if (caseInterface.getCaseId() == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "caseId");
        }
        if (caseInterface.getInterfaceType() == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "interfaceType");
        }
        if (caseInterface.getMessageType() == null) {
            caseInterface.setMessageType(CaseEnum.messageType_other.getValue());//默认报文类型为其他
        }
        return interfaceDao.save(caseInterface);
    }

    /**
     * 根据caseId查询接口参数信息
     * @param caseId 用例模板ID主键
     * @return NaCaseInterface对象
     */
    public NaCaseInterface findByCaseId(Long caseId){
        if (caseId == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "caseId");
        }
        NaCaseInterface caseInterface=this.interfaceDao.findByCaseId(caseId);
        if (caseInterface == null) {
            BusinessException.throwBusinessException(ErrorCode.BAD_REQUEST);
        }
        return caseInterface;
    }
}
