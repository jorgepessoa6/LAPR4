package eapli.base.app.backoffice.console.presentation.productionorder;

import eapli.base.productionordermanagement.application.ConsultProductionOrdersOfOrderController;
import eapli.base.productionordermanagement.domain.ProductionOrder;
import eapli.framework.presentation.console.AbstractListUI;
import eapli.framework.util.Console;
import eapli.framework.visitor.Visitor;

/**
 *
 * @author Pedro Cardoso
 */
public class ConsultProductionOrdersOfOrderUI extends AbstractListUI<ProductionOrder> {

    private final ConsultProductionOrdersOfOrderController theController = new ConsultProductionOrdersOfOrderController();
    private String s = null;

    @Override
    protected Iterable<ProductionOrder> elements() {
        s = Console.readLine("ID of Order?");
        return this.theController.consult(s);
    }


    @Override
    protected String elementName() {
        return "Production Order";
    }

    @Override
    protected String listHeader() {
        return "Production Orders";
    }

    @Override
    public String headline() {
        return "Production Orders of inserted order ID";
    }

    @Override
    protected Visitor<ProductionOrder> elementPrinter() {
        return new ProductionOrderPrinter();
    }

    @Override
    protected String emptyMessage() {
        return "No data.";
    }
}
