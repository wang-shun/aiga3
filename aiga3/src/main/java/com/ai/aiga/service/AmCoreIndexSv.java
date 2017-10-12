package com.ai.aiga.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.dao.AmCoreIndexDao;
import com.ai.aiga.dao.jpa.Condition;
import com.ai.aiga.domain.AmCoreIndex;
import com.ai.aiga.domain.ArchitectureGrading;
import com.ai.aiga.domain.QuestionEvent;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.view.controller.archiQuesManage.dto.AmCoreIndexSelects;
@Service
@Transactional
public class AmCoreIndexSv extends BaseService {
	
	@Autowired
	private AmCoreIndexDao amCoreIndexDao;
	
	public List<AmCoreIndex>findAmCoreIndex(AmCoreIndexSelects condition){
    	List<Condition>cons = new ArrayList<Condition>();
    	if(StringUtils.isNoneBlank(condition.getIndexGroup())){
    		cons.add(new Condition("indexGroup", "%".concat(condition.getIndexGroup()).concat("%"), Condition.Type.LIKE));
    	}
    	if(StringUtils.isNoneBlank(condition.getIndexName())){
    		cons.add(new Condition("indexName", "%".concat(condition.getIndexName()).concat("%"), Condition.Type.LIKE));
    	}
    	return amCoreIndexDao.search(cons);
	}
	
	public Page<AmCoreIndex> findAmCoreIndexByPage(AmCoreIndexSelects condition, int pageNumber,
			int pageSize) throws ParseException {
        List<Condition> cons = new ArrayList<Condition>();
    	if(StringUtils.isNoneBlank(condition.getIndexGroup())){
    		cons.add(new Condition("indexGroup", "%".concat(condition.getIndexGroup()).concat("%"), Condition.Type.LIKE));
    	}
    	if(StringUtils.isNoneBlank(condition.getIndexName())){
    		cons.add(new Condition("indexName", "%".concat(condition.getIndexName()).concat("%"), Condition.Type.LIKE));
    	}
    	if(condition.getGroupId()==0){
    		cons.add(new Condition("groupId", condition.getGroupId(), Condition.Type.GT));
    	}
    	if(condition.getGroupId()!=0){
    		cons.add(new Condition("groupId", condition.getGroupId(), Condition.Type.EQ));
    	}
        if(pageNumber < 0){
            pageNumber = 0;
        }
        if(pageSize <= 0){
            pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
        }
        Pageable pageable = new PageRequest(pageNumber, pageSize);
		return amCoreIndexDao.search(cons, pageable);
	}	
	public List<AmCoreIndex> distinctAmCoreIndexDbname(){
		List<AmCoreIndex>list = amCoreIndexDao.findAll();
		List<AmCoreIndex>newList = new ArrayList<AmCoreIndex>(); 
		List<String>indexGrouplist = new ArrayList<String>(); 
		Iterator iter= list.iterator();
		while(iter.hasNext()){  
			AmCoreIndex am=(AmCoreIndex)iter.next();  
			if(!indexGrouplist.contains(am.getSchId().trim())){
				indexGrouplist.add(am.getSchId().trim());
				newList.add(am);
			}
		}  
		return newList;
	}
    public List<AmCoreIndex> distinctAmCoreIndex(){
    	List<AmCoreIndex>list = amCoreIndexDao.findAllDayConnects();
    	List<AmCoreIndex>newList = new ArrayList<AmCoreIndex>(); 
        List<String>indexGrouplist = new ArrayList<String>(); 
        Iterator iter= list.iterator();//List接口实现了Iterable接口  
        while(iter.hasNext()){  
        	AmCoreIndex am=(AmCoreIndex)iter.next();  
         	if(!indexGrouplist.contains(am.getIndexGroup().trim())){
         		indexGrouplist.add(am.getIndexGroup().trim());
         		newList.add(am);
         	}
      }  
        return newList;
    }
    public List<AmCoreIndex> distinctAmCoreIndex2(){
    	List<AmCoreIndex>list = amCoreIndexDao.findAll();
    	List<AmCoreIndex>newList = new ArrayList<AmCoreIndex>(); 
    	List<String>indexGrouplist = new ArrayList<String>(); 
    	Iterator iter= list.iterator();
    	while(iter.hasNext()){  
    		AmCoreIndex am=(AmCoreIndex)iter.next();  
    		if(!indexGrouplist.contains(am.getIndexGroup().trim())){
    			indexGrouplist.add(am.getIndexGroup().trim());
    			newList.add(am);
    		}
    	}  
    	return newList;
    }
    
