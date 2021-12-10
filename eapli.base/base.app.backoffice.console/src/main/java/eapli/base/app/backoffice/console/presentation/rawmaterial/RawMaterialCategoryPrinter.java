package eapli.base.app.backoffice.console.presentation.rawmaterial;

import eapli.base.rawmaterialmanagement.domain.RawMaterialCategory;
import eapli.framework.visitor.Visitor;

@SuppressWarnings("squid:S106")
class RawMaterialCategoryPrinter implements Visitor<RawMaterialCategory> {


    @Override
    public void visit(RawMaterialCategory visitee) {
        System.out.printf("%-10s%-30s", visitee.categoryCode(), visitee.categoryDescription());
    }
}
