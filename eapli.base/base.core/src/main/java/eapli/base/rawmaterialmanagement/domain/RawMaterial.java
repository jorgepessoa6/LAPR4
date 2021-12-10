package eapli.base.rawmaterialmanagement.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.general.domain.model.Designation;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * @author Miguel Fortes (1171611)
 */
@Entity
@XmlRootElement
public class RawMaterial implements AggregateRoot<Designation> {

    private static final long serialVersionUID = 1L;

    @Version
    private Long version;

    // ORM primary key
    @Id
    @GeneratedValue
    private Long pk;

    // business ID
    @Column(unique = true, nullable = false)
    @XmlElement
    private Designation internalCode;

    @XmlElement
    private String description;

    @ManyToOne()
    @XmlElement
    private RawMaterialCategory category;

    @Lob
    @Column(length = 100000)
    @XmlElement
    private DataSheet Datasheet; // FIXME Needs to be a PDF file??

    protected RawMaterial() {
        //for ORM
    }

    public RawMaterial(final Designation internalCode, final String description, final RawMaterialCategory category, final DataSheet Datasheet) {
        if (internalCode.isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.internalCode = internalCode;
        this.description = description;
        this.category = category;
        this.Datasheet = Datasheet;
    }

    public String description() {
        return this.description;
    }

    public RawMaterialCategory category() {
        return category;
    }

    public void changeDescriptionTo(final String newDescription) {
        this.description = newDescription;
    }

    @Override
    public Designation identity() {
        return internalCode;
    }

    @Override
    public boolean hasIdentity(final Designation id) {
        return id.equals(this.internalCode);
    }

    @Override
    public boolean sameAs(Object o) {
        if (!this.equals(o)) {
            return false;
        }
        final RawMaterial other = (RawMaterial) o;
        return internalCode.equals(other.internalCode) && description.equals(other.description);
    }

    @Override
    public boolean equals(final Object o) {
        return DomainEntities.areEqual(this, o);
    }

    @Override
    public int hashCode() {
        return DomainEntities.hashCode(this);
    }

    @Override
    public String toString() {
        return "RawMaterial{" + "internalCode=" + internalCode + ", description=" + description + '}';
    }
     

}
