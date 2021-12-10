package eapli.base.app.backoffice.console.presentation.productionorder;


import eapli.base.productionordermanagment.import_.application.ImportProductionOrderCsvController;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;

import java.io.IOException;
import java.text.ParseException;

/**
 *
 * @author Pedro Cardoso
 */
public class ImportProductionOrderUI extends AbstractUI {

    private ImportProductionOrderCsvController controller = new ImportProductionOrderCsvController();
    @Override
    protected boolean doShow() {
        try {
            final String name = Console.readLine("Path?");
            String divider = Console.readLine("Separator?");
            while (divider.equalsIgnoreCase(",")){
                  divider = Console.readLine("Separator?");
            }
            controller.importCSV(name,divider);
            System.out.println("Sucess!!");
        } catch (ParseException e) {
            System.out.println("File Not found!");
        } catch (IOException e) {
            System.out.println("Error while parsing !");
        }
        return false;
    }

    @Override
    public String headline() {
        return "Importing CSV files to Production Order";
    }
}
