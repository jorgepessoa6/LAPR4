/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.productionordermanagement.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import java.util.Calendar;
import java.util.Objects;

/**
 *
 * @author Bruno Pereira
 */
@Embeddable
public class RawTime implements ValueObject{
    
    private static final long serialVersionUID = 1L;
    
    private long rawTime;

    public RawTime() {
        //for ORM
    }

    public RawTime(long rawTime) {
        this.rawTime = rawTime;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.rawTime);
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
        final RawTime other = (RawTime) obj;
        if (!Objects.equals(this.rawTime, other.rawTime)) {
            return false;
        }
        return true;
    }
    
    
}
