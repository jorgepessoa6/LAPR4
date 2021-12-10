/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.productionordermanagement.domain;

import eapli.base.core.productmanager.domain.Batch;
import eapli.base.core.productmanager.domain.Product;
import eapli.base.productionlinemanagement.domain.ProductionLine;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.general.domain.model.Designation;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

/**
 * @author Bruno Pereira
 */
@XmlRootElement
@Entity
public class ProductionOrder implements AggregateRoot<Designation> {

    @Version
    private Long version;

    @Id
    @GeneratedValue
    private Long pk;

    @XmlElement
    @Column(unique = true, nullable = false)
    private Designation codeOrder;


    /**
     * devia ser enumerado
     */
    @XmlElement
    @Embedded
    private ProductionOrderStatus status;

    @XmlElement
    @Temporal(TemporalType.DATE)
    private Calendar startExecution;

    @XmlElement
    private String unity;

    @XmlElement
    private int quantity;

    @XmlElement
    @Temporal(TemporalType.DATE)
    private Calendar expectedDate;

    @JoinColumn()
    @ManyToOne()
    private ProductionLine productionLine;

    @XmlElement
    @JoinColumn()
    @ManyToOne()
    private Product productId;

    @XmlElement
    @ElementCollection
    @CollectionTable()
    private List<Order> encomenda;

    @OneToMany(cascade = CascadeType.ALL)
    private List<ProductionOrderExecutionEvent>  prodOrderExEvent;

     /*@OneToMany(cascade = CascadeType.ALL)
     private List<AbstractProductionMovementEvent> prodOrderMovEvents = new ArrayList<>();
     */
    public ProductionOrder() {
        // for ORM only
    }

    public boolean isOrderInProductionOrder(String id) {
        for (Order o : this.encomenda) {
            if (o.equals(Designation.valueOf(id))) {
                return true;
            }
        }
        return false;
    }

    public ProductionOrder(Designation codeOrder, Calendar startExecution, Calendar expectedDate, ProductionLine productionLine, AbstractProductionOrderExecutionEvent prodOrderExEvent, Product productId) {
        this.codeOrder = codeOrder;
        this.startExecution = startExecution;
        this.expectedDate = expectedDate;
        this.productionLine = productionLine;
        this.encomenda= new ArrayList<>();
        this.productId = productId;
        this.status = new ProductionOrderStatus(Status.EM_EXECUCAO);
    }

    public ProductionOrder(Designation codeOrder, Calendar startExecution, Calendar expectedDate, Product productId,
                           int quant, String unit) {
        this.codeOrder = codeOrder;
        this.startExecution = startExecution;
        this.expectedDate = expectedDate;
        this.productionLine = null;
        this.encomenda= new ArrayList<>();
        //this.prodOrderExEvent = null;
        this.productId = productId;
        this.status = new ProductionOrderStatus(Status.PENDENTE);
        this.prodOrderExEvent = new ArrayList<>();
        this.unity = unit;
        this.quantity = quant;
    }

    public boolean isProductionOrderInExecution() {
        ProductionOrderStatus a = new ProductionOrderStatus(Status.EM_EXECUCAO);
        return this.status == a;
    }


    public List<ProductionOrderExecutionEvent> getProdOrderExEvent() {
        return prodOrderExEvent;
    }



    public void setProdOrderEvent(ProductionOrderExecutionEvent prodOrderExEvent) {
        this.prodOrderExEvent.add(prodOrderExEvent);
    }

    public int getQuantity() {
        return quantity;
    }


    public Calendar getStartExecution() {
        return startExecution;
    }

    public void setStartExecution(Calendar startExecution) {
        this.startExecution = startExecution;
    }


    @XmlElement
    public ProductionLine getProductionLine() {
        return productionLine;
    }

    public void setProductionLine(ProductionLine productionLine) {
        this.productionLine = productionLine;
    }

    public ProductionOrderStatus getStatus() {
        return status;
    }
    public void addOrder(Order o){
        this.encomenda.add(o);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.codeOrder);
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
        final ProductionOrder other = (ProductionOrder) obj;
        if (!Objects.equals(this.codeOrder, other.codeOrder)) {
            return false;
        }
        return true;
    }

    @Override
    public boolean sameAs(Object other) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Designation identity() {
        return this.codeOrder;
    }

}
