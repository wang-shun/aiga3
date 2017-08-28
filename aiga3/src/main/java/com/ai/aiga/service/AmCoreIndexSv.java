package com.ai.aiga.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ai.aiga.dao.AmCoreIndexDao;
import com.ai.aiga.dao.jpa.Condition;
import com.ai.aiga.domain.AmCoreIndex;
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
//		return amCoreIndexDao.findAll();
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
    
}
