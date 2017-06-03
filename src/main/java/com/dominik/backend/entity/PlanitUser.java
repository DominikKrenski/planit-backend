package com.dominik.backend.entity;

import com.dominik.backend.validator.PasswordMatch;
import com.dominik.backend.validator.ValidYear;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by dominik on 06.04.17.
 */

@Entity
@Table(name = "users")
@PasswordMatch(first = "password", second = "repeatedPassword")
public class PlanitUser {

    @Id
    @SequenceGenerator(sequenceName = "users_id_seq", name = "UserIdSequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UserIdSequence")
    @Column(name = "id")
    @ApiModelProperty(notes = "User's id (empty if request incomes)", required = false, position = 1)
    private Long id;

    @Column(name = "login", length = 20, nullable = false, unique = true)
    @NotNull(message = "{null.message}")
    @Size(min = 3, max = 20, message = "{loginLength.message}")
    @Pattern(regexp = "^[A-Za-z0-9ĘÓĄŚŁŻŹĆŃęóąśłżźćń]+$", message = "loginPattern.message")
    @JsonProperty("LOGIN")
    @ApiModelProperty(notes = "User's login, mininum length: 3, maximum length: 20", required = true, position = 2)
    private String login;

    @Column(name = "password", length = 60, nullable = false)
    @NotNull(message = "{null.message}")
    @Size(min = 5, message = "{passwordLength.message}")
    @ApiModelProperty(notes = "User's password, minimun length: 5", required = true, position = 3)
    private String password;

    @Transient
    @ApiModelProperty(notes = "User's repeated password", required = true, position = 4)
    private String repeatedPassword;

    @Column(name = "name", length = 50, nullable = false)
    @NotNull(message = "{null.message}")
    @Size(min = 3, max = 50, message = "{nameLength.message}")
    @Pattern(regexp = "^[A-Za-zĘÓĄŚŁŻŹĆŃęóąśłżźćń]+$", message = "{namePattern.message}")
    @JsonProperty("NAME")
    @ApiModelProperty(notes = "User's name, minimum length: 3, maximum length: 50", required = true, position = 5)
    private String name;

    @Column(name = "surname", length = 50, nullable = false)
    @NotNull(message = "{null.message}")
    @Size(min = 3, max = 50, message = "{surnameLength.message}")
    @Pattern(regexp = "^[A-Za-zĘÓĄŚŁŻŹĆŃęóąśłżźćń\\-]+$", message = "{surnamePattern.message}")
    @JsonProperty("SURNAME")
    @ApiModelProperty(notes = "User's surname, minimum length: 3, maximum length: 50", required = true, position = 6)
    private String surname;

    @Column(name = "email", length = 255, nullable = false, unique = true)
    @NotNull(message = "{null.message}")
    @Email(message = "{email.message}")
    @JsonProperty("EMAIL")
    @ApiModelProperty(notes = "User's email", required = true, position = 7)
    private String email;

    @Column(name = "user_group", length = 10, nullable = false)
    @NotNull(message = "{null.message}")
    @Size(min = 1, max = 10, message = "{groupLength.message}")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "{groupPattern.message}")
    @JsonProperty("GROUP")
    @ApiModelProperty(notes = "User's group, minimum length: 1, maximum length: 10", required = true, position = 8)
    private String group;

    @Column(name = "index_number", nullable = false, unique = true)
    @NotNull(message = "{null.message}")
    @JsonProperty("INDEX_NUMBER")
    @ApiModelProperty(notes = "User's index number", required = true, position = 9)
    private int indexNumber;

    @Column(name = "start_year", nullable = false)
    @NotNull(message = "{null.message}")
    @ValidYear
    @JsonProperty("START_YEAR")
    @ApiModelProperty(notes = "User's study start year", required = true, position = 10)
    private int startYear;

    @Column(name = "info", length = 2147483647)
    @JsonProperty("INFO")
    @ApiModelProperty(notes = "Info about user", required = true, position = 11)
    private String info;


    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
                joinColumns = {@JoinColumn(name = "user_id")},
                inverseJoinColumns = {@JoinColumn(name = "role_id")})
    //@JsonIgnore
    private Set<Role> roles = new HashSet<>();

    protected PlanitUser() {}

    public PlanitUser(String login, String password, String repeatedPassword, String name, String surname, String email,
                      String group, int indexNumber, int startYear, String info) {
        this.login = login;
        this.password = password;
        this.repeatedPassword = repeatedPassword;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.group = group;
        this.indexNumber = indexNumber;
        this.startYear = startYear;
        this.info = info;
    }

    @JsonIgnore
    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty("ID")
    public Long getId() {
        return id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    @JsonProperty("PASSWORD")
    public void setPassword(String password) {
        this.password = password;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty("REPEATED_PASSWORD")
    public void setRepeatedPassword(String repeatedPassword) {
        this.repeatedPassword = repeatedPassword;
    }

    @JsonIgnore
    public String getRepeatedPassword() {
        return repeatedPassword;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSurname() {
        return surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getGroup() {
        return group;
    }

    public void setIndexNumber(int indexNumber) {
        this.indexNumber = indexNumber;
    }

    public int getIndexNumber() {
        return indexNumber;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public int getStartYear() {
        return startYear;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfo() {
        return  info;
    }

    //@JsonIgnore
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @JsonProperty("ROLES")
    public Set<Role> getRoles() {
        return roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlanitUser)) return false;

        PlanitUser that = (PlanitUser) o;

        if (getIndexNumber() != that.getIndexNumber()) return false;
        if (getStartYear() != that.getStartYear()) return false;
        if (!getLogin().equals(that.getLogin())) return false;
        if (!getPassword().equals(that.getPassword())) return false;
        if (!getName().equals(that.getName())) return false;
        if (!getSurname().equals(that.getSurname())) return false;
        if (!getEmail().equals(that.getEmail())) return false;
        if (!getGroup().equals(that.getGroup())) return false;
        return getInfo().equals(that.getInfo());
    }

    @Override
    public int hashCode() {
        int result = getLogin().hashCode();
        result = 31 * result + getPassword().hashCode();
        result = 31 * result + getName().hashCode();
        result = 31 * result + getSurname().hashCode();
        result = 31 * result + getEmail().hashCode();
        result = 31 * result + getGroup().hashCode();
        result = 31 * result + getIndexNumber();
        result = 31 * result + getStartYear();
        result = 31 * result + getInfo().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "PlanitUser{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", repeatedPassword='" + repeatedPassword + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", group='" + group + '\'' +
                ", indexNumber=" + indexNumber +
                ", startYear=" + startYear +
                ", info='" + info + '\'' +
                ", roles=" + roles +
                '}';
    }
}
