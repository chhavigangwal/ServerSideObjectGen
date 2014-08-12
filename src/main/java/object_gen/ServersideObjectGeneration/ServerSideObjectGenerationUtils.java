package object_gen.ServersideObjectGeneration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.impetus.client.cassandra.thrift.ThriftClientFactory;
import com.impetus.client.mongodb.MongoDBClientFactory;

public class ServerSideObjectGenerationUtils
{
    /**
     * 
     * @param className
     * @param joinColumnAttributeName
     * @param relationshipType
     * @param relationshipClassName
     * @param CLASSES_PATH
     * @param packge
     * @param database
     */
    public static void fillRelationship(String className, String joinColumnAttributeName,
            String inverseJoinColumnAttributeName, String joinTableName, String relationshipType,
            String relationshipClassName, String CLASSES_PATH, String packge, String database)
    {
        try
        {
            String text = "";

            if (relationshipType.equals("OneToMany"))
            {
                text = fillOneToManyRelationship(joinColumnAttributeName, relationshipType, relationshipClassName, text);
            }
            else if (relationshipType.equals("OneToOne") || relationshipType.equals("ManyToOne"))
            {
                text = fillOneToOneOrManyToOneRelationship(joinColumnAttributeName, relationshipType,
                        relationshipClassName, text);
            }
            else if (relationshipType.equals("ManyToMany"))
            {
                text = fillManyToManyRelationship(className, joinColumnAttributeName, inverseJoinColumnAttributeName,
                        joinTableName, relationshipType, relationshipClassName, database, text);
            }
            FileWriter writer = new FileWriter(
                    CLASSES_PATH + "/" + packge + "/" + database + "/" + className + ".java", true);
            writer.write(text);
            writer.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 
     * @param className
     * @param joinColumnAttributeName
     * @param relationshipType
     * @param relationshipClassName
     * @param database
     * @param text
     * @return
     */
    private static String fillManyToManyRelationship(String className, String joinColumnAttributeName,
            String inverseJoinColumnAttributeName, String joinTableName, String relationshipType,
            String relationshipClassName, String database, String text)
    {
        text = text + "@" + relationshipType + "(cascade = CascadeType.ALL, fetch = FetchType.EAGER)\n";
        text = text + "@JoinTable(name=\"" + joinTableName + "\", schema = \"" + database
                + "\", joinColumns = { @JoinColumn(name = \"" + joinColumnAttributeName
                + "\" ) }, inverseJoinColumns = { @JoinColumn(name = \"" + inverseJoinColumnAttributeName + "\" ) })\n";
        text = text + "private " + relationshipClassName + " " + relationshipClassName.toLowerCase() + ";\n";
        text += "@JsonProperty(\"" + relationshipClassName + "\")\n";

        StringBuffer sb = new StringBuffer();

        // creating getters
        String getterName = "get" + relationshipClassName.substring(0, 1).toUpperCase()
                + relationshipClassName.substring(1);

        sb.append("public ").append(relationshipClassName).append(" ").append(getterName).append("(){")
                .append("return this.").append(relationshipClassName.toLowerCase()).append(";").append("}");

        // creating setters
        String setterName = "set" + relationshipClassName.substring(0, 1).toUpperCase()
                + relationshipClassName.substring(1);

        StringBuffer sb1 = new StringBuffer();
        sb1.append("public void ").append(setterName).append("( ").append(relationshipClassName).append(" ")
                .append(relationshipClassName.toLowerCase()).append(")").append("{").append("this.")
                .append(relationshipClassName.toLowerCase()).append("=").append(relationshipClassName.toLowerCase())
                .append(";").append("}");

        text = text + sb.toString() + "\n";
        text += "@JsonProperty(\"" + relationshipClassName + "\")\n";
        text = text + sb1.toString() + "\n";
        return text;
    }

    /**
     * 
     * @param joinColumnAttributeName
     * @param relationshipType
     * @param relationshipClassName
     * @param text
     * @return
     */
    private static String fillOneToOneOrManyToOneRelationship(String joinColumnAttributeName, String relationshipType,
            String relationshipClassName, String text)
    {
        text = text + "@" + relationshipType + "(cascade = CascadeType.ALL, fetch = FetchType.EAGER)\n";
        text = text + "@JoinColumn(name=\"" + joinColumnAttributeName + "\")\n";
        text = text + "private " + relationshipClassName + " " + relationshipClassName.toLowerCase() + ";\n";
        text += "@JsonProperty(\"" + relationshipClassName + "\")\n";

        StringBuffer sb = new StringBuffer();

        // creating getters
        String getterName = "get" + relationshipClassName.substring(0, 1).toUpperCase()
                + relationshipClassName.substring(1);

        sb.append("public ").append(relationshipClassName).append(" ").append(getterName).append("(){")
                .append("return this.").append(relationshipClassName.toLowerCase()).append(";").append("}");

        // creating setters
        String setterName = "set" + relationshipClassName.substring(0, 1).toUpperCase()
                + relationshipClassName.substring(1);

        StringBuffer sb1 = new StringBuffer();
        sb1.append("public void ").append(setterName).append("( ").append(relationshipClassName).append(" ")
                .append(relationshipClassName.toLowerCase()).append(")").append("{").append("this.")
                .append(relationshipClassName.toLowerCase()).append("=").append(relationshipClassName.toLowerCase())
                .append(";").append("}");

        text = text + sb.toString() + "\n";
        text += "@JsonProperty(\"" + relationshipClassName + "\")\n";
        text = text + sb1.toString() + "\n";
        return text;
    }

    /**
     * 
     * @param joinColumnAttributeName
     * @param relationshipType
     * @param relationshipClassName
     * @param text
     * @return
     */
    private static String fillOneToManyRelationship(String joinColumnAttributeName, String relationshipType,
            String relationshipClassName, String text)
    {
        text = text + "@" + relationshipType + "(cascade = CascadeType.ALL, fetch = FetchType.EAGER)\n";
        text = text + "@JoinColumn(name=\"" + joinColumnAttributeName + "\")\n";
        text = text + "private Set<" + relationshipClassName + "> " + relationshipClassName + ";\n";
        text += "@JsonProperty(\"" + relationshipClassName + "\")\n";

        StringBuffer sb = new StringBuffer();

        // creating getters
        String getterName = "get" + relationshipClassName.substring(0, 1).toUpperCase()
                + relationshipClassName.substring(1);

        sb.append("public ").append("Set<" + relationshipClassName + "> ").append(" ").append(getterName).append("(){")
                .append("return this.").append(relationshipClassName).append(";").append("}");

        // creating setters
        String setterName = "set" + relationshipClassName.substring(0, 1).toUpperCase()
                + relationshipClassName.substring(1);

        StringBuffer sb1 = new StringBuffer();
        sb1.append("public void ").append(setterName).append("(").append("Set<" + relationshipClassName + "> ")
                .append(" ").append(relationshipClassName).append(")").append("{").append("this.")
                .append(relationshipClassName).append("=").append(relationshipClassName).append(";").append("}");

        text = text + sb.toString() + "\n";
        text += "@JsonProperty(\"" + relationshipClassName + "\")\n";
        text = text + sb1.toString() + "\n";
        return text;
    }


    private static Map<String, String> datastoreToPuName = new HashMap<String, String>();

    private static Map<String, String> datastoreToClientFactoryName = new HashMap<String, String>();
    static
    {
        datastoreToPuName.put("cassandra", "cassandra_pu");
        datastoreToPuName.put("mongodb", "mongo_pu");
        datastoreToPuName.put("hbase", "hbase_pu");
        datastoreToPuName.put("rdbms", "rdbms_pu");
    }

    static
    {
        datastoreToClientFactoryName.put("cassandra", ThriftClientFactory.class.getName());
        datastoreToClientFactoryName.put("mongodb", MongoDBClientFactory.class.getName());
        // datastoreToPuName.put("hbase", HBaseClientFactory.class.getName());
        // datastoreToPuName.put("rdbms", RDBMSClientFactory.class.getName());
    }

    /**
     * 
     * @param datastorename
     * @param Host
     * @param Port
     * @param kespaceName
     * @param packageName
     * @param className
     * @param outputPackagePath
     */
    public static void createPersistenceUnitInExistingPersistenceXml(String datastorename, String Host, String Port,
            String kespaceName, String packageName, List<String> classNames, String outputPackagePath)
    {
        try
        {
            String samplePersistenceTemplate = "<!--PERSISTENCE_SAMPLE-->" + "\n"
                    + "<persistence-unit name=\"%DATABASE_PU\">" + "\n"
                    + "<provider>com.impetus.kundera.KunderaPersistence</provider>" + "\n"
                    + "<!--<class>classes</class>-->" + "\n"
                    + "<exclude-unlisted-classes>true</exclude-unlisted-classes>" + "\n" + "<properties>" + "\n"
                    + "<property name=\"kundera.nodes\" value=\"%DATABASE_HOST\"/>" + "\n"
                    + "<property name=\"kundera.port\" value=\"%DATABASE_PORT\"/>" + "\n"
                    + "<property name=\"kundera.keyspace\" value=\"%KUNDERA_KEYSPACE\"/>" + "\n"
                    + "<property name=\"kundera.dialect\" value=\"%KUNDERA_DIALECT\"/>" + "\n"
                    + "<property name=\"kundera.client.property\" value=\"%CLIENT_PROPERTY\"/>" + "\n"
                    + "<property name=\"kundera.client.lookup.class\" value=\"%LOOKUP_CLASS\"/>" + "\n"
                    + "</properties>" + "\n" + "</persistence-unit>" + "\n";
            String pathToMetaInf = outputPackagePath + "/META-INF";

            String pathToPersistenceXml = pathToMetaInf + "/persistence.xml";

            File persistenceXmlFile = new File(pathToPersistenceXml);

            if (persistenceXmlFile.exists())
            {
                FileWriter writer = new FileWriter(pathToPersistenceXml, true);
                BufferedReader reader = new BufferedReader(new FileReader(persistenceXmlFile));
                String line = "", oldtext = "";
//                String classTag = "<class>" + packageName + "." + className + "</class>\n";
                // classTag = classTag + "<!--<class>classes</class>-->";

                StringBuilder stringBuilder = new StringBuilder();
                for(String className :  classNames )
                {
                    stringBuilder.append("<class>" + packageName + "." + className + "</class>\n");
                }
                
                String classTag = stringBuilder.toString();
                
                while ((line = reader.readLine()) != null)
                {
                    oldtext += line + "\n";
                }

                if (oldtext.contains(datastoreToPuName.get(datastorename)))
                {
                    String checkHostPort = "<property name=\"kundera.nodes\" value=\"" + Host + "\"/>" + "\n"
                            + "<property name=\"kundera.port\" value=\"" + Port + "\"/>" + "\n"
                            + "<property name=\"kundera.keyspace\" value=\"" + kespaceName + "\"/>" + "\n";
                    if (oldtext.contains(checkHostPort))
                    {

                        String newtext5 = "";
//                        if (!oldtext.contains("<class>" + packageName + "." + className + "</class>"))
//                        {
                            newtext5 = oldtext.replaceAll("<!--<class>classes</class>-->", classTag);
                            writer.write(newtext5);
//                        }
//                        else
//                        {
//                            writer.write(oldtext);
//                        }
                    }
                    else
                    {
                        String textToReplace = oldtext.substring(
                                oldtext.indexOf("<persistence-unit name=\"" + datastoreToPuName.get(datastorename)
                                        + "\">"),
                                oldtext.indexOf(
                                        "</persistence-unit>",
                                        oldtext.indexOf("<persistence-unit name=\""
                                                + datastoreToPuName.get(datastorename) + "\">")));
                        textToReplace = textToReplace + "</persistence-unit>";
                        oldtext = oldtext.replaceAll(textToReplace, "");

                        String newtext1 = oldtext.replaceAll("%DATABASE_PU", datastoreToPuName.get(datastorename));
                        String newtext2 = newtext1.replaceAll("%DATABASE_HOST", Host);
                        String newtext3 = newtext2.replaceAll("%DATABASE_PORT", Port);
                        String newtext4 = newtext3.replaceAll("%KUNDERA_KEYSPACE", kespaceName);
                        String newtext5 = newtext4.replaceAll("<!--<class>classes</class> -->", classTag);
                        String newtext6 = newtext5.replaceAll("%KUNDERA_DIALECT", datastorename);
                        String newtext7 = newtext6.replaceAll("%CLIENT_PROPERTY", "KunderaConnection.xml");
                        String newtext8 = newtext7.replaceAll("%LOOKUP_CLASS",
                                datastoreToClientFactoryName.get(datastorename));
                        newtext8 = newtext8.replaceAll("<!--PERSISTENCE_SAMPLE-->", samplePersistenceTemplate);
                        writer.write(newtext8);
                    }
                }
                else
                {
                    String newtext1 = oldtext.replaceAll("%DATABASE_PU", datastoreToPuName.get(datastorename));
                    String newtext2 = newtext1.replaceAll("%DATABASE_HOST", Host);
                    String newtext3 = newtext2.replaceAll("%DATABASE_PORT", Port);
                    String newtext4 = newtext3.replaceAll("%KUNDERA_KEYSPACE", kespaceName);
                    String newtext5 = newtext4.replaceAll("<!--<class>classes</class>-->", classTag);
                    String newtext6 = newtext5.replaceAll("%KUNDERA_DIALECT", datastorename);
                    String newtext7 = newtext6.replaceAll("%CLIENT_PROPERTY", "KunderaConnection.xml");
                    String newtext8 = newtext7.replaceAll("%LOOKUP_CLASS",
                            datastoreToClientFactoryName.get(datastorename));
                    newtext8 = newtext8.replaceAll("<!--PERSISTENCE_SAMPLE-->", samplePersistenceTemplate);
                    writer.write(newtext8);
                }              
                writer.close();
            }
            else
            {
                throw new UnsupportedOperationException("Persistenxe.xml not Found at location: " + pathToMetaInf);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
