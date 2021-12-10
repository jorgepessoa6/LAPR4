package eapli.base.machinemanagement.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.machinemanagement.domain.Machine;
import eapli.base.productionlinemanagement.domain.ProductionLine;
import eapli.base.productionlinemanagement.repositories.ProductionLineRepository;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.general.domain.model.Designation;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

import java.util.Calendar;


/**
 * @author Rui Mendes (1170385)
 */

public class RegisterMachineController {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final ProductionLineRepository productionLineRepository = PersistenceContext.repositories().productionLines();


    public ProductionLine registerMachine(final String internalCode, final String seriesNumber,
                                   final String description, final String brand, String model, String networkAddress, ProductionLine productionLine) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.GESTOR_CHAO_FABRICA, BaseRoles.GESTOR_PROJETO);

        final Machine newMachine = new Machine(Designation.valueOf(internalCode), seriesNumber, description, Calendar.getInstance(), brand, model, networkAddress, productionLine);
        productionLine.addMachine(newMachine);

        final TransactionalContext ctx = PersistenceContext.repositories().newTransactionalContext();
        ctx.beginTransaction();

        this.productionLineRepository.save(productionLine);
        ctx.commit();
        return productionLine;
    }
}
