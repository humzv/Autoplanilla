package com.neoproc.autoplanilla.repository;

import com.neoproc.autoplanilla.domain.Planilla;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class PlanillaRepositoryWithBagRelationshipsImpl implements PlanillaRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Planilla> fetchBagRelationships(Optional<Planilla> planilla) {
        return planilla.map(this::fetchUsers);
    }

    @Override
    public Page<Planilla> fetchBagRelationships(Page<Planilla> planillas) {
        return new PageImpl<>(fetchBagRelationships(planillas.getContent()), planillas.getPageable(), planillas.getTotalElements());
    }

    @Override
    public List<Planilla> fetchBagRelationships(List<Planilla> planillas) {
        return Optional.of(planillas).map(this::fetchUsers).orElse(Collections.emptyList());
    }

    Planilla fetchUsers(Planilla result) {
        return entityManager
            .createQuery(
                "select planilla from Planilla planilla left join fetch planilla.users where planilla is :planilla",
                Planilla.class
            )
            .setParameter("planilla", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Planilla> fetchUsers(List<Planilla> planillas) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, planillas.size()).forEach(index -> order.put(planillas.get(index).getId(), index));
        List<Planilla> result = entityManager
            .createQuery(
                "select distinct planilla from Planilla planilla left join fetch planilla.users where planilla in :planillas",
                Planilla.class
            )
            .setParameter("planillas", planillas)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
