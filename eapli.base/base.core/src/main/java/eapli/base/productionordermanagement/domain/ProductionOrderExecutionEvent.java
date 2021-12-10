/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.productionordermanagement.domain;

import javax.persistence.Entity;
import java.util.Calendar;

/**
 *
 * @author Bruno Pereira
 */
@Entity
public class ProductionOrderExecutionEvent extends AbstractProductionOrderExecutionEvent{
    private static final long serialVersionUID = 1L;

    public ProductionOrderExecutionEvent() {
        super();
    }

    public ProductionOrderExecutionEvent(Calendar startTime) {
        super(startTime);
    }
    
}
