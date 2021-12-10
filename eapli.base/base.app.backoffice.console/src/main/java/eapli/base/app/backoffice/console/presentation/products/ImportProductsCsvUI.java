/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.app.backoffice.console.presentation.products;


import eapli.base.core.productmanager.import_.aplication.ImportProductsCSVController;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;

/**
 *
 * @author Pedro Cardoso
 */
public class ImportProductsCsvUI extends AbstractUI {

    private final ImportProductsCSVController controller = new ImportProductsCSVController();

    @Override
    protected boolean doShow() {
        try {
            final String name = Console.readLine("Path?");
            controller.importCSV(name);
            System.out.println("Sucess!!");
        } catch (Exception e) {
            System.out.println("Error importing files");
        }
        return false;
    }

    @Override
    public String headline() {
        return "Importing CSV files to Products";
    }
}
