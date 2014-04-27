package com.fly.house.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import java.util.List;

import static com.fly.house.model.Role.ROLE_USER;
import static javax.persistence.EnumType.STRING;

/**
 * Created by dimon on 4/15/14.
 */
@Entity
public class Account extends BasedEntity {

    @NotEmpty
    @Column(unique = true)
    private String name;

    @Email
    @NotEmpty
    @Column(unique = true)
    private String email;

    @NotEmpty
    private String password;

    @ManyToMany(mappedBy = "accounts")
    private List<Artifact> artifacts;

    @NotNull
    @Enumerated(STRING)
    private Role role;

    public Account() {
        role = ROLE_USER;
    }

    public Account(String name, String email, String password) {
        this();
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Artifact> getArtifacts() {
        return artifacts;
    }

    public void setArtifacts(List<Artifact> artifacts) {
        this.artifacts = artifacts;
    }

    @Override
    public String toString() {
        return "Account{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", artifacts=" + artifacts +
                ", role=" + role +
                '}';
    }
}
