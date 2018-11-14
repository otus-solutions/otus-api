package br.org.otus.laboratory.project.business;

import br.org.otus.laboratory.configuration.aliquot.AliquotExamCorrelation;
import br.org.otus.laboratory.configuration.collect.aliquot.AliquoteDescriptor;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import java.util.LinkedHashSet;

public interface LaboratoryProjectService {

    LinkedHashSet<AliquoteDescriptor> getAvailableExams(String center) throws DataNotFoundException;

    AliquotExamCorrelation getAliquotExamCorrelation() throws DataNotFoundException;
}
