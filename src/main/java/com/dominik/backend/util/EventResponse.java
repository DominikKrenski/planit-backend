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
    private boolean isAccepted;
    private boolean isArchive;
    private Long userId;

    public EventResponse() {}

    public EventResponse(Long id, String name, String place, String type, LocalDate startDate, LocalTime startHour, LocalTime endHour,
                        boolean isAccepted, boolean isArchive, Long userId)
    {
        this.id = id;
        this.name = name;
        this.place = place;
        this.type = type;
        this.startDate = startDate;
        this.startHour = startHour;
        this.endHour = endHour;
        this.isAccepted = isAccepted;
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
    public void setIsAccepted(boolean isAccepted) {
        this.isAccepted = isAccepted;
    }

    @JsonProperty("IS_ACCEPTED")
    public boolean getIsAccepted() {
        return isAccepted;
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

        if (isAccepted != that.isAccepted) return false;
        if (isArchive != that.isArchive) return false;
        if (!getName().equals(that.getName())) return false;
        if (!getPlace().equals(that.getPlace())) return false;
        if (!getType().equals(that.getType())) return false;
        if (!getStartDate().equals(that.getStartDate())) return false;
        if (!getStartHour().equals(that.getStartHour())) return false;
        return getEndHour().equals(that.getEndHour());
    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + getPlace().hashCode();
        result = 31 * result + getType().hashCode();
        result = 31 * result + getStartDate().hashCode();
        result = 31 * result + getStartHour().hashCode();
        result = 31 * result + getEndHour().hashCode();
        result = 31 * result + (isAccepted ? 1 : 0);
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
                ", isAccepted=" + isAccepted +
                ", isArchive=" + isArchive +
                ", userId=" + userId +
                '}';
    }
}
