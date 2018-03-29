package org.ccem.otus.persistence;

import java.util.List;

import org.ccem.otus.model.dataSources.exam.ExamDataSourceResult;
import org.ccem.otus.model.dataSources.exam.ExamDataSource;

public interface ExamDataSourceDao {

	List<ExamDataSourceResult> getResult(Long recruitmentNumber, ExamDataSource examDataSource);

}
