package eapli.base.productionordermanagement.domain;

import eapli.base.core.productmanager.domain.Batch;
import eapli.base.depositmanagement.domain.Deposit;
import eapli.base.machinemanagement.domain.Machine;
import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;

@Embeddable
public class DeliverProduction implements ValueObject {

    private static final long serialVersionUID = 1L;

    private double quantity;

    private String idDeposit;

    private String idBatch;

    public DeliverProduction() {
        //for ORM
    }

    public DeliverProduction(double quantity, String idDeposit, String idBatch) {
        this.quantity = quantity;
        this.idDeposit = idDeposit;
        this.idBatch = idBatch;
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
        final DeliverProduction other = (DeliverProduction) obj;
        if (Double.doubleToLongBits(this.quantity) != Double.doubleToLongBits(other.quantity)) {
            return false;
        }
        return true;
    }
}
