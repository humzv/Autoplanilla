package com.neoproc.autoplanilla.repository;

import com.neoproc.autoplanilla.domain.Planilla;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface PlanillaRepositoryWithBagRelationships {
    Optional<Planilla> fetchBagRelationships(Optional<Planilla> planilla);

    List<Planilla> fetchBagRelationships(List<Planilla> planillas);

    Page<Planilla> fetchBagRelationships(Page<Planilla> planillas);
}
