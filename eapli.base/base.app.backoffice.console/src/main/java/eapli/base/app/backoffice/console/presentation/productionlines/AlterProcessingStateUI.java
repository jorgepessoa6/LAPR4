
package eapli.base.app.backoffice.console.presentation.productionlines;

import base.spm.client.application.ProcessMessageSPMController;
import eapli.base.productionlinemanagement.domain.ProductionLine;
import eapli.base.productionlinemanagement.domain.ProductionLineStatus;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import eapli.framework.util.Console;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AlterProcessingStateUI extends AbstractUI {

    private static final String OPTION_1 = "1- Activate/Deactivate  the recurrent processing of messages";
    private static final String OPTION_2 = "2- (Re)processing of messages for a given time interval";
    ProcessMessageSPMController controller1 = new ProcessMessageSPMController();

    @Override
    protected boolean doShow() {
        try {
            final Iterable<ProductionLine> listMat = controller1.allProductionLines();
            final SelectWidget<ProductionLine> selectorMat = new SelectWidget<>("Select a ProductionLine", listMat);
            selectorMat.show();
            ProductionLine pl = selectorMat.selectedElement();

            ProductionLineStatus status = pl.getProductionLineStatus();
            System.out.println("\nState: " + status.toString());
            Calendar lastUpdate = pl.getDateLastProcessement();
            if (lastUpdate == null) {
                System.out.println("\nLast processing was never");
            } else {
                System.out.println("\nLast processing:" + lastUpdate.getTime().toString());
            }
            //Show state and last update

            final SelectWidget<String> selectorOpt = new SelectWidget<>("Select an Option", fillOptions());
            selectorOpt.show();
            String opt = selectorOpt.selectedElement();
            switch (opt) {
                case OPTION_1:
                    if (status.toString().equalsIgnoreCase("NON_ACTIVE")) {
                        final int timeInterval = Console.readInteger("Time interval in minutes?");
                        final String startTime = Console.readLine("Do you with to add a specific time [Y/N] (N- Current time)");
                        Calendar cal = Calendar.getInstance();
                        if (startTime.equalsIgnoreCase("Y") || startTime.equalsIgnoreCase("yes")) {
                            cal = Console.readCalendar("Start Date dd/MM/yyyy HH:mm ?", "dd/MM/yyyy HH:mm");
                        }
                        controller1.startProcessingMessagesTimer(timeInterval, cal, pl.identity().toString());

                    } else {
                        controller1.interruptThread(pl);
                    }

                    break;
                case OPTION_2:
                    //time interval
                    final Calendar date1 = Console.readCalendar("Date 1 dd/MM/yyyy?", "dd/MM/yyyy");
                    final Calendar date2 = Console.readCalendar("Date 2 dd/MM/yyyy?", "dd/MM/yyyy");
                    controller1.startProcessingMessagesBetweenTimes(date1, date2, pl.identity().toString());
                    break;
            }
            System.out.println("\nSucess!");
            return true;
        } catch (
                Exception e) {
            System.out.println("Error");
        }
        return false;
    }

    protected List<String> fillOptions() {
        List<String> options = new ArrayList<>();
        options.add(OPTION_1);
        options.add(OPTION_2);
        return options;
    }

    @Override
    public String headline() {
        return "Alter processing state";
    }
}
