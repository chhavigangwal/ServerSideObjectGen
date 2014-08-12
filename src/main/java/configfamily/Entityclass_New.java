package configfamily;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Entityclass_New
{

    public void filltable(String setSchema, String keyspace, String table, String CLASSES_PATH, String className,
            String packge, String relationshipPackage)
    {

        try
        {

            // File file = new
            // File(CLASSES_PATH+"../resources/code/table_new.txt");
            // src/main/resources/code
            if (setSchema.equals("true"))
            {

                File file = new File(CLASSES_PATH + "/conf/table.txt");
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line = "", oldtext = "";
                while ((line = reader.readLine()) != null)
                {
                    oldtext += line + "\n";
                }

                String newtext = oldtext.replaceAll("table", table);
                String newtext2 = newtext.replaceAll("Idbenchfamily", className);
                String newtext3 = newtext2.replaceAll("packge", packge + "." + keyspace);
                newtext3 = newtext3.replaceAll("kspace", keyspace);
                if (!relationshipPackage.equals(packge) && !relationshipPackage.isEmpty())
                {
                    System.out.println("hello either");

                    String importRelationshipClass = "//%IMPORT%" + "\n" + "import " + relationshipPackage + "."
                            + keyspace + ".* ;" + "\n";

                    newtext3 = newtext3.replaceAll("//%IMPORT%", importRelationshipClass);
                }

                FileWriter writer = new FileWriter(CLASSES_PATH + "/" + packge + "/" + keyspace + "/" + className
                        + ".java");
                writer.write(newtext3);
                writer.close();

                // //////////////////////////////////

            }
            else
            {
                File file = new File(CLASSES_PATH + "/conf/table_new.txt");
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line = "", oldtext = "";
                while ((line = reader.readLine()) != null)
                {
                    oldtext += line + "\n";
                }

                String newtext = oldtext.replaceAll("table", table);
                String newtext2 = newtext.replaceAll("Idbenchfamily", className);
                String newtext3 = newtext2.replaceAll("packge", packge + "." + keyspace);
                if (!relationshipPackage.equals(packge) && !relationshipPackage.isEmpty())
                {
                    String importRelationshipClass = "/*IMPORT*/" + "\n" + "import " + relationshipPackage + "."
                            + keyspace + ".* ;" + "\n";
                    newtext3 = newtext3.replaceAll("/*IMPORT*/", importRelationshipClass);
                }
                FileWriter writer = new FileWriter(CLASSES_PATH + "/" + packge + "/" + keyspace + "/" + className
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
            if (fname.equals("compkey"))
            {
                text = text + "@EmbeddedId\n";
                text = text + ftype + " " + fname + ";\n";
            }
            else
            {
                text = text + "@Id\n";
                text = text + "@Column(name= \"" + fname + "\" )\n";
                text = text + "private " + ftype + " " + fname + ";\n";
            }
            text += "@JsonProperty(\"" + fname + "\")\n";

            StringBuffer sb = new StringBuffer();
            if (ftype.equals("boolean"))
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

            String text = "";

            text = text + "@Column(name= \"" + fname + "\" )\n";
            text = text + "private " + ftype + " " + fname + ";\n";
            text += "@JsonProperty(\"" + fname + "\")\n";
            StringBuffer sb = new StringBuffer();
            if (ftype.equals("boolean"))
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

    public void fillcompositeclass(String database, String table, String keyspace, String CLASSES_PATH, String packge)
    {
        try
        {

            File file = new File(CLASSES_PATH + "/conf/compositekeytable.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = "", oldtext = "";
            while ((line = reader.readLine()) != null)
            {
                oldtext += line + "\n";
            }
            reader.close();

            String newtext = oldtext.replaceAll("CompositekeyClass", "Composite" + table.substring(0, 1).toUpperCase()
                    + table.substring(1));
            String newtext1 = newtext.replaceAll("packge", packge + "." + database);
            FileWriter writer = new FileWriter(CLASSES_PATH + "/" + packge + "/" + database + "/Composite"
                    + table.substring(0, 1).toUpperCase() + table.substring(1) + ".java");
            writer.write(newtext1);
            writer.close();
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }

    }

    /*
     * public void fillRelationship(String className, String attributeName,
     * String relationshipType, String className_2, String CLASSES_PATH, String
     * packge) { try { String text = ""; text = text + "@" + relationshipType +
     * "(cascade = CascadeType.ALL, fetch = FetchType.EAGER)\n"; text = text +
     * "@JoinColumn(name=\"" + attributeName + "\")\n"; text = text +
     * "private Set<" + className_2 + "> " + className_2 + ";\n"; text +=
     * "@JsonProperty(\"" + className_2 + "\")\n"; StringBuffer sb = new
     * StringBuffer(); // creating getters
     * 
     * String getterName = "get" + className_2.substring(0, 1).toUpperCase() +
     * className_2.substring(1);
     * 
     * sb.append("public ").append("Set<" + className_2 +
     * "> ").append(" ").append(getterName).append("(){")
     * .append("return this.").append(className_2).append(";").append("}"); //
     * creating setters String setterName = "set" + className_2.substring(0,
     * 1).toUpperCase() + className_2.substring(1);
     * 
     * StringBuffer sb1 = new StringBuffer();
     * sb1.append("public void ").append(setterName).append("(").append("Set<" +
     * className_2 + "> ").append(" ")
     * .append(className_2).append(")").append("{"
     * ).append("this.").append(className_2).append("=")
     * .append(className_2).append(";").append("}");
     * 
     * text = text + sb.toString() + "\n"; text += "@JsonProperty(\"" +
     * className_2 + "\")\n"; text = text + sb1.toString() + "\n"; FileWriter
     * writer = new FileWriter(CLASSES_PATH + "/" + packge + "/" + className +
     * ".java", true); writer.write(text); writer.close(); } catch (Exception e)
     * { e.printStackTrace(); } }
     */

    public void createPersistence(String Host, String Port, String kespaceName, String packageName, String className,
            String CLASSES_OUTPUT)
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
                classTag = classTag + "<!--<class>cassandraClass</class>-->";

                while ((line = reader.readLine()) != null)
                {
                    oldtext += line + "\n";
                }

                if (oldtext.contains("cassandra_pu"))
                {
                    String checkHostPort = "<property name=\"kundera.nodes\" value=\"" + Host + "\"/>" + "\n"
                            + "<property name=\"kundera.port\" value=\"" + Port + "\"/>" + "\n";
                    if (oldtext.contains(checkHostPort))
                    {

                        FileWriter writer = new FileWriter(CLASSES_OUTPUT + "/META-INF/persistence.xml");

                        String newtext5 = "";
                        if (!oldtext.contains("<class>" + packageName + "." + className + "</class>"))
                        {

                            newtext5 = oldtext.replaceAll("<!--<class>cassandraClass</class>-->", classTag);
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
                                oldtext.indexOf("<persistence-unit name=\"cassandra_pu\">"),
                                oldtext.indexOf("</persistence-unit>",
                                        oldtext.indexOf("<persistence-unit name=\"cassandra_pu\">")));
                        textToReplace = textToReplace + "</persistence-unit>";
                        oldtext = oldtext.replaceAll(textToReplace, "");

                        String newtext1 = oldtext.replaceAll("%DATABASE_PU", "cassandra_pu");
                        String newtext2 = newtext1.replaceAll("%DATABASE_HOST", Host);
                        String newtext3 = newtext2.replaceAll("%DATABASE_PORT", Port);
                        String newtext4 = newtext3.replaceAll("%KUNDERA_KEYSPACE", kespaceName);
                        String newtext5 = newtext4.replaceAll("<!--<class>mongoSampleClass</class>-->", classTag);
                        String newtext6 = newtext5.replaceAll("%KUNDERA_DIALECT", "cassandra");
                        String newtext7 = newtext6.replaceAll("%CLIENT_PROPERTY", "KunderaConnection.xml");
                        String newtext8 = newtext7.replaceAll("%LOOKUP_CLASS",
                                "com.impetus.client.cassandra.thrift.ThriftClientFactory");
                        newtext8 = newtext8.replaceAll("<!--<class>mongoSampleClass</class>-->", "");
                        newtext8 = newtext8.replaceAll("<!--PERSISTENCE_SAMPLE-->", samplePersistenceTemplate);
                        FileWriter writer = new FileWriter(CLASSES_OUTPUT + "/META-INF/persistence.xml");
                        writer.write(newtext8);
                        writer.close();
                    }

                }
                else
                {

                    String newtext1 = oldtext.replaceAll("%DATABASE_PU", "cassandra_pu");
                    String newtext2 = newtext1.replaceAll("%DATABASE_HOST", Host);
                    String newtext3 = newtext2.replaceAll("%DATABASE_PORT", Port);
                    String newtext4 = newtext3.replaceAll("%KUNDERA_KEYSPACE", kespaceName);
                    String newtext5 = newtext4.replaceAll("<!--<class>cassandraSampleClass</class>-->", classTag);
                    String newtext6 = newtext5.replaceAll("%KUNDERA_DIALECT", "cassandra");
                    String newtext7 = newtext6.replaceAll("%CLIENT_PROPERTY", "KunderaConnection.xml");
                    String newtext8 = newtext7.replaceAll("%LOOKUP_CLASS",
                            "com.impetus.client.cassandra.thrift.ThriftClientFactory");
                    newtext8 = newtext8.replaceAll("<!--<class>mongoSampleClass</class>-->", "");
                    newtext8 = newtext8.replaceAll("<!--PERSISTENCE_SAMPLE-->", samplePersistenceTemplate);
                    FileWriter writer = new FileWriter(CLASSES_OUTPUT + "/META-INF/persistence.xml");
                    writer.write(newtext8);
                    writer.close();
                }

            }
            else
            {
                String CLASSES_PATH = System.getProperty("user.dir");

                File file = new File(CLASSES_PATH + "/conf/persistence.txt");
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line = "", oldtext = "";
                String classTag = "<class>" + packageName + "." + kespaceName + "." + className + "</class>\n";
                classTag = classTag + "<!--<class>cassandraClass</class>-->";

                while ((line = reader.readLine()) != null)
                {
                    oldtext += line + "\n";
                }

                String newtext1 = oldtext.replaceAll("%DATABASE_PU", "cassandra_pu");
                String newtext2 = newtext1.replaceAll("%DATABASE_HOST", Host);
                String newtext3 = newtext2.replaceAll("%DATABASE_PORT", Port);
                String newtext4 = newtext3.replaceAll("%KUNDERA_KEYSPACE", kespaceName);
                String newtext5 = newtext4.replaceAll("<!--<class>cassandraSampleClass</class>-->", classTag);
                String newtext6 = newtext5.replaceAll("%KUNDERA_DIALECT", "cassandra");
                String newtext7 = newtext6.replaceAll("%CLIENT_PROPERTY", "KunderaConnection.xml");
                String newtext8 = newtext7.replaceAll("%LOOKUP_CLASS",
                        "com.impetus.client.cassandra.thrift.ThriftClientFactory");
                newtext8 = newtext8.replaceAll("<!--<class>mongoSampleClass</class>-->", "");
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

            String CLASSES_PATH = System.getProperty("user.dir");
            List<String> ls = new ArrayList<String>();

            ls.add("sh");
            ls.add(CLASSES_PATH + "/conf/create_jar.sh");
            ls.add(CLASSES_OUTPUT);
            ls.add(packageName.split("\\.")[0]);
            ls.add(DATA_STORE);

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
