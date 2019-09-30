package org.ccem.otus.model.dataSources.activity;

import org.ccem.otus.model.survey.activity.filling.FillContainer;

public class ActivityDataSourceFilters {
	
	private String acronym = null;
	private String category = null;
	private ActivityDataSourceStatusHistoryFilter statusHistory = null;
	private FillContainer fillContainer = null;

	public String getAcronym() {
		return acronym;
	}

	public String getCategory() {
		return category;
	}

	public FillContainer getFillContainer() {
		return fillContainer;
	}

	public ActivityDataSourceStatusHistoryFilter getStatusHistory() {
		return statusHistory;
	}
}
