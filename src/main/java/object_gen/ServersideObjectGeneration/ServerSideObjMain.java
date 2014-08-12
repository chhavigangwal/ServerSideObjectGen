package object_gen.ServersideObjectGeneration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class ServerSideObjMain

{
    public static void main(String[] args)
    {
        String filePath = args[0];
        String outputPackage = args[1];
        Map<String, ArrayList<Map<String, Object>>> connectionDetails = readConnectionDetails(filePath);

        DynamicObjectGeneration dobg = new DynamicObjectGeneration();
        ArrayList<Map<String, Object>> connections = connectionDetails.get("properties");

        for (int v = 0; v < connections.size(); v++)
        {
            Map<String, Object> nodeProperties = connections.get(v);

            ArrayList<Map<String, String>> entities = (ArrayList<Map<String, String>>) nodeProperties.get("entities");
            ArrayList<String> databases = (ArrayList<String>) nodeProperties.get("database");
            if (entities.isEmpty())
            {
                for (int i = 0; i < databases.size(); i++)
                {
                    dobg.generateObjectsForDatabase(nodeProperties.get("DATA_STORE").toString(),
                            nodeProperties.get("HOST").toString(), nodeProperties.get("PORT").toString(),
                            nodeProperties.get("setSchema").toString(), databases.get(i),
                            nodeProperties.get("packageName").toString(), outputPackage);
                }
            }
            else
            {
                Map<String, String> entity = new HashMap<String, String>();

                for (int i = 0; i < entities.size(); i++)
                {
                    entity = entities.get(i);
                    dobg.generateObjectForTable(nodeProperties.get("DATA_STORE").toString(), nodeProperties.get("HOST")
                            .toString(), nodeProperties.get("PORT").toString(), nodeProperties.get("setSchema")
                            .toString(), databases.get(0), entity.get("table"), entity.get("className"), nodeProperties
                            .get("packageName").toString(), entity.get("isRelationship"), entity
                            .get("relationshipType"), entity.get("joinColumnAttribute"),entity.get("inverseJoinColumnAttribute"),entity.get("joinTable"), entity.get("relationshipTable"),
                            entity.get("relationshipClass"), entity.get("relationshipDATA_STORE"), entity
                                    .get("relationshipHost"), entity.get("relationshipPort"), entity
                                    .get("relationshipPackage"), outputPackage);
                }
            }
        }

        /*
         * ProcessBuilder pb = new ProcessBuilder(
         * "/home/impadmin/workspace/ServersideObjectGeneration/src/DataViewer.sh"
         * ); pb.redirectError();
         * 
         * 
         * try { Process p = pb.start(); } catch (IOException e) { // TODO
         * Auto-generated catch block e.printStackTrace(); }
         */

    }

    public static Map<String, ArrayList<Map<String, Object>>> readConnectionDetails(String filePath)
    {

        Map<String, ArrayList<Map<String, Object>>> connectionDetails = new HashMap<String, ArrayList<Map<String, Object>>>();
        try
        {
            connectionDetails = new ObjectMapper().readValue(new File(filePath), HashMap.class);
        }
        catch (JsonParseException e)
        {
            e.printStackTrace();
        }
        catch (JsonMappingException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return connectionDetails;
    }
}
