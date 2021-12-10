package eapli.base.productionordermanagement.domain;

import eapli.base.core.productmanager.domain.Product;
import eapli.base.machinemanagement.domain.Machine;
import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class DeflectionProduction implements ValueObject {

    private static final long serialVersionUID = 1L;

    private double quantity;

    private String idProduct;

    public DeflectionProduction() {
        //for ORM
    }

    public DeflectionProduction(double quantity, String idProduct) {
        this.quantity = quantity;
        this.idProduct = idProduct;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + (int) (Double.doubleToLongBits(this.quantity) ^ (Double.doubleToLongBits(this.quantity) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DeflectionProduction other = (DeflectionProduction) obj;
        if (Double.doubleToLongBits(this.quantity) != Double.doubleToLongBits(other.quantity)) {
            return false;
        }
        return true;
    }

}
