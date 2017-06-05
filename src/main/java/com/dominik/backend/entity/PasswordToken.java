package com.dominik.backend.entity;

import javax.persistence.*;

/**
 * Created by dominik on 05.06.2017.
 */

@Entity
@Table(name = "tokens")
public class PasswordToken {

    @Id
    @SequenceGenerator(sequenceName = "tokens_id_seq", name = "TokenIdSequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TokenIdSequence")
    @Column(name = "id")
    private Long id;

    @Column(name = "token", length = 36, nullable = false)
    private String token;

    @OneToOne(targetEntity = PlanitUser.class, optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private PlanitUser user;

    protected PasswordToken() {}

    public PasswordToken(String token, PlanitUser user) {
        this.token = token;
        this.user = user;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setUser(PlanitUser user) {
        this.user = user;
    }

    public PlanitUser getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PasswordToken)) return false;

        PasswordToken that = (PasswordToken) o;

        if (!getToken().equals(that.getToken())) return false;
        return getUser().equals(that.getUser());
    }

    @Override
    public int hashCode() {
        int result = getToken().hashCode();
        result = 31 * result + getUser().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "PasswordToken{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", user=" + user +
                '}';
    }
}
