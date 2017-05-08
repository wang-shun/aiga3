package com.ai.aiga.core.transaction;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.springframework.transaction.support.ResourceHolderSupport;

/**
 * @ClassName: MultipleTransactionHolder
 * @author: taoyf
 * @date: 2017年5月5日 下午2:51:22
 * @Description:
 * 
 */
public class MultipleTransactionHolder extends ResourceHolderSupport {
	
	private boolean transactionActive;
	
	private Stack<ResourceHolder> stack = new Stack<ResourceHolder>();
	 
	private Map<String, ResourceHolder> map = new HashMap<String, ResourceHolder>();
	
	
	private void addHolder(ResourceHolder holder){
		stack.push(holder);
	}
	
	/**
	 * 
	 * @author: taoyf
	 * @date: 2017年5月5日 下午5:22:58
	 *
	 * @Description:看一眼
	 */
	public ResourceHolder peekHolder(){
		if(stack.isEmpty()){
			return null;
		}
		return stack.peek();
	}
	
	public boolean stackIsEmpty(){
		return stack.isEmpty();
	}
	
	public int stackSize(){
		return stack.size();
	}
	
	/**
	 * 
	 * @author: taoyf
	 * @date: 2017年5月5日 下午5:22:58
	 *
	 * @Description:拿出来
	 */
	public ResourceHolder popHolder(){
		if(stack.isEmpty()){
			return null;
		}
		return stack.pop();
	}
	
	public ResourceHolder getHolder(String key){
		return map.get(key);
	}
	
	public void putHolder(String key, ResourceHolder value){
		if(!map.containsKey(key)){
			addHolder(value);
		}
		map.put(key, value);
	}
	
	public void removeHolder(String key){
		map.remove(key);
	}

	/**
	 * @ClassName: MultipleTransactionHolder :: getAllHolder
	 * @author: taoyf
	 * @date: 2017年5月6日 下午3:54:35
	 *
	 * @Description:
	 * @return          
	 */
	public Map<String, ResourceHolder> getAllHolder() {
		return map;
	}

	/**
	 * @ClassName: MultipleTransactionHolder :: clearHolder
	 * @author: taoyf
	 * @date: 2017年5月6日 下午4:14:38
	 *
	 * @Description:          
	 */
	public void clearHolder() {
		map.clear();
		stack.clear();
	}
	
	

}

