package eapli.base.app.backoffice.presentation.machine;

import eapli.base.machinemanagement.application.ImportConfigTXTFileController;
import eapli.base.machinemanagement.domain.Machine;
import eapli.base.rawmaterialmanagement.domain.RawMaterial;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import eapli.framework.util.Console;

public class ImportConfigTXTFileUI extends AbstractUI {

    private final ImportConfigTXTFileController controller = new ImportConfigTXTFileController();

    @Override
    protected boolean doShow() {
        try {
            final String name = Console.readLine("Path?");
            final Iterable<Machine> listMach = this.controller.showAllMachinesNoConfigFile();
            final SelectWidget<Machine> selectorMach = new SelectWidget<>("Select a Machine", listMach);
            selectorMach.show();
            Machine machine = selectorMach.selectedElement();
            this.controller.addConfigFile(machine, name);
            System.out.println("Sucess!!");
        } catch (Exception e) {
            System.out.println("Error importing files");
        }
        return false;
    }

    @Override
    public String headline() {
        return "Importing TXT files to Machines";
    }
}
