package server;


import base.scm.threads.application.ProcessMachineMessagesService;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.machinemanagement.domain.Machine;
import eapli.base.productionlinemanagement.repositories.ProductionLineRepository;
import eapli.framework.general.domain.model.Designation;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Base64;

class SCMReceiveMessageService {
    static final int SERVER_PORT = 30604;
    static final String TRUSTED_STORE = "serverJ.jks";
    static final String KEYSTORE_PASS = "forgotten";


    public void callTCP() throws IOException {
        SSLServerSocket sock = null;
        Socket cliSock;
        DataInputStream sIn;
        DataOutputStream sOut;

        // Trust these certificates provided by authorized clients
        System.setProperty("javax.net.ssl.trustStore", TRUSTED_STORE);
        System.setProperty("javax.net.ssl.trustStorePassword", KEYSTORE_PASS);

        // Use this certificate and private key as server certificate
        System.setProperty("javax.net.ssl.keyStore", TRUSTED_STORE);
        System.setProperty("javax.net.ssl.keyStorePassword", KEYSTORE_PASS);

        SSLServerSocketFactory sslF = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();


        try {
            sock = (SSLServerSocket) sslF.createServerSocket(SERVER_PORT);
            sock.setNeedClientAuth(true);
        } catch (IOException ex) {
            System.out.println("Failed to open server socket");
            System.exit(1);
        }
        while (true) {
            System.out.println("Waiting for connection\n");
            cliSock = sock.accept();
            sOut = new DataOutputStream(cliSock.getOutputStream());
            sIn = new DataInputStream(cliSock.getInputStream());
            new Thread(new TcpSrvSumThread(cliSock, sIn, sOut)).start();
        }
    }
}

class TcpSrvSumThread implements Runnable {
    private Socket s;
    private DataOutputStream sOut;
    private DataInputStream sIn;
    private final ProductionLineRepository plr = PersistenceContext.repositories().productionLines();

    public TcpSrvSumThread(Socket cli_s, DataInputStream sIn, DataOutputStream sOut) {
        s = cli_s;
        this.sIn = sIn;
        this.sOut = sOut;
    }

    public void run() {
        InetAddress clientIP;

        clientIP = s.getInetAddress();
        System.out.println("New client connection from " + clientIP.getHostAddress() + ", port number " + s.getPort());
        while (s.isConnected()) {
            try {
                String msgAck = "ACK";
                String msgNack = "NACK";
                int sizeAck = (msgAck.getBytes()).length;
                int sizeNack = (msgNack.getBytes()).length;
                byte[] ack = new byte[6 + sizeAck];
                byte[] nack = new byte[6 + sizeNack];
                // Message ACK
                ack[0] = (byte) 0;
                ack[1] = (byte) 150;
                ack[2] = (byte) 0;
                ack[3] = (byte) 0;
/*            Arrays.fill(ack, 4, 5, (byte) sizeAck); // fill ack[4] and ack[5]
            Arrays.fill(ack, 6, ack.length - 1, Byte.parseByte(msgAck));*/
                // Message NACK
                nack[0] = (byte) 0;
                nack[1] = (byte) 151;
                nack[2] = (byte) 0;
                nack[3] = (byte) 0;
/*            Arrays.fill(nack, 4, 5, (byte) sizeNack); // fill nack[4] and nack[5]
            Arrays.fill(nack, 6, nack.length - 1, Byte.parseByte(msgNack));*/
                byte[] bytes = new byte[2];
                int version = sIn.readByte();
                while (version == 0) {
                    version = sIn.readByte();
                }
                int msgCode = sIn.readByte();
                int val = sIn.readByte() + 256 * sIn.readByte();
                short idFloat = (short) val;
                String machineId = "M" + idFloat;
                //size
                int val2 = sIn.readByte() + 256 * sIn.readByte();
                byte[] rawData = new byte[val2];
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                StringBuilder sB = new StringBuilder(val2);
                for (int j = 0; j < val2; j++) {
                    out.write(sIn.readByte());
                    //sB.append(sIn.readByte());
                    //rawData[j] = sIn.readByte();
                }
                Charset charset = Charset.forName("UTF-8");
                String var = Base64.getEncoder().encodeToString(out.toByteArray());
                //String frase = new String( rawData, charset);
                //System.out.println("\n\n\nFRASE: " + frase);
                //String machineId = new String(bytes);
                if (msgCode == 0) { // for msgCode "HELLO"
                    try {
                        System.out.println("\n\n----------------HELLO----------");
                        Machine m = plr.machineByAddress(Designation.valueOf(machineId));
                        m.updateNetworkAddress(clientIP.getHostAddress());
                        System.out.println("\n\nIP de Client: " + m.getNetworkAddress());
                        plr.save(m.getProductionLine());
                        sOut.write(ack[1]); // If machine found send ACK message
                        System.out.println("Machine with id: " + machineId + " found!");
                    } catch (Exception e) {
                        sOut.write(nack[1]); // If machine not found send NACK message
                        System.out.println("Machine with id: " + machineId + " not found!");
                        System.out.println("Client " + clientIP.getHostAddress() + ", port number:" + s.getPort() + " disconnected ");
                        s.close();
                    }
                }
                if (msgCode == 1) { // for message
                    try {
                        System.out.println("----------MSG------------------");
                        ProcessMachineMessagesService pms = new ProcessMachineMessagesService();
                        System.out.println("\n\nNumero Bytes na mensagem: " + val2);
                        String ss = sB.toString();
                        //String message = out.toString("UTF-8");
                        System.out.println("\n\nMensagem: " + var);
                        pms.readMachineMessages(var);
                        System.out.println("\nMensagem Gravada com Sucesso!");
                        Thread.currentThread().sleep(11000);
                    } catch (Exception e) {
                        System.out.println("\nMensagem com Formato Errado!!");
                        System.out.println("Client " + clientIP.getHostAddress() + ", port number:" + s.getPort() + " disconnected ");
                        s.close();
                        break;
                    }
                }
            } catch (Exception e2) {
                System.out.println("Error");
                try {
                    Thread.currentThread().sleep(1100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
