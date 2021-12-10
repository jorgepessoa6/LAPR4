package eapli.base.rawmaterialmanagement.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.general.domain.model.Designation;
import eapli.framework.util.HashCoder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * @author Miguel Fortes (1171611)
 */
@Entity
@XmlRootElement
public class RawMaterialCategory implements AggregateRoot<Designation> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long pk;

    @Column(unique = true, nullable = false)
    @XmlElement
    private final Designation code;

    @Column(nullable = false)
    @XmlElement
    private final String description;


    public RawMaterialCategory(final Designation code, final String description) {
        this.code = code;
        this.description = description;
    }

    public RawMaterialCategory() {
        // for ORM
        code = null;
        description = null;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RawMaterialCategory)) {
            return false;
        }

        final RawMaterialCategory that = (RawMaterialCategory) o;
        return code == that.code && description == that.description;
    }

    @Override
    public int hashCode() {
        return new HashCoder().with(code).with(description).code();
    }

    @Override
    public boolean sameAs(Object other) {
        return false;
    }

    @Override
    public String toString() {
        return "Category: " + this.code + " " + this.description;
    }

    public Designation categoryCode() {
        return this.code;
    }

    public String categoryDescription() {
        return this.description;
    }

    @Override
    public Designation identity() {
        return this.code;
    }
}
