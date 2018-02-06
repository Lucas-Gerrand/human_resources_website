package hrrss.test.model;

import java.sql.SQLException;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.BeforeClass;

public abstract class AbstractTestSuite {

    private static EntityManagerFactory emf;

    @BeforeClass
    public static void setUpClass() {
	emf = Persistence.createEntityManagerFactory("hrrss_pu");
    }

    // @AfterClass
    public static void tearDownClass() throws SQLException {
	emf.close();
    }

    public static EntityManagerFactory getEmf() {
	return emf;
    }

    public static void setEmf(EntityManagerFactory emf) {
	AbstractTestSuite.emf = emf;
    }

}