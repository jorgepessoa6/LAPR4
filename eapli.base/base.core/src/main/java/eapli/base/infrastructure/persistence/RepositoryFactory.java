/**
 *
 */
package eapli.base.infrastructure.persistence;

import eapli.base.clientusermanagement.repositories.ClientUserRepository;
import eapli.base.clientusermanagement.repositories.SignupRequestRepository;
import eapli.base.core.productmanager.repositories.ProductRepository;
import eapli.base.depositmanagement.repositories.DepositRepository;
//import eapli.base.machinemanagement.repositories.MachineRepository;
import eapli.base.messagemanagement.repositories.MessageRepository;
import eapli.base.notificationmanagement.repositories.NotificationRepository;
import eapli.base.productionlinemanagement.repositories.ProductionLineRepository;
import eapli.base.productionordermanagement.repositories.ProductionOrderRepository;
import eapli.base.rawmaterialmanagement.repositories.RawMaterialCategoryRepository;
import eapli.base.rawmaterialmanagement.repositories.RawMaterialRepository;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;


/**
 * @author Paulo Gandra Sousa
 *
 */
public interface RepositoryFactory {

    /**
     * factory method to create a transactional context to use in the repositories
     *
     * @return
     */
    TransactionalContext newTransactionalContext();

    /**
     *
     * @param autoTx the transactional context to enrol
     * @return
     */
    UserRepository users(TransactionalContext autoTx);

    /**
     * repository will be created in auto transaction mode
     *
     * @return
     */
    UserRepository users();

    /**
     * repository will be created in auto transaction mode
     *
     * @return
     */
    ProductRepository products();

    /**
     *
     * @param autoTx the transactional context to enroll
     * @return
     */
    ClientUserRepository clientUsers(TransactionalContext autoTx);

    /**
     * repository will be created in auto transaction mode
     *
     * @return
     */
    ClientUserRepository clientUsers();

    /**
     *
     * @param autoTx the transactional context to enroll
     * @return
     */
    SignupRequestRepository signupRequests(TransactionalContext autoTx);

    /**
     * repository will be created in auto transaction mode
     *
     * @return
     */
    SignupRequestRepository signupRequests();


    /**
     *
     * @return
     */
    RawMaterialCategoryRepository categories();

    /**
     *
     * @return
     */
    RawMaterialRepository rawMaterials();

    /**
     * repository will be created in auto transaction mode
     *
     * @return
     */
    DepositRepository deposits();

    /**
     *
     * @return
     */
    ProductionLineRepository productionLines();
    
    /**
     *
     * @return
     */
    ProductionOrderRepository productionOrders();

    /**
     *
     * @return
     */
    MessageRepository messages();

    /**
     *
     * @return
     */
    NotificationRepository notifications();

//    /**
//     *
//     * @return
//     */
//    MachineRepository machines ();
}
