package com.cosmicode.roomie.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.cosmicode.roomie.domain.enumeration.AppointmentState;

/**
 * A DTO for the Appointment entity.
 */
public class AppointmentDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 4, max = 500)
    private String description;

    @NotNull
    private Instant dateTime;

    @NotNull
    private AppointmentState state;

    private Long petitionerId;

    private Long roomId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getDateTime() {
        return dateTime;
    }

    public void setDateTime(Instant dateTime) {
        this.dateTime = dateTime;
    }

    public AppointmentState getState() {
        return state;
    }

    public void setState(AppointmentState state) {
        this.state = state;
    }

    public Long getPetitionerId() {
        return petitionerId;
    }

    public void setPetitionerId(Long roomieId) {
        this.petitionerId = roomieId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AppointmentDTO appointmentDTO = (AppointmentDTO) o;
        if (appointmentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), appointmentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AppointmentDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", dateTime='" + getDateTime() + "'" +
            ", state='" + getState() + "'" +
            ", petitioner=" + getPetitionerId() +
            ", room=" + getRoomId() +
            "}";
    }
}
