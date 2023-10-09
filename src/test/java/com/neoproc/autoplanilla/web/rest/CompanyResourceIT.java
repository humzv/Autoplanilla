package com.neoproc.autoplanilla.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.neoproc.autoplanilla.IntegrationTest;
import com.neoproc.autoplanilla.domain.Company;
import com.neoproc.autoplanilla.domain.enumeration.Estado;
import com.neoproc.autoplanilla.domain.enumeration.TipoCedulaCompany;
import com.neoproc.autoplanilla.repository.CompanyRepository;
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
 * Integration tests for the {@link CompanyResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CompanyResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final TipoCedulaCompany DEFAULT_TIPO_IDENTIFICACION = TipoCedulaCompany.JURIDICA;
    private static final TipoCedulaCompany UPDATED_TIPO_IDENTIFICACION = TipoCedulaCompany.FISICA;

    private static final String DEFAULT_IDENTIFICACION = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICACION = "BBBBBBBBBB";

    private static final String DEFAULT_CORREO_ELECTRONICO = "AAAAAAAAAA";
    private static final String UPDATED_CORREO_ELECTRONICO = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE_COMERCIAL = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_COMERCIAL = "BBBBBBBBBB";

    private static final Integer DEFAULT_TELEFONO = 1;
    private static final Integer UPDATED_TELEFONO = 2;

    private static final String DEFAULT_PROVINCIA = "AAAAAAAAAA";
    private static final String UPDATED_PROVINCIA = "BBBBBBBBBB";

    private static final String DEFAULT_CANTON = "AAAAAAAAAA";
    private static final String UPDATED_CANTON = "BBBBBBBBBB";

    private static final String DEFAULT_DISTRITO = "AAAAAAAAAA";
    private static final String UPDATED_DISTRITO = "BBBBBBBBBB";

    private static final String DEFAULT_BARRIO = "AAAAAAAAAA";
    private static final String UPDATED_BARRIO = "BBBBBBBBBB";

    private static final String DEFAULT_SENNAS = "AAAAAAAAAA";
    private static final String UPDATED_SENNAS = "BBBBBBBBBB";

    private static final Estado DEFAULT_ESTADO = Estado.ACTIVO;
    private static final Estado UPDATED_ESTADO = Estado.INACTIVO;

    private static final String DEFAULT_WEBSITE = "AAAAAAAAAA";
    private static final String UPDATED_WEBSITE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/companies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CompanyRepository companyRepository;

    @Mock
    private CompanyRepository companyRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompanyMockMvc;

    private Company company;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Company createEntity(EntityManager em) {
        Company company = new Company()
            .nombre(DEFAULT_NOMBRE)
            .tipoIdentificacion(DEFAULT_TIPO_IDENTIFICACION)
            .identificacion(DEFAULT_IDENTIFICACION)
            .correoElectronico(DEFAULT_CORREO_ELECTRONICO)
            .nombreComercial(DEFAULT_NOMBRE_COMERCIAL)
            .telefono(DEFAULT_TELEFONO)
            .provincia(DEFAULT_PROVINCIA)
            .canton(DEFAULT_CANTON)
            .distrito(DEFAULT_DISTRITO)
            .barrio(DEFAULT_BARRIO)
            .sennas(DEFAULT_SENNAS)
            .estado(DEFAULT_ESTADO)
            .website(DEFAULT_WEBSITE);
        return company;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Company createUpdatedEntity(EntityManager em) {
        Company company = new Company()
            .nombre(UPDATED_NOMBRE)
            .tipoIdentificacion(UPDATED_TIPO_IDENTIFICACION)
            .identificacion(UPDATED_IDENTIFICACION)
            .correoElectronico(UPDATED_CORREO_ELECTRONICO)
            .nombreComercial(UPDATED_NOMBRE_COMERCIAL)
            .telefono(UPDATED_TELEFONO)
            .provincia(UPDATED_PROVINCIA)
            .canton(UPDATED_CANTON)
            .distrito(UPDATED_DISTRITO)
            .barrio(UPDATED_BARRIO)
            .sennas(UPDATED_SENNAS)
            .estado(UPDATED_ESTADO)
            .website(UPDATED_WEBSITE);
        return company;
    }

    @BeforeEach
    public void initTest() {
        company = createEntity(em);
    }

    @Test
    @Transactional
    void createCompany() throws Exception {
        int databaseSizeBeforeCreate = companyRepository.findAll().size();
        // Create the Company
        restCompanyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(company)))
            .andExpect(status().isCreated());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeCreate + 1);
        Company testCompany = companyList.get(companyList.size() - 1);
        assertThat(testCompany.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testCompany.getTipoIdentificacion()).isEqualTo(DEFAULT_TIPO_IDENTIFICACION);
        assertThat(testCompany.getIdentificacion()).isEqualTo(DEFAULT_IDENTIFICACION);
        assertThat(testCompany.getCorreoElectronico()).isEqualTo(DEFAULT_CORREO_ELECTRONICO);
        assertThat(testCompany.getNombreComercial()).isEqualTo(DEFAULT_NOMBRE_COMERCIAL);
        assertThat(testCompany.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testCompany.getProvincia()).isEqualTo(DEFAULT_PROVINCIA);
        assertThat(testCompany.getCanton()).isEqualTo(DEFAULT_CANTON);
        assertThat(testCompany.getDistrito()).isEqualTo(DEFAULT_DISTRITO);
        assertThat(testCompany.getBarrio()).isEqualTo(DEFAULT_BARRIO);
        assertThat(testCompany.getSennas()).isEqualTo(DEFAULT_SENNAS);
        assertThat(testCompany.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testCompany.getWebsite()).isEqualTo(DEFAULT_WEBSITE);
    }

    @Test
    @Transactional
    void createCompanyWithExistingId() throws Exception {
        // Create the Company with an existing ID
        company.setId(1L);

        int databaseSizeBeforeCreate = companyRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(company)))
            .andExpect(status().isBadRequest());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyRepository.findAll().size();
        // set the field null
        company.setNombre(null);

        // Create the Company, which fails.

        restCompanyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(company)))
            .andExpect(status().isBadRequest());

        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTipoIdentificacionIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyRepository.findAll().size();
        // set the field null
        company.setTipoIdentificacion(null);

        // Create the Company, which fails.

        restCompanyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(company)))
            .andExpect(status().isBadRequest());

        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIdentificacionIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyRepository.findAll().size();
        // set the field null
        company.setIdentificacion(null);

        // Create the Company, which fails.

        restCompanyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(company)))
            .andExpect(status().isBadRequest());

        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCorreoElectronicoIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyRepository.findAll().size();
        // set the field null
        company.setCorreoElectronico(null);

        // Create the Company, which fails.

        restCompanyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(company)))
            .andExpect(status().isBadRequest());

        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEstadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyRepository.findAll().size();
        // set the field null
        company.setEstado(null);

        // Create the Company, which fails.

        restCompanyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(company)))
            .andExpect(status().isBadRequest());

        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCompanies() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList
        restCompanyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(company.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].tipoIdentificacion").value(hasItem(DEFAULT_TIPO_IDENTIFICACION.toString())))
            .andExpect(jsonPath("$.[*].identificacion").value(hasItem(DEFAULT_IDENTIFICACION)))
            .andExpect(jsonPath("$.[*].correoElectronico").value(hasItem(DEFAULT_CORREO_ELECTRONICO)))
            .andExpect(jsonPath("$.[*].nombreComercial").value(hasItem(DEFAULT_NOMBRE_COMERCIAL)))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)))
            .andExpect(jsonPath("$.[*].provincia").value(hasItem(DEFAULT_PROVINCIA)))
            .andExpect(jsonPath("$.[*].canton").value(hasItem(DEFAULT_CANTON)))
            .andExpect(jsonPath("$.[*].distrito").value(hasItem(DEFAULT_DISTRITO)))
            .andExpect(jsonPath("$.[*].barrio").value(hasItem(DEFAULT_BARRIO)))
            .andExpect(jsonPath("$.[*].sennas").value(hasItem(DEFAULT_SENNAS)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())))
            .andExpect(jsonPath("$.[*].website").value(hasItem(DEFAULT_WEBSITE)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCompaniesWithEagerRelationshipsIsEnabled() throws Exception {
        when(companyRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCompanyMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(companyRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCompaniesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(companyRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCompanyMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(companyRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getCompany() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get the company
        restCompanyMockMvc
            .perform(get(ENTITY_API_URL_ID, company.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(company.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.tipoIdentificacion").value(DEFAULT_TIPO_IDENTIFICACION.toString()))
            .andExpect(jsonPath("$.identificacion").value(DEFAULT_IDENTIFICACION))
            .andExpect(jsonPath("$.correoElectronico").value(DEFAULT_CORREO_ELECTRONICO))
            .andExpect(jsonPath("$.nombreComercial").value(DEFAULT_NOMBRE_COMERCIAL))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO))
            .andExpect(jsonPath("$.provincia").value(DEFAULT_PROVINCIA))
            .andExpect(jsonPath("$.canton").value(DEFAULT_CANTON))
            .andExpect(jsonPath("$.distrito").value(DEFAULT_DISTRITO))
            .andExpect(jsonPath("$.barrio").value(DEFAULT_BARRIO))
            .andExpect(jsonPath("$.sennas").value(DEFAULT_SENNAS))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()))
            .andExpect(jsonPath("$.website").value(DEFAULT_WEBSITE));
    }

    @Test
    @Transactional
    void getNonExistingCompany() throws Exception {
        // Get the company
        restCompanyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCompany() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        int databaseSizeBeforeUpdate = companyRepository.findAll().size();

        // Update the company
        Company updatedCompany = companyRepository.findById(company.getId()).get();
        // Disconnect from session so that the updates on updatedCompany are not directly saved in db
        em.detach(updatedCompany);
        updatedCompany
            .nombre(UPDATED_NOMBRE)
            .tipoIdentificacion(UPDATED_TIPO_IDENTIFICACION)
            .identificacion(UPDATED_IDENTIFICACION)
            .correoElectronico(UPDATED_CORREO_ELECTRONICO)
            .nombreComercial(UPDATED_NOMBRE_COMERCIAL)
            .telefono(UPDATED_TELEFONO)
            .provincia(UPDATED_PROVINCIA)
            .canton(UPDATED_CANTON)
            .distrito(UPDATED_DISTRITO)
            .barrio(UPDATED_BARRIO)
            .sennas(UPDATED_SENNAS)
            .estado(UPDATED_ESTADO)
            .website(UPDATED_WEBSITE);

        restCompanyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCompany.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCompany))
            )
            .andExpect(status().isOk());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeUpdate);
        Company testCompany = companyList.get(companyList.size() - 1);
        assertThat(testCompany.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testCompany.getTipoIdentificacion()).isEqualTo(UPDATED_TIPO_IDENTIFICACION);
        assertThat(testCompany.getIdentificacion()).isEqualTo(UPDATED_IDENTIFICACION);
        assertThat(testCompany.getCorreoElectronico()).isEqualTo(UPDATED_CORREO_ELECTRONICO);
        assertThat(testCompany.getNombreComercial()).isEqualTo(UPDATED_NOMBRE_COMERCIAL);
        assertThat(testCompany.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testCompany.getProvincia()).isEqualTo(UPDATED_PROVINCIA);
        assertThat(testCompany.getCanton()).isEqualTo(UPDATED_CANTON);
        assertThat(testCompany.getDistrito()).isEqualTo(UPDATED_DISTRITO);
        assertThat(testCompany.getBarrio()).isEqualTo(UPDATED_BARRIO);
        assertThat(testCompany.getSennas()).isEqualTo(UPDATED_SENNAS);
        assertThat(testCompany.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testCompany.getWebsite()).isEqualTo(UPDATED_WEBSITE);
    }

    @Test
    @Transactional
    void putNonExistingCompany() throws Exception {
        int databaseSizeBeforeUpdate = companyRepository.findAll().size();
        company.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, company.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(company))
            )
            .andExpect(status().isBadRequest());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCompany() throws Exception {
        int databaseSizeBeforeUpdate = companyRepository.findAll().size();
        company.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(company))
            )
            .andExpect(status().isBadRequest());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCompany() throws Exception {
        int databaseSizeBeforeUpdate = companyRepository.findAll().size();
        company.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(company)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCompanyWithPatch() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        int databaseSizeBeforeUpdate = companyRepository.findAll().size();

        // Update the company using partial update
        Company partialUpdatedCompany = new Company();
        partialUpdatedCompany.setId(company.getId());

        partialUpdatedCompany
            .tipoIdentificacion(UPDATED_TIPO_IDENTIFICACION)
            .identificacion(UPDATED_IDENTIFICACION)
            .nombreComercial(UPDATED_NOMBRE_COMERCIAL)
            .provincia(UPDATED_PROVINCIA)
            .barrio(UPDATED_BARRIO)
            .sennas(UPDATED_SENNAS)
            .website(UPDATED_WEBSITE);

        restCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompany.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompany))
            )
            .andExpect(status().isOk());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeUpdate);
        Company testCompany = companyList.get(companyList.size() - 1);
        assertThat(testCompany.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testCompany.getTipoIdentificacion()).isEqualTo(UPDATED_TIPO_IDENTIFICACION);
        assertThat(testCompany.getIdentificacion()).isEqualTo(UPDATED_IDENTIFICACION);
        assertThat(testCompany.getCorreoElectronico()).isEqualTo(DEFAULT_CORREO_ELECTRONICO);
        assertThat(testCompany.getNombreComercial()).isEqualTo(UPDATED_NOMBRE_COMERCIAL);
        assertThat(testCompany.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testCompany.getProvincia()).isEqualTo(UPDATED_PROVINCIA);
        assertThat(testCompany.getCanton()).isEqualTo(DEFAULT_CANTON);
        assertThat(testCompany.getDistrito()).isEqualTo(DEFAULT_DISTRITO);
        assertThat(testCompany.getBarrio()).isEqualTo(UPDATED_BARRIO);
        assertThat(testCompany.getSennas()).isEqualTo(UPDATED_SENNAS);
        assertThat(testCompany.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testCompany.getWebsite()).isEqualTo(UPDATED_WEBSITE);
    }

    @Test
    @Transactional
    void fullUpdateCompanyWithPatch() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        int databaseSizeBeforeUpdate = companyRepository.findAll().size();

        // Update the company using partial update
        Company partialUpdatedCompany = new Company();
        partialUpdatedCompany.setId(company.getId());

        partialUpdatedCompany
            .nombre(UPDATED_NOMBRE)
            .tipoIdentificacion(UPDATED_TIPO_IDENTIFICACION)
            .identificacion(UPDATED_IDENTIFICACION)
            .correoElectronico(UPDATED_CORREO_ELECTRONICO)
            .nombreComercial(UPDATED_NOMBRE_COMERCIAL)
            .telefono(UPDATED_TELEFONO)
            .provincia(UPDATED_PROVINCIA)
            .canton(UPDATED_CANTON)
            .distrito(UPDATED_DISTRITO)
            .barrio(UPDATED_BARRIO)
            .sennas(UPDATED_SENNAS)
            .estado(UPDATED_ESTADO)
            .website(UPDATED_WEBSITE);

        restCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompany.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompany))
            )
            .andExpect(status().isOk());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeUpdate);
        Company testCompany = companyList.get(companyList.size() - 1);
        assertThat(testCompany.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testCompany.getTipoIdentificacion()).isEqualTo(UPDATED_TIPO_IDENTIFICACION);
        assertThat(testCompany.getIdentificacion()).isEqualTo(UPDATED_IDENTIFICACION);
        assertThat(testCompany.getCorreoElectronico()).isEqualTo(UPDATED_CORREO_ELECTRONICO);
        assertThat(testCompany.getNombreComercial()).isEqualTo(UPDATED_NOMBRE_COMERCIAL);
        assertThat(testCompany.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testCompany.getProvincia()).isEqualTo(UPDATED_PROVINCIA);
        assertThat(testCompany.getCanton()).isEqualTo(UPDATED_CANTON);
        assertThat(testCompany.getDistrito()).isEqualTo(UPDATED_DISTRITO);
        assertThat(testCompany.getBarrio()).isEqualTo(UPDATED_BARRIO);
        assertThat(testCompany.getSennas()).isEqualTo(UPDATED_SENNAS);
        assertThat(testCompany.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testCompany.getWebsite()).isEqualTo(UPDATED_WEBSITE);
    }

    @Test
    @Transactional
    void patchNonExistingCompany() throws Exception {
        int databaseSizeBeforeUpdate = companyRepository.findAll().size();
        company.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, company.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(company))
            )
            .andExpect(status().isBadRequest());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCompany() throws Exception {
        int databaseSizeBeforeUpdate = companyRepository.findAll().size();
        company.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(company))
            )
            .andExpect(status().isBadRequest());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCompany() throws Exception {
        int databaseSizeBeforeUpdate = companyRepository.findAll().size();
        company.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(company)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCompany() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        int databaseSizeBeforeDelete = companyRepository.findAll().size();

        // Delete the company
        restCompanyMockMvc
            .perform(delete(ENTITY_API_URL_ID, company.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
