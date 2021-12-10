/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.app.backoffice.console.presentation.deposits;

import eapli.base.depositmanagement.application.RegisterDepositController;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;

/**
 *
 * @author Bruno Pereira
 */
public class RegisterDepositUI extends AbstractUI {
    
    private final RegisterDepositController theController = new RegisterDepositController();
    
    @Override
    protected boolean doShow() {
        final String cod = Console.readLine("Deposit Code");
        final String description = Console.readLine("Description");

        try {
            this.theController.registerDeposit(cod, description);
        } catch (@SuppressWarnings("unused") final IntegrityViolationException e) {
            System.out.println("You tried to enter a product which already exists in the database.");
        }

        return false;
    }

    @Override
    public String headline() {
        return "Register Deposit";
    }
}
