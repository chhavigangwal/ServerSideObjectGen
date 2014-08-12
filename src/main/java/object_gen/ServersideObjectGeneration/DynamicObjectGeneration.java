package object_gen.ServersideObjectGeneration;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import code.ColumnFamily;

import com.impetus.client.mongodb.MongoDBClient;
import com.impetus.kundera.client.Client;

//import configfamily.Entityclass_New;

public class DynamicObjectGeneration {
    String CLASSES_PATH = "";

    String CLASSES_OUTPUT = "";

    String packge = "";

    public void generateObjectForTable(String data_store, String Host, String Port, String setSchema, String database,
        String table, String className, String packageName, String isRelationship, String relationshipType,
        String joinColumnAttributeName, String inverseJoinColumnAttributeName, String joinTableName,
        String relationshipTable, String relationshipClass, String relationshipDATA_STORE, String relationshipHost,
        String relationshipPort, String relationshipPackage, String OUTPUT_PACKAGE) {
        // String CLASSES_PATH = System.getProperty("user.dir");
        String classPath = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        int endIndex = classPath.indexOf("/src") != -1 ? classPath.indexOf("/src") : classPath
            .indexOf("/ServerSideObjectGen-0.0.1-jar-with-dependencies.jar");
        String path = classPath.substring(0, endIndex);
        String CLASSES_PATH = path;

        File node = new File(CLASSES_PATH);
        if (!node.exists()) {
            node.mkdir();
        }
        String CLASSES_OUTPUT = OUTPUT_PACKAGE;
        node = new File(CLASSES_OUTPUT);
        if (!node.exists()) {
            node.mkdir();
        }

        try {
            if (data_store.toLowerCase().equals("mongodb")) {
                // node = new File(CLASSES_PATH + "/" + packageName);
                // if (!node.exists())
                // {
                // node.mkdir();
                // }
                node = new File(CLASSES_PATH + "/" + packageName + "/" + database);
                if (!node.exists()) {
                    node.mkdirs();
                }
                if (!relationshipPackage.equals(packageName) && !relationshipPackage.isEmpty()) {
                    // node = new File(CLASSES_PATH + "/" +
                    // relationshipPackage);
                    // if (!node.exists())
                    // {
                    // node.mkdir();
                    // }
                    node = new File(CLASSES_PATH + "/" + relationshipPackage + "/" + database);
                    if (!node.exists()) {
                        node.mkdirs();
                    }

                }

                GettingMetadata_New gmd = new GettingMetadata_New();
                gmd.generateMongoEntity(data_store, Host, Port, setSchema, database, table, className, packageName,
                    CLASSES_PATH, CLASSES_OUTPUT, isRelationship, relationshipType, joinColumnAttributeName,
                    inverseJoinColumnAttributeName, joinTableName, relationshipTable, relationshipClass,
                    relationshipDATA_STORE, relationshipHost, relationshipPort, relationshipPackage);
            } else if (data_store.toLowerCase().equals("cassandra")) {
                // node = new File(CLASSES_PATH + "/" + packageName);
                // if (!node.exists())
                // {
                // node.mkdir();
                // }
                node = new File(CLASSES_PATH + "/" + packageName + "/" + database);
                if (!node.exists()) {
                    node.mkdirs();
                }

                if (!relationshipPackage.equals(packageName) && !relationshipPackage.isEmpty()) {
                    // node = new File(CLASSES_PATH + "/" +
                    // relationshipPackage);
                    // if (!node.exists())
                    // {
                    // node.mkdir();
                    // }
                    node = new File(CLASSES_PATH + "/" + relationshipPackage + "/" + database);
                    if (!node.exists()) {
                        node.mkdirs();
                    }

                }
                GettingMetadata_New gmd = new GettingMetadata_New();
                gmd.generateCassandraEntity(data_store, Host, Port, setSchema, database, table, className, packageName,
                    CLASSES_PATH, CLASSES_OUTPUT, isRelationship, relationshipType, joinColumnAttributeName,
                    inverseJoinColumnAttributeName, joinTableName, relationshipTable, relationshipClass,
                    relationshipDATA_STORE, relationshipHost, relationshipPort, relationshipPackage);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void generateObjectsForDatabase(String data_store, String Host, String Port, String setSchema,
        String database, String packageName, String OUTPUT_PACKAGE) {
        try {
            // String CLASSES_PATH = System.getProperty("user.dir");
            String classPath = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
            int endIndex =
                classPath.indexOf("/src") != -1 ? classPath.indexOf("/src") : classPath
                    .indexOf("/ServerSideObjectGen-0.0.1-jar-with-dependencies.jar");
            String path = classPath.substring(0, endIndex);
            String CLASSES_PATH = path;
            File node = new File(CLASSES_PATH);
            if (!node.exists()) {
                node.mkdir();
            }
            String CLASSES_OUTPUT = OUTPUT_PACKAGE;
            node = new File(CLASSES_OUTPUT);
            if (!node.exists()) {
                node.mkdir();
            }

            if (data_store.toLowerCase().equals("mongodb")) {
                // node = new File(CLASSES_PATH + "/" + packageName);
                // if (!node.exists())
                // {
                // node.mkdir();
                // }
                node = new File(CLASSES_PATH + "/" + packageName + "/" + database);
                if (!node.exists()) {
                    node.mkdirs();
                }
                GettingMetadata_New gmd = new GettingMetadata_New();

                List<String> collections = new ArrayList<String>();
                HashMap<String, String> connnectionProperties = new HashMap<String, String>();
                connnectionProperties.put("kundera.nodes", Host);
                connnectionProperties.put("kundera.port", Port);
                connnectionProperties.put("kundera.keyspace", database);
                EntityManagerFactory emf =
                    Persistence.createEntityManagerFactory("mongodatabase_pu", connnectionProperties);

                EntityManager em = emf.createEntityManager();
                Map<String, Client<Query>> clients = (Map<String, Client<Query>>) em.getDelegate();
                Client client = clients.get("mongodatabase_pu");
                String jScript = "db.getCollectionNames().length";

                Integer noOfCollections =
                    (int) Double.parseDouble(((MongoDBClient) client).executeScript(jScript).toString());
                for (int i = 0; i < noOfCollections; i++) {
                    jScript = "db.getCollectionNames()[" + i + "]";
                    String collectionName = ((MongoDBClient) client).executeScript(jScript).toString();
                    if (collectionName.equals("system.indexes") || collectionName.equals("system.users")) {
                        continue;
                    } else {
                        gmd.generateMongoEntity(data_store, Host, Port, setSchema, database, collectionName, database
                            + collectionName, packageName, CLASSES_PATH, CLASSES_OUTPUT, "false", "", "", "", "", "",
                            "", "", "", "", "");
                    }
                }
            } else if (data_store.toLowerCase().equals("cassandra")) {
                // node = new File(CLASSES_PATH + "/" + packageName);
                // if (!node.exists())
                // {
                // node.mkdir();
                // }
                node = new File(CLASSES_PATH + "/" + packageName + "/" + database);
                if (!node.exists()) {
                    node.mkdirs();
                }
                GettingMetadata_New gmd = new GettingMetadata_New();
                HashMap<String, String> connnectionProperties = new HashMap<String, String>();

                // Set your properties as you like:
                connnectionProperties.put("kundera.nodes", Host);
                connnectionProperties.put("kundera.port", Port);
                EntityManagerFactory emf =
                    Persistence.createEntityManagerFactory("cassandra_pu", connnectionProperties);

                EntityManager em = emf.createEntityManager();
                Query q1 = em.createQuery("select q from ColumnFamily q where q.key.keyspace_name= " + database);
                List<ColumnFamily> result = q1.getResultList();
                List<String> colfList = new ArrayList<String>();

                for (ColumnFamily q : result) {
                    colfList.add(q.getKey().getColumnfamily_name());
                }

                for (int i = 0; i < colfList.size(); i++) {
                    gmd.generateCassandraEntity(data_store, Host, Port, setSchema, database, colfList.get(i), database
                        + colfList.get(i), packageName, CLASSES_PATH, OUTPUT_PACKAGE, "false", "", "", "", "", "", "",
                        "", "", "", "");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
