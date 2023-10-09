package com.neoproc.autoplanilla.web.rest;

import com.neoproc.autoplanilla.domain.DetallePago;
import com.neoproc.autoplanilla.repository.DetallePagoRepository;
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
 * REST controller for managing {@link com.neoproc.autoplanilla.domain.DetallePago}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DetallePagoResource {

    private final Logger log = LoggerFactory.getLogger(DetallePagoResource.class);

    private static final String ENTITY_NAME = "detallePago";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DetallePagoRepository detallePagoRepository;

    public DetallePagoResource(DetallePagoRepository detallePagoRepository) {
        this.detallePagoRepository = detallePagoRepository;
    }

    /**
     * {@code POST  /detalle-pagos} : Create a new detallePago.
     *
     * @param detallePago the detallePago to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new detallePago, or with status {@code 400 (Bad Request)} if the detallePago has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/detalle-pagos")
    public ResponseEntity<DetallePago> createDetallePago(@Valid @RequestBody DetallePago detallePago) throws URISyntaxException {
        log.debug("REST request to save DetallePago : {}", detallePago);
        if (detallePago.getId() != null) {
            throw new BadRequestAlertException("A new detallePago cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DetallePago result = detallePagoRepository.save(detallePago);
        return ResponseEntity
            .created(new URI("/api/detalle-pagos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /detalle-pagos/:id} : Updates an existing detallePago.
     *
     * @param id the id of the detallePago to save.
     * @param detallePago the detallePago to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated detallePago,
     * or with status {@code 400 (Bad Request)} if the detallePago is not valid,
     * or with status {@code 500 (Internal Server Error)} if the detallePago couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/detalle-pagos/{id}")
    public ResponseEntity<DetallePago> updateDetallePago(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DetallePago detallePago
    ) throws URISyntaxException {
        log.debug("REST request to update DetallePago : {}, {}", id, detallePago);
        if (detallePago.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, detallePago.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!detallePagoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DetallePago result = detallePagoRepository.save(detallePago);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, detallePago.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /detalle-pagos/:id} : Partial updates given fields of an existing detallePago, field will ignore if it is null
     *
     * @param id the id of the detallePago to save.
     * @param detallePago the detallePago to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated detallePago,
     * or with status {@code 400 (Bad Request)} if the detallePago is not valid,
     * or with status {@code 404 (Not Found)} if the detallePago is not found,
     * or with status {@code 500 (Internal Server Error)} if the detallePago couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/detalle-pagos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DetallePago> partialUpdateDetallePago(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DetallePago detallePago
    ) throws URISyntaxException {
        log.debug("REST request to partial update DetallePago partially : {}, {}", id, detallePago);
        if (detallePago.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, detallePago.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!detallePagoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DetallePago> result = detallePagoRepository
            .findById(detallePago.getId())
            .map(existingDetallePago -> {
                if (detallePago.getUserID() != null) {
                    existingDetallePago.setUserID(detallePago.getUserID());
                }
                if (detallePago.getCompany() != null) {
                    existingDetallePago.setCompany(detallePago.getCompany());
                }
                if (detallePago.getSequenceBank() != null) {
                    existingDetallePago.setSequenceBank(detallePago.getSequenceBank());
                }
                if (detallePago.getSalarioBruto() != null) {
                    existingDetallePago.setSalarioBruto(detallePago.getSalarioBruto());
                }
                if (detallePago.getAdicionales() != null) {
                    existingDetallePago.setAdicionales(detallePago.getAdicionales());
                }
                if (detallePago.getDeducciones() != null) {
                    existingDetallePago.setDeducciones(detallePago.getDeducciones());
                }
                if (detallePago.getOtrasdeducciones() != null) {
                    existingDetallePago.setOtrasdeducciones(detallePago.getOtrasdeducciones());
                }
                if (detallePago.getSocialSecurity() != null) {
                    existingDetallePago.setSocialSecurity(detallePago.getSocialSecurity());
                }
                if (detallePago.getRenta() != null) {
                    existingDetallePago.setRenta(detallePago.getRenta());
                }
                if (detallePago.getPagoNeto() != null) {
                    existingDetallePago.setPagoNeto(detallePago.getPagoNeto());
                }
                if (detallePago.getAdicionalesnodeducibles() != null) {
                    existingDetallePago.setAdicionalesnodeducibles(detallePago.getAdicionalesnodeducibles());
                }
                if (detallePago.getType() != null) {
                    existingDetallePago.setType(detallePago.getType());
                }
                if (detallePago.getStatus() != null) {
                    existingDetallePago.setStatus(detallePago.getStatus());
                }
                if (detallePago.getProcessID() != null) {
                    existingDetallePago.setProcessID(detallePago.getProcessID());
                }

                return existingDetallePago;
            })
            .map(detallePagoRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, detallePago.getId().toString())
        );
    }

    /**
     * {@code GET  /detalle-pagos} : get all the detallePagos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of detallePagos in body.
     */
    @GetMapping("/detalle-pagos")
    public List<DetallePago> getAllDetallePagos() {
        log.debug("REST request to get all DetallePagos");
        return detallePagoRepository.findAll();
    }

    /**
     * {@code GET  /detalle-pagos/:id} : get the "id" detallePago.
     *
     * @param id the id of the detallePago to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the detallePago, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/detalle-pagos/{id}")
    public ResponseEntity<DetallePago> getDetallePago(@PathVariable Long id) {
        log.debug("REST request to get DetallePago : {}", id);
        Optional<DetallePago> detallePago = detallePagoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(detallePago);
    }

    /**
     * {@code DELETE  /detalle-pagos/:id} : delete the "id" detallePago.
     *
     * @param id the id of the detallePago to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/detalle-pagos/{id}")
    public ResponseEntity<Void> deleteDetallePago(@PathVariable Long id) {
        log.debug("REST request to delete DetallePago : {}", id);
        detallePagoRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
