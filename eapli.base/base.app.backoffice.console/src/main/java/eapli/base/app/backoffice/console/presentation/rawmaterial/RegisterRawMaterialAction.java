
package eapli.base.app.backoffice.console.presentation.rawmaterial;

import eapli.framework.actions.Action;


/**
 * @author Miguel Fortes (1171611)
 */
public class RegisterRawMaterialAction implements Action {

    @Override
    public boolean execute() {
        return new RegisterRawMaterialUI().show();
    }
}
