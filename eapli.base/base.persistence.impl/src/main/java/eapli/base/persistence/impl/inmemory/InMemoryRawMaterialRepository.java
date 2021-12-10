package eapli.base.persistence.impl.inmemory;

import eapli.base.rawmaterialmanagement.domain.RawMaterial;
import eapli.base.rawmaterialmanagement.repositories.RawMaterialRepository;
import eapli.framework.general.domain.model.Designation;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

public class InMemoryRawMaterialRepository extends InMemoryDomainRepository<RawMaterial, Designation> implements RawMaterialRepository {

    static {
        InMemoryInitializer.init();
    }

    @Override
    public RawMaterial rawMaterialByDesignation(Designation designation) {
        return null;
    }
}


