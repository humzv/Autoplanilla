package com.neoproc.autoplanilla.web.rest;

import com.neoproc.autoplanilla.domain.Planilla;
import com.neoproc.autoplanilla.repository.PlanillaRepository;
import com.neoproc.autoplanilla.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.neoproc.autoplanilla.domain.Planilla}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PlanillaResource {

    private final Logger log = LoggerFactory.getLogger(PlanillaResource.class);

    private static final String ENTITY_NAME = "planilla";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlanillaRepository planillaRepository;

    public PlanillaResource(PlanillaRepository planillaRepository) {
        this.planillaRepository = planillaRepository;
    }

    /**
     * {@code POST  /planillas} : Create a new planilla.
     *
     * @param planilla the planilla to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new planilla, or with status {@code 400 (Bad Request)} if the planilla has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/planillas")
    public ResponseEntity<Planilla> createPlanilla(@Valid @RequestBody Planilla planilla) throws URISyntaxException {
        log.debug("REST request to save Planilla : {}", planilla);
        if (planilla.getId() != null) {
            throw new BadRequestAlertException("A new planilla cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Planilla result = planillaRepository.save(planilla);
        return ResponseEntity
            .created(new URI("/api/planillas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /planillas/:id} : Updates an existing planilla.
     *
     * @param id the id of the planilla to save.
     * @param planilla the planilla to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated planilla,
     * or with status {@code 400 (Bad Request)} if the planilla is not valid,
     * or with status {@code 500 (Internal Server Error)} if the planilla couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/planillas/{id}")
    public ResponseEntity<Planilla> updatePlanilla(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Planilla planilla
    ) throws URISyntaxException {
        log.debug("REST request to update Planilla : {}, {}", id, planilla);
        if (planilla.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, planilla.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!planillaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Planilla result = planillaRepository.save(planilla);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, planilla.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /planillas/:id} : Partial updates given fields of an existing planilla, field will ignore if it is null
     *
     * @param id the id of the planilla to save.
     * @param planilla the planilla to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated planilla,
     * or with status {@code 400 (Bad Request)} if the planilla is not valid,
     * or with status {@code 404 (Not Found)} if the planilla is not found,
     * or with status {@code 500 (Internal Server Error)} if the planilla couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/planillas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Planilla> partialUpdatePlanilla(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Planilla planilla
    ) throws URISyntaxException {
        log.debug("REST request to partial update Planilla partially : {}, {}", id, planilla);
        if (planilla.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, planilla.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!planillaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Planilla> result = planillaRepository
            .findById(planilla.getId())
            .map(existingPlanilla -> {
                if (planilla.getTipo() != null) {
                    existingPlanilla.setTipo(planilla.getTipo());
                }
                if (planilla.getConsecutivoBAC() != null) {
                    existingPlanilla.setConsecutivoBAC(planilla.getConsecutivoBAC());
                }
                if (planilla.getPlanBAC() != null) {
                    existingPlanilla.setPlanBAC(planilla.getPlanBAC());
                }
                if (planilla.getNombre() != null) {
                    existingPlanilla.setNombre(planilla.getNombre());
                }
                if (planilla.getNotas() != null) {
                    existingPlanilla.setNotas(planilla.getNotas());
                }
                if (planilla.getAprobador() != null) {
                    existingPlanilla.setAprobador(planilla.getAprobador());
                }
                if (planilla.getNotificantes() != null) {
                    existingPlanilla.setNotificantes(planilla.getNotificantes());
                }
                if (planilla.getMoneda() != null) {
                    existingPlanilla.setMoneda(planilla.getMoneda());
                }
                if (planilla.getVacasMulti() != null) {
                    existingPlanilla.setVacasMulti(planilla.getVacasMulti());
                }
                if (planilla.getGrupoAprobador() != null) {
                    existingPlanilla.setGrupoAprobador(planilla.getGrupoAprobador());
                }
                if (planilla.getPagador() != null) {
                    existingPlanilla.setPagador(planilla.getPagador());
                }
                if (planilla.getGrupoContabilidad() != null) {
                    existingPlanilla.setGrupoContabilidad(planilla.getGrupoContabilidad());
                }
                if (planilla.getEstado() != null) {
                    existingPlanilla.setEstado(planilla.getEstado());
                }

                return existingPlanilla;
            })
            .map(planillaRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, planilla.getId().toString())
        );
    }

    /**
     * {@code GET  /planillas} : get all the planillas.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of planillas in body.
     */
    @GetMapping("/planillas")
    public List<Planilla> getAllPlanillas(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Planillas");
        if (eagerload) {
            return planillaRepository.findAllWithEagerRelationships();
        } else {
            return planillaRepository.findAll();
        }
    }

    /**
     * {@code GET  /planillas/:id} : get the "id" planilla.
     *
     * @param id the id of the planilla to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the planilla, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/planillas/{id}")
    public ResponseEntity<Planilla> getPlanilla(@PathVariable Long id) {
        log.debug("REST request to get Planilla : {}", id);
        Optional<Planilla> planilla = planillaRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(planilla);
    }

    /**
     * {@code DELETE  /planillas/:id} : delete the "id" planilla.
     *
     * @param id the id of the planilla to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/planillas/{id}")
    public ResponseEntity<Void> deletePlanilla(@PathVariable Long id) {
        log.debug("REST request to delete Planilla : {}", id);
        planillaRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
