package com.dominik.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by dominik on 06.04.17.
 */

@Entity
@Table(name = "users")
public class PlanitUser {

    @Id
    @SequenceGenerator(sequenceName = "users_id_seq", name = "UserIdSequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UserIdSequence")
    @Column(name = "id")
    @JsonProperty("ID")
    @ApiModelProperty(notes = "User's id (empty if request incomes)", required = false, position = 1)
    private Long id;

    @Column(name = "login", length = 20, nullable = false, unique = true)
    @JsonProperty("LOGIN")
    @ApiModelProperty(notes = "User's login, mininum length: 3, maximum length: 20", required = true, position = 2)
    private String login;

    @Column(name = "password", length = 60, nullable = false)
    @JsonProperty("PASSWORD")
    @ApiModelProperty(notes = "User's password, minimun length: 5, maximum length: 20", required = true, position = 3)
    private String password;

    @Transient
    @JsonProperty("REPEATED_PASSWORD")
    @ApiModelProperty(notes = "User's repeated password", required = true, position = 4)
    private String repeatedPassword;

    @Column(name = "name", length = 50, nullable = false)
    @JsonProperty("NAME")
    @ApiModelProperty(notes = "User's name, minimum length: 3, maximum length: 50", required = true, position = 5)
    private String name;

    @Column(name = "surname", length = 50, nullable = false)
    @JsonProperty("SURNAME")
    @ApiModelProperty(notes = "User's surname, minimum length: 3, maximum length: 50", required = true, position = 6)
    private String surname;

    @Column(name = "email", length = 255, nullable = false, unique = true)
    @JsonProperty("EMAIL")
    @ApiModelProperty(notes = "User's email", required = true, position = 7)
    private String email;

    @Column(name = "user_group", length = 10, nullable = false)
    @JsonProperty("GROUP")
    @ApiModelProperty(notes = "User's group, minimum length: 1, maximum length: 10", required = true, position = 8)
    private String group;

    @Column(name = "index_number", nullable = false, unique = true)
    @JsonProperty("INDEX_NUMBER")
    @ApiModelProperty(notes = "User's index number", required = true, position = 9)
    private int indexNumber;

    @Column(name = "start_year", nullable = false)
    @JsonProperty("START_YEAR")
    @ApiModelProperty(notes = "User's study start year", required = true, position = 10)
    private int startYear;

    @Column(name = "info", length = 2147483647)
    @JsonProperty("INFO")
    @ApiModelProperty(notes = "Info about user", required = true, position = 11)
    private String info;


    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "users_roles",
                joinColumns = {@JoinColumn(name = "user_id")},
                inverseJoinColumns = {@JoinColumn(name = "role_id")})
    @JsonIgnore
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

    public Long getId() {
        return id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setRepeatedPassword(String repeatedPassword) {
        this.repeatedPassword = repeatedPassword;
    }

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
}
