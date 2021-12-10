package eapli.base.productionordermanagment.import_.application;

import eapli.base.core.productmanager.domain.Product;
import eapli.base.core.productmanager.repositories.ProductRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.productionordermanagement.domain.Order;
import eapli.base.productionordermanagement.domain.ProductionOrder;
import eapli.base.productionordermanagement.repositories.ProductionOrderRepository;
import eapli.framework.general.domain.model.Designation;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Pedro Cardoso
 */
public class ImportProdutionOrderService {
    private final ProductRepository productRepository = PersistenceContext.repositories().products();
    private final ProductionOrderRepository productionOrderRepository = PersistenceContext.repositories().productionOrders();

    public void importCSV(String filename,String divider) throws IOException, ParseException {

            File file = new File(filename);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = "";
            String[] tempArr;
            br.readLine();
            int i = 0;
            while ((line = br.readLine()) != null) {
                String values[] = line.split(divider);
                persistValues(values);

            }
            br.close();

    }

    private void persistValues(String values[]) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date dateEmission = sdf.parse(values[1]);
        Date dateExecution = sdf.parse(values[2]);
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateEmission);
        Calendar cal2 = Calendar.getInstance();
        cal.setTime(dateExecution);
        List<String> orderList = Arrays.asList(values[6].split(","));
        Product prod = productRepository.ofIdentity(Designation.valueOf(values[3])).orElseThrow(IllegalStateException::new);
        ProductionOrder p = new ProductionOrder(Designation.valueOf(values[0]), cal, cal2, prod, Integer.parseInt(values[4]), values[5]);
        for (String s :
                orderList) {
            Order o = new Order((s));
            p.addOrder(o);
        }
        productionOrderRepository.save(p);
    }
}
