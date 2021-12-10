package eapli.base.machinemanagement.domain;

import eapli.base.productionlinemanagement.domain.ProductionLine;
import eapli.framework.general.domain.model.Designation;
import org.junit.jupiter.api.Test;


import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Rui Mendes (1170385)
 */

public class MachineTest {

    @org.junit.jupiter.api.Test
    void testEquals() {
        ProductionLine pl1 = new ProductionLine(Designation.valueOf("10f"));
        Machine instance1 = new Machine(Designation.valueOf("m27"), "000aa3", "asdfgh", Calendar.getInstance(),"bish", "bosh",pl1);
        Machine instance2 = new Machine(Designation.valueOf("m27"), "000aa3", "asdfgh", Calendar.getInstance(),"bish", "bosh",pl1);
        Machine instance3 = new Machine(Designation.valueOf("m28"), "000ba3", "asefgh", Calendar.getInstance(),"bosh", "bish",pl1);
        boolean res1 = instance1.equals(instance2);
        boolean res2 = instance1.equals(instance3);

        assertEquals(true, res1);
        assertEquals(false, res2);

    }
}
