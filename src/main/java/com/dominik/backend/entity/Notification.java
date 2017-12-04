package com.dominik.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @SequenceGenerator(sequenceName = "notifications_id_seq", name = "NotificationIdSequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NotificationIdSequence")
    @Column(name = "id")
    @ApiModelProperty(notes = "Notification id (empty if request incomes)", required = false, position = 1)
    private Long id;

    @Column(name = "title")
    @NotNull(message = "{null.message}")
    @Size(min = 3, max = 20, message = "{notificationTitle.message}")
    @JsonProperty("TITLE")
    @ApiModelProperty(notes = "Notification title", required = true, position = 2)
    private String title;

    @Lob
    @Column(name = "notification")
    @NotNull(message = "{null.message}")
    @JsonProperty("NOTIFICATION")
    @ApiModelProperty(notes = "Notification text", required = false, position = 3)
    private String notification;

    protected Notification() {}

    public Notification(String title, String notification) {
        this.title = title;
        this.notification = notification;
    }

    @JsonIgnore
    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty("ID")
    public Long getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getNotification() {
        return notification;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Notification)) return false;

        Notification that = (Notification) o;

        if (!getTitle().equals(that.getTitle())) return false;
        return getNotification().equals(that.getNotification());
    }

    @Override
    public int hashCode() {
        int result = getTitle().hashCode();
        result = 31 * result + getNotification().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", notification='" + notification + '\'' +
                '}';
    }
}
