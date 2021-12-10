package eapli.base.persistence.impl.jpa;

import eapli.base.rawmaterialmanagement.domain.RawMaterialCategory;
import eapli.base.rawmaterialmanagement.repositories.RawMaterialCategoryRepository;
import eapli.framework.general.domain.model.Designation;

public class JpaRawMaterialCategoryRepository extends BasepaRepositoryBase<RawMaterialCategory, Long, Designation> implements RawMaterialCategoryRepository {

    JpaRawMaterialCategoryRepository() {
        super("code");
    }
}
