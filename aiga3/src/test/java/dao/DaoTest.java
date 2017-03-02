package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = { "/spring/applicationContext.xml" })
public class DaoTest {
	
	@PersistenceContext
	protected EntityManager entityManager;
	
	@Test
	public void test(){
		System.out.println(entityManager);
		
		String sql = "select af.staff_id,af.code,af.name,af.state,ao.organize_id,ao.organize_name,ao.code as code2 " +
"from aiga_staff af, aiga_organize ao, aiga_staff_org_relat ar " +
"where af.staff_id = ar.staff_id ";
		
		Query query = entityManager.createNativeQuery(sql);
		List list = query.getResultList();
		
		System.out.println(list);
		
		
//		List<Object[]> list = query.getResultList();
//		
//		for(Object[] objs : list){
//			for(Object o : objs){
//				System.out.print(o + ", ");
//			}
//			System.out.println();
//		}
		
		//System.out.println(list);
		
	}

}
