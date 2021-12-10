/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.app.backoffice.console.presentation.factoryfloor;

import eapli.base.factoryfloor.application.GenerateFactoryFloorXSDController;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import eapli.framework.util.Console;
import static eapli.framework.util.Console.readLine;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 *
 * @author Bruno Pereira
 */
public class GenerateFactoryFloorXSDUI extends AbstractUI{

    
    private final GenerateFactoryFloorXSDController theController = new GenerateFactoryFloorXSDController();
    
    
    @Override
    protected boolean doShow() {
        String name = Console.readLine("Name for the file?");  
        
        this.theController.exportXSD(name);
        return false;
    }

    @Override
    public String headline() {
        return "Generate XSD";
    }
    
}
