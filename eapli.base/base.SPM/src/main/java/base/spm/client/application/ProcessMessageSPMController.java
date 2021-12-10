package base.spm.client.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.messagemanagement.repositories.MessageRepository;
import eapli.base.productionlinemanagement.domain.ProductionLine;
import eapli.base.productionlinemanagement.repositories.ProductionLineRepository;
import eapli.framework.general.domain.model.Designation;

import java.time.LocalDateTime;
import java.util.*;

public class ProcessMessageSPMController {

    private final MessageRepository plr = PersistenceContext.repositories().messages();
    private final ProductionLineRepository productionLineRepository = PersistenceContext.repositories().productionLines();
    private double intervalo;
    private Calendar dataInicio;
    private Calendar dataFim;
    private String idProdLine;
    private static List<Thread> lstThreads = new ArrayList<>();

    public void startProcessingMessagesTimer(double intervalo, Calendar dataInicio, String idProdLine) {
        this.intervalo = intervalo;
        this.dataInicio = dataInicio;
        this.idProdLine = idProdLine;
        ProcessingMessagesTask task = new ProcessingMessagesTask();
        Timer timer = new Timer();
        Date date = dataInicio.getTime();
        timer.schedule(task,date);
    }

/*    public boolean checkExistingThreads() {
        for (Thread p :
                lstThreads) {
            if (idProdLine.equalsIgnoreCase(p.getName())) {
                p.activateProcessing();
                return true;
            }
        }
        return false;
    }*/

    class ProcessingMessagesTask extends TimerTask {
        @Override
        public void run() {
            if (idProdLine != null) {
                ProductionLine productionLine = productionLineRepository.productionLineByDesig(Designation.valueOf(idProdLine));
                ProcessMessageSPMService r = new ProcessMessageSPMService(productionLine, intervalo);
                Thread temp = new Thread(r, productionLine.identity().toString());
                lstThreads.add(temp);
                temp.start();
                try {
                    temp.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                Iterable<ProductionLine> productionLines = productionLineRepository.nonActiveForProcess();
                for (ProductionLine productionLine : productionLines) {
                    ProcessMessageSPMService r = new ProcessMessageSPMService(productionLine, intervalo);
                    Thread temp = new Thread(r, productionLine.identity().toString());
                    lstThreads.add(temp);
                    temp.start();
                    try {
                        temp.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public boolean interruptThread(ProductionLine p) {
        for (Thread pl : lstThreads) {
            if (p.identity().toString().equalsIgnoreCase(pl.getName())) {
                pl.interrupt();
                System.out.println("\nThread finished!");
                return true;
            }
        }
        System.out.println("\n Thread not interruptedd :(");
        return false;
    }

    public void startProcessingMessagesBetweenTimes(Calendar d1, Calendar d2, String idProdLine) {
        this.dataInicio = d1;
        this.dataFim = d2;
        this.idProdLine = idProdLine;
        int i = 0;
        if (idProdLine != null) {
            ProductionLine productionLine = productionLineRepository.productionLineByDesig(Designation.valueOf(idProdLine));
            ProcessMessageBetweenTimesSPMService r = new ProcessMessageBetweenTimesSPMService(productionLine, dataInicio, dataFim);
            Thread temp = new Thread(r, "Thread #" + i);
            i++;
            temp.start();
            try {
                temp.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            Iterable<ProductionLine> productionLines = productionLineRepository.nonActiveForProcess();
            for (ProductionLine productionLine : productionLines) {
                ProcessMessageBetweenTimesSPMService r = new ProcessMessageBetweenTimesSPMService(productionLine, dataInicio, dataFim);
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

    public Iterable<ProductionLine> allProductionLines() {
        return productionLineRepository.findAll();
    }
}
