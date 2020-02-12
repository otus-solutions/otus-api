package unattached;

import br.org.otus.laboratory.participant.ParticipantLaboratory;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

public class UnattachedLaboratory implements UnattachedLaboratoryService{

  @Override
  public ParticipantLaboratory create(Long rn) throws DataNotFoundException {
    return null;
  }
}
