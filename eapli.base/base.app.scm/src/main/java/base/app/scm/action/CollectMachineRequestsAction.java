package base.app.scm.action;

import eapli.framework.actions.Action;
import server.SCMReceiveMessageController;

public class CollectMachineRequestsAction implements Action {

    @Override
    public boolean execute() {
        SCMReceiveMessageController controller = new SCMReceiveMessageController();
        try {
            controller.startReceivingMessages();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
