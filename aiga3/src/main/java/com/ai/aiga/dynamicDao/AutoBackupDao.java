package com.ai.aiga.dynamicDao;

import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: AutoBackupDao
 * @author: taoyf
 * @date: 2017年5月5日 上午11:09:06
 * @Description:
 * 
 */
@Repository
@Transactional
public class AutoBackupDao{
	
    private JdbcTemplate jdbcTemplate;

    @Autowired
    @Qualifier("dynamicDataSource")
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    
    

}

