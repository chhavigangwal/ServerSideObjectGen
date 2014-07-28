package object_gen.ServersideObjectGeneration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import code.ColumnFamily;
import code.ColumnfamilyColumns;

import com.google.common.base.CharMatcher;

import configfamily.BuildCollectionEntity_New;

import configfamily.BuildSubDocEntity_New;
import configfamily.Entityclass_New;

public class GettingMetadata_New {
	
	

	
	public void generateCassandraEntity(String Host , String Port, String database, String table ,String className,String packge, String classes_path, String classes_output, String isRelatioship, String  relationshipType, String attributeName,
			String relationshipTable,String relationshipClass,String relationshipDATA_STORE, String relationshipHost, String relationshipPort){
		
		try{
			
			String columnfamilyName= table;
			
			String keyspaceName= database;
			String CLASSES_PATH =classes_path;
			
			String CLASSES_OUTPUT = classes_output;
			
			HashMap<String, String> connnectionProperties = new HashMap<String, String>();
	   	 
	   //  Set your properties as you like:
	    connnectionProperties.put("kundera.nodes",Host);
	    connnectionProperties.put("kundera.port",Port );
	  
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("cassandra_pu",connnectionProperties);
		EntityManager em = emf.createEntityManager();
		//getting the Cassandra Schema Information
		
		//Query prim5 = em.createQuery("select q from Keyspace q");
		//List<Keyspace> result5=prim5.getResultList();
		//System.out.println("keyspace_name : "+result5.get(0).getKeyspace_name());
		
		System.out.println("query : select q from ColumnFamily q where q.key.keyspace_name= "+keyspaceName+" and q.key.columnfamily_name = "+columnfamilyName);
		Query prim = em.createQuery("select q from ColumnFamily q where q.key.keyspace_name= "+keyspaceName+" and q.key.columnfamily_name = "+columnfamilyName);
		List<ColumnFamily> result4=prim.getResultList();

	    

		Query q4= em.createQuery("select q from ColumnfamilyColumns q where q.key.keyspace_name=  "+keyspaceName+" and q.key.columnfamily_name = "+columnfamilyName);
		List<ColumnfamilyColumns> result1=q4.getResultList();
		
		List<String> pField=new ArrayList<String>();
		List<String> pType=new ArrayList<String>();
		for (ColumnFamily q:result4){
			/*System.out.print(q.getKey_aliases());

			System.out.print(q.getKey_validator());
			System.out.print(q.getColumn_aliases());
			System.out.println(q.getComparator());
*/
			String s2 = new String();
			String s3= new String();

			if (q.getColumn_aliases().equals("[]")) {
	             
				s2 = q.getKey_aliases().replaceAll("[]\"]",
						"");
				s2 = CharMatcher.is('[').removeFrom(s2);

				pField.add(s2);
				s3=q.getKey_validator().replaceAll("org.apache.cassandra.db.marshal.", "");
				pType.add(s3);

			} else {
				s2 = q.getKey_aliases().replaceAll("[]\"]",
						"");
				s2 = CharMatcher.is('[').removeFrom(s2);
				pField.add(s2);
				s3=q.getKey_validator().replaceAll("org.apache.cassandra.db.marshal.", "");
				pType.add(s3);
				String colAlias =q.getColumn_aliases();
				s2 = colAlias.replaceAll("[]\"]", "");
				s2 = CharMatcher.is('[').removeFrom(s2);

				if (s2.contains(",")) {
					for (String retval : s2.split(",")) {

						pField.add(retval);
					}
				} else
					pField.add(s2);
	            
				if(q.getComparator().contains("org.apache.cassandra.db.marshal.CompositeType")){
				s3=q.getComparator().replaceAll("org.apache.cassandra.db.marshal.CompositeType","");
				s3 = CharMatcher.is(')').removeFrom(s3);
				s3 = CharMatcher.is('(').removeFrom(s3);
				s3 = s3.replaceAll("org.apache.cassandra.db.marshal.", "");
				
				System.out.println("this is Pfield type " +s3);
				int i=1;
				for (String  pdata: s3.split(",")) {
					if(i==pField.size())
						break;
					pType.add(pdata);
					i++;
				}
				}
				else{
					s3 = s3.replaceAll("org.apache.cassandra.db.marshal.", "");
					pType.add(s3);
					
				}
				
				
			}
		}
/*
		for(int i=0;i<pField.size();i++){
			System.out.println(pField.get(i));
			System.out.println(pType.get(i));
		}
	*/
		List<String> columns=new ArrayList<String>();
		List<String> colData = new ArrayList<String>();
		for (ColumnfamilyColumns q:result1){
			columns.add(q.getKey().getColumn_name());
			String s= new String();
			s=q.getValidator().replaceAll("org.apache.cassandra.db.marshal.", "");
			colData.add(s);


		}
/*
		for(int i=0;i<columns.size();i++){
			System.out.println(columns.get(i));
			System.out.println(colData.get(i));
		}

*/
		


		List<String> paType = new ArrayList<String>();
		List<String> caType = new ArrayList<String>();
	if(!pField.isEmpty()){
		for(int i=0;i<pType.size();i++)
		{
			if(pType.get(i).equals("UTF8Type")||pType.get(i).equals("InetAddressType"))
				paType.add("String");
			else if (pType.get(i).equals("Int32Type")) {
				paType.add("int");

			}
			else if (pType.get(i).equals("BooleanType")) {
				paType.add("boolean");

			}
			else if(pType.get(i).equals("UUIDType")||pType.get(i).equals("TimeUUIDType"))	
			{
				paType.add("UUID");
			}
			else if (pType.get(i).equals("DoubleType")) {
				paType.add("double");

			}
			else 
				paType.add("String");

		}
	}
	if(!columns.isEmpty()){

		for(int i=0;i<colData.size();i++){
			if(colData.get(i).equals("UTF8Type") || colData.get(i).equals("InetAddressType"))
				caType.add("String");
			else if (colData.get(i).equals("Int32Type")) {
				caType.add("int");

			}
			else if (colData.get(i).equals("BooleanType")) {
				caType.add("boolean");

			}
			else if(colData.get(i).equals("UUIDType") || colData.get(i).equals("TimeUUIDType"))	
			{
				caType.add("UUID");
			}
			else if (colData.get(i).equals("DoubleType")) {
				caType.add("double");

			}
			else
				caType.add("String");
		}
		for(int i=0;i<pField.size();i++){
			System.out.println(pField.get(i));
			System.out.println(paType.get(i));
		}
		//check for duplicate fields 
				for(int i=0;i<pField.size();i++){
		    		for(int j=0;j<columns.size();j++){
		    			
		    			if(pField.get(i).equals(columns.get(j))){
		    				
		    				columns.remove(j);
		    				caType.remove(j);
		    			}
		    			else 
						continue;	
						
		    		}
		    		
		    	}
		for(int i=0;i<columns.size();i++){
			System.out.println(columns.get(i));
			System.out.println(caType.get(i));
		}
	}


		Entityclass_New e =new Entityclass_New();
		
		e.filltable(table,CLASSES_PATH,className,packge);
		
		if(pField.size()==1){

			e.fillid(pField.get(0), paType.get(0),CLASSES_PATH,className,packge);

		}
		else{

			e.fillid("compkey", "Composite"+columnfamilyName.substring(0, 1).toUpperCase()+columnfamilyName.substring(1),CLASSES_PATH,className,packge);
			e.fillcompositeclass(columnfamilyName,keyspaceName,CLASSES_PATH,packge);
			for(int i=0;i<pField.size();i++)
				e.fillcolumn(pField.get(i), paType.get(i),"Composite"+columnfamilyName.substring(0, 1).toUpperCase()+columnfamilyName.substring(1),CLASSES_PATH,packge);
			FileWriter writer = new FileWriter(CLASSES_PATH+"/"+packge+"/Composite"+columnfamilyName.substring(0, 1).toUpperCase()+columnfamilyName.substring(1)+".java",true);
			writer.write("}");
			writer.close();

		}
		
		for(int i=0;i<columns.size();i++){
			if(columns.get(i).equals(attributeName))
				continue;
			else
			e.fillcolumn(columns.get(i), caType.get(i),className,CLASSES_PATH,packge);
		}
		
		if(isRelatioship.equals("true")){
		//	System.out.println(attributeName+relationshipType+relationshipClass);
			if(relationshipDATA_STORE.equals("cassandra"))
			generateCassandraEntity(relationshipHost, relationshipPort, database, relationshipTable, relationshipClass, packge, CLASSES_PATH, CLASSES_OUTPUT, "false", "", attributeName,
					"", "","","","");
			if(relationshipDATA_STORE.equals("mongodb"))
				generateMongoEntity(relationshipHost, relationshipPort, database, relationshipTable, relationshipClass, packge,
						CLASSES_PATH, CLASSES_OUTPUT, "false", "", attributeName, "", "","","","");
			e.fillRelationship(className, attributeName, relationshipType, relationshipClass, CLASSES_PATH, packge);
		}
		FileWriter writer = new FileWriter(CLASSES_PATH+"/"+packge+"/"+className+".java",true);
		writer.write("}");
		writer.close();
	      
		System.out.println("pfield.size "+pField.size());
		
		if(pField.size()!=1){
	    	   File sourceFile1  = new File(CLASSES_PATH+"/"+packge+"/Composite"+columnfamilyName.substring(0, 1).toUpperCase()+columnfamilyName.substring(1)+".java");

	    	    JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
			   	StandardJavaFileManager sjfm = compiler.getStandardFileManager(null, null, null);
			  // String classpath=System.getProperty("java.class.path");
			   	System.out.println("current path "+System.getProperty("user.dir"));
			   	String testpath =":"+CLASSES_PATH+"/lib/persistence-api-2.0.jar";
			   	testpath = testpath+":"+CLASSES_PATH+"/lib/jackson-annotations-2.4.1.jar";
			   	
			   	testpath =testpath+":"+CLASSES_OUTPUT;

			   List<String> optionList = new ArrayList<String>();
			   optionList.addAll(Arrays.asList("-classpath",testpath,"-d",CLASSES_OUTPUT));

			   Iterable fileObjects = sjfm.getJavaFileObjectsFromFiles(Arrays.asList(sourceFile1));
			   JavaCompiler.CompilationTask task = compiler.getTask(null, sjfm, null,optionList,null,fileObjects);
			   task.call();
			   sjfm.close();
				
			   
			      File sourceFile2   = new File(CLASSES_PATH+"/"+packge+"/"+className+".java");
			       //File sourceFile   = new File("Idbench"+colfList.get(k).substring(0, 1).toUpperCase()+colfList.get(k).substring(1)+".java");
				//File sourceFile   = new File("/home/ashish/Test.java");
			       if(sourceFile2.exists()){
			    	   System.out.println("file exists");
			    	   
			    	     	  
			       }
			 
			       
			     	System.out.println("current path "+System.getProperty("user.dir"));
			     
			       JavaCompiler compiler2 = ToolProvider.getSystemJavaCompiler();
			   	StandardJavaFileManager sjfm2 = compiler2.getStandardFileManager(null, null, null);
			   String classpath=System.getProperty("java.class.path");
			   String testpath2 =classpath+":"+CLASSES_PATH+"/lib/persistence-api-2.0.jar";
			   testpath2 = testpath2 +":"+CLASSES_PATH+"/lib/jackson-annotations-2.4.1.jar";
			  // String testpath2 =classpath+":"+CLASSES_PATH+"WEB-INF/lib/persistence-api-2.0.jar";
			 //  Class.forName("code.CompositeIdbench"+colfList.get(k).substring(0, 1).toUpperCase()+colfList.get(k).substring(1));
			   String testpath3 =testpath2+":"+CLASSES_OUTPUT;
			//   File javaFile =  new File("/home/ashish/Test.java");


			   List<String> optionList2 = new ArrayList<String>();
			   optionList2.addAll(Arrays.asList("-classpath",testpath3,"-d",CLASSES_OUTPUT));

			   Iterable fileObjects2 = sjfm2.getJavaFileObjectsFromFiles(Arrays.asList(sourceFile2));
			   JavaCompiler.CompilationTask task2 = compiler2.getTask(null, sjfm2, null,optionList2,null,fileObjects2);
			   task2.call();
			   sjfm2.close();
				
			   
	   
	       }
	       
		else{  
	       
	      File sourceFile   = new File(CLASSES_PATH+"/"+packge+"/"+className+".java");
	       
	       if(sourceFile.exists()){
	    	   System.out.println("file exists1234");
	    	   
	    	     	  
	       }
	       
	       
	     
	     System.out.println("Source file Name "+sourceFile.getName());
	       JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
	   	StandardJavaFileManager sjfm = compiler.getStandardFileManager(null, null, null);
	   String classpath=System.getProperty("java.class.path");
		String testpath =":"+CLASSES_PATH+"/lib/persistence-api-2.0.jar";
		  testpath = testpath+":"+CLASSES_PATH+"/lib/mongo-java-driver-2.9.1.jar";
		  testpath = testpath+":"+CLASSES_PATH+"/lib/jackson-annotations-2.4.1.jar";
		testpath =testpath+":"+CLASSES_OUTPUT;

	   List<String> optionList = new ArrayList<String>();
	   optionList.addAll(Arrays.asList("-classpath",testpath,"-d",CLASSES_OUTPUT));

	   Iterable fileObjects = sjfm.getJavaFileObjectsFromFiles(Arrays.asList(sourceFile));
	   JavaCompiler.CompilationTask task = compiler.getTask(null, sjfm, null,optionList,null,fileObjects);
	   task.call();
	   sjfm.close();
	  
		
	   	

		}
		
		
		e.createPersistence(Host, Port, database, packge, className, CLASSES_OUTPUT);
		
		
		e.createJar(CLASSES_OUTPUT, packge);
		//
			}
			catch(Exception e){
				e.printStackTrace();
				
			}
		
		
		
		
		
	}
	

