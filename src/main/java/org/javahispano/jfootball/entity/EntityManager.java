/**
 * 
 */
package org.javahispano.jfootball.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * @author adou
 *
 */
public class EntityManager {
	private Map<Integer, BaseGameEntity> EntityMap = new HashMap<Integer, BaseGameEntity>();
	private static EntityManager instance;
	
	private EntityManager() {
		
	}

	public static EntityManager getInstance() {
		if (instance == null) {
			instance = new EntityManager();
		}
		
		return instance;
	}

	/**
	 * @return the entityMap
	 */
	public Map<Integer, BaseGameEntity> getEntityMap() {
		return EntityMap;
	}
	
	
}
