package base.app.spm.action;

import base.spm.client.application.ProcessMessageSPMController;
import eapli.framework.actions.Action;

import java.util.Calendar;

public class ProcessMessageBetweenTimesSPMAction implements Action {

    Calendar dataIncial;
    Calendar dataFinal;
    String idProdLine;

    public ProcessMessageBetweenTimesSPMAction(Calendar dataIncial, Calendar dataFinal, String idProd) {
        this.dataFinal = dataFinal;
        this.dataIncial = dataIncial;
        this.idProdLine = idProd;
    }
    @Override
    public boolean execute() {
        ProcessMessageSPMController controller = new ProcessMessageSPMController();
        try {
            controller.startProcessingMessagesBetweenTimes(dataIncial, dataFinal, idProdLine);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
