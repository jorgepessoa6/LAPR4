package base.app.smm.action;

import base.smm.client.application.MonitorMachinesController;
import base.smm.client.application.ResetRequestController;
import eapli.framework.actions.Action;

public class ResetRequestAction implements Action {
    String ipMachine;

    public ResetRequestAction(String ipMachine) {
        this.ipMachine = ipMachine;
    }

    @Override
    public boolean execute() {
        ResetRequestController controller = new ResetRequestController();
        try {
            controller.sendResetRequest(ipMachine);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
