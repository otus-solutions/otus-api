package org.ccem.otus.persistence;

import java.util.List;

import org.ccem.otus.model.dataSources.exam.ExamDataSourceResult;
import org.ccem.otus.model.dataSources.exam.ExamResultDataSource;

public interface ExamResultDataSourceDao {

	List<ExamDataSourceResult> getResult(Long recruitmentNumber, ExamResultDataSource examDataSource);

}
