package object_gen.ServersideObjectGeneration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
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

//import configfamily.BuildCollectionEntity_New;
import configfamily.BuildCollectionEntity_New;
import configfamily.BuildSubDocEntity_New;
import configfamily.Entityclass_New;

//import configfamily.BuildSubDocEntity_New;
//import configfamily.Entityclass_New;

public class GettingMetadata_New
{

    public void generateCassandraEntity(String DATA_STORE, String Host, String Port, String setSchema, String database,
            String table, String className, String packge, String classes_path, String classes_output,
            String isRelatioship, String relationshipType, String joinColumnAttributeName,
            String inverseJoinColumnAttributeName, String joinTableName, String relationshipTable,
            String relationshipClass, String relationshipDATA_STORE, String relationshipHost, String relationshipPort,
            String relationshipPackage)
    {
        try
        {
            String columnfamilyName = table;

            String keyspaceName = database;
            String CLASSES_PATH = classes_path;

            String CLASSES_OUTPUT = classes_output;

            HashMap<String, String> connnectionProperties = new HashMap<String, String>();

            // Set your properties as you like:
            connnectionProperties.put("kundera.nodes", Host);
            connnectionProperties.put("kundera.port", Port);

            EntityManagerFactory emf = Persistence.createEntityManagerFactory("cassandra_pu", connnectionProperties);
            EntityManager em = emf.createEntityManager();
            // getting the Cassandra Schema Information

            System.out.println("query : select q from ColumnFamily q where q.key.keyspace_name= " + keyspaceName
                    + " and q.key.columnfamily_name = " + columnfamilyName);
            Query prim = em.createQuery("select q from ColumnFamily q where q.key.keyspace_name= " + keyspaceName
                    + " and q.key.columnfamily_name = " + columnfamilyName);
            List<ColumnFamily> result4 = prim.getResultList();

            Query q4 = em.createQuery("select q from ColumnfamilyColumns q where q.key.keyspace_name=  " + keyspaceName
                    + " and q.key.columnfamily_name = " + columnfamilyName);
            List<ColumnfamilyColumns> result1 = q4.getResultList();

            List<String> pField = new ArrayList<String>();
            List<String> pType = new ArrayList<String>();
            for (ColumnFamily q : result4)
            {
                String s2 = new String();
                String s3 = new String();

                if (q.getColumn_aliases().equals("[]"))
                {

                    s2 = q.getKey_aliases().replaceAll("[]\"]", "");
                    s2 = CharMatcher.is('[').removeFrom(s2);

                    pField.add(s2);
                    s3 = q.getKey_validator().replaceAll("org.apache.cassandra.db.marshal.", "");
                    pType.add(s3);

                }
                else
                {
                    s2 = q.getKey_aliases().replaceAll("[]\"]", "");
                    s2 = CharMatcher.is('[').removeFrom(s2);
                    pField.add(s2);
                    s3 = q.getKey_validator().replaceAll("org.apache.cassandra.db.marshal.", "");
                    pType.add(s3);
                    String colAlias = q.getColumn_aliases();
                    s2 = colAlias.replaceAll("[]\"]", "");
                    s2 = CharMatcher.is('[').removeFrom(s2);

                    if (s2.contains(","))
                    {
                        for (String retval : s2.split(","))
                        {

                            pField.add(retval);
                        }
                    }
                    else
                    {
                        pField.add(s2);
                    }

                    if (q.getComparator().contains("org.apache.cassandra.db.marshal.CompositeType"))
                    {
                        s3 = q.getComparator().replaceAll("org.apache.cassandra.db.marshal.CompositeType", "");
                        s3 = CharMatcher.is(')').removeFrom(s3);
                        s3 = CharMatcher.is('(').removeFrom(s3);
                        s3 = s3.replaceAll("org.apache.cassandra.db.marshal.", "");

                        int i = 1;
                        for (String pdata : s3.split(","))
                        {
                            if (i == pField.size())
                            {
                                break;
                            }
                            pType.add(pdata);
                            i++;
                        }
                    }
                    else
                    {
                        s3 = s3.replaceAll("org.apache.cassandra.db.marshal.", "");
                        pType.add(s3);
                    }

                }
            }

            List<String> columns = new ArrayList<String>();
            List<String> colData = new ArrayList<String>();
            for (ColumnfamilyColumns q : result1)
            {
                columns.add(q.getKey().getColumn_name());
                String s = new String();
                s = q.getValidator().replaceAll("org.apache.cassandra.db.marshal.", "");
                colData.add(s);
            }

            List<String> paType = new ArrayList<String>();
            List<String> caType = new ArrayList<String>();
            if (!pField.isEmpty())
            {
                for (int i = 0; i < pType.size(); i++)
                {
                    if (pType.get(i).equals("UTF8Type") || pType.get(i).equals("InetAddressType"))
                    {
                        paType.add("String");
                    }
                    else if (pType.get(i).equals("Int32Type"))
                    {
                        paType.add("int");
                    }
                    else if (pType.get(i).equals("BooleanType"))
                    {
                        paType.add("boolean");
                    }
                    else if (pType.get(i).equals("UUIDType") || pType.get(i).equals("TimeUUIDType"))
                    {
                        paType.add("UUID");
                    }
                    else if (pType.get(i).equals("DoubleType"))
                    {
                        paType.add("double");
                    }
                    else
                    {
                        paType.add("String");
                    }
                }
            }
            if (!columns.isEmpty())
            {
                for (int i = 0; i < colData.size(); i++)
                {
                    if (colData.get(i).equals("UTF8Type") || colData.get(i).equals("InetAddressType"))
                    {
                        caType.add("String");
                    }
                    else if (colData.get(i).equals("Int32Type"))
                    {
                        caType.add("int");
                    }
                    else if (colData.get(i).equals("BooleanType"))
                    {
                        caType.add("boolean");
                    }
                    else if (colData.get(i).equals("UUIDType") || colData.get(i).equals("TimeUUIDType"))
                    {
                        caType.add("UUID");
                    }
                    else if (colData.get(i).equals("DoubleType"))
                    {
                        caType.add("double");
                    }
                    else
                    {
                        caType.add("String");
                    }
                }
                // check for duplicate fields
                for (int i = 0; i < pField.size(); i++)
                {
                    for (int j = 0; j < columns.size(); j++)
                    {
                        if (pField.get(i).equals(columns.get(j)))
                        {
                            columns.remove(j);
                            caType.remove(j);
                        }
                        else
                        {
                            continue;
                        }
                    }
                }
            }

            Entityclass_New e = new Entityclass_New();

            e.filltable(setSchema, database, table, CLASSES_PATH, className, packge, relationshipPackage);

            if (pField.size() == 1)
            {
                e.fillid(database, pField.get(0), paType.get(0), CLASSES_PATH, className, packge);
            }
            else
            {

                e.fillid(database, "compkey", "Composite" + columnfamilyName.substring(0, 1).toUpperCase()
                        + columnfamilyName.substring(1), CLASSES_PATH, className, packge);
                e.fillcompositeclass(database, columnfamilyName, keyspaceName, CLASSES_PATH, packge);
                for (int i = 0; i < pField.size(); i++)
                {
                    e.fillcolumn(database, pField.get(i), paType.get(i), "Composite"
                            + columnfamilyName.substring(0, 1).toUpperCase() + columnfamilyName.substring(1),
                            CLASSES_PATH, packge);
                }
                FileWriter writer = new FileWriter(CLASSES_PATH + "/" + packge + "/" + database + "/Composite"
                        + columnfamilyName.substring(0, 1).toUpperCase() + columnfamilyName.substring(1) + ".java",
                        true);
                writer.write("}");
                writer.close();
            }

            for (int i = 0; i < columns.size(); i++)
            {
                if (columns.get(i).equals(joinColumnAttributeName))
                {
                    continue;
                }
                else
                {
                    e.fillcolumn(database, columns.get(i), caType.get(i), className, CLASSES_PATH, packge);
                }
            }

            if (isRelatioship.equals("true"))
            {
                if (relationshipDATA_STORE.equals("cassandra"))
                {
                    generateCassandraEntity(DATA_STORE, relationshipHost, relationshipPort, setSchema, database,
                            relationshipTable, relationshipClass, relationshipPackage, CLASSES_PATH, CLASSES_OUTPUT,
                            "false", "", joinColumnAttributeName, inverseJoinColumnAttributeName, joinTableName, "",
                            "", "", "", "", "");
                }
                if (relationshipDATA_STORE.equals("mongodb"))
                {
                    generateMongoEntity(DATA_STORE, relationshipHost, relationshipPort, setSchema, database,
                            relationshipTable, relationshipClass, relationshipPackage, CLASSES_PATH, CLASSES_OUTPUT,
                            "false", "", joinColumnAttributeName, inverseJoinColumnAttributeName, joinTableName, "",
                            "", "", "", "", "");
                }
                ServerSideObjectGenerationUtils.fillRelationship(className, joinColumnAttributeName,
                        inverseJoinColumnAttributeName, joinTableName, relationshipType, relationshipClass,
                        CLASSES_PATH, packge, database);
            }
            FileWriter writer = new FileWriter(
                    CLASSES_PATH + "/" + packge + "/" + database + "/" + className + ".java", true);
            writer.write("}");
            writer.close();

            System.out.println("pfield.size " + pField.size());

            if (pField.size() != 1)
            {
                File sourceFile1 = new File(CLASSES_PATH + "/" + packge + "/" + database + "/Composite"
                        + columnfamilyName.substring(0, 1).toUpperCase() + columnfamilyName.substring(1) + ".java");

                JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
                StandardJavaFileManager sjfm = compiler.getStandardFileManager(null, null, null);
                // String classpath=System.getProperty("java.class.path");
                System.out.println("current path " + System.getProperty("user.dir"));
                String testpath = ":" + CLASSES_PATH + "/lib/persistence-api-2.0.jar";
                testpath = testpath + ":" + CLASSES_PATH + "/lib/jackson-annotations-2.4.1.jar";

                testpath = testpath + ":" + CLASSES_OUTPUT;

                List<String> optionList = new ArrayList<String>();
                optionList.addAll(Arrays.asList("-classpath", testpath, "-d", CLASSES_OUTPUT));

                Iterable fileObjects = sjfm.getJavaFileObjectsFromFiles(Arrays.asList(sourceFile1));
                JavaCompiler.CompilationTask task = compiler.getTask(null, sjfm, null, optionList, null, fileObjects);
                task.call();
                sjfm.close();

                File sourceFile2 = new File(CLASSES_PATH + "/" + packge + "/" + database + "/" + className + ".java");

                JavaCompiler compiler2 = ToolProvider.getSystemJavaCompiler();
                StandardJavaFileManager sjfm2 = compiler2.getStandardFileManager(null, null, null);
                String classpath = System.getProperty("java.class.path");
                String testpath2 = classpath + ":" + CLASSES_PATH + "/lib/persistence-api-2.0.jar";
                testpath2 = testpath2 + ":" + CLASSES_PATH + "/lib/jackson-annotations-2.4.1.jar";
                String testpath3 = testpath2 + ":" + CLASSES_OUTPUT;

                List<String> optionList2 = new ArrayList<String>();
                optionList2.addAll(Arrays.asList("-classpath", testpath3, "-d", CLASSES_OUTPUT));

                Iterable fileObjects2 = sjfm2.getJavaFileObjectsFromFiles(Arrays.asList(sourceFile2));
                JavaCompiler.CompilationTask task2 = compiler2.getTask(null, sjfm2, null, optionList2, null,
                        fileObjects2);
                task2.call();
                sjfm2.close();
            }
            else
            {
                File sourceFile = new File(CLASSES_PATH + "/" + packge + "/" + database + "/" + className + ".java");

                JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
                StandardJavaFileManager sjfm = compiler.getStandardFileManager(null, null, null);
                String classpath = System.getProperty("java.class.path");
                String testpath = ":" + CLASSES_PATH + "/lib/persistence-api-2.0.jar";
                testpath = testpath + ":" + CLASSES_PATH + "/lib/mongo-java-driver-2.9.1.jar";
                testpath = testpath + ":" + CLASSES_PATH + "/lib/jackson-annotations-2.4.1.jar";
                testpath = testpath + ":" + CLASSES_OUTPUT;

                List<String> optionList = new ArrayList<String>();
                optionList.addAll(Arrays.asList("-classpath", testpath, "-d", CLASSES_OUTPUT));

                Iterable fileObjects = sjfm.getJavaFileObjectsFromFiles(Arrays.asList(sourceFile));
                JavaCompiler.CompilationTask task = compiler.getTask(null, sjfm, null, optionList, null, fileObjects);
                task.call();
                sjfm.close();
            }

            e.createPersistence(Host, Port, database, packge, className, CLASSES_OUTPUT);

            e.createJar(DATA_STORE, CLASSES_OUTPUT, packge);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void generateMongoEntity(String DATA_STORE, String Host, String Port, String setSchema, String database,
            String table, String className, String packge, String classes_path, String classes_output,
            String isRelatioship, String relationshipType, String joinColumnAttributeName,
            String inverseJoinColumnAttributeName, String joinTableName, String relationshipTable,
            String relationshipClass, String relationshipDATA_STORE, String relationshipHost, String relationshipPort,
            String relationshipPackage)
    {
        try
        {
            // Getting collection schema information
            Process p;
            String MONGO_HOME = System.getenv().get("MONGO_HOME");
            System.out.println("table name " + table);
            String[] params = { MONGO_HOME + "/mongo", "-host", Host, "-port", Port, database, "--eval",
                    "var collection = \"" + table + "\"", classes_path + "/conf/variety.js", };

            p = Runtime.getRuntime().exec(params);
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = reader.readLine();

            List<String> colName = new ArrayList<String>();
            List<String> colType = new ArrayList<String>();

            int j = 0;
            while (line != null)
            {
                if (j == 0 || j == 1)
                {
                    line = reader.readLine();
                    j++;
                }
                else
                {
                    String[] valueType = line.split(":");

                    colName.add(valueType[0].replace(" ", ""));
                    colType.add(valueType[1]);
                    System.out.println(valueType[0].replace(" ", "") + ":::" + valueType[1]);
                    line = reader.readLine();
                }
            }

            for (int var = 0; var < colName.size(); var++)
            {
                String colValue = colName.get(var);
                int count = var + 1;
                for (int k = var + 1; k < colName.size(); k++)
                {
                    if (colName.get(k).contains(colValue + "."))
                    {
                        String countValue = colName.get(k);
                        colName.set(k, colName.get(count));
                        colName.set(count, countValue);
                        String countType = colType.get(k);
                        colType.set(k, colType.get(count));
                        colType.set(count, countType);
                        count++;
                    }
                    else
                    {
                        continue;
                    }
                }
            }

            // /Dynamic Object generation
            String collectionName = table;
            String databaseName = database;
            String CLASSES_PATH = classes_path;

            String CLASSES_OUTPUT = classes_output;
            j = 0;
            BuildCollectionEntity_New bce = new BuildCollectionEntity_New();
            if (!colName.isEmpty())
            {
                while (j < colType.size())
                {
                    if (j == 0)
                    {
                        bce.filltable(setSchema, database, collectionName, CLASSES_PATH, className, packge,
                                relationshipPackage);
                        bce.fillid(database, colName.get(j), colType.get(j), CLASSES_PATH, className, packge);
                        j++;
                    }
                    else
                    {
                        if (colType.get(j).equals("Object"))
                        {
                            String ftype = collectionName.substring(0, 1).toUpperCase() + collectionName.substring(1)
                                    + colName.get(j).substring(0, 1).toUpperCase() + colName.get(j).substring(1);
                            bce.fillObjectcolumn(database, colName.get(j), ftype, CLASSES_PATH, className, packge);
                            int k;
                            for (k = j + 1; k < colType.size(); k++)
                                if (colName.get(k).contains(colName.get(j) + "."))
                                {
                                    continue;
                                }
                                else
                                {
                                    break;
                                }
                            BuildSubDocEntity_New bsde = new BuildSubDocEntity_New();
                            List<String> preColumnList = new ArrayList<String>();
                            bsde.buildSubDocEntity(databaseName, collectionName, colName, colType, j, k, preColumnList,
                                    CLASSES_PATH, CLASSES_OUTPUT, packge);
                            j = k;
                        }
                        else
                        {
                            if (isRelatioship.equals("false"))
                            {
                                if (colName.get(j).equals(joinColumnAttributeName))
                                {
                                    j++;
                                    continue;
                                }
                                else
                                {
                                    bce.fillcolumn(database, colName.get(j), colType.get(j), className, CLASSES_PATH,
                                            packge);
                                    j++;
                                }
                            }
                            else
                            {
                                bce.fillcolumn(database, colName.get(j), colType.get(j), className, CLASSES_PATH,
                                        packge);
                                j++;
                            }
                        }
                    }
                }
                if (isRelatioship.equals("true"))
                {
                    if (relationshipDATA_STORE.equals("mongodb"))
                    {
                        generateMongoEntity(DATA_STORE, relationshipHost, relationshipPort, setSchema, database,
                                relationshipTable, relationshipClass, relationshipPackage, CLASSES_PATH,
                                CLASSES_OUTPUT, "false", "", "", "", "", "", "", "", "", "", "");
                    }
                    if (relationshipDATA_STORE.equals("cassandra"))
                    {
                        generateCassandraEntity(DATA_STORE, relationshipHost, relationshipPort, setSchema, database,
                                relationshipTable, relationshipClass, relationshipPackage, CLASSES_PATH,
                                CLASSES_OUTPUT, "false", "", "", "", "", "", "", "", "", "", "");
                    }
                    ServerSideObjectGenerationUtils.fillRelationship(className, joinColumnAttributeName,
                            inverseJoinColumnAttributeName, joinTableName, relationshipType, relationshipClass,
                            CLASSES_PATH, packge, databaseName);
                }
                FileWriter writer = new FileWriter(CLASSES_PATH + "/" + packge + "/" + database + "/" + className
                        + ".java", true);
                writer.write("}");
                writer.close();
            }

            // Compiling the entity definition file
            File sourceFile = new File(CLASSES_PATH + "/" + packge + "/" + database + "/" + className + ".java");

            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            StandardJavaFileManager sjfm = compiler.getStandardFileManager(null, null, null);
            String classpath = System.getProperty("java.class.path");
            String testpath = ":" + CLASSES_PATH + "/lib/persistence-api-2.0.jar";

            testpath = testpath + ":" + CLASSES_PATH + "/lib/mongo-java-driver-2.9.1.jar";
            testpath = testpath + ":" + CLASSES_PATH + "/lib/jackson-annotations-2.4.1.jar";
            testpath = testpath + ":" + CLASSES_OUTPUT;

            List<String> optionList = new ArrayList<String>();
            optionList.addAll(Arrays.asList("-classpath", testpath, "-d", CLASSES_OUTPUT));

            Iterable fileObjects = sjfm.getJavaFileObjectsFromFiles(Arrays.asList(sourceFile));
            JavaCompiler.CompilationTask task = compiler.getTask(null, sjfm, null, optionList, null, fileObjects);
            task.call();
            sjfm.close();

            bce.createPersistenceMongo(Host, Port, databaseName, packge, className, CLASSES_OUTPUT);
            bce.createJar(DATA_STORE, CLASSES_OUTPUT, packge);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
