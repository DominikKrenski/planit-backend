package com.dominik.backend.entity;

import javax.persistence.*;

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
    private Long id;

    @Column(name = "name", length = 20, nullable = false)
    private String name;

    @Column(name = "is_accepted", nullable = false)
    private boolean isAccepted;

    private Tag() { }

    public Tag(String name, boolean isAccepted) {
        this.id = 0L;
        this.name = name;
        this.isAccepted = isAccepted;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setIsAccepted(boolean isAccepted) {
        this.isAccepted = isAccepted;
    }

    public boolean getIsAccepted() {
        return isAccepted;
    }
}
