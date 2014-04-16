package com.fly.house.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Created by dimon on 4/15/14.
 */
@Entity
public class Account extends BasedEntity {

    @NotEmpty
    private String name;

    @OneToMany(mappedBy = "account")
    private List<Artifact> artifacts;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Artifact> getArtifacts() {
        return artifacts;
    }

    public void setArtifacts(List<Artifact> artifacts) {
        this.artifacts = artifacts;
    }
}
