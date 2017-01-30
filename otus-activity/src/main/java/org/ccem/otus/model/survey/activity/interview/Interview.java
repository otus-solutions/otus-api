package org.ccem.otus.model.survey.activity.interview;

import java.util.Date;

import org.ccem.otus.model.survey.activity.interviewer.Interviewer;

public class Interview {

	private String objectType;
	private Date date;
	private Interviewer interviewer;

	public String getObjectType() {
		return objectType;
	}

	public Date getDate() {
		return date;
	}

	public Interviewer getInterviewer() {
		return interviewer;
	}

}
