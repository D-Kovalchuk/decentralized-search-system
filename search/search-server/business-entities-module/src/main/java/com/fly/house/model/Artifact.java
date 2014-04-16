package com.fly.house.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import static javax.persistence.FetchType.LAZY;

/**
 * Created by dimon on 4/15/14.
 */
@Entity
public class Artifact extends BasedEntity {

    @Lob
    @NotEmpty
    @Basic(fetch = LAZY)
    private String fullText;

    private String shortText;

    @ManyToOne
    private Account account;

    public String getFullText() {
        return fullText;
    }

    public void setFullText(String fullText) {
        this.fullText = fullText;
    }

    public String getShortText() {
        return shortText;
    }

    public void setShortText(String shortText) {
        this.shortText = shortText;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
