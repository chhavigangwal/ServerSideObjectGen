package code;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class KunderaCompositeKey implements Serializable
{

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

    public String getColumnfamily_name()
    {
        return columnfamily_name;
    }

    public void setColumnfamily_name(String columnfamily_name)
    {
        this.columnfamily_name = columnfamily_name;
    }

    public KunderaCompositeKey()
    {

    }

}