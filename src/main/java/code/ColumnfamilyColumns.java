package code;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "schema_columns", schema = "system@cassandra_pu")
public class ColumnfamilyColumns
{

    @EmbeddedId
    ColumnsCompositekey key;

    public ColumnfamilyColumns()
    {

    }

    public ColumnfamilyColumns(ColumnsCompositekey key)
    {
        this.key = key;

    }

    @Column(name = "component_index")
    private int component_index;

    @Column(name = "index_name")
    private String index_name;

    @Column(name = "index_options")
    private String index_options;

    @Column(name = "index_type")
    private String index_type;

    @Column(name = "validator")
    private String validator;

    public ColumnsCompositekey getKey()
    {
        return key;
    }

    public void setKey(ColumnsCompositekey key)
    {
        this.key = key;
    }

    public int getComponent_index()
    {
        return component_index;
    }

    public void setComponent_index(int component_index)
    {
        this.component_index = component_index;
    }

    public String getIndex_name()
    {
        return index_name;
    }

    public void setIndex_name(String index_name)
    {
        this.index_name = index_name;
    }

    public String getIndex_options()
    {
        return index_options;
    }

    public void setIndex_options(String index_options)
    {
        this.index_options = index_options;
    }

    public String getIndex_type()
    {
        return index_type;
    }

    public void setIndex_type(String index_type)
    {
        this.index_type = index_type;
    }

    public String getValidator()
    {
        return validator;
    }

    public void setValidator(String validator)
    {
        this.validator = validator;
    }

}
