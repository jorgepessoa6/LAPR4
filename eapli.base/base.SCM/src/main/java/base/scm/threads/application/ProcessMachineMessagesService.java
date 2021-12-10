package base.scm.threads.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.machinemanagement.domain.Machine;
import eapli.base.messagemanagement.domain.Message;
import eapli.base.messagemanagement.domain.Type;
import eapli.base.messagemanagement.repositories.MessageRepository;
import eapli.base.productionlinemanagement.repositories.ProductionLineRepository;
import eapli.framework.general.domain.model.Designation;

import java.util.Calendar;

public class ProcessMachineMessagesService {
    private final MessageRepository mr = PersistenceContext.repositories().messages();
    private final ProductionLineRepository plr = PersistenceContext.repositories().productionLines();

    public void readMachineMessages(String line) {
        Type t;
        String[] values = line.split(";");
        Machine m=null;
        try {
             m = plr.machineByAddress(Designation.valueOf(values[0]));
        }catch (Exception e){
             m = plr.machineByAddress(Designation.valueOf("M"+values[0]));
        }
        switch (values[1]) {
            case "C0":
                t = Type.CONSUMO;
                persistMessage(m, t, line);
                break;
            case "C9":
                t = Type.ENTREGA_PRODUCAO;
                persistMessage(m, t, line);
                break;
            case "P1":
                t = Type.PRODUCAO;
                persistMessage(m, t, line);
                break;
            case "P2":
                t = Type.ESTORNO;
                persistMessage(m, t, line);
                break;
            case "S0":
                t = Type.INICIO_ACTIVIDADE;
                persistMessage(m, t, line);
                break;
            case "S1":
                t = Type.RETOMA_ATIVIDADE;
                persistMessage(m, t, line);
                break;
            case "S8":
                t = Type.PARAGEM;
                persistMessage(m, t, line);
                break;
            case "S9":
                t = Type.FIM_ATIVIDADE;
                persistMessage(m, t, line);
                break;
        }
    }

    private void persistMessage(Machine m, Type t, String line) {
        Message mess = new Message(m, Calendar.getInstance(), t, line);
        mr.save(mess);
        System.out.println("Message sent successfully");
    }
}
