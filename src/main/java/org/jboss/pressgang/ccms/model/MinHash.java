package org.jboss.pressgang.ccms.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;
import org.jboss.pressgang.ccms.model.base.AuditedEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Audited
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name = "MinHash", uniqueConstraints = @UniqueConstraint(columnNames = {"TopicID", "MinHashFuncID"}))
public class MinHash extends AuditedEntity implements java.io.Serializable {
    public static final String SELECT_ALL_QUERY = "SELECT minhash FROM MinHash as MinHash";

    private Integer minHashId;
    private Integer minHashFuncID;
    private Topic topic;
    private Integer minHash;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "MinHashID", unique = true, nullable = false)
    public Integer getMinHashId() {
        return minHashId;
    }

    public void setMinHashId(final Integer minHashId) {
        this.minHashId = minHashId;
    }

    @Column(name = "MinHashFuncID", unique = false, nullable = false)
    public Integer getMinHashFuncID() {
        return minHashFuncID;
    }

    public void setMinHashFuncID(final Integer minHashFuncID) {
        this.minHashFuncID = minHashFuncID;
    }

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "TopicID", nullable = false)
    @NotNull
    public Topic getTopic() {
        return topic;
    }

    public void setTopic(final Topic topic) {
        this.topic = topic;
    }

    @Column(name = "MinHash", unique = false, nullable = false)
    public Integer getMinHash() {
        return minHash;
    }

    public void setMinHash(final Integer minHash) {
        this.minHash = minHash;
    }

    @Override
    @Transient
    public Integer getId() {
        return minHashId;
    }
}
