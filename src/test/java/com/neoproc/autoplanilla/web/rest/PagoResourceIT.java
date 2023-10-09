package com.neoproc.autoplanilla.web.rest;

import static com.neoproc.autoplanilla.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.neoproc.autoplanilla.IntegrationTest;
import com.neoproc.autoplanilla.domain.Pago;
import com.neoproc.autoplanilla.domain.enumeration.EstadoPago;
import com.neoproc.autoplanilla.repository.PagoRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PagoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PagoResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY = "BBBBBBBBBB";

    private static final Integer DEFAULT_SEQUENCE_BANK = 1;
    private static final Integer UPDATED_SEQUENCE_BANK = 2;

    private static final BigDecimal DEFAULT_SALARIO_BRUTO = new BigDecimal(0);
    private static final BigDecimal UPDATED_SALARIO_BRUTO = new BigDecimal(1);

    private static final BigDecimal DEFAULT_ADICIONALES = new BigDecimal(0);
    private static final BigDecimal UPDATED_ADICIONALES = new BigDecimal(1);

    private static final BigDecimal DEFAULT_DEDUCCIONES = new BigDecimal(0);
    private static final BigDecimal UPDATED_DEDUCCIONES = new BigDecimal(1);

    private static final BigDecimal DEFAULT_OTRASDEDUCCIONES = new BigDecimal(0);
    private static final BigDecimal UPDATED_OTRASDEDUCCIONES = new BigDecimal(1);

    private static final BigDecimal DEFAULT_SOCIAL_SECURITY = new BigDecimal(0);
    private static final BigDecimal UPDATED_SOCIAL_SECURITY = new BigDecimal(1);

    private static final BigDecimal DEFAULT_RENTA = new BigDecimal(0);
    private static final BigDecimal UPDATED_RENTA = new BigDecimal(1);

    private static final BigDecimal DEFAULT_PAGO_NETO = new BigDecimal(0);
    private static final BigDecimal UPDATED_PAGO_NETO = new BigDecimal(1);

    private static final BigDecimal DEFAULT_ADICIONALESNODEDUCIBLES = new BigDecimal(0);
    private static final BigDecimal UPDATED_ADICIONALESNODEDUCIBLES = new BigDecimal(1);

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final EstadoPago DEFAULT_STATUS = EstadoPago.DRAFT;
    private static final EstadoPago UPDATED_STATUS = EstadoPago.COMPLETED;

    private static final String DEFAULT_PROCESS_ID = "AAAAAAAAAA";
    private static final String UPDATED_PROCESS_ID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/pagos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPagoMockMvc;

    private Pago pago;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pago createEntity(EntityManager em) {
        Pago pago = new Pago()
            .userID(DEFAULT_USER_ID)
            .company(DEFAULT_COMPANY)
            .sequenceBank(DEFAULT_SEQUENCE_BANK)
            .salarioBruto(DEFAULT_SALARIO_BRUTO)
            .adicionales(DEFAULT_ADICIONALES)
            .deducciones(DEFAULT_DEDUCCIONES)
            .otrasdeducciones(DEFAULT_OTRASDEDUCCIONES)
            .socialSecurity(DEFAULT_SOCIAL_SECURITY)
            .renta(DEFAULT_RENTA)
            .pagoNeto(DEFAULT_PAGO_NETO)
            .adicionalesnodeducibles(DEFAULT_ADICIONALESNODEDUCIBLES)
            .type(DEFAULT_TYPE)
            .status(DEFAULT_STATUS)
            .processID(DEFAULT_PROCESS_ID);
        return pago;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pago createUpdatedEntity(EntityManager em) {
        Pago pago = new Pago()
            .userID(UPDATED_USER_ID)
            .company(UPDATED_COMPANY)
            .sequenceBank(UPDATED_SEQUENCE_BANK)
            .salarioBruto(UPDATED_SALARIO_BRUTO)
            .adicionales(UPDATED_ADICIONALES)
            .deducciones(UPDATED_DEDUCCIONES)
            .otrasdeducciones(UPDATED_OTRASDEDUCCIONES)
            .socialSecurity(UPDATED_SOCIAL_SECURITY)
            .renta(UPDATED_RENTA)
            .pagoNeto(UPDATED_PAGO_NETO)
            .adicionalesnodeducibles(UPDATED_ADICIONALESNODEDUCIBLES)
            .type(UPDATED_TYPE)
            .status(UPDATED_STATUS)
            .processID(UPDATED_PROCESS_ID);
        return pago;
    }

    @BeforeEach
    public void initTest() {
        pago = createEntity(em);
    }

    @Test
    @Transactional
    void createPago() throws Exception {
        int databaseSizeBeforeCreate = pagoRepository.findAll().size();
        // Create the Pago
        restPagoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pago)))
            .andExpect(status().isCreated());

        // Validate the Pago in the database
        List<Pago> pagoList = pagoRepository.findAll();
        assertThat(pagoList).hasSize(databaseSizeBeforeCreate + 1);
        Pago testPago = pagoList.get(pagoList.size() - 1);
        assertThat(testPago.getUserID()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testPago.getCompany()).isEqualTo(DEFAULT_COMPANY);
        assertThat(testPago.getSequenceBank()).isEqualTo(DEFAULT_SEQUENCE_BANK);
        assertThat(testPago.getSalarioBruto()).isEqualByComparingTo(DEFAULT_SALARIO_BRUTO);
        assertThat(testPago.getAdicionales()).isEqualByComparingTo(DEFAULT_ADICIONALES);
        assertThat(testPago.getDeducciones()).isEqualByComparingTo(DEFAULT_DEDUCCIONES);
        assertThat(testPago.getOtrasdeducciones()).isEqualByComparingTo(DEFAULT_OTRASDEDUCCIONES);
        assertThat(testPago.getSocialSecurity()).isEqualByComparingTo(DEFAULT_SOCIAL_SECURITY);
        assertThat(testPago.getRenta()).isEqualByComparingTo(DEFAULT_RENTA);
        assertThat(testPago.getPagoNeto()).isEqualByComparingTo(DEFAULT_PAGO_NETO);
        assertThat(testPago.getAdicionalesnodeducibles()).isEqualByComparingTo(DEFAULT_ADICIONALESNODEDUCIBLES);
        assertThat(testPago.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testPago.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPago.getProcessID()).isEqualTo(DEFAULT_PROCESS_ID);
    }

    @Test
    @Transactional
    void createPagoWithExistingId() throws Exception {
        // Create the Pago with an existing ID
        pago.setId(1L);

        int databaseSizeBeforeCreate = pagoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPagoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pago)))
            .andExpect(status().isBadRequest());

        // Validate the Pago in the database
        List<Pago> pagoList = pagoRepository.findAll();
        assertThat(pagoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkUserIDIsRequired() throws Exception {
        int databaseSizeBeforeTest = pagoRepository.findAll().size();
        // set the field null
        pago.setUserID(null);

        // Create the Pago, which fails.

        restPagoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pago)))
            .andExpect(status().isBadRequest());

        List<Pago> pagoList = pagoRepository.findAll();
        assertThat(pagoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCompanyIsRequired() throws Exception {
        int databaseSizeBeforeTest = pagoRepository.findAll().size();
        // set the field null
        pago.setCompany(null);

        // Create the Pago, which fails.

        restPagoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pago)))
            .andExpect(status().isBadRequest());

        List<Pago> pagoList = pagoRepository.findAll();
        assertThat(pagoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSalarioBrutoIsRequired() throws Exception {
        int databaseSizeBeforeTest = pagoRepository.findAll().size();
        // set the field null
        pago.setSalarioBruto(null);

        // Create the Pago, which fails.

        restPagoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pago)))
            .andExpect(status().isBadRequest());

        List<Pago> pagoList = pagoRepository.findAll();
        assertThat(pagoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAdicionalesIsRequired() throws Exception {
        int databaseSizeBeforeTest = pagoRepository.findAll().size();
        // set the field null
        pago.setAdicionales(null);

        // Create the Pago, which fails.

        restPagoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pago)))
            .andExpect(status().isBadRequest());

        List<Pago> pagoList = pagoRepository.findAll();
        assertThat(pagoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDeduccionesIsRequired() throws Exception {
        int databaseSizeBeforeTest = pagoRepository.findAll().size();
        // set the field null
        pago.setDeducciones(null);

        // Create the Pago, which fails.

        restPagoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pago)))
            .andExpect(status().isBadRequest());

        List<Pago> pagoList = pagoRepository.findAll();
        assertThat(pagoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOtrasdeduccionesIsRequired() throws Exception {
        int databaseSizeBeforeTest = pagoRepository.findAll().size();
        // set the field null
        pago.setOtrasdeducciones(null);

        // Create the Pago, which fails.

        restPagoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pago)))
            .andExpect(status().isBadRequest());

        List<Pago> pagoList = pagoRepository.findAll();
        assertThat(pagoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSocialSecurityIsRequired() throws Exception {
        int databaseSizeBeforeTest = pagoRepository.findAll().size();
        // set the field null
        pago.setSocialSecurity(null);

        // Create the Pago, which fails.

        restPagoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pago)))
            .andExpect(status().isBadRequest());

        List<Pago> pagoList = pagoRepository.findAll();
        assertThat(pagoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRentaIsRequired() throws Exception {
        int databaseSizeBeforeTest = pagoRepository.findAll().size();
        // set the field null
        pago.setRenta(null);

        // Create the Pago, which fails.

        restPagoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pago)))
            .andExpect(status().isBadRequest());

        List<Pago> pagoList = pagoRepository.findAll();
        assertThat(pagoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPagoNetoIsRequired() throws Exception {
        int databaseSizeBeforeTest = pagoRepository.findAll().size();
        // set the field null
        pago.setPagoNeto(null);

        // Create the Pago, which fails.

        restPagoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pago)))
            .andExpect(status().isBadRequest());

        List<Pago> pagoList = pagoRepository.findAll();
        assertThat(pagoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAdicionalesnodeduciblesIsRequired() throws Exception {
        int databaseSizeBeforeTest = pagoRepository.findAll().size();
        // set the field null
        pago.setAdicionalesnodeducibles(null);

        // Create the Pago, which fails.

        restPagoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pago)))
            .andExpect(status().isBadRequest());

        List<Pago> pagoList = pagoRepository.findAll();
        assertThat(pagoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = pagoRepository.findAll().size();
        // set the field null
        pago.setType(null);

        // Create the Pago, which fails.

        restPagoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pago)))
            .andExpect(status().isBadRequest());

        List<Pago> pagoList = pagoRepository.findAll();
        assertThat(pagoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = pagoRepository.findAll().size();
        // set the field null
        pago.setStatus(null);

        // Create the Pago, which fails.

        restPagoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pago)))
            .andExpect(status().isBadRequest());

        List<Pago> pagoList = pagoRepository.findAll();
        assertThat(pagoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPagos() throws Exception {
        // Initialize the database
        pagoRepository.saveAndFlush(pago);

        // Get all the pagoList
        restPagoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pago.getId().intValue())))
            .andExpect(jsonPath("$.[*].userID").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].company").value(hasItem(DEFAULT_COMPANY)))
            .andExpect(jsonPath("$.[*].sequenceBank").value(hasItem(DEFAULT_SEQUENCE_BANK)))
            .andExpect(jsonPath("$.[*].salarioBruto").value(hasItem(sameNumber(DEFAULT_SALARIO_BRUTO))))
            .andExpect(jsonPath("$.[*].adicionales").value(hasItem(sameNumber(DEFAULT_ADICIONALES))))
            .andExpect(jsonPath("$.[*].deducciones").value(hasItem(sameNumber(DEFAULT_DEDUCCIONES))))
            .andExpect(jsonPath("$.[*].otrasdeducciones").value(hasItem(sameNumber(DEFAULT_OTRASDEDUCCIONES))))
            .andExpect(jsonPath("$.[*].socialSecurity").value(hasItem(sameNumber(DEFAULT_SOCIAL_SECURITY))))
            .andExpect(jsonPath("$.[*].renta").value(hasItem(sameNumber(DEFAULT_RENTA))))
            .andExpect(jsonPath("$.[*].pagoNeto").value(hasItem(sameNumber(DEFAULT_PAGO_NETO))))
            .andExpect(jsonPath("$.[*].adicionalesnodeducibles").value(hasItem(sameNumber(DEFAULT_ADICIONALESNODEDUCIBLES))))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].processID").value(hasItem(DEFAULT_PROCESS_ID)));
    }

    @Test
    @Transactional
    void getPago() throws Exception {
        // Initialize the database
        pagoRepository.saveAndFlush(pago);

        // Get the pago
        restPagoMockMvc
            .perform(get(ENTITY_API_URL_ID, pago.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pago.getId().intValue()))
            .andExpect(jsonPath("$.userID").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.company").value(DEFAULT_COMPANY))
            .andExpect(jsonPath("$.sequenceBank").value(DEFAULT_SEQUENCE_BANK))
            .andExpect(jsonPath("$.salarioBruto").value(sameNumber(DEFAULT_SALARIO_BRUTO)))
            .andExpect(jsonPath("$.adicionales").value(sameNumber(DEFAULT_ADICIONALES)))
            .andExpect(jsonPath("$.deducciones").value(sameNumber(DEFAULT_DEDUCCIONES)))
            .andExpect(jsonPath("$.otrasdeducciones").value(sameNumber(DEFAULT_OTRASDEDUCCIONES)))
            .andExpect(jsonPath("$.socialSecurity").value(sameNumber(DEFAULT_SOCIAL_SECURITY)))
            .andExpect(jsonPath("$.renta").value(sameNumber(DEFAULT_RENTA)))
            .andExpect(jsonPath("$.pagoNeto").value(sameNumber(DEFAULT_PAGO_NETO)))
            .andExpect(jsonPath("$.adicionalesnodeducibles").value(sameNumber(DEFAULT_ADICIONALESNODEDUCIBLES)))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.processID").value(DEFAULT_PROCESS_ID));
    }

    @Test
    @Transactional
    void getNonExistingPago() throws Exception {
        // Get the pago
        restPagoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPago() throws Exception {
        // Initialize the database
        pagoRepository.saveAndFlush(pago);

        int databaseSizeBeforeUpdate = pagoRepository.findAll().size();

        // Update the pago
        Pago updatedPago = pagoRepository.findById(pago.getId()).get();
        // Disconnect from session so that the updates on updatedPago are not directly saved in db
        em.detach(updatedPago);
        updatedPago
            .userID(UPDATED_USER_ID)
            .company(UPDATED_COMPANY)
            .sequenceBank(UPDATED_SEQUENCE_BANK)
            .salarioBruto(UPDATED_SALARIO_BRUTO)
            .adicionales(UPDATED_ADICIONALES)
            .deducciones(UPDATED_DEDUCCIONES)
            .otrasdeducciones(UPDATED_OTRASDEDUCCIONES)
            .socialSecurity(UPDATED_SOCIAL_SECURITY)
            .renta(UPDATED_RENTA)
            .pagoNeto(UPDATED_PAGO_NETO)
            .adicionalesnodeducibles(UPDATED_ADICIONALESNODEDUCIBLES)
            .type(UPDATED_TYPE)
            .status(UPDATED_STATUS)
            .processID(UPDATED_PROCESS_ID);

        restPagoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPago.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPago))
            )
            .andExpect(status().isOk());

        // Validate the Pago in the database
        List<Pago> pagoList = pagoRepository.findAll();
        assertThat(pagoList).hasSize(databaseSizeBeforeUpdate);
        Pago testPago = pagoList.get(pagoList.size() - 1);
        assertThat(testPago.getUserID()).isEqualTo(UPDATED_USER_ID);
        assertThat(testPago.getCompany()).isEqualTo(UPDATED_COMPANY);
        assertThat(testPago.getSequenceBank()).isEqualTo(UPDATED_SEQUENCE_BANK);
        assertThat(testPago.getSalarioBruto()).isEqualByComparingTo(UPDATED_SALARIO_BRUTO);
        assertThat(testPago.getAdicionales()).isEqualByComparingTo(UPDATED_ADICIONALES);
        assertThat(testPago.getDeducciones()).isEqualByComparingTo(UPDATED_DEDUCCIONES);
        assertThat(testPago.getOtrasdeducciones()).isEqualByComparingTo(UPDATED_OTRASDEDUCCIONES);
        assertThat(testPago.getSocialSecurity()).isEqualByComparingTo(UPDATED_SOCIAL_SECURITY);
        assertThat(testPago.getRenta()).isEqualByComparingTo(UPDATED_RENTA);
        assertThat(testPago.getPagoNeto()).isEqualByComparingTo(UPDATED_PAGO_NETO);
        assertThat(testPago.getAdicionalesnodeducibles()).isEqualByComparingTo(UPDATED_ADICIONALESNODEDUCIBLES);
        assertThat(testPago.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testPago.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPago.getProcessID()).isEqualTo(UPDATED_PROCESS_ID);
    }

    @Test
    @Transactional
    void putNonExistingPago() throws Exception {
        int databaseSizeBeforeUpdate = pagoRepository.findAll().size();
        pago.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPagoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pago.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pago))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pago in the database
        List<Pago> pagoList = pagoRepository.findAll();
        assertThat(pagoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPago() throws Exception {
        int databaseSizeBeforeUpdate = pagoRepository.findAll().size();
        pago.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPagoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pago))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pago in the database
        List<Pago> pagoList = pagoRepository.findAll();
        assertThat(pagoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPago() throws Exception {
        int databaseSizeBeforeUpdate = pagoRepository.findAll().size();
        pago.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPagoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pago)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pago in the database
        List<Pago> pagoList = pagoRepository.findAll();
        assertThat(pagoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePagoWithPatch() throws Exception {
        // Initialize the database
        pagoRepository.saveAndFlush(pago);

        int databaseSizeBeforeUpdate = pagoRepository.findAll().size();

        // Update the pago using partial update
        Pago partialUpdatedPago = new Pago();
        partialUpdatedPago.setId(pago.getId());

        partialUpdatedPago
            .userID(UPDATED_USER_ID)
            .company(UPDATED_COMPANY)
            .deducciones(UPDATED_DEDUCCIONES)
            .otrasdeducciones(UPDATED_OTRASDEDUCCIONES)
            .socialSecurity(UPDATED_SOCIAL_SECURITY)
            .renta(UPDATED_RENTA)
            .type(UPDATED_TYPE);

        restPagoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPago.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPago))
            )
            .andExpect(status().isOk());

        // Validate the Pago in the database
        List<Pago> pagoList = pagoRepository.findAll();
        assertThat(pagoList).hasSize(databaseSizeBeforeUpdate);
        Pago testPago = pagoList.get(pagoList.size() - 1);
        assertThat(testPago.getUserID()).isEqualTo(UPDATED_USER_ID);
        assertThat(testPago.getCompany()).isEqualTo(UPDATED_COMPANY);
        assertThat(testPago.getSequenceBank()).isEqualTo(DEFAULT_SEQUENCE_BANK);
        assertThat(testPago.getSalarioBruto()).isEqualByComparingTo(DEFAULT_SALARIO_BRUTO);
        assertThat(testPago.getAdicionales()).isEqualByComparingTo(DEFAULT_ADICIONALES);
        assertThat(testPago.getDeducciones()).isEqualByComparingTo(UPDATED_DEDUCCIONES);
        assertThat(testPago.getOtrasdeducciones()).isEqualByComparingTo(UPDATED_OTRASDEDUCCIONES);
        assertThat(testPago.getSocialSecurity()).isEqualByComparingTo(UPDATED_SOCIAL_SECURITY);
        assertThat(testPago.getRenta()).isEqualByComparingTo(UPDATED_RENTA);
        assertThat(testPago.getPagoNeto()).isEqualByComparingTo(DEFAULT_PAGO_NETO);
        assertThat(testPago.getAdicionalesnodeducibles()).isEqualByComparingTo(DEFAULT_ADICIONALESNODEDUCIBLES);
        assertThat(testPago.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testPago.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPago.getProcessID()).isEqualTo(DEFAULT_PROCESS_ID);
    }

    @Test
    @Transactional
    void fullUpdatePagoWithPatch() throws Exception {
        // Initialize the database
        pagoRepository.saveAndFlush(pago);

        int databaseSizeBeforeUpdate = pagoRepository.findAll().size();

        // Update the pago using partial update
        Pago partialUpdatedPago = new Pago();
        partialUpdatedPago.setId(pago.getId());

        partialUpdatedPago
            .userID(UPDATED_USER_ID)
            .company(UPDATED_COMPANY)
            .sequenceBank(UPDATED_SEQUENCE_BANK)
            .salarioBruto(UPDATED_SALARIO_BRUTO)
            .adicionales(UPDATED_ADICIONALES)
            .deducciones(UPDATED_DEDUCCIONES)
            .otrasdeducciones(UPDATED_OTRASDEDUCCIONES)
            .socialSecurity(UPDATED_SOCIAL_SECURITY)
            .renta(UPDATED_RENTA)
            .pagoNeto(UPDATED_PAGO_NETO)
            .adicionalesnodeducibles(UPDATED_ADICIONALESNODEDUCIBLES)
            .type(UPDATED_TYPE)
            .status(UPDATED_STATUS)
            .processID(UPDATED_PROCESS_ID);

        restPagoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPago.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPago))
            )
            .andExpect(status().isOk());

        // Validate the Pago in the database
        List<Pago> pagoList = pagoRepository.findAll();
        assertThat(pagoList).hasSize(databaseSizeBeforeUpdate);
        Pago testPago = pagoList.get(pagoList.size() - 1);
        assertThat(testPago.getUserID()).isEqualTo(UPDATED_USER_ID);
        assertThat(testPago.getCompany()).isEqualTo(UPDATED_COMPANY);
        assertThat(testPago.getSequenceBank()).isEqualTo(UPDATED_SEQUENCE_BANK);
        assertThat(testPago.getSalarioBruto()).isEqualByComparingTo(UPDATED_SALARIO_BRUTO);
        assertThat(testPago.getAdicionales()).isEqualByComparingTo(UPDATED_ADICIONALES);
        assertThat(testPago.getDeducciones()).isEqualByComparingTo(UPDATED_DEDUCCIONES);
        assertThat(testPago.getOtrasdeducciones()).isEqualByComparingTo(UPDATED_OTRASDEDUCCIONES);
        assertThat(testPago.getSocialSecurity()).isEqualByComparingTo(UPDATED_SOCIAL_SECURITY);
        assertThat(testPago.getRenta()).isEqualByComparingTo(UPDATED_RENTA);
        assertThat(testPago.getPagoNeto()).isEqualByComparingTo(UPDATED_PAGO_NETO);
        assertThat(testPago.getAdicionalesnodeducibles()).isEqualByComparingTo(UPDATED_ADICIONALESNODEDUCIBLES);
        assertThat(testPago.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testPago.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPago.getProcessID()).isEqualTo(UPDATED_PROCESS_ID);
    }

    @Test
    @Transactional
    void patchNonExistingPago() throws Exception {
        int databaseSizeBeforeUpdate = pagoRepository.findAll().size();
        pago.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPagoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pago.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pago))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pago in the database
        List<Pago> pagoList = pagoRepository.findAll();
        assertThat(pagoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPago() throws Exception {
        int databaseSizeBeforeUpdate = pagoRepository.findAll().size();
        pago.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPagoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pago))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pago in the database
        List<Pago> pagoList = pagoRepository.findAll();
        assertThat(pagoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPago() throws Exception {
        int databaseSizeBeforeUpdate = pagoRepository.findAll().size();
        pago.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPagoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(pago)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pago in the database
        List<Pago> pagoList = pagoRepository.findAll();
        assertThat(pagoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePago() throws Exception {
        // Initialize the database
        pagoRepository.saveAndFlush(pago);

        int databaseSizeBeforeDelete = pagoRepository.findAll().size();

        // Delete the pago
        restPagoMockMvc
            .perform(delete(ENTITY_API_URL_ID, pago.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Pago> pagoList = pagoRepository.findAll();
        assertThat(pagoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
