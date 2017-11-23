package br.com.uff.ubicomp.activityrecognition.client.persistence.repository;

public abstract class Validation {
	
	public static void assertNotNull(Object object) {
		if(object == null) {
			throw new IllegalArgumentException("[Assertion failed] - the object argument must not be null");			
		}		
	}	
}