    public List<AmCoreIndex> distinctMonthAmCoreIndex(){
    	List<AmCoreIndex>list = amCoreIndexDao.findAllMonthConnects();
    	List<AmCoreIndex>newList = new ArrayList<AmCoreIndex>(); 
    	List<String>indexGrouplist = new ArrayList<String>(); 
    	Iterator iter= list.iterator();//List接口实现了Iterable接口  
    	while(iter.hasNext()){  
    		AmCoreIndex am=(AmCoreIndex)iter.next();  
    		if(!indexGrouplist.contains(am.getIndexGroup().trim())){
    			indexGrouplist.add(am.getIndexGroup().trim());
    			newList.add(am);
    		}
    	}  
    	return newList;
    }
    
    public List<AmCoreIndex>selectIndexName(AmCoreIndexSelects condition){
    	List<Condition>cons = new ArrayList<Condition>();
    	if(StringUtils.isNoneBlank(condition.getIndexGroup())){
    		cons.add(new Condition("indexGroup", "%".concat(condition.getIndexGroup()).concat("%"), Condition.Type.LIKE));
    	}
    	if(StringUtils.isNoneBlank(condition.getIndexName())){
    		cons.add(new Condition("indexName", "%".concat(condition.getIndexName()).concat("%"), Condition.Type.LIKE));
    	}
    	return amCoreIndexDao.search(cons);
    }
    
    public List<AmCoreIndex>justIndexName(String indexGroup){
    	return amCoreIndexDao.findByIndexGroup(indexGroup);
    }
    
    public void save(AmCoreIndex amCoreIndex){
    	amCoreIndexDao.save(amCoreIndex);
    }
    
	public void delete(Long indexId){
		if(indexId==null||indexId<0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		amCoreIndexDao.delete(indexId);
	}
	
	public List<AmCoreIndex> findAll(){
		return amCoreIndexDao.findAll();
	}
	
	public void update(AmCoreIndex amCoreIndex){
		amCoreIndexDao.save(amCoreIndex);
	}
	
	public Page<AmCoreIndex> queryAmCores(AmCoreIndex condition, int pageNumber,
			int pageSize) throws ParseException {
        List<Condition> cons = new ArrayList<Condition>();
    	if(condition.getIndexId()==0){
    		cons.add(new Condition("indexId", condition.getIndexId(), Condition.Type.GT));
    	}
    	if(condition.getIndexId()!=0){
    		cons.add(new Condition("indexId", condition.getIndexId(), Condition.Type.EQ));
    	}
    	if(StringUtils.isNoneBlank(condition.getIndexName())){
    		cons.add(new Condition("indexName", "%" + condition.getIndexName() + "%", Condition.Type.LIKE));
    	}
    	if(StringUtils.isNoneBlank(condition.getIndexGroup())){
    		cons.add(new Condition("indexGroup", "%" + condition.getIndexGroup() + "%", Condition.Type.LIKE));
    	}
    	if(StringUtils.isNoneBlank(condition.getSchId())){
    		cons.add(new Condition("schId", "%" + condition.getSchId() + "%", Condition.Type.LIKE));
    	}
    	if(StringUtils.isNoneBlank(String.valueOf(condition.getCreateDate()))){
    		cons.add(new Condition("createDate", condition.getCreateDate(), Condition.Type.GT));
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
    	if(condition.getCreateOpId()!=null){
    		cons.add(new Condition("createOpId", "%" + condition.getCreateOpId() + "%", Condition.Type.EQ));
    	}
    	if(condition.getGroupId()!=null){
    		cons.add(new Condition("groupId", "%" + condition.getGroupId() + "%", Condition.Type.EQ));
    	}
        if(pageNumber < 0){
            pageNumber = 0;
        }
        if(pageSize <= 0){
            pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
        }
        Pageable pageable = new PageRequest(pageNumber, pageSize);
		return amCoreIndexDao.search(cons, pageable);
	}

}
