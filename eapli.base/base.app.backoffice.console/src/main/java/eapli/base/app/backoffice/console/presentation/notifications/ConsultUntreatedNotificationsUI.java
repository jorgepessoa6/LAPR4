package eapli.base.app.backoffice.console.presentation.notifications;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.machinemanagement.application.ImportConfigTXTFileController;
import eapli.base.machinemanagement.domain.Machine;
import eapli.base.notificationmanagement.application.ConsultUntreatedNotificationsController;
import eapli.base.notificationmanagement.domain.ErrorType;
import eapli.base.productionlinemanagement.domain.ProductionLine;
import eapli.base.productionlinemanagement.repositories.ProductionLineRepository;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import eapli.framework.util.Console;

import java.util.ArrayList;
import java.util.List;

public class ConsultUntreatedNotificationsUI extends AbstractUI {
    private ProductionLineRepository pl = PersistenceContext.repositories().productionLines();
    private final ConsultUntreatedNotificationsController theController = new ConsultUntreatedNotificationsController();
    final List<String> optionList = new ArrayList<>();
    String errorFilter = "Error";
    String productionLineFilter = "Production Line";
    String both = "both";
    String notUseFilter = " No filters usage";

    @Override
    protected boolean doShow() {
        final List<ErrorType> errorType = new ArrayList<>();
        populateList(errorType);
        try {
            optionList.add(errorFilter);
            optionList.add(productionLineFilter);
            optionList.add(both);
            optionList.add(notUseFilter);
            final SelectWidget<String> selectorOpt = new SelectWidget<>("Do you wish to filter by Error, Production Line, Both or not use filters?", optionList);
            selectorOpt.show();
            String option = selectorOpt.selectedElement();
            if(option.equals(errorFilter)) {
                final SelectWidget<ErrorType> opt = new SelectWidget<>("Select an Error Type", errorType);
                ErrorType errorSelected = opt.selectedElement();
                this.theController.consultUntreatedNotificationsByError(errorSelected);
            }
            if(option.equals(productionLineFilter)) {
                final SelectWidget<ProductionLine> optProductionLine = new SelectWidget<>("Select a ProductionLine", pl.findAll());
                ProductionLine plSelected = optProductionLine.selectedElement();
                this.theController.consultUntreatedNotificationsByProductionLine(plSelected);
            }
            if(option.equals(both)){
                final SelectWidget<ErrorType> opt = new SelectWidget<>("Select an Error Type", errorType);
                ErrorType errorSelected = opt.selectedElement();
                final SelectWidget<ProductionLine> optProductionLine = new SelectWidget<>("Select a ProductionLine", pl.findAll());
                ProductionLine plSelected = optProductionLine.selectedElement();
                this.theController.consultUntreatedNotificationsByProductionLineAndErrorType(plSelected,errorSelected);
            }
            if(option.equals(notUseFilter)){
                this.theController.consultEveryUntreatedNotifications();
            }
        } catch (Exception e) {
            System.out.println("Error loading information");
        }
        return false;

    }

    @Override
    public String headline() {
        return "Consult Notifications";
    }

    private void populateList(List<ErrorType> lst) {
        lst.add(ErrorType.ALFANUMERICO_EM_CAMPO_INTEIRO);
        lst.add(ErrorType.REFERENCIA_ELEMENTOS_NAO_ESPECIFICADOS);

    }
}
