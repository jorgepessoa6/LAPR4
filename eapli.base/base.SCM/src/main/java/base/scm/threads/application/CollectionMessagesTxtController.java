package base.scm.threads.application;

import java.io.*;

public class CollectionMessagesTxtController{
    private static final String PATH_ENTRADA = "MensagensEntrada";


    public void CollectMessages() {
        File direc = new File(PATH_ENTRADA);
        int i = 0;
        for (File fileEntry : direc.listFiles()) {
            RunThreadsService r = new RunThreadsService(fileEntry);
            Thread temp = new Thread(r, "Thread #" + i);
            i++;
            temp.start();
            try {
                temp.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}

