package eapli.base.factoryfloor.application;

import eapli.base.core.productmanager.domain.Product;
import eapli.base.core.productmanager.repositories.ProductRepository;
import eapli.base.depositmanagement.domain.Deposit;
import eapli.base.depositmanagement.repositories.DepositRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.machinemanagement.domain.Machine;
import eapli.base.productionlinemanagement.domain.ProductionLine;
import eapli.base.productionlinemanagement.repositories.ProductionLineRepository;
import eapli.base.productionordermanagement.domain.ProductionOrder;
import eapli.base.productionordermanagement.repositories.ProductionOrderRepository;
import eapli.base.rawmaterialmanagement.domain.RawMaterial;
import eapli.base.rawmaterialmanagement.domain.RawMaterialCategory;
import eapli.base.rawmaterialmanagement.repositories.RawMaterialCategoryRepository;
import eapli.base.rawmaterialmanagement.repositories.RawMaterialRepository;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import org.apache.commons.io.FileUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class ExportFactoryFloorService {
    private static final String PRODUCT = "Product";
    private static final String RAW_MATERIAL = "RawMaterial";
    private static final String DEPOSIT = "Deposit";
    private static final String CATEGORY = "Category";
    private static final String MACHINE = "Machine";
    private static final String PRODUCTION_LINE = "ProductionLine";
    private static final String PRODUCTION_ORDER = "ProductionOrder";


    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final ProductRepository produtoRepository = PersistenceContext.repositories().products();
    private final RawMaterialRepository rawMaterialRepository = PersistenceContext.repositories().rawMaterials();
    private final RawMaterialCategoryRepository rawMaterialCategoryRepositoryRepository = PersistenceContext.repositories().categories();
    private final DepositRepository depositRepository = PersistenceContext.repositories().deposits();
    private final ProductionLineRepository productionLineRepository = PersistenceContext.repositories().productionLines();
    private final ProductionOrderRepository productionOrderRepository = PersistenceContext.repositories().productionOrders();

    @XmlRootElement(name = "FactoryFloor")
    static class FactoryFloor {
        @XmlElement
        private Products products;
        @XmlElement
        private RawMaterials rawMaterials;
        @XmlElement
        private RawMaterialCategories rawMaterialCategories;
        @XmlElement
        private Deposits deposits;
        @XmlElement
        private ProductionLines productionLines;
        @XmlElement
        private ProductionOrders productionOrders;
        @XmlElement
        private Machines machines;

        public FactoryFloor() {
            this.products = new Products();
            this.rawMaterials = new RawMaterials();
            this.rawMaterialCategories = new RawMaterialCategories();
            this.deposits = new Deposits();
            this.productionLines = new ProductionLines();
            this.productionOrders = new ProductionOrders();
            this.machines= new Machines();
        }

        @XmlRootElement(name = "Products")
        static class Products {
            @XmlElement
            private List<Product> product = new ArrayList<>();

            public void addLstProd(Product p) {
                this.product.add(p);
            }
        }

        @XmlRootElement(name = "RawMaterials")
        static class RawMaterials {
            @XmlElement
            private List<RawMaterial> rawMaterial = new ArrayList<>();

            public void addLstRawMat(RawMaterial rawMat) {
                this.rawMaterial.add(rawMat);
            }
        }


        @XmlRootElement(name = "RawMaterialCategories")
        static class RawMaterialCategories {
            @XmlElement
            private List<RawMaterialCategory> rawMaterialCategory = new ArrayList<>();

            public void addLstRawMatCat(RawMaterialCategory rawMatCat) {
                this.rawMaterialCategory.add(rawMatCat);
            }
        }

        @XmlRootElement(name = "Deposits")
        static class Deposits {
            @XmlElement
            private List<Deposit> deposit = new ArrayList<>();

            public void addLstDep(Deposit deposit) {
                this.deposit.add(deposit);
            }
        }

        @XmlRootElement(name = "ProductionLines")
        static class ProductionLines {
            @XmlElement
            private List<ProductionLine> productionLine = new ArrayList<>();

            public void addLstProdLine(ProductionLine prodLine) {
                this.productionLine.add(prodLine);
            }
        }

        @XmlRootElement(name = "ProductionOrders")
        static class ProductionOrders {
            @XmlElement
            private List<ProductionOrder> productionOrder = new ArrayList<>();

            public void addLstProdOrder(ProductionOrder prodOrder) {
                this.productionOrder.add(prodOrder);
            }
        }
        @XmlRootElement(name = "Machines")
        static class Machines {
            @XmlElement
            private List<Machine> machines = new ArrayList<>();

            public void addMachine(Machine m) {
                this.machines.add(m);
            }
        }

    }


    public void export(LinkedHashMap<String, String> lst, String path) throws JAXBException, ParseException, IOException, XsdValidationException {
        FactoryFloor ff = new FactoryFloor();
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.GESTOR_PRODUCAO);

        for (String s : lst.keySet()) {

            if (s.equalsIgnoreCase(PRODUCT)) {
                Iterator<Product> it1 = produtoRepository.findAll().iterator();
                while (it1.hasNext()) {
                    ff.products.addLstProd(it1.next());
                }
            }
            if (s.equalsIgnoreCase(RAW_MATERIAL)) {
                Iterator<RawMaterial> it1 = rawMaterialRepository.findAll().iterator();
                while (it1.hasNext()) {
                    ff.rawMaterials.addLstRawMat(it1.next());
                }
            }
            if (s.equalsIgnoreCase(DEPOSIT)) {
                Iterator<Deposit> it1 = depositRepository.findAll().iterator();
                while (it1.hasNext()) {
                    ff.deposits.addLstDep(it1.next());
                }
            }
            if (s.equalsIgnoreCase(CATEGORY)) {
                Iterator<RawMaterialCategory> it1 = rawMaterialCategoryRepositoryRepository.findAll().iterator();
                while (it1.hasNext()) {
                    ff.rawMaterialCategories.addLstRawMatCat(it1.next());
                }
            }
            if (s.equalsIgnoreCase(PRODUCTION_LINE)) {
                Iterator<ProductionLine> it1 = productionLineRepository.findAll().iterator();
                while (it1.hasNext()) {
                    ff.productionLines.addLstProdLine(it1.next());
                }
            }
            if (s.equalsIgnoreCase(PRODUCTION_ORDER)) {
                String obj = lst.get(s);
                if (obj != null) {
                    String[] strings = obj.split("-");
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    Date date1 = sdf.parse(strings[0]);
                    Date date2 = sdf.parse(strings[1]);
                    Calendar cal1 = Calendar.getInstance();
                    cal1.setTime(date1);
                    Calendar cal2 = Calendar.getInstance();
                    cal2.setTime(date2);
                    Iterator<ProductionOrder> it1 = productionOrderRepository.allproductionOrderByTimeFrame(cal1,cal2).iterator();
                    while (it1.hasNext()) {
                        ff.productionOrders.addLstProdOrder(it1.next());
                    }

                } else {
                    Iterator<ProductionOrder> it1 = productionOrderRepository.findAll().iterator();
                    while (it1.hasNext()) {
                        ff.productionOrders.addLstProdOrder(it1.next());
                    }
                }
            }
            if (s.equalsIgnoreCase(MACHINE)) {
                Iterator<Machine> it1 = productionLineRepository.allMachinesNoConfigFile().iterator();
                while (it1.hasNext()) {
                    ff.machines.addMachine(it1.next());
                }
            }
        }
        export(path,ff);

    }
    private void export(String path, FactoryFloor ff) throws JAXBException, IOException , XsdValidationException{
        File file = new File("XML/"+path);
        JAXBContext jaxbContext = JAXBContext.newInstance(FactoryFloor.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        marshaller.marshal(ff, file);

        String xml = FileUtils.readFileToString(file, StandardCharsets.UTF_8);

        System.out.println("\nXML:\n" + xml);

        //Validator
        System.out.println("Validate XML against XSD Schema");
        XsdValidator validator = new XsdValidator("/schema1.xsd");
        validator.validate(xml);
        System.out.println("Validation is successful");
    }
}

