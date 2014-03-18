package hrrss.test.model;

import hrrss.model.impl.ModelFactory;
import hrrss.model.impl.TestData;
import hrrss.service.impl.TestService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
public class Test_Test {

    ModelFactory modelFactory = new ModelFactory();

    @Autowired
    private TestService service;

    @Test
    public void Test() {

    }

    @Before
    public void setUp() {

	TestData testData = new TestData();
	testData.setData("String");

	service.save(testData);
    }

}
