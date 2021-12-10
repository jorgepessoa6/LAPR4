/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.productionordermanagement.domain;

import eapli.framework.domain.model.ValueObject;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Bruno Pereira
 */
@Embeddable
public class Order implements ValueObject{
    private static final long serialVersionUID = 1L;

    @Column
    private String orderId;

    public String getOrderId() {
        return orderId;
    }

    public Order() {
        //for ORM
    }

    public Order(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + Objects.hashCode(this.orderId);
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
        final Order other = (Order) obj;
        return this.orderId.equals(other.orderId);
    }


}
