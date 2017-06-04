package com.dominik.backend.util;

import com.dominik.backend.validator.PasswordMatch;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by dominik on 04.06.2017.
 */

@PasswordMatch(first = "newPassword", second = "newPasswordConfirm")
public class ChangePassword {

    @NotNull(message = "{null.message}")
    @Size(min = 5, message = "{passwordLength.message}")
    @ApiModelProperty(notes = "User's password, minimun length: 5", required = true, position = 1)
    private String oldPassword;

    @NotNull(message = "{null.message}")
    @Size(min = 5, message = "{passwordLength.message}")
    @ApiModelProperty(notes = "User's password, minimun length: 5", required = true, position = 3)
    private String newPassword;

    @NotNull(message = "{null.message}")
    @Size(min = 5, message = "{passwordLength.message}")
    @ApiModelProperty(notes = "User's password, minimun length: 5", required = true, position = 3)
    private String newPasswordConfirm;

    public ChangePassword() {}

    public ChangePassword(String oldPassword, String newPassword, String newPasswordConfirm) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.newPasswordConfirm = newPasswordConfirm;
    }

    @JsonProperty("OLD_PASSWORD")
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    @JsonProperty("NEW_PASSWORD")
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    @JsonProperty("NEW_PASSWORD_CONFIRM")
    public void setNewPasswordConfirm(String newPasswordConfirm) {
        this.newPasswordConfirm = newPasswordConfirm;
    }

    public String getNewPasswordConfirm() {
        return newPasswordConfirm;
    }

    @Override
    public String toString() {
        return "ChangePassword{" +
                "oldPassword='" + oldPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                ", newPasswordConfirm='" + newPasswordConfirm + '\'' +
                '}';
    }
}
