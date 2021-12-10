/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.core.productmanager.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.general.domain.model.Designation;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Pedro Cardoso
 */

@XmlRootElement
@Entity
public class Product implements AggregateRoot<Designation> {

    private static final long serialVersionUID = 1L;

    @Version
    private Long version;
    
    @Id
    @GeneratedValue
    private Long pk;

    @XmlElement
    @Column(unique = true, nullable = false)
    private Designation codeFactory;

    @XmlElement
    @Column(unique = true, nullable = false)
    private String codeComercial;

    @XmlElement
    private String unit;

    @XmlElement
    private String category;

    @XmlElement
    private String descBrief;

    @XmlElement
    private String descComplete;


    @XmlElement(name = "ProductionRecord")
    @JoinColumn
    @OneToOne(cascade = CascadeType.ALL)
    private ProductionRecord pRecord;



    public Product(Designation codeFactory, String codeComercial, String descBrief, String descComplete, String unit, String cat) {
        this.codeFactory = codeFactory;
        this.codeComercial = codeComercial;
        this.descBrief = descBrief;
        this.descComplete = descComplete;
        this.category = cat;
        this.unit = unit;
        this.pRecord=null;
    }

    protected Product() {
//         for ORM only
    }
    public String codeComercial() {
        return codeComercial;
    }

    public String unit() {
        return unit;
    }

    public String category() {
        return category;
    }

    public String descBrief() {
        return descBrief;
    }

    public String descComplete() {
        return descComplete;
    }

    public ProductionRecord pRecord() {
        return pRecord;
    }

    @Override
    public boolean sameAs(Object other) {
        if (!(other instanceof Product)) {
            return false;
        }

        final Product that = (Product) other;
        if (this == that) {
            return true;
        }

        return this.codeFactory.equals(that.codeFactory)
                && descBrief.equals(that.descBrief);
    }

    @Override
    public Designation identity() {
        return this.codeFactory;
    }
    
    public boolean isProductionRecordEmpty(){
        if (this.pRecord==null) {
            return true;
        }   
        return false;
    }
    public void addProductionRecord(ProductionRecord rec){
        this.pRecord=rec;
    }
    
    public String briefDescription(){
        String desc = this.descBrief;
        return desc;
    }

    @Override
    public String toString() {
        return "Product{" + "codeFactory=" + codeFactory + ", descBrief=" + descBrief + '}';
    }
     @Override
    public boolean equals(final Object o) {
        return DomainEntities.areEqual(this, o);
    }
    @Override
    public int hashCode() {
        return DomainEntities.hashCode(this);
    }
    

}
