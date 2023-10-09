package com.neoproc.autoplanilla.repository;

import com.neoproc.autoplanilla.domain.Company;
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
public class CompanyRepositoryWithBagRelationshipsImpl implements CompanyRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Company> fetchBagRelationships(Optional<Company> company) {
        return company.map(this::fetchUsers);
    }

    @Override
    public Page<Company> fetchBagRelationships(Page<Company> companies) {
        return new PageImpl<>(fetchBagRelationships(companies.getContent()), companies.getPageable(), companies.getTotalElements());
    }

    @Override
    public List<Company> fetchBagRelationships(List<Company> companies) {
        return Optional.of(companies).map(this::fetchUsers).orElse(Collections.emptyList());
    }

    Company fetchUsers(Company result) {
        return entityManager
            .createQuery("select company from Company company left join fetch company.users where company is :company", Company.class)
            .setParameter("company", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Company> fetchUsers(List<Company> companies) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, companies.size()).forEach(index -> order.put(companies.get(index).getId(), index));
        List<Company> result = entityManager
            .createQuery(
                "select distinct company from Company company left join fetch company.users where company in :companies",
                Company.class
            )
            .setParameter("companies", companies)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
