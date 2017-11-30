package br.com.uff.ubicomp.activityrecognition.client.persistence.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId; 

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;


import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import br.com.uff.ubicomp.activityrecognition.client.persistence.PersistenceManager;
import br.com.uff.ubicomp.activityrecognition.client.persistence.entity.EnergyPositionActivity;
import br.com.uff.ubicomp.activityrecognition.client.smarthome.Environment;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public abstract class AbstractRepository<T, ID extends Serializable, USER_ID extends Serializable, TIME extends Serializable, SENSOR extends Serializable> {
	
	private Logger logger = Logger.getLogger(AbstractRepository.class);
	
	public abstract Class<T> getDomainClass();
	
	public EntityManager getEntityManager() {		

		return PersistenceManager.getInstance().getEntityManager();
	}
	
	public void delete(ID id) throws RuntimeException {
	
		T entity = findOne(id);
		
		Validation.assertNotNull(entity);

		delete(entity);
	}
	
	public void delete(T entity) {
		
		Validation.assertNotNull(entity);
		
		EntityManager em = getEntityManager();
		
		try {
			em.getTransaction().begin();
			em.remove(entity);
			em.getTransaction().commit();
		} catch (RuntimeException e) {
			logger.error(e.getMessage());
			em.getTransaction().rollback();
		} finally {
			em.close();
		}

	}


	public void delete(Iterable<? extends T> entities) {
		
		Validation.assertNotNull(entities);

		EntityManager em = getEntityManager();

		try {
			em.getTransaction().begin();
			for (T entity : entities) {
				em.remove(entity);
			}
			em.getTransaction().commit();
		} catch (RuntimeException e) {
			logger.error(e.getMessage());
			try {
				em.getTransaction().rollback();
			} catch (HibernateException he) {
				logger.error(he.getMessage());
			}
		} finally {
			em.close();
		}
	}
	
	public T findOne(ID id) {
		
		Class<T> classType = getDomainClass();
		T entity = null;
		
		EntityManager em = getEntityManager();
		
		try {
			em.getTransaction().begin();
			entity = (T) em.find(classType, id);
			em.getTransaction().commit();
		} catch (RuntimeException e) {
			logger.error(e.getMessage());
			em.getTransaction().rollback();
		} finally {
			em.close();
		}		
		
		return entity;
	}
        
        public String findOne(USER_ID user_id, TIME time, SENSOR sensor) {
		
		Class<T> classType = getDomainClass();
		T entity = null;
		
		EntityManager em = getEntityManager();
                
                String retorno = "";
                		
		try {
			em.getTransaction().begin();
			
                            Session s = (Session)em.getDelegate();

                            String query = "SELECT h.user_id, h.time, h.user_position, h.activity, h.livingRoomEnergy, h.bedroomEnergy, h.bathroomEnergy, h.kitchenEnergy, h.externalAreaEnergy, h.id FROM historic h WHERE h.user_id = :user_id and h.time = :time";
                            
                            Query q = em.createNativeQuery(query.toString(),EnergyPositionActivity.class);
                            q.setParameter("user_id", user_id);
                            q.setParameter("time", time);
                            
                            List<EnergyPositionActivity> results; 
                            results = (List<EnergyPositionActivity>) q.getResultList();
                            
                            for (EnergyPositionActivity epa : results)
                            {
                                retorno = retorno + ((EnergyPositionActivity)epa).getUserId() + ";";
                                retorno = retorno + ((EnergyPositionActivity)epa).getTime().toString() + ";";
                                
                                if (sensor.equals("USER"))
                                {
                                    retorno = retorno + ((EnergyPositionActivity)epa).getUser_position().name() + ";";
                                    retorno = retorno + ((EnergyPositionActivity)epa).getActivity().name();
                                }
                                else if (sensor.equals("BATHROOM"))
                                {
                                    retorno = retorno + ((EnergyPositionActivity)epa).getMeasurementOfBathroom().toString();
                                }
                                else if (sensor.equals("BEDROOM"))
                                {
                                    retorno = retorno + ((EnergyPositionActivity)epa).getMeasurementOfBedroom().toString();
                                }
                                else if (sensor.equals("EXTERNALAREA"))
                                {
                                    retorno = retorno + ((EnergyPositionActivity)epa).getMeasurementOfExternal_area().toString();
                                }
                                else if (sensor.equals("KITCHEN"))
                                {
                                    retorno = retorno + ((EnergyPositionActivity)epa).getMeasurementOfKitchen().toString();
                                }
                                else if (sensor.equals("LIVINGROOM"))
                                {
                                    retorno = retorno + ((EnergyPositionActivity)epa).getMeasurementOfRoom().toString();
                                }
                            }
                        em.getTransaction().commit();
		} catch (RuntimeException e) {
			logger.error(e.getMessage());
			em.getTransaction().rollback();
		} finally {
			em.close();
		}		
		
                //return entity;
                return retorno;
	}
        
        public boolean exists(ID id) {

		return findOne(id) != null;
	}
	
	public List<T> findAll() {		
		EntityManager em = getEntityManager();
		
		Class<T> classType = getDomainClass();		

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<T> criteria = builder.createQuery(classType);
		List<T> list = em.createQuery(criteria).getResultList();
		
		em.close();
		
		return list;
	}
	
	public long count() {
		
		EntityManager em = getEntityManager();
		
		Class<T> classType = getDomainClass();
		
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<T> root = criteria.from(classType);
		criteria.select(builder.count(root));

		Long rowCount = em.createQuery(criteria).getSingleResult();
		
		em.close();
			
		return rowCount;
	}

	
	public <S extends T> S save(S entity) {
		
		Validation.assertNotNull(entity);
		
		EntityManager em = getEntityManager();

		try {
			em.getTransaction().begin();
			em.merge(entity);
			em.getTransaction().commit();
			return entity;
		} catch (RuntimeException e) {
			logger.error(e.getMessage());
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
		
		return null;
	}
	
	@Transactional
	public <S extends T> List<S> save(Iterable<S> entities) {
		
		Validation.assertNotNull(entities);
		
		List<S> result = new ArrayList<S>();

		EntityManager em = getEntityManager();
		
		try {
			em.getTransaction().begin();		
			for (S entity : entities) {
				em.merge(entity); 
				result.add(entity);
			}
			em.getTransaction().commit();
			return result;
		} catch (RuntimeException e) {
			logger.error(e.getMessage());		
			try {
				em.getTransaction().rollback();
			} catch (HibernateException he) {
				logger.error(he.getMessage());
			}
		} finally {
			em.close();
		}
		
		return null;
	}
	
}