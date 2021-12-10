package eapli.base.app.backoffice.presentation.machine;


import base.scm.client.application.SendConfigRequestController;
import eapli.base.machinemanagement.application.ImportConfigTXTFileController;
import eapli.base.machinemanagement.domain.Machine;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import eapli.framework.util.Console;

public class SendConfigRequestUI extends AbstractUI {

    private final SendConfigRequestController theController = new SendConfigRequestController();
    private final ImportConfigTXTFileController otherController = new ImportConfigTXTFileController();

    @Override
    protected boolean doShow() {

        final Iterable<Machine> machinesWithIPAddress = this.theController.machinesWithIPAddress();

        String path = null;

        final SelectWidget<Machine> selector = new SelectWidget<>("Machines with IP Addresses Defined:", machinesWithIPAddress, new MachinePrinter());
        selector.show();

        final Machine machine = selector.selectedElement();
        
        if(machine.getConfigFile() == null) {
            System.out.println("Do you wish to associate a configuration File to the machine?");
            String awnser = Console.readLine("Y/N?");
            if (awnser.equalsIgnoreCase("Y")) {
                path = Console.readLine("Path: ");
                otherController.addConfigFile(machine, path);
            }
        }

        try {
            this.theController.sendConfigRequest(machine);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public String headline() {
        return "Select Machine to send Configuration:";
    }
}

