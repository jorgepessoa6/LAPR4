package eapli.base.app.backoffice.console.presentation.factoryfloor;

import eapli.base.factoryfloor.application.ExportFactoryFloorController;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static eapli.framework.util.Console.readLine;

public class ExportFactoryFloorUI extends AbstractUI {

    private final ExportFactoryFloorController controller = new ExportFactoryFloorController();

    @Override
    protected boolean doShow() {
        List<String> elements = new ArrayList<>();
        populateList(elements);
        LinkedHashMap<String, String> elementsEscolhidos = new LinkedHashMap<>();
        try {
            boolean flag = true;
            while (flag) {
                System.out.println("List of FactoryFloor Elements, choose One ");
                final SelectWidget<String> selectorMat = new SelectWidget<>("Select one", elements);
                selectorMat.show();
                String el = selectorMat.selectedElement();
                String e2 = null;
                if (el.equals("ProductionOrder")) {
                    final String str = readLine("Insert filter? [Y/N]");
                    if (str.equalsIgnoreCase("Y")) {
                        e2 = readLine("Filter dd/MM/yyyy-dd/MM/yyyy");
                    }
                }
                elementsEscolhidos.put(el, e2);
                elements.remove(el);
                final String str = readLine("Add Element? [Y/N]");
                if (str.equalsIgnoreCase("N")) {
                    flag = false;
                }
            }
            final String str = readLine("Path to xml?");
            controller.exportElements(elementsEscolhidos, str);

        } catch (Exception e) {
            System.out.println("Error");
        }
        return false;
    }

    private void populateList(List<String> lst) {
        lst.add("Product");
        lst.add("RawMaterial");
        lst.add("Deposit");
        lst.add("Machine");
        lst.add("Category");
        lst.add("ProductionLine");
        lst.add("ProductionOrder");
    }

    @Override
    public String headline() {
        return "Specify ProductionRecord";
    }
}
