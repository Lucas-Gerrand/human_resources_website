package hrrss.dao.impl;

import hrrss.model.impl.TestData;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(value = "transactionManagerHrrss", readOnly = false, propagation = Propagation.NOT_SUPPORTED, timeout = 180)
public class TestDAO extends AbstractDAO<TestData> {

}
