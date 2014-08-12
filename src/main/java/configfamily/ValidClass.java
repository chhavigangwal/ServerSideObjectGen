package configfamily;

public class ValidClass
{

    public boolean isClass(String className)
    {
        boolean exist = true;
        try
        {
            Class.forName(className);
        }
        catch (ClassNotFoundException e)
        {
            exist = false;
        }
        return exist;
    }

}
