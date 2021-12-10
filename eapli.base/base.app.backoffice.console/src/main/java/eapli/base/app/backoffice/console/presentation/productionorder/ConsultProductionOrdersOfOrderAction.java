package eapli.base.app.backoffice.console.presentation.productionorder;

import eapli.framework.actions.Action;
/**
 *
 * @author Pedro Cardoso
 */
public class ConsultProductionOrdersOfOrderAction  implements Action {

    @Override
    public boolean execute() {
        return new ConsultProductionOrdersOfOrderUI().show();
    }
}
