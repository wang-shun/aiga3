package com.ai.aiga.cache;

import java.util.Map;

public interface Icache {

	void reload();

	Object getValue(Object key);

	Map getAll();

}
