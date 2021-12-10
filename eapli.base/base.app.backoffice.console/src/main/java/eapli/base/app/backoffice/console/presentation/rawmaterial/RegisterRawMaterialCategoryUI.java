package eapli.base.app.backoffice.console.presentation.rawmaterial;

import eapli.base.rawmaterialmanagement.application.RegisterRawMaterialCategoryController;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;

/**
 * @author Miguel Fortes (117611)
 */
public class RegisterRawMaterialCategoryUI extends AbstractUI {

    private final RegisterRawMaterialCategoryController theController = new RegisterRawMaterialCategoryController();

    @Override
    protected boolean doShow() {

        final String code = Console.readLine("Code: ");
        final String description = Console.readLine("Description: ");

        try {
            this.theController.registerRawMaterialCategory(code, description);
        } catch (@SuppressWarnings("unused") final IntegrityViolationException e) {
            System.out.println("You tried to enter a raw material category which already exists in the database.");
        }
        return false;
    }

    @Override
    public String headline() {
        return "Register Raw Material Category";
    }
}

