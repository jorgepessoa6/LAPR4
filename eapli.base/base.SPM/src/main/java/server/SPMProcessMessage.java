package server;

import eapli.base.core.productmanager.repositories.ProductRepository;
import eapli.base.depositmanagement.repositories.DepositRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.machinemanagement.domain.Machine;
import eapli.base.messagemanagement.domain.Message;
import eapli.base.messagemanagement.domain.Type;
import eapli.base.messagemanagement.repositories.MessageRepository;
import eapli.base.notificationmanagement.domain.Error;
import eapli.base.notificationmanagement.domain.Notification;
import eapli.base.notificationmanagement.domain.ErrorType;
import eapli.base.notificationmanagement.repositories.NotificationRepository;
import eapli.base.productionlinemanagement.domain.ProductionLine;
import eapli.base.productionlinemanagement.repositories.ProductionLineRepository;
import eapli.base.productionordermanagement.domain.ProductionOrder;
import eapli.base.productionordermanagement.repositories.ProductionOrderRepository;
import eapli.framework.general.domain.model.Designation;

import javax.persistence.NoResultException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class SPMProcessMessage {

    public static void main(String args[]) throws ParseException {
        if(args[0].isEmpty() && args[1].isEmpty()){
            new Thread(new ProcessMessage()).start();
        }else {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Calendar dataInicial = Calendar.getInstance();
            Calendar dataFinal = Calendar.getInstance();
            dataInicial.setTime(sdf.parse(args[0]));
            dataFinal.setTime(sdf.parse(args[1]));
            new Thread(new ProcessMessage(dataFinal, dataInicial)).start();
        }
    }

}

 class ProcessMessage implements Runnable {

    private final Calendar dataInicial;
    private final Calendar dataFinal;

    private final MessageRepository plr = PersistenceContext.repositories().messages();
    private final ProductRepository productRepository = PersistenceContext.repositories().products();
    private final ProductionLineRepository productionLineRepository = PersistenceContext.repositories().productionLines();
    private final ProductionOrderRepository productionOrderRepository = PersistenceContext.repositories().productionOrders();
    private final NotificationRepository notificationRepository = PersistenceContext.repositories().notifications();
    private final DepositRepository depositRepository = PersistenceContext.repositories().deposits();

    public ProcessMessage(Calendar dataFinal, Calendar dataInicial) {
        this.dataFinal = dataFinal;
        this.dataInicial = dataInicial;
    }

    public ProcessMessage(){
        this.dataInicial = null;
        this.dataFinal = null;
    }

     public void run() {
         Iterable<Message> messages;
         List<Message> validMessages = new ArrayList<>();
         if (dataFinal == null && dataInicial == null) {
             messages = plr.findAll();
         } else {
             messages = plr.allMessagesByTime(dataInicial, dataFinal);
         }
         for (Message message : messages) {
             message.getMessageDateCreation().getTime();
             Type type = message.getMessage().getType();
             String mess = message.getDesc();
             String[] arr = mess.split(";");
             String maquina = arr[0];
             Machine machine = null;
             System.out.println(maquina);
             try {
                 machine = productionLineRepository.machineByAddress(Designation.valueOf(maquina));
             } catch (NoResultException e) {
                 Error error = new Error(ErrorType.REFERENCIA_ELEMENTOS_NAO_ESPECIFICADOS, "Maquina não especificada no sistema");
                 notificationRepository.save(new Notification(message, error, null));
                 System.out.println("NoResultException: " + e.getMessage());
             }
             if(machine != null) {
                 Iterable<ProductionLine> productionLines = productionLineRepository.findAll();
                 ProductionLine productionLine = null;
                 for (ProductionLine productionLine1 : productionLines) {
                     for (Machine machine1 : productionLine1.getMachines()) {
                         System.out.println(machine);
                         if (machine.equals(machine1)) {
                             productionLine = productionLine1;
                             break;
                         }
                     }
                 }
                 try {
                     Long.parseLong(arr[2].trim());
                 } catch (NumberFormatException e) {
                     Error error = new Error(ErrorType.ALFANUMERICO_EM_CAMPO_INTEIRO, "Data inválida");
                     notificationRepository.save(new Notification(message, error, productionLine));
                     System.out.println("NumberFormatException: " + e.getMessage());
                 }
                 if (type.equals(Type.CONSUMO) || type.equals(Type.ENTREGA_PRODUCAO) || type.equals(Type.ESTORNO)) {
                     String productId = arr[3];
                     try {
                         productRepository.containsOfIdentity(Designation.valueOf(productId));
                     } catch (NoResultException e) {
                         Error error = new Error(ErrorType.REFERENCIA_ELEMENTOS_NAO_ESPECIFICADOS, "Produto não especificada no sistema");
                         notificationRepository.save(new Notification(message, error, productionLine));
                         System.out.println("NoResultException: " + e.getMessage());
                     }
                     try {
                         Integer.parseInt(arr[4].trim());
                     } catch (NumberFormatException e) {
                         Error error = new Error(ErrorType.ALFANUMERICO_EM_CAMPO_INTEIRO, "Quantidade inválida");
                         notificationRepository.save(new Notification(message, error, productionLine));
                         System.out.println("NumberFormatException: " + e.getMessage());
                     }
                     String deposito = arr[5];
                     try {
                         depositRepository.containsOfIdentity(Designation.valueOf(deposito));
                     } catch (NoResultException e) {
                         Error error = new Error(ErrorType.REFERENCIA_ELEMENTOS_NAO_ESPECIFICADOS, "Deposito não especificada no sistema");
                         notificationRepository.save(new Notification(message, error, productionLine));
                         System.out.println("NoResultException: " + e.getMessage());
                     }
                 }
                 if (type.equals(Type.PRODUCAO)) {
                     String productId = arr[3];
                     try {
                         productRepository.containsOfIdentity(Designation.valueOf(productId));
                     } catch (NoResultException e) {
                         Error error = new Error(ErrorType.REFERENCIA_ELEMENTOS_NAO_ESPECIFICADOS, "Produto não especificada no sistema");
                         notificationRepository.save(new Notification(message, error, productionLine));
                         System.out.println("NoResultException: " + e.getMessage());
                     }
                     try {
                         Integer.parseInt(arr[4].trim());
                     } catch (NumberFormatException e) {
                         Error error = new Error(ErrorType.ALFANUMERICO_EM_CAMPO_INTEIRO, "Quantidade inválida");
                         notificationRepository.save(new Notification(message, error, productionLine));
                         System.out.println("NumberFormatException: " + e.getMessage());
                     }
                     String lote = arr[5];
                 }
                 if (type.equals(Type.INICIO_ACTIVIDADE) || type.equals(Type.FIM_ATIVIDADE)) {
                     String ordemId = null;
                     try {
                         ordemId = arr[3];
                     } catch (ArrayIndexOutOfBoundsException e) {
                         for (ProductionOrder productionOrder : productionOrderRepository.findAll()) {
                             if (productionOrder.getProductionLine().equals(productionLine)) {
                                 for (Machine machine1 : productionLine.getMachines()) {
                                     if (machine1.equals(machine)) {
                                         ordemId = productionOrder.identity().toString();
                                         String v = ";";
                                         String desc = arr[0] + v + arr[1] + v + arr[2] + v + ordemId;
                                         message.setDesc(desc);
                                         plr.save(message);
                                         break;
                                     }
                                 }
                             }
                         }
                         System.out.println("ArrayIndexOutOfBoundsException: " + e.getMessage());
                     }
                     try {
                         productionOrderRepository.containsOfIdentity(Designation.valueOf(ordemId));
                     } catch (NoResultException e) {
                         Error error = new Error(ErrorType.REFERENCIA_ELEMENTOS_NAO_ESPECIFICADOS, "Ordem de Produção não especificada no sistema");
                         notificationRepository.save(new Notification(message, error, productionLine));
                         System.out.println("NoResultException: " + e.getMessage());
                     }
                 }
                 validMessages.add(message);
             }
         }
         for (Message vMessage : validMessages) {
             Machine m = vMessage.getMach();
         }
     }
}
