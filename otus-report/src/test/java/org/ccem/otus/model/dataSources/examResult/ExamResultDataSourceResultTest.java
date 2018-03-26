package org.ccem.otus.model.dataSources.examResult;

import java.util.List;

import org.junit.Before;

import br.org.otus.examUploader.ExamResult;

public class ExamResultDataSourceResultTest {

	private ExamResultDataSourceResult examResultDataSourceResult;
	private List<ExamResult> result;

	@Before
	public void setUp() {
		this.examResultDataSourceResult = new ExamResultDataSourceResult();		
	}

}
