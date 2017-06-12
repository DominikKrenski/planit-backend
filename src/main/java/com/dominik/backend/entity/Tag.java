package com.dominik.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by dominik on 11.06.2017.
 */

@Entity
@Table(name = "tags")
public class Tag {

    @Id
    @SequenceGenerator(sequenceName = "tags_id_seq", name = "TagIdSequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TagIdSequence")
    @Column(name = "id")
    @ApiModelProperty(notes = "Tag's id (empty if request incomes)", required = false, position = 1)
    private Long id;

    @Column(name = "name", length = 20, nullable = false)
    @NotNull(message = "{null.message}")
    @Size(max = 20, message = "{tagNameLength.message}")
    @Pattern(regexp = "^[A-Za-zĘÓĄŚŁŻŹĆŃęóąśłżźćń]+", message = "{tagNamePattern.message}")
    @JsonProperty("NAME")
    @ApiModelProperty(notes = "Name of the tag", required = true, position = 2)
    private String name;

    @Column(name = "is_accepted", nullable = false)
    @NotNull(message = "{null.message}")
    @JsonProperty("IS_ACCEPTED")
    @ApiModelProperty(notes = "Status of the tag", required = true, position = 3)
    private boolean isAccepted;

    protected Tag() { }

    public Tag(String name, boolean isAccepted) {
        this.id = 0L;
        this.name = name;
        this.isAccepted = isAccepted;
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

    public void setIsAccepted(boolean isAccepted) {
        this.isAccepted = isAccepted;
    }

    public boolean getIsAccepted() {
        return isAccepted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tag)) return false;

        Tag tag = (Tag) o;

        return getName().equals(tag.getName());
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isAccepted=" + isAccepted +
                '}';
    }
}
