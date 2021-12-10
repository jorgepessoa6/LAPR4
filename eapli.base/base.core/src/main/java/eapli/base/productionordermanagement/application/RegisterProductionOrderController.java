package eapli.base.productionordermanagement.application;

import eapli.base.core.productmanager.application.ListProductService;
import eapli.base.core.productmanager.domain.Product;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.productionordermanagement.domain.Order;
import eapli.base.productionordermanagement.domain.ProductionOrder;
import eapli.base.productionordermanagement.repositories.ProductionOrderRepository;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.general.domain.model.Designation;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

import java.util.*;

public class RegisterProductionOrderController {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final ListProductService svc = new ListProductService();
    private final ProductionOrderRepository newProductionOrderRepository = PersistenceContext.repositories().productionOrders();

    public ProductionOrder registerProductionOrder(final String productionOrderId, final Calendar dataEmissao, final Calendar dataExecucao, final Product product, final List<String> orderList, final int quant, final String unit) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.GESTOR_PRODUCAO,BaseRoles.GESTOR_PROJETO);
        final ProductionOrder newProductionOrder = new ProductionOrder(Designation.valueOf(productionOrderId), dataEmissao, dataExecucao, product,quant,unit);

        for (String s :
                orderList) {
            Order o = new Order((s));
            newProductionOrder.addOrder(o);
        }
        return this.newProductionOrderRepository.save(newProductionOrder);
    }

    public Iterable<Product> products() {
        return this.svc.allProducts();
    }
}
