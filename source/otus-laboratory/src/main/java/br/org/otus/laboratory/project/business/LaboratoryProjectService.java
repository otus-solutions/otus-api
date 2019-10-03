package br.org.otus.laboratory.project.business;

import java.util.LinkedHashSet;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import br.org.otus.laboratory.configuration.aliquot.AliquotExamCorrelation;
import br.org.otus.laboratory.configuration.collect.aliquot.AliquoteDescriptor;

public interface LaboratoryProjectService {

  LinkedHashSet<AliquoteDescriptor> getAvailableExams(String center) throws DataNotFoundException;

  AliquotExamCorrelation getAliquotExamCorrelation() throws DataNotFoundException;

}
