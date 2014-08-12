package configfamily;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class BuildCollectionEntity_New
{

    public void filltable(String setSchema, String database, String table, String CLASSES_PATH, String className,
            String packge, String relationshipPackage)
    {

        try
        {

            if (setSchema.equals("true"))
            {
                File file = new File(CLASSES_PATH + "/conf/table1.txt");
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line = "", oldtext = "";
                while ((line = reader.readLine()) != null)
                {
                    oldtext += line + "\n";
                }

                String newtext = oldtext.replaceAll("table", table);
                newtext = newtext.replaceAll("kspace", database);
                String newtext2 = newtext.replaceAll("databasecollection", className);
                String newtext3 = newtext2.replaceAll("packge", packge + "." + database);
                if (!relationshipPackage.equals(packge) && !relationshipPackage.isEmpty())
                {
                    String importRelationshipClass = "//%IMPORT%" + "\n" + "import " + relationshipPackage + "."
                            + database + ".* ;" + "\n";
                    newtext3 = newtext3.replaceAll("//%IMPORT%", importRelationshipClass);
                }
                FileWriter writer = new FileWriter(CLASSES_PATH + "/" + packge + "/" + database + "/" + className
                        + ".java");
                writer.write(newtext3);
                writer.close();
            }
            else
            {
                File file = new File(CLASSES_PATH + "/conf/table1_new.txt");
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line = "", oldtext = "";
                while ((line = reader.readLine()) != null)
                {
                    oldtext += line + "\n";
                }

                String newtext = oldtext.replaceAll("table", table);
                newtext = newtext.replaceAll("", database);
                String newtext2 = newtext.replaceAll("databasecollection", className);
                String newtext3 = newtext2.replaceAll("packge", packge);
                if (!relationshipPackage.equals(packge) && !relationshipPackage.isEmpty())
                {
                    String importRelationshipClass = "/*IMPORT*/" + "\n" + "import " + relationshipPackage + "."
                            + database + ".* ;" + "\n";
                    newtext3 = newtext3.replaceAll("/*IMPORT*/", importRelationshipClass);
                }
                FileWriter writer = new FileWriter(CLASSES_PATH + "/" + packge + "/" + database + "/" + className
                        + ".java");
                writer.write(newtext3);
                writer.close();

            }
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }

    public void fillid(String database, String fname, String ftype, String CLASSES_PATH, String className, String packge)
    {
        try
        {

            String text = "";

            text = text + "@Id\n";
            text = text + "@Column(name= \"" + fname + "\" )\n";
            text = text + "private " + ftype + " " + fname + ";\n";
            text += "@JsonProperty(\"" + fname + "\")\n";

            StringBuffer sb = new StringBuffer();
            if (ftype.equals("Boolean"))
            {

                String getterName = "is" + fname.substring(0, 1).toUpperCase() + fname.substring(1);

                sb.append("public ").append(ftype).append(" ").append(getterName).append("(){").append("return this.")
                        .append(fname).append(";").append("}");

            }
            else
            {
                String getterName = "get" + fname.substring(0, 1).toUpperCase() + fname.substring(1);

                sb.append("public ").append(ftype).append(" ").append(getterName).append("(){").append("return this.")
                        .append(fname).append(";").append("}");

            }

            String setterName = "set" + fname.substring(0, 1).toUpperCase() + fname.substring(1);

            StringBuffer sb1 = new StringBuffer();
            sb1.append("public void ").append(setterName).append("(").append(ftype).append(" ").append(fname)
                    .append(")").append("{").append("this.").append(fname).append("=").append(fname).append(";")
                    .append("}");

            text = text + sb.toString() + "\n";
            text += "@JsonProperty(\"" + fname + "\")\n";
            text = text + sb1.toString() + "\n";
            FileWriter writer = new FileWriter(
                    CLASSES_PATH + "/" + packge + "/" + database + "/" + className + ".java", true);
            writer.write(text);
            writer.close();
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }

    }

    public void fillObjectcolumn(String database, String fname, String ftype, String CLASSES_PATH, String className,
            String packge)
    {
        try
        {

            String text = "";

            text = text + "@Embedded\n";
            text = text + "private " + ftype + " " + fname + ";\n";
            text += "@JsonProperty(\"" + fname + "\")\n";

            StringBuffer sb = new StringBuffer();
            if (ftype.equals("Boolean"))
            {

                String getterName = "is" + fname.substring(0, 1).toUpperCase() + fname.substring(1);

                sb.append("public ").append(ftype).append(" ").append(getterName).append("(){").append("return this.")
                        .append(fname).append(";").append("}");

            }
            else
            {
                String getterName = "get" + fname.substring(0, 1).toUpperCase() + fname.substring(1);

                sb.append("public ").append(ftype).append(" ").append(getterName).append("(){").append("return this.")
                        .append(fname).append(";").append("}");

            }

            String setterName = "set" + fname.substring(0, 1).toUpperCase() + fname.substring(1);

            StringBuffer sb1 = new StringBuffer();
            sb1.append("public void ").append(setterName).append("(").append(ftype).append(" ").append(fname)
                    .append(")").append("{").append("this.").append(fname).append("=").append(fname).append(";")
                    .append("}");

            text = text + sb.toString() + "\n";
            text += "@JsonProperty(\"" + fname + "\")\n";
            text = text + sb1.toString() + "\n";
            FileWriter writer = new FileWriter(
                    CLASSES_PATH + "/" + packge + "/" + database + "/" + className + ".java", true);
            writer.write(text);
            writer.close();
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }

    public void fillcolumn(String database, String fname, String ftype, String tab, String CLASSES_PATH, String packge)
    {
        try
        {

            if (ftype.equals("Arrayint") || ftype.equals("Arrayfloat") || ftype.equals("Arraystring"))
                ftype = "String";
            String text = "";

            text = text + "@Column(name= \"" + fname + "\" )\n";
            text = text + "private " + ftype + " " + fname + ";\n";
            text += "@JsonProperty(\"" + fname + "\")\n";

            StringBuffer sb = new StringBuffer();
            if (ftype.equals("Boolean"))
            {

                String getterName = "is" + fname.substring(0, 1).toUpperCase() + fname.substring(1);

                sb.append("public ").append(ftype).append(" ").append(getterName).append("(){").append("return this.")
                        .append(fname).append(";").append("}");

            }
            else
            {
                String getterName = "get" + fname.substring(0, 1).toUpperCase() + fname.substring(1);

                sb.append("public ").append(ftype).append(" ").append(getterName).append("(){").append("return this.")
                        .append(fname).append(";").append("}");

            }

            String setterName = "set" + fname.substring(0, 1).toUpperCase() + fname.substring(1);

            StringBuffer sb1 = new StringBuffer();
            sb1.append("public void ").append(setterName).append("(").append(ftype).append(" ").append(fname)
                    .append(")").append("{").append("this.").append(fname).append("=").append(fname).append(";")
                    .append("}");

            text = text + sb.toString() + "\n";
            text += "@JsonProperty(\"" + fname + "\")\n";
            text = text + sb1.toString() + "\n";
            FileWriter writer = new FileWriter(CLASSES_PATH + "/" + packge + "/" + database + "/" + tab + ".java", true);
            writer.write(text);
            writer.close();
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }

    public void fillcompositeclass(String database, String table, String CLASSES_PATH, String packge,
            List<String> preColList)
    {
        try
        {

            File file = new File(CLASSES_PATH + "/conf/compositekeytable1.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = "", oldtext = "";
            while ((line = reader.readLine()) != null)
            {
                oldtext += line + "\n";
            }
            reader.close();
            String field = "";
            for (int i = 0; i < preColList.size(); i++)
            {
                field = field + preColList.get(i).substring(0, 1).toUpperCase() + preColList.get(i).substring(1);
            }
            String newtext = oldtext.replaceAll("databasecollectionfield",
                    table.substring(0, 1).toUpperCase() + table.substring(1) + field);
            String newtext1 = newtext.replaceAll("packge", packge + "." + database);
            FileWriter writer = new FileWriter(CLASSES_PATH + "/" + packge + "/" + database + "/"
                    + table.substring(0, 1).toUpperCase() + table.substring(1) + field + ".java");
            writer.write(newtext1);
            writer.close();

        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }

    }
  

    public void createPersistenceMongo(String Host, String Port, String kespaceName, String packageName,
            String className, String CLASSES_OUTPUT)
    {

        try
        {
            String samplePersistenceTemplate = "<!--PERSISTENCE_SAMPLE-->" + "\n"
                    + "<persistence-unit name=\"%DATABASE_PU\">" + "\n"
                    + "<provider>com.impetus.kundera.KunderaPersistence</provider>" + "\n"
                    + "<!--<class>cassandraSampleClass</class>-->" + "\n" + "<!--<class>mongoSampleClass</class>-->"
                    + "\n" + "<exclude-unlisted-classes>true</exclude-unlisted-classes>" + "\n" + "<properties>" + "\n"
                    + "<property name=\"kundera.nodes\" value=\"%DATABASE_HOST\"/>" + "\n"
                    + "<property name=\"kundera.port\" value=\"%DATABASE_PORT\"/>" + "\n"
                    + "<property name=\"kundera.keyspace\" value=\"%KUNDERA_KEYSPACE\"/>" + "\n"
                    + "<property name=\"kundera.dialect\" value=\"%KUNDERA_DIALECT\"/>" + "\n"
                    + "<property name=\"kundera.client.property\" value=\"%CLIENT_PROPERTY\"/>" + "\n"
                    + "<property name=\"kundera.client.lookup.class\" value=\"%LOOKUP_CLASS\"/>" + "\n"
                    + "</properties>" + "\n" + "</persistence-unit>" + "\n";
            // System.out.println(samplePersistenceTemplate);
            File node = new File(CLASSES_OUTPUT + "/META-INF");
            if (!node.exists())
                node.mkdir();

            File sourceFile = new File(CLASSES_OUTPUT + "/META-INF/persistence.xml");

            if (sourceFile.exists())
            {
                System.out.println("file exists");
                BufferedReader reader = new BufferedReader(new FileReader(sourceFile));
                String line = "", oldtext = "";
                String classTag = "<class>" + packageName + "." + kespaceName + "." + className + "</class>\n";
                classTag = classTag + "<!--<class>mongoClass</class>-->";

                while ((line = reader.readLine()) != null)
                {
                    oldtext += line + "\n";
                }

                if (oldtext.contains("mongodatabase_pu"))
                {
                    String checkHostPort = "<property name=\"kundera.nodes\" value=\"" + Host + "\"/>" + "\n"
                            + "<property name=\"kundera.port\" value=\"" + Port + "\"/>" + "\n";
                    if (oldtext.contains(checkHostPort))
                    {

                        FileWriter writer = new FileWriter(CLASSES_OUTPUT + "/META-INF/persistence.xml");

                        String newtext5 = "";
                        if (!oldtext.contains("<class>" + packageName + "." + className + "</class>"))
                        {

                            newtext5 = oldtext.replaceAll("<!--<class>mongoClass</class>-->", classTag);
                            writer.write(newtext5);

                        }
                        else
                        {
                            writer.write(oldtext);
                        }
                        writer.close();

                    }
                    else
                    {
                        String textToReplace = oldtext.substring(
                                oldtext.indexOf("<persistence-unit name=\"mongodatabase_pu\">"),
                                oldtext.indexOf("</persistence-unit>",
                                        oldtext.indexOf("<persistence-unit name=\"mongodatabase_pu\">")));
                        textToReplace = textToReplace + "</persistence-unit>";
                        oldtext = oldtext.replaceAll(textToReplace, "");

                        String newtext1 = oldtext.replaceAll("%DATABASE_PU", "mongodatabase_pu");
                        String newtext2 = newtext1.replaceAll("%DATABASE_HOST", Host);
                        String newtext3 = newtext2.replaceAll("%DATABASE_PORT", Port);
                        String newtext4 = newtext3.replaceAll("%KUNDERA_KEYSPACE", kespaceName);
                        String newtext5 = newtext4.replaceAll("<!--<class>mongoSampleClass</class>-->", classTag);
                        String newtext6 = newtext5.replaceAll("%KUNDERA_DIALECT", "mongodb");
                        String newtext7 = newtext6.replaceAll("%CLIENT_PROPERTY", "KunderaConnection.xml");
                        String newtext8 = newtext7.replaceAll("%LOOKUP_CLASS",
                                "com.impetus.client.mongodb.MongoDBClientFactory");
                        newtext8 = newtext8.replaceAll("<!--<class>cassandraSampleClass</class>-->", "");
                        newtext8 = newtext8.replaceAll("<!--PERSISTENCE_SAMPLE-->", samplePersistenceTemplate);
                        FileWriter writer = new FileWriter(CLASSES_OUTPUT + "/META-INF/persistence.xml");
                        writer.write(newtext8);
                        writer.close();
                    }

                }
                else
                {

                    String newtext1 = oldtext.replaceAll("%DATABASE_PU", "mongodatabase_pu");
                    String newtext2 = newtext1.replaceAll("%DATABASE_HOST", Host);
                    String newtext3 = newtext2.replaceAll("%DATABASE_PORT", Port);
                    String newtext4 = newtext3.replaceAll("%KUNDERA_KEYSPACE", kespaceName);
                    String newtext5 = newtext4.replaceAll("<!--<class>mongoSampleClass</class>-->", classTag);
                    String newtext6 = newtext5.replaceAll("%KUNDERA_DIALECT", "mongodb");
                    String newtext7 = newtext6.replaceAll("%CLIENT_PROPERTY", "KunderaConnection.xml");
                    String newtext8 = newtext7.replaceAll("%LOOKUP_CLASS",
                            "com.impetus.client.mongodb.MongoDBClientFactory");
                    newtext8 = newtext8.replaceAll("<!--<class>cassandraSampleClass</class>-->", "");
                    newtext8 = newtext8.replaceAll("<!--PERSISTENCE_SAMPLE-->", samplePersistenceTemplate);
                    FileWriter writer = new FileWriter(CLASSES_OUTPUT + "/META-INF/persistence.xml");
                    writer.write(newtext8);
                    writer.close();
                }

            }
            else
            {
                //String CLASSES_PATH = System.getProperty("user.dir");
                String classPath =this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
                int endIndex = classPath.indexOf("/src") != -1 ? classPath.indexOf("/src") : classPath
                                    .indexOf("/ServerSideObjectGen-0.0.1-jar-with-dependencies.jar");
                String path = classPath.substring(0, endIndex);
                String CLASSES_PATH = path;

                File file = new File(CLASSES_PATH + "/conf/persistence.txt");
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line = "", oldtext = "";
                String classTag = "<class>" + packageName + "." + kespaceName + "." + className + "</class>\n";
                classTag = classTag + "<!--<class>mongoClass</class>-->";

                while ((line = reader.readLine()) != null)
                {
                    oldtext += line + "\n";
                }

                String newtext1 = oldtext.replaceAll("%DATABASE_PU", "mongodatabase_pu");
                String newtext2 = newtext1.replaceAll("%DATABASE_HOST", Host);
                String newtext3 = newtext2.replaceAll("%DATABASE_PORT", Port);
                String newtext4 = newtext3.replaceAll("%KUNDERA_KEYSPACE", kespaceName);
                String newtext5 = newtext4.replaceAll("<!--<class>mongoSampleClass</class>-->", classTag);
                String newtext6 = newtext5.replaceAll("%KUNDERA_DIALECT", "mongodb");
                String newtext7 = newtext6.replaceAll("%CLIENT_PROPERTY", "KunderaConnection.xml");
                String newtext8 = newtext7.replaceAll("%LOOKUP_CLASS",
                        "com.impetus.client.mongodb.MongoDBClientFactory");
                newtext8 = newtext8.replaceAll("<!--<class>cassandraSampleClass</class>-->", "");
                newtext8 = newtext8.replaceAll("<!--PERSISTENCE_SAMPLE-->", samplePersistenceTemplate);
                FileWriter writer = new FileWriter(CLASSES_OUTPUT + "/META-INF/persistence.xml");
                writer.write(newtext8);
                writer.close();
            }
        }
        catch (Exception e)
        {

            e.printStackTrace();
        }

    }

    public void createJar(String DATA_STORE, String CLASSES_OUTPUT, String packageName)
    {
        try
        {

            //String CLASSES_PATH = System.getProperty("user.dir");
            String classPath =this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
            int endIndex = classPath.indexOf("/src") != -1 ? classPath.indexOf("/src") : classPath
                .indexOf("/ServerSideObjectGen-0.0.1-jar-with-dependencies.jar");
            String path = classPath.substring(0, endIndex);
            String CLASSES_PATH = path;

            List<String> ls = new ArrayList<String>();
           
            String javaHome = System.getenv("JAVA_HOME");
            
            ls.add(javaHome + "/bin/jar");
            ls.add("cvf");
            if(DATA_STORE.equals("mongodb")) {
               ls.add(CLASSES_OUTPUT+"/dynamic-mongodb-entity.jar");
            } else if(DATA_STORE.equals("cassandra")) {
               ls.add(CLASSES_OUTPUT+"/dynamic-cassandra-entity.jar");
            }
           
            ls.add(CLASSES_OUTPUT+"/"+packageName.split("\\.")[0]);
            ProcessBuilder pb = new ProcessBuilder(ls);
            pb.redirectErrorStream();

            InputStream is = null;

            Process p = pb.start();
            is = p.getInputStream();
            StringBuilder output = new StringBuilder(80);
            int in = -1;
            while ((in = is.read()) != -1)
            {
                if (in != '\n')
                {
                    output.append((char) in);

                    System.out.print(output.toString());

                    output.delete(0, output.length());
                }

            }

        }
        catch (Exception e)
        {
            e.printStackTrace();

        }

    }

}