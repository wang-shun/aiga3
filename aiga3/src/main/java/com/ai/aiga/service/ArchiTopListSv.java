package com.ai.aiga.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.dao.ArchiTopListDao;
import com.ai.aiga.dao.jpa.Condition;
import com.ai.aiga.domain.ArchiTopList;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.base.BaseService;

@Service
@Transactional
public class ArchiTopListSv extends BaseService {

	@Autowired
	private ArchiTopListDao archiTopListDao;
	
	//find
	public List<ArchiTopList> findAll(){
		return archiTopListDao.findAll();
	}
	
	//add
	public void save(ArchiTopList request){
		archiTopListDao.save(request);
	}
	
	//delete
	public void delete(Long indexId){
		if(indexId==null||indexId<0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		archiTopListDao.delete(indexId);
	}

	//update
	public void update(ArchiTopList request){
		archiTopListDao.save(request);
	}

	public Page<ArchiTopList> findAllByPage(ArchiTopList request, int pageNumber, int pageSize) {
        List<Condition> cons = new ArrayList<Condition>();
        if(pageNumber < 0){
            pageNumber = 0;
        }
        if(pageSize <= 0){
            pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
        }
        Pageable pageable = new PageRequest(pageNumber, pageSize);
        return archiTopListDao.search(cons, pageable);
	}
	
	public Page<ArchiTopList> queryByCondition(ArchiTopList condition, int pageNumber,
			int pageSize) throws ParseException {
        List<Condition> cons = new ArrayList<Condition>();
    	if(condition.getIndexId()==0){
    		cons.add(new Condition("indexId", condition.getIndexId(), Condition.Type.GT));
    	}
    	if(condition.getIndexId()!=0){
    		cons.add(new Condition("indexId", condition.getIndexId(), Condition.Type.EQ));
    	}
    	if(condition.getIndexSeq()!=null){
    		cons.add(new Condition("indexSeq", condition.getIndexSeq(), Condition.Type.EQ));
    	}
    	if(StringUtils.isNoneBlank(condition.getIndexName())){
    		cons.add(new Condition("indexName", "%" + condition.getIndexName() + "%", Condition.Type.LIKE));
    	}
    	if(StringUtils.isNoneBlank(condition.getIndexGroup())){
    		cons.add(new Condition("indexGroup", "%" + condition.getIndexGroup() + "%", Condition.Type.LIKE));
    	}
    	if(StringUtils.isNoneBlank(condition.getSettMonth())){
    		cons.add(new Condition("settMonth", condition.getSettMonth(), Condition.Type.EQ));
    	}
        if(pageNumber < 0){
            pageNumber = 0;
        }
        if(pageSize <= 0){
            pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
        }
        Pageable pageable = new PageRequest(pageNumber, pageSize);
		return archiTopListDao.search(cons, pageable);
	}
	
}
