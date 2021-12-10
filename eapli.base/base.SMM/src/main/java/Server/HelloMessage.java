package Server;

import java.util.Arrays;

public class HelloMessage {

    String msgHello;
    short sizeHello ;
    byte[] hello ;
    public HelloMessage() {
        this.msgHello = "HELLO";
        this.sizeHello=(short) (msgHello.getBytes()).length;
    }

    private void fillArray() {
        hello[0] = (byte) 0;
        //code
        hello[1] = (byte) 0;
        //machine id
        hello[2] = (byte) 0;
        hello[3] = (byte) 0;
        //data length
        Arrays.fill(hello, 4, 5, (byte) sizeHello);
        //machine i
    }

    public byte[] sendHelloMeesage() {
        return hello;
    }
}
