package org.ccem.otus.model.survey.activity.interview;

import org.ccem.otus.model.survey.activity.interviewer.Interviewer;

import java.time.LocalDateTime;

public class Interview {

	private String objectType;
	private LocalDateTime date;
	private Interviewer interviewer;

	public String getObjectType() {
		return objectType;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public Interviewer getInterviewer() {
		return interviewer;
	}

}
