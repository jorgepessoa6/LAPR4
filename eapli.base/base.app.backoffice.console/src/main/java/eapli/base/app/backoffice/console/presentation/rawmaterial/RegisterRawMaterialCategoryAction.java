package eapli.base.app.backoffice.console.presentation.rawmaterial;

import eapli.framework.actions.Action;


public class RegisterRawMaterialCategoryAction implements Action {

    @Override
    public boolean execute() {
        return new RegisterRawMaterialCategoryUI().show();
    }
}
