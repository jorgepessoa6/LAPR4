package eapli.base.app.backoffice.presentation.machine;

import eapli.base.app.backoffice.console.presentation.productionlines.ProductionLinePrinter;
import eapli.base.machinemanagement.application.RegisterMachineController;
import eapli.base.productionlinemanagement.domain.ListProductionLineService;
import eapli.base.productionlinemanagement.domain.ProductionLine;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import eapli.framework.util.Console;

/**
 * @author Rui Mendes (1170385)
 */

public class RegisterMachineUI extends AbstractUI {
    private final ListProductionLineService listProductionLineService =new ListProductionLineService();
    private final RegisterMachineController theController = new RegisterMachineController();

    @Override
    protected boolean doShow() {

        final String code = Console.readLine("Internal Code: ");

        String seriesNumber = Console.readLine("Series Number (between 6 and 20 caracters):");
        while (seriesNumber.length() < 6 || seriesNumber.length() > 20) {
            seriesNumber = Console.readLine("Insert a valid Series Number:");
        }
        final String description = Console.readLine("Description: ");

        final String brand = Console.readLine("Brand:");

        final String model = Console.readLine("Model:");

        final Iterable<ProductionLine> listProductionLine= this.listProductionLineService.allProductionLines();
        final SelectWidget<ProductionLine> selector = new SelectWidget<>("Production Lines:", listProductionLine, new ProductionLinePrinter());
        selector.show();

        final ProductionLine productionLine = selector.selectedElement();

        try {
            this.theController.registerMachine(code, seriesNumber, description, brand, model, null, productionLine);
        } catch (@SuppressWarnings("unused") final IntegrityViolationException e) {
            System.out.println("You tried to register a machine which already exists in the database.");
        }

        return false;
    }

    @Override
    public String headline() {
        return "Register Machine";
    }

}
