package base.app.scm.run;

import base.app.scm.action.CollectMachineRequestsNoProtAction;

public class CollectMachineRequestsSMNoProt {
    public static void main(String[] args) {
        CollectMachineRequestsNoProtAction action = new CollectMachineRequestsNoProtAction();
        action.execute();
    }
}
