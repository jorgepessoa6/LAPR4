package eapli.base.app.backoffice.console.presentation.productionorder;

import eapli.base.app.backoffice.console.presentation.products.ProductsPrinter;
import eapli.base.core.productmanager.domain.Product;
import eapli.base.productionordermanagement.application.RegisterProductionOrderController;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import eapli.framework.util.Console;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RegisterProductionOrderUI extends AbstractUI {
    private final RegisterProductionOrderController theController = new RegisterProductionOrderController();

    @Override
    protected boolean doShow() {
        final String productionOrderId = Console.readLine("Production Order Id: ");

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        Calendar dataEmissao = Calendar.getInstance();
        Date dateEmission;
        try {
            dateEmission = sdf.parse(Console.readLine("Emission Date: "));
            dataEmissao.setTime(dateEmission);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar dataExecucao = Calendar.getInstance();
        Date dateExecution;
        try {
            dateExecution = sdf.parse(Console.readLine("Execution Date: "));
            dataExecucao.setTime(dateExecution);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        final Iterable<Product> products = this.theController.products();

        final SelectWidget<Product> selector = new SelectWidget<>("Products: ", products, new ProductsPrinter());
        selector.show();

        final Product product = selector.selectedElement();

        boolean flag = true;

        List<String> orderList = new ArrayList<>();
        while(flag){
            String order = Console.readLine("Order id: ");
            orderList.add(order);
            final String str = Console.readLine("Add Another order? [Y/N]");
            if (str.equalsIgnoreCase("N")) {
                flag = false;
            }
        }

        final int quant = Console.readInteger("Quantity: ");

        final String unit = Console.readLine("Unit: ");

        try {
            this.theController.registerProductionOrder(productionOrderId, dataEmissao, dataExecucao, product, orderList, quant, unit);
        } catch (@SuppressWarnings("unused") final IntegrityViolationException e) {
            System.out.println("You tried to enter a production order which already exists in the database.");
        }
        return false;
    }

    @Override
    public String headline() {
        return "Register Production Orders";
    }
}
