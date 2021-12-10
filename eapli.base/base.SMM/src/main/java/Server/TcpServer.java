//package Server;
//
//
//
//import eapli.base.infrastructure.persistence.PersistenceContext;
//import eapli.base.machinemanagement.application.ListMachinesAndStateService;
//import eapli.base.machinemanagement.domain.Machine;
//import eapli.base.messagemanagement.domain.Message;
//import eapli.base.productionlinemanagement.repositories.ProductionLineRepository;
//import eapli.framework.general.domain.model.Designation;
//import org.apache.commons.collections.IteratorUtils;
//import org.apache.commons.io.IOUtils;
//
//import java.io.DataInputStream;
//import java.io.DataOutputStream;
//import java.io.File;
//import java.io.IOException;
//import java.net.InetAddress;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//class TcpServer {
//    static ServerSocket sock;
//
//    public static void main(String args[]) throws Exception {
//        Socket cliSock;
//        try {
//            sock = new ServerSocket(30604);
//        } catch (IOException ex) {
//            System.out.println("Failed to open server socket");
//            System.exit(1);
//        }
//        while (true) {
//            cliSock = sock.accept();
//            new Thread(new TcpSrvSumThread(cliSock)).start();
//        }
//    }
//}
//
//class TcpSrvSumThread implements Runnable {
//    private Socket s;
//    private DataOutputStream sOut;
//    private DataInputStream sIn;
//    private final ProductionLineRepository plr = PersistenceContext.repositories().productionLines();
//
//    public TcpSrvSumThread(Socket cli_s) {
//        s = cli_s;
//    }
//
//    public void run() {
//        long f, i, num, sum;
//        InetAddress clientIP;
//        clientIP = s.getInetAddress();
//        System.out.println("New client connection from " + clientIP.getHostAddress() + ", port number " + s.getPort());
//        try {
//            sOut = new DataOutputStream(s.getOutputStream());
//            sIn = new DataInputStream(s.getInputStream());
//
//
//            String msgAck = "ACK";
//            String msgNack = "NACK";
//            int sizeAck = (msgAck.getBytes()).length;
//            int sizeNack = (msgNack.getBytes()).length;
//            byte[] ack = new byte[6 + sizeAck];
//            byte[] nack = new byte[6 + sizeNack];
//
//            // Message ACK
//
//            ack[0] = (byte) 0;
//            ack[1] = (byte) 150;
//            ack[2] = (byte) 0;
//            ack[3] = (byte) 0;
//
//            List<Machine> machine = IteratorUtils.toList(plr.allMachinesStated().iterator());
//            String a =  new ListMachinesAndStateService().machineStatehtmlBuilder(machine);
//            Arrays.fill(ack, 4, 5, (byte) a); // fill ack[4] and ack[5]
//            Arrays.fill(ack, 6, ack.length - 1, (byte) a);
//
////            // Message NACK
////            nack[0] = (byte) 0;
////            nack[1] = (byte) 151;
////            nack[2] = (byte) 0;
////            nack[3] = (byte) 0;
////            Arrays.fill(nack, 4, 5, (byte) sizeNack); // fill nack[4] and nack[5]
////            Arrays.fill(nack, 6, nack.length - 1, Byte.parseByte(msgNack));
//
//            byte[] bytes = IOUtils.toByteArray(sIn);
//            int msgCode = bytes[1];
//            String machineId = new String(bytes, 2, 2);
//            byte length = (byte) (bytes[4] + (bytes[5] * 256)); // NUMBER = first byte + ( second byte * 256 )
//            byte rawData = (byte) sIn.read(bytes, 6, length);
//
//            if (msgCode == 0) { // for msgCode "HELLO"
//                Machine m = plr.machineWithId(Designation.valueOf(machineId));
//                if (m == null) {
//                    sOut.write(nack); // If machine not found send NACK message
//                    System.out.println("Machine with id: " + machineId + " not found!");
//                } else {
//                    m.updateNetworkAddress(clientIP.getHostAddress());
//                    PersistenceContext.repositories().productionLines().save(m.productionLineOfMachine());
//                    sOut.write(ack); // If machine found send ACK message
//                    System.out.println("Machine with id: " + machineId + " found!");
//                }
//            }
//
//            if (msgCode == 1) { // for file
//                Machine m = plr.machineWithId(Designation.valueOf(machineId));
//                if (m == null) {
//                    sOut.write(nack); // If machine not found send NACK message
//                    System.out.println("Machine with id: " + machineId + " not found!");
//                } else {
//                    File file = new File(String.valueOf(rawData));
//                    Message msg = null;
//                    PersistenceContext.repositories().messages().save(msg);
//                    System.out.println("Message sent successfully");
//                }
//            }
//            System.out.println("Client " + clientIP.getHostAddress() + ", port number:" + s.getPort() + " disconnected ");
//            s.close();
//        } catch (IOException ex) {
//            System.out.println("IOException");
//        }
//    }
//
//    public int convertByteToInt(byte[] b) {
//        int value = 0;
//        for (int i = 0; i < b.length; i++)
//            value = (value << 8) | b[i];
//        return value;
//    }
//}
