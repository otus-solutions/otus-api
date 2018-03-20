package org.ccem.otus.persistence;

import org.ccem.otus.model.dataSources.examResult.ExamResultDataSource;
import org.ccem.otus.model.dataSources.examResult.ExamResultDataSourceResult;

public interface ExamResultDataSourceDao {

	ExamResultDataSourceResult getResult(Long recruitmentNumber, ExamResultDataSource examDataSource);

}
