package eapli.base.persistence.impl.jpa;

import eapli.base.core.productmanager.domain.Product;
import eapli.base.rawmaterialmanagement.domain.RawMaterial;
import eapli.base.rawmaterialmanagement.repositories.RawMaterialRepository;
import eapli.framework.general.domain.model.Designation;

import javax.persistence.TypedQuery;

public class JpaRawMaterialRepository extends BasepaRepositoryBase<RawMaterial, Long, Designation> implements RawMaterialRepository {

    JpaRawMaterialRepository() {
        super("internalCode");
    }

    @Override
    public RawMaterial rawMaterialByDesignation(Designation designation) {
        String s = designation.toString();
        final TypedQuery<RawMaterial> query = entityManager().createQuery("SELECT m FROM RawMaterial m WHERE NAME = : s", RawMaterial.class);
        query.setParameter("s", s);
        return query.getSingleResult();
    }
}
