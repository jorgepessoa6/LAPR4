/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.productionordermanagement.domain;

import eapli.base.machinemanagement.domain.Machine;
import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.util.Calendar;
import java.util.Objects;

/**
 *
 * @author Bruno Pereira
 */
@Embeddable
public class MachineRawTime implements ValueObject{
    
    private static final long serialVersionUID = 1L;
    
    private long machineRawTime;

    private String machineId;

    public MachineRawTime() {
        //for ORM
    }

    public MachineRawTime(long machineRawTime, String machineId) {
        this.machineRawTime = machineRawTime;
        this.machineId = machineId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.machineRawTime);
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
        final MachineRawTime other = (MachineRawTime) obj;
        if (!Objects.equals(this.machineRawTime, other.machineRawTime)) {
            return false;
        }
        return true;
    }
    
}
