package com.neoproc.autoplanilla.repository;

import com.neoproc.autoplanilla.domain.Planilla;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Planilla entity.
 *
 * When extending this class, extend PlanillaRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface PlanillaRepository extends PlanillaRepositoryWithBagRelationships, JpaRepository<Planilla, Long> {
    default Optional<Planilla> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findOneWithToOneRelationships(id));
    }

    default List<Planilla> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships());
    }

    default Page<Planilla> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships(pageable));
    }

    @Query(
        value = "select distinct planilla from Planilla planilla left join fetch planilla.company",
        countQuery = "select count(distinct planilla) from Planilla planilla"
    )
    Page<Planilla> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct planilla from Planilla planilla left join fetch planilla.company")
    List<Planilla> findAllWithToOneRelationships();

    @Query("select planilla from Planilla planilla left join fetch planilla.company where planilla.id =:id")
    Optional<Planilla> findOneWithToOneRelationships(@Param("id") Long id);
}
