package Server;


import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.machinemanagement.domain.Machine;
import eapli.base.productionlinemanagement.repositories.ProductionLineRepository;
import eapli.framework.general.domain.model.Designation;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.Arrays;
import java.util.Iterator;

class UdpCliTo {
    static InetAddress IPdestino;
    private static int TIMEOUT = 30;
    private static ProductionLineRepository plr = PersistenceContext.repositories().productionLines();

    public static void main(String args[]) throws Exception {


        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        DatagramSocket sock = new DatagramSocket();
        sock.setSoTimeout(1000 * TIMEOUT); // set the socket timeout

        String msgHello = "HELLO";
        int sizeHello = (msgHello.getBytes()).length;
        byte[] hello = new byte[6 + sizeHello];
        //version
        hello[0] = (byte) 0;
        //code
        hello[1] = (byte) 0;
        //machine id
        hello[2] = (byte) 0;
        hello[3] = (byte) 0;
        //data length
        Arrays.fill(hello, 4, 5, (byte) sizeHello);
        //machine id
        byte[] msg = msgHello.getBytes();
        for (int i = 0; i < msg.length; i++) {
            hello[i + 6] = msg[i];
        }
        // Arrays.fill(hello, 6, hello.length - 1, (byte) msg);

        Iterator it = plr.machineByAddress().iterator();
        while (true) {
            while (it.hasNext()) {
                IPdestino = InetAddress.getByName((String) it.next());
                DatagramPacket udpPacket = new DatagramPacket(hello, hello.length, IPdestino, 30604);
                System.out.print("\nSending Hello Request to: " + IPdestino);
                sock.send(udpPacket);
                try {
                    sock.receive(udpPacket);
                    int code = udpPacket.getData()[1];
                    String machineId = new String(udpPacket.getData(), 2, 2);
                    if (code == 150) {
                        /*
                        int size = convertByteToInt(Arrays.copyOfRange(udpPacket.getData(), 4, 5));
                        String state = new String(udpPacket.getData(), 6, size);
                        System.out.println("Received reply. STATE: " + state);
                        */
                        System.out.println("\nACK CODE by: " + IPdestino);
                        Machine m = plr.machineByAddress(Designation.valueOf(machineId));
                        m.activateState();
                        plr.save(m.productionLineOfMachine());


                    } else {
                        System.out.println("\nNACK CODE by: " + IPdestino);
                        Machine m = plr.machineByAddress(Designation.valueOf(machineId));
                        m.deactivateState();
                        PersistenceContext.repositories().productionLines().save(m.productionLineOfMachine());

                    }
                } catch (SocketTimeoutException ex) {
                    System.out.println("No reply");
                    Machine m = plr.machineByAddress((String) it.next());
                    m.deactivateState();
                    plr.save(m.productionLineOfMachine());
                    //estado inativo
                }
            }
        }
        //sock.close();
    }

    public static int convertByteToInt(byte[] b) {
        int value = 0;
        for (int i = 0; i < b.length; i++)
            value = (value << 8) | b[i];
        return value;
    }
}