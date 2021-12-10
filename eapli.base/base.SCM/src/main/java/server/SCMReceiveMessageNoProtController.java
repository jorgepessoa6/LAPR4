package server;

import java.io.IOException;

public class SCMReceiveMessageNoProtController {

    private final SCMReceiveMessageServiceNoProt scmReceiveMessageServiceNoProt = new SCMReceiveMessageServiceNoProt();

    public void startReceivingMessages(){
        try {
            scmReceiveMessageServiceNoProt.callTCP();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
