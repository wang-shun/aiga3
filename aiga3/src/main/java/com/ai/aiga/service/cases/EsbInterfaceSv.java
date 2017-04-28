package com.ai.aiga.service.cases;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.dao.AigaEsbInterfaceDao;
import com.ai.aiga.dao.jpa.Condition;
import com.ai.aiga.domain.AigaEsbInterface;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.view.json.cases.EsbInterfaceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * ESB报文管理
 *
 * @author defaultekey
 * @date 2017/4/27
 */
@Service
@Transactional
public class EsbInterfaceSv {
    
    @Autowired
    private AigaEsbInterfaceDao esbInterfaceDao;

    /**
     * 根据ID获取ESB报文信息
     * @param esbId ID
     * @return AigaEsbInterface
     */
    public AigaEsbInterface findById(Long esbId){
        AigaEsbInterface esbInterface=this.esbInterfaceDao.findOne(esbId);
        if (esbInterface == null) {
            BusinessException.throwBusinessException(ErrorCode.BAD_REQUEST);
        }
        return esbInterface;
    }

    /**
     * 查询全部信息
     * @return
     */
    public List<AigaEsbInterface> findAll(){
        return this.esbInterfaceDao.findAll();
    }

    /**
     * 根据分页查询
     * @param condition 条件
     * @param pageNumber 页码
     * @param pageSize 每页数量
     * @return 分页查询的结果
     */
    public Object listByPage(EsbInterfaceRequest condition,int pageNumber,int pageSize){
        List<Condition> conditions = new ArrayList<Condition>();
        if(pageNumber < 0){
            pageNumber = 0;
        }

        if(pageSize <= 0){
            pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
        }

        Pageable pageable = new PageRequest(pageNumber, pageSize);
        return this.esbInterfaceDao.search(conditions,pageable);
    } 
}
