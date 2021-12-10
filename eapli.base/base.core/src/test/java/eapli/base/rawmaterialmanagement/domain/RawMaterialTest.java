package eapli.base.rawmaterialmanagement.domain;

import eapli.framework.general.domain.model.Designation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RawMaterialTest {

    @Test
    void testEquals() {
        RawMaterial raw = new RawMaterial(Designation.valueOf("codeF"), "codeC", null,null);
        RawMaterial raw1 = new RawMaterial(Designation.valueOf("codeF"), "codeC", null,null);
        assertEquals(raw,raw1);
    }

    @Test
    void canCreate() {
        RawMaterial raw = new RawMaterial(Designation.valueOf("codeF"), "codeC", null,null);
        assertEquals(raw.description(),"codeC");
        assertEquals(raw.identity(),Designation.valueOf("codeF"));
    }
}