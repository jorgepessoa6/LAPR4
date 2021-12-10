package eapli.base.rawmaterialmanagement.domain;

import eapli.framework.general.domain.model.Designation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RawMaterialCategoryTest {


    @Test
    void canCreate() {
        RawMaterialCategory raw = new RawMaterialCategory(Designation.valueOf("codeF"), "codeC");
        assertEquals(raw.categoryDescription(), "codeC");
        assertEquals(raw.identity(), Designation.valueOf("codeF"));
    }
}