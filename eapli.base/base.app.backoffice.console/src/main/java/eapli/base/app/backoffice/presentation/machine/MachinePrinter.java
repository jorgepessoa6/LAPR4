package eapli.base.app.backoffice.presentation.machine;

import eapli.base.machinemanagement.domain.Machine;
import eapli.framework.visitor.Visitor;

@SuppressWarnings("squid:S106")

public class MachinePrinter implements Visitor<Machine> {

    @Override
    public void visit(final Machine visitee) {
        System.out.print(visitee.toString());
    }
}
