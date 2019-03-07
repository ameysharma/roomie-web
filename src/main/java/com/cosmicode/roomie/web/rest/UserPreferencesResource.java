package com.cosmicode.roomie.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cosmicode.roomie.service.UserPreferencesService;
import com.cosmicode.roomie.web.rest.errors.BadRequestAlertException;
import com.cosmicode.roomie.web.rest.util.HeaderUtil;
import com.cosmicode.roomie.web.rest.util.PaginationUtil;
import com.cosmicode.roomie.service.dto.UserPreferencesDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing UserPreferences.
 */
@RestController
@RequestMapping("/api")
public class UserPreferencesResource {

    private final Logger log = LoggerFactory.getLogger(UserPreferencesResource.class);

    private static final String ENTITY_NAME = "userPreferences";

    private final UserPreferencesService userPreferencesService;

    public UserPreferencesResource(UserPreferencesService userPreferencesService) {
        this.userPreferencesService = userPreferencesService;
    }

    /**
     * POST  /user-preferences : Create a new userPreferences.
     *
     * @param userPreferencesDTO the userPreferencesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userPreferencesDTO, or with status 400 (Bad Request) if the userPreferences has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-preferences")
    @Timed
    public ResponseEntity<UserPreferencesDTO> createUserPreferences(@RequestBody UserPreferencesDTO userPreferencesDTO) throws URISyntaxException {
        log.debug("REST request to save UserPreferences : {}", userPreferencesDTO);
        if (userPreferencesDTO.getId() != null) {
            throw new BadRequestAlertException("A new userPreferences cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserPreferencesDTO result = userPreferencesService.save(userPreferencesDTO);
        return ResponseEntity.created(new URI("/api/user-preferences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-preferences : Updates an existing userPreferences.
     *
     * @param userPreferencesDTO the userPreferencesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userPreferencesDTO,
     * or with status 400 (Bad Request) if the userPreferencesDTO is not valid,
     * or with status 500 (Internal Server Error) if the userPreferencesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-preferences")
    @Timed
    public ResponseEntity<UserPreferencesDTO> updateUserPreferences(@RequestBody UserPreferencesDTO userPreferencesDTO) throws URISyntaxException {
        log.debug("REST request to update UserPreferences : {}", userPreferencesDTO);
        if (userPreferencesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserPreferencesDTO result = userPreferencesService.save(userPreferencesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userPreferencesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-preferences : get all the userPreferences.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of userPreferences in body
     */
    @GetMapping("/user-preferences")
    @Timed
    public ResponseEntity<List<UserPreferencesDTO>> getAllUserPreferences(Pageable pageable) {
        log.debug("REST request to get a page of UserPreferences");
        Page<UserPreferencesDTO> page = userPreferencesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/user-preferences");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /user-preferences/:id : get the "id" userPreferences.
     *
     * @param id the id of the userPreferencesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userPreferencesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/user-preferences/{id}")
    @Timed
    public ResponseEntity<UserPreferencesDTO> getUserPreferences(@PathVariable Long id) {
        log.debug("REST request to get UserPreferences : {}", id);
        Optional<UserPreferencesDTO> userPreferencesDTO = userPreferencesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userPreferencesDTO);
    }

    /**
     * DELETE  /user-preferences/:id : delete the "id" userPreferences.
     *
     * @param id the id of the userPreferencesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-preferences/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserPreferences(@PathVariable Long id) {
        log.debug("REST request to delete UserPreferences : {}", id);
        userPreferencesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/user-preferences?query=:query : search for the userPreferences corresponding
     * to the query.
     *
     * @param query the query of the userPreferences search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/user-preferences")
    @Timed
    public ResponseEntity<List<UserPreferencesDTO>> searchUserPreferences(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of UserPreferences for query {}", query);
        Page<UserPreferencesDTO> page = userPreferencesService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/user-preferences");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}