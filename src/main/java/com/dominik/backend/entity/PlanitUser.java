package com.dominik.backend.entity;

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
    private Long id;

    @Column(name = "login", length = 20, nullable = false, unique = true)
    private String login;

    @Column(name = "password", length = 60, nullable = false)
    private String password;

    @Transient
    private String repeatedPassword;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "surname", length = 50, nullable = false)
    private String surname;

    @Column(name = "email", length = 255, nullable = false, unique = true)
    private String email;

    @Column(name = "user_group", length = 10, nullable = false)
    private String group;

    @Column(name = "index_number", nullable = false, unique = true)
    private int indexNumber;

    @Column(name = "start_year", nullable = false)
    private int startYear;

    @Column(name = "info", length = 2147483647)
    private String info;


    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "users_roles",
                joinColumns = {@JoinColumn(name = "user_id")},
                inverseJoinColumns = {@JoinColumn(name = "role_id")})
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
