package base.app.spm.run;

import base.app.spm.action.ProcessMessageBetweenTimesSPMAction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;

public class ProcessMessageBetweenTimesSPM {

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
        Calendar dataInicial = Calendar.getInstance();
        Calendar dataFinal = Calendar.getInstance();
        dataInicial.setTime(sdf.parse(args[0]));
        dataFinal.setTime(sdf.parse(args[1]));
        LocalDateTime localDateTime = LocalDateTime.now();
        dataFinal.set(localDateTime.getYear(), localDateTime.getMonthValue() - 1, localDateTime.getDayOfMonth());
        ProcessMessageBetweenTimesSPMAction action;
        try{
            String idProd = args[2];
            action = new ProcessMessageBetweenTimesSPMAction(dataInicial, dataFinal, idProd);
        }catch (ArrayIndexOutOfBoundsException e){
            action = new ProcessMessageBetweenTimesSPMAction(dataInicial, dataFinal, null);
            System.out.println("ArrayIndexOutOfBoundsException: " + e.getMessage());
        }
        action.execute();
    }
}
