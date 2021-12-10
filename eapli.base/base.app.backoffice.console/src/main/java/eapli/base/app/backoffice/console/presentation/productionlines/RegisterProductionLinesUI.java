package eapli.base.app.backoffice.console.presentation.productionlines;

import eapli.base.productionlinemanagement.application.RegisterProductionLineController;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;

/**
 * @author Jorge Pessoa (1180761)
 */

public class RegisterProductionLinesUI extends AbstractUI {
    private final RegisterProductionLineController theController = new RegisterProductionLineController();

    @Override
    protected boolean doShow() {
        final String productionLineId = Console.readLine("Production Line Id: ");

        try {
            this.theController.registerProductionLine(productionLineId);
        } catch (@SuppressWarnings("unused") final IntegrityViolationException e) {
            System.out.println("You tried to enter a production line which already exists in the database.");
        }
        return false;
    }

    @Override
    public String headline() {
        return "Register Production Lines";
    }
}
