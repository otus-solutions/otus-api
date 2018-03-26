package org.ccem.otus.persistence;

import java.util.List;

import org.ccem.otus.model.dataSources.examResult.ExamResultDataSource;
import org.ccem.otus.model.dataSources.examResult.ExamResultDataSourceResult;

public interface ExamResultDataSourceDao {

	List<ExamResultDataSourceResult> getResult(Long recruitmentNumber, ExamResultDataSource examDataSource);

}