	public void generateMongoEntity(String Host , String Port, String database, String table ,String className,
			String packge, String classes_path, String classes_output, String isRelatioship, String  relationshipType, 
			String attributeName, String relationshipTable,String relationshipClass,
			String relationshipDATA_STORE, String relationshipHost, String relationshipPort){
		try{
			
				
				//Getting collection schema information 
			 Process p;
	     	// /ilabs/apps/mongodb-linux-x86_64-2.4.4/bin/
	     	 System.out.println(System.getenv().get("MONGO_HOME"));
	     	 String MONGO_HOME =System.getenv().get("MONGO_HOME");
	     //	String MONGO_HOME ="/ilabs/apps/mongodb-linux-x86_64-2.4.4/bin/";
	     	 System.out.println("table name "+table);
			       //root/software/mongodb-linux/mongodb-linux-x86_64-2.0.4/
		        String[] params = {MONGO_HOME+"/mongo",
						           "-host",
						           Host,
						           "-port",
						           Port,
						           database,
						           "--eval",
						           "var collection = \""+table+"\"",
						          classes_path+"/conf/variety.js",
									};
		       
		        p = Runtime.getRuntime().exec(params);
				p.waitFor();
				BufferedReader reader = new BufferedReader(new InputStreamReader(
						p.getInputStream()));

			

				String line = reader.readLine();
			
				List<String> colName = new ArrayList<String>();
				List<String> colType = new ArrayList<String>();
				
			int j=0;
				while (line != null) {
					if(j==0||j==1){
						
						line = reader.readLine();
						j++;
					}else
					{
						
						String[] valueType=line.split(":");
						
						colName.add(valueType[0].replace(" ", ""));
						colType.add(valueType[1]);
					System.out.println(valueType[0].replace(" ", "")+":::"+valueType[1]);
					line = reader.readLine();
					}
				}
	           
				for (int var=0;var<colName.size();var++){
					
					String colValue= colName.get(var);
					int count=var+1;
					for (int k=var+1;k<colName.size();k++){
						if(colName.get(k).contains(colValue+".")){
						   String countValue= colName.get(k);
						   colName.set(k, colName.get(count));
						   colName.set(count, countValue);
						   String countType=colType.get(k);
						   colType.set(k, colType.get(count));
						   colType.set(count, countType);
						   count++;
							
						}
						else{
							continue;
						}
						
						
					}
					
					
				}
				//MongoMetaData mmd = new MongoMetaData(colName, colType);
				
				///Dynamic Object generation
				String collectionName= table;
				String databaseName= database;
				String CLASSES_PATH = classes_path;
				
				String CLASSES_OUTPUT = classes_output;
				j=0;
				BuildCollectionEntity_New bce= new BuildCollectionEntity_New();
				if(!colName.isEmpty()) {
					
				while(j<colType.size()){
					
					if(j==0){
						//BuildCollectionEntity_New bce= new BuildCollectionEntity_New();
						bce.filltable( collectionName,CLASSES_PATH, className,packge);
						bce.fillid(colName.get(j), colType.get(j), CLASSES_PATH,className, packge);
						j++;
					}
					else{
						if(colType.get(j).equals("Object")){
							
							
							 String ftype= collectionName.substring(0, 1).toUpperCase()+collectionName.substring(1)+colName.get(j).substring(0, 1).toUpperCase()+colName.get(j).substring(1); 
							//	BuildCollectionEntity_New bce= new BuildCollectionEntity_New();
								bce.fillObjectcolumn(colName.get(j), ftype, CLASSES_PATH,className,packge);
							int k;
							for(k=j+1;k<colType.size();k++)
							if(colName.get(k).contains(colName.get(j)+".")){
							
								//j=k;
								System.out.println("j:"+j);
								continue;
							}
								else{
									//j=k;
									System.out.println("k="+k);
									break;
								}
						     BuildSubDocEntity_New bsde= new BuildSubDocEntity_New();
						     List<String> preColumnList= new ArrayList<String>();
						   bsde.buildSubDocEntity(databaseName, collectionName, colName, colType, j, k, preColumnList, CLASSES_PATH,CLASSES_OUTPUT, packge);
						     //  bsde.buildSubDocEntity(databaseName,collectionName,colName, colType, j, k,preColumnList);
							 j=k;
							 
							}
						else{
							
							if(isRelatioship.equals("false")){
								if(colName.get(j).equals(attributeName)){
									j++;
									continue;
								}
									
								else{
									bce.fillcolumn(colName.get(j), colType.get(j),className, CLASSES_PATH, packge);
									System.out.println("hi");
								j++;
								}
							}
							else{
							bce.fillcolumn(colName.get(j), colType.get(j),className, CLASSES_PATH, packge);
							j++;
							}
						}
								
						}
						
						
					}
				if(isRelatioship.equals("true")){
					//	System.out.println(attributeName+relationshipType+relationshipClass);
						if(relationshipDATA_STORE.equals("mongodb"))
						generateMongoEntity(relationshipHost, relationshipPort, databaseName, relationshipTable, relationshipClass, packge,
								CLASSES_PATH, CLASSES_OUTPUT, "false", "", attributeName, "", "","","","");
						if(relationshipDATA_STORE.equals("cassandra"))
							generateCassandraEntity(relationshipHost, relationshipPort, database, relationshipTable, relationshipClass, packge, CLASSES_PATH, CLASSES_OUTPUT, "false", "", attributeName,
									"", "","","","");
						//BuildCollectionEntity_New bce= new BuildCollectionEntity_New();
						bce.fillRelationship(className, attributeName, relationshipType, relationshipClass, CLASSES_PATH, packge);
					}
				FileWriter writer = new FileWriter(CLASSES_PATH+"/"+packge+"/"+ className+".java",true);
				writer.write("}");
				writer.close();	
	            }
				
				
				//Compiling the entity definition file
				 File sourceFile   = new File(CLASSES_PATH+"/"+packge+"/"+className+".java");
			       
			       JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
			   	StandardJavaFileManager sjfm = compiler.getStandardFileManager(null, null, null);
			   String classpath=System.getProperty("java.class.path");
			   String testpath =":"+CLASSES_PATH+"/lib/persistence-api-2.0.jar";
			 //  /home/ashish/.m2/repository/org/mongodb/mongo-java-driver/2.9.1/mongo-java-driver-2.9.1.jar
			   testpath = testpath+":"+CLASSES_PATH+"/lib/mongo-java-driver-2.9.1.jar";
			   testpath = testpath+":"+CLASSES_PATH+"/lib/jackson-annotations-2.4.1.jar";
			   testpath =testpath+":"+CLASSES_OUTPUT;

			   List<String> optionList = new ArrayList<String>();
			   optionList.addAll(Arrays.asList("-classpath",testpath,"-d",CLASSES_OUTPUT));

			   Iterable fileObjects = sjfm.getJavaFileObjectsFromFiles(Arrays.asList(sourceFile));
			   JavaCompiler.CompilationTask task = compiler.getTask(null, sjfm, null,optionList,null,fileObjects);
			   task.call();
			   sjfm.close();
				
			//   BuildCollectionEntity_New bce= new BuildCollectionEntity_New();
				bce.createPersistenceMongo(Host, Port, databaseName, packge, className, CLASSES_OUTPUT);
				bce.createJar(CLASSES_OUTPUT, packge);
			   
			 
		}catch(Exception e){
			e.printStackTrace();
			
		}
	}

}