package br.org.otus.laboratory.unattached;

import br.org.otus.laboratory.unattached.DTOs.ListUnattachedLaboratoryDTO;
import br.org.otus.laboratory.unattached.model.UnattachedLaboratory;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

public interface UnattachedLaboratoryDao {
  void persist(UnattachedLaboratory unattachedLaboratory);

  ListUnattachedLaboratoryDTO find(String fieldCenterAcronym, String collectGroupDescriptorName, int page, int quantityByPage) throws DataNotFoundException;

  UnattachedLaboratory find(int laboratoryIdentification) throws DataNotFoundException;

  void update(Integer identification, UnattachedLaboratory unattachedLaboratory);
}
