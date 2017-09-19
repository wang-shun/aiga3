package com.ai.aiga.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.dao.AmCoreIndexExtDao;
import com.ai.aiga.dao.jpa.Condition;
import com.ai.aiga.domain.AmCoreIndex;
import com.ai.aiga.domain.AmCoreIndexExt;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.base.BaseService;
@Service
@Transactional
public class AmCoreIndexExtSv extends BaseService {

	@Autowired
	private AmCoreIndexExtDao amCoreIndexExtDao;
	
	public Page<AmCoreIndexExt> queryAmCoreExts(AmCoreIndexExt condition, int pageNumber,
			int pageSize) throws ParseException {
        List<Condition> cons = new ArrayList<Condition>();
    	if(condition.getIndexId()==0){
    		cons.add(new Condition("indexId", condition.getIndexId(), Condition.Type.GT));
    	}
    	if(condition.getIndexId()!=0){
    		cons.add(new Condition("indexId", condition.getIndexId(), Condition.Type.EQ));
    	}
    	if(StringUtils.isNoneBlank(condition.getKey1())){
    		cons.add(new Condition("key1", "%" + condition.getKey1() + "%", Condition.Type.LIKE));
    	}
    	if(StringUtils.isNoneBlank(condition.getKey2())){
    		cons.add(new Condition("key2", "%" + condition.getKey2() + "%", Condition.Type.LIKE));
    	}
    	if(StringUtils.isNoneBlank(condition.getKey3())){
    		cons.add(new Condition("key3", "%" + condition.getKey3() + "%", Condition.Type.LIKE));
    	}
    	if(StringUtils.isNoneBlank(condition.getQuerySql())){
    		cons.add(new Condition("querySql", "%" + condition.getQuerySql() + "%", Condition.Type.LIKE));
    	}
    	if(condition.getCreateOpId()!=null){
    		cons.add(new Condition("createOpId", "%" + condition.getCreateOpId() + "%", Condition.Type.EQ));
    	}
        if(pageNumber < 0){
            pageNumber = 0;
        }
        if(pageSize <= 0){
            pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
        }
        Pageable pageable = new PageRequest(pageNumber, pageSize);
		return amCoreIndexExtDao.search(cons, pageable);
	}
	
    public void save(AmCoreIndexExt amCoreIndexExt){
    	amCoreIndexExtDao.save(amCoreIndexExt);
    }
    
	public List<AmCoreIndexExt> findAll(){
		return amCoreIndexExtDao.findAll();
	}
	
	public void delete(Long indexId){
		if(indexId==null||indexId<0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		amCoreIndexExtDao.delete(indexId);
	}
	
	public void update(AmCoreIndexExt amCoreIndexExt){
		amCoreIndexExtDao.save(amCoreIndexExt);
	}
}
