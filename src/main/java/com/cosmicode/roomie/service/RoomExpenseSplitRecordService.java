package com.cosmicode.roomie.service;

import com.cosmicode.roomie.domain.RoomExpenseSplitRecord;
import com.cosmicode.roomie.repository.RoomExpenseSplitRecordRepository;
import com.cosmicode.roomie.service.dto.RoomExpenseSplitRecordDTO;
import com.cosmicode.roomie.service.mapper.RoomExpenseSplitRecordMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing RoomExpenseSplitRecord.
 */
@Service
@Transactional
public class RoomExpenseSplitRecordService {

    private final Logger log = LoggerFactory.getLogger(RoomExpenseSplitRecordService.class);

    private final RoomExpenseSplitRecordRepository roomExpenseSplitRecordRepository;

    private final RoomExpenseSplitRecordMapper roomExpenseSplitRecordMapper;

    public RoomExpenseSplitRecordService(RoomExpenseSplitRecordRepository roomExpenseSplitRecordRepository, RoomExpenseSplitRecordMapper roomExpenseSplitRecordMapper) {
        this.roomExpenseSplitRecordRepository = roomExpenseSplitRecordRepository;
        this.roomExpenseSplitRecordMapper = roomExpenseSplitRecordMapper;
    }

    /**
     * Save a roomExpenseSplitRecord.
     *
     * @param roomExpenseSplitRecordDTO the entity to save
     * @return the persisted entity
     */
    public RoomExpenseSplitRecordDTO save(RoomExpenseSplitRecordDTO roomExpenseSplitRecordDTO) {
        log.debug("Request to save RoomExpenseSplitRecord : {}", roomExpenseSplitRecordDTO);
        RoomExpenseSplitRecord roomExpenseSplitRecord = roomExpenseSplitRecordMapper.toEntity(roomExpenseSplitRecordDTO);
        roomExpenseSplitRecord = roomExpenseSplitRecordRepository.save(roomExpenseSplitRecord);
        RoomExpenseSplitRecordDTO result = roomExpenseSplitRecordMapper.toDto(roomExpenseSplitRecord);
        return result;
    }

    /**
     * Get all the roomExpenseSplitRecords.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<RoomExpenseSplitRecordDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RoomExpenseSplitRecords");
        return roomExpenseSplitRecordRepository.findAll(pageable)
            .map(roomExpenseSplitRecordMapper::toDto);
    }


    /**
     * Get one roomExpenseSplitRecord by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<RoomExpenseSplitRecordDTO> findOne(Long id) {
        log.debug("Request to get RoomExpenseSplitRecord : {}", id);
        return roomExpenseSplitRecordRepository.findById(id)
            .map(roomExpenseSplitRecordMapper::toDto);
    }

    /**
     * Delete the roomExpenseSplitRecord by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete RoomExpenseSplitRecord : {}", id);
        roomExpenseSplitRecordRepository.deleteById(id);
    }
}
