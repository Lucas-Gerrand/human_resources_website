package hrrss.dao.impl;

import hrrss.dao.IEmployerDAO;

import org.hibernate.SessionFactory;

// @Component("EmployerDao")
// @Transactional(value = "transactionManagerHrrss", readOnly = true, propagation = Propagation.NOT_SUPPORTED, timeout = 180)
public class EmployerDAO implements IEmployerDAO {

    // @Autowired
    private SessionFactory remoteServerSessionFactory;

}
