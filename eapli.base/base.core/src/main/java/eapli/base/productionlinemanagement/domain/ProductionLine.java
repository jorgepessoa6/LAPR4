package eapli.base.productionlinemanagement.domain;

import eapli.base.machinemanagement.domain.Machine;
import eapli.base.productionlinemanagement.repositories.ProductionLineRepository;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.general.domain.model.Designation;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.*;

/**
 * @author Jorge Pessoa (1180761)
 */

@XmlRootElement
@Entity
public class ProductionLine implements AggregateRoot<Designation>{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long pk;

    @Version
    private Long version;

    //@ElementCollection
    @OneToMany(cascade = CascadeType.ALL)
    private List<Machine> machines;; // FIXME  Missing Machine class

    @XmlElement
    @Column(unique = true, nullable = false)
    private Designation productionLineId;

    private ProductionLineStatus productionLineStatus;

    private Calendar dateLastProcessement;

    public ProductionLine(final Designation productionLineId) {
        this.productionLineId = productionLineId;
        this.machines = new ArrayList<>();
        this.productionLineStatus = ProductionLineStatus.NON_ACTIVE;
        this.dateLastProcessement = null;
    }

    protected ProductionLine() {
        //for ORM
    }

    public List<Machine> getMachines() {
        return machines;
    }

    public boolean addMachine(Machine machine){
        return machines.add(machine);
    }

    public Calendar getDateLastProcessement() {
        return dateLastProcessement;
    }

    public void setDateLastProcessement(Calendar dateLastProcessement) {
        this.dateLastProcessement = dateLastProcessement;
    }

    public ProductionLineStatus getProductionLineStatus() {
        return productionLineStatus;
    }

    public void setProductionLineStatus(ProductionLineStatus productionLineStatus) {
        this.productionLineStatus = productionLineStatus;
    }

    @Override
    public boolean sameAs(Object o) {
        if (!this.equals(o)) {
            return false;
        }
        final ProductionLine other = (ProductionLine) o;
        return productionLineId.equals(other.productionLineId) && machines.equals(other.machines);

    }
    @Override
    public Designation identity() {
        return productionLineId;
    }

    @Override
    public String toString() {
        return "ProductionLine{" +
                "productionLineId=" + productionLineId +
                '}';
    }
}


