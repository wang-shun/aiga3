package com.ai.am.service.datasource;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.am.core.datasource.DataSourceInfo;
import com.ai.am.core.datasource.IDynamicDataSourceSv;
import com.ai.am.dao.NaAutoDbAcctDao;
import com.ai.am.domain.NaAutoDbAcct;
import com.ai.am.service.base.BaseService;

/**
 * @ClassName: DynamicDataSourceSv
 * @author: taoyf
 * @date: 2017年5月4日 下午4:01:47
 * @Description:
 * 
 */
@Service()
@Transactional
public class DynamicDataSourceSv extends BaseService implements IDynamicDataSourceSv {
	
	@Autowired
	private NaAutoDbAcctDao naAutoDbAcctDao;

	@Override
	public List<DataSourceInfo> getDataSources() {
		List<NaAutoDbAcct> dbAcctList = naAutoDbAcctDao.findByState('U');
		
		if(dbAcctList == null){
			return null;
		}else{
			List<DataSourceInfo> list = new ArrayList<DataSourceInfo>(dbAcctList.size());
			for(NaAutoDbAcct acct : dbAcctList){
				DataSourceInfo info = new DataSourceInfo();
				info.setDbAcctCode(acct.getDbAcctCode());
				info.setDefaultConnMax(acct.getDefaultConnMax());
				info.setDefaultConnMin(acct.getDefaultConnMin());
				info.setHost(acct.getHost());
				info.setPassword(acct.getPassword());
				info.setPort(acct.getPort());
				info.setSid(acct.getSid());
				info.setUsername(acct.getUsername());
				list.add(info);
			}
			return list;
		}
	}

}

