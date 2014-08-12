package configfamily;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class BuildSubDocEntity_New
{

    public void buildSubDocEntity(String database, String collection, List<String> colName, List<String> colType,
            int startIndex, int endIndex, List<String> preColList, String CLASSES_PATH, String CLASSES_OUTPUT,
            String packge)
    {
        // System.out.println(startIndex);
        // System.out.println(endIndex);
        // String CLASSES_OUTPUT=CLASSES_PATH+"WEB-INF/classes";

        try
        {
            preColList.add(colName.get(startIndex));
            // System.out.println(preColList);
            String field = "";
            for (int i = 0; i < preColList.size(); i++)
            {
                field = field + preColList.get(i).substring(0, 1).toUpperCase() + preColList.get(i).substring(1);
            }
            BuildCollectionEntity_New bce = new BuildCollectionEntity_New();
            bce.fillcompositeclass(database, collection, CLASSES_PATH, packge, preColList);
            for (int i = startIndex + 1; i < endIndex; i++)
            {
                colName.set(i, colName.get(i).replaceAll(colName.get(startIndex) + ".", ""));

            }
            int j = startIndex + 1;
            while (j < endIndex)
            {
                if (colType.get(j).equals("Object"))
                {
                    String ftype = collection.substring(0, 1).toUpperCase() + collection.substring(1) + field
                            + colName.get(j).substring(0, 1).toUpperCase() + colName.get(j).substring(1);
                    // BuildCollectionEntity bce=new BuildCollectionEntity();

                    bce.fillObjectcolumn(database, colName.get(j), ftype, CLASSES_PATH, collection.substring(0, 1)
                            .toUpperCase() + collection.substring(1) + field, packge);
                    int k;
                    for (k = j + 1; k < colType.size(); k++)
                        if (colName.get(k).contains(colName.get(j)))
                        {

                            // j=k;
                            System.out.println("j:" + j);
                            continue;
                        }
                        else
                        {
                            // j=k;
                            System.out.println("j=" + j);
                            break;
                        }
                    BuildSubDocEntity_New bsde = new BuildSubDocEntity_New();
                    // bsde.buildSubDocEntity(database, collection, colName,
                    // colType, startIndex, endIndex, preColList, CLASSES_PATH,
                    // packge)
                    bsde.buildSubDocEntity(database, collection, colName, colType, j, k, preColList, CLASSES_PATH,
                            CLASSES_OUTPUT, packge);
                    j = k;

                }
                else
                {

                    bce.fillcolumn(database, colName.get(j), colType.get(j), collection.substring(0, 1).toUpperCase()
                            + collection.substring(1) + field, CLASSES_PATH, packge);
                    j++;

                }

            }
            FileWriter writer = new FileWriter(CLASSES_PATH + "/" + packge + "/" + database + "/"
                    + collection.substring(0, 1).toUpperCase() + collection.substring(1) + field + ".java", true);
            writer.write("}");
            writer.close();

            File sourceFile = new File(CLASSES_PATH + "/" + packge + "/" + database + "/"
                    + collection.substring(0, 1).toUpperCase() + collection.substring(1) + field + ".java");

            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            StandardJavaFileManager sjfm = compiler.getStandardFileManager(null, null, null);
            String classpath = System.getProperty("java.class.path");
            String testpath = ":" + CLASSES_PATH + "/lib/persistence-api-2.0.jar";
            // /home/ashish/.m2/repository/org/mongodb/mongo-java-driver/2.9.1/mongo-java-driver-2.9.1.jar
            testpath = testpath + ":" + CLASSES_PATH + "/lib/mongo-java-driver-2.9.1.jar";
            testpath = testpath + ":" + CLASSES_PATH + "/lib/jackson-annotations-2.4.1.jar";
            testpath = testpath + ":" + CLASSES_OUTPUT;

            List<String> optionList = new ArrayList<String>();
            optionList.addAll(Arrays.asList("-classpath", testpath, "-d", CLASSES_OUTPUT));

            Iterable fileObjects = sjfm.getJavaFileObjectsFromFiles(Arrays.asList(sourceFile));
            JavaCompiler.CompilationTask task = compiler.getTask(null, sjfm, null, optionList, null, fileObjects);
            task.call();
            sjfm.close();
            preColList.remove(preColList.size() - 1);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
