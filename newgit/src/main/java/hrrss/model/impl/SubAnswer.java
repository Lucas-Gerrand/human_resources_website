package hrrss.model.impl;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import hrrss.model.IQuastion;
import hrrss.model.ISubAnswer;

@Entity
@Table(name = "SubAnswer")
@NamedQueries({
	@NamedQuery(name = "loadSubAnswersByQuestion", query = "select s from SubAnswer s where s.question.id = :id")
})
public class SubAnswer implements ISubAnswer, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_subanswer_generator")
	@SequenceGenerator(name = "seq_subanswer_generator", sequenceName = "SEQ_SUBANSWER")
	@Column(name = "ID")
	private Long id;

	@ManyToOne(targetEntity = Quastion.class)
	@JoinColumn(name = "Quastion_ID")
	private IQuastion question;

//	@Column(name = "number")
//	private int number;
//	
//	public int getNumber() {
//		return number;
//	}
//
//	public void setNumber(int number) {
//		this.number = number;
//	}

	@Column(name = "Subanswer")
	private String subAnswer;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;	
	}

	@Override
	public IQuastion getQuestion() {
		return question;
	}

	@Override
	public void setQuestion(IQuastion question) {
		this.question = question;
	}

	@Override
	public String getSubAnswer() {
		return subAnswer;
	}

	@Override
	public void setSubAnswer(String subAnswer) {
		this.subAnswer= subAnswer;
	}

}
