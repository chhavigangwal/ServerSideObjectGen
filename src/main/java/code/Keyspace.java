package code;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "schema_keyspaces", schema = "system@cassandra_pu")
public class Keyspace
{

    public Keyspace()
    {

    }

    @Id
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

    @Column(name = "durable_writes")
    private boolean durable_writes;

    @Column(name = "strategy_class")
    private String strategy_class;

    public boolean isDurable_writes()
    {
        return durable_writes;
    }

    public void setDurable_writes(boolean durable_writes)
    {
        this.durable_writes = durable_writes;
    }

    public String getStrategy_class()
    {
        return strategy_class;
    }

    public void setStrategy_class(String strategy_class)
    {
        this.strategy_class = strategy_class;
    }

    public String getStrategy_options()
    {
        return strategy_options;
    }

    public void setStrategy_options(String strategy_options)
    {
        this.strategy_options = strategy_options;
    }

    @Column(name = "strategy_options")
    private String strategy_options;

}
