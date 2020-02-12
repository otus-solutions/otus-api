package unattached;

import br.org.otus.laboratory.configuration.collect.group.CollectGroupDescriptor;
import br.org.otus.laboratory.participant.ParticipantLaboratory;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.FieldCenter;

public interface UnattachedLaboratoryService {
  ParticipantLaboratory create(CollectGroupDescriptor collectGroupDescriptor, FieldCenter fieldCenter) throws DataNotFoundException;
}
