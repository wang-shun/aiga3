package com.ai.am.dao.jpa;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.internal.util.StringHelper;
import org.hibernate.transform.AliasedTupleSubsetResultTransformer;

/**
 * @ClassName: ColToBeanResultTransformer
 * @author: taoyf
 * @date: 2017年4月17日 下午6:24:28
 * @Description:
 * 
 */
public class ColToMapResultTransformer extends AliasedTupleSubsetResultTransformer {

	public static final ColToMapResultTransformer INSTANCE = new ColToMapResultTransformer();

	/**
	 * Disallow instantiation of AliasToEntityMapResultTransformer.
	 */
	private ColToMapResultTransformer() {
	}

	@Override
	public Object transformTuple(Object[] tuple, String[] aliases) {
		Map result = new HashMap(tuple.length);
		for ( int i=0; i<tuple.length; i++ ) {
			String alias = aliases[i];
			
			String humpName = SqlHelp.toHumpName(StringHelper.unqualify(alias));
			if ( humpName!=null ) {
				result.put( humpName, tuple[i] );
			}
		}
		return result;
	}

	@Override
	public boolean isTransformedValueATupleElement(String[] aliases, int tupleLength) {
		return false;
	}

	/**
	 * Serialization hook for ensuring singleton uniqueing.
	 *
	 * @return The singleton instance : {@link #INSTANCE}
	 */
	private Object readResolve() {
		return INSTANCE;
	}

}

