/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.productionordermanagement.domain;

import eapli.base.depositmanagement.domain.Deposit;
import eapli.base.machinemanagement.domain.Machine;
import eapli.base.rawmaterialmanagement.domain.RawMaterial;
import eapli.framework.domain.model.ValueObject;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

/**
 *
 * @author Bruno Pereira
 */
@Embeddable
public class Consumption implements ValueObject{
    
    private static final long serialVersionUID = 1L;

    private double consumption_quantity;


    private String rawMaterialId;


    private String depositId;
    
    public Consumption() {
        //for ORM
    }

    public Consumption(double quantity, String rawMaterialId, String depositId) {
        this.consumption_quantity = quantity;
        this.rawMaterialId = rawMaterialId;
        this.depositId = depositId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.rawMaterialId);
        hash = 79 * hash + Objects.hashCode(this.depositId);
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
        final Consumption other = (Consumption) obj;
        if (Double.doubleToLongBits(this.consumption_quantity) != Double.doubleToLongBits(other.consumption_quantity)) {
            return false;
        }
        if (!Objects.equals(this.rawMaterialId, other.rawMaterialId)) {
            return false;
        }
        if (!Objects.equals(this.depositId, other.depositId)) {
            return false;
        }
        return true;
    }
}
