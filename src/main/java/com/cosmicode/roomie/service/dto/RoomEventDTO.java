package com.cosmicode.roomie.service.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the RoomEvent entity.
 */
public class RoomEventDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 4, max = 50)
    private String title;

    @Size(min = 4, max = 500)
    private String description;

    @NotNull
    private Boolean isPrivate;

    @NotNull
    private Instant startTime;

    @NotNull
    private Instant endTime;


    private Long roomId;

    private Long organizerId;

    private RoomieDTO organizer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(Boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(Long roomieId) {
        this.organizerId = roomieId;
    }

    public RoomieDTO getOrganizer() {
        return organizer;
    }

    public void setOrganizer(RoomieDTO organizer) {
        this.organizer = organizer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RoomEventDTO roomEventDTO = (RoomEventDTO) o;
        if (roomEventDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), roomEventDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RoomEventDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", isPrivate='" + isIsPrivate() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", room=" + getRoomId() +
            ", organizer=" + getOrganizerId() +
            "}";
    }
}
