package base.app.scm.run;

import base.app.scm.action.CollectMachineRequestsAction;

public class CollectMachineResquestsSM {
    public static void main(String[] args) {
        CollectMachineRequestsAction action = new CollectMachineRequestsAction();
        action.execute();
    }
}
