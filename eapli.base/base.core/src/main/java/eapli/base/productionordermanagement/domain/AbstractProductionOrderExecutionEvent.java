/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.productionordermanagement.domain;

import eapli.base.core.productmanager.domain.Batch;
import eapli.framework.domain.events.DomainEvent;
import eapli.framework.domain.events.DomainEventBase;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author Bruno Pereira
 */
@Entity
public abstract class AbstractProductionOrderExecutionEvent extends DomainEventBase implements DomainEvent{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue
    private Long pk;

    private Calendar startTime;

    private Calendar endTime;

    private ActiveTime activeTime;

    private RawTime rawTime;

    @ElementCollection
    private List<MachineActiveTime> machineActiveTime;
    @ElementCollection
    private List<MachineRawTime> machineRawTime;

    @ElementCollection
    private List<DeflectionConsumption> deflectionConsumptions;

    @ElementCollection
    private List<DeflectionProduction> deflectionProductions;

    @ElementCollection
    private List<Replacement> replacements;

    @ElementCollection
    private List<Consumption> consumption;

    @ElementCollection
    private List<Batch> batch;


    @ElementCollection
    private List<DeliverProduction> deliverProductions;

    public AbstractProductionOrderExecutionEvent() {
        //for ORM
    }

    public AbstractProductionOrderExecutionEvent(Calendar startTime) {
        this.startTime = startTime;
        this.machineActiveTime = new ArrayList<>();
        this.machineRawTime = new ArrayList<>();
        this.deflectionConsumptions = new ArrayList<>();
        this.deflectionProductions = new ArrayList<>();
        this.consumption = new ArrayList<>();
        this.batch = new ArrayList<>();
        this.replacements =  new ArrayList<>();
        this.deflectionProductions = new ArrayList<>();
        this.deliverProductions = new ArrayList<>();
    }

    public Calendar getStartTime() {
        return startTime;
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    public Calendar getEndTime() {
        return endTime;
    }

    public void setEndTime(Calendar endTime) {
        this.endTime = endTime;
    }

    public ActiveTime getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(ActiveTime activeTime) {
        this.activeTime = activeTime;
    }

    public RawTime getRawTime() {
        return rawTime;
    }

    public void setRawTime(RawTime rawTime) {
        this.rawTime = rawTime;
    }

    public List<MachineActiveTime> getMachineActiveTime() {
        return machineActiveTime;
    }

    public void setMachineActiveTime(List<MachineActiveTime> machineActiveTime) {
        this.machineActiveTime = machineActiveTime;
    }

    public List<MachineRawTime> getMachineRawTime() {
        return machineRawTime;
    }

    public void setMachineRawTime(List<MachineRawTime> machineRawTime) {
        this.machineRawTime = machineRawTime;
    }

    public List<DeflectionConsumption> getDeflectionConsumptions() {
        return deflectionConsumptions;
    }

    public void setDeflectionConsumptions(List<DeflectionConsumption> deflectionConsumptions) {
        this.deflectionConsumptions = deflectionConsumptions;
    }

    public List<DeflectionProduction> getDeflectionProductions() {
        return deflectionProductions;
    }

    public void setDeflectionProductions(List<DeflectionProduction> deflectionProductions) {
        this.deflectionProductions = deflectionProductions;
    }

    public List<Replacement> getReplacements() {
        return replacements;
    }

    public void setReplacements(List<Replacement> replacements) {
        this.replacements = replacements;
    }

    public void addMachineActiveTime(MachineActiveTime machineActiveTime){
        this.machineActiveTime.add(machineActiveTime);
    }

    public void addMachineRawTime(MachineRawTime machineRawTime){
        this.machineRawTime.add(machineRawTime);
    }

    public void addReplacement(Replacement replacement){
        this.replacements.add(replacement);
    }

    public void addDeflectionCons(DeflectionConsumption deflectionConsumption){
        this.deflectionConsumptions.add(deflectionConsumption);
    }

    public void addDeflectionProd(DeflectionProduction deflectionProduction){
        this.deflectionProductions.add(deflectionProduction);
    }

    public void addBatch(Batch batch){
        this.batch.add(batch);
    }

    public void addConsumption(Consumption consumption){
        this.consumption.add(consumption);
    }

    public void addDeliverProduction(DeliverProduction deliverProduction){
        this.deliverProductions.add(deliverProduction);
    }

    public List<Batch> getBatch() {
        return batch;
    }

    public void setBatch(List<Batch> batch) {
        this.batch = batch;
    }

    @Override
    public String toString() {
        return "AbstractProductionOrderExecutionEvent{" +
                "pk=" + pk +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", activeTime=" + activeTime +
                ", rawTime=" + rawTime +
                ", machineActiveTime=" + machineActiveTime +
                ", machineRawTime=" + machineRawTime +
                ", deflectionConsumptions=" + deflectionConsumptions +
                ", deflectionProductions=" + deflectionProductions +
                ", replacements=" + replacements +
                ", consumption=" + consumption +
                ", batch=" + batch +
                ", deliverProductions=" + deliverProductions +
                '}';
    }
}
