package br.org.otus.laboratory.project.business;

import br.org.otus.laboratory.project.aliquot.WorkAliquot;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import java.util.List;

public interface LaboratoryProjectService {

    public List<WorkAliquot> getAllAliquots() throws DataNotFoundException;
}
