package com.fly.house.model;

import org.apache.lucene.analysis.charfilter.HTMLStripCharFilterFactory;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.core.StopFilterFactory;
import org.apache.lucene.analysis.snowball.SnowballPorterFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static org.hibernate.search.annotations.TermVector.WITH_POSITION_OFFSETS;

/**
 * Created by dimon on 4/15/14.
 */
@Entity
@Indexed
@AnalyzerDef(
        name = "customanalyzer",
        charFilters = @CharFilterDef(factory = HTMLStripCharFilterFactory.class),
        tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class),
        filters = {
                @TokenFilterDef(factory = LowerCaseFilterFactory.class),
                @TokenFilterDef(factory = SnowballPorterFilterFactory.class),
                @TokenFilterDef(factory = StopFilterFactory.class, params = @Parameter(name = "ignoreCase", value = "true"))
        }
)
public class Artifact extends BasedEntity {

    @NotEmpty
    @Field(boost = @Boost(2.0f),
            termVector = WITH_POSITION_OFFSETS,
            analyzer = @Analyzer(definition = "customanalyzer"))
    private String title;

    @Lob
    @NotEmpty
    @Basic(fetch = LAZY)
    @Field(boost = @Boost(1.8f),
            termVector = WITH_POSITION_OFFSETS,
            analyzer = @Analyzer(definition = "customanalyzer"))
    private String fullText;

    @Lob
    @NotEmpty
    @Basic(fetch = LAZY)
    private String shortText;

    @Basic(fetch = LAZY)
    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<Account> accounts;

    @Enumerated(STRING)
    private ArtifactCategory category;

    private String type;

    private Long size;

    public Artifact() {
        category = ArtifactCategory.NONE;
    }

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

    @Override
    public String toString() {
        return "Artifact{" +
                "id='" + getId() + '\'' +
                "title='" + title + '\'' +
                ", fullText='" + fullText + '\'' +
                ", shortText='" + shortText + '\'' +
                ", accounts=" + accounts +
                ", category=" + category +
                ", type='" + type + '\'' +
                ", size=" + size +
                '}';
    }

}
