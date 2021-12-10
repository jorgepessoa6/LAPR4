package base.app.spm.run;

import base.app.spm.action.ProcessMessageTimerSPMAction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ProcessMessageTimerSPM {

    public static void main(String args[]){
        double intervalo = Double.parseDouble(args[0]);
        SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
        Calendar dataInicial = Calendar.getInstance();
        try {
            dataInicial.setTime(sdf.parse(args[1]));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ProcessMessageTimerSPMAction action;
        try{
            String idProd = args[2];
            action = new ProcessMessageTimerSPMAction(intervalo, dataInicial, idProd);
        }catch (ArrayIndexOutOfBoundsException e){
            action = new ProcessMessageTimerSPMAction(intervalo, dataInicial, null);
            System.out.println("ArrayIndexOutOfBoundsException: " + e.getMessage());
        }
        action.execute();
    }
}
