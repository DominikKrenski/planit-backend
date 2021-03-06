package com.dominik.backend.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by dominik on 06.04.17.
 */

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @SequenceGenerator(sequenceName = "roles_id_seq", name = "RoleIdSequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RoleIdSequence")
    @Column(name = "id")
    @JsonProperty("ID")
    @ApiModelProperty(notes = "Role's id", required = false, position = 1)
    private Long id;

    @Column(name = "name", length = 30, nullable = false)
    @NotNull(message = "{null.message}")
    @Pattern(regexp = "^[A-Z_]+$", message = "{groupNamePattern.message}")
    @JsonProperty("NAME")
    @ApiModelProperty(notes = "Role's name", required = true, position = 2)
    private String name;

    protected Role() {}

    public Role(String name) {
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;

        Role role = (Role) o;

        return getName().equals(role.getName());
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }

    @Override
    public String toString() {
        return name;
    }
}
