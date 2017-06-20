package com.dominik.backend.util;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Klasa, która zostaje zwrócona w odpowiedzi na żądanie pobrania eventu
 * Created by dominik on 20.06.2017.
 */
public class EventResponse {

    private Long id;
    private String name;
    private String place;
    private String type;
    private LocalDate startDate;
    private LocalTime startHour;
    private LocalTime endHour;
    private boolean isArchive;
    private Long userId;

    public EventResponse() {}

    public EventResponse(Long id, String name, String place, String type, LocalDate startDate, LocalTime startHour, LocalTime endHour,
                         boolean isArchive, Long userId)
    {
        this.id = id;
        this.name = name;
        this.place = place;
        this.type = type;
        this.startDate = startDate;
        this.startHour = startHour;
        this.endHour = endHour;
        this.isArchive = isArchive;
        this.userId = userId;
    }

    @JsonIgnore
    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty("ID")
    public Long getId() {
        return id;
    }

    @JsonIgnore
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("NAME")
    public String getName() {
        return name;
    }

    @JsonIgnore
    public void setPlace(String place) {
        this.place = place;
    }

    @JsonProperty("PLACE")
    public String getPlace() {
        return place;
    }

    @JsonIgnore
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("TYPE")
    public String getType() {
        return type;
    }

    @JsonIgnore
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    @JsonProperty("START_DATE")
    @JsonFormat(pattern = "dd/MM/yyyy")
    public LocalDate getStartDate() {
        return startDate;
    }

    @JsonIgnore
    public void setStartHour(LocalTime startHour) {
        this.startHour = startHour;
    }

    @JsonProperty("START_HOUR")
    @JsonFormat(pattern = "HH:mm")
    public LocalTime getStartHour() {
        return startHour;
    }

    @JsonIgnore
    public void setEndHour(LocalTime endHour) {
        this.endHour = endHour;
    }

    @JsonProperty("END_HOUR")
    @JsonFormat(pattern = "HH:mm")
    public LocalTime getEndHour() {
        return endHour;
    }

    @JsonIgnore
    public void setIsArchive(boolean isArchive) {
        this.isArchive = isArchive;
    }

    @JsonProperty("IS_ARCHIVE")
    public boolean getIsArchive() {
        return isArchive;
    }

    @JsonIgnore
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @JsonProperty("USER_ID")
    public Long getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventResponse)) return false;

        EventResponse that = (EventResponse) o;

        if (isArchive != that.isArchive) return false;
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) return false;
        if (getPlace() != null ? !getPlace().equals(that.getPlace()) : that.getPlace() != null) return false;
        if (getType() != null ? !getType().equals(that.getType()) : that.getType() != null) return false;
        if (getStartDate() != null ? !getStartDate().equals(that.getStartDate()) : that.getStartDate() != null)
            return false;
        if (getStartHour() != null ? !getStartHour().equals(that.getStartHour()) : that.getStartHour() != null)
            return false;
        return getEndHour() != null ? getEndHour().equals(that.getEndHour()) : that.getEndHour() == null;
    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (getPlace() != null ? getPlace().hashCode() : 0);
        result = 31 * result + (getType() != null ? getType().hashCode() : 0);
        result = 31 * result + (getStartDate() != null ? getStartDate().hashCode() : 0);
        result = 31 * result + (getStartHour() != null ? getStartHour().hashCode() : 0);
        result = 31 * result + (getEndHour() != null ? getEndHour().hashCode() : 0);
        result = 31 * result + (isArchive ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "EventResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", place='" + place + '\'' +
                ", type='" + type + '\'' +
                ", startDate=" + startDate +
                ", startHour=" + startHour +
                ", endHour=" + endHour +
                ", isValid=" + isArchive +
                ", userId=" + userId +
                '}';
    }
}
