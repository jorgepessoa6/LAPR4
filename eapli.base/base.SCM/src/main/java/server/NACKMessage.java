package server;

import java.util.Arrays;

public class NACKMessage {

    String msgNack;
    int sizeNack;
    byte[] nack = new byte[6 + sizeNack];

    public NACKMessage() {
        this.msgNack = "NACK";
        this.sizeNack = (msgNack.getBytes()).length;
        byte[] nack = new byte[6 + sizeNack];
    }

    private void fillArray() {
        nack[0] = (byte) 0;
        nack[1] = (byte) 151;
        nack[2] = (byte) 0;
        nack[3] = (byte) 0;
        Arrays.fill(nack, 4, 5, (byte) sizeNack); // fill nack[4] and nack[5]
        Arrays.fill(nack, 6, nack.length - 1, Byte.parseByte(msgNack));
    }

    public byte[] sendNackMeesage() {
        return nack;
    }
}
