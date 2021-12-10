/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.app.backoffice.console.presentation.notifications;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.notificationmanagement.application.ConsultArchivedNotificationsController;
import eapli.base.notificationmanagement.domain.ErrorType;
import eapli.base.notificationmanagement.domain.Notification;
import eapli.base.productionlinemanagement.domain.ProductionLine;
import eapli.base.productionlinemanagement.repositories.ProductionLineRepository;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import eapli.framework.util.Console;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.collections.IteratorUtils;

/**
 *
 * @author Bruno Pereira
 */
public class ConsultArchivedNotificationsUI extends AbstractUI{
    
    private final ConsultArchivedNotificationsController theController = new ConsultArchivedNotificationsController();
    private ProductionLineRepository pl = PersistenceContext.repositories().productionLines();
    
    
    
    private final String errorFilter = "Error";
    private final String productionLineFilter = "Production Line";
    private final String both = "Both";
    private final String notUseFilter = " None";
    
    @Override
    protected boolean doShow() {
        final List<ErrorType> errorType = new ArrayList<>();
        final List<String> optionList = new ArrayList<>();
        populateList(errorType);
        populateOptionList(optionList);
        try {
            System.out.println("\nWhich filter do you what to use?\n");
            final SelectWidget<String> selectorOpt = new SelectWidget<>("Select an option", optionList);
            selectorOpt.show();
            String option = selectorOpt.selectedElement();
            
            if(option.equals(errorFilter)) {
                final SelectWidget<ErrorType> opt = new SelectWidget<>("Select an Error Type", errorType);
                ErrorType errorSelected = opt.selectedElement();
                this.theController.consultArchivedNotificationsByError(errorSelected);
            }
            if(option.equals(productionLineFilter)) {
                final SelectWidget<ProductionLine> optProductionLine = new SelectWidget<>("Select a ProductionLine", pl.findAll());
                ProductionLine plSelected = optProductionLine.selectedElement();
                this.theController.consultArchivedNotificationsByProductionLine(plSelected);
            }
            if(option.equals(both)){
                final SelectWidget<ErrorType> opt = new SelectWidget<>("Select an Error Type", errorType);
                ErrorType errorSelected = opt.selectedElement();
                final SelectWidget<ProductionLine> optProductionLine = new SelectWidget<>("Select a ProductionLine", pl.findAll());
                ProductionLine plSelected = optProductionLine.selectedElement();
                this.theController.consultArchivedNotificationsByProductionLineAndErrorType(plSelected,errorSelected);
            }
            if(option.equals(notUseFilter)){
                this.theController.consultAllArchivedNotifications();
            }
        } catch (Exception e) {
            System.out.println("Error loading information");
        }
        return false;

    }

    @Override
    public String headline() {
        return "Consult Archive Notifications";
    }
    
    private void populateList(List<ErrorType> lst) {
        lst.add(ErrorType.ALFANUMERICO_EM_CAMPO_INTEIRO);
        lst.add(ErrorType.REFERENCIA_ELEMENTOS_NAO_ESPECIFICADOS);
    }
    
    private void populateOptionList(List<String> optionList){
        optionList.add(errorFilter);
        optionList.add(productionLineFilter);
        optionList.add(both);
        optionList.add(notUseFilter);
    }
    
}
