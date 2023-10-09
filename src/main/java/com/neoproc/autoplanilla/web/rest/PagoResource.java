package com.neoproc.autoplanilla.web.rest;

import com.neoproc.autoplanilla.domain.Pago;
import com.neoproc.autoplanilla.repository.PagoRepository;
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
 * REST controller for managing {@link com.neoproc.autoplanilla.domain.Pago}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PagoResource {

    private final Logger log = LoggerFactory.getLogger(PagoResource.class);

    private static final String ENTITY_NAME = "pago";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PagoRepository pagoRepository;

    public PagoResource(PagoRepository pagoRepository) {
        this.pagoRepository = pagoRepository;
    }

    /**
     * {@code POST  /pagos} : Create a new pago.
     *
     * @param pago the pago to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pago, or with status {@code 400 (Bad Request)} if the pago has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pagos")
    public ResponseEntity<Pago> createPago(@Valid @RequestBody Pago pago) throws URISyntaxException {
        log.debug("REST request to save Pago : {}", pago);
        if (pago.getId() != null) {
            throw new BadRequestAlertException("A new pago cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Pago result = pagoRepository.save(pago);
        return ResponseEntity
            .created(new URI("/api/pagos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pagos/:id} : Updates an existing pago.
     *
     * @param id the id of the pago to save.
     * @param pago the pago to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pago,
     * or with status {@code 400 (Bad Request)} if the pago is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pago couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pagos/{id}")
    public ResponseEntity<Pago> updatePago(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Pago pago)
        throws URISyntaxException {
        log.debug("REST request to update Pago : {}, {}", id, pago);
        if (pago.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pago.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pagoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Pago result = pagoRepository.save(pago);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pago.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /pagos/:id} : Partial updates given fields of an existing pago, field will ignore if it is null
     *
     * @param id the id of the pago to save.
     * @param pago the pago to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pago,
     * or with status {@code 400 (Bad Request)} if the pago is not valid,
     * or with status {@code 404 (Not Found)} if the pago is not found,
     * or with status {@code 500 (Internal Server Error)} if the pago couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/pagos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Pago> partialUpdatePago(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Pago pago
    ) throws URISyntaxException {
        log.debug("REST request to partial update Pago partially : {}, {}", id, pago);
        if (pago.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pago.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pagoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Pago> result = pagoRepository
            .findById(pago.getId())
            .map(existingPago -> {
                if (pago.getUserID() != null) {
                    existingPago.setUserID(pago.getUserID());
                }
                if (pago.getCompany() != null) {
                    existingPago.setCompany(pago.getCompany());
                }
                if (pago.getSequenceBank() != null) {
                    existingPago.setSequenceBank(pago.getSequenceBank());
                }
                if (pago.getSalarioBruto() != null) {
                    existingPago.setSalarioBruto(pago.getSalarioBruto());
                }
                if (pago.getAdicionales() != null) {
                    existingPago.setAdicionales(pago.getAdicionales());
                }
                if (pago.getDeducciones() != null) {
                    existingPago.setDeducciones(pago.getDeducciones());
                }
                if (pago.getOtrasdeducciones() != null) {
                    existingPago.setOtrasdeducciones(pago.getOtrasdeducciones());
                }
                if (pago.getSocialSecurity() != null) {
                    existingPago.setSocialSecurity(pago.getSocialSecurity());
                }
                if (pago.getRenta() != null) {
                    existingPago.setRenta(pago.getRenta());
                }
                if (pago.getPagoNeto() != null) {
                    existingPago.setPagoNeto(pago.getPagoNeto());
                }
                if (pago.getAdicionalesnodeducibles() != null) {
                    existingPago.setAdicionalesnodeducibles(pago.getAdicionalesnodeducibles());
                }
                if (pago.getType() != null) {
                    existingPago.setType(pago.getType());
                }
                if (pago.getStatus() != null) {
                    existingPago.setStatus(pago.getStatus());
                }
                if (pago.getProcessID() != null) {
                    existingPago.setProcessID(pago.getProcessID());
                }

                return existingPago;
            })
            .map(pagoRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pago.getId().toString())
        );
    }

    /**
     * {@code GET  /pagos} : get all the pagos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pagos in body.
     */
    @GetMapping("/pagos")
    public List<Pago> getAllPagos() {
        log.debug("REST request to get all Pagos");
        return pagoRepository.findAll();
    }

    /**
     * {@code GET  /pagos/:id} : get the "id" pago.
     *
     * @param id the id of the pago to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pago, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pagos/{id}")
    public ResponseEntity<Pago> getPago(@PathVariable Long id) {
        log.debug("REST request to get Pago : {}", id);
        Optional<Pago> pago = pagoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(pago);
    }

    /**
     * {@code DELETE  /pagos/:id} : delete the "id" pago.
     *
     * @param id the id of the pago to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pagos/{id}")
    public ResponseEntity<Void> deletePago(@PathVariable Long id) {
        log.debug("REST request to delete Pago : {}", id);
        pagoRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
