/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.depositmanagement.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.general.domain.model.Designation;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * @author Bruno Pereira
 */
@Entity
@XmlRootElement
public class Deposit implements AggregateRoot<Designation> {

    @Id
    @GeneratedValue
    private Long id;
    @Version
    private Long version;

    @XmlElement
    @Column(unique = true, nullable = false)
    private Designation cod;

    @XmlElement
    private String description;

    public Deposit() {
        this.cod = null;
        this.description = null;
    }

    public Deposit(Designation cod, String description) {
        this.cod = cod;
        this.description = description;
    }

    public Designation cod() {
        return this.cod;
    }

    public String description() {
        return this.description;
    }

    @Override
    public boolean sameAs(Object other) {
        if (!(other instanceof Deposit)) {
            return false;
        }

        final Deposit that = (Deposit) other;
        if (this == that) {
            return true;
        }

        return identity().equals(that.identity());
    }

    @Override
    public Designation identity() {
        return this.cod;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
