package eapli.base.app.backoffice.console.presentation.rawmaterial;

import eapli.base.rawmaterialmanagement.application.RegisterRawMaterialController;
import eapli.base.rawmaterialmanagement.domain.DataSheet;
import eapli.base.rawmaterialmanagement.domain.RawMaterialCategory;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import eapli.framework.util.Console;

public class RegisterRawMaterialUI extends AbstractUI {

    private final RegisterRawMaterialController theController = new RegisterRawMaterialController();

    @Override
    protected boolean doShow() {

        final String code = Console.readLine("Internal Code: ");

        final String description = Console.readLine("Description: ");

        final Iterable<RawMaterialCategory> rawMaterialCategories = this.theController.rawMaterialCategories();

        final SelectWidget<RawMaterialCategory> selector = new SelectWidget<>("Raw Material Categories:", rawMaterialCategories, new RawMaterialCategoryPrinter());
        selector.show();

        final RawMaterialCategory rawMaterialCategory = selector.selectedElement();

        final String fileName = Console.readLine("DataSheet File Name: ");
        final String filePath = Console.readLine("DataSheet File Path: ");

        DataSheet dataSheet = new DataSheet(fileName, filePath);


        try {
            this.theController.registerRawMaterial(code, description, rawMaterialCategory, dataSheet);
        } catch (@SuppressWarnings("unused") final IntegrityViolationException e) {
            System.out.println("You tried to enter a raw material which already exists in the database.");
        }

        return false;
    }

    @Override
    public String headline() {
        return "Register Raw Material";
    }
}

