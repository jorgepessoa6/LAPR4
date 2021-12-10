/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.app.backoffice.console.presentation.products;

import eapli.base.core.productmanager.application.ListProductsNoProductionRecordController;
import eapli.base.core.productmanager.domain.Product;
import eapli.framework.presentation.console.AbstractListUI;
import eapli.framework.visitor.Visitor;

/**
 * This classes lists dish types by using the AbstractListUI from the framework.
 *
 * @author MCN on 29/03/2016.
 */
public class ListProductNoProductionRecordUI extends AbstractListUI<Product> {

    private final ListProductsNoProductionRecordController theController = new ListProductsNoProductionRecordController();

    @Override
    protected Iterable<Product> elements() {
        return this.theController.listarProdutosSemFichaProducao();
    }

   
    @Override
    protected String elementName() {
        return "Produto";
    }

    @Override
    protected String listHeader() {
        return "Produtos";
    }

    @Override
    public String headline() {
        return "Produtos sem ficha de producao definida";
    }

    @Override
    protected Visitor<Product> elementPrinter() {
       return new ProductsPrinter();
    }

    @Override
    protected String emptyMessage() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
