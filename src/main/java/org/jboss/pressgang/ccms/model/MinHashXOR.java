package org.jboss.pressgang.ccms.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;
import org.jboss.pressgang.ccms.model.base.AuditedEntity;

@Entity
@Audited
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name = "MinHashXOR")
public class MinHashXOR extends AuditedEntity implements java.io.Serializable {
    public static final String SELECT_ALL_QUERY = "SELECT xor FROM MinHashXOR as xor";

    private Integer minHashXORId;
    private Integer minHashXOR;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "MinHashXORID", unique = true, nullable = false)
    public Integer getMinHashXORId() {
        return minHashXORId;
    }

    public void setMinHashXORId(final Integer minHashXORId) {
        this.minHashXORId = minHashXORId;
    }


    @Column(name = "MinHashXOR", unique = true, nullable = false)
    @NotNull
    public Integer getMinHashXOR() {
        return minHashXOR;
    }

    public void setMinHashXOR(final Integer minHashXOR) {
        this.minHashXOR = minHashXOR;
    }

    @Override
    @Transient
    public Integer getId() {
        return minHashXORId;
    }
}
