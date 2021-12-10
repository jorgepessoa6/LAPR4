package base.smm.client.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.machinemanagement.domain.Machine;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Iterator;

public class ResetRequestService {
    static InetAddress IPdestino;

    public boolean SendUdpCliTo(String ip) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        DatagramSocket sock = new DatagramSocket();
        byte[] reset = new byte[2];
        reset[0] = 0;
        reset[1] = (byte) 3;
        System.out.println("\n\n\nIP: " + ip);
        try {
            IPdestino = InetAddress.getByName(ip);
        } catch (UnknownHostException ex) {
            System.out.println("\nConnection Error");
            System.exit(1);
        }
        DatagramPacket udpPacket = new DatagramPacket(reset, reset.length, IPdestino, 30604);
        System.out.print("\nSending Reset Request to: " + IPdestino);
        sock.send(udpPacket);
        try {
            System.out.println("\nWaiting for reponse...");
            sock.receive(udpPacket);
            int code = udpPacket.getData()[1] + 256;
            System.out.println("\nMessage Back code:" + code);
            if (code == 150) {
                System.out.println("\nACK CODE by: " + IPdestino + "\nReset Request Sucessful");
            } else {
                System.out.println("\nNACK CODE by: " + IPdestino + "\nReset Request Unsucessful");
            }
        } catch (SocketTimeoutException ex) {
            System.out.println("No reply");
            return false;
        }
        return true;
    }
}
