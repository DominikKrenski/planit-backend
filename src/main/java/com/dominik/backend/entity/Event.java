package com.dominik.backend.entity;

import com.dominik.backend.validator.ValidDate;
import com.dominik.backend.validator.ValidHours;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by dominik on 20.06.2017.
 */

@Entity
@Table(name = "events")
@ValidHours(first = "startHour", second = "endHour")
public class Event {

    @Id
    @SequenceGenerator(sequenceName = "events_id_seq", name = "EventIdSequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EventIdSequence")
    @Column(name = "id")
    @ApiModelProperty(notes = "Event's id (empty if request incomes)", required = false, position = 1)
    private Long id;

    @Column(name = "name", length = 50, nullable = false, unique = true)
    @NotNull(message = "{null.message}")
    @JsonProperty("NAME")
    @ApiModelProperty(notes = "Event's name", required = true, position = 2)
    private String name;

    @Column(name = "place", nullable = false)
    @NotNull(message = "{null.message}")
    @Size(min = 5, max = 255, message = "{planeName.message}")
    @Pattern(regexp = "^[A-Za-z0-9ĘÓĄŚŁŻŹĆŃęóąśłżźćń_ ]{5,255}$", message = "{placeNamePattern.message}")
    @JsonProperty("PLACE")
    @ApiModelProperty(notes = "Event's place address (allowed characters are: letters, digits, spaces and _)," +
            "minLength: 5 maxLength: 255", required = true, position = 3)
    private String place;

    @Column(name = "type", length = 30, nullable = false)
    @NotNull(message = "{null.message}")
    @Size(min = 5, max = 30, message = "{type.message}")
    @Pattern(regexp = "[A-Za-z0-9ĘÓĄŚŁŻŹĆŃęóąśłżźćń]{5,30}$", message = "{eventPattern.message}")
    @JsonProperty("TYPE")
    @ApiModelProperty(notes = "Event's type", required = true, position = 4)
    private String type;

    @Column(name = "start_date", nullable = false)
    @NotNull(message = "{null.message}")
    @Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4}$", message = "{startDatePattern.message}")
    @ValidDate
    @JsonFormat(pattern = "dd/mm/yyyy")
    @JsonProperty("START_DATE")
    @ApiModelProperty(notes = "Event's start date in format dd/mm/yyyy", required = true, position = 5)
    private LocalDate startDate;

    @Column(name = "start_hour", nullable = false)
    @NotNull(message = "{null.message}")
    @Pattern(regexp = "^\\d{2}\\d{2}$", message = "{hourPattern.message}")
    @JsonFormat(pattern = "KK:mm")
    @JsonProperty("START_HOUR")
    @ApiModelProperty(notes = "Event's start hour in format KK:mm", required = true, position = 6)
    private LocalTime startHour;

    @Column(name = "end_hour", nullable = false)
    @NotNull(message = "{null.message}")
    @Pattern(regexp = "^\\d{2}\\d{2}$", message = "{hourPattern.message}")
    @JsonFormat(pattern = "KK:mm")
    @JsonProperty("END_HOUR")
    @ApiModelProperty(notes = "Event's end hour", required = true, position = 7)
    private LocalTime endHour;

    @Column(name = "is_archive", nullable = false)
    @NotNull(message = "{null.message}")
    @Pattern(regexp = "^(true)|(false)$", message = "{archive.message}")
    @JsonProperty("IS_ARCHIVE")
    @ApiModelProperty(notes = "Flag indicating if event is finished or not, default: false", required = true, position = 8)
    private boolean isArchive;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk2_users_table"))
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

    @JsonIgnore
    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty("ID")
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

    @JsonIgnore
    public void setUser(PlanitUser user) {
        this.user = user;
    }

    @JsonProperty("USER")
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
