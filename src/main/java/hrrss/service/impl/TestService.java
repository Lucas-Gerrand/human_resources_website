package hrrss.service.impl;

import hrrss.dao.impl.TestDAO;
import hrrss.model.impl.TestData;

import java.util.ArrayList;
import java.util.List;

public class TestService {

    private TestDAO dao;

    public TestDAO getDao() {
	return dao;
    }

    public void setDao(TestDAO dao) {
	this.dao = dao;
    }

    public void save(TestData entity) {
	dao.save((TestData) entity);
    }

    public void update(TestData entity) {
	dao.update((TestData) entity);
    }

    public TestData find(Long id) {
	return (TestData) dao.findOne(id);
    }

    public void deleteById(Long id) {
	dao.deleteById(id);
    }

    public List<TestData> findAll() {
	dao.setClazz(TestData.class);
	List<TestData> data = new ArrayList<TestData>();
	for (TestData testData : dao.findAll()) {
	    data.add(testData);
	}
	return data;
    }
}