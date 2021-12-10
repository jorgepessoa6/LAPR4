package base.app.scm.run;

import base.app.scm.action.CollectMessagesAction;

public class CollectMessageSMM {
    public static void main(String args[]) throws Exception {
        CollectMessagesAction action = new CollectMessagesAction();
        action.execute();
    }
}
