package org.ccem.otus.persistence;

import org.ccem.otus.model.dataSources.exam.ExamDataSource;
import org.ccem.otus.model.dataSources.exam.ExamDataSourceResult;

public interface ExamDataSourceDao {

	ExamDataSourceResult getResult(Long recruitmentNumber, ExamDataSource examDataSource);

}
