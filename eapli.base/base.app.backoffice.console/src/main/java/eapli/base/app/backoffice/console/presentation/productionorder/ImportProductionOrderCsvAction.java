package eapli.base.app.backoffice.console.presentation.productionorder;

import eapli.base.app.backoffice.console.presentation.products.ImportProductsCsvUI;
import eapli.framework.actions.Action;

public class ImportProductionOrderCsvAction implements Action {

    @Override
    public boolean execute() {
        return new ImportProductionOrderUI().show();
    }
}
