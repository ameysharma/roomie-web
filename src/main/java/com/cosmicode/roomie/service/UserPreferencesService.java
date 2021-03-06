package com.cosmicode.roomie.service;

import com.cosmicode.roomie.domain.UserPreferences;
import com.cosmicode.roomie.repository.UserPreferencesRepository;
import com.cosmicode.roomie.service.dto.UserPreferencesDTO;
import com.cosmicode.roomie.service.mapper.UserPreferencesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing UserPreferences.
 */
@Service
@Transactional
public class UserPreferencesService {

    private final Logger log = LoggerFactory.getLogger(UserPreferencesService.class);

    private final UserPreferencesRepository userPreferencesRepository;

    private final UserPreferencesMapper userPreferencesMapper;

    public UserPreferencesService(UserPreferencesRepository userPreferencesRepository, UserPreferencesMapper userPreferencesMapper) {
        this.userPreferencesRepository = userPreferencesRepository;
        this.userPreferencesMapper = userPreferencesMapper;
    }

    /**
     * Save a userPreferences.
     *
     * @param userPreferencesDTO the entity to save
     * @return the persisted entity
     */
    public UserPreferencesDTO save(UserPreferencesDTO userPreferencesDTO) {
        log.debug("Request to save UserPreferences : {}", userPreferencesDTO);
        UserPreferences userPreferences = userPreferencesMapper.toEntity(userPreferencesDTO);
        userPreferences = userPreferencesRepository.save(userPreferences);
        UserPreferencesDTO result = userPreferencesMapper.toDto(userPreferences);
        return result;
    }

    /**
     * Get all the userPreferences.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<UserPreferencesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserPreferences");
        return userPreferencesRepository.findAll(pageable)
            .map(userPreferencesMapper::toDto);
    }


    /**
     * Get one userPreferences by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<UserPreferencesDTO> findOne(Long id) {
        log.debug("Request to get UserPreferences : {}", id);
        return userPreferencesRepository.findById(id)
            .map(userPreferencesMapper::toDto);
    }

    /**
     * Delete the userPreferences by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete UserPreferences : {}", id);
        userPreferencesRepository.deleteById(id);
    }
}
