package com.nisum.web.rest;

import com.nisum.NisumApp;
import com.nisum.domain.PhoneUser;
import com.nisum.domain.User;
import com.nisum.repository.PhoneUserRepository;
import com.nisum.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.nisum.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PhoneUserResource} REST controller.
 */
@SpringBootTest(classes = NisumApp.class)
public class PhoneUserResourceIT {

    private static final Long DEFAULT_PHONE_NUMBER = 6L;
    private static final Long UPDATED_PHONE_NUMBER = 7L;

    private static final Integer DEFAULT_CITY_CODE = 1;
    private static final Integer UPDATED_CITY_CODE = 2;

    private static final Integer DEFAULT_COUNTRY_CODE = 1;
    private static final Integer UPDATED_COUNTRY_CODE = 2;

    @Autowired
    private PhoneUserRepository phoneUserRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restPhoneUserMockMvc;

    private PhoneUser phoneUser;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PhoneUserResource phoneUserResource = new PhoneUserResource(phoneUserRepository);
        this.restPhoneUserMockMvc = MockMvcBuilders.standaloneSetup(phoneUserResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PhoneUser createEntity(EntityManager em) {
        PhoneUser phoneUser = new PhoneUser()
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .cityCode(DEFAULT_CITY_CODE)
            .countryCode(DEFAULT_COUNTRY_CODE);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        phoneUser.setUser(user);
        return phoneUser;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PhoneUser createUpdatedEntity(EntityManager em) {
        PhoneUser phoneUser = new PhoneUser()
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .cityCode(UPDATED_CITY_CODE)
            .countryCode(UPDATED_COUNTRY_CODE);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        phoneUser.setUser(user);
        return phoneUser;
    }

    @BeforeEach
    public void initTest() {
        phoneUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createPhoneUser() throws Exception {
        int databaseSizeBeforeCreate = phoneUserRepository.findAll().size();

        // Create the PhoneUser
        restPhoneUserMockMvc.perform(post("/api/phone-users")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(phoneUser)))
            .andExpect(status().isCreated());

        // Validate the PhoneUser in the database
        List<PhoneUser> phoneUserList = phoneUserRepository.findAll();
        assertThat(phoneUserList).hasSize(databaseSizeBeforeCreate + 1);
        PhoneUser testPhoneUser = phoneUserList.get(phoneUserList.size() - 1);
        assertThat(testPhoneUser.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testPhoneUser.getCityCode()).isEqualTo(DEFAULT_CITY_CODE);
        assertThat(testPhoneUser.getCountryCode()).isEqualTo(DEFAULT_COUNTRY_CODE);
    }

    @Test
    @Transactional
    public void createPhoneUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = phoneUserRepository.findAll().size();

        // Create the PhoneUser with an existing ID
        phoneUser.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPhoneUserMockMvc.perform(post("/api/phone-users")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(phoneUser)))
            .andExpect(status().isBadRequest());

        // Validate the PhoneUser in the database
        List<PhoneUser> phoneUserList = phoneUserRepository.findAll();
        assertThat(phoneUserList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPhoneNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = phoneUserRepository.findAll().size();
        // set the field null
        phoneUser.setPhoneNumber(null);

        // Create the PhoneUser, which fails.

        restPhoneUserMockMvc.perform(post("/api/phone-users")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(phoneUser)))
            .andExpect(status().isBadRequest());

        List<PhoneUser> phoneUserList = phoneUserRepository.findAll();
        assertThat(phoneUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCityCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = phoneUserRepository.findAll().size();
        // set the field null
        phoneUser.setCityCode(null);

        // Create the PhoneUser, which fails.

        restPhoneUserMockMvc.perform(post("/api/phone-users")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(phoneUser)))
            .andExpect(status().isBadRequest());

        List<PhoneUser> phoneUserList = phoneUserRepository.findAll();
        assertThat(phoneUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCountryCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = phoneUserRepository.findAll().size();
        // set the field null
        phoneUser.setCountryCode(null);

        // Create the PhoneUser, which fails.

        restPhoneUserMockMvc.perform(post("/api/phone-users")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(phoneUser)))
            .andExpect(status().isBadRequest());

        List<PhoneUser> phoneUserList = phoneUserRepository.findAll();
        assertThat(phoneUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPhoneUsers() throws Exception {
        // Initialize the database
        phoneUserRepository.saveAndFlush(phoneUser);

        // Get all the phoneUserList
        restPhoneUserMockMvc.perform(get("/api/phone-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(phoneUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].cityCode").value(hasItem(DEFAULT_CITY_CODE)))
            .andExpect(jsonPath("$.[*].countryCode").value(hasItem(DEFAULT_COUNTRY_CODE)));
    }
    
    @Test
    @Transactional
    public void getPhoneUser() throws Exception {
        // Initialize the database
        phoneUserRepository.saveAndFlush(phoneUser);

        // Get the phoneUser
        restPhoneUserMockMvc.perform(get("/api/phone-users/{id}", phoneUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(phoneUser.getId().intValue()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER.intValue()))
            .andExpect(jsonPath("$.cityCode").value(DEFAULT_CITY_CODE))
            .andExpect(jsonPath("$.countryCode").value(DEFAULT_COUNTRY_CODE));
    }

    @Test
    @Transactional
    public void getNonExistingPhoneUser() throws Exception {
        // Get the phoneUser
        restPhoneUserMockMvc.perform(get("/api/phone-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePhoneUser() throws Exception {
        // Initialize the database
        phoneUserRepository.saveAndFlush(phoneUser);

        int databaseSizeBeforeUpdate = phoneUserRepository.findAll().size();

        // Update the phoneUser
        PhoneUser updatedPhoneUser = phoneUserRepository.findById(phoneUser.getId()).get();
        // Disconnect from session so that the updates on updatedPhoneUser are not directly saved in db
        em.detach(updatedPhoneUser);
        updatedPhoneUser
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .cityCode(UPDATED_CITY_CODE)
            .countryCode(UPDATED_COUNTRY_CODE);

        restPhoneUserMockMvc.perform(put("/api/phone-users")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPhoneUser)))
            .andExpect(status().isOk());

        // Validate the PhoneUser in the database
        List<PhoneUser> phoneUserList = phoneUserRepository.findAll();
        assertThat(phoneUserList).hasSize(databaseSizeBeforeUpdate);
        PhoneUser testPhoneUser = phoneUserList.get(phoneUserList.size() - 1);
        assertThat(testPhoneUser.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testPhoneUser.getCityCode()).isEqualTo(UPDATED_CITY_CODE);
        assertThat(testPhoneUser.getCountryCode()).isEqualTo(UPDATED_COUNTRY_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingPhoneUser() throws Exception {
        int databaseSizeBeforeUpdate = phoneUserRepository.findAll().size();

        // Create the PhoneUser

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPhoneUserMockMvc.perform(put("/api/phone-users")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(phoneUser)))
            .andExpect(status().isBadRequest());

        // Validate the PhoneUser in the database
        List<PhoneUser> phoneUserList = phoneUserRepository.findAll();
        assertThat(phoneUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePhoneUser() throws Exception {
        // Initialize the database
        phoneUserRepository.saveAndFlush(phoneUser);

        int databaseSizeBeforeDelete = phoneUserRepository.findAll().size();

        // Delete the phoneUser
        restPhoneUserMockMvc.perform(delete("/api/phone-users/{id}", phoneUser.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PhoneUser> phoneUserList = phoneUserRepository.findAll();
        assertThat(phoneUserList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
