
package eapli.base.app.backoffice.console.presentation.productionlines;

import eapli.framework.actions.Action;

public class AlterProcessingStateAction  implements Action {

    @Override
    public boolean execute() {
        return new AlterProcessingStateUI().show();
    }
}

