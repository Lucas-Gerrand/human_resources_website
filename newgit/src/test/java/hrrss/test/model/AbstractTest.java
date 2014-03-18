package hrrss.test.model;

import hrrss.model.impl.ModelFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.junit.Before;

public abstract class AbstractTest {

    protected EntityManagerFactory emf;
    protected EntityManager em;

    // protected JdbcConnection jdbcConnection;
    protected ModelFactory modelFactory;

    //@Before
    public void init() throws Exception {
	emf = AbstractTestSuite.getEmf();
	em = emf.createEntityManager();
	// jdbcConnection = new JdbcConnection();
	modelFactory = new ModelFactory();
    }

    // @After
    public void clean() throws Exception {
	if (em.getTransaction().isActive())
	    em.getTransaction().rollback();
	em.close();
	// JdbcHelper.cleanTables(jdbcConnection);
	// jdbcConnection.disconnect();
    }

    private void setUp() {

    }
}
