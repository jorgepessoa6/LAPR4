/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.core.productmanager.application;

import eapli.base.core.productmanager.domain.Product;
import eapli.base.core.productmanager.domain.ProductionRecord;
import eapli.base.core.productmanager.repositories.ProductRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.rawmaterialmanagement.application.ListRawMaterialService;
import eapli.base.rawmaterialmanagement.domain.RawMaterial;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.application.UseCaseController;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

import java.util.List;

/**
 *
 * @author Pedro Cardoso
 */
@UseCaseController
public class SpecityProductionRecordController {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final ProductRepository productRepository = PersistenceContext.repositories().products();
    private final ListRawMaterialService listMaterialsService = new ListRawMaterialService();
    private final ListProductsNoProductionRecordService listProductsService = new ListProductsNoProductionRecordService();

    public Product specifyProductionRecord(Product prod, int unit,List<RawMaterial> mat, List<Integer> quant ) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.GESTOR_PRODUCAO);
        final ProductionRecord pRecord = new ProductionRecord(mat,quant,unit,prod);
        prod.addProductionRecord(pRecord);
        this.productRepository.save(prod);
        return prod;
    }

    public Iterable<RawMaterial> rawMaterials() {
        return this.listMaterialsService.allRawMaterialCategories();
    }
    public Iterable<Product> products() {
        return this.listProductsService.productsNoProductionRecord();
    }
}
