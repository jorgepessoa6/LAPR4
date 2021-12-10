package base.spm.client.application;

import eapli.base.core.productmanager.domain.Batch;
import eapli.base.core.productmanager.domain.Product;
import eapli.base.core.productmanager.repositories.ProductRepository;
import eapli.base.depositmanagement.domain.Deposit;
import eapli.base.depositmanagement.repositories.DepositRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.machinemanagement.domain.Machine;
import eapli.base.messagemanagement.domain.Message;
import eapli.base.messagemanagement.domain.MessageStatus;
import eapli.base.messagemanagement.domain.Type;
import eapli.base.messagemanagement.repositories.MessageRepository;
import eapli.base.notificationmanagement.domain.Error;
import eapli.base.notificationmanagement.domain.ErrorType;
import eapli.base.notificationmanagement.domain.Notification;
import eapli.base.notificationmanagement.repositories.NotificationRepository;
import eapli.base.productionlinemanagement.domain.ProductionLine;
import eapli.base.productionlinemanagement.repositories.ProductionLineRepository;
import eapli.base.productionordermanagement.domain.*;
import eapli.base.productionordermanagement.repositories.ProductionOrderRepository;
import eapli.base.rawmaterialmanagement.domain.RawMaterial;
import eapli.base.rawmaterialmanagement.repositories.RawMaterialRepository;
import eapli.framework.general.domain.model.Designation;

