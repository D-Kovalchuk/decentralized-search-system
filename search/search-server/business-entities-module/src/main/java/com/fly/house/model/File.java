package com.fly.house.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

/**
 * Created by dimon on 5/2/14.
 */
@Entity
public class File extends BasedEntity {

    private String path;

    @NotNull
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Artifact artifact;

    @NotNull
    @OneToOne
    private Account account;

    public File(Account account, Artifact artifact, String path) {
        this.path = path;
        this.artifact = artifact;
        this.account = account;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Artifact getArtifact() {
        return artifact;
    }

    public void setArtifact(Artifact artifact) {
        this.artifact = artifact;
    }
}
