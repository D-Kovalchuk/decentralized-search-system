package com.fly.house.dao.repository;

import com.fly.house.model.Account;
import com.fly.house.model.Artifact;
import org.apache.lucene.search.Query;
import org.hibernate.search.SearchFactory;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.function.Predicate;

import static org.hibernate.search.jpa.Search.getFullTextEntityManager;

/**
 * Created by dimon on 4/27/14.
 */
@Repository
public class ArtifactRepositoryImpl implements ArtifactRepository {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private IpRepository ipRepository;

    private FullTextEntityManager fullTextEntityManager;

    public void init() {
        fullTextEntityManager = getFullTextEntityManager(entityManager);
    }

    public void searchOnlineOnly(String queryString) {
        List<Artifact> list = search(queryString);

        Predicate<Account> isOnline = ac -> ipRepository.isOnline(ac.getName());
        Predicate<? super Artifact> predicate = a -> a.getAccounts().stream().filter(isOnline).count() > 0;
        list.stream().filter(predicate);
    }

    public List<Artifact> search(String queryString, Pageable pageRequest) {
        pageRequest.getOffset();
        pageRequest.next().getOffset();

        SearchFactory searchFactory = fullTextEntityManager.getSearchFactory();

        QueryBuilder qb = getBuildQuery(searchFactory);
        Query query = createQuery(qb, queryString);

        FullTextQuery fullTextQuery = fullTextEntityManager.createFullTextQuery(query, Artifact.class);

        fullTextQuery.setFirstResult(0);
        fullTextQuery.setMaxResults(20);

        return fullTextQuery.getResultList();
    }

    public List<Artifact> search(String queryString) {
        return null;
    }

    public Artifact save(Artifact artifact) {
        entityManager.persist(artifact);
        return artifact;
    }

    public void update(Artifact artifact) {
        entityManager.merge(artifact);
    }

    public void delete(Artifact artifact) {
        entityManager.merge(artifact);
    }

    private Query createQuery(QueryBuilder queryBuilder, String queryString) {
        return queryBuilder
                .keyword()
                .fuzzy()
                .withPrefixLength(1)
                .onFields("title", "fulltext", "category")
                .matching(queryString)
                .createQuery();
    }

    private QueryBuilder getBuildQuery(SearchFactory searchFactory) {
        return searchFactory
                .buildQueryBuilder()
                .forEntity(Artifact.class)
                .get();
    }

}
