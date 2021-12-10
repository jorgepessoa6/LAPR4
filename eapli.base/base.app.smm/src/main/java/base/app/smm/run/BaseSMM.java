package base.app.smm.run;

import base.app.smm.action.MonitorMachinesAction;

public  class BaseSMM {
    public static void main(String args[]) throws Exception {
        MonitorMachinesAction action = new MonitorMachinesAction();
        action.execute();
    }
}
