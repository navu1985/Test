package com.datacert.surveysystem.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.datacert.surveysystem.dto.QuestionResponse;

@XmlRootElement(name = "questionResponse")
public class QuestionResponseDto implements QuestionResponse, Comparable<QuestionResponseDto> {

	private String responseDescription;
	private long responseSequence;

	@XmlElement(name = "responseDescription")
	public String getResponseDescription() {
		return responseDescription;
	}

	public void setResponseDescription(String responseDescription) {
		this.responseDescription = responseDescription;
	}

	@XmlElement(name = "responseSequence")
	public long getResponseSequence() {
		return responseSequence;
	}

	public void setResponseSequence(long responseSequence) {
		this.responseSequence = responseSequence;
	}

	@Override
	public int compareTo(QuestionResponseDto question) {
		if (this.responseSequence > question.getResponseSequence())
			return 1; // make -1 to sort in decreasing order
		else if (this.responseSequence < question.getResponseSequence())
			return -1;// make 1 to sort in decreasing order
		else
			return 0;
	}
}
