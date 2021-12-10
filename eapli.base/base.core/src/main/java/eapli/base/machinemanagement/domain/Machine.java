package eapli.base.machinemanagement.domain;

import eapli.base.productionlinemanagement.domain.ProductionLine;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.domain.model.DomainEntity;
import eapli.framework.general.domain.model.Designation;
import eapli.framework.validations.Preconditions;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;

/**
 * @author Rui Mendes (1170385)
 */

@Entity
@XmlRootElement
public class Machine implements DomainEntity<Designation> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long pk;

    @Version
    private Long version;

    @XmlElement
    private Designation internalCode;

    @XmlElement
    @Column(unique = true, nullable = false)
    private String seriesNumber;

    @XmlElement
    private String description;

    private MachineState machState;

    private Calendar instalationDate;

    @XmlElement
    private String brand;

    @XmlElement
    private String model;

    @Lob
    private byte[] config;

    private String networkAddress;

    @ManyToOne
    @XmlElement
    private ProductionLine productionLine;


    /**
     * Constructs a machine with the machine information
     *
     * @param internalCode
     * @param seriesNumber
     * @param description
     * @param instalationDate
     * @param brand
     * @param model
     * @param productionLine
     */
    public Machine(final Designation internalCode, final String seriesNumber,
                   final String description,
                   final Calendar instalationDate, final String brand, String model, ProductionLine productionLine) {

        Preconditions.noneNull(internalCode, seriesNumber, description, instalationDate, brand, model, productionLine);

        this.internalCode = internalCode;
        this.seriesNumber = seriesNumber;
        this.description = description;
        this.instalationDate = instalationDate;
        this.brand = brand;
        this.model = model;
        this.productionLine = productionLine;
        this.config = null;
        this.networkAddress = null;
        this.machState = null;
    }

    public Machine(Designation internalCode, String seriesNumber, String description, Calendar instalationDate, String brand, String model, String networkAddress, ProductionLine productionLine) {

        Preconditions.noneNull(internalCode, seriesNumber, description, instalationDate, brand, model, productionLine);

        this.internalCode = internalCode;
        this.seriesNumber = seriesNumber;
        this.description = description;
        this.machState = null;
        this.instalationDate = instalationDate;
        this.brand = brand;
        this.model = model;
        this.config = null;
        this.networkAddress = networkAddress;
        this.productionLine = productionLine;
    }

    public void activateState() {
        this.machState = MachineState.ON;
    }

    public void deactivateState() {
        this.machState = MachineState.OF;
    }

    protected Machine() {
        // for ORM only
    }

    public Designation getInternalCode() {
        return internalCode;
    }

    public ProductionLine getProductionLine() {
        return productionLine;
    }

    public byte[] getConfigFile() {
        return config;
    }


    @Override
    public boolean equals(final Object o) {
        return DomainEntities.areEqual(this, o);
    }

    @Override
    public int hashCode() {
        return DomainEntities.hashCode(this);
    }

    @Override
    public boolean sameAs(final Object other) {
        if (!(other instanceof Machine)) {
            return false;
        }

        final Machine that = (Machine) other;
        if (this == that) {
            return true;
        }

        return identity().equals(that.identity())
                && seriesNumber.equals(that.seriesNumber);
    }

    @Override
    public Designation identity() {
        return this.internalCode;
    }

    public void addConfigurationFile(String path) {
        this.config = convertFile(path);
    }

    public boolean isConfigFileEmpty() {
        if (this.config == null) {
            return true;
        }
        return false;
    }

    public ProductionLine productionLineOfMachine() {
        return this.productionLine;
    }

    @Override
    public String toString() {
        return "Machine{" +
                "internalCode=" + internalCode +
                ", seriesNumber='" + seriesNumber + '\'' +
                ", description='" + description + '\'' +
                ", machState=" + machState +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", networkAddress='" + networkAddress + '\'' +
                ", productionLine=" + productionLine.identity() +
                '}';
    }

    public static byte[] convertFile(String path) {
        byte[] pdf = null;
        try {
            Path pdfPath = Paths.get(path);
            pdf = Files.readAllBytes(pdfPath);
        } catch (IOException e) {
            System.out.println("File not Found!");
        } catch (Exception e) {
            System.out.println("Invalid File!");
        }
        return pdf;
    }

    public void updateNetworkAddress(String networkAddress) {
        this.networkAddress = networkAddress;
    }

    public String getNetworkAddress() {
        return this.networkAddress;
    }

    public MachineState MachineState() {
        return machState;
    }

    public Designation MachineInternalCode() {
        return internalCode;
    }
}
