/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.productionordermanagement.domain;

import eapli.base.machinemanagement.domain.Machine;
import eapli.base.rawmaterialmanagement.domain.RawMaterial;
import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

/**
 *
 * @author Bruno Pereira
 */
@Embeddable
public class DeflectionConsumption implements ValueObject{
    
    private static final long serialVersionUID = 1L;
    
    private double quantity;

    private String idMaterial;

    public DeflectionConsumption() {
        //for ORM
    }

    public DeflectionConsumption(double quantity, String idMaterial) {
        this.idMaterial = idMaterial;
        this.quantity = quantity;
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
        final DeflectionConsumption other = (DeflectionConsumption) obj;
        if (Double.doubleToLongBits(this.quantity) != Double.doubleToLongBits(other.quantity)) {
            return false;
        }
        return true;
    }
    
    
    
}
