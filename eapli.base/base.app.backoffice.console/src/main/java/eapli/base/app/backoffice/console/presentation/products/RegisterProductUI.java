/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.app.backoffice.console.presentation.products;

import eapli.base.core.productmanager.application.RegisterProductController;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;

/**
 *
 * @author Pedro Cardoso
 */
public class RegisterProductUI  extends AbstractUI {

    private final RegisterProductController theController = new RegisterProductController();

    @Override
    protected boolean doShow() {
        final String codFactory = Console.readLine("Factory Code");
        final String codComercial = Console.readLine("Comercial Code");
        final String descBrief = Console.readLine("Complete Description");
        final String descComplete = Console.readLine("Brief Description");
        final String unit = Console.readLine("Unit");
        final String category = Console.readLine("Category");

        try {
            this.theController.registerProduct(codFactory, codComercial, descBrief,descComplete,unit,category);
        } catch (@SuppressWarnings("unused") final IntegrityViolationException e) {
            System.out.println("You tried to enter a product which already exists in the database.");
        }

        return false;
    }

    @Override
    public String headline() {
        return "Register Product";
    }
}
