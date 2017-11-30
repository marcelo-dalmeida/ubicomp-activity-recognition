package br.com.uff.ubicomp.activityrecognition.server;

import java.util.Random;

import br.com.uff.ubicomp.activityrecognition.server.persistence.entity.EnergyPositionActivity;
import br.com.uff.ubicomp.activityrecognition.server.persistence.repository.EnergyPositionActivityRepository;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import weka.experiment.InstanceQuery;

public class Main {

	public static void main (String[] args) throws Exception {
		
		Instances data = readDatabase();
		execute(data);
	}
	
	public static Instances readDatabase() throws Exception {
		
		//TODO To avoid querying this way, create a ResultSet or find another way of creating 'Instances'
		InstanceQuery instanceQuery = new InstanceQuery();
		instanceQuery.setUsername("root");
		instanceQuery.setPassword("rootmysqlpassword");
		instanceQuery.setQuery("SELECT average_power, median_position, activity "
							 + "FROM energy_position_activity");
		
		Instances data = instanceQuery.retrieveInstances();
	    data.setClassIndex(data.numAttributes() - 1);
		
		return data;
	}
	
	public static void execute(Instances data) throws Exception {

		System.out.println(data);
		System.out.println("\n");
		
		//classifier
		RandomForest randomForest = new RandomForest();
		randomForest.buildClassifier(data);
		System.out.println(randomForest);
		System.out.println("\n");
		
		//evaluation
		Evaluation eval = new Evaluation(data);
		eval.crossValidateModel(randomForest, data, 10, new Random(1));
		System.out.println("Correct -> " + (eval.correct()/eval.numInstances())*100 + "%");
	}
}
