package eapli.base.app.backoffice.console.presentation.productionlines;
import eapli.base.productionlinemanagement.domain.ProductionLine;
import eapli.framework.visitor.Visitor;

/**
 * @author Rui Mendes (1170385)
 */

@SuppressWarnings("squid:S106")

    public class ProductionLinePrinter implements Visitor<ProductionLine> {
        @Override
        public void visit(final ProductionLine visitee) {
            System.out.printf("%-30s", visitee.identity());
        }
}
