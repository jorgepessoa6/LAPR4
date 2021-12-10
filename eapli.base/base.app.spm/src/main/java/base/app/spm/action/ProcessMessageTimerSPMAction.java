package base.app.spm.action;

import base.spm.client.application.ProcessMessageSPMController;
import eapli.framework.actions.Action;

import java.util.Calendar;

public class ProcessMessageTimerSPMAction implements Action {

    double timer;
    Calendar start;
    String idProdLine;

    public ProcessMessageTimerSPMAction(double timer, Calendar start, String idProd) {
        this.timer = timer;
        this.start = start;
        this.idProdLine = idProd;
    }
    @Override
    public boolean execute() {
        ProcessMessageSPMController controller = new ProcessMessageSPMController();
        try {
            controller.startProcessingMessagesTimer(timer, start, idProdLine);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
