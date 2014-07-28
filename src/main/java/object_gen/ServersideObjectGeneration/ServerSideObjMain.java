package object_gen.ServersideObjectGeneration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.cassandra.thrift.Cassandra.system_update_keyspace_result;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;


public class ServerSideObjMain 

	
{
	public static void main(String[] args) {
				
	
		String filePath= args[0];
		String outputPackage = args[1];
		Map<String, Object> nodeProperties = readConnectionDetails(filePath);
				System.out.println("current path for main "+System.getProperty("user.dir"));
		DynamicObjectGeneration dobg = new DynamicObjectGeneration();
		
		ArrayList<Map<String, String>> entities = (ArrayList<Map<String, String>>)nodeProperties.get("entities");
		
		if (entities.isEmpty()) {
			
			dobg.generateObject(nodeProperties.get("DATA_STORE").toString(), nodeProperties.get("HOST").toString(),
					nodeProperties.get("PORT").toString(), nodeProperties.get("database").toString(),
					nodeProperties.get("packageName").toString(), outputPackage);
			
		}
		else{
		
		Map<String, String> entity = new HashMap<String, String>();
		
		for (int i = 0; i < entities.size(); i++) {
			
			entity = entities.get(i);
		
			dobg.generateObject(nodeProperties.get("DATA_STORE").toString(), nodeProperties.get("HOST").toString(), nodeProperties.get("PORT").toString(), nodeProperties.get("database").toString(), entity.get("table"),
					entity.get("className"), nodeProperties.get("packageName").toString(), entity.get("isRelationship"), entity.get("relationshipType"),entity.get("attributeName"), entity.get("relationshipTable"), entity.get("relationshipClass"),
					entity.get("relationshipDATA_STORE"),entity.get("relationshipHost"), entity.get("relationshipPort"),outputPackage);			
		}
		}
		
		/*ProcessBuilder pb = new ProcessBuilder("/home/impadmin/workspace/ServersideObjectGeneration/src/DataViewer.sh");
    	pb.redirectError();

    	  	
    	    try {
				Process p = pb.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		
	}
	
    
    
    public static Map<String, Object> readConnectionDetails(String filePath) {

		Map<String, Object> nodeProperties = new HashMap<String, Object>();
		try {
			nodeProperties = new ObjectMapper().readValue(new File(filePath),
					HashMap.class);
			System.out.println(nodeProperties);

		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nodeProperties;

	}
}
