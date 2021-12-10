/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.productionordermanagement.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlElement;
import java.util.Objects;

/**
 *
 * @author Bruno Pereira
 */
@Embeddable
public class ProductionOrderStatus implements ValueObject{
    @XmlElement
    private Status status;

    public ProductionOrderStatus() {
        this.status = null;
    }

    /**
     *
     * @param status
     */
    public ProductionOrderStatus(Status status) {
        this.status = status;
    }
    
    public Status stringToStatus(String status){
        
        if(status.equalsIgnoreCase("PENDENTE")) return Status.PENDENTE;
        
        if(status.equalsIgnoreCase("EM_EXECUCAO")) return Status.EM_EXECUCAO;
        
        if(status.equalsIgnoreCase("EXECUCAO_PARADA")) return Status.EXECUCAO_PARADA;
        
        if(status.equalsIgnoreCase("CONCLUIDA")) return Status.CONCLUIDA;
        
        if(status.equalsIgnoreCase("SUSPENSA")) return Status.SUSPENSA;
        
        return null;
    }
    
    public String getStatusString(){
        return this.status.toString();
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.status);
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
        final ProductionOrderStatus other = (ProductionOrderStatus) obj;
        if (this.status != other.status) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ProductionOrderStatus{" + "status=" + status + '}';
    }
    
    
    
    
}
