/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.core.productmanager.domain;

import eapli.base.rawmaterialmanagement.domain.RawMaterial;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Pedro Cardoso
 */
@Entity
@XmlRootElement
public class ProductionRecord {

    private static final long serialVersionUID = 1L;

    @Version
    private Long version;

    @Id
    @GeneratedValue
    private Long pk;

    @XmlElement
    @OneToMany(cascade = CascadeType.ALL)
    private List<RawMaterial> materials = new ArrayList<>();

    @XmlElement
    @ElementCollection
    private List<Integer> quant = new ArrayList<>();

    @OneToOne()
    @XmlElement
    private Product prod;

    @XmlElement
    private int units;
    
    public ProductionRecord(){
         // for ORM only
    }
    
    public ProductionRecord(List<RawMaterial> mat, List<Integer> quant, int units,Product prod){
        this.materials=mat;
        this.quant=quant;
        this.units= units;
        this.prod=prod;
    }

    public List<RawMaterial> materials() {
        return materials;
    }

    public List<Integer> quantity() {
        return quant;
    }

    public int units() {
        return units;
    }
}
