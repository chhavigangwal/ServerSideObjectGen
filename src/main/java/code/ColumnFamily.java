package code;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "schema_columnfamilies", schema = "system@cassandra_pu")
public class ColumnFamily
{

    @EmbeddedId
    KunderaCompositeKey key;

    public ColumnFamily()
    {

    }

    public ColumnFamily(KunderaCompositeKey key)
    {
        this.key = key;

    }

    @Column(name = "bloom_filter_fp_chance")
    private Double bloom_filter_fp_chance;

    @Column(name = "caching")
    private String caching;

    @Column(name = "column_aliases")
    private String column_aliases;

    @Column(name = "comment")
    private String comment;

    @Column(name = "compaction_strategy_class")
    private String compaction_strategy_class;

    @Column(name = "compaction_strategy_options")
    private String compaction_strategy_options;

    @Column(name = "comparator")
    private String comparator;

    @Column(name = "compression_parameters")
    private String compression_parameters;

    @Column(name = "default_read_consistency")
    private String default_read_consistency;

    @Column(name = "default_validator")
    private String default_validator;

    @Column(name = "default_write_consistency")
    private String default_write_consistency;

    @Column(name = "gc_grace_seconds")
    private String gc_grace_seconds;

    @Column(name = "id")
    private int id;

    @Column(name = "key_alias")
    private String key_alias;

    @Column(name = "key_aliases")
    private String key_aliases;

    @Column(name = "key_validator")
    private String key_validator;

    @Column(name = "local_read_repair_chance")
    private Double local_read_repair_chance;

    @Column(name = "max_compaction_threshold")
    private int max_compaction_threshold;

    @Column(name = "min_compaction_threshold")
    private int min_compaction_threshold;

    @Column(name = "read_repair_chance")
    private Double read_repair_chance;

    @Column(name = "replicate_on_write")
    private boolean replicate_on_write;

    @Column(name = "subcomparator")
    private String subcomparator;

    @Column(name = "type")
    private String type;

    @Column(name = "value_alias")
    private String value_alias;

    public KunderaCompositeKey getKey()
    {
        return key;
    }

    public void setKey(KunderaCompositeKey key)
    {
        this.key = key;
    }

    public Double getBloom_filter_fp_chance()
    {
        return bloom_filter_fp_chance;
    }

    public void setBloom_filter_fp_chance(Double bloom_filter_fp_chance)
    {
        this.bloom_filter_fp_chance = bloom_filter_fp_chance;
    }

    public String getCaching()
    {
        return caching;
    }

    public void setCaching(String caching)
    {
        this.caching = caching;
    }

    public String getColumn_aliases()
    {
        return column_aliases;
    }

    public void setColumn_aliases(String column_aliases)
    {
        this.column_aliases = column_aliases;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public String getCompaction_strategy_class()
    {
        return compaction_strategy_class;
    }

    public void setCompaction_strategy_class(String compaction_strategy_class)
    {
        this.compaction_strategy_class = compaction_strategy_class;
    }

    public String getCompaction_strategy_options()
    {
        return compaction_strategy_options;
    }

    public void setCompaction_strategy_options(String compaction_strategy_options)
    {
        this.compaction_strategy_options = compaction_strategy_options;
    }

    public String getComparator()
    {
        return comparator;
    }

    public void setComparator(String comparator)
    {
        this.comparator = comparator;
    }

    public String getCompression_parameters()
    {
        return compression_parameters;
    }

    public void setCompression_parameters(String compression_parameters)
    {
        this.compression_parameters = compression_parameters;
    }

    public String getDefault_read_consistency()
    {
        return default_read_consistency;
    }

    public void setDefault_read_consistency(String default_read_consistency)
    {
        this.default_read_consistency = default_read_consistency;
    }

    public String getDefault_validator()
    {
        return default_validator;
    }

    public void setDefault_validator(String default_validator)
    {
        this.default_validator = default_validator;
    }

    public String getDefault_write_consistency()
    {
        return default_write_consistency;
    }

    public void setDefault_write_consistency(String default_write_consistency)
    {
        this.default_write_consistency = default_write_consistency;
    }

    public String getGc_grace_seconds()
    {
        return gc_grace_seconds;
    }

    public void setGc_grace_seconds(String gc_grace_seconds)
    {
        this.gc_grace_seconds = gc_grace_seconds;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getKey_alias()
    {
        return key_alias;
    }

    public void setKey_alias(String key_alias)
    {
        this.key_alias = key_alias;
    }

    public String getKey_aliases()
    {
        return key_aliases;
    }

    public void setKey_aliases(String key_aliases)
    {
        this.key_aliases = key_aliases;
    }

    public String getKey_validator()
    {
        return key_validator;
    }

    public void setKey_validator(String key_validator)
    {
        this.key_validator = key_validator;
    }

    public Double getLocal_read_repair_chance()
    {
        return local_read_repair_chance;
    }

    public void setLocal_read_repair_chance(Double local_read_repair_chance)
    {
        this.local_read_repair_chance = local_read_repair_chance;
    }

    public int getMax_compaction_threshold()
    {
        return max_compaction_threshold;
    }

    public void setMax_compaction_threshold(int max_compaction_threshold)
    {
        this.max_compaction_threshold = max_compaction_threshold;
    }

    public int getMin_compaction_threshold()
    {
        return min_compaction_threshold;
    }

    public void setMin_compaction_threshold(int min_compaction_threshold)
    {
        this.min_compaction_threshold = min_compaction_threshold;
    }

    public Double getRead_repair_chance()
    {
        return read_repair_chance;
    }

    public void setRead_repair_chance(Double read_repair_chance)
    {
        this.read_repair_chance = read_repair_chance;
    }

    public boolean getReplicate_on_write()
    {
        return replicate_on_write;
    }

    public void setReplicate_on_write(boolean replicate_on_write)
    {
        this.replicate_on_write = replicate_on_write;
    }

    public String getSubcomparator()
    {
        return subcomparator;
    }

    public void setSubcomparator(String subcomparator)
    {
        this.subcomparator = subcomparator;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getValue_alias()
    {
        return value_alias;
    }

    public void setValue_alias(String value_alias)
    {
        this.value_alias = value_alias;
    }

}
