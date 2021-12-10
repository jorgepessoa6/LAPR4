package base.app.scm.action;

import base.scm.threads.application.CollectionMessagesTxtController;
import eapli.framework.actions.Action;

public class CollectMessagesAction   implements Action {

    @Override
    public boolean execute() {
        CollectionMessagesTxtController controller = new CollectionMessagesTxtController();
        try {
            controller.CollectMessages();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}