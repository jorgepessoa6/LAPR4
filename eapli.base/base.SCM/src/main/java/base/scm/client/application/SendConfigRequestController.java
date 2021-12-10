package base.scm.client.application;


import eapli.base.machinemanagement.domain.ListMachineWithIPAddressService;
import eapli.base.machinemanagement.domain.Machine;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

import java.io.File;

public class SendConfigRequestController {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final ListMachineWithIPAddressService listMachineWithIPAddressService = new ListMachineWithIPAddressService();
    private final SendConfigRequestService scs = new SendConfigRequestService();

    public void sendConfigRequest(Machine machine) throws Exception {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.GESTOR_CHAO_FABRICA);
        scs.SendTcpCliTo(machine);
    }

    public Iterable<Machine> machinesWithIPAddress() {
        return this.listMachineWithIPAddressService.machinesWithIPAddress();
    }
}
