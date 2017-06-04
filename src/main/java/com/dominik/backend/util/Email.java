package com.dominik.backend.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Pattern;

/**
 * Created by dominik on 04.06.2017.
 */
public class Email {

    @JsonProperty("RECEIVER")
    @ApiModelProperty(notes = "Field required if email is sent to one user and must be empty if email is sent to all users", position = 1)
    private String receiverEmailAddress;

    @Pattern(regexp = "^[A-Za-zĘÓĄŚŁŻŹĆŃęóąśłżźćń.,()_\\- ]+$", message = "{subjectPattern.message}")
    @JsonProperty("SUBJECT")
    @ApiModelProperty(notes = "Subject of given email message", required = true, position = 2)
    private String subject;

    @Pattern(regexp = "^[A-Za-zĘÓĄŚŁŻŹĆŃęóąśłżźćń.,()\\[\\]{}_\\- ]+$", message = "{bodyPattern.message}")
    @JsonProperty("MESSAGE")
    @ApiModelProperty(notes = "Email body", required = true, position = 3)
    private String message;

    public Email() {}

    public Email(String subject, String message) {
        this.subject = subject;
        this.message = message;
        this.receiverEmailAddress = null;
    }

    public Email(String receiverEmailAddress, String subject, String message) {
        this(subject, message);
        this.receiverEmailAddress = receiverEmailAddress;
    }

    public void setReceiverEmailAddress(String receiverEmailAddress) {
        this.receiverEmailAddress = receiverEmailAddress;
    }

    public String getReceiverEmailAddress() {
        return receiverEmailAddress;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Email{" +
                "receiverEmailAddress='" + receiverEmailAddress + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
