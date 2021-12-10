package base.app.scm.action;

import eapli.framework.actions.Action;
import server.SCMReceiveMessageNoProtController;

public class CollectMachineRequestsNoProtAction implements Action {

    @Override
    public boolean execute() {
        SCMReceiveMessageNoProtController controller = new SCMReceiveMessageNoProtController();
        try {
            controller.startReceivingMessages();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
