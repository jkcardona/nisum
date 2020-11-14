package com.nisum.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.nisum.domain.PhoneUser;
import com.nisum.repository.PhoneUserRepository;
import com.nisum.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.nisum.domain.PhoneUser}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PhoneUserResource {

    private final Logger log = LoggerFactory.getLogger(PhoneUserResource.class);

    private static final String ENTITY_NAME = "phoneUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PhoneUserRepository phoneUserRepository;

    public PhoneUserResource(PhoneUserRepository phoneUserRepository) {
        this.phoneUserRepository = phoneUserRepository;
    }

    /**
     * {@code POST  /phone-users} : Create a new phoneUser.
     *
     * @param phoneUser the phoneUser to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new phoneUser, or with status {@code 400 (Bad Request)} if the phoneUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/phone-users")
    public ResponseEntity<PhoneUser> createPhoneUser(@Valid @RequestBody PhoneUser phoneUser) throws URISyntaxException {
        log.debug("REST request to save PhoneUser : {}", phoneUser);
        if (phoneUser.getId() != null) {
            throw new BadRequestAlertException("A new phoneUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PhoneUser result = phoneUserRepository.save(phoneUser);
        return ResponseEntity.created(new URI("/api/phone-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /phone-users} : Updates an existing phoneUser.
     *
     * @param phoneUser the phoneUser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated phoneUser,
     * or with status {@code 400 (Bad Request)} if the phoneUser is not valid,
     * or with status {@code 500 (Internal Server Error)} if the phoneUser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/phone-users")
    public ResponseEntity<PhoneUser> updatePhoneUser(@Valid @RequestBody PhoneUser phoneUser) throws URISyntaxException {
        log.debug("REST request to update PhoneUser : {}", phoneUser);
        if (phoneUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PhoneUser result = phoneUserRepository.save(phoneUser);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, phoneUser.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /phone-users} : get all the phoneUsers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of phoneUsers in body.
     */
    @GetMapping("/phone-users")
    public ResponseEntity<List<PhoneUser>> getAllPhoneUsers(Pageable pageable) {
        log.debug("REST request to get a page of PhoneUsers");
        Page<PhoneUser> page = phoneUserRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /phone-users/:id} : get the "id" phoneUser.
     *
     * @param id the id of the phoneUser to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the phoneUser, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/phone-users/{id}")
    public ResponseEntity<PhoneUser> getPhoneUser(@PathVariable Long id) {
        log.debug("REST request to get PhoneUser : {}", id);
        Optional<PhoneUser> phoneUser = phoneUserRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(phoneUser);
    }

    /**
     * {@code DELETE  /phone-users/:id} : delete the "id" phoneUser.
     *
     * @param id the id of the phoneUser to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/phone-users/{id}")
    public ResponseEntity<Void> deletePhoneUser(@PathVariable Long id) {
        log.debug("REST request to delete PhoneUser : {}", id);
        phoneUserRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
