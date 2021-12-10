package eapli.base.core.productmanager.domain;

import eapli.framework.general.domain.model.Designation;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductTest {

    @Test
    void isProductionRecordEmpty() {
        Product instance1 = new Product(Designation.valueOf("codeF"), "codeC", "teste desc","test descCOmplete", "UN","cat");
        boolean res1 = instance1.isProductionRecordEmpty();
        boolean exResult1 = true;
        assertEquals(exResult1, res1);

    }
/*    @Test
    void addProductionRecord() {
        Product instance1 = new Product(Designation.valueOf("codeF"), "codeC", "teste desc","test descCOmplete", "UN","cat");
        List<RawMaterial> lstRaw = new ArrayList<RawMaterial>();
        RawMaterial raw = new RawMaterial(Designation.valueOf("codeF"), "codeC", null,null);
        lstRaw.add(raw);
        List<Integer> lstInt = new ArrayList<Integer>();
        lstInt.add(1);
        ProductionRecord pr = new ProductionRecord(Designation.valueOf("a"),lstRaw,lstInt,1);
        boolean exResult1 = true;
        instance1.addProductionRecord(pr);
        assertEquals(false, instance1.isProductionRecordEmpty());

    }*/
    @Test
    void equals() {
        Product instance1 = new Product(Designation.valueOf("codeF"), "codeC", "teste desc","test descCOmplete", "UN","cat");
        Product instance2 = new Product(Designation.valueOf("codeF"), "codeC", "teste desc","test descCOmplete", "UN","cat");
        assertEquals(instance1,instance2);

    }
    @Test
    void canCreate() {
        Product instance1 = new Product(Designation.valueOf("codeF"), "codeC", "teste desc","test descCOmplete", "UN","cat");
        assertEquals(instance1.briefDescription(),"teste desc");
        assertEquals(instance1.identity(),Designation.valueOf("codeF"));
    }
    @Test
    public void testObjectToXml() throws JAXBException, FileNotFoundException {
        Product instance1 = new Product(Designation.valueOf("codeF"), "codeC", "teste desc", "test descCOmplete", "UN", "cat");
        JAXBContext jaxbContext = JAXBContext.newInstance(Product.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(instance1, new File("product.xml"));
        marshaller.marshal(instance1, System.out);


    }

    }