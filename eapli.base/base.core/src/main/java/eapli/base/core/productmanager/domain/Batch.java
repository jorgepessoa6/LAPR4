/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.core.productmanager.domain;

import eapli.framework.domain.model.ValueObject;
import java.util.Objects;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author Bruno Pereira
 */
@Embeddable
public class Batch implements ValueObject{
    
    private static final long serialVersionUID = 1L;
    
    @Column(unique = true, nullable = false)
    private String batchId;

    private String productId;

    private int quantity;

    public Batch() {
        //for ORM
    }

    public Batch(String batchId, String productId, int quantity) {
        this.batchId = batchId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public String getProduct() {
        return productId;
    }

    public void setProduct(String product) {
        this.productId = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.batchId);
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
        final Batch other = (Batch) obj;
        if (!Objects.equals(this.batchId, other.batchId)) {
            return false;
        }
        return true;
    }

    
    
}
