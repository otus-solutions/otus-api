package br.org.otus.laboratory.project.business;

import br.org.otus.laboratory.configuration.collect.aliquot.AliquoteDescriptor;
import br.org.otus.laboratory.project.aliquot.WorkAliquot;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import java.util.LinkedHashSet;
import java.util.List;

public interface LaboratoryProjectService {

    List<WorkAliquot> getAllAliquots() throws DataNotFoundException;
    LinkedHashSet<AliquoteDescriptor> getAvailableExams(String center) throws DataNotFoundException;
}