import javax.persistence.NoResultException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ProcessMessageBetweenTimesSPMService implements Runnable {
    private final MessageRepository plr = PersistenceContext.repositories().messages();
    private final ProductRepository productRepository = PersistenceContext.repositories().products();
    private final ProductionLineRepository productionLineRepository = PersistenceContext.repositories().productionLines();
    private final ProductionOrderRepository productionOrderRepository = PersistenceContext.repositories().productionOrders();
    private final NotificationRepository notificationRepository = PersistenceContext.repositories().notifications();
    private final DepositRepository depositRepository = PersistenceContext.repositories().deposits();
    private final RawMaterialRepository rawMaterialRepository = PersistenceContext.repositories().rawMaterials();
    private final ProductionLine productionLine;
    private final Calendar dataInicial;
    private final Calendar dataFinal;
    private boolean flag=true;

    public ProcessMessageBetweenTimesSPMService(ProductionLine productionLine, Calendar dataInicial, Calendar dataFinal) {
        this.productionLine = productionLine;
        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;
    }

    @Override
    public void run() {
        System.out.println("Thread" + Thread.currentThread().getName() + " Processing Message");
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        List<Message> validMessages = new ArrayList<>();
        Iterable<Message> messages = plr.allMessagesToProcessByProductionLineByTime(productionLine, dataInicial, dataFinal);
        for (Message message : messages) {
            flag = true;
            message.getMessageDateCreation().getTime();
            Type type = message.getMessage().getType();
            String mess = message.getDesc();
            String[] arr = mess.split(";");
            Machine machine = getMachine(arr[0], message);
            if (machine != null) {
                ProductionLine productionLine = getProductionLine(machine);
                validDate(arr[2], productionLine, message);
                if (type.equals(Type.CONSUMO) || type.equals(Type.ENTREGA_PRODUCAO) || type.equals(Type.ESTORNO)) {
                    validRawMaterial(productionLine, arr[3], message);
                    validQuantity(productionLine, arr[4], message);
                    validDeposit(arr[5], productionLine, message);
                }
                if (type.equals(Type.PRODUCAO)) {
                    validProduct(productionLine, arr[3], message);
                    validQuantity(productionLine, arr[4], message);
                    String lote = arr[5];
                }
                if (type.equals(Type.INICIO_ACTIVIDADE) || type.equals(Type.FIM_ATIVIDADE)) {
                    String ordemId = getOrder(arr, machine, productionLine, message);
                    validOrder(productionLine, ordemId, message);
                }
                if (flag) {
                    validMessages.add(message);
                }
            }
        }
        Date firstDate = null;
        Date lastDate = null;
        Date dateForMachine = null;
        int quantProduto = 0;
        int quantProdutoTotal = 0;
        int quantMaterialConsumido = 0;
        String loteA = null;
        String loteB = null;
        ProductionOrder productionOrder = null;
        ProductionOrder productionOrder1 = null;
        Date date = null;
        Product product = null;
        RawMaterial material = null;
        int quantMaterial = 0;
        String depositoA = null;
        String depositoB = null;
        boolean firstMessage = true;
        Machine m = null;
        int quantityConsumo = 1;
        Machine m2;
        ProductionOrderExecutionEvent productionOrderExecutionEvent = null;
        long paragem = 0;
        long paragemMaquina = 0;
        for (Message vMessage : validMessages) {
            m2 = vMessage.getMach();
            if(m != null && m != m2 ){
                paragemMaquina = 0;
                firstMessage = true;
            }
            Type type = vMessage.getMessage().getType();
            if(firstMessage == true && !type.equals(Type.INICIO_ACTIVIDADE)){
                break;
            }else{
                m = vMessage.getMach();
                firstMessage = false;
            }
            String mess = vMessage.getDesc();
            String[] arr = mess.split(";");

            if(type.equals(Type.INICIO_ACTIVIDADE)){
                try {
                    dateForMachine = df.parse(arr[2]);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                quantMaterial = 0;
                quantProduto = 0;
                productionOrder1 = productionOrderRepository.productionOrderByDesignation(Designation.valueOf(arr[3]));
                if(productionOrder != productionOrder1) {
                    try {
                        firstDate = df.parse(arr[2]);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(firstDate);
                    productionOrder = productionOrderRepository.productionOrderByDesignation(Designation.valueOf(arr[3]));
                    productionOrderExecutionEvent = new ProductionOrderExecutionEvent(calendar);
                }
            }
            if(type.equals(Type.CONSUMO)){
                material = rawMaterialRepository.rawMaterialByDesignation(Designation.valueOf(arr[3]));
                depositoB = arr[5];
                if(depositoA == null){
                    depositoA = arr[5];
                }else if(depositoA.equals(depositoB)){
                    quantMaterial += Integer.parseInt(arr[4]);
                    quantMaterialConsumido += Integer.parseInt(arr[4]);
                }else {
                    createConsumption(material, quantMaterial, depositoA, productionOrderExecutionEvent);
                    quantMaterial = Integer.parseInt(arr[4]);
                    quantMaterialConsumido += Integer.parseInt(arr[4]);
                    depositoA = depositoB;
                    depositoB = null;
                }
            }
            if(type.equals(Type.PRODUCAO)){
                product = productRepository.productByDesignation(Designation.valueOf(arr[3]));
                loteB = arr[5];
                if(loteA == null){
                    loteA = arr[5];
                    quantProduto += Integer.parseInt(arr[4]);
                    quantProdutoTotal += Integer.parseInt(arr[4]);
                }else if(loteA.equals(loteB)){
                    quantProduto += Integer.parseInt(arr[4]);
                    quantProdutoTotal += Integer.parseInt(arr[4]);
                }else {
                    createBatch(quantProduto, loteA, product, productionOrderExecutionEvent);
                    loteA = loteB;
                    quantProduto = Integer.parseInt(arr[4]);
                    quantProdutoTotal += Integer.parseInt(arr[4]);
                    loteB = null;
                }
            }
            if(type.equals(Type.ESTORNO)){
                RawMaterial rawMaterial = rawMaterialRepository.rawMaterialByDesignation(Designation.valueOf(arr[3]));
                Deposit deposit = depositRepository.depositByDesignation(Designation.valueOf(arr[5]));
                Replacement replacement = new Replacement(Double.parseDouble(arr[4]), rawMaterial.identity().toString(),deposit.identity().toString());
                productionOrderExecutionEvent.addReplacement(replacement);
            }
            if(type.equals(Type.PARAGEM)){
                try {
                    date = df.parse(arr[2]);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if(type.equals(Type.RETOMA_ATIVIDADE)){
                Date date1 = null;
                try {
                    date1 = df.parse(arr[2]);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                paragemMaquina += Math.abs(date1.getTime() - date.getTime());
                paragem += Math.abs(date1.getTime() - date.getTime());
            }
            if(type.equals(Type.ENTREGA_PRODUCAO)){
                int quant = Integer.parseInt(arr[4]);
                String prodId = arr[3];
                Batch batchId = null;
                for(Batch batch : productionOrderExecutionEvent.getBatch()){
                    if(batch.getQuantity() == quant && batch.getProduct().equals(prodId)){
                        batchId = batch;
                    }
                }
                if(batchId != null){
                    Deposit deposit = depositRepository.depositByDesignation(Designation.valueOf(arr[5]));
                    productionOrderExecutionEvent.addDeliverProduction(new DeliverProduction(quant, deposit.identity().toString(), batchId.getBatchId()));
                }
            }
            if(type.equals(Type.FIM_ATIVIDADE)){
                try {
                    lastDate = df.parse(arr[2]);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                createConsumption(material, quantMaterial, depositoA, productionOrderExecutionEvent);

                if(quantMaterial != 0 && quantProduto != 0) {
                    int desvioProduto = quantProdutoTotal - productionOrder.getQuantity();
                    DeflectionProduction deflectionProduction = new DeflectionProduction(desvioProduto, product.identity().toString());
                    productionOrderExecutionEvent.addDeflectionProd(deflectionProduction);
                    int desvioConsumo = 0;
                        /*for (RawMaterial r : product.pRecord().materials()) {
                            int i = 0;
                            if (r.identity().toString().equals(material.identity().toString())) {
                                quantityConsumo = product.pRecord().quantity().get(i);
                            }
                            i++;
                        }
                         */
                    if(loteA != loteB ) {
                        createBatch(quantProduto, loteA, product, productionOrderExecutionEvent);
                        loteB = loteA;
                    }
                    desvioConsumo = quantMaterialConsumido - (quantMaterial * quantityConsumo);
                    DeflectionConsumption deflectionConsumption = new DeflectionConsumption(desvioConsumo, material.identity().toString());
                    productionOrderExecutionEvent.addDeflectionCons(deflectionConsumption);
                }
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(lastDate);
                productionOrderExecutionEvent.setEndTime(calendar);
                long diffInMillies = Math.abs(lastDate.getTime() - firstDate.getTime());
                productionOrderExecutionEvent.setRawTime(new RawTime(TimeUnit.MILLISECONDS.toMinutes(diffInMillies)));
                productionOrderExecutionEvent.setActiveTime(new ActiveTime(TimeUnit.MILLISECONDS.toMinutes(diffInMillies - paragem)));
                long diffInMilliesMachine = Math.abs(lastDate.getTime() - dateForMachine.getTime());
                productionOrderExecutionEvent.addMachineActiveTime(new MachineActiveTime(TimeUnit.MILLISECONDS.toMinutes(diffInMilliesMachine - paragemMaquina), m.identity().toString()));
                productionOrderExecutionEvent.addMachineRawTime(new MachineRawTime(TimeUnit.MILLISECONDS.toMinutes(diffInMilliesMachine), m.identity().toString()));


            }
            vMessage.setMessageState(MessageStatus.PROCESSADA);
            plr.save(vMessage);
        }
        if(productionOrder != null) {
            productionOrder.setProdOrderEvent(productionOrderExecutionEvent);
            productionOrderRepository.save(productionOrder);
        }
        Calendar calendar = Calendar.getInstance();
        LocalDateTime localDateTime = LocalDateTime.now();
        calendar.set(localDateTime.getYear(), localDateTime.getMonthValue() - 1, localDateTime.getDayOfMonth(),
                localDateTime.getHour(), localDateTime.getMinute(), localDateTime.getSecond());

        productionLine.setDateLastProcessement(calendar);
        productionLineRepository.save(productionLine);
    }

    private ProductionOrder validOrder(ProductionLine productionLine, String ordemId, Message message) {
        try {
            return productionOrderRepository.productionOrderByDesignation(Designation.valueOf(ordemId));
        } catch (NoResultException e) {
            flag = false;
            Error error = new Error(ErrorType.REFERENCIA_ELEMENTOS_NAO_ESPECIFICADOS, "Ordem de Produção não especificada no sistema");
            notificationRepository.save(new Notification(message, error, productionLine));
            System.out.println("NoResultException: " + e.getMessage());
        }
        return null;
    }

    private String getOrder(String[] arr, Machine machine, ProductionLine productionLine, Message message) {
        String ordemId = null;
        try {
            ordemId = arr[3];
        } catch (ArrayIndexOutOfBoundsException e) {
            flag = false;

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
        return ordemId;
    }

    private void createBatch(int quantProduto, String loteA, Product product, ProductionOrderExecutionEvent productionOrderExecutionEvent) {
        Batch batch = new Batch(loteA, product.identity().toString(), quantProduto);
        productionOrderExecutionEvent.addBatch(batch);
    }

    private void createConsumption(RawMaterial material, int quantMaterial, String depositoA, ProductionOrderExecutionEvent productionOrderExecutionEvent) {
        Deposit deposit = depositRepository.depositByDesignation(Designation.valueOf(depositoA));
        Consumption consumption = new Consumption(quantMaterial, material.identity().toString(), deposit.identity().toString());
        productionOrderExecutionEvent.addConsumption(consumption);
    }

    private Deposit validDeposit(String deposito1, ProductionLine productionLine, Message message) {
        String deposito = deposito1;
        try {
            return depositRepository.depositByDesignation(Designation.valueOf(deposito));
        } catch (NoResultException e) {
            flag = false;

            Error error = new Error(ErrorType.REFERENCIA_ELEMENTOS_NAO_ESPECIFICADOS, "Deposito não especificada no sistema");
            notificationRepository.save(new Notification(message, error, productionLine));
            System.out.println("NoResultException: " + e.getMessage());
        }
        return null;
    }

    private int validQuantity(ProductionLine productionLine, String s, Message message) {
        try {
            return Integer.parseInt(s.trim());
        } catch (NumberFormatException e) {
            flag = false;

            Error error = new Error(ErrorType.ALFANUMERICO_EM_CAMPO_INTEIRO, "Quantidade inválida");
            notificationRepository.save(new Notification(message, error, productionLine));
            System.out.println("NumberFormatException: " + e.getMessage());
        }
        return 0;
    }

    private Product validProduct(ProductionLine productionLine, String productId1, Message message) {
        String productId = productId1;
        try {
            return productRepository.productByDesignation(Designation.valueOf(productId));
        } catch (NoResultException e) {
            flag = false;

            Error error = new Error(ErrorType.REFERENCIA_ELEMENTOS_NAO_ESPECIFICADOS, "Produto não especificada no sistema");
            notificationRepository.save(new Notification(message, error, productionLine));
            System.out.println("NoResultException: " + e.getMessage());
        }
        return null;
    }

    private RawMaterial validRawMaterial(ProductionLine productionLine, String rawMaterialId, Message message) {
        String rawMaterialId1 = rawMaterialId;
        try {
            return rawMaterialRepository.rawMaterialByDesignation(Designation.valueOf(rawMaterialId1));
        } catch (NoResultException e) {
            flag = false;

            Error error = new Error(ErrorType.REFERENCIA_ELEMENTOS_NAO_ESPECIFICADOS, "Material não especificada no sistema");
            notificationRepository.save(new Notification(message, error, productionLine));
            System.out.println("NoResultException: " + e.getMessage());
        }
        return null;
    }

    private long validDate(String s, ProductionLine productionLine, Message message) {
        try {
            return Long.parseLong(s.trim());
        } catch (NumberFormatException e) {
            flag = false;

            Error error = new Error(ErrorType.ALFANUMERICO_EM_CAMPO_INTEIRO, "Data inválida");
            notificationRepository.save(new Notification(message, error, productionLine));
            System.out.println("NumberFormatException: " + e.getMessage());
        }
        return 0;
    }

    private ProductionLine getProductionLine(Machine machine) {
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
        return productionLine;
    }

    private Machine getMachine(String maquina1, Message message) {
        String maquina = maquina1;
        Machine machine = null;
        try {
            machine = productionLineRepository.machineByAddress(Designation.valueOf(maquina));
        } catch (NoResultException e) {
            flag = false;

            Error error = new Error(ErrorType.REFERENCIA_ELEMENTOS_NAO_ESPECIFICADOS, "Maquina não especificada no sistema");
            notificationRepository.save(new Notification(message, error, null));
            System.out.println("NoResultException: " + e.getMessage());
        }
        return machine;
    }

}
