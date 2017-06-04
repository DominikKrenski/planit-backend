package com.dominik.backend.util;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Pattern;

/**
 * Created by dominik on 04.06.2017.
 */
public class Email {

    @JsonProperty("RECEIVER")
    private String receiverEmailAddress;

    @Pattern(regexp = "^[A-Za-zĘÓĄŚŁŻŹĆŃęóąśłżźćń.,()_\\- ]+$", message = "{subjectPattern.message}")
    @JsonProperty("SUBJECT")
    private String subject;

    @Pattern(regexp = "^[A-Za-zĘÓĄŚŁŻŹĆŃęóąśłżźćń.,()\\[\\]{}_\\- ]+$", message = "{bodyPattern.message}")
    @JsonProperty("MESSAGE")
    private String message;

    public Email() {}

    public Email(String receiverEmailAddress, String subject, String message) {
        this.receiverEmailAddress = receiverEmailAddress;
        this.subject = subject;
        this.message = message;
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
