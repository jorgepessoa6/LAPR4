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
public class ActiveTime implements ValueObject{
    
    private static final long serialVersionUID = 1L;
    
    private long activeTime;

    public ActiveTime() {
        //for ORM
    }

    public ActiveTime(long activeTime) {
        this.activeTime = activeTime;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.activeTime);
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
        final ActiveTime other = (ActiveTime) obj;
        if (!Objects.equals(this.activeTime, other.activeTime)) {
            return false;
        }
        return true;
    }
}
