package server;

import java.io.IOException;

public class SCMReceiveMessageController {

    private final SCMReceiveMessageService scmReceiveMessageService = new SCMReceiveMessageService();

    public void startReceivingMessages(){
        try {
            scmReceiveMessageService.callTCP();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
