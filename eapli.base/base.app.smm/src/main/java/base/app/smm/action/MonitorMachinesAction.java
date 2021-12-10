package base.app.smm.action;

import base.smm.client.application.MonitorMachinesController;
import eapli.framework.actions.Action;

/**
 *
 * @author Pedro Cardoso
 */
public class MonitorMachinesAction  implements Action {

    @Override
    public boolean execute() {
        MonitorMachinesController controller = new MonitorMachinesController();
        try {
            controller.startMachineMonitoring();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

}
