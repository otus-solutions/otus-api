package org.ccem.otus.model.dataSources.examResult;

import java.util.List;

import org.ccem.otus.model.dataSources.exam.ExamDataSourceResult;
import org.junit.Before;
import org.junit.Ignore;

import br.org.otus.examUploader.ExamResult;

@Ignore
public class ExamResultDataSourceResultTest {

	private ExamDataSourceResult examResultDataSourceResult;
	private List<ExamResult> result;

	@Before
	public void setUp() {
		this.examResultDataSourceResult = new ExamDataSourceResult();		
	}

}
