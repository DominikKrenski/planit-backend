package com.dominik.backend.util;

import com.dominik.backend.entity.Role;
import com.dominik.backend.validator.Avatar;
import com.dominik.backend.validator.ValidYear;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by dominik on 03.06.17.
 */

public class UpdateUser {

    @NotNull(message = "{null.message}")
    @Size(min = 3, max = 20, message = "{loginLength.message}")
    @Pattern(regexp = "^[A-Za-z0-9ĘÓĄŚŁŻŹĆŃęóąśłżźćń]+$", message = "loginPattern.message")
    @JsonProperty("LOGIN")
    @ApiModelProperty(notes = "User's login, mininum length: 3, maximum length: 20", required = true, position = 2)
    private String login;

    @NotNull(message = "{null.message}")
    @Size(min = 5, message = "{passwordLength.message}")
    @ApiModelProperty(notes = "User's password, minimun length: 5", required = true, position = 3)
    private String password;


    @NotNull(message = "{null.message}")
    @Size(min = 3, max = 50, message = "{nameLength.message}")
    @Pattern(regexp = "^[A-Za-zĘÓĄŚŁŻŹĆŃęóąśłżźćń]+$", message = "{namePattern.message}")
    @JsonProperty("NAME")
    @ApiModelProperty(notes = "User's name, minimum length: 3, maximum length: 50", required = true, position = 5)
    private String name;

    @NotNull(message = "{null.message}")
    @Size(min = 3, max = 50, message = "{surnameLength.message}")
    @Pattern(regexp = "^[A-Za-zĘÓĄŚŁŻŹĆŃęóąśłżźćń\\-]+$", message = "{surnamePattern.message}")
    @JsonProperty("SURNAME")
    @ApiModelProperty(notes = "User's surname, minimum length: 3, maximum length: 50", required = true, position = 6)
    private String surname;

    @NotNull(message = "{null.message}")
    @Email(message = "{email.message}")
    @JsonProperty("EMAIL")
    @ApiModelProperty(notes = "User's email", required = true, position = 7)
    private String email;

    @NotNull(message = "{null.message}")
    @Size(min = 1, max = 10, message = "{groupLength.message}")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "{groupPattern.message}")
    @JsonProperty("GROUP")
    @ApiModelProperty(notes = "User's group, minimum length: 1, maximum length: 10", required = true, position = 8)
    private String group;

    @NotNull(message = "{null.message}")
    @JsonProperty("INDEX_NUMBER")
    @ApiModelProperty(notes = "User's index number", required = true, position = 9)
    private int indexNumber;

    @NotNull(message = "{null.message}")
    @ValidYear
    @JsonProperty("START_YEAR")
    @ApiModelProperty(notes = "User's study start year", required = true, position = 10)
    private int startYear;

    @JsonProperty("INFO")
    @ApiModelProperty(notes = "Info about user", required = true, position = 11)
    private String info;

    @Avatar
    @JsonProperty("AVATAR")
    @ApiModelProperty(notes = "User's avatar", required = true, position = 12)
    private String avatar;

    @ApiModelProperty(notes = "Roles assigned to user", position = 13)
    private Set<Role> roles = new HashSet<>();


    protected UpdateUser() {}

    public UpdateUser(String login, String name, String surname, String email, String group, int indexNumber, int startYear, String info, String avatar, Set<Role> roles) {
        this.login = login;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.group = group;
        this.indexNumber = indexNumber;
        this.startYear = startYear;
        this.info = info;
        this.avatar = avatar;
        this.roles = roles;
        this.password = "";
    }

    public UpdateUser(String login, String password, String name, String surname, String email,
                      String group, int indexNumber, int startYear, String info, String avatar, Set<Role> roles) {
        this(login, name, surname, email, group, indexNumber, startYear, info, avatar,  roles);
        this.password = password;
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

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }

    @JsonIgnore
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
        if (!(o instanceof UpdateUser)) return false;

        UpdateUser that = (UpdateUser) o;

        if (getIndexNumber() != that.getIndexNumber()) return false;
        if (getStartYear() != that.getStartYear()) return false;
        if (!getLogin().equals(that.getLogin())) return false;
        if (!getName().equals(that.getName())) return false;
        if (!getSurname().equals(that.getSurname())) return false;
        if (!getEmail().equals(that.getEmail())) return false;
        if (!getGroup().equals(that.getGroup())) return false;
        return getInfo().equals(that.getInfo());
    }

    @Override
    public int hashCode() {
        int result = getLogin().hashCode();
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
        return "UpdateUser{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", group='" + group + '\'' +
                ", indexNumber=" + indexNumber +
                ", startYear=" + startYear +
                ", info='" + info + '\'' +
                '}';
    }
}
