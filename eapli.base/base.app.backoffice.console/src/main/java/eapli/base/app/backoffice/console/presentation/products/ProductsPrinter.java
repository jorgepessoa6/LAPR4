/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.app.backoffice.console.presentation.products;

import eapli.base.core.productmanager.domain.Product;
import eapli.framework.visitor.Visitor;

/**
 *
 * @author Pedro Cardoso
 */
public class ProductsPrinter implements Visitor<Product> {
     @Override
    public void visit(final Product visitee) {
        System.out.printf("%-10s%-30s", visitee.identity(), visitee.briefDescription());
    }
}
