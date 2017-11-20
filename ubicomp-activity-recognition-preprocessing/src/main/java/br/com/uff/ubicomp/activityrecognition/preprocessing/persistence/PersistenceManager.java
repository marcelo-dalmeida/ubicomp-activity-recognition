package br.com.uff.ubicomp.activityrecognition.preprocessing.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenceManager {
	
	private static PersistenceManager instance = null;

	private EntityManagerFactory emFactory;
	
	private PersistenceManager() {
		emFactory = Persistence.createEntityManagerFactory("ubicomp-activity-recognition-preprocessing");
	}
	
	public static PersistenceManager getInstance() {
      if(instance == null) {
         instance = new PersistenceManager();
      }
      return instance;
   }
	
	public EntityManager getEntityManager() {
		return emFactory.createEntityManager();
	}

	public void close() {
		emFactory.close();
	}
}