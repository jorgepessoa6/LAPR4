package eapli.base.machinemanagement.application;

import eapli.base.core.productmanager.repositories.ProductRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.machinemanagement.domain.Machine;
import eapli.base.productionlinemanagement.domain.ProductionLine;
import eapli.base.productionlinemanagement.repositories.ProductionLineRepository;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

import java.io.File;
import java.util.List;

public class ImportConfigTXTFileController {
    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final ProductionLineRepository productionLineRepository = PersistenceContext.repositories().productionLines();

    public Machine addConfigFile (Machine m, String path){
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.GESTOR_CHAO_FABRICA);
        m.addConfigurationFile(path);
        ProductionLine productionLine= m.productionLineOfMachine();
        this.productionLineRepository.save(productionLine);
        return m;
    }

    public Iterable<Machine> showAllMachinesNoConfigFile(){
        return this.productionLineRepository.allMachinesNoConfigFile();

    }
}
