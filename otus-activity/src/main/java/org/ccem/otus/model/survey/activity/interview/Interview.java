package org.ccem.otus.model.survey.activity.interview;

import java.time.LocalDateTime;

import org.ccem.otus.model.survey.activity.interviewer.Interviewer;

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
