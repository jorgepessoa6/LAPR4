package eapli.base.persistence.impl.inmemory;

import eapli.base.rawmaterialmanagement.domain.RawMaterialCategory;
import eapli.base.rawmaterialmanagement.repositories.RawMaterialCategoryRepository;
import eapli.framework.general.domain.model.Designation;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

public class InMemoryRawMaterialCategoryRepository extends InMemoryDomainRepository<RawMaterialCategory, Designation> implements RawMaterialCategoryRepository {

    static {InMemoryInitializer.init();
    }



}
