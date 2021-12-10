package base.scm.threads.application;

import eapli.base.messagemanagement.domain.Type;

import java.io.*;

public class RunThreadsService implements Runnable {
    private static final String PATH_SAIDA = "MensagensProcessada";
    File f;
    private final ProcessMachineMessagesService svc = new ProcessMachineMessagesService();
    private final TransferFileService svc2 = new TransferFileService();

    public RunThreadsService(File fileEntry) {
        this.f = fileEntry;
    }

    @Override
    public void run() {
        System.out.println("Thread" + Thread.currentThread().getName() + " Looking for file");
        try {
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String line = "";
            Type t;
            while ((line = br.readLine()) != null) {
                svc.readMachineMessages(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            svc2.transferFile(f, PATH_SAIDA);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Thread " + Thread.currentThread().getName() + " Sucess!");

    }
}

