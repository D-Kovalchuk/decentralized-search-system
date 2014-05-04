package com.fly.house.dao.repository;

import com.fly.house.model.Account;
import com.fly.house.model.Artifact;
import org.apache.lucene.search.Query;
import org.hibernate.search.SearchFactory;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.function.Supplier;

import static java.util.stream.Collectors.toList;
import static org.hibernate.search.jpa.Search.getFullTextEntityManager;

/**
 * Created by dimon on 4/27/14.
 */
@Repository
@SuppressWarnings("unchecked")
public class ArtifactRepositoryImpl implements ArtifactRepository {

    private static Logger logger = LoggerFactory.getLogger(ArtifactRepositoryImpl.class);

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private IpRepository ipRepository;

    private FullTextEntityManager fullTextEntityManager;

    @PostConstruct
    public void init() {
        fullTextEntityManager = getFullTextEntityManager(entityManager);
    }

    @Override
    public Page<Artifact> searchOnlyAvailable(String queryString, Pageable pageable) {
        FullTextQuery fullTextQuery = createJpaQuery(queryString);
        List<Artifact> artifacts = getAvailableArtifacts(fullTextQuery, () -> (long) (pageable.next().getOffset() - pageable.getOffset()));
        resolveOnlineStatus(artifacts);
        int resultSize = fullTextQuery.getResultSize();
        return foldPage(artifacts, pageable, resultSize);
    }

    @Override
    public Page<Artifact> search(String queryString, Pageable pageRequest) {
        FullTextQuery fullTextQuery = createJpaQuery(queryString);
        setLimit(fullTextQuery, pageRequest);
        List<Artifact> artifacts = fullTextQuery.getResultList();
        resolveOnlineStatus(artifacts);
        int resultSize = fullTextQuery.getResultSize();
        return foldPage(artifacts, pageRequest, resultSize);
    }

    public void index(Long id) {
        Artifact artifact = fullTextEntityManager.getReference(Artifact.class, id);
        fullTextEntityManager.index(artifact);
    }

    @Override
    public Artifact save(Artifact artifact) {
        fullTextEntityManager.persist(artifact);
        logger.debug("Object saved: {}", artifact);
        return artifact;
    }

    @Override
    public void update(Artifact artifact) {
        fullTextEntityManager.merge(artifact);
        logger.debug("Object updated: {}", artifact);
    }

    @Override
    public void delete(Long id) {
        Artifact artifact = fullTextEntityManager.getReference(Artifact.class, id);
        fullTextEntityManager.remove(artifact);
        logger.debug("Artifact with id = {} has been deleted", artifact.getId());
    }

    @Override
    public Artifact findOne(Long id) {
        return fullTextEntityManager.find(Artifact.class, id);
    }

    public Artifact findByDigest(String digest) {
        TypedQuery<Artifact> query = fullTextEntityManager.createQuery("select a from Artifact a where a.digest = :digest", Artifact.class);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public long size() {
        TypedQuery<Artifact> query = fullTextEntityManager.createQuery("select a from Artifact a", Artifact.class);
        return query
                .getResultList()
                .size();
    }

    private List<Artifact> getAvailableArtifacts(FullTextQuery fullTextQuery, Supplier<Long> s) {
        List<Artifact> resultList = fullTextQuery.getResultList();
        return resultList.stream()
                .filter(this::isAnyUserOnline)
                .limit(s.get())
                .collect(toList());
    }

    private FullTextQuery createJpaQuery(String queryString) {
        Query query = buildQuery(queryString);
        return fullTextEntityManager.createFullTextQuery(query, Artifact.class);
    }

    private Page<Artifact> foldPage(List<Artifact> artifacts, Pageable pageable, long total) {
        Page<Artifact> page = new PageImpl<>(artifacts, pageable, total);
        return page;
    }

    private void setLimit(FullTextQuery fullTextQuery, Pageable pageRequest) {
        int from = pageRequest.getOffset();
        int to = pageRequest.next().getOffset();
        logger.debug("Set limit [from={} to={}]", from, to);
        fullTextQuery.setFirstResult(from);
        fullTextQuery.setMaxResults(to);
    }

    private void resolveOnlineStatus(List<Artifact> artifacts) {
        logger.debug("Resolve online status for each account who have artifact");
        artifacts.stream()
                .flatMap(artifact -> artifact.getAccounts().stream())
                .forEach(account -> {
                    String name = account.getName();
                    boolean online = ipRepository.isOnline(name);
                    account.setOnline(online);
                });
    }

    private boolean isAnyUserOnline(Artifact artifact) {
        return artifact.getAccounts()
                .stream()
                .map(Account::getName)
                .anyMatch(ipRepository::isOnline);
    }


    private Query buildQuery(String queryString) {
        logger.debug("Building query...");
        SearchFactory searchFactory = fullTextEntityManager.getSearchFactory();
        QueryBuilder qb = getBuildQuery(searchFactory);
        return createLuceneQuery(qb, queryString);
    }

    private Query createLuceneQuery(QueryBuilder queryBuilder, String queryString) {
        logger.debug("Setting up query options");
        return queryBuilder
                .keyword()
                .fuzzy()
                .withPrefixLength(1)
                .onFields("title", "fullText")
                .matching(queryString.toLowerCase())
                .createQuery();
    }

    private QueryBuilder getBuildQuery(SearchFactory searchFactory) {
        return searchFactory
                .buildQueryBuilder()
                .forEntity(Artifact.class)
                .get();
    }

}
