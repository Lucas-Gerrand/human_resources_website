package hrrss.model.impl;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "TEST_DATA")
public class TestData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_test_generator")
    @SequenceGenerator(name = "seq_test_generator", sequenceName = "SEQ_TEST")
    @Column(name = "ID")
    private Long id;

    @Column(name = "DATA")
    private String data;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getData() {
	return data;
    }

    public void setData(String data) {
	this.data = data;
    }

}
