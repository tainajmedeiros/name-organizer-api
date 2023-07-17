package com.NameOrganizerAPI.model;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * This class represent the entity users in database
 */
@Entity
@Table(name="users")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @Column
    @Size(max=100)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
