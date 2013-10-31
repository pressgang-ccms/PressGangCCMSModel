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
@Table(name = "MinHashXOR")
public class MinHashXOR extends AuditedEntity implements java.io.Serializable {
    private Integer minHashXORId;
    private Integer minHashXOR;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "MinHashXORID", unique = true, nullable = false)
    public Integer getMinHashXORId() {
        return minHashXORId;
    }

    public void setMinHashXORId(final Integer minHashId) {
        this.minHashXORId = minHashId;
    }


    @Column(name = "minHashXOR", unique = false, nullable = false)
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
