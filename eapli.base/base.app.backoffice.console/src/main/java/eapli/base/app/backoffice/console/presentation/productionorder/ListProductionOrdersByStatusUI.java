/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.app.backoffice.console.presentation.productionorder;

import eapli.base.productionordermanagement.application.ListProductionOrdersByStatusController;
import eapli.base.productionordermanagement.domain.ProductionOrder;
import eapli.base.productionordermanagement.domain.ProductionOrderStatus;
import eapli.base.productionordermanagement.domain.Status;
import eapli.framework.presentation.console.AbstractListUI;
import eapli.framework.presentation.console.SelectWidget;
import eapli.framework.util.Console;
import eapli.framework.visitor.Visitor;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Bruno Pereira
 */
public class ListProductionOrdersByStatusUI extends AbstractListUI {

    private final ListProductionOrdersByStatusController theController = new ListProductionOrdersByStatusController();
    private Status status = Status.PENDENTE;

    @Override
    protected Iterable<ProductionOrder> elements() {
        
        List<Status> elements = new ArrayList<>();
        populateList(elements);
        
        try {
        System.out.println("List of Production Order Status, choose One ");
        final SelectWidget<Status> selectorMat = new SelectWidget<>("Select one", elements);
        selectorMat.show();
        status = selectorMat.selectedElement();
        
        } catch (Exception e) {
            System.out.println("Error selecting status");
        }
        return this.theController.listarOrdensDeProducaoPorEstado(status);

    }

    private void populateList(List<Status> lst) {
        lst.add(Status.PENDENTE);
        lst.add(Status.EM_EXECUCAO);
        lst.add(Status.EXECUCAO_PARADA);
        lst.add(Status.CONCLUIDA);
        lst.add(Status.SUSPENSA);
    }

    @Override
    protected String elementName() {
        return "Ordem de producao";
    }

    @Override
    protected String listHeader() {
        return "Ordens de producao";
    }

    @Override
    public String headline() {
        return "Listar ordens de producao num certo estado";
    }

    @Override
    protected Visitor<ProductionOrder> elementPrinter() {
        return new ProductionOrderPrinter();
    }

    @Override
    protected String emptyMessage() {
        return "No data.";
    }
}
