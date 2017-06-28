package com.dominik.backend.util;

import com.dominik.backend.validator.ValidDate;
import com.dominik.backend.validator.ValidHours;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by dominik on 28.06.17.
 */

@ValidHours(first = "startHour", second = "endHour")
public class UpdateEvent {

    @NotNull(message = "{null.message}")
    @JsonProperty("NAME")
    @ApiModelProperty(notes = "Event's name", required = true, position = 1)
    private String name;

    @NotNull(message = "{null.message}")
    @Size(min = 5, max = 255, message = "{planeName.message}")
    @Pattern(regexp = "^[A-Za-z0-9ĘÓĄŚŁŻŹĆŃęóąśłżźćń_ ]{5,255}$", message = "{placeNamePattern.message}")
    @JsonProperty("PLACE")
    @ApiModelProperty(notes = "Event's place address (allowed characters are: letters, digits, spaces and _)," +
            "minLength: 5 maxLength: 255", required = true, position = 2)
    private String place;

    @NotNull(message = "{null.message}")
    @Size(min = 3, max = 30, message = "{type.message}")
    @Pattern(regexp = "[A-Za-z0-9ĘÓĄŚŁŻŹĆŃęóąśłżźćń]{5,30}$", message = "{eventPattern.message}")
    @JsonProperty("TYPE")
    @ApiModelProperty(notes = "Event's type", required = true, position = 3)
    private String type;

    @NotNull(message = "{null.message}")
    @ValidDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @JsonProperty("START_DATE")
    @ApiModelProperty(notes = "Event's start date in format dd/mm/yyyy", required = true, position = 4)
    private LocalDate startDate;

    @NotNull(message = "{null.message}")
    @JsonFormat(pattern = "HH:mm")
    @JsonProperty("START_HOUR")
    @ApiModelProperty(notes = "Event's start hour in format KK:mm", required = true, position = 5)
    private LocalTime startHour;

    @NotNull(message = "{null.message}")
    @JsonFormat(pattern = "HH:mm")
    @JsonProperty("END_HOUR")
    @ApiModelProperty(notes = "Event's end hour", required = true, position = 6)
    private LocalTime endHour;

    @NotNull(message = "{null.message}")
    @JsonProperty("IS_PRIVATE")
    @ApiModelProperty(notes = "Flag indicating if event is private or not, default: false", required = true, position = 7)
    private boolean isPrivate;

    @NotNull(message = "{null.message}")
    @JsonProperty("IS_IMPORTANT")
    @ApiModelProperty(notes = "Flag indicating is event is important for given user or not, default: false", required = true, position = 8)
    private boolean isImportant;


    protected UpdateEvent() {}

    public UpdateEvent(String name, String place, String type, LocalDate startDate, LocalTime startHour, LocalTime endHour, boolean isPrivate, boolean isImportant) {
        this.name = name;
        this.place = place;
        this.type = type;
        this.startDate = startDate;
        this.startHour = startHour;
        this.endHour = endHour;
        this.isPrivate = isPrivate;
        this.isImportant = isImportant;
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

    public void setIsImportant(boolean isImportant) {
        this.isImportant = isImportant;
    }

    public boolean getIsImportant() {
        return isImportant;
    }

    public void setIsPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public boolean getIsPrivate() {
        return isPrivate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UpdateEvent)) return false;

        UpdateEvent that = (UpdateEvent) o;

        if (isPrivate != that.isPrivate) return false;
        if (isImportant != that.isImportant) return false;
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
        result = 31 * result + (isPrivate ? 1 : 0);
        result = 31 * result + (isImportant ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UpdateEvent{" +
                "name='" + name + '\'' +
                ", place='" + place + '\'' +
                ", type='" + type + '\'' +
                ", startDate=" + startDate +
                ", startHour=" + startHour +
                ", endHour=" + endHour +
                ", isPrivate=" + isPrivate +
                ", isImportant=" + isImportant +
                '}';
    }
}
