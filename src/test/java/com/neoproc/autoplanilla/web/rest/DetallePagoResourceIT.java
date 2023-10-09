package com.neoproc.autoplanilla.web.rest;

import static com.neoproc.autoplanilla.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.neoproc.autoplanilla.IntegrationTest;
import com.neoproc.autoplanilla.domain.DetallePago;
import com.neoproc.autoplanilla.domain.enumeration.EstadoPago;
import com.neoproc.autoplanilla.repository.DetallePagoRepository;
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
 * Integration tests for the {@link DetallePagoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DetallePagoResourceIT {

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

    private static final String ENTITY_API_URL = "/api/detalle-pagos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DetallePagoRepository detallePagoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDetallePagoMockMvc;

    private DetallePago detallePago;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DetallePago createEntity(EntityManager em) {
        DetallePago detallePago = new DetallePago()
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
        return detallePago;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DetallePago createUpdatedEntity(EntityManager em) {
        DetallePago detallePago = new DetallePago()
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
        return detallePago;
    }

    @BeforeEach
    public void initTest() {
        detallePago = createEntity(em);
    }

    @Test
    @Transactional
    void createDetallePago() throws Exception {
        int databaseSizeBeforeCreate = detallePagoRepository.findAll().size();
        // Create the DetallePago
        restDetallePagoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detallePago)))
            .andExpect(status().isCreated());

        // Validate the DetallePago in the database
        List<DetallePago> detallePagoList = detallePagoRepository.findAll();
        assertThat(detallePagoList).hasSize(databaseSizeBeforeCreate + 1);
        DetallePago testDetallePago = detallePagoList.get(detallePagoList.size() - 1);
        assertThat(testDetallePago.getUserID()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testDetallePago.getCompany()).isEqualTo(DEFAULT_COMPANY);
        assertThat(testDetallePago.getSequenceBank()).isEqualTo(DEFAULT_SEQUENCE_BANK);
        assertThat(testDetallePago.getSalarioBruto()).isEqualByComparingTo(DEFAULT_SALARIO_BRUTO);
        assertThat(testDetallePago.getAdicionales()).isEqualByComparingTo(DEFAULT_ADICIONALES);
        assertThat(testDetallePago.getDeducciones()).isEqualByComparingTo(DEFAULT_DEDUCCIONES);
        assertThat(testDetallePago.getOtrasdeducciones()).isEqualByComparingTo(DEFAULT_OTRASDEDUCCIONES);
        assertThat(testDetallePago.getSocialSecurity()).isEqualByComparingTo(DEFAULT_SOCIAL_SECURITY);
        assertThat(testDetallePago.getRenta()).isEqualByComparingTo(DEFAULT_RENTA);
        assertThat(testDetallePago.getPagoNeto()).isEqualByComparingTo(DEFAULT_PAGO_NETO);
        assertThat(testDetallePago.getAdicionalesnodeducibles()).isEqualByComparingTo(DEFAULT_ADICIONALESNODEDUCIBLES);
        assertThat(testDetallePago.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testDetallePago.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testDetallePago.getProcessID()).isEqualTo(DEFAULT_PROCESS_ID);
    }

    @Test
    @Transactional
    void createDetallePagoWithExistingId() throws Exception {
        // Create the DetallePago with an existing ID
        detallePago.setId(1L);

        int databaseSizeBeforeCreate = detallePagoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDetallePagoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detallePago)))
            .andExpect(status().isBadRequest());

        // Validate the DetallePago in the database
        List<DetallePago> detallePagoList = detallePagoRepository.findAll();
        assertThat(detallePagoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkUserIDIsRequired() throws Exception {
        int databaseSizeBeforeTest = detallePagoRepository.findAll().size();
        // set the field null
        detallePago.setUserID(null);

        // Create the DetallePago, which fails.

        restDetallePagoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detallePago)))
            .andExpect(status().isBadRequest());

        List<DetallePago> detallePagoList = detallePagoRepository.findAll();
        assertThat(detallePagoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCompanyIsRequired() throws Exception {
        int databaseSizeBeforeTest = detallePagoRepository.findAll().size();
        // set the field null
        detallePago.setCompany(null);

        // Create the DetallePago, which fails.

        restDetallePagoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detallePago)))
            .andExpect(status().isBadRequest());

        List<DetallePago> detallePagoList = detallePagoRepository.findAll();
        assertThat(detallePagoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSalarioBrutoIsRequired() throws Exception {
        int databaseSizeBeforeTest = detallePagoRepository.findAll().size();
        // set the field null
        detallePago.setSalarioBruto(null);

        // Create the DetallePago, which fails.

        restDetallePagoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detallePago)))
            .andExpect(status().isBadRequest());

        List<DetallePago> detallePagoList = detallePagoRepository.findAll();
        assertThat(detallePagoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAdicionalesIsRequired() throws Exception {
        int databaseSizeBeforeTest = detallePagoRepository.findAll().size();
        // set the field null
        detallePago.setAdicionales(null);

        // Create the DetallePago, which fails.

        restDetallePagoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detallePago)))
            .andExpect(status().isBadRequest());

        List<DetallePago> detallePagoList = detallePagoRepository.findAll();
        assertThat(detallePagoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDeduccionesIsRequired() throws Exception {
        int databaseSizeBeforeTest = detallePagoRepository.findAll().size();
        // set the field null
        detallePago.setDeducciones(null);

        // Create the DetallePago, which fails.

        restDetallePagoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detallePago)))
            .andExpect(status().isBadRequest());

        List<DetallePago> detallePagoList = detallePagoRepository.findAll();
        assertThat(detallePagoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOtrasdeduccionesIsRequired() throws Exception {
        int databaseSizeBeforeTest = detallePagoRepository.findAll().size();
        // set the field null
        detallePago.setOtrasdeducciones(null);

        // Create the DetallePago, which fails.

        restDetallePagoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detallePago)))
            .andExpect(status().isBadRequest());

        List<DetallePago> detallePagoList = detallePagoRepository.findAll();
        assertThat(detallePagoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSocialSecurityIsRequired() throws Exception {
        int databaseSizeBeforeTest = detallePagoRepository.findAll().size();
        // set the field null
        detallePago.setSocialSecurity(null);

        // Create the DetallePago, which fails.

        restDetallePagoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detallePago)))
            .andExpect(status().isBadRequest());

        List<DetallePago> detallePagoList = detallePagoRepository.findAll();
        assertThat(detallePagoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRentaIsRequired() throws Exception {
        int databaseSizeBeforeTest = detallePagoRepository.findAll().size();
        // set the field null
        detallePago.setRenta(null);

        // Create the DetallePago, which fails.

        restDetallePagoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detallePago)))
            .andExpect(status().isBadRequest());

        List<DetallePago> detallePagoList = detallePagoRepository.findAll();
        assertThat(detallePagoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPagoNetoIsRequired() throws Exception {
        int databaseSizeBeforeTest = detallePagoRepository.findAll().size();
        // set the field null
        detallePago.setPagoNeto(null);

        // Create the DetallePago, which fails.

        restDetallePagoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detallePago)))
            .andExpect(status().isBadRequest());

        List<DetallePago> detallePagoList = detallePagoRepository.findAll();
        assertThat(detallePagoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAdicionalesnodeduciblesIsRequired() throws Exception {
        int databaseSizeBeforeTest = detallePagoRepository.findAll().size();
        // set the field null
        detallePago.setAdicionalesnodeducibles(null);

        // Create the DetallePago, which fails.

        restDetallePagoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detallePago)))
            .andExpect(status().isBadRequest());

        List<DetallePago> detallePagoList = detallePagoRepository.findAll();
        assertThat(detallePagoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = detallePagoRepository.findAll().size();
        // set the field null
        detallePago.setType(null);

        // Create the DetallePago, which fails.

        restDetallePagoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detallePago)))
            .andExpect(status().isBadRequest());

        List<DetallePago> detallePagoList = detallePagoRepository.findAll();
        assertThat(detallePagoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = detallePagoRepository.findAll().size();
        // set the field null
        detallePago.setStatus(null);

        // Create the DetallePago, which fails.

        restDetallePagoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detallePago)))
            .andExpect(status().isBadRequest());

        List<DetallePago> detallePagoList = detallePagoRepository.findAll();
        assertThat(detallePagoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDetallePagos() throws Exception {
        // Initialize the database
        detallePagoRepository.saveAndFlush(detallePago);

        // Get all the detallePagoList
        restDetallePagoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(detallePago.getId().intValue())))
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
    void getDetallePago() throws Exception {
        // Initialize the database
        detallePagoRepository.saveAndFlush(detallePago);

        // Get the detallePago
        restDetallePagoMockMvc
            .perform(get(ENTITY_API_URL_ID, detallePago.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(detallePago.getId().intValue()))
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
    void getNonExistingDetallePago() throws Exception {
        // Get the detallePago
        restDetallePagoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDetallePago() throws Exception {
        // Initialize the database
        detallePagoRepository.saveAndFlush(detallePago);

        int databaseSizeBeforeUpdate = detallePagoRepository.findAll().size();

        // Update the detallePago
        DetallePago updatedDetallePago = detallePagoRepository.findById(detallePago.getId()).get();
        // Disconnect from session so that the updates on updatedDetallePago are not directly saved in db
        em.detach(updatedDetallePago);
        updatedDetallePago
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

        restDetallePagoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDetallePago.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDetallePago))
            )
            .andExpect(status().isOk());

        // Validate the DetallePago in the database
        List<DetallePago> detallePagoList = detallePagoRepository.findAll();
        assertThat(detallePagoList).hasSize(databaseSizeBeforeUpdate);
        DetallePago testDetallePago = detallePagoList.get(detallePagoList.size() - 1);
        assertThat(testDetallePago.getUserID()).isEqualTo(UPDATED_USER_ID);
        assertThat(testDetallePago.getCompany()).isEqualTo(UPDATED_COMPANY);
        assertThat(testDetallePago.getSequenceBank()).isEqualTo(UPDATED_SEQUENCE_BANK);
        assertThat(testDetallePago.getSalarioBruto()).isEqualByComparingTo(UPDATED_SALARIO_BRUTO);
        assertThat(testDetallePago.getAdicionales()).isEqualByComparingTo(UPDATED_ADICIONALES);
        assertThat(testDetallePago.getDeducciones()).isEqualByComparingTo(UPDATED_DEDUCCIONES);
        assertThat(testDetallePago.getOtrasdeducciones()).isEqualByComparingTo(UPDATED_OTRASDEDUCCIONES);
        assertThat(testDetallePago.getSocialSecurity()).isEqualByComparingTo(UPDATED_SOCIAL_SECURITY);
        assertThat(testDetallePago.getRenta()).isEqualByComparingTo(UPDATED_RENTA);
        assertThat(testDetallePago.getPagoNeto()).isEqualByComparingTo(UPDATED_PAGO_NETO);
        assertThat(testDetallePago.getAdicionalesnodeducibles()).isEqualByComparingTo(UPDATED_ADICIONALESNODEDUCIBLES);
        assertThat(testDetallePago.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testDetallePago.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testDetallePago.getProcessID()).isEqualTo(UPDATED_PROCESS_ID);
    }

    @Test
    @Transactional
    void putNonExistingDetallePago() throws Exception {
        int databaseSizeBeforeUpdate = detallePagoRepository.findAll().size();
        detallePago.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDetallePagoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, detallePago.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(detallePago))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetallePago in the database
        List<DetallePago> detallePagoList = detallePagoRepository.findAll();
        assertThat(detallePagoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDetallePago() throws Exception {
        int databaseSizeBeforeUpdate = detallePagoRepository.findAll().size();
        detallePago.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetallePagoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(detallePago))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetallePago in the database
        List<DetallePago> detallePagoList = detallePagoRepository.findAll();
        assertThat(detallePagoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDetallePago() throws Exception {
        int databaseSizeBeforeUpdate = detallePagoRepository.findAll().size();
        detallePago.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetallePagoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detallePago)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DetallePago in the database
        List<DetallePago> detallePagoList = detallePagoRepository.findAll();
        assertThat(detallePagoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDetallePagoWithPatch() throws Exception {
        // Initialize the database
        detallePagoRepository.saveAndFlush(detallePago);

        int databaseSizeBeforeUpdate = detallePagoRepository.findAll().size();

        // Update the detallePago using partial update
        DetallePago partialUpdatedDetallePago = new DetallePago();
        partialUpdatedDetallePago.setId(detallePago.getId());

        partialUpdatedDetallePago
            .salarioBruto(UPDATED_SALARIO_BRUTO)
            .adicionales(UPDATED_ADICIONALES)
            .socialSecurity(UPDATED_SOCIAL_SECURITY)
            .pagoNeto(UPDATED_PAGO_NETO)
            .adicionalesnodeducibles(UPDATED_ADICIONALESNODEDUCIBLES)
            .status(UPDATED_STATUS)
            .processID(UPDATED_PROCESS_ID);

        restDetallePagoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDetallePago.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDetallePago))
            )
            .andExpect(status().isOk());

        // Validate the DetallePago in the database
        List<DetallePago> detallePagoList = detallePagoRepository.findAll();
        assertThat(detallePagoList).hasSize(databaseSizeBeforeUpdate);
        DetallePago testDetallePago = detallePagoList.get(detallePagoList.size() - 1);
        assertThat(testDetallePago.getUserID()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testDetallePago.getCompany()).isEqualTo(DEFAULT_COMPANY);
        assertThat(testDetallePago.getSequenceBank()).isEqualTo(DEFAULT_SEQUENCE_BANK);
        assertThat(testDetallePago.getSalarioBruto()).isEqualByComparingTo(UPDATED_SALARIO_BRUTO);
        assertThat(testDetallePago.getAdicionales()).isEqualByComparingTo(UPDATED_ADICIONALES);
        assertThat(testDetallePago.getDeducciones()).isEqualByComparingTo(DEFAULT_DEDUCCIONES);
        assertThat(testDetallePago.getOtrasdeducciones()).isEqualByComparingTo(DEFAULT_OTRASDEDUCCIONES);
        assertThat(testDetallePago.getSocialSecurity()).isEqualByComparingTo(UPDATED_SOCIAL_SECURITY);
        assertThat(testDetallePago.getRenta()).isEqualByComparingTo(DEFAULT_RENTA);
        assertThat(testDetallePago.getPagoNeto()).isEqualByComparingTo(UPDATED_PAGO_NETO);
        assertThat(testDetallePago.getAdicionalesnodeducibles()).isEqualByComparingTo(UPDATED_ADICIONALESNODEDUCIBLES);
        assertThat(testDetallePago.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testDetallePago.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testDetallePago.getProcessID()).isEqualTo(UPDATED_PROCESS_ID);
    }

    @Test
    @Transactional
    void fullUpdateDetallePagoWithPatch() throws Exception {
        // Initialize the database
        detallePagoRepository.saveAndFlush(detallePago);

        int databaseSizeBeforeUpdate = detallePagoRepository.findAll().size();

        // Update the detallePago using partial update
        DetallePago partialUpdatedDetallePago = new DetallePago();
        partialUpdatedDetallePago.setId(detallePago.getId());

        partialUpdatedDetallePago
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

        restDetallePagoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDetallePago.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDetallePago))
            )
            .andExpect(status().isOk());

        // Validate the DetallePago in the database
        List<DetallePago> detallePagoList = detallePagoRepository.findAll();
        assertThat(detallePagoList).hasSize(databaseSizeBeforeUpdate);
        DetallePago testDetallePago = detallePagoList.get(detallePagoList.size() - 1);
        assertThat(testDetallePago.getUserID()).isEqualTo(UPDATED_USER_ID);
        assertThat(testDetallePago.getCompany()).isEqualTo(UPDATED_COMPANY);
        assertThat(testDetallePago.getSequenceBank()).isEqualTo(UPDATED_SEQUENCE_BANK);
        assertThat(testDetallePago.getSalarioBruto()).isEqualByComparingTo(UPDATED_SALARIO_BRUTO);
        assertThat(testDetallePago.getAdicionales()).isEqualByComparingTo(UPDATED_ADICIONALES);
        assertThat(testDetallePago.getDeducciones()).isEqualByComparingTo(UPDATED_DEDUCCIONES);
        assertThat(testDetallePago.getOtrasdeducciones()).isEqualByComparingTo(UPDATED_OTRASDEDUCCIONES);
        assertThat(testDetallePago.getSocialSecurity()).isEqualByComparingTo(UPDATED_SOCIAL_SECURITY);
        assertThat(testDetallePago.getRenta()).isEqualByComparingTo(UPDATED_RENTA);
        assertThat(testDetallePago.getPagoNeto()).isEqualByComparingTo(UPDATED_PAGO_NETO);
        assertThat(testDetallePago.getAdicionalesnodeducibles()).isEqualByComparingTo(UPDATED_ADICIONALESNODEDUCIBLES);
        assertThat(testDetallePago.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testDetallePago.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testDetallePago.getProcessID()).isEqualTo(UPDATED_PROCESS_ID);
    }

    @Test
    @Transactional
    void patchNonExistingDetallePago() throws Exception {
        int databaseSizeBeforeUpdate = detallePagoRepository.findAll().size();
        detallePago.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDetallePagoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, detallePago.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(detallePago))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetallePago in the database
        List<DetallePago> detallePagoList = detallePagoRepository.findAll();
        assertThat(detallePagoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDetallePago() throws Exception {
        int databaseSizeBeforeUpdate = detallePagoRepository.findAll().size();
        detallePago.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetallePagoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(detallePago))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetallePago in the database
        List<DetallePago> detallePagoList = detallePagoRepository.findAll();
        assertThat(detallePagoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDetallePago() throws Exception {
        int databaseSizeBeforeUpdate = detallePagoRepository.findAll().size();
        detallePago.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetallePagoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(detallePago))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DetallePago in the database
        List<DetallePago> detallePagoList = detallePagoRepository.findAll();
        assertThat(detallePagoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDetallePago() throws Exception {
        // Initialize the database
        detallePagoRepository.saveAndFlush(detallePago);

        int databaseSizeBeforeDelete = detallePagoRepository.findAll().size();

        // Delete the detallePago
        restDetallePagoMockMvc
            .perform(delete(ENTITY_API_URL_ID, detallePago.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DetallePago> detallePagoList = detallePagoRepository.findAll();
        assertThat(detallePagoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
