package com.datacert.surveysystem.db.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.datacert.surveysystem.dto.QuestionResponse;

@Entity
@Table(name = "pp_survey_response_answer")
public class ExpectedQuestionResponse implements Serializable, QuestionResponse, Comparable<ExpectedQuestionResponse> {

	private static final long serialVersionUID = 895321048240082267L;

	@Id
	@Column(name = "id")
	@GeneratedValue
	private long expectedResponseId;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "question_id", referencedColumnName = "id")
	private QuestionDescriptor question;

	@Column(name = "question_response")
	private String expectedResponseText;

	@Column(name = "sequence")
	private long sequence;

	public long getExpectedResponseId() {
		return expectedResponseId;
	}

	public void setExpectedResponseId(long expectedResponseId) {
		this.expectedResponseId = expectedResponseId;
	}

	public String getExpectedResponseText() {
		return expectedResponseText;
	}

	public void setExpectedResponseText(String expectedResponseText) {
		this.expectedResponseText = expectedResponseText;
	}

	public long getSequence() {
		return sequence;
	}

	public void setSequence(long sequence) {
		this.sequence = sequence;
	}

	@Override
	public String getResponseDescription() {
		return expectedResponseText;
	}

	@Override
	public long getResponseSequence() {
		return sequence;
	}

	public QuestionDescriptor getQuestion() {
		return question;
	}

	public void setQuestion(QuestionDescriptor question) {
		this.question = question;
	}

	@Override
	public int compareTo(ExpectedQuestionResponse question) {
		if (this.sequence > question.getResponseSequence())
			return 1; // make -1 to sort in decreasing order
		else if (this.sequence < question.getResponseSequence())
			return -1;// make 1 to sort in decreasing order
		else
			return 0;
	}
}