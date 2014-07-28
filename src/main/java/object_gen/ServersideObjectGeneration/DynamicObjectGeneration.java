package object_gen.ServersideObjectGeneration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import code.ColumnFamily;
import code.ColumnfamilyColumns;
import code.Keyspace;

import com.google.common.base.CharMatcher;
import com.impetus.client.cassandra.common.CassandraConstants;
import com.impetus.client.mongodb.MongoDBClient;
import com.impetus.kundera.client.Client;

import configfamily.Entityclass_New;

public class DynamicObjectGeneration {
	String CLASSES_PATH= "";
	String CLASSES_OUTPUT="";
	String packge="";
	
	
	public void generateObject(String data_store,String Host, String Port, String database , String table, String className, String packageName,String isRelationship, String relationshipType, String 
			attributeName,String relationshipTable,String relationshipClass,
			String relationshipDATA_STORE,String relationshipHost, String relationshipPort,String OUTPUT_PACKAGE) { 
		String CLASSES_PATH= System.getProperty("user.dir");
	
		File node = new File(CLASSES_PATH);
        if (!node.exists()) 
    		node.mkdir();
		String CLASSES_OUTPUT= OUTPUT_PACKAGE;
		node = new File(CLASSES_OUTPUT);
        if (!node.exists()) 
    		node.mkdir();
		
		try{
			if (data_store.toLowerCase().equals("mongodb")){
				 node = new File(CLASSES_PATH+"/"+packageName);
		        if (!node.exists()) 
		    		node.mkdir();
		        GettingMetadata_New gmd= new GettingMetadata_New();
			      gmd.generateMongoEntity(Host, Port, database, table,className, packageName, CLASSES_PATH, CLASSES_OUTPUT,isRelationship,relationshipType,
			    		  attributeName,relationshipTable,relationshipClass,relationshipDATA_STORE,relationshipHost,relationshipPort);
			}
			else if (data_store.toLowerCase().equals("cassandra")) {
				 node = new File(CLASSES_PATH+"/"+packageName);
		        if (!node.exists()) 
		    		node.mkdir();
		        GettingMetadata_New gmd= new GettingMetadata_New();
		        gmd.generateCassandraEntity(Host, Port, database, table,className, packageName, CLASSES_PATH, CLASSES_OUTPUT,isRelationship,relationshipType,
		        		attributeName,relationshipTable,relationshipClass,relationshipDATA_STORE,relationshipHost,relationshipPort);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void generateObject(String data_store,String Host, String Port, String database , String packageName,String OUTPUT_PACKAGE){
		try{
			
			String CLASSES_PATH= System.getProperty("user.dir");
		
			File node = new File(CLASSES_PATH);
	        if (!node.exists()) 
	    		node.mkdir();
			String CLASSES_OUTPUT= OUTPUT_PACKAGE;
			node = new File(CLASSES_OUTPUT);
	        if (!node.exists()) 
	    		node.mkdir();
			
			
				if (data_store.toLowerCase().equals("mongodb")){
					 node = new File(CLASSES_PATH+"/"+packageName);
			        if (!node.exists()) 
			    		node.mkdir();
			        GettingMetadata_New gmd= new GettingMetadata_New();
			        					
			      List<String>  collections = new ArrayList<String>();
			      HashMap<String, String> connnectionProperties = new HashMap<String, String>();
			      connnectionProperties.put("kundera.nodes", Host);
			    	connnectionProperties.put("kundera.port", Port);
			    	connnectionProperties.put("kundera.keyspace", database);
			    	EntityManagerFactory emf = Persistence.createEntityManagerFactory("mongodatabase_pu",connnectionProperties);
			        
			        EntityManager em = emf.createEntityManager();
			        Map<String, Client<Query>> clients = (Map<String, Client<Query>>) em.getDelegate();
			        Client client = clients.get("mongodatabase_pu");
			        String jScript = "db.getCollectionNames().length";
			       
			        Integer noOfCollections= (int) Double.parseDouble(((MongoDBClient)client).executeScript(jScript).toString());
			        for(int i=0;i<noOfCollections;i++){
				         jScript = "db.getCollectionNames()["+i+"]";
				        String collectionName = ((MongoDBClient)client).executeScript(jScript).toString();
				        if(collectionName.equals("system.indexes")||collectionName.equals("system.users"))
				        	continue;
				        else
				        
				       gmd.generateMongoEntity(Host, Port, database, collectionName, collectionName, packageName, CLASSES_PATH, CLASSES_OUTPUT, "false",
				    		   "", "", "", "", "", "", "");
			        }
				     
				}
				else if (data_store.toLowerCase().equals("cassandra")) {
					 node = new File(CLASSES_PATH+"/"+packageName);
			        if (!node.exists()) 
			    		node.mkdir();
			        
			        GettingMetadata_New gmd= new GettingMetadata_New();
			        HashMap<String, String> connnectionProperties = new HashMap<String, String>();
			    	 
			    	// Set your properties as you like:
			    	connnectionProperties.put("kundera.nodes", Host);
			    	connnectionProperties.put("kundera.port", Port);
			    	EntityManagerFactory emf = Persistence.createEntityManagerFactory("cassandra_pu",connnectionProperties);
				        
				        EntityManager em = emf.createEntityManager();
				        Query q1 = em.createQuery("select q from ColumnFamily q where q.key.keyspace_name= "+database);
			            List<ColumnFamily> result=q1.getResultList();
			            List<String> colfList  =new ArrayList<String>();
			        	
			        	for (ColumnFamily q:result){
			        		
			        		colfList.add(q.getKey().getColumnfamily_name());
			        	}
				        
			        	
				        for(int i=0;i<colfList.size();i++){
				        	
				        	gmd.generateCassandraEntity(Host, Port, database, colfList.get(i), colfList.get(i), packageName, CLASSES_PATH, OUTPUT_PACKAGE, "false",
				        			"","", "","","", "", "");
				        	
				        }
			        
			 
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}
		
		
		
	}
	
	
	}
