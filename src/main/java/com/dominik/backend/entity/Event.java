package com.dominik.backend.entity;

import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by dominik on 20.06.2017.
 */

@Table(name = "events")
public class Event {

    private Long id;
    private String name;
    private String place;
    private String type;
    private LocalDate startDate;
    private LocalTime startHour;
    private LocalTime endHour;
    private boolean isArchive;
    private PlanitUser user;

    protected Event() { }

    public Event(String name, String place, String type, LocalDate startDate, LocalTime startHour, LocalTime endHour, boolean isArchive) {
        this.name = name;
        this.place = place;
        this.type = type;
        this.startDate = startDate;
        this.startHour = startHour;
        this.endHour = endHour;
        this.isArchive = isArchive;
        user = null;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPlace() {
        return place;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartHour(LocalTime startHour) {
        this.startHour = startHour;
    }

    public LocalTime getStartHour() {
        return startHour;
    }

    public void setEndHour(LocalTime endHour) {
        this.endHour = endHour;
    }

    public LocalTime getEndHour() {
        return endHour;
    }

    public void setIsArchive(boolean isArchive) {
        this.isArchive = isArchive;
    }

    public boolean getIsArchive() {
        return isArchive;
    }

    public void setUser(PlanitUser user) {
        this.user = user;
    }

    public PlanitUser getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Event)) return false;

        Event event = (Event) o;

        if (isArchive != event.isArchive) return false;
        if (!getName().equals(event.getName())) return false;
        if (!getPlace().equals(event.getPlace())) return false;
        if (!getType().equals(event.getType())) return false;
        if (!getStartDate().equals(event.getStartDate())) return false;
        if (!getStartHour().equals(event.getStartHour())) return false;
        return getEndHour().equals(event.getEndHour());
    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + getPlace().hashCode();
        result = 31 * result + getType().hashCode();
        result = 31 * result + getStartDate().hashCode();
        result = 31 * result + getStartHour().hashCode();
        result = 31 * result + getEndHour().hashCode();
        result = 31 * result + (isArchive ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", place='" + place + '\'' +
                ", type='" + type + '\'' +
                ", startDate=" + startDate +
                ", startHour=" + startHour +
                ", endHour=" + endHour +
                ", isArchive=" + isArchive +
                '}';
    }
}
