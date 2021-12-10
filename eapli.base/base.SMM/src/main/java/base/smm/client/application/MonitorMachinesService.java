package base.smm.client.application;

import Server.HelloMessage;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.machinemanagement.domain.Machine;
import eapli.base.productionlinemanagement.repositories.ProductionLineRepository;
import eapli.framework.general.domain.model.Designation;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Iterator;

public class MonitorMachinesService {
    static InetAddress IPdestino;
    private static int TIMEOUT = 30;
    private static ProductionLineRepository plr = PersistenceContext.repositories().productionLines();

    public boolean SendUdpCliTo() throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        DatagramSocket sock = new DatagramSocket();
        sock.setSoTimeout(1000 * TIMEOUT); // set the socket timeout
        //HelloMessage hello = new HelloMessage();
        Iterator it = plr.machineByAddress().iterator();
        byte[] hello = new byte[2];
        hello[0] = 0;
        hello[1] = 0;
        while (it.hasNext()) {
            Machine m = (Machine) it.next();
            System.out.println("\n\n\nIP: "+m.getNetworkAddress());

            try {
                IPdestino = InetAddress.getByName(m.getNetworkAddress());
            } catch (UnknownHostException ex) {
                System.out.println("ConnectionError");
                System.exit(1);
            }
            DatagramPacket udpPacket = new DatagramPacket(hello, hello.length, IPdestino, 30604);
            // DatagramPacket udpPacket= new DatagramPacket("a".getBytes(), "a".length(),IPdestino,30604);
            System.out.print("\nSending Hello Request to: " + IPdestino);
            sock.send(udpPacket);
            try {
                System.out.println("\nWaiting for reponse...");
                sock.receive(udpPacket);
                int code = udpPacket.getData()[1] + 256;

                System.out.println("\nMSG CODE::" +code);

                //String machineId = new String(udpPacket.getData(), 2, 2);
                if (code == 150) {
                        /*
                        int size = convertByteToInt(Arrays.copyOfRange(udpPacket.getData(), 4, 5));
                        String state = new String(udpPacket.getData(), 6, size);
                        System.out.println("Received reply. STATE: " + state);
                        */
                    System.out.println("\nACK CODE by: " + IPdestino);
                    m.activateState();
                    plr.save(m.productionLineOfMachine());
                    System.out.println("\n\nState Saved!");
                } else {
                    System.out.println("\nNACK CODE by: " + IPdestino);
                    m.deactivateState();
                    PersistenceContext.repositories().productionLines().save(m.productionLineOfMachine());
                }
            } catch (SocketTimeoutException ex) {
                System.out.println("No reply");
                m.deactivateState();
                PersistenceContext.repositories().productionLines().save(m.productionLineOfMachine());
                return false;
                //estado inativo
            }
        }
        return true;
    }
}
