package com.fly.house.dao.repository.repository;

import com.fly.house.dao.config.DataAccessConfig;
import com.fly.house.dao.repository.ArtifactRepository;
import com.fly.house.dao.repository.IpRepository;
import com.fly.house.dao.test.AbstractDbUnit;
import com.fly.house.model.Account;
import com.fly.house.model.Artifact;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.net.InetAddress;
import java.util.List;

import static com.fly.house.model.ArtifactCategory.COMPUTER_SCIENCE;
import static java.util.Arrays.asList;
import static org.fest.assertions.Assertions.assertThat;
import static org.hibernate.search.jpa.Search.getFullTextEntityManager;
import static org.springframework.transaction.TransactionDefinition.PROPAGATION_REQUIRES_NEW;

/**
 * Created by dimon on 5/1/14.
 */

//todo fix ignored tests
@Transactional
@ActiveProfiles("dev")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DataAccessConfig.class)
public class ArtifactRepositoryImplTest extends AbstractDbUnit {

    public static final PageRequest PAGE_REQUEST = new PageRequest(0, 10);

    @Autowired
    private ArtifactRepository artifactRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private Environment env;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PlatformTransactionManager transactionManager;


    @Autowired
    private IpRepository ipRepository;

    @Before
    public void setUp() throws Exception {
        ipRepository.put("login1", InetAddress.getByName("localhost"));
        ipRepository.put("login3", InetAddress.getByName("localhost"));

        FullTextEntityManager fullTextEntityManager = getFullTextEntityManager(em);

        loadDataSet(env);
        cleanAndInsert(dataSource);

        fullTextEntityManager.createIndexer().startAndWait();
    }

    @Test
    public void save() throws Exception {
        Artifact artifact = createArtifact();

        Artifact savedArtifact = artifactRepository.save(artifact);

        assertThat(savedArtifact.getId()).isNotNull();
    }

    @Test
    @Ignore
    public void searchByTitle() throws Exception {
        Page<Artifact> artifacts = artifactRepository.search("Title", PAGE_REQUEST);

        assertThat(artifacts.getNumberOfElements()).isEqualTo(3);
    }

    @Test
    @Ignore
    public void searchByFullText() throws Exception {
        Page<Artifact> artifacts = artifactRepository.search("email", PAGE_REQUEST);

        assertThat(artifacts.getNumberOfElements()).isEqualTo(2);
    }

    @Test
    public void deleteShouldDeleteArtifact() throws Exception {
        artifactRepository.delete(1L);

        Artifact artifact = em.find(Artifact.class, 1L);

        assertThat(artifact).isNull();
    }

    @Test(expected = DataAccessException.class)
    public void deleteShouldThrowExceptionWhenArtifactDoesNotExist() throws Exception {
        artifactRepository.delete(-1L);
    }

    @Test
    public void update() throws Exception {
        Artifact artifact = em.find(Artifact.class, 1L);
        artifact.setTitle("New TiTle");

        artifactRepository.update(artifact);

        Artifact findedArtifact = em.find(Artifact.class, 1L);

        assertThat(findedArtifact.getTitle()).isEqualTo("New TiTle");
    }

    @Test
    public void findOneShouldReturnArtifactWhenItExists() throws Exception {
        Artifact artifact = artifactRepository.findOne(1L);

        assertThat(artifact).isNotNull();
    }

    @Test
    public void findOneShouldReturnNullWhenItDoesNotExist() throws Exception {
        Artifact artifact = artifactRepository.findOne(-1L);

        assertThat(artifact).isNull();
    }

    @Test
    @Ignore
    public void searchOnlyAvailableByTitle() {
        Page<Artifact> artifacts = artifactRepository.searchOnlyAvailable("Title", PAGE_REQUEST);

        assertThat(artifacts.getNumberOfElements()).isEqualTo(2);
    }

    @Test
    @Ignore
    public void searchOnlyAvailableByFullText() {
        Page<Artifact> artifacts = artifactRepository.searchOnlyAvailable("If you find", PAGE_REQUEST);

        assertThat(artifacts.getNumberOfElements()).isEqualTo(2);
    }

    @Test
    public void searchOnlyAvailableShouldReturnDocumentsOnlyIfOneOfUsersOnline() throws Exception {
        Page<Artifact> artifacts = artifactRepository.searchOnlyAvailable("If you find", PAGE_REQUEST);

        List<Artifact> content = artifacts.getContent();
        content.stream()
                .flatMap(a -> a.getAccounts().stream())
                .map(Account::isOnline)
                .forEach(Assert::assertTrue);
    }

    @Test
    @Ignore
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void index() throws Exception {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(PROPAGATION_REQUIRES_NEW);
        TransactionStatus transaction = transactionManager.getTransaction(def);

        Artifact artifact = createArtifact();
        em.persist(artifact);

        transactionManager.commit(transaction);

        transaction = transactionManager.getTransaction(def);
        artifactRepository.index(artifact.getId());
        transactionManager.commit(transaction);

        transaction = transactionManager.getTransaction(def);
        Page<Artifact> page = artifactRepository.search("full text", PAGE_REQUEST);
        assertThat(page.getNumberOfElements()).isGreaterThan(0);
        transactionManager.commit(transaction);
    }

    private Artifact createArtifact() {
        Artifact artifact = new Artifact();
        artifact.setTitle("New artifact");
        artifact.setCategory(COMPUTER_SCIENCE);
        artifact.setFullText("full text full text full text full text full text full text full text full text full text");
        artifact.setShortText("full text full text full text full text full text");
        artifact.setSize(100L);
        artifact.setDigest("f5467ul242l432t342eqxt");
        artifact.setType("pdf/application");
        artifact.setAccounts(asList(new Account("name", "name@email.com", "password123")));
        return artifact;
    }


}
