package eapli.base.app.backoffice.console.presentation.factoryfloor;

import eapli.base.factoryfloor.application.ExportXMLController;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;

import java.util.ArrayList;
import java.util.List;

public class ExportXMLUI extends AbstractUI {

    private final ExportXMLController theController = new ExportXMLController();
    private static final String OPTION_1 = "1- .HTML";
    private static final String OPTION_2 = "2- .JSON";
    private static final String OPTION_3 = "3- .TEXT";


    @Override
    protected boolean doShow() {
        final SelectWidget<String> selectorOpt = new SelectWidget<>("Select an Option", fillOptions());
        selectorOpt.show();
        String opt = selectorOpt.selectedElement();
        String pathxml = Console.readLine("Path for the xml file?");
        String pathxsl = Console.readLine("Path for the xsl file?");
        switch(opt){
            case OPTION_1:
                this.theController.export(pathxml,pathxsl,".html");
                break;
            case OPTION_2:
                this.theController.export(pathxml,pathxsl,".json");
                break;
            case OPTION_3:
                this.theController.export(pathxml,pathxml,".txt");
        }

        return false;
    }
    protected List<String> fillOptions() {
        List<String> options = new ArrayList<>();
        options.add(OPTION_1);
        options.add(OPTION_2);
        options.add(OPTION_3);
        return options;
    }
    @Override
    public String headline() {
        return "Generate file";
    }
}
