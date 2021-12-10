package base.app.smm.run;

import base.app.smm.action.MonitorMachinesAction;
import base.app.smm.action.ResetRequestAction;

public class BaseSmmReset {
    public static void main(String args[]) throws Exception {
        ResetRequestAction action = new ResetRequestAction(args[0]);
        action.execute();
    }
}
