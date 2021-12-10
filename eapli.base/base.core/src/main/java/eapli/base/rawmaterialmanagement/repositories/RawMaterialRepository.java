package eapli.base.rawmaterialmanagement.repositories;


import eapli.base.rawmaterialmanagement.domain.RawMaterial;
import eapli.framework.domain.repositories.DomainRepository;
import eapli.framework.general.domain.model.Designation;


/**
 * @author Miguel Fortes (1171611)
 */
public interface RawMaterialRepository extends DomainRepository<Designation, RawMaterial> {
    //empty

    RawMaterial rawMaterialByDesignation(Designation designation);
}
