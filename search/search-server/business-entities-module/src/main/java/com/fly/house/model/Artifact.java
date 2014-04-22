package com.fly.house.model;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

/**
 * Created by dimon on 4/15/14.
 */
//todo set up configuration for lucene
@Entity
@Indexed
public class Artifact extends BasedEntity {

    @Field
    private String title;

    @Field
    @Lob
    @NotEmpty
    @Basic(fetch = LAZY)
    private String fullText;

    @Field
    @Lob
    @NotEmpty
    @Basic(fetch = LAZY)
    private String shortText;

    @ManyToMany
    @Basic(fetch = LAZY)
    private List<Account> accounts;

    @Field
    @Enumerated(STRING)
    private ArtifactCategory category;

    private String type;

    private Long size;

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

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArtifactCategory getCategory() {
        return category;
    }

    public void setCategory(ArtifactCategory category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }
}
