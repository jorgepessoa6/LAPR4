/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.app.backoffice.console.presentation.products;

import eapli.base.core.productmanager.application.SpecityProductionRecordController;
import eapli.base.core.productmanager.domain.Product;
import eapli.base.rawmaterialmanagement.domain.RawMaterial;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import eapli.framework.util.Console;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Pedro Cardoso
 */
public class SpecifyProductionRecordUI extends AbstractUI {

    private final SpecityProductionRecordController controller = new SpecityProductionRecordController();


    @Override
    protected boolean doShow() {
        List<RawMaterial> mat = new ArrayList<>();
        List<Integer> quant = new ArrayList<>();
        try {
            final Product prod = selectProduct();
            final Integer units = Console.readInteger("Units?");
            controller.specifyProductionRecord(prod, units, mat,quant);
            boolean flag = true;
            while (flag) {
                System.out.println("List of Materials - Select a Material ");
                final Iterable<RawMaterial> listMat = this.controller.rawMaterials();
                final SelectWidget<RawMaterial> selectorMat = new SelectWidget<>("Select a Material", listMat);
                selectorMat.show();
                RawMaterial material = selectorMat.selectedElement();
                mat.add(material);
                final Integer quantity = Console.readInteger("Quantity?");
                quant.add(quantity);
                final String str = Console.readLine("Add another Material? [Y/N]");
                if (str.equalsIgnoreCase("N")) {
                    flag = false;
                }
            }

        } catch (@SuppressWarnings("unused") final IntegrityViolationException ex) {
            System.out.println("Error");
        }
        return false;
    }



    private Product selectProduct() {
        System.out.println("List of Products - Select a Product");
        final Iterable<Product> listProduct = this.controller.products();
        final SelectWidget<Product> selectorProduct = new SelectWidget<>("Select a Product", listProduct);
        selectorProduct.show();
        return selectorProduct.selectedElement();
    }

    @Override
    public String headline() {
        return "Specify ProductionRecord";
    }
}
