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
import java.util.HashSet;
import java.util.Set;

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

    @Column(name = "name", length = 50, nullable = false)
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
    @Size(min = 3, max = 30, message = "{type.message}")
    @Pattern(regexp = "[A-Za-z0-9ĘÓĄŚŁŻŹĆŃęóąśłżźćń]{3,30}$", message = "{eventPattern.message}")
    @JsonProperty("TYPE")
    @ApiModelProperty(notes = "Event's type", required = true, position = 4)
    private String type;

    @Column(name = "start_date", nullable = false)
    @NotNull(message = "{null.message}")
    //@ValidDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @JsonProperty("START_DATE")
    @ApiModelProperty(notes = "Event's start date in format dd/mm/yyyy", required = true, position = 5)
    private LocalDate startDate;

    @Column(name = "start_hour", nullable = false)
    @NotNull(message = "{null.message}")
    @JsonFormat(pattern = "HH:mm")
    @JsonProperty("START_HOUR")
    @ApiModelProperty(notes = "Event's start hour in format KK:mm", required = true, position = 6)
    private LocalTime startHour;

    @Column(name = "end_hour", nullable = false)
    @NotNull(message = "{null.message}")
    @JsonFormat(pattern = "HH:mm")
    @JsonProperty("END_HOUR")
    @ApiModelProperty(notes = "Event's end hour", required = true, position = 7)
    private LocalTime endHour;

    @Column(name = "is_private", nullable = false)
    @NotNull(message = "{null.message}")
    @JsonProperty("IS_PRIVATE")
    @ApiModelProperty(notes = "Flag indicating if event is private or not, default: false", required = true, position = 8)
    private boolean isPrivate;

    @Column(name = "is_important")
    @NotNull(message = "{null.message}")
    @JsonProperty("IS_IMPORTANT")
    @ApiModelProperty(notes = "Flag indicating is event is important for given user or not, default: false", required = true, position = 9)
    private boolean isImportant;

    @Column(name = "is_accepted")
    @NotNull(message = "{null.message}")
    @JsonProperty("IS_ACCEPTED")
    @ApiModelProperty(notes = "Flag indicating if event is accepted by admin or not, default: false", required = true, position = 10)
    private boolean isAccepted;

    @Column(name = "is_archive", nullable = false)
    @NotNull(message = "{null.message}")
    @JsonProperty("IS_ARCHIVE")
    @ApiModelProperty(notes = "Flag indicating if event is finished or not, default: false", required = true, position = 11)
    private boolean isArchive;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk2_users_table"))
    @JsonIgnore
    private PlanitUser user;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinTable(name = "events_tags",
                joinColumns = {@JoinColumn(name = "event_id")},
                inverseJoinColumns = {@JoinColumn(name = "tag_id")})
    private Set<Tag> tags = new HashSet<>();

    protected Event() { }

    public Event(String name, String place, String type, LocalDate startDate, LocalTime startHour, LocalTime endHour,
                 boolean isPrivate, boolean isImportant, boolean isAccepted ,boolean isArchive) {
        this.name = name;
        this.place = place;
        this.type = type;
        this.startDate = startDate;
        this.startHour = startHour;
        this.endHour = endHour;
        this.isPrivate = isPrivate;
        this.isImportant = isImportant;
        this.isAccepted = isAccepted;
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

    public void setIsPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public boolean getIsPrivate() {
        return isPrivate;
    }

    public void setIsImportant(boolean isImportant) {
        this.isImportant = isImportant;
    }

    public boolean getIsImportant() {
        return isImportant;
    }

    public void setIsAccepted(boolean isAccepted) {
        this.isAccepted = isAccepted;
    }

    public boolean getIsAccepted() {
        return isAccepted;
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

    @JsonIgnore
    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    @JsonProperty("TAGS")
    public Set<Tag> getTags() {
        return tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Event)) return false;

        Event event = (Event) o;

        if (isPrivate != event.isPrivate) return false;
        if (isImportant != event.isImportant) return false;
        if (isAccepted != event.isAccepted) return false;
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
        result = 31 * result + (isPrivate ? 1 : 0);
        result = 31 * result + (isImportant ? 1 : 0);
        result = 31 * result + (isAccepted ? 1 : 0);
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
                ", isPrivate=" + isPrivate +
                ", isImportant=" + isImportant +
                ", isAccepted=" + isAccepted +
                ", isArchive=" + isArchive +
                '}';
    }
}
