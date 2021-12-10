package server;

import java.util.Arrays;

public class ACKMessage {

    String msgAck;
    int sizeAck;
    byte[] ack = new byte[6 + sizeAck];

    //version
    public ACKMessage() {
        this.msgAck = "ACK";
        this.sizeAck = (msgAck.getBytes()).length;
        byte[] hello = new byte[6 + sizeAck];
    }

    private void fillArray() {
        ack[0] = (byte) 0;
        ack[1] = (byte) 150;
        ack[2] = (byte) 0;
        ack[3] = (byte) 0;
        Arrays.fill(ack, 4, 5, (byte) sizeAck); // fill ack[4] and ack[5]
        Arrays.fill(ack, 6, ack.length - 1, Byte.parseByte(msgAck));
    }

    public byte[] sendAckMeesage() {
        return ack;
    }
}
