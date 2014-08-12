package code;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ColumnsCompositekey implements Serializable
{

    @Override
    public boolean equals(Object obj)
    {
        // TODO Auto-generated method stub
        return super.equals(obj);
    }

    @Override
    public int hashCode()
    {
        // TODO Auto-generated method stub
        return super.hashCode();
    }

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    @Column(name = "keyspace_name")
    private String keyspace_name;

    public String getKeyspace_name()
    {
        return keyspace_name;
    }

    public void setKeyspace_name(String keyspace_name)
    {
        this.keyspace_name = keyspace_name;
    }

    @Column(name = "columnfamily_name")
    private String columnfamily_name;

    @Column(name = "column_name")
    private String column_name;

    public String getColumnfamily_name()
    {
        return columnfamily_name;
    }

    public void setColumnfamily_name(String columnfamily_name)
    {
        this.columnfamily_name = columnfamily_name;
    }

    public String getColumn_name()
    {
        return column_name;
    }

    public void setColumn_name(String column_name)
    {
        this.column_name = column_name;
    }

    public ColumnsCompositekey()
    {

    }

}