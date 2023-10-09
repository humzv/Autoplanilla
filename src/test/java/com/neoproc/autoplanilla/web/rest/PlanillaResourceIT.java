package com.neoproc.autoplanilla.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.neoproc.autoplanilla.IntegrationTest;
import com.neoproc.autoplanilla.domain.Planilla;
import com.neoproc.autoplanilla.domain.enumeration.Currency;
import com.neoproc.autoplanilla.domain.enumeration.Estado;
import com.neoproc.autoplanilla.repository.PlanillaRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PlanillaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PlanillaResourceIT {

    private static final String DEFAULT_TIPO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO = "BBBBBBBBBB";

    private static final Integer DEFAULT_CONSECUTIVO_BAC = 1;
    private static final Integer UPDATED_CONSECUTIVO_BAC = 2;

    private static final String DEFAULT_PLAN_BAC = "AAAAAAAAAA";
    private static final String UPDATED_PLAN_BAC = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_NOTAS = "AAAAAAAAAA";
    private static final String UPDATED_NOTAS = "BBBBBBBBBB";

    private static final String DEFAULT_APROBADOR = "AAAAAAAAAA";
    private static final String UPDATED_APROBADOR = "BBBBBBBBBB";

    private static final String DEFAULT_NOTIFICANTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTIFICANTES = "BBBBBBBBBB";

    private static final Currency DEFAULT_MONEDA = Currency.CRC;
    private static final Currency UPDATED_MONEDA = Currency.USD;

    private static final Float DEFAULT_VACAS_MULTI = 1F;
    private static final Float UPDATED_VACAS_MULTI = 2F;

    private static final String DEFAULT_GRUPO_APROBADOR = "AAAAAAAAAA";
    private static final String UPDATED_GRUPO_APROBADOR = "BBBBBBBBBB";

    private static final String DEFAULT_PAGADOR = "AAAAAAAAAA";
    private static final String UPDATED_PAGADOR = "BBBBBBBBBB";

    private static final String DEFAULT_GRUPO_CONTABILIDAD = "AAAAAAAAAA";
    private static final String UPDATED_GRUPO_CONTABILIDAD = "BBBBBBBBBB";

    private static final Estado DEFAULT_ESTADO = Estado.ACTIVO;
    private static final Estado UPDATED_ESTADO = Estado.INACTIVO;

    private static final String ENTITY_API_URL = "/api/planillas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PlanillaRepository planillaRepository;

    @Mock
    private PlanillaRepository planillaRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlanillaMockMvc;

    private Planilla planilla;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Planilla createEntity(EntityManager em) {
        Planilla planilla = new Planilla()
            .tipo(DEFAULT_TIPO)
            .consecutivoBAC(DEFAULT_CONSECUTIVO_BAC)
            .planBAC(DEFAULT_PLAN_BAC)
            .nombre(DEFAULT_NOMBRE)
            .notas(DEFAULT_NOTAS)
            .aprobador(DEFAULT_APROBADOR)
            .notificantes(DEFAULT_NOTIFICANTES)
            .moneda(DEFAULT_MONEDA)
            .vacasMulti(DEFAULT_VACAS_MULTI)
            .grupoAprobador(DEFAULT_GRUPO_APROBADOR)
            .pagador(DEFAULT_PAGADOR)
            .grupoContabilidad(DEFAULT_GRUPO_CONTABILIDAD)
            .estado(DEFAULT_ESTADO);
        return planilla;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Planilla createUpdatedEntity(EntityManager em) {
        Planilla planilla = new Planilla()
            .tipo(UPDATED_TIPO)
            .consecutivoBAC(UPDATED_CONSECUTIVO_BAC)
            .planBAC(UPDATED_PLAN_BAC)
            .nombre(UPDATED_NOMBRE)
            .notas(UPDATED_NOTAS)
            .aprobador(UPDATED_APROBADOR)
            .notificantes(UPDATED_NOTIFICANTES)
            .moneda(UPDATED_MONEDA)
            .vacasMulti(UPDATED_VACAS_MULTI)
            .grupoAprobador(UPDATED_GRUPO_APROBADOR)
            .pagador(UPDATED_PAGADOR)
            .grupoContabilidad(UPDATED_GRUPO_CONTABILIDAD)
            .estado(UPDATED_ESTADO);
        return planilla;
    }

    @BeforeEach
    public void initTest() {
        planilla = createEntity(em);
    }

    @Test
    @Transactional
    void createPlanilla() throws Exception {
        int databaseSizeBeforeCreate = planillaRepository.findAll().size();
        // Create the Planilla
        restPlanillaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planilla)))
            .andExpect(status().isCreated());

        // Validate the Planilla in the database
        List<Planilla> planillaList = planillaRepository.findAll();
        assertThat(planillaList).hasSize(databaseSizeBeforeCreate + 1);
        Planilla testPlanilla = planillaList.get(planillaList.size() - 1);
        assertThat(testPlanilla.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testPlanilla.getConsecutivoBAC()).isEqualTo(DEFAULT_CONSECUTIVO_BAC);
        assertThat(testPlanilla.getPlanBAC()).isEqualTo(DEFAULT_PLAN_BAC);
        assertThat(testPlanilla.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testPlanilla.getNotas()).isEqualTo(DEFAULT_NOTAS);
        assertThat(testPlanilla.getAprobador()).isEqualTo(DEFAULT_APROBADOR);
        assertThat(testPlanilla.getNotificantes()).isEqualTo(DEFAULT_NOTIFICANTES);
        assertThat(testPlanilla.getMoneda()).isEqualTo(DEFAULT_MONEDA);
        assertThat(testPlanilla.getVacasMulti()).isEqualTo(DEFAULT_VACAS_MULTI);
        assertThat(testPlanilla.getGrupoAprobador()).isEqualTo(DEFAULT_GRUPO_APROBADOR);
        assertThat(testPlanilla.getPagador()).isEqualTo(DEFAULT_PAGADOR);
        assertThat(testPlanilla.getGrupoContabilidad()).isEqualTo(DEFAULT_GRUPO_CONTABILIDAD);
        assertThat(testPlanilla.getEstado()).isEqualTo(DEFAULT_ESTADO);
    }

    @Test
    @Transactional
    void createPlanillaWithExistingId() throws Exception {
        // Create the Planilla with an existing ID
        planilla.setId(1L);

        int databaseSizeBeforeCreate = planillaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlanillaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planilla)))
            .andExpect(status().isBadRequest());

        // Validate the Planilla in the database
        List<Planilla> planillaList = planillaRepository.findAll();
        assertThat(planillaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkEstadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = planillaRepository.findAll().size();
        // set the field null
        planilla.setEstado(null);

        // Create the Planilla, which fails.

        restPlanillaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planilla)))
            .andExpect(status().isBadRequest());

        List<Planilla> planillaList = planillaRepository.findAll();
        assertThat(planillaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPlanillas() throws Exception {
        // Initialize the database
        planillaRepository.saveAndFlush(planilla);

        // Get all the planillaList
        restPlanillaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planilla.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)))
            .andExpect(jsonPath("$.[*].consecutivoBAC").value(hasItem(DEFAULT_CONSECUTIVO_BAC)))
            .andExpect(jsonPath("$.[*].planBAC").value(hasItem(DEFAULT_PLAN_BAC)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].notas").value(hasItem(DEFAULT_NOTAS)))
            .andExpect(jsonPath("$.[*].aprobador").value(hasItem(DEFAULT_APROBADOR)))
            .andExpect(jsonPath("$.[*].notificantes").value(hasItem(DEFAULT_NOTIFICANTES)))
            .andExpect(jsonPath("$.[*].moneda").value(hasItem(DEFAULT_MONEDA.toString())))
            .andExpect(jsonPath("$.[*].vacasMulti").value(hasItem(DEFAULT_VACAS_MULTI.doubleValue())))
            .andExpect(jsonPath("$.[*].grupoAprobador").value(hasItem(DEFAULT_GRUPO_APROBADOR)))
            .andExpect(jsonPath("$.[*].pagador").value(hasItem(DEFAULT_PAGADOR)))
            .andExpect(jsonPath("$.[*].grupoContabilidad").value(hasItem(DEFAULT_GRUPO_CONTABILIDAD)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPlanillasWithEagerRelationshipsIsEnabled() throws Exception {
        when(planillaRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPlanillaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(planillaRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPlanillasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(planillaRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPlanillaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(planillaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getPlanilla() throws Exception {
        // Initialize the database
        planillaRepository.saveAndFlush(planilla);

        // Get the planilla
        restPlanillaMockMvc
            .perform(get(ENTITY_API_URL_ID, planilla.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(planilla.getId().intValue()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO))
            .andExpect(jsonPath("$.consecutivoBAC").value(DEFAULT_CONSECUTIVO_BAC))
            .andExpect(jsonPath("$.planBAC").value(DEFAULT_PLAN_BAC))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.notas").value(DEFAULT_NOTAS))
            .andExpect(jsonPath("$.aprobador").value(DEFAULT_APROBADOR))
            .andExpect(jsonPath("$.notificantes").value(DEFAULT_NOTIFICANTES))
            .andExpect(jsonPath("$.moneda").value(DEFAULT_MONEDA.toString()))
            .andExpect(jsonPath("$.vacasMulti").value(DEFAULT_VACAS_MULTI.doubleValue()))
            .andExpect(jsonPath("$.grupoAprobador").value(DEFAULT_GRUPO_APROBADOR))
            .andExpect(jsonPath("$.pagador").value(DEFAULT_PAGADOR))
            .andExpect(jsonPath("$.grupoContabilidad").value(DEFAULT_GRUPO_CONTABILIDAD))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPlanilla() throws Exception {
        // Get the planilla
        restPlanillaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPlanilla() throws Exception {
        // Initialize the database
        planillaRepository.saveAndFlush(planilla);

        int databaseSizeBeforeUpdate = planillaRepository.findAll().size();

        // Update the planilla
        Planilla updatedPlanilla = planillaRepository.findById(planilla.getId()).get();
        // Disconnect from session so that the updates on updatedPlanilla are not directly saved in db
        em.detach(updatedPlanilla);
        updatedPlanilla
            .tipo(UPDATED_TIPO)
            .consecutivoBAC(UPDATED_CONSECUTIVO_BAC)
            .planBAC(UPDATED_PLAN_BAC)
            .nombre(UPDATED_NOMBRE)
            .notas(UPDATED_NOTAS)
            .aprobador(UPDATED_APROBADOR)
            .notificantes(UPDATED_NOTIFICANTES)
            .moneda(UPDATED_MONEDA)
            .vacasMulti(UPDATED_VACAS_MULTI)
            .grupoAprobador(UPDATED_GRUPO_APROBADOR)
            .pagador(UPDATED_PAGADOR)
            .grupoContabilidad(UPDATED_GRUPO_CONTABILIDAD)
            .estado(UPDATED_ESTADO);

        restPlanillaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPlanilla.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPlanilla))
            )
            .andExpect(status().isOk());

        // Validate the Planilla in the database
        List<Planilla> planillaList = planillaRepository.findAll();
        assertThat(planillaList).hasSize(databaseSizeBeforeUpdate);
        Planilla testPlanilla = planillaList.get(planillaList.size() - 1);
        assertThat(testPlanilla.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testPlanilla.getConsecutivoBAC()).isEqualTo(UPDATED_CONSECUTIVO_BAC);
        assertThat(testPlanilla.getPlanBAC()).isEqualTo(UPDATED_PLAN_BAC);
        assertThat(testPlanilla.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testPlanilla.getNotas()).isEqualTo(UPDATED_NOTAS);
        assertThat(testPlanilla.getAprobador()).isEqualTo(UPDATED_APROBADOR);
        assertThat(testPlanilla.getNotificantes()).isEqualTo(UPDATED_NOTIFICANTES);
        assertThat(testPlanilla.getMoneda()).isEqualTo(UPDATED_MONEDA);
        assertThat(testPlanilla.getVacasMulti()).isEqualTo(UPDATED_VACAS_MULTI);
        assertThat(testPlanilla.getGrupoAprobador()).isEqualTo(UPDATED_GRUPO_APROBADOR);
        assertThat(testPlanilla.getPagador()).isEqualTo(UPDATED_PAGADOR);
        assertThat(testPlanilla.getGrupoContabilidad()).isEqualTo(UPDATED_GRUPO_CONTABILIDAD);
        assertThat(testPlanilla.getEstado()).isEqualTo(UPDATED_ESTADO);
    }

    @Test
    @Transactional
    void putNonExistingPlanilla() throws Exception {
        int databaseSizeBeforeUpdate = planillaRepository.findAll().size();
        planilla.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanillaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, planilla.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(planilla))
            )
            .andExpect(status().isBadRequest());

        // Validate the Planilla in the database
        List<Planilla> planillaList = planillaRepository.findAll();
        assertThat(planillaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPlanilla() throws Exception {
        int databaseSizeBeforeUpdate = planillaRepository.findAll().size();
        planilla.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanillaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(planilla))
            )
            .andExpect(status().isBadRequest());

        // Validate the Planilla in the database
        List<Planilla> planillaList = planillaRepository.findAll();
        assertThat(planillaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPlanilla() throws Exception {
        int databaseSizeBeforeUpdate = planillaRepository.findAll().size();
        planilla.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanillaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planilla)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Planilla in the database
        List<Planilla> planillaList = planillaRepository.findAll();
        assertThat(planillaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePlanillaWithPatch() throws Exception {
        // Initialize the database
        planillaRepository.saveAndFlush(planilla);

        int databaseSizeBeforeUpdate = planillaRepository.findAll().size();

        // Update the planilla using partial update
        Planilla partialUpdatedPlanilla = new Planilla();
        partialUpdatedPlanilla.setId(planilla.getId());

        partialUpdatedPlanilla
            .consecutivoBAC(UPDATED_CONSECUTIVO_BAC)
            .notas(UPDATED_NOTAS)
            .moneda(UPDATED_MONEDA)
            .grupoAprobador(UPDATED_GRUPO_APROBADOR)
            .pagador(UPDATED_PAGADOR);

        restPlanillaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlanilla.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlanilla))
            )
            .andExpect(status().isOk());

        // Validate the Planilla in the database
        List<Planilla> planillaList = planillaRepository.findAll();
        assertThat(planillaList).hasSize(databaseSizeBeforeUpdate);
        Planilla testPlanilla = planillaList.get(planillaList.size() - 1);
        assertThat(testPlanilla.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testPlanilla.getConsecutivoBAC()).isEqualTo(UPDATED_CONSECUTIVO_BAC);
        assertThat(testPlanilla.getPlanBAC()).isEqualTo(DEFAULT_PLAN_BAC);
        assertThat(testPlanilla.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testPlanilla.getNotas()).isEqualTo(UPDATED_NOTAS);
        assertThat(testPlanilla.getAprobador()).isEqualTo(DEFAULT_APROBADOR);
        assertThat(testPlanilla.getNotificantes()).isEqualTo(DEFAULT_NOTIFICANTES);
        assertThat(testPlanilla.getMoneda()).isEqualTo(UPDATED_MONEDA);
        assertThat(testPlanilla.getVacasMulti()).isEqualTo(DEFAULT_VACAS_MULTI);
        assertThat(testPlanilla.getGrupoAprobador()).isEqualTo(UPDATED_GRUPO_APROBADOR);
        assertThat(testPlanilla.getPagador()).isEqualTo(UPDATED_PAGADOR);
        assertThat(testPlanilla.getGrupoContabilidad()).isEqualTo(DEFAULT_GRUPO_CONTABILIDAD);
        assertThat(testPlanilla.getEstado()).isEqualTo(DEFAULT_ESTADO);
    }

    @Test
    @Transactional
    void fullUpdatePlanillaWithPatch() throws Exception {
        // Initialize the database
        planillaRepository.saveAndFlush(planilla);

        int databaseSizeBeforeUpdate = planillaRepository.findAll().size();

        // Update the planilla using partial update
        Planilla partialUpdatedPlanilla = new Planilla();
        partialUpdatedPlanilla.setId(planilla.getId());

        partialUpdatedPlanilla
            .tipo(UPDATED_TIPO)
            .consecutivoBAC(UPDATED_CONSECUTIVO_BAC)
            .planBAC(UPDATED_PLAN_BAC)
            .nombre(UPDATED_NOMBRE)
            .notas(UPDATED_NOTAS)
            .aprobador(UPDATED_APROBADOR)
            .notificantes(UPDATED_NOTIFICANTES)
            .moneda(UPDATED_MONEDA)
            .vacasMulti(UPDATED_VACAS_MULTI)
            .grupoAprobador(UPDATED_GRUPO_APROBADOR)
            .pagador(UPDATED_PAGADOR)
            .grupoContabilidad(UPDATED_GRUPO_CONTABILIDAD)
            .estado(UPDATED_ESTADO);

        restPlanillaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlanilla.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlanilla))
            )
            .andExpect(status().isOk());

        // Validate the Planilla in the database
        List<Planilla> planillaList = planillaRepository.findAll();
        assertThat(planillaList).hasSize(databaseSizeBeforeUpdate);
        Planilla testPlanilla = planillaList.get(planillaList.size() - 1);
        assertThat(testPlanilla.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testPlanilla.getConsecutivoBAC()).isEqualTo(UPDATED_CONSECUTIVO_BAC);
        assertThat(testPlanilla.getPlanBAC()).isEqualTo(UPDATED_PLAN_BAC);
        assertThat(testPlanilla.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testPlanilla.getNotas()).isEqualTo(UPDATED_NOTAS);
        assertThat(testPlanilla.getAprobador()).isEqualTo(UPDATED_APROBADOR);
        assertThat(testPlanilla.getNotificantes()).isEqualTo(UPDATED_NOTIFICANTES);
        assertThat(testPlanilla.getMoneda()).isEqualTo(UPDATED_MONEDA);
        assertThat(testPlanilla.getVacasMulti()).isEqualTo(UPDATED_VACAS_MULTI);
        assertThat(testPlanilla.getGrupoAprobador()).isEqualTo(UPDATED_GRUPO_APROBADOR);
        assertThat(testPlanilla.getPagador()).isEqualTo(UPDATED_PAGADOR);
        assertThat(testPlanilla.getGrupoContabilidad()).isEqualTo(UPDATED_GRUPO_CONTABILIDAD);
        assertThat(testPlanilla.getEstado()).isEqualTo(UPDATED_ESTADO);
    }

    @Test
    @Transactional
    void patchNonExistingPlanilla() throws Exception {
        int databaseSizeBeforeUpdate = planillaRepository.findAll().size();
        planilla.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanillaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, planilla.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(planilla))
            )
            .andExpect(status().isBadRequest());

        // Validate the Planilla in the database
        List<Planilla> planillaList = planillaRepository.findAll();
        assertThat(planillaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPlanilla() throws Exception {
        int databaseSizeBeforeUpdate = planillaRepository.findAll().size();
        planilla.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanillaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(planilla))
            )
            .andExpect(status().isBadRequest());

        // Validate the Planilla in the database
        List<Planilla> planillaList = planillaRepository.findAll();
        assertThat(planillaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPlanilla() throws Exception {
        int databaseSizeBeforeUpdate = planillaRepository.findAll().size();
        planilla.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanillaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(planilla)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Planilla in the database
        List<Planilla> planillaList = planillaRepository.findAll();
        assertThat(planillaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePlanilla() throws Exception {
        // Initialize the database
        planillaRepository.saveAndFlush(planilla);

        int databaseSizeBeforeDelete = planillaRepository.findAll().size();

        // Delete the planilla
        restPlanillaMockMvc
            .perform(delete(ENTITY_API_URL_ID, planilla.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Planilla> planillaList = planillaRepository.findAll();
        assertThat(planillaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
